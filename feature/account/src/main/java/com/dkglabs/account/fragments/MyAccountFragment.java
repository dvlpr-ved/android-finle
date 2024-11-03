package com.dkglabs.account.fragments;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentMyAccountBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.UserManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class MyAccountFragment extends BaseFragment implements View.OnClickListener, MenuProvider, ResponseListener {
    protected ActivityResultLauncher<Intent> launcherGallery;
    protected ActivityResultLauncher<Intent> launcherCamera;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    protected ActivityResultLauncher<String[]> launcherPermissionCamera;
    private ContentValues values;
    private Uri imageUri;
    private FragmentMyAccountBinding binding = null;

    public MyAccountFragment() {
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

        launcherGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    startCropImageActivity(uri);
                }
            }
        });

        launcherCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().addMenuProvider(this, getViewLifecycleOwner());

        binding.textViewName.setText(String.format(getString(R.string.user_name_placeholder), PersistentManager.getUserResponse().getFirstName(), PersistentManager.getUserResponse().getLastName()));
        binding.textViewPhone.setText(String.format(getString(R.string.user_account_phone_placeholder), PersistentManager.getUserResponse().getMobileNumber()));
        binding.userId.setText(PersistentManager.getUserResponse().getUserId());
        binding.email.setText(PersistentManager.getUserResponse().getEmail());
        binding.whatsAppNo.setText(PersistentManager.getUserResponse().getWhatsappNumber());

        loadProfileImage();

        AppViewModel appViewModel = new AppViewModel();
        viewModel.setSelectedItem(appViewModel);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), o -> {
            AppViewModel model = (AppViewModel) o;
            switch (model.getChangeProfile()) {
                case "gallery":
                    validateStoragePermission();
                    break;
                case "camera":
                    validateCameraPermission();
                    break;
                case "remove":
                    removeImage();
                    break;
            }
        });
    }

    private void initializeView() {
        setUpBackToolbar(getString(R.string.account_image));
        binding.accountImage.setOnClickListener(this);
        binding.buttonEdit.setOnClickListener(this);
        binding.cardUpdatePassword.setOnClickListener(this);
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

    private void loadProfileImage() {
        GlideUrl imageUrl = UserManager.downloadImage(PersistentManager.getUserResponse().getUserId(), PersistentManager.getAuthToken());
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.signatureOf(new ObjectKey(PersistentManager.getImageSignature())))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .placeholder(com.dkglabs.base.R.drawable.placeholder_user)
                .into(binding.accountImage);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.accountImage) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myAccountFragment_to_accountImageFragment);
        } else if (id == R.id.buttonEdit) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myAccountFragment_to_editAccountImageDialogFragment);
        } else if (id == R.id.cardUpdatePassword) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myAccountFragment_to_updatePasswordFragment);
        }
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_update_image) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myAccountFragment_to_editAccountFragment);
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            uploadImage(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            LogsManager.printLog(cropError.getLocalizedMessage());
        }
    }

    private void uploadImage(Uri resultUri) {
        Glide.with(getContext()).load(resultUri).into(binding.accountImage);
        UIUtils.showView(binding.uploadingProgress);
        UserManager.uploadProfileImage(1001, resultUri.toString(), PersistentManager.getUserResponse().getUserId(),  this);
    }

    private void startGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpg");
        launcherGallery.launch(Intent.createChooser(intent, getString(R.string.select_image_from)));
    }

    private void startCameraIntent() {
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "PROFILE_IMAGE");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your camera");
        imageUri = context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        launcherCamera.launch(intent);
    }

    private void removeImage() {
        UserManager.removeProfileImage(1002, PersistentManager.getUserResponse().getUserId(),  this);
    }

    private void startCropImageActivity(Uri uri) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(60);

        String image = UUID.randomUUID().toString() + ".jpg";
        Uri resultUri = Uri.fromFile(new File(getActivity().getCacheDir(), image));
        UCrop.of(uri, resultUri).withOptions(options).withAspectRatio(1, 1).start(context, this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {

        switch (requestCode) {
            case 1001:
                String msg = (String) response.getResponseData();
                if (msg.equals("Profile Image saved.")) {
                    PersistentManager.setImageSignature(AppUtils.getCurrentTimeMillis());
                    loadProfileImage();
                }
                break;
            case 1002:
                Boolean isRemoved = (Boolean) response.getResponseData();
                if (isRemoved) {
                    UIUtils.showToast(context, getString(R.string.image_removed));
                    PersistentManager.setImageSignature(AppUtils.getCurrentTimeMillis());
                    loadProfileImage();
                }
                break;
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
        loadProfileImage();
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
        loadProfileImage();
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.hideViewGone(binding.uploadingProgress);
    }
}
