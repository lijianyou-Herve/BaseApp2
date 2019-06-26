package com.herve.library.commonlibrary.socketUtil;

import android.annotation.SuppressLint;

import com.herve.library.commonlibrary.utils.LogUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author by DELL
 * @date on 2018/1/15
 * @describe
 */

public class RxSocketManager {
    private static final String TAG = RxSocketManager.class.getSimpleName();

    private String host;//ip
    private int port;//端口号
    private long timeOut = 1500;
    private int tryCount = 0;//失败重试次数 默认0
    private int tryCreateCount;//
    private SocketType socketType /*= ISocket.SocketType.TCP*/;// TCP/UDP

    private Disposable monitorTask;//循环读取数据任务
    private List<Disposable> timeOutTaskList;//超时任务列表

    private ResultCallback callback;
    private OnSocketStatusListener onSocketStatusListener;

    private static volatile RxSocketManager rxSocketManager;
    private static ISocket socket;
    private static boolean isConnected;//是否已连接

    private RxSocketManager() {
        //callback = new ArrayList<>();//callback 可以是列表
    }

    /**
     * 获取单例 如有需要可自行实现DCL单例
     *
     * @return
     */
    public static RxSocketManager getInstance() {
        if (rxSocketManager == null) {
            synchronized (RxSocketManager.class) {
                if (rxSocketManager == null) {
                    rxSocketManager = new RxSocketManager();
                }
            }
        }
        return rxSocketManager;
    }

    /**
     * @param socketType socket类型 TCP/UDP
     * @param host       ip
     * @param port       端口
     * @return
     */
    public RxSocketManager setClient(SocketType socketType, String host,
            int port) {
        this.socketType = socketType;
        this.host = host;
        this.port = port;
        return this;
    }

    //    public RxSocketManager setClientType(ISocket.SocketType socketType) {
    //        this.socketType = socketType;
    //        return this;
    //    }

    /**
     * 设置读取超时时间 超过该时间后，没有返回数据则视为超时 或者视为断开连接 （视具体业务逻辑调整）
     *
     * @param timeOut
     * @return
     */
    public RxSocketManager setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    /**
     * 设置socket创建失败 重试次数
     *
     * @param tryCount
     * @return
     */
    public RxSocketManager setTryCount(int tryCount) {
        this.tryCount = tryCount;
        return this;
    }

    /**
     * 返回结果监听
     *
     * @param callback
     * @return
     */
    public RxSocketManager setResultCallback(ResultCallback callback) {
        this.callback = callback;

        if (!isConnected()) {
            // TODO: 2019/3/8  可以用于重连
            //            setClient(SocketType.TCP, HostUrl.IP_TCP, HostUrl.IP_TCP_PORT)
            //                    .setSocketStatusListener(new RxSocketManager.OnSocketStatusListener() {
            //                        @Override
            //                        public void onConnectSucceed() {
            //                            L.d("------------onConnectSucceed");
            //                            ToastUtil.s("已连接");
            //                        }
            //
            //                        @Override
            //                        public void onConnectFailed() {
            //                            L.d("------------onConnectFailed");
            //                            ToastUtil.s("连接失败");
            //                        }
            //
            //                        @Override
            //                        public void onDisConnected() {
            //                            L.d("------------onDisConnected");
            //                            ToastUtil.s("已断开连接");
            //                        }
            //                    }).build();
        }

        return this;
    }

    /**
     * socket 状态监听
     *
     * @param onSocketStatusListener
     * @return
     */
    public RxSocketManager setSocketStatusListener(
            OnSocketStatusListener onSocketStatusListener) {
        this.onSocketStatusListener = onSocketStatusListener;
        return this;
    }

    /**
     * 移除状态监听
     *
     * @return
     */
    public RxSocketManager removeSocketStatusListener() {
        this.onSocketStatusListener = null;
        return this;
    }

    /**
     * 创建
     */
    public void build() {
        initSocket();
    }

    private void initSocket() {
        tryCreateCount = tryCount;
        createSocket();
        timeOutTaskList = new ArrayList<>();
    }

