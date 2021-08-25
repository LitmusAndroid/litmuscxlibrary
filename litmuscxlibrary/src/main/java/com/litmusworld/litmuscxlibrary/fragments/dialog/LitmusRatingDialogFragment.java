package com.litmusworld.litmuscxlibrary.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.litmusworld.litmuscxlibrary.R;
import com.litmusworld.litmuscxlibrary.fragments.LitmusRatingFragment;
import com.litmusworld.litmuscxlibrary.utils.LitmusUtilities;

import java.util.HashMap;

public class LitmusRatingDialogFragment extends DialogFragment {

    public final static String TAG_DIALOG_FRAGMENT = "dialog_fragment_tag";

    private final static String TAG_FRAGMENT = "fragment_tag";

    private LitmusRatingFragment m_oLitmusRatingFragment;

    private String strUserId;
    private String strAppId;
    private String strUserName;
    private int nReminderNumber;
    private String strCustomerEmail;
    private boolean isAllowMultipleFeedbacks;
    private String strBaseUrl;
    private String strWebLink;
    private HashMap<String, Object> oTagParameters;

    private boolean isCopyAllowed=false,isShareAllowed=false;
    private boolean isMoreImageBlackElseWhite = true;
    private static AlertDialog.Builder builder;

    public static LitmusRatingDialogFragment newInstance(String strBaseUrl, String strUserId, String strAppId,
                                                         String strUserName, int nReminderNumber, String strCustomerEmail,
                                                         boolean isAllowMultipleFeedbacks, HashMap<String, Object> oOptionalTagParameters,
                                                        boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {
        Bundle args = new Bundle();
        args.putString(LitmusRatingFragment.PARAM_USER_ID, strUserId);
        args.putString(LitmusRatingFragment.PARAM_APP_ID, strAppId);
        args.putString(LitmusRatingFragment.PARAM_USERNAME, strUserName);
        args.putInt(LitmusRatingFragment.PARAM_REMINDER_NUMBER, nReminderNumber);
        args.putString(LitmusRatingFragment.PARAM_CUSTOMER_EMAIL, strCustomerEmail);
        args.putBoolean(LitmusRatingFragment.PARAM_ALLOW_MULTIPLE_FEEDBACKS, isAllowMultipleFeedbacks);
        args.putString(LitmusRatingFragment.PARAM_BASE_URL, strBaseUrl);
        args.putSerializable(LitmusRatingFragment.PARAM_TAG_PARAMETERS, oOptionalTagParameters);
        args.putBoolean(LitmusRatingFragment.PARAM_IS_COPY_ALLOWED, isCopyAllowed);
        args.putBoolean(LitmusRatingFragment.PARAM_IS_SHARE_ALLOWED, isShareAllowed);
        args.putBoolean(LitmusRatingFragment.PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE, isMoreImageBlackElseWhite);

        LitmusRatingDialogFragment oLitmusRatingDialogFragment = new LitmusRatingDialogFragment();
        oLitmusRatingDialogFragment.setArguments(args);
        return oLitmusRatingDialogFragment;
    }

    public static LitmusRatingDialogFragment newInstance(String strWebUrl,   boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {
        Bundle args = new Bundle();
        args.putString(LitmusRatingFragment.PARAM_WEB_URL, strWebUrl);

        args.putBoolean(LitmusRatingFragment.PARAM_IS_COPY_ALLOWED, isCopyAllowed);
        args.putBoolean(LitmusRatingFragment.PARAM_IS_SHARE_ALLOWED, isShareAllowed);
        args.putBoolean(LitmusRatingFragment.PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE, isMoreImageBlackElseWhite);

        LitmusRatingDialogFragment oLitmusRatingDialogFragment = new LitmusRatingDialogFragment();
        oLitmusRatingDialogFragment.setArguments(args);
        return oLitmusRatingDialogFragment;
    }

    public static LitmusRatingDialogFragment newInstanceAndShow(String strBaseUrl, String strUserId, String strAppId,
                                                         String strUserName, int nReminderNumber, String strCustomerEmail,
                                                             boolean isAllowMultipleFeedbacks, HashMap<String, Object> oOptionalTagParameters,
                                                                FragmentManager fm,    boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {

        LitmusRatingDialogFragment oLitmusRatingDialogFragment = newInstance(strBaseUrl, strUserId, strAppId,
                strUserName, nReminderNumber, strCustomerEmail, isAllowMultipleFeedbacks, oOptionalTagParameters,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);

        oLitmusRatingDialogFragment.fnShowRatingDialog(fm);

        return oLitmusRatingDialogFragment;
    }

    public static LitmusRatingDialogFragment newInstanceAndShow(String strWebUrl, FragmentManager fm,   boolean isCopyAllowed,boolean isShareAllowed, boolean isMoreImageBlackElseWhite) {

        LitmusRatingDialogFragment oLitmusRatingDialogFragment = newInstance(strWebUrl,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);

        oLitmusRatingDialogFragment.fnShowRatingDialog(fm);

        return oLitmusRatingDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.litmus_fragment_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            strUserId = getArguments().getString(LitmusRatingFragment.PARAM_USER_ID);
            strAppId  = getArguments().getString(LitmusRatingFragment.PARAM_APP_ID);
            strUserName = getArguments().getString(LitmusRatingFragment.PARAM_USERNAME);
            nReminderNumber = getArguments().getInt(LitmusRatingFragment.PARAM_REMINDER_NUMBER);
            strCustomerEmail = getArguments().getString(LitmusRatingFragment.PARAM_CUSTOMER_EMAIL);
            isAllowMultipleFeedbacks = getArguments().getBoolean(LitmusRatingFragment.PARAM_ALLOW_MULTIPLE_FEEDBACKS);
            strBaseUrl = getArguments().getString(LitmusRatingFragment.PARAM_BASE_URL);
            strWebLink = getArguments().getString(LitmusRatingFragment.PARAM_WEB_URL);
            isCopyAllowed = getArguments().getBoolean(LitmusRatingFragment.PARAM_IS_COPY_ALLOWED,false);
            isShareAllowed = getArguments().getBoolean(LitmusRatingFragment.PARAM_IS_SHARE_ALLOWED,false);
            isMoreImageBlackElseWhite = getArguments().getBoolean(LitmusRatingFragment.PARAM_IS_MORE_IMAGE_BLACK_ELSE_WHITE);

            oTagParameters = (HashMap<String, Object>) getArguments().getSerializable(LitmusRatingFragment.PARAM_TAG_PARAMETERS);
        }

        m_oLitmusRatingFragment = (LitmusRatingFragment) getChildFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (m_oLitmusRatingFragment == null) {

            if(strAppId != null && strAppId.length() > 0) {

                m_oLitmusRatingFragment = LitmusRatingFragment.newInstance(strBaseUrl, strUserId, strAppId,
                        strUserName, nReminderNumber, strCustomerEmail, isAllowMultipleFeedbacks, oTagParameters, true,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);

            } else if(strWebLink != null && strWebLink.length() > 0) {
                m_oLitmusRatingFragment = LitmusRatingFragment.newInstance(strWebLink, true,isCopyAllowed,isShareAllowed, isMoreImageBlackElseWhite);
            }

        }

        if(m_oLitmusRatingFragment != null) {
            if (m_oLitmusRatingFragment.isAdded()) {
                // Already added, no need to update
            } else {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.add(R.id.fl_fragment_container, m_oLitmusRatingFragment, TAG_FRAGMENT).commit();
            }
        }

    }

    // Litmus Embedded demo

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        setStyle(DialogFragment.STYLE_NO_TITLE,
//                R.style.MyTransparentDialog_BackgroundBlurDialogEffect);
//
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*Bundle args = getArguments();
        String paramTitle = args.getString(PARAM_TITLE);

        dialog.setTitle(paramTitle);*/
        return dialog;
    }


    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);

        int nLeftRightMargin = getResources().getDimensionPixelOffset(R.dimen.rating_details_dialog_margin) * 2;
        int nTopBottomMargin = getResources().getDimensionPixelOffset(R.dimen.rating_details_dialog_margin_top) * 2;

        DisplayMetrics dp = LitmusUtilities.fnGetScreenSize(getActivity());
        int dialogWidth = dp.widthPixels - nLeftRightMargin;
        int dialogHeight = dp.heightPixels - nTopBottomMargin;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

//        getDialog().getWindow().setLayout(dialogWidth,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
    };

    public void fnShowRatingDialog(FragmentManager fm) {
        show(fm, LitmusRatingDialogFragment.TAG_DIALOG_FRAGMENT);
    }


    public static void fnCloseRatingDialog(FragmentManager fm) {
        Fragment oLitmusDialogFragment = fm.findFragmentByTag(TAG_DIALOG_FRAGMENT);
        if (oLitmusDialogFragment != null) {
            fm.beginTransaction().remove(oLitmusDialogFragment)
                    .commitAllowingStateLoss();
        }
    }

    public void FeedbackGivenDialog(final Context mContext){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setCancelable(false);

        builder.setTitle("").setMessage("User Already given feedback")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
                        fnCloseRatingDialog(getActivity().getSupportFragmentManager());

                    }
                });


        try {

            builder.show();
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
            e.printStackTrace();
        }
    }

}
