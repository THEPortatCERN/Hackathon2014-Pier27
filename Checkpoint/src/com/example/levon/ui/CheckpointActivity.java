package com.example.levon.ui;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.levon.R;
import com.example.levon.services.CheckpointDelegate;
import com.example.levon.services.CheckpointService;
import com.example.levon.services.LogDelegate;
import com.example.levon.utils.AmbulanceInfo;

public class CheckpointActivity extends Activity {
	
	Button backBtn;
	CheckpointService checkpoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkpoint);
		checkpoint = new CheckpointService(this, logDelegate, checkpointDelegate);
		checkpoint.start();
		//addBackButtonListener();
	//	ImageView scanningImage = (ImageView) findViewById(R.id.scanning_image);
	  //  scanningImage.setBackgroundResource(R.drawable.pic_no_400ok);

	}
	
	private LogDelegate logDelegate = new LogDelegate() {
		public void log(String msg) {
			TextView logTxt = (TextView) findViewById(R.id.checkpoint_log);
			logTxt.append("\n"+msg);
		}		
	};
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    // TODO Auto-generated method stub
	    super.onWindowFocusChanged(hasFocus);
	    if (hasFocus) {
			 
			ImageView scanningImage = (ImageView) findViewById(R.id.scanning_image);
			 scanningImage.setBackgroundResource(R.anim.scanning);

			 AnimationDrawable scanningAnimation = (AnimationDrawable) scanningImage.getBackground();
			 scanningAnimation.start();
	    }
	}
	
	private CheckpointDelegate checkpointDelegate = new CheckpointDelegate() {
		@Override
		public void onAmbulanceDiscovered(boolean authentic, String message) {
			//Access the tables containing the ambulance details
			 TableLayout ambulanceDetailsOne = (TableLayout) findViewById(R.id.ambulance_details_one);
			 TableLayout ambulanceDetailsTwo = (TableLayout) findViewById(R.id.ambulance_details_two);
			 ImageView resultImage = (ImageView) findViewById(R.id.result_image);
			//If it is an authentic ambulance, show the info
			if(authentic) {
				//Make the tables visible
				ambulanceDetailsOne.setVisibility(View.VISIBLE);
				ambulanceDetailsTwo.setVisibility(View.VISIBLE);
				 //Change the image to a green check mark
				 ImageView scanningImage = (ImageView) findViewById(R.id.scanning_image);
				 AnimationDrawable scanningAnimation = (AnimationDrawable) scanningImage.getBackground();
				 scanningAnimation.stop();
				 scanningImage.setVisibility(View.GONE);
			     resultImage.setImageResource(R.drawable.pic_yes_400ok);
				 resultImage.setVisibility(View.VISIBLE);
				 scanningImage.setBackgroundResource(R.drawable.pic_yes_400ok);
				 //Then fill in the details of the ambulance
				 AmbulanceInfo ambulance = getAmbulanceInfoFromString(message);
				 TextView ambulanceTxt = (TextView) findViewById(R.id.driver_name);
				 ambulanceTxt.setText(ambulance.getDriverName());
				 ambulanceTxt = (TextView) findViewById(R.id.license_plate);
				 ambulanceTxt.setText(ambulance.getLicensePlate());
				 ambulanceTxt = (TextView) findViewById(R.id.organization);
				 ambulanceTxt.setText(ambulance.getOrganization());
				 ambulanceTxt = (TextView) findViewById(R.id.signed_by);
				 ambulanceTxt.setText(ambulance.getSignedBy());
				 TextView invalidText = (TextView) findViewById(R.id.fake_message);
				 invalidText.setVisibility(View.GONE);
			}
			else {
				//Make the tables invisible
				ambulanceDetailsOne.setVisibility(View.INVISIBLE);
				ambulanceDetailsTwo.setVisibility(View.INVISIBLE);
				 //Change the image to a failed cross mark
				 ImageView scanningImage = (ImageView) findViewById(R.id.scanning_image);
				 AnimationDrawable scanningAnimation = (AnimationDrawable) scanningImage.getBackground();
				 scanningAnimation.stop();
				 scanningImage.setVisibility(View.GONE);
				 resultImage.setImageResource(R.drawable.pic_no_400ok);
				 resultImage.setVisibility(View.VISIBLE);
				 TextView invalidText = (TextView) findViewById(R.id.fake_message);
				 invalidText.setVisibility(View.VISIBLE);
			}
		}		
	};
	
	public AmbulanceInfo getAmbulanceInfoFromString(String message){
		AmbulanceInfo ambulanceInfo = new AmbulanceInfo("", "", "", "");
		String[] split = message.split("\n");
		ambulanceInfo.setDriverName(split[0]);
		ambulanceInfo.setLicensePlate(split[1]);
		ambulanceInfo.setOrganization(split[2]);
		ambulanceInfo.setSignedBy(split[3]);
		return ambulanceInfo;
	}

	/*
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
	*/
	@Override
	protected void onDestroy(){
		super.onDestroy();
		checkpoint.stop();
		checkpoint=null;
	}

}