    @SuppressLint("CheckResult")
    private void createSocket() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(
                    ObservableEmitter<Boolean> e) throws Exception {
                switch (socketType) {
                    case TCP:
                        LogUtils.d(TAG, "initSocket  create TCPClient");
                        socket = new TCPClient(host, port);
                        break;
                    case UDP:
                        LogUtils.d(TAG, "initSocket  create UDPClient");
                        socket = new UDPClient(host, port);
                        break;
                }

                e.onNext(true);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (monitorTask == null || monitorTask.isDisposed()) {
                            monitorTask = RxSocketManager.this.createMonitorTask();
                        }
                        if (socket != null) {
                            isConnected = true;
                            if (onSocketStatusListener != null) {
                                onSocketStatusListener.onConnectSucceed();
                            }
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //创建连接失败重试
                        if (tryCreateCount == 0) {
                            isConnected = false;
                            tryCreateCount = tryCount;
                            throwable.printStackTrace();
                            if (onSocketStatusListener != null) {
                                onSocketStatusListener.onConnectFailed();
                            }
                        } else {
                            tryCreateCount--;
                            RxSocketManager.this.createSocket();
                        }
                    }
                });
    }

    public synchronized Disposable send(byte[] data) {
        return send(data, false);
    }

    /**
     * 发送数据
     *
     * @param data
     * @param isTimeout 该请求是否启用超时
     * @return
     */
    public synchronized Disposable send(final byte[] data,
            final boolean isTimeout) {

        // TODO: 2019/2/21 发送前是否需要判断当前连接状态
        //        if (!RxSocketManager.getInstance().isConnected()) {
        //            EventBus.getDefault().post("", EventBusTag.TAG_RECONNECT_SOCKET);
        //        }

        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(
                    ObservableEmitter<Boolean> e) throws Exception {
                if (socket != null) {
                    if (socketType == SocketType.TCP) {
                        if (RxSocketManager.this.isConnected()) {
                            socket.send(data);
                            e.onNext(true);
                        } else {
                            e.onNext(false);
                        }
                    } else if (socketType == SocketType.UDP) {
                        socket.send(data);
                        e.onNext(true);
                    }

                } else e.onNext(false);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                               @Override
                               public void accept(Boolean aBoolean) throws Exception {
                                   if (aBoolean) {
                                       LogUtils.d(TAG, "Send Succeed...");
                                       if (isTimeout)
                                           RxSocketManager.this.createTimeOutTask();
                                   } else {
                                       LogUtils.d(TAG, "Send Failed...");

                                       //dispatchFailed(new Throwable("Send Failed"));
                                       if (callback != null) {
                                           callback.onFailed(new Throwable("Send Failed"));
                                       }
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                LogUtils.d(TAG, "Send Failed...");
                                //dispatchFailed(throwable);
                                if (callback != null) {
                                    callback.onFailed(throwable);
                                }
                            }
                        });
    }

    /**
     * 创建超时 计时任务
     */
    private void createTimeOutTask() {
        Disposable disposable = Observable.timer(timeOut, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        /*** 这里需要根据业务逻辑 调整 是返回连接超时
                         * 还是数据获取失败 {@link ResultCallback#onFailed(Throwable)}
                         * */
                        if (onSocketStatusListener != null) {
                            onSocketStatusListener.onDisConnected();
                        }
                    }
                });
        timeOutTaskList.add(disposable);
    }

    /**
     * 取消超时
     */
    private synchronized void disposeTimeOut() {
        if (timeOutTaskList.isEmpty()) return;
        Disposable disposable = timeOutTaskList.remove(0);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 接收数据
     *
     * @return
     */
    private Disposable createMonitorTask() {
        return Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(
                    ObservableEmitter<byte[]> e) throws Exception {
                LogUtils.d(TAG, "createMonitorTask");

                while (socket != null) {
                    try {
                        byte[] rec = socket.receive();
                        if (rec != null) {
                            e.onNext(rec);
                        }
                    } catch (Throwable t) {
                        RxSocketManager.this.close();
                        e.onError(t);
                    }
                }
            }
        })
                .subscribeOn(Schedulers.computation())//有界的线程池
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] bytes) throws Exception {
                        LogUtils.d("Socket rec== ", new String(bytes));
                        RxSocketManager.this.disposeTimeOut();//
                        //dispatchSucceed(bytes);
                        //  L.d("callback != null  == " + (callback != null) + "当前 RxSocket " + toString());

                        if (callback != null) {
                            callback.onSucceed(bytes);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        RxSocketManager.this.disposeTask();
                        //throwable.printStackTrace();
                        LogUtils.d("Socket 断开连接  :" + throwable.getMessage());
                        if (throwable instanceof SocketTimeoutException) {
                            if (onSocketStatusListener != null) {
                                onSocketStatusListener.onConnectFailed();
                            }
                        } else if (throwable instanceof SocketException) {
                            if (onSocketStatusListener != null) {
                                onSocketStatusListener.onDisConnected();
                            }
                        } else {
                            if (onSocketStatusListener != null) {
                                onSocketStatusListener.onDisConnected();
                            }
                        }/* else {
                        if (callback != null) {
                            callback.onFailed(throwable);
                        }
                    }*/
                    }
                });
    }

    /**
     * callback 如果是列表 可以纷发结果
     */
    //    private void dispatchSucceed(byte[] bytes) {
    //        for (int i = 0; i < callback.size(); i++) {
    //            callback.get(i).onSucceed(bytes);
    //        }
    //    }
    //
    //    private void dispatchFailed(Throwable throwable) {
    //        for (int i = 0; i < callback.size(); i++) {
    //            callback.get(i).onFailed(throwable);
    //        }
    //    }
    public ISocket getSocket() {
        return socket;
    }

    public SocketType getSocketType() {
        return socketType;
    }

    /**
     * 关闭socket 释放资源
     */
    public void close() {
        LogUtils.d(TAG, "close");
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            isConnected = false;
            rxSocketManager = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disposeTask() {
        if (monitorTask != null && !monitorTask.isDisposed()) {
            monitorTask.dispose();
            monitorTask = null;
        }
    }

    /**
     * 是否已经连接 系统给的不是很准，请以状态监听接口返回为准
     *
     * @return
     */
    public boolean isConnected() {
        return isConnected;
        //        if (socket != null && !socket.isClosed() && socket.isConnected()) {
        //            return true;
        //        } else return false;
    }


    public interface OnSocketStatusListener {
        /**
         * 连接成功
         */
        void onConnectSucceed();

        /**
         * 连接失败
         */
        void onConnectFailed();

        /**
         * 断开连接
         */
        void onDisConnected();
    }

    /**
     * 结果返回
     */
    public interface ResultCallback {

        /**
         * 成功返回
         *
         * @param data
         */
        void onSucceed(byte[] data);

        /**
         * 失败
         *
         * @param t
         */
        void onFailed(Throwable t);
    }

    public static abstract class SimpleResultCallback implements ResultCallback {

        @Override
        public void onFailed(Throwable t) {

        }
    }

}
