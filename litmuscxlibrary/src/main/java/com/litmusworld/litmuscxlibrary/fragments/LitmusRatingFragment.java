package com.litmusworld.litmuscxlibrary.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.litmusworld.litmuscxlibrary.R;
import com.litmusworld.litmuscxlibrary.connection.ConnectionAsyncTask;
import com.litmusworld.litmuscxlibrary.databases.LitmusApplicationSharedPreferences;
import com.litmusworld.litmuscxlibrary.interfaces.LitmusOnConnectionResultListener;
import com.litmusworld.litmuscxlibrary.parser.LitmusParseUtility;
import com.litmusworld.litmuscxlibrary.utils.LitmusUtilities;

import org.json.JSONObject;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.litmusworld.litmuscxlibrary.fragments.dialog.LitmusRatingDialogFragment.fnCloseRatingDialog;

/**
 * This fragment renders the Feedback Page in Webview.
 * This fragment must be initialized using newInstance() method.
 * <p>
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public class LitmusRatingFragment extends Fragment {

    public static final String PARAM_USER_ID = "param_user_id";
    public static final String PARAM_APP_ID = "param_app_id";
    public static final String PARAM_USERNAME = "param_username";
    public static final String PARAM_REMINDER_NUMBER = "param_reminder_number";
    public static final String PARAM_ALLOW_MULTIPLE_FEEDBACKS = "param_is_allow_multiple_feedbacks";
    public static final String PARAM_CUSTOMER_EMAIL = "param_customer_email";

    // Custom base url
    public static final String PARAM_BASE_URL = "param_base_url";

    // web link open
    public static final String PARAM_WEB_URL = "param_web_url";

    // optional hashmap
    public static final String PARAM_TAG_PARAMETERS = "param_tag_parameters";

    public static final String PARAM_SHOW_IN_DIALOG = "param_show_in_dialog";
    public static final String PARAM_IS_COPY_ALLOWED = "param_is_copy_allowed";
    public static final String PARAM_IS_SHARE_ALLOWED = "param_is_share_allowed";
    public static final String PARAM_IS_CLOSE_ONLY = "param_is_close_only";
    public static final String PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE = "param_is_more_image_black";
    private static final int PICKFILE_REQUEST_CODE = 1000;

    private String[] fileReadPermission = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_READ_PERMISSION = 2000;

    private WebView m_web_view;
    private View m_progress_view;
    private View m_ll_main_reload;
    private TextView m_text_close;
    private ImageView m_more_menu;

    private String strUserId;
    private String strAppId;
    private String strUserName;
    private int nReminderNumber;
    private String strCustomerEmail;
    private boolean isAllowMultipleFeedbacks;
    private String strBaseUrl;
    private String strWebLink;
    private HashMap<String, Object> oTagParameters;

    private boolean isCloseOnly = false;
    private boolean isCopyAllowed = false;
    private boolean isShareAllowed = false;
    private boolean isMoreImageBlackElseWhite = true;

    private OnLitmusRatingFragmentListener m_listener;
    private boolean showInDialog;
    private static AlertDialog.Builder builder;
    private Context mContext;
    private ValueCallback<Uri[]> mFilePathCallback;

    public interface OnLitmusRatingFragmentListener {
        public void onRatingCloseClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (context instanceof OnLitmusRatingFragmentListener) {
            m_listener = (OnLitmusRatingFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        m_listener = null;
    }

    /**
     * Creates a new Instance for LitmusRatingFragment.
     *
     * @param strUserId       User Id
     * @param strAppId        App id or Feedback Project Id
     * @param strUserName     Name of the User
     * @param nReminderNumber Reminder number
     * @return LitmusRatingFragment instance
     */
    public static LitmusRatingFragment newInstance(String strUserId, String strAppId,
                                                   String strUserName, int nReminderNumber, String strCustomerEmail,
                                                   boolean isAllowMultipleFeedbacks, boolean showInDialog) {

        return newInstance(null, strUserId, strAppId, strUserName, nReminderNumber, strCustomerEmail,
                isAllowMultipleFeedbacks, showInDialog);
    }

    /**
     * Creates a new Instance for LitmusRatingFragment.
     *
     * @param strUserId       User Id
     * @param strAppId        App id or Feedback Project Id
     * @param strUserName     Name of the User
     * @param nReminderNumber Reminder number
     * @return LitmusRatingFragment instance
     */
    public static LitmusRatingFragment newInstance(String strBaseUrl, String strUserId, String strAppId,
                                                   String strUserName, int nReminderNumber, String strCustomerEmail,
                                                   boolean isAllowMultipleFeedbacks, boolean showInDialog) {

        return newInstance(strBaseUrl, strUserId, strAppId, strUserName, nReminderNumber,
                strCustomerEmail, isAllowMultipleFeedbacks, null, showInDialog,
                false, false, false, true);
    }

    public static LitmusRatingFragment newInstance(String strBaseUrl, String strUserId, String strAppId,
                                                   String strUserName, int nReminderNumber, String strCustomerEmail,
                                                   boolean isAllowMultipleFeedbacks,
                                                   HashMap<String, Object> oOptionalTagParameters,
                                                   boolean showInDialog, boolean isCloseOnly, boolean isCopyAllowed,
                                                   boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {

        Bundle args = new Bundle();
        args.putString(PARAM_USER_ID, strUserId);
        args.putString(PARAM_APP_ID, strAppId);
        args.putString(PARAM_USERNAME, strUserName);
        args.putInt(PARAM_REMINDER_NUMBER, nReminderNumber);
        args.putString(PARAM_CUSTOMER_EMAIL, strCustomerEmail);
        args.putBoolean(PARAM_ALLOW_MULTIPLE_FEEDBACKS, isAllowMultipleFeedbacks);
        args.putString(PARAM_BASE_URL, strBaseUrl);
        args.putSerializable(PARAM_TAG_PARAMETERS, oOptionalTagParameters);
        args.putBoolean(PARAM_SHOW_IN_DIALOG, showInDialog);
        args.putBoolean(PARAM_IS_CLOSE_ONLY, isCloseOnly);
        args.putBoolean(PARAM_IS_COPY_ALLOWED, isCopyAllowed);
        args.putBoolean(PARAM_IS_SHARE_ALLOWED, isShareAllowed);
        args.putBoolean(PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE, isMoreImageBlackElseWhite);


        LitmusRatingFragment fragment = new LitmusRatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static LitmusRatingFragment newInstance(String strWebLink, boolean showInDialog, boolean isCloseOnly,
                                                   boolean isCopyAllowed, boolean isShareAllowed,
                                                   boolean isMoreImageBlackElseWhite) {

        Bundle args = new Bundle();
        args.putString(PARAM_WEB_URL, strWebLink);
        args.putBoolean(PARAM_SHOW_IN_DIALOG, showInDialog);
        args.putBoolean(PARAM_IS_CLOSE_ONLY, isCloseOnly);
        args.putBoolean(PARAM_IS_COPY_ALLOWED, isCopyAllowed);
        args.putBoolean(PARAM_IS_SHARE_ALLOWED, isShareAllowed);
        args.putBoolean(PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE, isMoreImageBlackElseWhite);

        LitmusRatingFragment fragment = new LitmusRatingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.litmus_rating_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_web_view = (WebView) view.findViewById(R.id.web_view);
        m_progress_view = view.findViewById(R.id.progress_bar);
        m_ll_main_reload = view.findViewById(R.id.ll_main_reload);
        m_text_close = (TextView) view.findViewById(R.id.text_close);
        m_more_menu = (ImageView) view.findViewById(R.id.more_menu);

        fnAskForReadExternalStoragePermission(getActivity(), REQUEST_READ_PERMISSION, fileReadPermission);


//        m_text_close.measure(0,0);
//        int nFixedHeaderHeightInPx = LitmusUtilities.dpToPx(43, getActivity());
//        int nHeightOfCloseInPx = m_text_close.getMeasuredHeight();
//        int nTopMargin = nFixedHeaderHeightInPx/2 - nHeightOfCloseInPx/2;
//
//        nTopMargin = nTopMargin < 0 ? 0 : nTopMargin;
//
//        ((RelativeLayout.LayoutParams)m_text_close.getLayoutParams()).topMargin = nTopMargin;

        m_more_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCloseOnly){
                    fnCloseRatingFragment();
                }else{
                    showPopupMenu(v);
                }
            }
        });

        m_text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fnCloseRatingFragment();

            }
        });

        // Getting error - "Denied starting an intent without a user gesture" on Samsung S7
        m_web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                m_text_close.setTextColor(Color.WHITE);

                int nLastIndex = url.lastIndexOf("#");

                if (nLastIndex != -1 && (nLastIndex) < url.length()) {

                    String strPageName = url.substring(nLastIndex);

                    if (strPageName.equalsIgnoreCase("#thank-you-screen") ||
                            strPageName.equalsIgnoreCase("#thank-you-screen-phone")) {
                        Handler oHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);

                                fnCloseRatingFragment();
                            }
                        };

                        oHandler.sendEmptyMessageDelayed(1, 2000);
                    }

                }
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        m_web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mFilePathCallback = filePathCallback;
                // Launch Intent for picking file
                //ask permission for upload
                pickUploadFile();
                return true;

            }
        });

        m_web_view.getSettings().setJavaScriptEnabled(true);

        // Getting this error - “Uncaught TypeError: Cannot read property 'getItem' of null”
        // To solve above error, enabled dom storage settings
        WebSettings settings = m_web_view.getSettings();
        settings.setDomStorageEnabled(true);

