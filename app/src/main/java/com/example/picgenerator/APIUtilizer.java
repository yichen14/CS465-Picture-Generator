package com.example.picgenerator;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIUtilizer {
    /**
     * Post picture generating request and return the response
     * @param accessToken the access token for the generator
     * @param text the description for the picture
     * @param style the style of the picture
     * @throws IOException
     */
    public static void postPicGenRequest(String accessToken, String text, String style) throws IOException {
        String command = "curl -i -k \"https://wenxin.baidu.com/moduleApi/portal/api/rest/1.0/ernievilg/v1/txt2img?access_token="
                + accessToken + "\" --data-urlencode \"text=" + text + "\" --data-urlencode \"style=" + style + " \"";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }

    /**
     * Get access token from the API
     * @param apiKey api key for the token
     * @param secretKey secret key for the token
     * @return the access token of the API
     * @throws IOException
     */
    public static String getAccessTokenFromAPI(String apiKey, String secretKey) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //执行 curl -XPOST "https://wenxin.baidu.com/moduleApi/portal/api/oauth/token?grant_type=client_credentials&client_id={your_ak}&client_secret={your_sk}" -H "Content-Type:application/x-www-form-urlencoded"
        String command = "curl -XPOST \"https://wenxin.baidu.com/moduleApi/portal/api/oauth/token?grant_type=client_credentials&client_id="
                + apiKey + "&client_secret=" + secretKey + "\" -H \"Content-Type:application/x-www-form-urlencoded\"";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //将结果存入response
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        AccessToken accessToken = mapper.readValue(response.toString(), AccessToken.class);
        //得到access token
        String accessKey = accessToken.getData();
        return accessKey;
    }

    /**
     * Check the status for the picture generation process
     * @param accessToken the access token for the API
     * @param taskId the task id for the generation
     * @throws IOException
     */
    public static void checkGenerationStatus(String accessToken, String taskId) throws IOException {
        String command = "curl -i -k \"https://wenxin.baidu.com/moduleApi/portal/api/rest/1.0/ernievilg/v1/getImg?access_token=" + accessToken + "\" --data-urlencode \"taskId="+ taskId + "\"";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }
}
