package com.thinqq.qsports.test;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

public class RestClientUtil {
	public static String DEV_ENVIRONMENT = "DEVELOPMENT";
	private static String DEV_END_POINT = "http://dev.thinqq.com:8080/qs/ws/ckt";
	private static String DEV_SECRET_KEY = "0IGpk3GTkNBEbO8v8ei35J1vRY9v7WR1";
	public static RestClient getRestClient(){
		RestClient client = new RestClient();
		return client;
	}
	
	public static String getRawEndpoint(String env){
		if(DEV_ENVIRONMENT.equals(env)){
			return DEV_END_POINT;
		}
		return DEV_END_POINT;
	}
	public static Resource getLoginResource(String env){
		Resource resource = getRestClient().resource(getRawEndpoint(env)+"/login");
		setCommonHeaders(env, resource);
		return resource;
	}
	public static Resource getLogoutResource(String env){
		Resource resource = getRestClient().resource(getRawEndpoint(env)+"/logout");
		setCommonHeaders(env, resource);
		return resource;
	}

	private static void setCommonHeaders(String env, Resource resource) {
		resource.accept("application/json");
		resource.contentType("application/json");
		if(DEV_ENVIRONMENT.equals(env)) {
			resource.header("SECRET_KEY", DEV_SECRET_KEY);
		} else {
			resource.header("SECRET_KEY", DEV_SECRET_KEY);
		}
	}
}
