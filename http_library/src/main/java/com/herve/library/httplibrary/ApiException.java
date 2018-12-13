package com.herve.library.httplibrary;

public class ApiException extends RuntimeException {

    // 未知错误
    public static final int UNKNOWN = 10000;

    // token失效
    public static final int TOKEN_ERROR = -1000;

    // 解析错误
    public static final int PARSE_ERROR = 1001;

    // 网络错误
    public static final int NETWORD_ERROR = 1002;

    // 连接超时与连接失败统一为连接失败
    public static final int REQUEST_FILED = 1006;

    private int mErrorCode;
    private String displayMessage;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.mErrorCode = code;
    }

    public ApiException(String errorMessage) {
        super(errorMessage);
        displayMessage = errorMessage;
    }

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
        displayMessage = errorMessage;
    }

    /**
     * API是否强制下线
     *
     * @return 失败返回true, 成功返回false
     */
    public boolean isKinkedOut() {
        return (mErrorCode == -1000 && String.valueOf(displayMessage).toLowerCase().contains("token"));
    }

    public int getCode() {
        return mErrorCode;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg;
    }


}