package com.litmusworld.litmuscxlibrary.connection;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;

import com.litmusworld.litmuscxlibrary.interfaces.LitmusOnConnectionResultListener;

import org.json.JSONObject;

/**
 * This is a helper class which is used to make Http/https request Asynchronously.
 * This is a reusable component, which basically accepts Request parameters and Make http/https request
 * asyncronously using AsynckTask. With the help of a LitmusOnConnectionResultListener, it informs the caller
 * once request is completed
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public class ConnectionAsyncTask extends AsyncTask<Void, Void, String> {

	public static final int REQUEST_TYPE_SEND_PUSH_NOTIFICATION = 59;
	public static final int REQUEST_TYPE_GENERATE_FEEDBACK_URL_2 = 60;
	public static final int REQUEST_TYPE_GENERATE_FEEDBACK_URL_2_WITH_BASE_URL = 61;

	private LitmusOnConnectionResultListener mConnectionResultListener;
	private int mRequestId;
	private RequestManager mRequestManager;

	private String strGCMRegId;
	private String strDeviceIdentifier;


	private String strAppId;
	private String strCustomerEmailAddress;
	private String strCustomerId;
	private String strCustomerName;
	private String strNotifyChannel;
	private boolean isAllowMultipleFeedbacks;

	private JSONObject oDataBody;
	private ArrayList<String> arrRegistrationIds;
	private String strAuthorizationKey;

	private String strBaseUrl;
	private HashMap<String, Object> oTagParameters;

	public ConnectionAsyncTask(LitmusOnConnectionResultListener listener,
			Context context) {
		mConnectionResultListener = listener;
		mRequestManager = new RequestManager(context);
	}

	public void fnSendPushNotification(JSONObject oDataBody, ArrayList<String> arrRegistrationIds,
									   String strAuthorizationKey) {
		this.oDataBody= oDataBody;
		this.arrRegistrationIds = arrRegistrationIds;
		this.strAuthorizationKey= strAuthorizationKey;

		mRequestId = REQUEST_TYPE_SEND_PUSH_NOTIFICATION;
	}

	public void fnGenerateFeedbackUrl2(String strAppId, String strCustomerId, String strCustomerName,
									   String strCustomerEmail, boolean isAllowMultipleFeedbacks,
									   HashMap<String, Object> oTagParameters) {
		this.strAppId = strAppId;
		this.strCustomerId = strCustomerId;
		this.strCustomerName = strCustomerName;
		this.strCustomerEmailAddress = strCustomerEmail;
		this.isAllowMultipleFeedbacks = isAllowMultipleFeedbacks;
		this.oTagParameters = oTagParameters;

		mRequestId = REQUEST_TYPE_GENERATE_FEEDBACK_URL_2;
	}

	public void fnGenerateFeedbackUrl2(String strBaseUrl, String strAppId, String strCustomerId,
									   String strCustomerName,String strCustomerEmail, boolean isAllowMultipleFeedbacks,
									   HashMap<String, Object> oTagParameters) {
		this.strAppId = strAppId;
		this.strCustomerId = strCustomerId;
		this.strCustomerName = strCustomerName;
		this.strCustomerEmailAddress = strCustomerEmail;
		this.isAllowMultipleFeedbacks = isAllowMultipleFeedbacks;
		this.strBaseUrl = strBaseUrl;
		this.oTagParameters = oTagParameters;

		mRequestId = REQUEST_TYPE_GENERATE_FEEDBACK_URL_2_WITH_BASE_URL;
	}

	@Override
	protected String doInBackground(Void... params) {
		String strResult = null;

		switch (mRequestId) {

			case REQUEST_TYPE_SEND_PUSH_NOTIFICATION:
				strResult = mRequestManager.fnSendPushNotification(oDataBody, arrRegistrationIds, strAuthorizationKey);

				break;

			case REQUEST_TYPE_GENERATE_FEEDBACK_URL_2:
				strResult = mRequestManager.fnGenerateFeedbackUrl2(strAppId, strCustomerId, strCustomerName,
						strCustomerEmailAddress, isAllowMultipleFeedbacks, oTagParameters);

				break;

			case REQUEST_TYPE_GENERATE_FEEDBACK_URL_2_WITH_BASE_URL:
				strResult = mRequestManager.fnGenerateFeedbackUrl2(strBaseUrl, strAppId, strCustomerId,
						strCustomerName,strCustomerEmailAddress, isAllowMultipleFeedbacks, oTagParameters);

				break;

			default:
				break;
		}

		return strResult;
	}

	@Override
	protected void onPostExecute(String result) {
		if (mConnectionResultListener != null) {
			mConnectionResultListener.onResultReceived(result, mRequestId,
					isCancelled());
		}
	}

}
