package com.example.levon.ui;

import com.example.levon.MainActivity;
import com.example.levon.R;
import com.example.levon.R.id;
import com.example.levon.R.layout;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class HospitalActivity extends Activity {

	Button backBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital);
		addBackButtonListener();
		
        final CheckBox checkBoxFakeMsg = (CheckBox) findViewById(R.id.check_fake_hospital_msg);
        if (checkBoxFakeMsg.isChecked()) {
        	checkBoxFakeMsg.setChecked(false);
        }
        final CheckBox checkBoxFakeCert = (CheckBox) findViewById(R.id.check_fake_hospital_cert);
        if (checkBoxFakeCert.isChecked()) {
        	checkBoxFakeCert.setChecked(false);
        }
	}
	
    public void addBackButtonListener() {
     	 
		final Context context = this;
		backBtn = (Button) findViewById(R.id.btn_back_hospital);
		backBtn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);   
                finish();
 
			}
		});
 
	}

}
