package com.vitali.mythreadsapp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Handler.Callback{

    private HandlerThread backgroundThread;
    private Handler backgroundHandler;
    private ThreadPoolExecutor mPoolExecutor;
    private ExecutorService mExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //----------------------------------------

        //startBackgroundThread();
        //backgroundHandler.post(new MyRunnable(new Handler(Looper.getMainLooper(), this)));

        //----------------------------------------

        //mPoolExecutor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        //runSomeOnBackgroundPool();

        //-----------------------------------------

        mExecutor = Executors.newSingleThreadExecutor();
        mExecutor.execute(new MyRunnable(new Handler(this)));
    }

    private void runSomeOnBackgroundPool() {

        for (int i = 0; i < 10; i++)
        {
            mExecutor.submit(new MyRunnable(new Handler(this)));
        }

    }


    @Override
    protected void onStop() {
        super.onStop();

        if(mPoolExecutor != null) mPoolExecutor.shutdown();

        mExecutor.shutdown();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stopBackgroundThread();
    }

    private void stopBackgroundThread() {
        if (backgroundThread != null) {
            backgroundThread.quitSafely();
            try {
                backgroundThread.join();
                backgroundThread = null;
                backgroundHandler = null;
            }
            catch (InterruptedException e)
            {
                Logger.logError("TAG", e.toString());
            }
        }
    }
    private void startBackgroundThread() {
        backgroundThread = new HandlerThread("BackgroundTread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    //-----------------------------------
    // Handler.Callback - implementation
    //-----------------------------------
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what)
        {
            case Constants.MESSAGE_1:
                String data = (String) msg.getData().getSerializable(Constants.EXTRA_1);
                Logger.logDebug("TAG", data);

        }

        return false;
    }

}
