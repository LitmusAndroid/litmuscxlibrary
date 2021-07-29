package com.litmusworld.litmuscxlibrary_android;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.litmusworld.litmuscxlibrary.activities.LitmusRatingActivity;
import com.litmusworld.litmuscxlibrary.fragments.dialog.LitmusRatingDialogFragment;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CheckBox m_checkbox_copy;
    private CheckBox m_checkbox_share;
    private CheckBox m_checkbox_dialog;
    private CheckBox m_checkbox_more_image_black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_checkbox_copy = (CheckBox) findViewById(R.id.checkbox_copy);
        m_checkbox_share = (CheckBox) findViewById(R.id.checkbox_share);
        m_checkbox_dialog = (CheckBox) findViewById(R.id.checkbox_dialog);
        m_checkbox_more_image_black = (CheckBox) findViewById(R.id.checkbox_more_image_black);

        // fnOnButtonClick(null);
    }

    public void fnOnButtonClick(View v) {
        String strAppId = "xxxxxxxx"; // to be replaced by the app id shared with you

        boolean isCopyAllowed = m_checkbox_copy.isChecked();
        boolean isShareAllowed = m_checkbox_share.isChecked();
        boolean isShowDialog = m_checkbox_dialog.isChecked();
        boolean isMoreImageBlackElseWhite = m_checkbox_more_image_black.isChecked();

        fnOpenLitmusFeedback(this, strAppId, getRandomString(10),
                isShowDialog, isCopyAllowed, isShareAllowed, isMoreImageBlackElseWhite);
    }


    public static void fnOpenLitmusFeedback(Context context, String strAppId, String strRandomId,
                                            boolean showInDialog, boolean isCopyAllowed, boolean isShareAllowed,
                                            boolean isMoreImageBlackElseWhite) {

        String strBaseUrl = null; // Base url to be used to get conversation url (Optional)
        // (Default if not sent is "https://app.litmusworld.com/rateus",
        // other values are For Demo = "https://demo.litmusworld.com"
        // For App india = "https://app-india.litmusworld.com/rateus"

        String strUserId = strRandomId; // Userid of the user (Replace this with your UserId)
        String strUserName = null; // optional username (Replace this with your user name or keep it empty)
        String strUserEmail = null; // optional email id (Replace this with your user email address or keep it empty)
        int nReminderNumber = -1; // -1 will call api every time.
        boolean isAllowMultipleFeedbacks = true;

        if(showInDialog) {
            if(context instanceof FragmentActivity) {

                LitmusRatingDialogFragment.newInstanceAndShow(strBaseUrl, strUserId, strAppId, strUserName,
                        nReminderNumber, strUserEmail, isAllowMultipleFeedbacks, null,
                        ((FragmentActivity) context).getSupportFragmentManager(), isCopyAllowed, isShareAllowed, isMoreImageBlackElseWhite);
            }
        } else {
            LitmusRatingActivity.fnStartActivity(strUserId, strAppId, strUserName, nReminderNumber,
                    strUserEmail, isAllowMultipleFeedbacks, strBaseUrl, null, context, isCopyAllowed, isShareAllowed, isMoreImageBlackElseWhite);
        }

    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
