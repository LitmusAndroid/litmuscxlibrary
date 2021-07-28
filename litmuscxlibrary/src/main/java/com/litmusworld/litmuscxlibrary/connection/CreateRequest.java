package com.litmusworld.litmuscxlibrary.connection;

import android.util.Log;

import com.litmusworld.litmuscxlibrary.BuildConfig;
import com.litmusworld.litmuscxlibrary.businessobjects.NameValuePair;
import com.litmusworld.litmuscxlibrary.utils.LitmusConstants;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This is a helper class which accepts parameters and formats them as required by the respective Api
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016

 */
public class CreateRequest implements LitmusConstants {

	private static final String TAG = CreateRequest.class.getSimpleName();

	public ArrayList<NameValuePair> fnSendPushNotificationHeaders(
			String strAuthorizationKey) {
		ArrayList<NameValuePair> arrNameValuePairs = new ArrayList<>();

		arrNameValuePairs.add(new NameValuePair("Authorization", "key=" + strAuthorizationKey));

		return arrNameValuePairs;
	}

	public String fnSendPushNotification(JSONObject oDataObject, ArrayList<String> arrRegistrationIds) {

		JSONObject oOuterJsonObject = new JSONObject();
		JSONArray oRegistrationIdsArray = new JSONArray();

		if(arrRegistrationIds != null && arrRegistrationIds.size() > 0) {
			for(int i=0; i<arrRegistrationIds.size(); i++) {
				oRegistrationIdsArray.put(arrRegistrationIds.get(i));
			}
		}

		try {
			oOuterJsonObject.put("registration_ids", oRegistrationIdsArray);
			oOuterJsonObject.put("data", oDataObject);
		} catch (JSONException e) {
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library fnSendPushNotification: ", e);

			}
		}

		return oOuterJsonObject.toString();
	}

	public String fnGenerateFeedbackUrl2(String strAppId, String strCustomerId, String strCustomerName,
										 String strCustomerEmail, boolean isAllowMultipleFeedbacks,
										 HashMap<String, Object> oTagParameters) {

		JSONObject oOuterJsonObject = new JSONObject();

		try {
			oOuterJsonObject.put("app_id", strAppId);
			oOuterJsonObject.put("customer_id", strCustomerId);
			oOuterJsonObject.put("name", strCustomerName);
			oOuterJsonObject.put("user_email", strCustomerEmail);
			oOuterJsonObject.put("allow_multiple_feedbacks", isAllowMultipleFeedbacks);
			oOuterJsonObject.put("tag_os", "Android");
			oOuterJsonObject.put("tag_user_id", strCustomerId);

			if(oTagParameters != null) {

				for (Map.Entry<String, Object> entry : oTagParameters.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					oOuterJsonObject.put(key, value);
				}
				
			}


		} catch (JSONException e) {
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library fnGenerateFeedbackUrl2: ", e);

			}
		}

		return oOuterJsonObject.toString();
	}
}
