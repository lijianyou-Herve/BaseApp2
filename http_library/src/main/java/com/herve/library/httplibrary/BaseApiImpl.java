package com.herve.library.httplibrary;


import com.google.gson.Gson;
import com.herve.library.commonlibrary.bean.Result;
import com.herve.library.commonlibrary.utils.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseApiImpl implements BaseApi {
    private Retrofit retrofit;
    private static final int TIME_OUT = 15;
    protected Retrofit.Builder retrofitBuilder;
    protected OkHttpClient.Builder httpBuilder;

    public BaseApiImpl(String baseUrl) {
        retrofitBuilder = new Retrofit.Builder();
        httpBuilder = new OkHttpClient.Builder();
        //缓存
        httpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .addNetworkInterceptor();
//                .cache()
                .addInterceptor(new CommonInterceptor())
                .addInterceptor(new CreateInterceptor())
                .addInterceptor(getLoggerInterceptor());
        retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpBuilder.build())//添加应用拦截器，在response的时候被调用一次
                .baseUrl(baseUrl);
        retrofit = retrofitBuilder.build();
    }


    /**
     * 构建retroft
     *
     * @return Retrofit对象
     */
    @Override
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            //锁定代码块
            synchronized (BaseApiImpl.class) {
                if (retrofit == null) {
                    retrofit = retrofitBuilder.build(); //创建retrofit对象
                }
            }
        }
        return retrofit;

    }


    @Override
    public OkHttpClient.Builder setInterceptor(Interceptor interceptor) {
        return httpBuilder.addInterceptor(interceptor);
    }

    @Override
    public Retrofit.Builder setConverterFactory(Converter.Factory factory) {
        return retrofitBuilder.addConverterFactory(factory);
    }

    /**
     * 日志拦截器
     * 将你访问的接口信息
     *
     * @return 拦截器
     */
    private HttpLoggingInterceptor getLoggerInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                LogUtils.logNetResponse(message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    /**
     * 公共参数
     */
    private class CommonInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {

            //添加公共参数
            HashMap<String, String> commomParamsMap = new HashMap<>();
            commomParamsMap.put("baseKey01", "baseValue01");
            commomParamsMap.put("baseKey02", "baseValue02");
            //获取原先的请求
            Request originalRequest = chain.request();
            //重新构建url
            HttpUrl.Builder builder = originalRequest.url().newBuilder();
            LogUtils.logNetResponse("请求参数--------------");
            for (Map.Entry<String, String> entry : commomParamsMap.entrySet()) {
                LogUtils.logNetResponse(entry.getKey() + " : " + entry.getValue());
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            //如果是post请求的话就把参数重新拼接一下，get请求的话就可以直接加入公共参数了
            FormBody body = (FormBody) originalRequest.body();
            if (body != null) {
                for (int i = 0; i < body.size(); i++) {
                    LogUtils.logNetResponse(body.name(i) + " : " + body.value(i));
                    if (originalRequest.method().equals("POST")) {
                        builder.addQueryParameter(body.name(i), body.value(i));
                    }
                }
            }
            LogUtils.i("请求参数--------------");
            //新的url
            HttpUrl httpUrl = builder.build();
            Request request = originalRequest.newBuilder()
                    .method(originalRequest.method(), originalRequest.body())
                    .url(httpUrl).build();
            return chain.proceed(request);
        }
    }

    /**
     * 拦截token 失效，其他设备登陆
     */
    public class CreateInterceptor implements Interceptor {
        private static final int HTTP_CODE_ACCEPT = 200;

        // token 失效
        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            String url = chain.request().url().url().getFile();
            Response response = chain.proceed(chain.request());
            if (response.code() == HTTP_CODE_ACCEPT) {
                ResponseBody body = response.body();
                if (body != null) {
                    getTokenError(body, url);
                }
            }
            return response;
        }
    }

    /**
     * 转码
     */
    public class CodeInterceptor implements Interceptor {

        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if (request.method().toLowerCase().equals("get")) {
                String string = request.url().toString();
                string = string.replace("%20", " ");
                string = string.replace("%22", "\"");
                string = string.replace("%23", "#");
                string = string.replace("%25", "%");
                string = string.replace("%26", "&");
                string = string.replace("%28", "(");
                string = string.replace("%29", ")");
                string = string.replace("%2B", "+");
                string = string.replace("%2C", ",");
                string = string.replace("%2F", "/");
                string = string.replace("%3A", ":");
                string = string.replace("%3B", "<");
                string = string.replace("%3C", "<");
                string = string.replace("%3D", "=");
                string = string.replace("%3E", ">");
                string = string.replace("%3F", "?");
                string = string.replace("%40", "@");
                string = string.replace("%5C", "\\");
                string = string.replace("%7C", "|");

                Request newRequest = new Request.Builder()
                        .url(string)
                        .build();

                return chain.proceed(newRequest);
            } else {
                return chain.proceed(request);
            }

        }
    }

    /**
     * token 失效问题
     *
     * @param responseBody
     * @param url
     * @throws IOException
     */
    private void getTokenError(ResponseBody responseBody, String url) throws IOException {
        BufferedSource source = responseBody.source();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }

        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        if (charset != null) {
            Result baseBean = new Gson().fromJson(buffer.clone().readString(charset), Result.class);
            if (baseBean.getCode() == ApiException.TOKEN_ERROR) {
                throw new ApiException(ApiException.TOKEN_ERROR, url + " token error");
            }
        }
    }
}
