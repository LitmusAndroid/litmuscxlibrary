package com.litmusworld.litmuscxlibrary.parser;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.litmusworld.litmuscxlibrary.connection.URLConstants;
import com.litmusworld.litmuscxlibrary.utils.LitmusConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * THis class is used to parse json response from the server
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public class LitmusParseUtility implements URLConstants, LitmusConstants
{

	/**
	 *
	 * @param strJson
	 * 			JSON object string
	 * @param strKey
	 * 			Name of the key whose value is required
     * @return
     *      Value of the key. Returns null if key not found or invalid json input
     */
	public Object fnGetKeyValueAll(String strJson, String strKey)
	{
		try
		{
			JSONObject oJsonObject = new JSONObject(strJson);

			if (oJsonObject.has(strKey))
			{
				return oJsonObject.opt(strKey);
			}
		}
		catch (JSONException e)
		{
			System.out.println(e.toString());
		}

		return null;
	}
}
