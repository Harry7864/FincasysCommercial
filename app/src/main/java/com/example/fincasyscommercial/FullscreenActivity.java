package com.example.fincasyscommercial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.fincasyscommercial.network.CommonResponce;
import com.example.fincasyscommercial.network.RestCall;
import com.example.fincasyscommercial.network.RestClient;
import com.example.fincasyscommercial.selectsociety.FilterActivity;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Locale;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {


    WebSettings webSettings;


    public static final int INPUT_FILE_REQUEST_CODE = 1;

    private ValueCallback<Uri[]> mFilePathCallback;

    private String mCameraPhotoPath;

    WebView webView;
    RelativeLayout recy_error;

    TextView tv_refresh;
    ProgressBar progressBar;

    boolean val = false;

    PreferenceManager preferenceManager;


    AppUpdateManager appUpdateManager;

    int MY_REQUEST_CODE = 1;

    Tools tools;

    boolean isUrlRequest = false;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        webView = findViewById(R.id.webview);
        preferenceManager = new PreferenceManager(this);
        tools = new Tools(this);
        progressBar = findViewById(R.id.pbProcessing);
        recy_error = findViewById(R.id.recy_error);
        tv_refresh = findViewById(R.id.tv_refresh);

        recy_error.setVisibility(View.GONE);

        if (VariableBag.visitorNotificationNM != null) {
            VariableBag.visitorNotificationNM.cancel(VariableBag.NOTIFICATION_SOS_ID);
        }

        webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Android; X11; Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.34 Safari/534.24");
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.addJavascriptInterface(new JavaScriptInterface(getApplicationContext()), "Android");


        tv_refresh.setOnClickListener(v -> {

            webView.reload();

        });

//        webView.loadUrl(preferenceManager.getBaseUrlApAdmin() + "controller/loginControllerAndroid.php?admin_mobile=" + preferenceManager.getKeyValueString("adminMobile") + "&otp=" + preferenceManager.getKeyValueString("otp"));
        webView.loadUrl("www.google.com");


        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                if (!url.contains("blob")) {

                    String filename = null;
                    try {
                        filename = URLUtil.guessFileName(URLDecoder.decode(url, "UTF-8"), contentDisposition, mimetype);

                        String cookies = CookieManager.getInstance().getCookie(url);
                        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(url));
                        downloadRequest.addRequestHeader("cookie", cookies);
                        downloadRequest.allowScanningByMediaScanner();
                        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                        try {
                            dm.enqueue(downloadRequest);
                        } catch (SecurityException e) {
                            Toast.makeText(getApplicationContext(),"No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }else {
                    webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url, mimetype));
                }


            }
        });
        webSettings.setSupportZoom(true);
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);

        webView.addJavascriptInterface(new JavaScriptInterface(FullscreenActivity.this), "Android");
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccessFromFileURLs(true);

        Log.e("@@@", "onCreate: " + preferenceManager.getBaseUrlApAdmin() + "controller/loginControllerAndroid.php?admin_mobile=" + preferenceManager.getKeyValueString("adminMobile") + "&otp=" + preferenceManager.getKeyValueString("otp"));
        webSettings.setAllowFileAccess(true);

        isUrlRequest = VariableBag.URL_CLICK != null && VariableBag.URL_CLICK.trim().length() > 5;

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                progressBar.setVisibility(View.VISIBLE);
                val = false;

                if (url.equalsIgnoreCase(preferenceManager.getBaseUrlApAdmin() + "index.php")) {

                    logout();

                } else if (url.equalsIgnoreCase(preferenceManager.getBaseUrlApAdmin() + "index.php?LoginFirst")) {

                    webView.loadUrl(preferenceManager.getBaseUrlApAdmin() + "controller/loginControllerAndroid.php?admin_mobile=" + preferenceManager.getKeyValueString("adminMobile") + "&otp=" + preferenceManager.getKeyValueString("otp"));

                } else if (url.contains("qr.php")) {

//                    startActivity(new Intent(FullscreenActivity.this, ScanCodeActivity.class));

                }

                super.onPageStarted(view, url, favicon);

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageFinished(WebView view, String url) {

                if (isUrlRequest) {
                    isUrlRequest = false;
                    webView.loadUrl(preferenceManager.getBaseUrlApAdmin() + VariableBag.URL_CLICK);

                } else {
                    if (val) {
                        progressBar.setVisibility(View.GONE);
                        webView.setVisibility(View.GONE);
                        recy_error.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        recy_error.setVisibility(View.GONE);
                    }

                }
                super.onPageFinished(view, url);

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                super.onReceivedError(view, request, error);

                val = true;


                Log.e("**** Error 1 ", error.getErrorCode() + "");
                Log.e("**** Error 1 ", error.getDescription() + "");
            }

            @SuppressLint("LongLogTag")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("**** shouldOverrideUrlLoading ", url);
                if (url.contains(".pdf") || url.contains(".doc")
                        || url.contains(".docx") || url.contains(".ppt") ||
                        url.contains(".pptx") || url.contains(".csv") ||
                        url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
                    openFile(url);
                }
                return false;
            }

        });

        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setWebChromeClient(new WebChromeClient() {

            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e("TAG", "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");

                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

                return true;
            }
        });


        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void logout() {

        tools.showLoading();

        RestCall call = RestClient.createService(RestCall.class, preferenceManager.getBaseUrl(), preferenceManager.getApiKey());

        call.logout("user_logout", preferenceManager.getRegisteredUserId(), preferenceManager.getSocietyId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonResponce>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {


                        tools.stopLoading();

                        Tools.toast(FullscreenActivity.this, e.getLocalizedMessage(), 1);
                        preferenceManager.clearPreferences();
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(FullscreenActivity.this, FilterActivity.class));
                        finish();
                    }

                    @Override
                    public void onNext(CommonResponce commonResponce) {

                        tools.stopLoading();
                        Tools.toast(FullscreenActivity.this, commonResponce.getMessage(), 0);

                        preferenceManager.clearPreferences();
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(FullscreenActivity.this, FilterActivity.class));
                        finish();
                    }
                });


    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            final AlertDialog.Builder bui = new AlertDialog.Builder(this);
            bui.setTitle("Are you sure to exit?");
            bui.setMessage("Thanks for using this app.");
            bui.setCancelable(false);
            bui.setPositiveButton(getResources().getString(R.string.Exit), (dialog, which) -> finishAffinity());
            bui.setNegativeButton(getResources().getString(R.string.Cancel), (dialog, which) -> dialog.dismiss());
            AlertDialog alert11 = bui.create();
            alert11.show();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint({"NewApi", "LocalSuppress"}) String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        if (!preferenceManager.getLoginSession()) {
            preferenceManager.clearPreferences();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(FullscreenActivity.this, FilterActivity.class));
            finish();
        }

    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri[] results = null;

        // Check that the response is a good one
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                // If there is not data, then we may have taken a photo
                if (mCameraPhotoPath != null) {
                    results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                }
            } else {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }

        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;
    }

    private void openFile(String url) {

        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (url.contains(".doc") || url.contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.contains(".ppt") || url.contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.contains(".xls") || url.contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip");
            } else if (url.contains(".rar")) {
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed");
            } else if (url.contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.contains(".wav") || url.contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.contains(".csv")) {
                // CSV file
                intent.setDataAndType(uri, "text/csv");
            } else if (url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png") ||
                    url.contains(".JPG") || url.contains(".JPEG") || url.contains(".PNG")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.contains(".3gp") || url.contains(".mpg") ||
                    url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //Toast.makeText(this, "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getBase64StringFromBlobUrl(String blobUrl) {
        if (blobUrl.startsWith("blob")) {
            return "javascript: var xhr = new XMLHttpRequest();" +
                    "xhr.open('GET', '" + blobUrl + "', true);" +
                    "xhr.setRequestHeader('Content-type','application/pdf');" +
                    "xhr.responseType = 'blob';" +
                    "xhr.onload = function(e) {" +
                    "    if (this.status == 200) {" +
                    "        var blobPdf = this.response;" +
                    "        var reader = new FileReader();" +
                    "        reader.readAsDataURL(blobPdf);" +
                    "        reader.onloadend = function() {" +
                    "            base64data = reader.result;" +
                    "            Android.getBase64FromBlobData(base64data);" +
                    "        }" +
                    "    }" +
                    "};" +
                    "xhr.send();";
        }
        return "javascript: console.log('It is not a Blob URL');";
    }


}
