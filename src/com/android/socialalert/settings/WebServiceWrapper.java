package com.android.socialalert.settings;

import com.android.socialalert.webservice.HttpsWebService;

public class WebServiceWrapper extends HttpsWebService {

    public static void initializeWebServiceWrapper(String webServiceUrl) {
        URL = webServiceUrl;
    }

}
