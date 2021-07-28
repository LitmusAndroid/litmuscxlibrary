package com.litmusworld.litmuscxlibrary.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.NotificationCompat;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;


/**
 * This is the utility class consists of various uitlity methods to be used by Library
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public class LitmusUtilities implements LitmusConstants
{
	/**
	 * Show notification on device
	 * @param context
	 * 			Context class instance
	 * @param oIntent
	 * 			Instance of Intent class. To be used when notification is clicked
	 * @param strNotificationTitle
	 * 			Title of notification
	 * @param strNotificationMessage
	 * 			Subtitle of notification
	 * @param autoCancel
	 * 			True - if auto cancel notification false otherwise
	 * @param bigPicture
	 * 			Bitmap image for Notification. Can be null if no image is present
	 * @param NOTIFICATION_ID
	 * 			Unique notification ID
	 * @param isPlaySound
	 * 			True to play default notification sound, false otherwise
     * @param isVibrate
	 * 			True for default vibration, false otherwise
     * @param drawableId
	 * 			Drawable id for the image resource which needs to be shown in notification bar
     */
	public static void fnShowNotification(Context context, Intent oIntent,
										  String strNotificationTitle, String strNotificationMessage,
										  boolean autoCancel, Bitmap bigPicture, int NOTIFICATION_ID,
										  boolean isPlaySound, boolean isVibrate, int drawableId) {

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		//int color = context.getResources().getColor(R.color.color_theme_primary);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				oIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(drawableId)
				.setContentTitle(strNotificationTitle)
				.setContentText(strNotificationMessage)
				.setAutoCancel(autoCancel)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);
						// color for notification icon for Android 5.0 and above, it is ignored for lower OS versions
				//.setColor(color);



		if(isPlaySound)
			mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

		if(isVibrate)
			mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);

		if (bigPicture == null) {
			mBuilder.setStyle(
					new NotificationCompat.BigTextStyle()
							.bigText(strNotificationMessage));
		} else {
			mBuilder.setStyle(
					new NotificationCompat.BigPictureStyle()
							.bigPicture(bigPicture).setBigContentTitle(strNotificationMessage));
		}

		// inbox style (clubbed notification)
//		Notification notif = new Notification.Builder(mContext)
//				.setContentTitle("5 New mails from " + sender.toString())
//				.setContentText(subject)
//				.setSmallIcon(R.drawable.new_mail)
//				.setLargeIcon(aBitmap)
//				.setStyle(new Notification.InboxStyle()
//						.addLine(str1)
//						.addLine(str2)
//						.setContentTitle("")
//						.setSummaryText("+3 more"))
//				.build();

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


	}

	/**
	 * Gets unique device id
	 * @param context
	 * 			Context class instance
	 * @return	Unique devide Id
     */
	public static String fnGetDeviceId(Context context)
	{
		DeviceUuidFactory oDeviceUuidFactory = new DeviceUuidFactory(context);
		String android_id = oDeviceUuidFactory.getDeviceUuid().toString();

		// String android_id = Secure.getString(context.getContentResolver(),
		// Secure.ANDROID_ID);

		return android_id;
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersionCode(Context context)
	{
		try
		{
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Check if device is connected to the internet connection
	 * @param context
	 * 			Context instance
	 * @return	true - if connected to internet, false otherwise
     */
	public static boolean isConnected(Context context) {


		try {
			ConnectivityManager cm =
					(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			boolean isConnected = activeNetwork != null &&
					activeNetwork.isConnectedOrConnecting();

			return isConnected;


		} catch (Exception e) {
			System.out.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity error", e.toString());
		}
		return false;
	}

	public static int dpToPx(int dp, Context context)
	{
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
//		int px = Math.round(dp
//				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

		int px = Math.round(dp
				* (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));

		return px;
	}

	public static int pxToDp(int px, Context context)
	{
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
//		int px = Math.round(dp
//				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

		int dp = Math.round(px
				/ (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));

		return dp;
	}

	public static DisplayMetrics fnGetScreenSize(Context context)
	{
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics;
	}
}
