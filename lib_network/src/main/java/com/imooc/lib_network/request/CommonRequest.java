package com.imooc.lib_network.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CommonRequest {

    public static Request createPostRequest(String url, RequestParams params)
    {
        return createPostRequest(url, params, null);
    }

    /**
     * 对外创建post请求对象
     *
     * @param url     url路径
     * @param params  参数
     * @param headers 请求头
     * @return 请求实体
     */
    public static Request createPostRequest(String url, RequestParams params, RequestParams headers) {
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                // 参数遍历
                mFormBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                // 请求头遍历
                mHeadersBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        return new Request.Builder()
                .url(url)
                .headers(mHeadersBuilder.build())
                .post(mFormBodyBuilder.build())
                .build();
    }

    public static Request createGetRequest(String url, RequestParams params) {
        return createGetRequest(url, params, null);
    }

    /**
     * 可以带请求头的GET请求
     *
     * @param url     url路径
     * @param params  请求参数
     * @param headers 请求头
     * @return 请求实体
     */
    public static Request createGetRequest(String url, RequestParams params, RequestParams headers) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                // 参数遍历
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                // 请求头遍历
                mHeadersBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        return new Request.Builder()
                .url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .headers(mHeadersBuilder.build())
                .get()
                .build();
    }


    /**
     * 文件上传请求
     *
     * @return
     */
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    public static Request createMultiPostRequest(String url, RequestParams params) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }

        return new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
    }
}
