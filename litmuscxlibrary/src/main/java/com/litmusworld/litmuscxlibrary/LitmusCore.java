package com.litmusworld.litmuscxlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.litmusworld.litmuscxlibrary.connection.ConnectionAsyncTask;
import com.litmusworld.litmuscxlibrary.databases.LitmusApplicationSharedPreferences;
import com.litmusworld.litmuscxlibrary.interfaces.LitmusOnConnectionResultListener;
import com.litmusworld.litmuscxlibrary.utils.LitmusConstants;
import com.litmusworld.litmuscxlibrary.utils.LitmusUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is a helper class which provides many helper function for sending
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 * */
public class LitmusCore implements LitmusOnConnectionResultListener, LitmusConstants {
	/**
	 * Field Description private static context private static latitude private
	 * static longitude private static accuracy
	 * */
	private Context context;

	private static final String TAG = LitmusCore.class.getSimpleName();


	private String regid;

	/** Constructor Description of LitmusCore(Context ctxt) */
	private LitmusCore(Context ctxt) {
		context = ctxt;
	}

	/**
	 * Description of getInstance(Context ctxt) this will call the private
	 * constructor LitmusCore and set the Context value. The method should be
	 * called like this way LitmusCore core =
	 * LitmusCore.getInstance(getActivity() .getApplicationContext());
	 * 
	 * @param ctxt
	 *            android Context
	 * @return LitmusCore reference
	 */
	public static LitmusCore getInstance(Context ctxt) {
		return new LitmusCore(ctxt);
	}


	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context)
	{
		String registrationId = LitmusApplicationSharedPreferences.getInstance(
				context).fnGetGCMRegId();
		if (registrationId.length() == 0)
		{
			//Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = LitmusApplicationSharedPreferences.getInstance(
				context).fnGetGCMAppVersionCode();
		int currentVersion = LitmusUtilities.getAppVersionCode(context);
		if (registeredVersion != currentVersion)
		{
			//Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}



	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId)
	{
		LitmusApplicationSharedPreferences.getInstance(context)
				.fnSaveGCMRegistrationIdAndAppVersion(
						LitmusUtilities.getAppVersionCode(context), regId);
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend()
	{
		// Your implementation here.
		String strDeviceId = LitmusUtilities.fnGetDeviceId(context);

		// send - regid to your server

	}

	/**
	 * Send Push notification to itself
	 * @param strTitle
	 * 			Notification title
	 * @param strMessage
	 * 			Notification message
	 * @param strAppId
	 * 			Application feedback Id
	 * @param strUserId
	 * 			User Id
	 * @param strUserName
	 * 			User Name
     * @param nReminderNumber
	 * 			Reminder Number
	 *
     */
	public void fnSendPushNotificationToMe(String strTitle, String strMessage, String strAppId,
										   String strUserId, String strUserName, int nReminderNumber,
										   String strGCMServerKey) {

		ConnectionAsyncTask oConnectionAsyncTask = new ConnectionAsyncTask(this, context);

		String strRegistrationId = LitmusApplicationSharedPreferences.getInstance(context).fnGetGCMRegId();
		ArrayList<String> arrRegistrationIds = new ArrayList<>();
		arrRegistrationIds.add(strRegistrationId);

		JSONObject oDataBody = new JSONObject();

		try {
			oDataBody.put("title", strTitle);
			oDataBody.put("message", strMessage);
			oDataBody.put("app_id", strAppId);

			// optional for demo purpose
			oDataBody.put("user_id", strUserId);

			// optional for demo purpose
			oDataBody.put("user_name", strUserName);
			oDataBody.put("reminder_number", nReminderNumber);

		} catch (JSONException e) {
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "fnSendPushNotificationToMe: ", e);

			}
		}

		oConnectionAsyncTask.fnSendPushNotification(oDataBody,arrRegistrationIds, strGCMServerKey);

		oConnectionAsyncTask.execute();
	}

	/**
	 * Description of onResultReceived(String response, int requestId, boolean
	 * isCancelled)
	 *
	 * @param response
	 *            string response
	 * @param requestId
	 *            int requestId
	 * @param isCancelled
	 *            boolean isCancelled
	 * @return boolean
	 */
	@Override
	public void onResultReceived(String response, int requestId,
			boolean isCancelled) {

	};
}
