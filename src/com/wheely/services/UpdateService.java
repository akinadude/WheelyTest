package com.wheely.services;

import java.util.Timer;
import java.util.TimerTask;
import com.wheely.activity.MainActivity;
import com.wheely.activity.MainActivity.LoadDataFromServerTask;
import com.wheely.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class UpdateService extends Service {

	private Handler mHandler;  
    private Timer mTimer;    
    private LoadDataFromServerTask mDataLoader;
       
 
    @Override
    public void onCreate() {
        if (mTimer != null)
            mTimer.cancel();
        else
            mTimer = new Timer();
        
        mHandler = new Handler();       
        mTimer.scheduleAtFixedRate(new UpdateDataFromServerTask(), Utils.UPDATE_INTERVAL, Utils.UPDATE_INTERVAL);       
    }
    
    
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private class UpdateDataFromServerTask extends TimerTask {		 
        @Override
        public void run() {       
            mHandler.post(new Runnable() { 
                @Override
                public void run() {
                	MainActivity mainActivityInstance = MainActivity.getInstance();
                	if (mainActivityInstance != null) {
	                	mDataLoader = MainActivity.getInstance().new LoadDataFromServerTask();
	                	mDataLoader.execute();
                	}
                }
            });
        }       
    }
}