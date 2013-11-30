/**
 * 
 */
package com.demo.showtime.service;

import java.text.SimpleDateFormat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author Hongyi Yao
 *
 */
public class TimeQueryService extends Service {
  private static final String TAG = "TimeQueryService, PID=" + Process.myPid();
  
//  private final ITimeQueryService.Stub mBinder = new ITimeQueryService.Stub() {
//    private int mCount = 0;
//    @Override
//    public String getCurrentTime() throws RemoteException {
//      mCount++;
//      return new SimpleDateFormat(" hh:mm:ss").format(System.currentTimeMillis()) + ", get count " + mCount;
//    }
//  };

  /* (non-Javadoc)
   * @see android.app.Service#onBind(android.content.Intent)
   */
  @Override
  public IBinder onBind(Intent arg0) {
    return new ITimeQueryService.Stub() {
      private int mCount = 0;
      @Override
      public String getCurrentTime() throws RemoteException {
        ++mCount;
        return new SimpleDateFormat(" hh:mm:ss").format(System.currentTimeMillis()) + ", get count " + mCount;
      }
    };
  }
  
  @Override
  public void onCreate() {
      // TODO Auto-generated method stub
      Log.d(TAG, TAG + " onCreate()");
      super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG, TAG + " onStart()");
    return super.onStartCommand(intent, flags, startId);
  }
//  @Override
//  public void onStart(Intent intent, int startId) {
//      // TODO Auto-generated method stub
//      Log.d(TAG, TAG + " onStart()");
//      super.onStart(intent, startId);
//  }

  @Override
  public void onDestroy() {
      // TODO Auto-generated method stub
      Log.d(TAG, TAG + " onDestroy()");
      super.onDestroy();
  }
}
