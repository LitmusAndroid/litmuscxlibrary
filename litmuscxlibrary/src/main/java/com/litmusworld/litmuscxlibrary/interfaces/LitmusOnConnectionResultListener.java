package com.litmusworld.litmuscxlibrary.interfaces;

/**
 *  This class is responsible for receiving Connection callbacks
 *
 *  Copyright (C) Litmusworld Pvt Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Fenil Jain <fenil.jain@litmusworld.com>, August 2016
 */
public interface LitmusOnConnectionResultListener
{
	/**
	 * This method is called after receiving Response from the server
	 * @param response
	 * 			Server Response
	 * @param requestId
	 * 			Request Id to identify which api is called
	 * @param isCancelled
	 * 			Boolean - true indicating Request is cancelled by user, false otherwise
     */
	public void onResultReceived(String response, int requestId, boolean isCancelled);
}
