package com.litmusworld.litmuscxlibrary.connection;

/**
 * URL for making Api calls are list in this class
 *
 * Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public interface URLConstants {
	String API_PREFIX = "/api/";

	String URL_GENERATE_FEEDBACK_URL2 = API_PREFIX + "feedbackrequests/generate_customer_feedback_url";

	String URL_GCM_PUSH = "https://android.googleapis.com/gcm/send";
}

