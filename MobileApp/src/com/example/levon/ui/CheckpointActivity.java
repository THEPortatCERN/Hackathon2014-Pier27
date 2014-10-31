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
import com.example.levon.services.CheckpointDelegate;
import com.example.levon.services.CheckpointService;
import com.example.levon.services.HelicopterDelegate;
import com.example.levon.services.LogDelegate;

public class CheckpointActivity extends Activity {
	
	Button backBtn;
	CheckpointService checkpoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkpoint);
		checkpoint = new CheckpointService(this, logDelegate, checkpointDelegate);
		checkpoint.start();
		addBackButtonListener();
	}
	
	private LogDelegate logDelegate = new LogDelegate() {
		public void log(String msg) {
			TextView logTxt = (TextView) findViewById(R.id.checkpoint_log);
			logTxt.append("\n"+msg);
		}		
	};
	
	private CheckpointDelegate checkpointDelegate = new CheckpointDelegate() {
		@Override
		public void onAmbulanceDiscovered(boolean authentic, String message) {
			String authenticStr = (authentic) ? "Real ambulance" : "Fake ambulance";
			TextView ambulanceTxt = (TextView) findViewById(R.id.ambulance_detected);
			ambulanceTxt.setText(authenticStr + "\n" + message);
		}		
	};
	
    public void addBackButtonListener() {
     	 
		final Context context = this;
		backBtn = (Button) findViewById(R.id.btn_back_checkpoint);
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
		checkpoint.stop();
		checkpoint=null;
	}

}
