package com.example.levon.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.levon.MainActivity;
import com.example.levon.R;
import com.example.levon.services.AmbulanceService;

public class AmbulanceActivity extends Activity {
	
	Button backBtn;
	CheckBox fakeCheckBox;
	AmbulanceService  ambulance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ambulance);
		ambulance = new AmbulanceService(this, null);
		//ambulance.start();
		addBackButtonListener();
		addFakeMsgCheckBoxListener();
		addFakeCertCheckBoxListener();
		
	}
	
	   public void addFakeMsgCheckBoxListener() {
		   
			fakeCheckBox = (CheckBox) findViewById(R.id.check_fake_ambulance_msg);
			fakeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					//ambulance.setUseFakeMessage(isChecked);
				}
			});
	 
		}
	    
	    public void addFakeCertCheckBoxListener() {
	    	 
			fakeCheckBox = (CheckBox) findViewById(R.id.check_fake_ambulance_cert);
			fakeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					//ambulance.setUseFakeCertificate(isChecked);
				}
			});
	 
		}
	
    public void addBackButtonListener() {
      	 
		final Context context = this;
		backBtn = (Button) findViewById(R.id.btn_back_ambulance);
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
		//ambulance.stop();
		ambulance=null;
	}

}
