package com.example.levon.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.levon.MainActivity;
import com.example.levon.R;
import com.example.levon.services.HelicopterDelegate;
import com.example.levon.services.HelicopterService;
import com.example.levon.services.LogDelegate;

public class HelicopterActivity extends Activity {

	Button backBtn;
	HelicopterService helicopter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helicopter);
		helicopter = new HelicopterService(this, logDelegate, helicopterDelegate);
		helicopter.start();
		addBackButtonListener();
	}
	
	private LogDelegate logDelegate = new LogDelegate() {
		public void log(String msg) {
			TextView logTxt = (TextView) findViewById(R.id.helicopter_log);
			logTxt.append("\n"+msg);
		}		
	};
	
	private HelicopterDelegate helicopterDelegate = new HelicopterDelegate() {
		@Override
		public void onHospitalDiscovered(boolean authentic, String message) {
			String authenticStr = (authentic) ? "Real hospital" : "Fake hospital";
			TextView hospitalTxt = (TextView) findViewById(R.id.hospital_detected);
			hospitalTxt.setText(authenticStr + "\n" + message);
		}		
	};
	
    public void addBackButtonListener() {
     	 
		final Context context = this;
		backBtn = (Button) findViewById(R.id.btn_back_helicopter);
		backBtn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);   
                finish();
 
			}
		});
 
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		helicopter.stop();
		helicopter = null;
	}
}
