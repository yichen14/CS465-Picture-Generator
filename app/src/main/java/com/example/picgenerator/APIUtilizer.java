package com.example.picgenerator;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIUtilizer {
    public static void postRequest(String apiKey, String secretKey) throws IOException {
        String accessToken = getAccessTokenFromAPI(apiKey, secretKey);
        URL url = new URL("https://wenxin.baidu.com/moduleApi/portal/api/rest/1.0/ernievilg/v1/txt2img?access_token=${access_token}");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }

    private static String getAccessTokenFromAPI(String apiKey, String secretKey) throws IOException {
        //执行 curl -XPOST "https://wenxin.baidu.com/moduleApi/portal/api/oauth/token?grant_type=client_credentials&client_id={your_ak}&client_secret={your_sk}" -H "Content-Type:application/x-www-form-urlencoded"
        URL url = new URL("https://wenxin.baidu.com/moduleApi/portal/api/oauth/token?grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setRequestProperty("Content-Length", "0");
        //将结果存入response
        BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        http.disconnect();
        ObjectMapper mapper = new ObjectMapper();
        AccessToken accessToken = mapper.readValue(response.toString(), AccessToken.class);
        String accessKey = accessToken.getData();
        return accessKey;
    }
}
