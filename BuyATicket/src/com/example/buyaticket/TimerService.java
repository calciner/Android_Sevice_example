package com.example.buyaticket;


import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class TimerService extends Service {

	public static final String KEY_TIME = "TIME";
	public static final String KEY_NOTIFICATION = "BUY_A_TICKET";
	public static CountDownTimer ct;
	long time;
	public TimerService() {
		super();
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {       
		super.onStartCommand(intent, flags, startId);
		Log.i("timer", "onStartCommand");
		startTimer(intent);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("timer", "onCreate");
	}

	private void startTimer(Intent intent)
	{
		if(intent==null)
			return;
		
		time =intent.getLongExtra("TOTAL_TIME", 39);
		time=time*60*1000;//timeInSeconds
	
		if(ct==null)
		{
			ct=new CountDownTimer(time, 1000) 
			{

				public void onTick(long millisUntilFinished)  
				{
					sendTime(millisUntilFinished / 1000);
				}

				public void onFinish() {
					ct=null;
					Intent intent = new Intent(KEY_NOTIFICATION);
					intent.putExtra(KEY_TIME, 0);
					intent.putExtra("IS_FINISHED", true);
					sendBroadcast(intent);
				}
			};
			
			ct.start();
		}
	}
	private void sendTime(long time) {
		Intent intent = new Intent(KEY_NOTIFICATION);
		intent.putExtra(KEY_TIME, time);
		sendBroadcast(intent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		if(ct!=null)
		{
			ct.cancel();
			ct=null;
		}
		super.onDestroy();
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
} 