//            m_web_view.loadUrl(strDashboardUrl);

        if (getArguments() != null) {
            strUserId = getArguments().getString(PARAM_USER_ID);
            strAppId = getArguments().getString(PARAM_APP_ID);
            strUserName = getArguments().getString(PARAM_USERNAME);
            nReminderNumber = getArguments().getInt(PARAM_REMINDER_NUMBER);
            strCustomerEmail = getArguments().getString(PARAM_CUSTOMER_EMAIL);
            isAllowMultipleFeedbacks = getArguments().getBoolean(PARAM_ALLOW_MULTIPLE_FEEDBACKS);
            strBaseUrl = getArguments().getString(PARAM_BASE_URL);
            isCloseOnly = getArguments().getBoolean(PARAM_IS_CLOSE_ONLY);
            isCopyAllowed = getArguments().getBoolean(PARAM_IS_COPY_ALLOWED);
            isShareAllowed = getArguments().getBoolean(PARAM_IS_SHARE_ALLOWED);
            isMoreImageBlackElseWhite = getArguments().getBoolean(PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE);

            // optional tag parameters
            Object oObjectTagParameters = getArguments().getSerializable(PARAM_TAG_PARAMETERS);

            if (oObjectTagParameters != null && oObjectTagParameters instanceof HashMap) {
                oTagParameters = (HashMap<String, Object>) oObjectTagParameters;
            }


            // web url
            strWebLink = getArguments().getString(PARAM_WEB_URL);

            // boolean parameter to check to close dialog or activity
            showInDialog = getArguments().getBoolean(PARAM_SHOW_IN_DIALOG);

            // Request feedback detail
            fnRequestFeedBackDetail();

        }

        if (isCloseOnly) {
            if (isMoreImageBlackElseWhite) {
                m_more_menu.setImageResource(R.drawable.ic_close_black_24);
            } else {
                m_more_menu.setImageResource(R.drawable.ic_close_white_24);
            }
        } else {
            if (isMoreImageBlackElseWhite) {
                m_more_menu.setImageResource(R.drawable.ic_more_vert_black_24dp);
            } else {
                m_more_menu.setImageResource(R.drawable.ic_more_vert_white_24dp);
            }
        }

        m_ll_main_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnRequestFeedBackDetail();
            }
        });
    }

    private void pickUploadFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_REQUEST_CODE) {
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            Uri[] resultsArray = new Uri[1];
            resultsArray[0] = result;
            if (data != null) {
                mFilePathCallback.onReceiveValue(resultsArray);
            } else {
                mFilePathCallback.onReceiveValue(null);
            }

        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.child_popup, popup.getMenu());
        MenuItem mCopyLink = popup.getMenu().findItem(R.id.action_copy_link);
        MenuItem mShareLink = popup.getMenu().findItem(R.id.action_share_link);
        MenuItem mClose = popup.getMenu().findItem(R.id.action_close);

        if (isCopyAllowed) {
            mCopyLink.setVisible(true);
        }

        if (isShareAllowed) {
            mShareLink.setVisible(true);
        }
        mCopyLink.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                fnCopyLink();
                return true;
            }
        });

        mShareLink.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                fnShareLink();
                return true;
            }
        });

        mClose.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                fnCloseRatingFragment();
                return true;
            }
        });


        popup.show();
    }

    private void fnShareLink() {
        String webUrl = m_web_view.getUrl();
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
//        share.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
        share.putExtra(Intent.EXTRA_TEXT, webUrl);

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    private void fnCopyLink() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        String webUrl = m_web_view.getUrl();


        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("link", webUrl);
            Toast.makeText(getActivity(), "Copied to ClipBoard", Toast.LENGTH_SHORT).show();
            clipboard.setPrimaryClip(clip);
        }
    }

    private void fnCloseRatingFragment() {
        if (m_listener != null) {
            m_listener.onRatingCloseClicked();
        }

        if (showInDialog) {
            fnCloseRatingDialog(getActivity().getSupportFragmentManager());
        } else {
            getActivity().finish();
        }
    }

    /**
     * Check in the Shared Preferences if long url for given user, app id and reminder number is already present.
     * If already present then load webview directly, if not then call Api to get long url and then load it on webview
     */
    private void fnRequestFeedBackDetail() {

        if (strWebLink != null && strWebLink.length() > 0) {

            m_ll_main_reload.setVisibility(View.GONE);
            m_progress_view.setVisibility(View.GONE);

            m_web_view.setVisibility(View.VISIBLE);
            m_web_view.loadUrl(strWebLink);

            return;
        }

        String strSavedUserId = LitmusApplicationSharedPreferences.getInstance(getActivity()).fnGetNotificationUserId();
        String strSavedAppId = LitmusApplicationSharedPreferences.getInstance(getActivity()).fnGetNotificationAppId();
        int nSavedReminderNumber = LitmusApplicationSharedPreferences.getInstance(getActivity()).fnGetNotificationReminderNumber();
        String strSavedFeedbackLogoUrl = LitmusApplicationSharedPreferences.getInstance(getActivity()).fnGetNotificationFeedbackLongUrl();

        if (strSavedUserId.length() > 0 && strSavedAppId.length() > 0 && nReminderNumber != -1) {

            if (strAppId.equals(strSavedAppId) && strUserId.equals(strSavedUserId)) {

                if (nReminderNumber == nSavedReminderNumber) {
                    // load saved url

                    if (strSavedFeedbackLogoUrl.length() > 0) {

                        m_ll_main_reload.setVisibility(View.GONE);
                        m_progress_view.setVisibility(View.GONE);

                        m_web_view.setVisibility(View.VISIBLE);
                        m_web_view.loadUrl(strSavedFeedbackLogoUrl);

                        return;
                    }
                }
            }
        }

        if (LitmusUtilities.isConnected(getActivity())) {
            m_progress_view.setVisibility(View.VISIBLE);
            m_ll_main_reload.setVisibility(View.GONE);

            ConnectionAsyncTask oConnectionAsyncTask = new ConnectionAsyncTask(listener, getActivity());

            if (strBaseUrl != null && strBaseUrl.length() > 0) {
                oConnectionAsyncTask.fnGenerateFeedbackUrl2(strBaseUrl, strAppId, strUserId,
                        strUserName, strCustomerEmail, isAllowMultipleFeedbacks, oTagParameters);
            } else {
                oConnectionAsyncTask.fnGenerateFeedbackUrl2(strAppId, strUserId, strUserName,
                        strCustomerEmail, isAllowMultipleFeedbacks, oTagParameters);
            }


            oConnectionAsyncTask.execute();

        } else {
            m_ll_main_reload.setVisibility(View.VISIBLE);
            m_progress_view.setVisibility(View.GONE);
        }
    }

    private LitmusOnConnectionResultListener listener = new LitmusOnConnectionResultListener() {
        @Override
        public void onResultReceived(String response, int requestId, boolean isCancelled) {

            if (!isCancelled && isAdded() && !getActivity().isFinishing()) {

                switch (requestId) {

                    case ConnectionAsyncTask.REQUEST_TYPE_GENERATE_FEEDBACK_URL_2:
                    case ConnectionAsyncTask.REQUEST_TYPE_GENERATE_FEEDBACK_URL_2_WITH_BASE_URL:
                        m_ll_main_reload.setVisibility(View.GONE);
                        m_progress_view.setVisibility(View.GONE);

                        if (response != null) {

                            LitmusParseUtility parser = new LitmusParseUtility();

                            Object object = parser.fnGetKeyValueAll(response, "data");

                            if (object != null && object instanceof JSONObject) {
                                JSONObject oJsonObject = (JSONObject) object;

                                int nCode = oJsonObject.optInt("code");

                                if (nCode == 0) {

                                    String strFeedbackRequestToken = oJsonObject.optString("feedback_request_token");
                                    String strLongUrl = oJsonObject.optString("long_url");
                                    // String strShortUrl = oJsonObject.optString("short_url");
                                    boolean has_responded = oJsonObject.optBoolean("has_responded", false);

                                    if (has_responded) {

                                        FeedbackSubmittedDialog(mContext);

                                    } else {

                                        m_web_view.setVisibility(View.VISIBLE);
                                        m_web_view.loadUrl(strLongUrl);

                                        LitmusApplicationSharedPreferences.getInstance(getActivity()).fnSaveNotificationAppId(strAppId);
                                        LitmusApplicationSharedPreferences.getInstance(getActivity()).fnSaveNotificationReminderNumber(nReminderNumber);
                                        LitmusApplicationSharedPreferences.getInstance(getActivity()).fnSaveNotificationFeedbackLongUrl(strLongUrl);
                                        LitmusApplicationSharedPreferences.getInstance(getActivity()).fnSaveNotificationUserId(strUserId);

                                    }


//                                    Intent i = new Intent(Intent.ACTION_VIEW);
//                                    i.setData(Uri.parse(strLongUrl));
//                                    startActivity(i);

                                } else if (nCode == 1 || nCode == 2) {
                                    String strErrorMessage = oJsonObject
                                            .optString("error_message");

                                }
                            }

                        } else {
                            m_ll_main_reload.setVisibility(View.VISIBLE);
                        }

                        break;
                }

            }

        }
    };

    public void fnAskForReadExternalStoragePermission(Activity thisActivity, int requestCode, String[] fileReadPermissions) {

        if (ContextCompat.checkSelfPermission(thisActivity,
                fileReadPermissions[0]) != PackageManager.PERMISSION_GRANTED) {

            // Temp workaround to be assigned null so that upload file can be called again
            mFilePathCallback = null;

            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, fileReadPermissions[0])) {
                //Explain to the user why we need to read the contacts

                ActivityCompat.requestPermissions(thisActivity, fileReadPermissions, requestCode);
            } else {
                ActivityCompat.requestPermissions(thisActivity, fileReadPermissions, requestCode);
            }
        }
    }

    public void FeedbackSubmittedDialog(final Context mContext) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setCancelable(false);

        builder.setTitle("FeedBack").setMessage(getResources().getString(R.string.feedback_submitted))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        fnCloseRatingFragment();

                    }
                });
        try {

            builder.show();
        } catch (WindowManager.BadTokenException e) {
            //use a log message
            e.printStackTrace();
        }
    }
}
