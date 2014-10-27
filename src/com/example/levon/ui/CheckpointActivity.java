package com.example.levon.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.levon.MainActivity;
import com.example.levon.R;

public class CheckpointActivity extends Activity {
	
	Button backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkpoint);
		addBackButtonListener();
	}
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
}
