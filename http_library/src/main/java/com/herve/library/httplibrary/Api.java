package com.herve.library.httplibrary;

import android.util.ArrayMap;

/**
 * 对应多个host的retrofit
 * Created by admin on 2017/10/18.
 */

public class Api extends BaseApiImpl {

    //满足下面两个条件我们可以使用SparseArray代替HashMap：
    //数据量不大，最好在千级以内
    //key必须为int类型，这中情况下的HashMap可以用SparseArray代替：
    private static ArrayMap<String, Api> sRetrofitManager = new ArrayMap<>();

    //构造方法私有
    private Api(String baseUrl) {
        super(baseUrl);
    }

    public static ApiService getService_C() {
        return getService(ApiConstant.BASE_URL_SERVER_C);
    }

    public static ApiService getService_PHP() {
        return getService(ApiConstant.BASE_URL_SERVER_PHP);
    }


    private static ApiService getService(String baseUrl) {
        Api api = sRetrofitManager.get(baseUrl);
        if (api == null) {
            api = new Api(baseUrl);
            sRetrofitManager.put(baseUrl, api);
        }
//        return api.getRetrofit().create(ApiService.class);
        return api.getRetrofit().create(ApiService.class);
    }


}
