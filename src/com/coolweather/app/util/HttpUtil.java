package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener){
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					URL url =new URL(address);
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream is =connection.getInputStream();
					BufferedReader br= new BufferedReader(new InputStreamReader(is));
					StringBuilder resonse=new StringBuilder();
					String line;
					while((line=br.readLine())!=null){
						resonse.append(line);
					}
					if(listener!=null){
						listener.onFinish(resonse.toString());
					}
				} catch (Exception e) {
					if(listener!=null){
						listener.onError(e);
					}
				} finally{
					if(connection!=null){
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}
}
