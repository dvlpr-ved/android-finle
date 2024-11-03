package com.dkglabs.apply_loan.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentBasicKycPanBinding;
import com.dkglabs.apply_loan.model.NbfcAndDealerData;
import com.dkglabs.apply_loan.utils.ImageUtils;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.DocumentRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PanResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class BasicKycPanFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private final int ACTION_PAN = 101;
    private Animation animation;
    private ContentValues values;
    private Uri imageUri;
    protected ActivityResultLauncher<Intent> launcherPan;
    protected ActivityResultLauncher<Intent> launcherPanCam;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    protected ActivityResultLauncher<String[]> launcherPermissionCamera;
    private FragmentBasicKycPanBinding binding = null;
    protected NbfcAndDealerData nbfcAndDealerData;

    public BasicKycPanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(1);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loanBackPressFragment, bundle);
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

        launcherPan = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri panUri = result.getData().getData();
                    startCropImageActivity(panUri);
                }
            }
        });
        launcherPanCam = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        startCropImageActivity(imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nbfcAndDealerData=BasicKycPanFragmentArgs.fromBundle(getArguments()).getNbfcPartnerId();
        binding = FragmentBasicKycPanBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.selectPan.setOnClickListener(this);
        animation = AnimationUtils.loadAnimation(context, R.anim.scanner);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.animationBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.selectPan) {
            Bundle args = new Bundle();
            args.putInt("action", ACTION_PAN);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_basicKycPanFragment_to_chooseImageMethodFragment, args);
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


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        PanResponse panResponse = (PanResponse) response.getResponseData();
        LoanPersistentManager.setLoanId(panResponse.getLoanId());
        BasicKycPanFragmentDirections.ActionBasicKycPanFragmentToPanDetailsFragment directions = BasicKycPanFragmentDirections.actionBasicKycPanFragmentToPanDetailsFragment(panResponse);
        Navigation.findNavController(binding.getRoot()).navigate(directions);
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showView(binding.selectPan);
        UIUtils.hideViewGone(binding.previewLayout);
        UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showView(binding.selectPan);
        UIUtils.hideViewGone(binding.previewLayout);
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.hideViewGone(binding.verifyProgress);
        binding.animationBar.clearAnimation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            validatePan(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            LogsManager.printLog(cropError.getLocalizedMessage());
        }
    }

    private void validatePan(Uri resultUri) {
        Glide.with(getContext()).load(resultUri).into(binding.panPreview);
        UIUtils.showView(binding.verifyProgress);
        UIUtils.showView(binding.previewLayout);
        binding.animationBar.startAnimation(animation);

        String resultPanUri = String.valueOf(resultUri);
        DocumentRequest request = new DocumentRequest();
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setMobileNo(PersistentManager.getUserResponse().getMobileNumber());
        request.setDocType("PAN_FRONT");

        request.setImageBase64(AppUtils.getBase64(resultPanUri));
        request.setNbfcId(nbfcAndDealerData.nbfcId);
        request.setPartnerId(nbfcAndDealerData.partnerId);
        DocumentManager.validatePanAndAddDealerNbfcMaping(1001, request,  this);
    }

    private void startGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpg");
        launcherPan.launch(Intent.createChooser(intent, getString(R.string.select_file_from)));
    }

    private void startCameraIntent() {
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "PAN Image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        launcherPanCam.launch(intent);
    }

    private void startCropImageActivity(Uri uri) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(100);

        String resultPan = UUID.randomUUID().toString() + ".jpg";
        Uri resultUri = Uri.fromFile(new File(getActivity().getCacheDir(), resultPan));
        UCrop.of(uri, resultUri)
                .withOptions(options)
                .withAspectRatio(3, 2)
                .start(context, this);
    }


}