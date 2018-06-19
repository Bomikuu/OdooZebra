package ph.com.justin.odoozebra;

/**
 * Created by irvin on 8/4/16.
 */

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class WebRequest {

    public static int testConnection = 10;
    public static InputStream is = null;
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;

    //Constructor with no parameter
    public WebRequest() {

    }

    /**
     * Making web service call
     *
     * @url - url to make request
     * @requestmethod - http request method
     */
    public String makeWebServiceCall(String url, int requestmethod) {
        return this.makeWebServiceCall(url, requestmethod, null);
    }

    /**
     * Making service call
     *
     * @url - url to make request
     * @requestmethod - http request method
     * @params - http request params
     */
    public String makeWebServiceCall(String urladdress, int requestmethod,
                                     HashMap<String, String> params) {
        URL url;
        String response = "";
        try {
            url = new URL(urladdress);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            if (requestmethod == POST) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            } else if (requestmethod == GET) {
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
            }

            Log.e("Request method: " , conn.getRequestMethod());


            if (params != null) {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first)
                        first = false;
                    else
                        result.append("&");

                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }

                writer.write(result.toString());
                writer.flush();
                writer.close();
                os.close();

            }

            int responseCode = conn.getResponseCode();

            Log.e("Response Code: " , Integer.toString(responseCode));

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

            Log.e("Odoo Zebra Response: " , response);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return response;
    }


    public static String makePostRequestAuthentication(String stringUrl, String payload,
                                                       Context context) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();
        try {
            uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //uc.setRequestProperty("Authorization","Bearer ${" + ModGlobal.access_token + "}");
            uc.setReadTimeout(60000);
            uc.setConnectTimeout(60000);
            uc.setRequestMethod("POST");
            uc.setDoInput(true);
            uc.setInstanceFollowRedirects(false);
            uc.connect();
            OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        uc.disconnect();
        return jsonString.toString();
    }

    public static String makePostRequest(String stringUrl, String payload,
                                         Context context) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();
        try {
            uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            uc.setRequestMethod("POST");
            uc.setReadTimeout(15000);
            uc.setConnectTimeout(15000);
            uc.setDoOutput(true);
            uc.setInstanceFollowRedirects(false);
            uc.connect();
            OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");

            Log.e("Result string: " , payload);

            writer.write(payload);
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        uc.disconnect();

        Log.e("Test :", jsonString.toString());

        return jsonString.toString();
    }

    public static String makePutRequest(String stringUrl, String payload,
                                        Context context) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();
        try {
            uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            uc.setRequestMethod("PUT");
            uc.setReadTimeout(15000);
            uc.setConnectTimeout(15000);
            uc.setDoOutput(true);
            uc.setInstanceFollowRedirects(false);
            uc.connect();
            OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");

            Log.e("Result string: " , payload);

            writer.write(payload);
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        uc.disconnect();

        Log.e("Test :", jsonString.toString());

        return jsonString.toString();
    }

    public static String makeDeleteRequest(String stringUrl,
                                           Context context) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();
        try {
            uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            uc.setRequestMethod("DELETE");
            uc.setReadTimeout(15000);
            uc.setConnectTimeout(15000);
            uc.setDoOutput(true);
            uc.setInstanceFollowRedirects(false);
            uc.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        uc.disconnect();

        Log.e("Test :", jsonString.toString());

        return jsonString.toString();
    }


    public String makeRefreshToken(String urladdress, int requestmethod, HashMap<String, String> params) {
        URL url;
        String response = "";
        try {
            url = new URL(urladdress);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if (requestmethod == POST) {
                conn.setRequestMethod("POST");
            } else if (requestmethod == GET) {
                conn.setRequestMethod("GET");
            }


            if (params != null) {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first)
                        first = false;
                    else
                        result.append("&");

                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }

                writer.write(result.toString());

                writer.flush();
                writer.close();
                os.close();
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
