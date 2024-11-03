package com.dkglabs.loan_application.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.loan_application.databinding.FragmentPreImageUploadScreenBinding;
import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.loan_application.staticModels.PreStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.request.PostSaveLoanRequest;
import com.dkglabs.model.request.PreSendRequest;
import com.dkglabs.model.request.SaveLoanPaymentDetailsRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class PreImageUploadScreen extends BaseFragment implements View.OnClickListener, ResponseListener {
    ApplicantDocDetails response;
    FragmentPreImageUploadScreenBinding binding;
    private ActivityResultLauncher<String> mTakePhoto;
    protected ActivityResultLauncher<String> launcherPermissionStorage;
    ImageView imgSelected;
    protected int imageSelectedId=0;
    List<Boolean> imgList = Arrays.asList(false, false, false, false, false);
    List<Uri> uriList = Arrays.asList(null, null, null, null, null);
    boolean selectDone=false;
    int count1=0;
    int count2=0;
    View viewS;

    ProgressDialog progressDialog;

    public PreImageUploadScreen() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        response=PreImageUploadScreenArgs.fromBundle(getArguments()).getResponse();
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Uploading Images Please Wait...");
        progressDialog.setCancelable(false);
        mTakePhoto=registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            imgSelected.setBackgroundResource(0);
                            imgSelected.setImageURI(uri);
                            if (imageSelectedId == binding.imgOne.getId()) {
                                selectDone = true;
                                uriList.set(0,uri);
                                imgList.set(0,true);
                            } else if (imageSelectedId == binding.imgTwo.getId()) {
                                uriList.set(1,uri);
                                imgList.set(1,true);
                            } else if (imageSelectedId == binding.imgThree.getId()) {
                                uriList.set(2,uri);
                                imgList.set(2,true);
                            } else if (imageSelectedId == binding.imgFour.getId()) {
                                uriList.set(3,uri);
                                imgList.set(3,true);
                            } else if (imageSelectedId == binding.imgFive.getId()) {
                                uriList.set(4,uri);
                                imgList.set(4,true);
                            }
                        }
                    }
                });
        launcherPermissionStorage = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (Boolean.TRUE.equals(isGranted)) {
                startGalleryIntent();
            } else {
                showSettingsDialog();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentPreImageUploadScreenBinding.inflate(inflater, container, false);
        binding.txtNumberTitle.setText(response.getTITLE());
        binding.imgOne.setOnClickListener(this);
        binding.imgTwo.setOnClickListener(this);
        binding.imgThree.setOnClickListener(this);
        binding.imgFour.setOnClickListener(this);
        binding.imgFive.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        return  binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        viewS=v;
       int id=v.getId();
       if(id==binding.imgOne.getId() || id==binding.imgTwo.getId() || id==binding.imgThree.getId() ||
        id==binding.imgFour.getId() || id==binding.imgFive.getId()){
           imgSelected=v.findViewById(id);
           imageSelectedId=id;
           checkPermission();
       }
       if(id==binding.btnSave.getId()){
            if(selectDone){
                for(int i=0;i<imgList.size();i++)
                    if(imgList.get(i))
                        count1++;

                if(binding.txtNumber.getText().toString().length()!=0) {

                    uploadNumber();
                    uploadDocument(uriList);
                    progressDialog.show();
                }else{
                    Toast.makeText(context , "Enter the Number", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, "Select Image", Toast.LENGTH_SHORT).show();
            }
       }
    }
    private void checkPermission() {
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
    private void startGalleryIntent() {
        mTakePhoto.launch("image/*");
    }
    private void uploadNumber() {
        if(response.isPre_post()==1){
            String number=binding.txtNumber.getText().toString();
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
        }else{
            String number=binding.txtNumber.getText().toString();
            PostSaveLoanRequest requestBody=new PostSaveLoanRequest();
            requestBody.setDealerId(PersistentManager.getUserResponse().getUserId());
            requestBody.setLoanId(response.getLoneId());

            switch (response.getPreNumberType()){
                case 601:
                    requestBody.setRegistrationNumber(number);
                    break;
                case 606:
                    requestBody.setAdditionalDocNumber(number);
                    break;
            }
            DocumentManager.saveLoanPostDisbursemen(998,requestBody,this);
        }
    }

    private void uploadDocument(List<Uri> uri) {
        DocDetails docDetails=new DocDetails();
        Uri crUri=null;
        List<String> preVrfs=PreStaticModelList.getPreOptional().get(response.getDocDetails().getVrfsCode());

        docDetails.setVrfCode(response.getDocDetails().getVrfCode());

        for(int i=0;i<preVrfs.size();i++){
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
        if(requestCode!=998 || requestCode!=999){
            count2++;
            if(count2==count1){
                progressDialog.dismiss();
                Toast.makeText(context, "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                binding.btnSave.setEnabled(false);
            }
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        progressDialog.dismiss();
        Toast.makeText(context, "Something Went Wrong " + message , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(context, "onFailure" + requestCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void commonCall(int requestCode) {
       if(requestCode!=998 || requestCode!=999){
            count2++;
            if(count2==count1){
                progressDialog.dismiss();
                Toast.makeText(context, "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                binding.btnSave.setEnabled(false);
            }
        }
    }
}