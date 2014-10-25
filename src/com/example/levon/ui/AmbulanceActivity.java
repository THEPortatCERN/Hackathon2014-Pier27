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

public class AmbulanceActivity extends Activity {
	
	Button backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ambulance);
		addBackButtonListener();
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

}
