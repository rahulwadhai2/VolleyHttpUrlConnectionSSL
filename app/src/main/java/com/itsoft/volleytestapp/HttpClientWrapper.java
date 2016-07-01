package com.itsoft.volleytestapp;


import android.util.Base64;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpClientWrapper {


	public static String  post(String requestUrl,String postValues) {

		URL url;
		String response = "";
		HttpURLConnection conn = null;
		String auth = "fau" +":"+"a";
		String base64EncodedCredentials = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
		try {
			url = new URL(requestUrl);

			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);// Specifies whether this URLConnection allows receiving data.
			conn.setDoOutput(true);// Specifies whether this URLConnection allows sending data.
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("Accept", "application/json; charset=utf-8");
			conn.setRequestProperty("Authorization", "Basic " + base64EncodedCredentials);


			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			writer.write("");

			writer.flush();
			writer.close();
			os.close();
			int responseCode=conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line="";
				BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				StringBuilder sb = new StringBuilder();
				while ((line=br.readLine()) != null) {
//                    response+=line;

					sb.append(line+"\n");
					response = sb.toString().substring(0, sb.toString().length() - 1);
				}
			}
			else {
				response="";
			}
		} catch (IOException ex) {
			if (conn != null) {
				conn.disconnect();
			}
		} finally {
			if (conn!= null) {
				conn.disconnect();
			}
		}

		return response;
	}


	public static String getResponseGET(String url, SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier) {

		String response = "";
		HttpsURLConnection httpsURLConnection = null;
		String auth = "1" +":"+"1";
		String base64EncodedCredentials = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);

		try {
			URL u = new URL(url);
			httpsURLConnection = (HttpsURLConnection) u.openConnection();
			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setConnectTimeout(15000);
			httpsURLConnection.setReadTimeout(15000);
			httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
			httpsURLConnection.setHostnameVerifier(hostnameVerifier);
			httpsURLConnection.setRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
			httpsURLConnection.setRequestProperty("branchCode", "1002");
			httpsURLConnection.connect();

			//22000/
			int status = httpsURLConnection.getResponseCode();

			switch (status) {
				case 200:
				case 201:
					BufferedReader br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line+"\n");
						response = sb.toString().substring(0, sb.toString().length() - 1);
					}
					br.close();
					return response;
			}

		} catch (IOException ex) {
			if (httpsURLConnection != null) {
				httpsURLConnection.disconnect();
			}
		} finally {
			if (httpsURLConnection!= null) {
					httpsURLConnection.disconnect();
			}
		}
		return null;
	}


}
