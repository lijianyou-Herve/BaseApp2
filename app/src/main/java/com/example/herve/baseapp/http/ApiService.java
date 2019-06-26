package com.example.herve.baseapp.http;


import com.herve.library.commonlibrary.bean.LoginResult;
import com.herve.library.httplibrary.Result;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Lijianyou on 2018/11/23 0023.
 *
 * @author Lijianyou
 */
public interface ApiService {

    //验证验证码
    @FormUrlEncoded
    @POST
    Observable<LoginResult> login(@Url String path, @FieldMap Map<String, String> paramsMap);

    //其中的query字段会被默认使用urlencode编码
    @GET
    Observable<String> getRecommed(@Url String path);

    //其中的query字段会被默认使用urlencode编码
    @GET
    Observable<Result> msgFilter(@Url String path, @QueryMap Map<String, String> map);
}
