package com.dkglabs.loan_application.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.model.ItemViewModel;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.databinding.FragmentPrePostDocUploadBinding;

import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.loan_application.staticModels.PreStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.request.PostSaveLoanRequest;
import com.dkglabs.model.request.PreSendRequest;
import com.dkglabs.model.request.SaveLoanFulfillment;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import okhttp3.internal.Util;


public class PrePostDocUpload extends BaseFragment implements View.OnClickListener, ResponseListener {
    FragmentPrePostDocUploadBinding binding;
    protected ActivityResultLauncher<Intent> launcherDl;
    protected ActivityResultLauncher<Intent> launcherDlCam;
    protected ItemViewModel viewModel;
    private ContentValues values;
    private Uri imageUri;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    protected ActivityResultLauncher<String[]> launcherPermissionCamera;
    private int SET_CODE=000;

    ApplicantDocDetails response;

    private final int[] ACTION_DOC={101,102,103,104,105};


    List<Boolean> imgList = Arrays.asList(false, false, false, false, false);
    List<Uri> uriList = Arrays.asList(null, null, null, null, null);
    boolean selectDone=false;
    int count1=0;
    int count2=0;

    private static final int RESIDENCE_IMAGE=508;

    public PrePostDocUpload() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response=PrePostDocUploadArgs.fromBundle(getArguments()).getRespomse();

