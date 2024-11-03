package com.dkglabs.apply_loan.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;

import com.dkglabs.apply_loan.model.KYCVideoRequestModel;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.KycResultRequestModel;
import com.dkglabs.model.response.KycResultModel;
import com.dkglabs.remote.interfaces.KycResponseListener;
import com.dkglabs.remote.manager.KycCallManager;
import com.dkglabs.remote.utils.KycUtilManager;

import java.util.List;
import java.util.Objects;


public class WebVideoCalling extends BaseFragment implements View.OnClickListener , KycResponseListener {
    private static final int CAMERA_MICROPHONE_PERMISSION_REQUEST_CODE = 101;
    private static final Integer VIDEO_KYC_RESULT=1004;
    private WebView webView;
    KYCVideoRequestModel requestModel;
    View view;
    private Dialog dialog;
    private static final int DELAY =5000;
    public WebVideoCalling() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)   {
        view = inflater.inflate(R.layout.fragment_web_video_calling, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        if (getArguments() != null) {
            requestModel=WebVideoCallingArgs.fromBundle(getArguments()).getKycReqModel();
        }
        Log.d("URL : ",requestModel.getUrl());
        webView = view.findViewById(R.id.webVideCall);
        requestCameraAndMicrophonePermissions();
        initializeWebView();
        testingFunc();
        return view;
    }

    private void testingFunc() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        WebSettings mySetings=webView.getSettings();
        setMyWebviewSettings(mySetings);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                if(url.equals(KycUtilManager.IMG_TICK) || url.equals(KycUtilManager.VERIFIED)){
//
//                }
                Log.d("WebView", "your current url when webpage loading..  " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                if(url.equals(KycUtilManager.IMG_TICK) || url.equals(KycUtilManager.VERIFIED)){
//
//                }
                Log.d("WebView", "your current url when webpage loading.. finish  " + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                // TODO Auto-generated method stub
//                if(url.equals(KycUtilManager.IMG_TICK) || url.equals(KycUtilManager.VERIFIED)){
//
//                }
                Log.d("WebView","url :- " + url);
                super.onLoadResource(view, url);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("WebView","new url:- " + url);
                if(url.equals(KycUtilManager.IMG_TICK) || url.equals(KycUtilManager.VERIFIED)){
                    dialog=UIUtils.showProgressDialog(getActivity(),"Please Wait...");
                    dialog.setCancelable(false);
                    checkForResult(requestModel.getAccessToken(), requestModel.getPartnerId(),requestModel.getToken());
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(android.webkit.PermissionRequest request) {
                request.grant(request.getResources());
            }

        });

        webView.loadUrl(requestModel.getUrl());
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
                Navigation.findNavController(view).navigate(R.id.action_global_loanBackPressFragment, bundle);
            }
        };
    }

    private void initializeWebView() {
//         Enable cookies
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
//        WebSettings mySetings=webView.getSettings();
//        setMyWebviewSettings(mySetings);
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onPermissionRequest(android.webkit.PermissionRequest request) {
//                request.grant(request.getResources());
//            }
//
//        });
//
//        webView.loadUrl();
    }
    private void setMyWebviewSettings(WebSettings MyWebviewSettings) {
        MyWebviewSettings.setAllowFileAccessFromFileURLs(true);
        MyWebviewSettings.setAllowUniversalAccessFromFileURLs(true);
        MyWebviewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        MyWebviewSettings.setJavaScriptEnabled(true);
        MyWebviewSettings.setDomStorageEnabled(true);
        MyWebviewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        MyWebviewSettings.setBuiltInZoomControls(true);
        MyWebviewSettings.setAllowFileAccess(true);
        MyWebviewSettings.setSupportZoom(true);
    }

    private void requestCameraAndMicrophonePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                        CAMERA_MICROPHONE_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_MICROPHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initializeWebView();
            } else {
                Toast.makeText(requireContext(), "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
    }
    private void checkForResult(String accessToken, String partnerId, String token) {
        KycResultRequestModel.Essentials essentials=new KycResultRequestModel.Essentials();
        essentials.setToken(token);
        essentials.setPatronId(partnerId);
        KycResultRequestModel resultRequestModel=new KycResultRequestModel();
        resultRequestModel.setTask("getData");
        resultRequestModel.setEssentials(essentials);
        KycCallManager.getKycVideoVerificationResult(this,VIDEO_KYC_RESULT,partnerId,accessToken,resultRequestModel);
    }

    @Override
    public void onSuccess(List data, Integer requestCode) {

    }

    @Override
    public void onError(String errorMessage, Integer requestCode) {
        if(Objects.equals(errorMessage, "Code: 404")) {
//            Toast.makeText(requireContext(), "It may take some time to upload Please be patient...", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkForResult(requestModel.getAccessToken(), requestModel.getPartnerId(),requestModel.getToken());
                }
            }, DELAY);
            return;
        }
        if(Objects.equals(errorMessage,"Code: 403")){
            Toast.makeText(requireContext(), "We have reached the daily limit of video KYC requests", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(Object data, Integer requestCode) {
        if(Objects.equals(requestCode,VIDEO_KYC_RESULT)){
            KycResultModel result=(com.dkglabs.model.response.KycResultModel) data;
            UIUtils.dismissDialog(dialog);
            WebVideoCallingDirections.ActionFragmentWebVideoCallingToKycResultFragment directions=
                    WebVideoCallingDirections.actionFragmentWebVideoCallingToKycResultFragment(result);
            Navigation.findNavController(view).navigate(directions);
        }
    }
}