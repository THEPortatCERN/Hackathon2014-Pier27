package com.example.levon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.levon.test.Test;
import com.example.levon.ui.CheckpointActivity;

public class MainActivity extends Activity {

	Button navigationBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addCheckpointButtonListener();

		//Test.selfTest();
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

}
