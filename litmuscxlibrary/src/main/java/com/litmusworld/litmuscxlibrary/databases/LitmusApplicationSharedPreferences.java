package com.litmusworld.litmuscxlibrary.databases;

import android.content.Context;
import android.content.SharedPreferences;

import com.litmusworld.litmuscxlibrary.utils.LitmusConstants;


/**
 * THis is used to store information required by this library in Applications shared preferences
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public class LitmusApplicationSharedPreferences implements LitmusConstants {

	/** SharedPreferences instance  */
	private SharedPreferences mSharedPreferences;

	/** Static instance of LitmusApplicationSharedPreferences class */
	private static LitmusApplicationSharedPreferences mApplicationSharedPreferences;

	/**
	 *	Singleton class to access this class
	 *
	 * @param context
	 * 			Context instance
	 * @return LitmusApplicationSharedPreferences instance
     */
	public static LitmusApplicationSharedPreferences getInstance(Context context)
	{
		if (mApplicationSharedPreferences == null)
		{
			mApplicationSharedPreferences = new LitmusApplicationSharedPreferences(
					context);
		}

		return mApplicationSharedPreferences;
	}

	/**
	 * Constructor with initializes shared preferences instance
	 * @param context
	 * 			Instance of Context class
     */
	public LitmusApplicationSharedPreferences(Context context)
	{
		mSharedPreferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
	}

	/**
	 * Clears all contents of this libraries shared preferences
	 */
	public void fnClearAll()
	{
		mSharedPreferences.edit().clear().apply();
	}

	/**
	 * Gets SharedPreferences instance
	 * @return	instance for SharedPreferences
     */
	public SharedPreferences getSharedPreferences()
	{
		return mSharedPreferences;
	}

	/**
	 *	Save App version and GCM registration id
	 *
	 * @param appVersion
	 * 			Application version
	 * @param regId
	 * 			GCM registraiotn id
     */
	public void fnSaveGCMRegistrationIdAndAppVersion(int appVersion,
			String regId)
	{
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(SAVED_PROPERTY_REG_ID, regId);
		editor.putInt(SAVED_PROPERTY_APP_VERSION, appVersion);
		editor.apply();
	}

	/**
	 *	Get Saved GCM registration id
	 * @return
	 * 		Saved GCM registration id
     */
	public String fnGetGCMRegId()
	{
		return mSharedPreferences.getString(SAVED_PROPERTY_REG_ID, "");
	}

	/**
	 *	Get Saved App version
	 * @return
	 * 		Saved App version
     */
	public int fnGetGCMAppVersionCode() {
		return mSharedPreferences.getInt(SAVED_PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
	}

	/**
	 *
	 * 	Gets drawable id saved
	 * @return
	 * 		Drawable Id
     */
	public int fnGetNotificationDrawableId() {
		return mSharedPreferences.getInt(SAVED_PROPERTY_NOTIFICATION_DRAWABLE_ID, 0);
	}

	/**
	 *	Save drawable id required for showing notification
	 * @param nNotificationDrawableId
	 * 			Drawable id
     */
	public void fnSaveNotificationDrawableId(int nNotificationDrawableId) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(SAVED_PROPERTY_NOTIFICATION_DRAWABLE_ID, nNotificationDrawableId);
		editor.apply();
	}

	/**
	 *	Gets Saved App ID
	 *
	 * @return saved App Id
     */
	public String fnGetNotificationAppId()
	{
		return mSharedPreferences.getString(SAVED_PROPERTY_NOTIFICATION_APP_ID, "");
	}

	/**
	 *	Save App Id for Notification received
	 * @param strAppId
	 * 			App Id / Feedback project Id
     */
	public void fnSaveNotificationAppId(String strAppId)
	{
		mSharedPreferences.edit()
				.putString(SAVED_PROPERTY_NOTIFICATION_APP_ID, strAppId).apply();
	}

	/**
	 *
	 * Gets saved reminder number
	 *
	 * @return saved reminder number
     */
	public int fnGetNotificationReminderNumber()
	{
		return mSharedPreferences.getInt(SAVED_PROPERTY_NOTIFICATION_REMINDER_NUMBER, -1);
	}

	/**
	 * Save Reminder number for the notification received
	 *
	 * @param nReminderNumber
	 * 		Reminder number
     */
	public void fnSaveNotificationReminderNumber(int nReminderNumber) {
		mSharedPreferences.edit()
				.putInt(SAVED_PROPERTY_NOTIFICATION_REMINDER_NUMBER, nReminderNumber).apply();
	}

	/**
	 * Gets Long feedback url which is saved
	 *
	 * @return Saved feedback url
     */
	public String fnGetNotificationFeedbackLongUrl() {
		return mSharedPreferences.getString(SAVED_PROPERTY_NOTIFICATION_FEEDBACK_LONG_URL, "");
	}

	/**
	 * Save Feedback long url for the notification received
	 *
	 * @param strNotificationFeedbackUrl
	 * 			feedback url
     */
	public void fnSaveNotificationFeedbackLongUrl(String strNotificationFeedbackUrl) {
		mSharedPreferences.edit()
				.putString(SAVED_PROPERTY_NOTIFICATION_FEEDBACK_LONG_URL, strNotificationFeedbackUrl).apply();
	}

	/**
	 * Gets User id saved for the notification received
	 * @return
	 * 		Saved user id
     */
	public String fnGetNotificationUserId() {
		return mSharedPreferences.getString(SAVED_PROPERTY_NOTIFICATION_USER_ID, "");
	}

	/**
	 * Save User id for the notification received
	 *
	 * @param strNotificationUserId
	 * 			User id
     */
	public void fnSaveNotificationUserId(String strNotificationUserId) {
		mSharedPreferences.edit()
				.putString(SAVED_PROPERTY_NOTIFICATION_USER_ID, strNotificationUserId).apply();
	}

}
