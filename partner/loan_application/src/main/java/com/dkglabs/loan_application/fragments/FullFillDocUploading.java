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
import android.widget.Toast;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.model.ItemViewModel;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.databinding.FragmentFullFillDocUploadingBinding;
import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class FullFillDocUploading extends BaseFragment implements View.OnClickListener, ResponseListener {
    FragmentFullFillDocUploadingBinding binding;

    protected ActivityResultLauncher<Intent> launcherDl;
    protected ActivityResultLauncher<Intent> launcherDlCam;
    protected ItemViewModel viewModel;
    private ContentValues values;
    private Uri imageUri;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    protected ActivityResultLauncher<String[]> launcherPermissionCamera;
    int SET_CODE=000;
    ApplicantDocDetails response;

    boolean imgSelect=false;


    public FullFillDocUploading() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response=FullFillDocUploadingArgs.fromBundle(getArguments()).getResponse();


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
        // Inflate the layout for this fragment
        binding=FragmentFullFillDocUploadingBinding.inflate(inflater, container, false);
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
        binding.txtNumberTitle.setText(response.getTITLE());
        binding.imgAddOne.setOnClickListener(this);
        binding.imgSelectOne.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.imgAddOne.getId()) {
            if(!imgSelect) {
                Bundle args = new Bundle();
                args.putInt("action", SET_CODE);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_fullFillDocUploading_to_chooseImageMethodFragment2, args);
            }else{
                clearImage(binding.imgAddOne);
                setImage(binding.imgAddOne,R.drawable.baseline_add_24);

                clearImage(binding.imgSelectOne);
                setImage(binding.imgSelectOne,R.drawable.dashed_border_image);

                imageUri=null;
                imgSelect=false;
            }
        }
        if(id==binding.btnSave.getId()){
            if(imageUri==null){
                Toast.makeText(context, "Select the Document", Toast.LENGTH_SHORT).show();
                return;
            }
            if(binding.txtNumber.getText().toString().length()!=0) {
                uploadDocument(imageUri);
                progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.uploading_doccument));
                progressDialog.setCancelable(false);
            }else{
                Toast.makeText(context , "Enter the Number", Toast.LENGTH_SHORT).show();
                return;
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
    }
    private void clearImage(ImageFilterButton button) {
        // Clear the image on the ImageFilterButton
        button.setImageResource(android.R.color.transparent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            if (requestCode == SET_CODE) {
                imgSelect=true;

                clearImage(binding.imgSelectOne);
                setImage(binding.imgSelectOne,resultUri);

                clearImage(binding.imgAddOne);
                setImage(binding.imgAddOne,R.drawable.baseline_cancel_24);

                imageUri=resultUri;
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            LogsManager.printLog(cropError.getMessage());
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
    private void uploadDocument(Uri uri) {
        DocumentManager.uploadDocument(response.getPosition(), context, uri ,response.getUserId() ,response.getDocDetails(),  this);
    }



    @Override
    public void onResponse(int requestCode, BaseResponse res) {
        if(requestCode==response.getPosition()){
            Toast.makeText(context, "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
        }
        UIUtils.dismissDialog(progressDialog);
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        Toast.makeText(context, "Validation Failure", Toast.LENGTH_SHORT).show();
        UIUtils.dismissDialog(progressDialog);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        UIUtils.dismissDialog(progressDialog);
    }

    @Override
    public void commonCall(int requestCode) {
    }
}