package com.wheely.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.wheely.utils.Utils;

/**
 * Работа с сетью
 */
public class NetworkUtils {	
	public static String getJsonString() {
		String jsonString = null;
		
		try {
			URL url = new URL(Utils.SERVER_URL);
			HttpURLConnection connection;
			
			connection = (HttpURLConnection) url.openConnection();					
			connection.setRequestMethod("GET");
			
			connection.setConnectTimeout(Utils.CONNECTION_TIMEOUT);
			
			jsonString = streamToString(connection.getInputStream());
		} catch (SocketTimeoutException e) {
			e.printStackTrace();			
		} catch (ProtocolException e) {	
			e.printStackTrace();			
		}
		catch (MalformedURLException e) {			
			e.printStackTrace();			
		}
		catch (IOException e) {
			e.printStackTrace();			
		}
		
		return jsonString;
	}
	
	
	private static String streamToString(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder total = new StringBuilder();
		String buf = "";
		
		try {
			while ((buf = reader.readLine()) != null) {
				total.append(buf);
			}
		} catch (IOException e) {
		   e.printStackTrace();
		}
		  
		return total.toString();
		
	}
}