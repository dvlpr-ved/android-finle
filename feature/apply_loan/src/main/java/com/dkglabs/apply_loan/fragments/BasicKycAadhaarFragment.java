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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
import com.dkglabs.apply_loan.databinding.FragmentBasicKycAadhaarBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.DocumentRequest;
import com.dkglabs.model.response.AadhaarResponse;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class BasicKycAadhaarFragment extends BaseFragment implements View.OnClickListener, ResponseListener {


    private final int ACTION_AF = 101;
    private final int ACTION_AB = 102;
    protected ActivityResultLauncher<Intent> launcherAB;
    protected ActivityResultLauncher<Intent> launcherABCam;
    protected ActivityResultLauncher<Intent> launcherAF;
    protected ActivityResultLauncher<Intent> launcherAFCam;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    protected ActivityResultLauncher<String[]> launcherPermissionCamera;
    private Animation animationFront;
    private Animation animationBack;
    private ContentValues valuesFront;
    private ContentValues valuesBack;
    private Uri imageUriFront;
    private Uri imageUriBack;
    private boolean aadhaarFront = false;
    private boolean aadhaarBack = false;
    private AadhaarResponse aadhaarResponse;
    private FragmentBasicKycAadhaarBinding binding = null;

    public BasicKycAadhaarFragment() {
        // Required empty public constructor
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
            if (!Boolean.TRUE.equals(isGranted)) {
                showSettingsDialog();
            }
        });

        launcherPermissionCamera = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!(Boolean.TRUE.equals(isGranted.get(Manifest.permission.READ_MEDIA_IMAGES))
                        && Boolean.TRUE.equals(isGranted.get(Manifest.permission.CAMERA)))) {
                    showSettingsDialog();
                }
            } else {
                if (!(Boolean.TRUE.equals(isGranted.get(Manifest.permission.READ_EXTERNAL_STORAGE))
                        && Boolean.TRUE.equals(isGranted.get(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        && Boolean.TRUE.equals(isGranted.get(Manifest.permission.CAMERA)))) {
                    showSettingsDialog();
                }
            }
        });

        launcherAF = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri aadhaarFrontUri = result.getData().getData();
                    startCropImageActivity(aadhaarFrontUri, ACTION_AF);
                    Glide.with(getContext()).load(aadhaarFrontUri).into(binding.aadhaarFrontPreview);
                }
            }
        });
        launcherAFCam = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        startCropImageActivity(imageUriFront, ACTION_AF);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        launcherAB = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri aadhaarFrontUri = result.getData().getData();
                    startCropImageActivity(aadhaarFrontUri, ACTION_AB);
                    Glide.with(getContext()).load(aadhaarFrontUri).into(binding.aadhaarFrontPreview);
                }
            }
        });
        launcherABCam = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                try {
                    startCropImageActivity(imageUriBack, ACTION_AB);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBasicKycAadhaarBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.setSelectedItem(new ApplyLoanModel());
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                ApplyLoanModel viewModel = (ApplyLoanModel) o;
                switch (viewModel.getFileMethod()) {
                    case "gallery":
                        validateStoragePermission(viewModel.getDocAction());
                        break;
                    case "camera":
                        validateCameraPermission(viewModel.getDocAction());
                        break;
                }
            }
        });
    }

    private void initializeView() {
        binding.selectAadhaarFront.setOnClickListener(this);
        binding.selectAadhaarBack.setOnClickListener(this);
        animationFront = AnimationUtils.loadAnimation(context, R.anim.scanner);
        animationBack = AnimationUtils.loadAnimation(context, R.anim.scanner);
        animationFront.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.animationBarFront.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animationBack.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.animationBarBack.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        aadhaarResponse = new AadhaarResponse();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.selectAadhaarFront) {
            Bundle args = new Bundle();
            args.putInt("action", ACTION_AF);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_chooseImageMethodFragment, args);
        } else if (id == R.id.selectAadhaarBack) {
            Bundle args = new Bundle();
            args.putInt("action", ACTION_AB);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_chooseImageMethodFragment, args);
        }
    }

    private void validateCameraPermission(int docAction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PermissionUtils.hasPermissions(getActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA}))
                startCameraIntent(docAction);
            else
                launcherPermissionCamera.launch(new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA});
        } else {
            if (PermissionUtils.hasPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}))
                startCameraIntent(docAction);
            else
                launcherPermissionCamera.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        }
    }

    private void validateStoragePermission(int docAction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PermissionUtils.hasPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES))
                startGalleryIntent(docAction);
            else
                launcherPermissionStorage.launch(Manifest.permission.READ_MEDIA_IMAGES);
        } else {
            if (PermissionUtils.hasPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))
                startGalleryIntent(docAction);
            else
                launcherPermissionStorage.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        AadhaarResponse aResponse = (AadhaarResponse) response.getResponseData();
        if (requestCode == 1001) {
            aadhaarResponse.setName(aResponse.getName());
            aadhaarFront = true;
        } else if (requestCode == 1002) {
            aadhaarResponse.setAddress(aResponse.getAddress());
            aadhaarResponse.setAddressString(aResponse.getAddressString());
            aadhaarResponse.setPin(aResponse.getPin());
            aadhaarBack = true;
        }

        if (aadhaarFront && aadhaarBack) {
            //LoanPersistentManager.setAadhaarResponse(aadhaarResponse);
            //UIUtils.showToast(context, "Aadhaar verified");
            //BasicKycAadhaarFragmentDirections.ActionBasicKycAadhaarFragmentToAadhaarDetailsFragment directions = BasicKycAadhaarFragmentDirections.actionBasicKycAadhaarFragmentToAadhaarDetailsFragment(aadhaarResponse);
            //Navigation.findNavController(binding.getRoot()).navigate(directions);
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        if (requestCode == 1001) {
            UIUtils.showView(binding.selectAadhaarFront);
            UIUtils.hideViewGone(binding.aadhaarFrontPreview);
        } else if (requestCode == 1002) {
            UIUtils.showView(binding.selectAadhaarBack);
            UIUtils.hideViewGone(binding.aadhaarBackPreview);
        }
        UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        if (requestCode == 1001) {
            UIUtils.showView(binding.selectAadhaarFront);
            UIUtils.hideViewGone(binding.aadhaarFrontPreview);
        } else if (requestCode == 1002) {
            UIUtils.showView(binding.selectAadhaarBack);
            UIUtils.hideViewGone(binding.aadhaarBackPreview);
        }
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        if (requestCode == 1001) {
            UIUtils.hideViewGone(binding.verifyProgressFront);
            binding.animationBarFront.clearAnimation();
        } else if (requestCode == 1002) {
            binding.animationBarBack.clearAnimation();
            UIUtils.hideViewGone(binding.verifyProgressBack);
        }
        UIUtils.dismissDialog(progressDialog);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            if (requestCode == ACTION_AF) {
                validateAadhaarFront(resultUri);
            } else if (requestCode == ACTION_AB) {
                validateAadhaarBack(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            LogsManager.printLog(cropError.getMessage());
        }
    }

    private void validateAadhaarFront(Uri uri) {
        UIUtils.hideViewGone(binding.selectAadhaarFront);
        Glide.with(getContext()).load(uri).into(binding.aadhaarFrontPreview);
        UIUtils.showView(binding.verifyProgressFront);
        UIUtils.showView(binding.previewLayoutFront);
        binding.animationBarFront.startAnimation(animationFront);

        String resultUri = String.valueOf(uri);
        DocumentRequest request = new DocumentRequest();
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setMobileNo(PersistentManager.getUserResponse().getMobileNumber());
        request.setImageBase64(AppUtils.getBase64(resultUri));
        request.setDocType("AADHAAR_FRONT");
        DocumentManager.validateAadhaar(1001, request,  this);
    }

    private void validateAadhaarBack(Uri uri) {
        UIUtils.hideViewGone(binding.selectAadhaarBack);
        Glide.with(getContext()).load(uri).into(binding.aadhaarBackPreview);
        UIUtils.showView(binding.verifyProgressBack);
        UIUtils.showView(binding.previewLayoutBack);
        binding.animationBarBack.startAnimation(animationBack);

        String resultUri = String.valueOf(uri);
        DocumentRequest request = new DocumentRequest();
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setMobileNo(PersistentManager.getUserResponse().getMobileNumber());
        request.setImageBase64(AppUtils.getBase64(resultUri));
        request.setDocType("AADHAAR_BACK");
        DocumentManager.validateAadhaar(1002, request,  this);
    }

    private void startGalleryIntent(int action) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpg");
        switch (action) {
            case ACTION_AF:
                launcherAF.launch(Intent.createChooser(intent, getString(R.string.select_file_from)));
                break;
            case ACTION_AB:
                launcherAB.launch(Intent.createChooser(intent, getString(R.string.select_file_from)));
                break;
        }
    }

    private void startCameraIntent(int action) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch (action) {
            case ACTION_AF:
                valuesFront = new ContentValues();
                valuesFront.put(MediaStore.Images.Media.TITLE, "Aadhaar Front Image");
                valuesFront.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUriFront = context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valuesFront);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFront);
                launcherAFCam.launch(intent);
                break;
            case ACTION_AB:
                valuesBack = new ContentValues();
                valuesBack.put(MediaStore.Images.Media.TITLE, "Aadhaar Back Image");
                valuesBack.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUriBack = context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valuesBack);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriBack);
                launcherABCam.launch(intent);
                break;
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
                .withAspectRatio(3, 2)
                .start(getContext(), this, code);
    }


}