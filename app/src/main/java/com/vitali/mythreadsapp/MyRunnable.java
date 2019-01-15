package com.vitali.mythreadsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class MyRunnable implements Runnable {

    private WeakReference<Handler> mHandler;

    public MyRunnable(Handler uiHandler)
    {
        mHandler = new WeakReference<>(uiHandler);
    }

    @Override
    public void run()
    {
        Logger.logDebug();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.EXTRA_1, "Some Data");

        Message message = Message.obtain(null, Constants.MESSAGE_1);
        message.setData(bundle);

        mHandler.get().sendMessage(message);
    }
}
