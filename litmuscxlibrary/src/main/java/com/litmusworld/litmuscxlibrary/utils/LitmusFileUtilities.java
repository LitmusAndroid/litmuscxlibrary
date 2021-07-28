package com.litmusworld.litmuscxlibrary.utils;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class LitmusFileUtilities implements LitmusConstants
{
	public static String readStreamToString(InputStream inputStream)
			throws IOException
	{
		BufferedInputStream bufferedInputStream = null;
		InputStreamReader reader = null;
		try
		{
			bufferedInputStream = new BufferedInputStream(inputStream);
			reader = new InputStreamReader(bufferedInputStream);
			StringBuilder stringBuilder = new StringBuilder();

			final int bufferSize = 1024 * 2;
			char[] buffer = new char[bufferSize];
			int n = 0;
			while ((n = reader.read(buffer)) != -1)
			{
				stringBuilder.append(buffer, 0, n);
			}

			return stringBuilder.toString();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			closeQuietly(bufferedInputStream);
			closeQuietly(reader);
		}
		return null;
	}

	public static void closeQuietly(Closeable closeable)
	{
		try
		{
			if (closeable != null)
			{
				closeable.close();
			}
		}
		catch (IOException ioe)
		{
			// ignore
		}
	}

}
