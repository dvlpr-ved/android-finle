package com.dkglabs.apply_loan.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanVideoKycBinding;
import com.dkglabs.apply_loan.model.KYCVideoRequestModel;
import com.dkglabs.base.manager.LogsManager;

import com.dkglabs.base.model.ItemViewModel;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.base.utils.UIUtils;


import com.dkglabs.model.request.KycResultRequestModel;
import com.dkglabs.model.request.KycVideoRequestModel;
import com.dkglabs.model.response.ImageUrlModel;
import com.dkglabs.model.response.KycAuthModel;
import com.dkglabs.model.response.KycVideoModel;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.remote.interfaces.KycResponseListener;

import com.dkglabs.remote.manager.KycCallManager;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LoanVideoKYCFragment extends BaseFragment implements View.OnClickListener, KycResponseListener {

    private FragmentLoanVideoKycBinding binding = null;
    protected ActivityResultLauncher<Intent> launcherDl;
    protected ActivityResultLauncher<Intent> launcherDlCam;

    private ContentValues values;
    private Uri imageUri;
    protected ItemViewModel viewModel2;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    protected ActivityResultLauncher<String[]> launcherPermissionCamera;
    private static final int SET_CODE = 000;
    private static final Integer AUTH_CODE = 1001;
    private static final Integer IMAGE_URL_CODE = 1002;

    private static final Integer VIDEO_KYC_CODE = 1003;
    private static final Integer VIDEO_KYC_RESULT = 1004;

    boolean imgSelect = false;
    private boolean isStartKyc = false;
    private boolean isKycAuth = false;
    private boolean iskycImage = false;
    private boolean isReadyToCall = false;
    ProgressDialog progressDialog;
    private String token;
    private String accessToken;
    private String partnerId;
    private String directURL;

    private String videoUrl;

    public LoanVideoKYCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    startCropImageActivity(dlUri, SET_CODE);
                }
            }
        });
        launcherDlCam = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        startCropImageActivity(imageUri, SET_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoanVideoKycBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(4);
        viewModel.setSelectedItem(applyLoanModel);

        viewModel2 = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        viewModel2.getSelectedItem().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                ApplyLoanModel viewModel2 = (ApplyLoanModel) o;
                switch (viewModel2.getFileMethod()) {
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
            if (PermissionUtils.hasPermissions(getActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA}))
                startCameraIntent();
            else
                launcherPermissionCamera.launch(new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA});
        } else {
            if (PermissionUtils.hasPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}))
                startCameraIntent();
            else
                launcherPermissionCamera.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        }
    }

    private void initializeView() {
        binding.buttonDone.setOnClickListener(this);
        binding.imgAddOne.setOnClickListener(this);
        binding.imgSelectOne.setOnClickListener(this);
        binding.buttonStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonDone) {
//            checkForResult(accessToken,partnerId,token);
            SkipVideoVerification();
            return;
        }
        if (id == R.id.buttonDone && (accessToken == null || partnerId == null || token == null)) {
            Toast.makeText(context, "Please Complete the Verification Process...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (id == binding.imgAddOne.getId()) {
            if (!imgSelect) {
                Bundle args = new Bundle();
                args.putInt("action", SET_CODE);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loanVideoKYCFragment_to_chooseImageMethodFragment, args);
            } else {
                clearImage(binding.imgAddOne);
                setImage(binding.imgAddOne, R.drawable.baseline_add_24);

                clearImage(binding.imgSelectOne);
                setImage(binding.imgSelectOne, R.drawable.dashed_border_image);
                binding.buttonStart.setText(R.string.verify_details);
                imageUri = null;

                imgSelect = false;

                isStartKyc = false;
                iskycImage = false;
                isKycAuth = false;

                isReadyToCall = false;
                directURL = null;
            }
        }
        if (id == binding.buttonStart.getId() && !imgSelect) {
            Toast.makeText(context, "Select The Image", Toast.LENGTH_SHORT).show();
            return;
        }
        if (id == binding.buttonStart.getId() && isReadyToCall && videoUrl != null) {
            startVerification(videoUrl, token, accessToken, partnerId);
            return;
        }
        if (id == binding.buttonStart.getId() && isStartKyc) {
            KycVideoRequestModel.Essentials essentials = new KycVideoRequestModel.Essentials();
            List<String> matchImage = new ArrayList<>();
            matchImage.add(directURL);
            essentials.setMatchImage(matchImage);
            essentials.setCustomVideoRecordTime("10"); //default time for 10 seconds

            KycVideoRequestModel kycVideoRequestModel = new KycVideoRequestModel();
            kycVideoRequestModel.setTask("url");

            kycVideoRequestModel.setEssentials(essentials);

            progressDialog = UIUtils.showSimpleProgressDialog(getActivity(), "Loading Please Wait...");
            KycCallManager.getKycVideoVerificationUrl(this, VIDEO_KYC_CODE, partnerId, accessToken, kycVideoRequestModel);

            return;
        }
        if (id == binding.buttonStart.getId()) {
            uploadDocument();
        }
    }

    private void SkipVideoVerification() {
        Navigation.findNavController(binding.getRoot()).navigate(LoanVideoKYCFragmentDirections.actionLoanVideoKYCFragmentToESignDocFragment());
    }

    private void checkForResult(String accessToken, String partnerId, String token) {
        KycResultRequestModel.Essentials essentials = new KycResultRequestModel.Essentials();
        essentials.setToken(token);
        essentials.setPatronId(partnerId);
        KycResultRequestModel resultRequestModel = new KycResultRequestModel();
        resultRequestModel.setTask("getData");
        resultRequestModel.setEssentials(essentials);
        KycCallManager.getKycVideoVerificationResult(this, VIDEO_KYC_RESULT, partnerId, accessToken, resultRequestModel);
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

    private void clearImage(ImageFilterButton button, int id) {
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
                imgSelect = true;

                clearImage(binding.imgSelectOne);
                setImage(binding.imgSelectOne, resultUri);
                clearImage(binding.imgAddOne);
                setImage(binding.imgAddOne, R.drawable.baseline_cancel_24);
                imageUri = resultUri;
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

    private void uploadDocument() {
        progressDialog = UIUtils.showSimpleProgressDialog(getActivity(), "Please Wait...");
        KycCallManager.getAuthToken(this, AUTH_CODE);
    }

    private void startVerification(String videoUrl, String token, String accessToken, String partnerId) {
//        Uri uri = Uri.parse(videoUrl);
//
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//        PackageManager packageManager = context.getPackageManager();
//        if (intent.resolveActivity(packageManager) != null) {
//            context.startActivity(intent);
//        } else {
//            Toast.makeText(context, "No app available to open the link", Toast.LENGTH_SHORT).show();
//        }

        if (videoUrl != null) {
            KYCVideoRequestModel kycVideoRequestModel = new KYCVideoRequestModel();
            kycVideoRequestModel.setUrl(videoUrl);
            kycVideoRequestModel.setToken(token);
            kycVideoRequestModel.setAccessToken(accessToken);
            kycVideoRequestModel.setPartnerId(partnerId);
            com.dkglabs.apply_loan.fragments.LoanVideoKYCFragmentDirections.ActionLoanVideoKYCFragmentToFragmentWebVideoCalling directions =
                    LoanVideoKYCFragmentDirections.actionLoanVideoKYCFragmentToFragmentWebVideoCalling(kycVideoRequestModel);
            Navigation.findNavController(binding.getRoot()).navigate(directions);
        } else {
            Toast.makeText(requireContext(), com.dkglabs.base.R.string.generic_error_msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(List data, Integer requestCode) {
        UIUtils.dismissProgressDialog(progressDialog);
    }

    @Override
    public void onError(String errorMessage, Integer requestCode) {
        UIUtils.dismissProgressDialog(progressDialog);
        if (Objects.equals(errorMessage, "Code: 404")) {
            Toast.makeText(context, "Verification is not complete or it is taking some time to upload Please wait", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Objects.equals(errorMessage, "Code: 403")) {
            Toast.makeText(context, "We have reached the daily limit of video KYC requests", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(Object data, Integer requestCode) {
        if (Objects.equals(requestCode, VIDEO_KYC_RESULT)) {
//            KycResultModel result=(KycResultModel) data;
//            LoanVideoKYCFragmentDirections.ActionLoanVideoKYCFragmentToKycResultFragment3 directions=
//                LoanVideoKYCFragmentDirections.actionLoanVideoKYCFragmentToKycResultFragment3(result);
//            Navigation.findNavController(binding.getRoot()).navigate(directions);
        } else if (Objects.equals(requestCode, VIDEO_KYC_CODE)) {
            UIUtils.dismissProgressDialog(progressDialog);
            KycVideoModel body = (KycVideoModel) data;
            token = body.getResult().getToken();
            videoUrl = body.getResult().getVideoUrl();
            isReadyToCall = true;
            binding.buttonStart.setText("Call...");
            startVerification(videoUrl, token, accessToken, partnerId);
        } else if (Objects.equals(requestCode, AUTH_CODE)) {
            KycAuthModel body = (KycAuthModel) data;
            accessToken = body.getId();
            partnerId = body.getUserId();

            isKycAuth = true;
            UIUtils.dismissProgressDialog(progressDialog);
            progressDialog = UIUtils.showSimpleProgressDialog(getActivity(), "Hold on tight almost there...");

            KycCallManager.getImageUrl(this, IMAGE_URL_CODE, context, imageUri);

        } else if (Objects.equals(requestCode, IMAGE_URL_CODE)) {
            iskycImage = true;
            ImageUrlModel body = (ImageUrlModel) data;
            directURL = body.getFileDetails().getDirectURL();
        }

        if (isKycAuth && iskycImage && !isReadyToCall) {
            UIUtils.dismissProgressDialog(progressDialog);
            binding.buttonStart.setText(R.string.start_kyc);
            isStartKyc = true;
        }
    }
}