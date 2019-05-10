package com.example.sqltest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by T016 on 2019/4/1.
 */
public class HttpUtil {
    public static CloseableHttpResponse jsonPost(String url, Map<String, String> param, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setHeader("Content-Type", "application/json");
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if(null != param && param.size() > 0) {
            JSONObject requestBody = JSONObject.parseObject(JSON.toJSONString(param));
            StringEntity entity = new StringEntity(requestBody.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);
        }
        CloseableHttpResponse response = client.execute(post);
        return response;
    }

    public static CloseableHttpResponse jsonPost(String url, JSONObject param, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setHeader("Content-Type", "application/json");
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if(null != param && param.size() > 0) {
            StringEntity entity = new StringEntity(param.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);
        }
        CloseableHttpResponse response = client.execute(post);
        return response;
    }


    public static CloseableHttpResponse get(String url, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = client.execute(get);
        return response;
    }

    public static void jsonPutNotResponse(String url, Map<String, String> param, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpPut put = new HttpPut(url);
        put.setConfig(requestConfig);
        put.setHeader("Content-Type", "application/json");
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                put.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if(null != param && param.size() > 0) {
            JSONObject requestBody = JSONObject.parseObject(JSON.toJSONString(param));
            StringEntity entity = new StringEntity(requestBody.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            put.setEntity(entity);
        }
        client.execute(put);
    }

    public static void getNotResponse(String url, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        client.execute(get);
    }

    public static void jsonPostNotResponse(String url, Map<String, String> param, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setHeader("Content-Type", "application/json");
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if(null != param && param.size() > 0) {
            JSONObject requestBody = JSONObject.parseObject(JSON.toJSONString(param));
            StringEntity entity = new StringEntity(requestBody.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);
        }
        client.execute(post);
    }

    public static void jsonPostNotResponse(String url, JSONObject param, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setHeader("Content-Type", "application/json");
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if(null != param && param.size() > 0) {
            StringEntity entity = new StringEntity(param.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);
        }
        client.execute(post);
    }

    public static CloseableHttpResponse formPost(String url, Map<String, String> param, Map<String, String> header) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        if(null != header && header.size() > 0){
            for(Map.Entry<String, String> entry : header.entrySet()){
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        List<NameValuePair> nvps = new ArrayList();
        if(null != param && param.size() > 0) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        System.out.println(post.getEntity());
        CloseableHttpResponse response = client.execute(post);
        return response;
    }

    public static void closeResponse(CloseableHttpResponse response) throws IOException {
        try {
            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
    }

    public static String getCookie(CloseableHttpResponse response) throws IOException {
        Header[] headers = response.getAllHeaders();
        String cookie;
        if(null != headers){
            for (Header header : headers){
                if("Set-Cookie".equals(header.getName())){
                    cookie = header.getValue();
                    HttpUtil.closeResponse(response);
                    return cookie;
                }
            }
        }
        HttpUtil.closeResponse(response);
        return null;
    }
}
