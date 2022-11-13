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
     * Response Example: HTTP/1.1 200 OKConnection: keep-aliveContent-Type: application/json;charset=UTF-8Date: Sun, 13 Nov 2022 05:54:06 GMTX-B3-Spanid: 957d7cc3-6317-11ed-81ce-3b621d2e66a0X-B3-Traceid: 957d7cc4-6317-11ed-81ce-3b621d2e66a0Transfer-Encoding: chunked{"code":0,"msg":"success","data":{"requestId":"289c05bceda86e8778fa66a077379e4a","taskId":8052360}}
     * @param accessToken the access token for the generator
     * @param text the description for the picture
     * @param style the style of the picture
     * @throws IOException
     */
    public static void postPicGenRequest(String accessToken, String text, String style) throws IOException {
        String command = "curl -i -k \"https://wenxin.baidu.com/moduleApi/portal/api/rest/1.0/ernievilg/v1/txt2img?access_token="
                + accessToken + "\" --data-urlencode \"text=" + text + "\" --data-urlencode \"style=" + style + "\"";
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
     * Response Example: {"code":0,"msg":"success","data":"24.bee597810ec65bb9e5f924df7fc97207.86400000.1668405228512.6215629b7349715e67c5e0b92abd5960-136892"}
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
     * Response Example: HTTP/1.1 200 OKConnection: keep-aliveContent-Type: application/json;charset=UTF-8Date: Sun, 13 Nov 2022 05:41:29 GMTX-B3-Spanid: d21e092c-6315-11ed-afa2-432c2e4f8413X-B3-Traceid: d21e092d-6315-11ed-afa2-432c2e4f8413Transfer-Encoding: chunked{"code":0,"msg":"success","data":{"img":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/54047505d90f8f4f7716b5ebc02b2f8ei4","waiting":"0","imgUrls":[{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/54047505d90f8f4f7716b5ebc02b2f8ei4","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/54047505d90f8f4f7716b5ebc02b2f8eex","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/54047505d90f8f4f7716b5ebc02b2f8ea2","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/54047505d90f8f4f7716b5ebc02b2f8e30","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/54047505d90f8f4f7716b5ebc02b2f8e5q","score":null},{"image":"https://wenxin.baidu.com/younger/file/ERNIE-ViLG/54047505d90f8f4f7716b5ebc02b2f8ev9","score":null}],"createTime":"2022-11-13 13:20:42","requestId":"f83a05e96c10b8c879344a98eaf712eb","style":"油画","text":"睡莲","resolution":"1024*1024","taskId":8050204,"status":1}}
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
