package com.thinqq.qsports.client.helper;

import java.util.Map;
import java.util.Set;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.thinqq.qsports.shared.Constants;

public class ServiceRequestHelper {
	
 public static void sendRequest(Map<String,String> paramMap,String endPoint, RequestCallback requestCallBack) throws RequestException{
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, endPoint);
		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
		StringBuffer postData = new StringBuffer();
		if(paramMap !=null){
			Set<String> paramNames = paramMap.keySet();
			for(String paramName:paramNames){
				String paramValue = paramMap.get(paramName);
				if(paramValue==null){
					paramValue = "";
				}
				postData.append(URL.encode(paramName)).append("=").append(URL.encode(paramValue));
				postData.append("&");
			}
		}
		requestBuilder.sendRequest(postData.toString(),requestCallBack);

 }
}
