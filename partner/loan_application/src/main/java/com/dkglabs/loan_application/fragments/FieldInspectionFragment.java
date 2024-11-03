package com.dkglabs.loan_application.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.model.ItemViewModel;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.databinding.FragmentFieldInspectionBinding;
import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.loan_application.staticModels.FieldInspectionStaticModel;
import com.dkglabs.loan_application.staticModels.PreStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class FieldInspectionFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, ResponseListener {
    String[] genders={"Male", "Female", "Others"};
    ArrayAdapter<String> genDropDownAdapter;
    private  int SET_CODE=000;
    private static final int PROFILE_CODE=101;
    private static final int DOC1=201;
    private static final int DOC2=202;
    private static final int DOC3=203;
    private ContentValues values;
    private Uri imageUri;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    protected ActivityResultLauncher<String[]> launcherPermissionCamera;
    protected ActivityResultLauncher<Intent> launcherDl;
    protected ActivityResultLauncher<Intent> launcherDlCam;
    Uri profilePic;
    List<Uri> uriList = Arrays.asList(null, null, null);
    private boolean isProfileSelected=false;
    private boolean isDoc1=false;
    private boolean isDoc2=false;
    private boolean isDoc3=false;
    ApplicantDocDetails response;
    private String NAME;
    private String AADHAAR;
    private String GENDER;
    private String ADDRESS;
    private String MOBILE;
    private String PIN;
    private int COUNT1=0;
    private int COUNT2=0;
   FragmentFieldInspectionBinding binding;
    public FieldInspectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response=FieldInspectionFragmentArgs.fromBundle(getArguments()).getResponse();
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                Navigation.findNavController(binding.getRoot()).navigate(com.dkglabs.apply_loan.R.id.action_global_loanBackPressFragment, bundle);
            }
        };

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
        binding = FragmentFieldInspectionBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
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

    private void initializeView() {
        genDropDownAdapter=new ArrayAdapter<String>(requireContext(), com.dkglabs.apply_loan.R.layout.gender_item_list,genders);
        binding.genderMenu.setAdapter(genDropDownAdapter);
        binding.genderMenu.setOnItemClickListener(this);
        binding.imgAddOneProf.setOnClickListener(this);
        binding.imgAddOne.setOnClickListener(this);
        binding.imgSelectOne.setOnClickListener(this);
        binding.imgAddTwo.setOnClickListener(this);
        binding.imgSelectTwo.setOnClickListener(this);
        binding.imgAddThree.setOnClickListener(this);
        binding.imgSelectThree.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            switch (requestCode) {
                case PROFILE_CODE:
                    profilePic = resultUri;
                    setImage(binding.imgSelectOneProf, profilePic);
                    binding.imgAddOneProf.setImageResource(R.drawable.baseline_cancel_24);
                    isProfileSelected = true;
                    break;
                case DOC1:
                    uriList.set(0,resultUri);
                    setImage(binding.imgSelectOne,resultUri);
                    setImage(binding.imgAddOne,R.drawable.baseline_cancel_24);
                    isDoc1=true;
                    break;
                case DOC2:
                    uriList.set(1,resultUri);
                    setImage(binding.imgSelectTwo,resultUri);
                    setImage(binding.imgAddTwo,R.drawable.baseline_cancel_24);
                    isDoc2=true;
                    break;
                case DOC3:
                    uriList.set(2,resultUri);
                    setImage(binding.imgSelectThree,resultUri);
                    setImage(binding.imgAddThree,R.drawable.baseline_cancel_24);
                    isDoc3=true;
                    break;

            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            LogsManager.printLog(cropError.getMessage());
        }
    }
    @Override
    public void onClick(View v) {
        int id=(int)v.getId();
        if(id==binding.imgAddOneProf.getId()){
            SET_CODE=PROFILE_CODE;
            if(!isProfileSelected) {
                selectImageFrag(SET_CODE);
            }else{
                clearImage(binding.imgAddOneProf);
                setImage(binding.imgAddOneProf,R.drawable.baseline_add_24);

                clearImage(binding.imgSelectOneProf);
                setImage(binding.imgSelectOneProf, com.dkglabs.apply_loan.R.drawable.profile_img);

                profilePic=null;
                isProfileSelected=false;
            }
        }
        if(id==binding.imgAddOne.getId()){
            SET_CODE=DOC1;
            ImageFilterButton docImg=binding.imgSelectOne;
            ImageFilterButton docSelect=binding.imgAddOne;
            if(!isDoc1){
                selectImageFrag(SET_CODE);
            }else{
                clearImage(docSelect);
                setImage(docSelect,R.drawable.baseline_add_24);

                clearImage(docImg);
                setImage(docImg, R.drawable.dashed_border_image);

                uriList.set(0,null);
                isDoc1=false;
            }

        }
        if(id==binding.imgAddTwo.getId()){
            SET_CODE=DOC2;
            ImageFilterButton docImg=binding.imgSelectTwo;
            ImageFilterButton docSelect=binding.imgAddTwo;
            if(!isDoc2){
                selectImageFrag(SET_CODE);
            }else{
                clearImage(docSelect);
                setImage(docSelect,R.drawable.baseline_add_24);

                clearImage(docImg);
                setImage(docImg, R.drawable.dashed_border_image);

                uriList.set(0,null);
                isDoc2=false;
            }
        }
        if(id==binding.imgAddThree.getId()){
            SET_CODE=DOC3;
            ImageFilterButton docImg=binding.imgSelectThree;
            ImageFilterButton docSelect=binding.imgAddThree;
            if(!isDoc3){
                selectImageFrag(SET_CODE);
            }else{
                clearImage(docSelect);
                setImage(docSelect,R.drawable.baseline_add_24);

                clearImage(docImg);
                setImage(docImg, R.drawable.dashed_border_image);

                uriList.set(0,null);
                isDoc3=false;
            }
        }


        if(id==binding.btnSubmit.getId()){
            if(checkAllconditions())
            {
                NAME=binding.txtRefName.getText().toString();
                AADHAAR=binding.txtAadhaarNumber.getText().toString();
                ADDRESS=binding.txtAddress.getText().toString();
                MOBILE=binding.txtMobileNumber.getText().toString();
                PIN=binding.txtPincode.getText().toString();

                progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.uploading_doccument));
                progressDialog.setCancelable(false);
                uploadReferences(NAME,AADHAAR,ADDRESS,MOBILE,GENDER,PIN);
                uploadDocuments(uriList,profilePic);

            }else{
                Toast.makeText(context, com.dkglabs.apply_loan.R.string.enterRefDetails, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GENDER=parent.getItemAtPosition(position).toString();
    }

    private boolean checkAllconditions() {
        if(binding.txtRefName.getText().toString().length()<=0) {
            return false;
        }
        if(binding.txtAadhaarNumber.getText().toString().length()<=0){
            return false;
        }
        if(binding.txtAddress.getText().toString().length()<=0){
            return false;
        }
        if(binding.txtMobileNumber.getText().toString().length()<=0){
            return false;
        }
        if(binding.txtPincode.getText().toString().length()<=0){
            return false;
        }
        if(GENDER==null || GENDER.length()<=0){
            return false;
        }
        if(!isProfileSelected){
            return false;
        }
        if(!isDoc1){
            return false;
        }
        return true;
    }

    private void selectImageFrag(int code){
        Bundle args = new Bundle();
        args.putInt("action",code);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_fieldInspectionFragment_to_chooseImageMethodFragment2, args);
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

    private void setImage(CircleImageView button, Uri uri) {
        // Set the image with URI directly
        button.setImageURI(uri);
    }
    private void setImage(CircleImageView button, int uri) {
        // Set the image with URI directly
        button.setImageResource(uri);
    }
    private void clearImage(ImageFilterButton button,int id) {
        // Clear the image on the ImageFilterButton
        button.setImageResource(android.R.color.transparent);
    }
    private void clearImage(CircleImageView button) {
        // Clear the image on the ImageFilterButton
        button.setImageResource(android.R.color.transparent);
    }
    private void clearImage(ImageFilterButton button) {
        // Clear the image on the ImageFilterButton
        button.setImageResource(android.R.color.transparent);
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
    private void uploadReferences(String name, String aadhaar, String address, String mobile, String gender,String pin) {
        SaveLoanFulfillment requestBody=new SaveLoanFulfillment();
        requestBody.setDealerId(PersistentManager.getUserResponse().getUserId());
        requestBody.setLoanId(response.getLoneId());
        if(response.docDetails.getVrfsCode().equals("A506")){
            requestBody.setRefOneName(name);
            requestBody.setRefOnePan(aadhaar);
            requestBody.setRefOneAddress(address);
            requestBody.setRefOneMobile(mobile);
            requestBody.setRefOneGender(gender);
            requestBody.setRefOnePin(pin);
        }else if(response.docDetails.getVrfsCode().equals("A507")){
            requestBody.setRefTwoName(name);
            requestBody.setRefTwoPan(aadhaar);
            requestBody.setRefTwoAddress(address);
            requestBody.setRefTwoMobile(mobile);
            requestBody.setRefTwoGender(gender);
            requestBody.setRefTwoPin(pin);
        }
        DocumentManager.saveLoanFullfilment(1001,requestBody,this);
    }
    private void uploadDocuments(List<Uri> uriList, Uri profilePic) {
        for(Uri ele : uriList ){
            if(ele!=null)
                COUNT1++;
        }
        if(profilePic!=null){
            COUNT1++;
        }
        DocDetails docDetails=new DocDetails();
        Uri crUri=null;
        docDetails.setVrfCode(response.getDocDetails().getVrfCode());
        if(profilePic!=null) {
            //uploading profile image
            if (response.docDetails.getVrfsCode().equals("A506")) {
                docDetails.setVrfsCode(FieldInspectionStaticModel.getRefOnePic()); //Dynamic Static
            } else if (response.docDetails.getVrfsCode().equals("A507")) {
                docDetails.setVrfsCode(FieldInspectionStaticModel.getRefTwoPic()); //Dynamic Static
            }
            docDetails.setVrfSName(response.getDocDetails().getVrfSName());
            docDetails.setVrfName(response.getDocDetails().getVrfName());
            docDetails.setMandatory(response.getDocDetails().getMandatory());
            docDetails.setUploadStatus(response.getDocDetails().isUploadStatus());
            docDetails.setUploading(response.getDocDetails().isUploading());
            docDetails.setUploadFailed(response.getDocDetails().isUploadFailed());
            DocumentManager.uploadDocument(3001 , context, profilePic,response.getUserId(),docDetails,  this);
        }
        for(int i=0;i<uriList.size() ;i++){
            if(response.docDetails.getVrfsCode().equals("A506")){
                docDetails.setVrfsCode(FieldInspectionStaticModel.getRefOne().get(i)); //Dynamic Static
            }else if(response.docDetails.getVrfsCode().equals("A507")){
                docDetails.setVrfsCode(FieldInspectionStaticModel.getRefTwo().get(i)); //Dynamic Static
            }
            docDetails.setVrfSName(response.getDocDetails().getVrfSName());
            docDetails.setVrfName(response.getDocDetails().getVrfName());
            docDetails.setMandatory(response.getDocDetails().getMandatory());
            docDetails.setUploadStatus(response.getDocDetails().isUploadStatus());
            docDetails.setUploading(response.getDocDetails().isUploading());
            docDetails.setUploadFailed(response.getDocDetails().isUploadFailed());

            crUri=uriList.get(i);
            if(crUri!=null)
                DocumentManager.uploadDocument(200+i , context, crUri,response.getUserId(),docDetails,  this);
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if(requestCode!=1001){
            COUNT2++;
            if(COUNT1==COUNT2) {
                UIUtils.dismissDialog(progressDialog);
                Toast.makeText(context, "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                binding.btnSubmit.setEnabled(false);
            }
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.dismissDialog(progressDialog);
        Toast.makeText(context,message , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.dismissDialog(progressDialog);
        Toast.makeText(context, "onFailure" + requestCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void commonCall(int requestCode) {
        if(requestCode!=1001){
            COUNT2++;
            if(COUNT1==COUNT2) {
                UIUtils.dismissDialog(progressDialog);
                Toast.makeText(context, "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                binding.btnSubmit.setEnabled(false);
            }
        }
    }
}