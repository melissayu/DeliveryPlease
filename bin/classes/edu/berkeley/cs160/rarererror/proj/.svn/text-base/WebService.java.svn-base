package edu.berkeley.cs160.rarererror.proj;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
public class WebService{
 
    DefaultHttpClient httpClient;
    HttpContext localContext;
    private String ret;
 
    HttpResponse response = null;
    HttpPost httpPost = null;
    HttpGet httpGet = null;
    HttpPut httpPut = null;
    HttpDelete httpDelete = null;
    String webServiceUrl;
    
    public WebService(String serviceName){
        HttpParams myParams = new BasicHttpParams();
 
        HttpConnectionParams.setConnectionTimeout(myParams, 50000);
        HttpConnectionParams.setSoTimeout(myParams, 50000);
        httpClient = new DefaultHttpClient(myParams);
        localContext = new BasicHttpContext();
        webServiceUrl = serviceName;
 
    }
 
    public String webPost(JSONObject json) {
    	ret = null;
    	 
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
 
        httpPost = new HttpPost(webServiceUrl);
        response = null;
 
        StringEntity tmp = null;        
 
        //httpPost.setHeader("User-Agent", "SET YOUR USER AGENT STRING HERE");
 
        
        httpPost.setHeader("Content-Type", "application/json");
        
 
        try {
            tmp = new StringEntity(json.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("deliveryplease", "HttpUtils : UnsupportedEncodingException : "+e);
        }
 
        httpPost.setEntity(tmp);
 
        Log.d("deliveryplease", webServiceUrl + "?" + json.toString());
 
        try {
            response = httpClient.execute(httpPost,localContext);
 
            if (response != null) {
                ret = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            Log.e("deliveryplease", "HttpUtils: " + e);
        }
 
        return ret;
    }
    
    public String webPut(JSONObject json) {
    	ret = null;
    	 
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
 
        httpPut = new HttpPut(webServiceUrl);
        
        response = null;
 
        StringEntity tmp = null;        
 
        //httpPost.setHeader("User-Agent", "SET YOUR USER AGENT STRING HERE");
 
        
        httpPut.setHeader("Content-Type", "application/json");
        
 
        try {
            tmp = new StringEntity(json.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("deliveryplease", "HttpUtils : UnsupportedEncodingException : "+e);
        }
 
        httpPut.setEntity(tmp);
 
        Log.d("deliveryplease", webServiceUrl + "?" + json.toString());
 
        try {
            response = httpClient.execute(httpPut,localContext);
 
            if (response != null) {
                ret = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            Log.e("deliveryplease", "HttpUtils: " + e);
        }
 
        return ret;
    }
    
    public JSONObject webGet() {
    	String retstr = null;
        String getUrl = webServiceUrl;
 
        httpGet = new HttpGet(getUrl);
 
        try {
            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            Log.e("deliveryplease", e.getMessage());
        }
 
     // we assume that the response body contains the error message
        try {
            retstr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            Log.e("deliveryplease", e.getMessage());
        }
        
        JSONObject ret = null;
		try {
			ret = new JSONObject(retstr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        return ret;
    }
    

    public String webDelete() {
    	String retstr = null;
        String getUrl = webServiceUrl;
 
        httpDelete = new HttpDelete(getUrl);
 
        try {
            response = httpClient.execute(httpDelete);
        } catch (Exception e) {
            Log.e("deliveryplease", e.getMessage());
        }
 
     // we assume that the response body contains the error message
        try {
            retstr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            Log.e("deliveryplease", e.getMessage());
        }
        
        return retstr;
    }

}
