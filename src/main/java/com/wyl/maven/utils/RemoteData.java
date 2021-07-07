package com.wyl.maven.utils;

import okhttp3.*;

import java.io.IOException;

/**
 * TODO
 *
 * @author wangyulin
 * @date 2021/7/3 5:23 PM
 */
public class RemoteData {

    public static void main(String[] args) {
        String result = RemoteData.getForecast();
        System.out.println(result);
    }

    public static String getForecast() {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?id=1796989&APPID=2d4afe2fb3ec685b7437e2ec9b6e95be&units=metric&cnt=3";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try{
            Response response = call.execute();
            // System.out.println("get="+response.body().string());
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
