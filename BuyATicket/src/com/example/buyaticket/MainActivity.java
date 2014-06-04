package com.example.buyaticket;


import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.Build;

public class MainActivity extends Activity {
	Spinner sTime;
	Button bBuyATicket;
	Button bResume;
	String[] spinnerText={"15 Mins","30 Mins","1 Hour","2 Hour"};
	long[] time={15,30,60,120};
	int selected=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setupViews();
		
		if (TimerService.ct!=null)
		{
			startNextActivity();
		}
	}

	private void setupViews()
	{
		sTime = (Spinner) findViewById(R.id.sTime);	
		ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, spinnerText);
		sTime.setAdapter(adapter);
		sTime.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selected=position;
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		bBuyATicket=(Button)findViewById(R.id.bBuyATicket);
		bBuyATicket.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(TimerService.ct!=null)
				{
					TimerService.ct.cancel();
					TimerService.ct=null;
				}
				startNextActivity();
				
			}
		});
		bResume=(Button)findViewById(R.id.bResume);
		bResume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startNextActivity();
				
			}
		});
		
	}
	
	private void startNextActivity()
	{
		Intent intent=new Intent(MainActivity.this,TimerActivity.class);
		intent.putExtra("TOTAL_TIME", time[selected]);
		startActivity(intent);
	}


}