        launcherPermissionStorage = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (Boolean.TRUE.equals(isGranted)) {
                startGalleryIntent();
            } else {
                showSettingsDialog();
            }
        });
        launcherPermissionCamera = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (Boolean.TRUE.equals(isGranted.get(Manifest.permission.READ_MEDIA_IMAGES))
                        && Boolean.TRUE.equals(isGranted.get(Manifest.permission.CAMERA))) {
                    startCameraIntent();
                } else {
                    showSettingsDialog();
                }
            } else {
                if (Boolean.TRUE.equals(isGranted.get(Manifest.permission.READ_EXTERNAL_STORAGE))
                        && Boolean.TRUE.equals(isGranted.get(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        && Boolean.TRUE.equals(isGranted.get(Manifest.permission.CAMERA))) {
                    startCameraIntent();
                } else {
                    showSettingsDialog();
                }
            }
        });

        launcherDl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri dlUri = result.getData().getData();
                    startCropImageActivity(dlUri,SET_CODE);
                }
            }
        });
        launcherDlCam = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        startCropImageActivity(imageUri,SET_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentPrePostDocUploadBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(1);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        viewModel.setSelectedItem(applyLoanModel);

        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                ApplyLoanModel viewModel = (ApplyLoanModel) o;
                switch (viewModel.getFileMethod()) {
                    case "gallery":
                        validateStoragePermission();
                        break;
                    case "camera":
                        validateCameraPermission();
                        break;
                }
            }
        });
    }
    private void validateStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PermissionUtils.hasPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES))
                startGalleryIntent();
            else
                launcherPermissionStorage.launch(Manifest.permission.READ_MEDIA_IMAGES);
        } else {
            if (PermissionUtils.hasPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))
                startGalleryIntent();
            else
                launcherPermissionStorage.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private void validateCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(PermissionUtils.hasPermissions(getActivity(),new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA}))
                startCameraIntent();
            else
                launcherPermissionCamera.launch(new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA});
        } else {
            if(PermissionUtils.hasPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}))
                startCameraIntent();
            else
                launcherPermissionCamera.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        }
    }
    private void startGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpg");
        launcherDl.launch(Intent.createChooser(intent, getString(com.dkglabs.apply_loan.R.string.select_file_from)));
    }
    private void startCameraIntent() {
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "PAN Image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        launcherDlCam.launch(intent);
    }
    private void initializeView() {
        binding.txtNumberTitle.setText(response.getTITLE());
        binding.imgAddOne.setOnClickListener(this);
        binding.imgAddTwo.setOnClickListener(this);
        binding.imgAddThree.setOnClickListener(this);
        binding.imgAddFour.setOnClickListener(this);
        binding.imgAddFive.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        if(response.getFulFilNumberType()==RESIDENCE_IMAGE)
            binding.txtNumber.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.imgAddOne.getId()) {
            if(!imgList.get(0)) {
                Bundle args = new Bundle();
                SET_CODE = ACTION_DOC[0];
                args.putInt("action", SET_CODE);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_prePostDocUpload_to_chooseImageMethodFragment2, args);
            }else{
                clearImage(binding.imgAddOne,0);
                setImage(binding.imgAddOne,R.drawable.baseline_add_24);

                clearImage(binding.imgSelectOne);
                setImage(binding.imgSelectOne,R.drawable.dashed_border_image);
            }
        }
        else if (id == binding.imgAddTwo.getId()) {
            if(!imgList.get(1)) {
                Bundle args = new Bundle();
                SET_CODE = ACTION_DOC[1];
                args.putInt("action", SET_CODE);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_prePostDocUpload_to_chooseImageMethodFragment2, args);
            }else{
                clearImage(binding.imgAddTwo,1);
                setImage(binding.imgAddTwo,R.drawable.baseline_add_24);

                clearImage(binding.imgSelectTwo);
                setImage(binding.imgSelectTwo,R.drawable.dashed_border_image);
            }
        }
        else if (id == binding.imgAddThree.getId()) {
            if(!imgList.get(2)) {
                Bundle args = new Bundle();
                SET_CODE = ACTION_DOC[2];
                args.putInt("action", SET_CODE);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_prePostDocUpload_to_chooseImageMethodFragment2, args);
            }else{
                clearImage(binding.imgAddThree,2);
                setImage(binding.imgAddThree,R.drawable.baseline_add_24);

                clearImage(binding.imgSelectThree);
                setImage(binding.imgSelectThree,R.drawable.dashed_border_image);
            }
        }
        else if (id == binding.imgAddFour.getId()) {
            if(!imgList.get(3)) {
                Bundle args = new Bundle();
                SET_CODE = ACTION_DOC[3];
                args.putInt("action", SET_CODE);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_prePostDocUpload_to_chooseImageMethodFragment2, args);
            }else{
                clearImage(binding.imgAddFour,3);
                setImage(binding.imgAddFour,R.drawable.baseline_add_24);

                clearImage(binding.imgSelectFour);
                setImage(binding.imgSelectFour,R.drawable.dashed_border_image);
            }
        }
        else if (id == binding.imgAddFive.getId()) {
            if(!imgList.get(4)) {
                Bundle args = new Bundle();
                SET_CODE = ACTION_DOC[4];
                args.putInt("action", SET_CODE);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_prePostDocUpload_to_chooseImageMethodFragment2, args);
            }else{
                clearImage(binding.imgAddFive,4);
                setImage(binding.imgAddFive,R.drawable.baseline_add_24);

                clearImage(binding.imgSelectFive);
                setImage(binding.imgSelectFive,R.drawable.dashed_border_image);
            }
        }

        if(id==binding.btnSave.getId()){
            if(selectDone){
                for(int i=0;i<imgList.size();i++)
                    if(imgList.get(i))
                        count1++;

                if(response.getFulFilNumberType()!=RESIDENCE_IMAGE && binding.txtNumber.getText().toString().length()!=0) {
                    uploadNumber();
                    uploadDocument(uriList);
                    progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.uploading_doccument));
                    progressDialog.setCancelable(false);
                }
                else if(response.getFulFilNumberType()==RESIDENCE_IMAGE){
                    uploadDocument(uriList);
                    progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.uploading_doccument));
                    progressDialog.setCancelable(false);
                }else{
                    Toast.makeText(context , "Enter the Number", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, "Select Image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void setImage(ImageFilterButton button, int resourceId) {
        // Set a new image to the ImageFilterButton
        button.setImageResource(resourceId);
        button.setScaleType(ImageFilterButton.ScaleType.FIT_CENTER);
    }

    private void setImage(ImageFilterButton button, Uri uri) {
        // Set the image with URI directly
        button.setImageURI(uri);
        button.setScaleType(ImageFilterButton.ScaleType.FIT_CENTER);
    }
    private void clearImage(ImageFilterButton button,int id) {
        // Clear the image on the ImageFilterButton
        button.setImageResource(android.R.color.transparent);
        if(id==0){
            selectDone=false;
        }
        imgList.set(id,false);
        uriList.set(id,null);
    }
    private void clearImage(ImageFilterButton button) {
        // Clear the image on the ImageFilterButton
        button.setImageResource(android.R.color.transparent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            if (requestCode == ACTION_DOC[0]) {
                clearImage(binding.imgSelectOne);
                setImage(binding.imgSelectOne,resultUri);

                clearImage(binding.imgAddOne);
                setImage(binding.imgAddOne,R.drawable.baseline_cancel_24);

                selectDone = true;
                uriList.set(0,resultUri);
                imgList.set(0,true);

            }
            else if (requestCode == ACTION_DOC[1]) {
                clearImage(binding.imgSelectTwo);
                setImage(binding.imgSelectTwo,resultUri);

                clearImage(binding.imgAddTwo);
                setImage(binding.imgAddTwo,R.drawable.baseline_cancel_24);

                uriList.set(1,resultUri);
                imgList.set(1,true);
            }
            else if (requestCode == ACTION_DOC[2]) {
                clearImage(binding.imgSelectThree);
                setImage(binding.imgSelectThree,resultUri);

                clearImage(binding.imgAddThree);
                setImage(binding.imgAddThree,R.drawable.baseline_cancel_24);

                uriList.set(2,resultUri);
                imgList.set(2,true);
            }
            else if (requestCode == ACTION_DOC[3]) {
                clearImage(binding.imgSelectFour);
                setImage(binding.imgSelectFour,resultUri);

                clearImage(binding.imgAddFour);
                setImage(binding.imgAddFour, R.drawable.baseline_cancel_24);

                uriList.set(3,resultUri);
                imgList.set(3,true);
            }
            else if (requestCode == ACTION_DOC[4]) {
                clearImage(binding.imgSelectFive);
                setImage(binding.imgSelectFive,resultUri);

                clearImage(binding.imgAddFive);
                setImage(binding.imgAddFive,R.drawable.baseline_cancel_24);

                uriList.set(4,resultUri);
                imgList.set(4,true);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            LogsManager.printLog(cropError.getMessage());
        }
    }
    private void uploadNumber() {
        String number=binding.txtNumber.getText().toString();
        if(response.isPre_post()==1){
            PreSendRequest requestBody=new PreSendRequest();
            requestBody.setDealerId(PersistentManager.getUserResponse().getUserId());
            requestBody.setLoanId(response.getLoneId());

            switch (response.getPreNumberType()) {
                case 401:
                    // Process the document for A401
                    requestBody.setMotorNumber(number);
                    break;
                case 406:
                    // Process the document for A406
                    requestBody.setBatteryNumber(number);
                    break;
                case 411:
                    // Process the document for A411
                    requestBody.setChassisNumber(number);
                    break;
                case 416:
                    // Process the document for A416
                    requestBody.setPassbook(number);
                    break;
                case 421:
                    // Process the document for A421
                    requestBody.setInvoiceNumber(number);
                    break;
                case 426:
                    // Process the document for A426
                    requestBody.setInsuranceNumber(number);
                    break;
                case 431:
                    // Process registration document for A431
                    requestBody.setRegistrationNumber(number);
                    break;
                case 436:
                    // Process other documents for A436
                    requestBody.setAdditionalDocNumber(number);
                    break;
            }
            DocumentManager.saveLoanPreDisbursemen(999,requestBody,this);
        }else if(response.isPre_post()==3){
            PostSaveLoanRequest requestBody=new PostSaveLoanRequest();
            requestBody.setDealerId(PersistentManager.getUserResponse().getUserId());
            requestBody.setLoanId(response.getLoneId());

            switch (response.getPostNumberType()){
                case 601:
                    requestBody.setRegistrationNumber(number);
                    break;
                case 606:
                    requestBody.setAdditionalDocNumber(number);
                    break;
            }
            DocumentManager.saveLoanPostDisbursemen(998,requestBody,this);
        }else if(response.isPre_post()==2){
            SaveLoanFulfillment requestBody=new SaveLoanFulfillment();
            requestBody.setDealerId(PersistentManager.getUserResponse().getUserId());
            requestBody.setLoanId(response.getLoneId());
            switch (response.getFulFilNumberType()){
                case 501:
                    requestBody.setChequeNumber(number);
                    break;
                case 514:
                    requestBody.setAdditionalDocNumber(number);
                    break;
                case 520:
                    requestBody.setImeiNumber(number);
                    break;
            }
            DocumentManager.saveLoanFullfilment(997,requestBody,this);
        }
    }
    private void startCropImageActivity(Uri uri, int code) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(100);

        String resultAadhaarUri = UUID.randomUUID().toString() + ".jpg";
        Uri resultUri = Uri.fromFile(new File(getActivity().getCacheDir(), resultAadhaarUri));
        UCrop.of(uri, resultUri)
                .withOptions(options)
                .start(getContext(), this, code);
    }

    private void uploadDocument(List<Uri> uri) {
        DocDetails docDetails=new DocDetails();
        Uri crUri=null;
        List<String> preVrfs= PreStaticModelList.getPreOptional().get(response.getDocDetails().getVrfsCode());
        docDetails.setVrfCode(response.getDocDetails().getVrfCode());
        for(int i=0;i< preVrfs.size();i++){
            docDetails.setVrfsCode(preVrfs.get(i)); //Dynamic Static
            crUri=uri.get(i);
            docDetails.setVrfSName(response.getDocDetails().getVrfSName());
            docDetails.setVrfName(response.getDocDetails().getVrfName());
            docDetails.setMandatory(response.getDocDetails().getMandatory());
            docDetails.setUploadStatus(response.getDocDetails().isUploadStatus());
            docDetails.setUploading(response.getDocDetails().isUploading());
            docDetails.setUploadFailed(response.getDocDetails().isUploadFailed());
            if(crUri!=null)
                DocumentManager.uploadDocument(100+i , context, crUri,response.getUserId(),docDetails,  this);
        }
    }
    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if(requestCode!=998 || requestCode!=999|| requestCode!=997){
            count2++;
            if(count2==count1){
                UIUtils.dismissDialog(progressDialog);
                Toast.makeText(context, "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                binding.btnSave.setEnabled(false);
            }
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.dismissDialog(progressDialog);
        Toast.makeText(context, "Something Went Wrong " + message , Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.dismissDialog(progressDialog);
        Toast.makeText(context, "onFailure" + requestCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void commonCall(int requestCode) {
        if(requestCode!=998 || requestCode!=999 || requestCode!=997){
            count2++;
            if(count2==count1){
                UIUtils.dismissDialog(progressDialog);
                Toast.makeText(context, "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                binding.btnSave.setEnabled(false);
            }
        }
    }
}