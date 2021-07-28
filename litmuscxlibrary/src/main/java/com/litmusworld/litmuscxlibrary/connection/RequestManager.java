package com.litmusworld.litmuscxlibrary.connection;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

import com.litmusworld.litmuscxlibrary.BuildConfig;
import com.litmusworld.litmuscxlibrary.businessobjects.NameValuePair;
import com.litmusworld.litmuscxlibrary.utils.LitmusConstants;

/**
 *
 * All web requests go through this class. It make a synchronous call to the server,
 * must be called asynchromously, to avoid blocking main thread
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public class RequestManager implements URLConstants, LitmusConstants {

	private static final String TAG = RequestManager.class.getSimpleName();

	private HttpConnection mHttpConnection;
	private CreateRequest mCreateRequest;

	private String strBaseUrl = null;

	/**
	 * Constructor for this class. Initializes with the base url for connection by retrieving meta data
	 * information from the Manifest file
	 *
	 * @param context
     */
	public RequestManager(Context context) {
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			strBaseUrl = bundle.getString("LitmusApiURL");
			if (strBaseUrl == null || strBaseUrl.length() == 0) {
				// we need to handle the case when LitmusApi Url is not defined
				// in the android manifest
			}
		} catch (NameNotFoundException | NullPointerException e) {
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library RequestManager: ", e);

			}
		}

		mHttpConnection = new HttpConnection();
		mCreateRequest = new CreateRequest();
	}

	public String fnSendPushNotification(JSONObject oDataBody, ArrayList<String> arrRegistrationIds, String strAuthorizationKey)
	{
		String strResult = null;

		try
		{
			String strPostMessage = mCreateRequest
					.fnSendPushNotification(oDataBody, arrRegistrationIds);

			ArrayList<NameValuePair> arrHeaderNameValuePairs = mCreateRequest
					.fnSendPushNotificationHeaders(strAuthorizationKey);

			strResult = mHttpConnection.fnJSONPost(URL_GCM_PUSH,strPostMessage,"POST", arrHeaderNameValuePairs);

		}
		catch (Exception e)
		{
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library fnSendPushNotification: ", e);

			}
		}

		return strResult;
	}

	public String fnGenerateFeedbackUrl2(String strAppId, String strCustomerId, String strCustomerName,
										 String strCustomerEmail, boolean isAllowMultipleFeedbacks,
										 HashMap<String, Object> oTagParameters) {

		return fnGenerateFeedbackUrl2(strBaseUrl, strAppId, strCustomerId, strCustomerName,
				strCustomerEmail, isAllowMultipleFeedbacks, oTagParameters);
	}

	public String fnGenerateFeedbackUrl2(String strBaseUrl, String strAppId, String strCustomerId,
										 String strCustomerName, String strCustomerEmail,
										 boolean isAllowMultipleFeedbacks, HashMap<String, Object> oTagParameters) {
		String strResult = null;

		try
		{

			String strPostMessage = mCreateRequest
					.fnGenerateFeedbackUrl2(strAppId, strCustomerId,
							strCustomerName, strCustomerEmail, isAllowMultipleFeedbacks, oTagParameters);

			strResult = mHttpConnection.fnJSONPost(strBaseUrl + URL_GENERATE_FEEDBACK_URL2,strPostMessage,"POST", null);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library fnGenerateFeedbackUrl2: ", e);

			}
		}

		return strResult;
	}

}