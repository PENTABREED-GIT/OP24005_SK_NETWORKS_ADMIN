package com.skn.admin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;

/**
 * Created by jinstech on 2021-03-16
 */
public class NTRestAPIUtil {
    private HttpsURLConnection httpsConnection;
    private HttpURLConnection httpConnection;

    private String statusCode;
    private String responseBody = "";
    private String requestBody;

    public String getRequestBody() {
        return this.requestBody;
    }
    public String getResponseBody() {
        return this.responseBody;
    }

    public Map<String, Object> getResponseBodyByMap() {
        Map<String, Object> resultBody = new Gson().fromJson(this.responseBody, Map.class);
        return resultBody;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public NTRestAPIUtil(String url, String method) {
        this(url, method, null);
    }

    public NTRestAPIUtil(String url, String method, Map<String, Object> requestBody) {
        // SSL
        if(url.toUpperCase().contains("HTTPS://")) {
            this.doHttpsConnect(url, method, requestBody);
        } else {
            this.doHttpConnect(url, method, requestBody);
        }
    }

    private void doHttpsConnect(String url, String method, Map<String, Object> requestBody) {

//        System.setProperty("jsse.enableSNIExtension", "false");
//        System.setProperty("https.protocols", "TLSv1.2");
        try {
            URL connURL = new URL(url.toString());
            //System.out.println("HTTPS CONNECT TO :::::::::::::::::::" + url.toString());
            SSLContext sc = SSLContext.getInstance("TLSv1.2");//"TLSv1.2"
            //System.setProperty("https.protocols", "TLSv1.2");
            sc.init(null, null, new SecureRandom());

            this.httpsConnection = (HttpsURLConnection) connURL.openConnection();
            this.httpsConnection.setSSLSocketFactory(sc.getSocketFactory());

            this.httpsConnection.setConnectTimeout(10000);
            //connection.setReadTimeout(30000);
            this.httpsConnection.setRequestMethod(method);
            if (requestBody != null) {
                this.httpsConnection.setDoOutput(true);
                this.httpsConnection.setRequestProperty("Content-Type", "application/json");
            }
            this.httpsConnection.setRequestProperty("Accept", "application/json");
            this.httpsConnection.setRequestProperty("AccessToken", "2dfb219e2e4cf9c21f4406230f9c0aab6092a33819e6fa55ef5e45f179df0006357e8cee4db627f556a1a20c9e7e5aae28bb0c0edde57b00b8dda90c75165090");
            //this.httpConnection.setRequestProperty("Authorization", "2dfb219e2e4cf9c21f4406230f9c0aab6092a33819e6fa55ef5e45f179df0006357e8cee4db627f556a1a20c9e7e5aae28bb0c0edde57b00b8dda90c75165090");

            ObjectMapper objectMapper = new ObjectMapper();
//            String body = null;

//            body = objectMapper.writeValueAsString(requestBody);

            if (requestBody != null) {
                    JSONObject jsonBody = new JSONObject();
                if(!method.equals("DELETE")) {
                    for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
                        jsonBody.put(entry.getKey(), entry.getValue());
                    }
                    OutputStreamWriter wr = new OutputStreamWriter(this.httpsConnection.getOutputStream());
                    wr.write(jsonBody.toString());
                    wr.flush();
                } else {
                    JSONArray jsonArray = new JSONArray();
                    //jsonArray = (JSONArray) requestBody.get("subscribers");
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(requestBody.get("subscribers"));
                    OutputStreamWriter wr = new OutputStreamWriter(this.httpsConnection.getOutputStream());
                    wr.write(jsonStr);
                    wr.flush();
                }
            }

            int responseCode = this.httpsConnection.getResponseCode();
            JSONObject responseJson = null;

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(this.httpsConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                //responseJson = new JSONObject(sb.toString());
                this.responseBody = sb.toString();
            } catch (Exception e) {
                System.out.println("NTRestAPIUtil Buffer Read Error ====================================");
                //System.out.println(url);
                e.printStackTrace();
            }

            this.statusCode = String.valueOf(responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doHttpConnect(String url, String method, Map<String, Object> requestBody) {
        try {
            URL connURL = new URL(url.toString());
            //System.out.println("HTTP CONNECT TO :::::::::::::::::::" + url.toString());
            SSLContext sc = SSLContext.getInstance("TLSv1.2");//"TLSv1.2"
            sc.init(null, null, new SecureRandom());

            this.httpConnection = (HttpURLConnection) connURL.openConnection();
            this.httpConnection.setConnectTimeout(10000);
            //connection.setReadTimeout(30000);
            this.httpConnection.setRequestMethod(method);
            if (requestBody != null) {
                this.httpConnection.setDoOutput(true);
                this.httpConnection.setRequestProperty("Content-Type", "application/json");
            }
            this.httpConnection.setRequestProperty("Accept", "application/json");
            this.httpConnection.setRequestProperty("Authorization", "2dfb219e2e4cf9c21f4406230f9c0aab6092a33819e6fa55ef5e45f179df0006357e8cee4db627f556a1a20c9e7e5aae28bb0c0edde57b00b8dda90c75165090");

            ObjectMapper objectMapper = new ObjectMapper();
//            String body = null;

//            body = objectMapper.writeValueAsString(requestBody);

            if (requestBody != null) {
                JSONObject jsonBody = new JSONObject();
                for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
                    jsonBody.put(entry.getKey(), entry.getValue());
                }

                OutputStreamWriter wr = new OutputStreamWriter(this.httpConnection.getOutputStream());
                wr.write(jsonBody.toString());
                wr.flush();
            }

            int responseCode = this.httpConnection.getResponseCode();
            JSONObject responseJson = null;

//            if(responseCode == 200) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(this.httpConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                //responseJson = new JSONObject(sb.toString());
                br.close();
                Map<String, String> resultBody = new Gson().fromJson(this.responseBody, Map.class);
                this.responseBody = sb.toString();

//            }
            } catch(Exception e) {
                e.printStackTrace();
            }
            this.statusCode = String.valueOf(responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
