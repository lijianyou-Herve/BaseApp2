package com.herve.library.httplibrary;


import com.herve.library.commonlibrary.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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
    Observable<LoginBean> login(@Url String path, @FieldMap Map<String, String> paramsMap);
}
