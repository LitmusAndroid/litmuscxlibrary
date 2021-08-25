package com.litmusworld.litmuscxlibrary.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.FrameLayout;

import com.litmusworld.litmuscxlibrary.fragments.LitmusRatingFragment;

import java.util.HashMap;

/**
 *
 * Sample Activity to be called to Directly Load Feedback Screen.
 *  This Activity must be called Either using fnGetIntent() OR fnStartActivity() method
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016

 */
public class LitmusRatingActivity extends FragmentActivity implements LitmusRatingFragment.OnLitmusRatingFragmentListener {

    private static final int FRAME_LAYOUT_ID = 1234;

    private String strUserId;
    private String strAppId;
    private String strUserName;
    private int nReminderNumber;
    private boolean isAllowMultipleFeedbacks;
    private String strUserEmail;
    private String strWebLink;
    private String strBaseUrl;
    private HashMap<String, Object> oTagParameters;
    private static LitmusRatingFragment.OnLitmusRatingFragmentListener m_listener;

    private boolean isCopyAllowed=false,isShareAllowed=false;
    private boolean isMoreImageBlackElseWhite = true;

    public static Intent fnGetIntent(Context context, String strUserId, String strAppId,
                                     String strUserName, int nReminderNumber, String strUserEmail,
                                     boolean isAllowMultipleFeedbacks, String strBaseUrl,
                                     HashMap<String, Object> oOptionalTagParameters,boolean isCopyAllowed,
                                     boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {
        Intent intent = new Intent(context, LitmusRatingActivity.class);
        intent.putExtra(LitmusRatingFragment.PARAM_USER_ID, strUserId);
        intent.putExtra(LitmusRatingFragment.PARAM_APP_ID, strAppId);
        intent.putExtra(LitmusRatingFragment.PARAM_USERNAME, strUserName);
        intent.putExtra(LitmusRatingFragment.PARAM_REMINDER_NUMBER, nReminderNumber);
        intent.putExtra(LitmusRatingFragment.PARAM_CUSTOMER_EMAIL, strUserEmail);
        intent.putExtra(LitmusRatingFragment.PARAM_ALLOW_MULTIPLE_FEEDBACKS, isAllowMultipleFeedbacks);
        intent.putExtra(LitmusRatingFragment.PARAM_BASE_URL, strBaseUrl);
        intent.putExtra(LitmusRatingFragment.PARAM_TAG_PARAMETERS, oOptionalTagParameters);
       intent.putExtra(LitmusRatingFragment.PARAM_IS_COPY_ALLOWED, isCopyAllowed);
        intent.putExtra(LitmusRatingFragment.PARAM_IS_SHARE_ALLOWED, isShareAllowed);
        intent.putExtra(LitmusRatingFragment.PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE, isMoreImageBlackElseWhite);

        return intent;
    }

    public static Intent fnGetIntentForWebLink(Context context, String strWeblink, boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {
        Intent intent = new Intent(context, LitmusRatingActivity.class);
        intent.putExtra(LitmusRatingFragment.PARAM_WEB_URL, strWeblink);
       intent.putExtra(LitmusRatingFragment.PARAM_IS_COPY_ALLOWED, isCopyAllowed);
        intent.putExtra(LitmusRatingFragment.PARAM_IS_SHARE_ALLOWED, isShareAllowed);
        intent.putExtra(LitmusRatingFragment.PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE, isMoreImageBlackElseWhite);
        return intent;
    }

    public static void fnStartActivity(String strUserId, String strAppId,String strUserName,
                                       int nReminderNumber, String strUserEmail,
                                       boolean isAllowMultipleFeedbacks , Context context,
                                     boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {

        fnStartActivity(strUserId, strAppId, strUserName, nReminderNumber,
                strUserEmail, isAllowMultipleFeedbacks, null, context,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);
    }

    public static void fnStartActivity(String strUserId, String strAppId,String strUserName,
                                       int nReminderNumber, String strUserEmail,
                                       boolean isAllowMultipleFeedbacks, String strBaseUrl, Context context,boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {
        fnStartActivity(strUserId, strAppId, strUserName, nReminderNumber,
                strUserEmail, isAllowMultipleFeedbacks, strBaseUrl, null, context,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite); //check hardcoded
    }

    public static void fnStartActivity(String strUserId, String strAppId,String strUserName,
                                       int nReminderNumber, String strUserEmail,
                                       boolean isAllowMultipleFeedbacks, String strBaseUrl,
                                       HashMap<String, Object> oOptionalTagParameters, Context context,boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {
        Intent oIntent = fnGetIntent(context, strUserId, strAppId, strUserName, nReminderNumber,
                strUserEmail, isAllowMultipleFeedbacks, strBaseUrl, oOptionalTagParameters,isCopyAllowed,isShareAllowed,isMoreImageBlackElseWhite);
        if(context instanceof LitmusRatingFragment.OnLitmusRatingFragmentListener) {
            m_listener = (LitmusRatingFragment.OnLitmusRatingFragmentListener) context;
        }
        context.startActivity(oIntent);

    }

    public static void fnOpenWebLinkActivity(String strWeblink, Context context ,boolean isCopyAllowed, boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {
        Intent oIntent = fnGetIntentForWebLink(context, strWeblink,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);
        if(context instanceof LitmusRatingFragment.OnLitmusRatingFragmentListener) {
            m_listener = (LitmusRatingFragment.OnLitmusRatingFragmentListener) context;
        }
        context.startActivity(oIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }

        FrameLayout oFrameLayout = new FrameLayout(this);
        oFrameLayout.setId(FRAME_LAYOUT_ID);

        setContentView(oFrameLayout);

        if(getIntent() != null) {
            strUserId = getIntent().getStringExtra(LitmusRatingFragment.PARAM_USER_ID);
            strAppId = getIntent().getStringExtra(LitmusRatingFragment.PARAM_APP_ID);
            strUserName = getIntent().getStringExtra(LitmusRatingFragment.PARAM_USERNAME);
            nReminderNumber = getIntent().getIntExtra(LitmusRatingFragment.PARAM_REMINDER_NUMBER, 0);
            isAllowMultipleFeedbacks = getIntent().getBooleanExtra(LitmusRatingFragment.PARAM_ALLOW_MULTIPLE_FEEDBACKS, false);
            strUserEmail = getIntent().getStringExtra(LitmusRatingFragment.PARAM_CUSTOMER_EMAIL);
            strWebLink = getIntent().getStringExtra(LitmusRatingFragment.PARAM_WEB_URL);
            strBaseUrl = getIntent().getStringExtra(LitmusRatingFragment.PARAM_BASE_URL);
            isCopyAllowed = getIntent().getBooleanExtra(LitmusRatingFragment.PARAM_IS_COPY_ALLOWED, false);
            isShareAllowed = getIntent().getBooleanExtra(LitmusRatingFragment.PARAM_IS_SHARE_ALLOWED, false);
            isMoreImageBlackElseWhite = getIntent().getBooleanExtra(LitmusRatingFragment.PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE, true);

            oTagParameters = (HashMap<String, Object>) getIntent().getSerializableExtra(LitmusRatingFragment.PARAM_TAG_PARAMETERS);
        }

        LitmusRatingFragment oLitmusRatingFragment = null;

        if(strAppId != null && strAppId.length() > 0) {

            oLitmusRatingFragment = LitmusRatingFragment.newInstance(strBaseUrl, strUserId,
                        strAppId, strUserName, nReminderNumber,strUserEmail, isAllowMultipleFeedbacks, oTagParameters, false,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);

        } else if(strWebLink != null && strWebLink.length() > 0) {
            oLitmusRatingFragment = LitmusRatingFragment.newInstance(strWebLink, false,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);
        }

        if(oLitmusRatingFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            ft.add(FRAME_LAYOUT_ID, oLitmusRatingFragment);
            ft.commit();
        }
    }

    @Override
    public void onRatingCloseClicked() {
        if(m_listener!=null)
        m_listener.onRatingCloseClicked();
    }
}
