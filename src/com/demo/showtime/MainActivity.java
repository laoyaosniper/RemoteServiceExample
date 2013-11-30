package com.demo.showtime;

import com.demo.showtime.R;
import com.demo.showtime.service.ITimeQueryService;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
  private static final String INTENT_BIND_TIME_QUERY = "com.demo.showtime.service.TimeQueryService";
  private static final String TAG = "MainActivity, PID=" + Process.myPid();
  
  //private Button disconnectButton;
  private Button queryButton;
  private TextView messageTextView;
  private TextView errorTextView;
  
  private ITimeQueryService mService;
  private int errorCount = 0;
  
  private ServiceConnection mConn = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mService = ITimeQueryService.Stub.asInterface(service);
      Log.d(TAG, TAG + " Service Connected.");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      mService = null;
      Log.d(TAG, TAG + " Service Disconnected.");
      // re-bind for uninstallation problem
      bindService(new Intent(INTENT_BIND_TIME_QUERY), mConn, Context.BIND_AUTO_CREATE);
    }

    
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, TAG + " onCreate()");
    
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    queryButton = (Button)findViewById(R.id.queryButton);
    messageTextView = (TextView)findViewById(R.id.messageTextView);
    errorTextView = (TextView)findViewById(R.id.errorTextView);

    bindService(new Intent(INTENT_BIND_TIME_QUERY), mConn, Context.BIND_AUTO_CREATE);

    queryButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Log.d(TAG, TAG + " Run function in Service.");
        try {
          messageTextView.setText(getString(R.string.time_message) + mService.getCurrentTime());
        } catch (RemoteException e) {
          Log.d(TAG, TAG + "Unexpected Remote Exception!");
          errorTextView.setText("Unexpected Remote Exception!");
        } catch (NullPointerException e1) {
          Log.d(TAG, TAG + "Null pointer! Service is disconnected");
          errorCount++;
          errorTextView.setText("Service Error No. " + errorCount);
          bindService(new Intent(INTENT_BIND_TIME_QUERY), mConn, Context.BIND_AUTO_CREATE);
        }

      }
    });
    
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  protected void onDestroy() {
    unbindService(mConn);
    Log.d(TAG, TAG + " onDestroy().");
    super.onDestroy();
  }
}
