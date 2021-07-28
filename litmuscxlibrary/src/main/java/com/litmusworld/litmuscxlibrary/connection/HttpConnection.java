package com.litmusworld.litmuscxlibrary.connection;

import android.util.Log;

import com.litmusworld.litmuscxlibrary.BuildConfig;
import com.litmusworld.litmuscxlibrary.LitmusCore;
import com.litmusworld.litmuscxlibrary.businessobjects.NameValuePair;
import com.litmusworld.litmuscxlibrary.utils.LitmusConstants;
import com.litmusworld.litmuscxlibrary.utils.LitmusFileUtilities;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *  This Class is responsible for making HTTP/HTTPS Connection request call.
 *  This class methods must be called asynchrounously as Request blocks the Main UI thread
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016

 */
public class HttpConnection implements LitmusConstants
{
	private static final String CONTENT_TYPE_HEADER = "Content-Type";

	private static final String TAG = HttpConnection.class.getSimpleName();


	public String fnJSONPost(String strUrl, String strPostMessage,
							 String strMethodName, ArrayList<NameValuePair> arrHeadersNameValuePairs)
	{
		String strResult = null;
		// Post Data
		try {
			URL oUrl = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) oUrl.openConnection();
			conn.setReadTimeout(60000);
			conn.setConnectTimeout(65000);

			conn.setRequestMethod(strMethodName);

//			conn.setRequestProperty(USER_AGENT_HEADER, "litmus-android-app/v"
//					+ QSquareApplication.getInstance().getAppVersion());

			conn.setRequestProperty(CONTENT_TYPE_HEADER, "application/json");

			if (arrHeadersNameValuePairs != null)
			{
				for (int i = 0; i < arrHeadersNameValuePairs.size(); i++)
				{
					NameValuePair oNameValuePair = arrHeadersNameValuePairs.get(i);
					conn.setRequestProperty(oNameValuePair.getName(), oNameValuePair.getValue());
				}
			}

			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					os, "UTF-8"));
			writer.write(strPostMessage);
			writer.flush();
			writer.close();
			os.close();
			conn.connect();

			InputStream inStream = conn.getInputStream();
			strResult = LitmusFileUtilities.readStreamToString(inStream);
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library fnJSONPost: ", e);

			}
		} catch (IOException e) {
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library fnJSONPost: ", e);

			}
		} catch (Exception e) {
//			e.printStackTrace();
			if(BuildConfig.DEBUG){
				Log.e(TAG, "CX Library fnJSONPost: ", e);

			}
		}

		return strResult;
	}
}
