package com.android.socialalert.webservice;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.android.socialalert.settings.ApplicationSettingsFilePath;

/** Common class to connect to https webservice and receives result */
public class HttpsConnection {

    public static String CallHttpsWebServiceJsonParam(String URL, String functionName, String jsonParameter)
            throws Exception, Error {
        StringEntity entity = new StringEntity(jsonParameter, HTTP.UTF_8);
        entity.setContentType("application/json");
        return executeHttpRequest(URL, functionName, entity);
    }

    public static String executeHttpRequestJson(String URL, String functionName, HttpEntity entity) throws Exception,
            Error {
        String output = null;
        HttpParams httpParameters = new BasicHttpParams();
        httpParameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        int timeoutConnection = 6000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 300000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        HttpClient httpclient = null;
        HttpPost httppost = null;
        HttpResponse response = null;

        try {
            httpclient = new MyHttpClient(httpParameters);
            // Url + method_name
            httppost = new HttpPost(URL + "/" + functionName);

            httppost.setEntity(entity);
            response = httpclient.execute(httppost);
            output = getStringFromHttpResponse(response);
        } catch (Exception ex) {
            Log.e("executeHttpRequestJson", ex.getMessage());
            throw ex;

        } catch (Error er) {
            throw er;
        } finally {

            // clean up connection
            if (response != null) {
                HttpEntity enty = response.getEntity();
                if (enty != null) {
                    enty.consumeContent();
                }
            }

            httpclient.getConnectionManager().shutdown();
            httppost.abort();
        }
        return output;
    }

    public static String executeHttpRequest(String URL, String functionName, HttpEntity entity) throws Exception, Error {
        String output = null;
        HttpParams httpParameters = new BasicHttpParams();
        httpParameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        int timeoutConnection = 6000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 300000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        HttpClient httpclient = null;
        HttpPost httppost = null;
        HttpResponse response = null;

        try {
            httpclient = new MyHttpClient(httpParameters);
            // Url + method_name
            httppost = new HttpPost(URL + "/" + functionName);
            httppost.setEntity(entity);
            response = httpclient.execute(httppost);
            output = getStringFromHttpResponse(response);
        } catch (Exception ex) {
            if (ex != null) {
                ex.printStackTrace();
            }
            throw ex;

        } catch (Error er) {
            throw er;
        } finally {

            // clean up connection
            if (response != null) {
                HttpEntity enty = response.getEntity();
                if (enty != null) {
                    enty.consumeContent();
                }
            }

            httpclient.getConnectionManager().shutdown();
            httppost.abort();
        }
        return output;
    }

    private static String executeHttpRequestGetMethod(String URL, String functionName, String emailAddress)
            throws Exception, Error {

        String output = null;
        HttpParams httpParameters = new BasicHttpParams();
        httpParameters.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        int timeoutConnection = 6000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 300000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        HttpClient httpclient = null;
        HttpGet httpGet = null;
        HttpResponse response = null;

        try {
            httpclient = new MyHttpClient(httpParameters);
            // Url + method_name
            httpGet = new HttpGet(URL + "/" + functionName + "?email=" + emailAddress + "&confirmed=false&"
                    + "cancelled=false&" + "localeId=" + ApplicationSettingsFilePath.language);

            HttpResponse httpResponse = httpclient.execute(httpGet);
            output = getStringFromHttpResponse(httpResponse);

        } catch (Exception ex) {
            Log.e("", ex.getMessage());
            throw ex;
        } catch (Error er) {
            throw er;
        } finally {

            // clean up connection
            if (response != null) {
                HttpEntity enty = response.getEntity();
                if (enty != null) {
                    enty.consumeContent();
                }
            }

            httpclient.getConnectionManager().shutdown();
            httpGet.abort();
        }
        return output;

    }

    public static String CallHttpsWebServiceGet(String URL, String functionName, String emailAddress) throws Exception,
            Error {
        return executeHttpRequestGetMethod(URL, functionName, emailAddress);
    }

    private static String getStringFromHttpResponse(HttpResponse response) {
        String result = "";
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream;
        try {
            inputStream = response.getEntity().getContent();

            int contentLength = (int) response.getEntity().getContentLength();
            if (contentLength < 0) {
            } else {
                byte[] data = new byte[512];
                int len = 0;

                while (-1 != (len = inputStream.read(data))) {
                    // converting to string and appending to stringbuffer…
                    buffer.append(new String(data, 0, len));
                }
                inputStream.close(); // closing the stream…..
                result = buffer.toString(); // converting stringbuffer to
                                            // string…..
            }

            return result;

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
