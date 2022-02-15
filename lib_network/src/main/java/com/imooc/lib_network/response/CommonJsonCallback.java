package com.imooc.lib_network.response;

import android.os.Handler;
import android.os.Looper;

import com.imooc.lib_network.exception.OkHttpException;
import com.imooc.lib_network.listener.DisposeDataHandle;
import com.imooc.lib_network.listener.DisposeDataListener;
import com.imooc.lib_network.utils.ResponseEntityToModule;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonJsonCallback implements Callback {

    /**
     * the logic layer exception, may alter in different app
     */
    protected final String EMPTY_MSG = "";

    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    private final DisposeDataListener mListener;
    private final Class<?> mClass;
    private final Handler mDeliveryHandler;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, IOException e) {
        mDeliveryHandler.post(() -> mListener.onFailure(new OkHttpException(NETWORK_ERROR, e)));
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(() -> handleRespose(result));
    }

    private void handleRespose(String result) {
        if (result == null || result.trim().equals("")) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {
            if (mClass == null) {
                mListener.onSuccess(result);
            } else {
                Object obj = ResponseEntityToModule.parseJsonToModule(result, mClass);
                if (obj != null) {
                    mListener.onSuccess(obj);
                } else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                }
            }
        } catch (Exception e) {
            mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
        }
    }
}
