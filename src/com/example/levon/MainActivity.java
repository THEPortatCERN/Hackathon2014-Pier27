package com.example.levon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.levon.ui.AmbulanceActivity;
import com.example.levon.ui.CheckpointActivity;
import com.example.levon.ui.HelicopterActivity;
import com.example.levon.ui.HospitalActivity;

public class MainActivity extends Activity {
	
	Button navigationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       addHospitalButtonListener();
       addHelicopterButtonListener();
       addCheckpointButtonListener();
       addAmbulanceButtonListener();
    }
    

    public void addHospitalButtonListener() {
    	 
		final Context context = this;
 
		navigationBtn = (Button) findViewById(R.id.btn_hospital);
		navigationBtn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, HospitalActivity.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
    public void addHelicopterButtonListener() {
   	 
		final Context context = this;
 
		navigationBtn = (Button) findViewById(R.id.btn_helicopter);
		navigationBtn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, HelicopterActivity.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
    public void addCheckpointButtonListener() {
   	 
		final Context context = this;
 
		navigationBtn = (Button) findViewById(R.id.btn_checkpoint);
		navigationBtn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, CheckpointActivity.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
    public void addAmbulanceButtonListener() {
   	 
		final Context context = this;
 
		navigationBtn = (Button) findViewById(R.id.btn_ambulance);
		navigationBtn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, AmbulanceActivity.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
    

}
