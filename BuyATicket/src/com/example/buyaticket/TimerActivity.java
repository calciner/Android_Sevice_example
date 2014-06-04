package com.example.buyaticket;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends Activity{
	TextView tvTimeRemaining;
	Button bStopTimer;
	long totalTime=0;
	Intent service;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer);
		setupViews();
		
		totalTime=getIntent().getLongExtra("TOTAL_TIME", 0);
		service = new Intent(this, TimerService.class);
		service.putExtra("TOTAL_TIME", totalTime);
		startService(service); 
	}
	
	private void setupViews()
	{
		tvTimeRemaining=(TextView)findViewById(R.id.tvTimeRemaining);
		bStopTimer=(Button)findViewById(R.id.bStopTimer);
		bStopTimer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(TimerService.ct!=null)
				{
					TimerService.ct.cancel();
					TimerService.ct=null;
				}
				stopService(service);
				finish();
			}
		});
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(receiver, new IntentFilter(TimerService.KEY_NOTIFICATION));
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}


	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			long time=intent.getLongExtra(TimerService.KEY_TIME, 0);
			long hour=time/60/60;
			long minute=time/60%60;
			long seconds=time%60;
			
			boolean isFinished=intent.getBooleanExtra("IS_FINISHED", false);
			if(isFinished)
				Toast.makeText(getApplicationContext(), "Timer Finished", Toast.LENGTH_SHORT).show();
						
			String sHour=String.format("%02d",hour);
			String sMinute=String.format("%02d",minute);
			String sSeconds=String.format("%02d",seconds);
			tvTimeRemaining.setText(sHour+":"+sMinute+":"+sSeconds);
		}
	};
}
