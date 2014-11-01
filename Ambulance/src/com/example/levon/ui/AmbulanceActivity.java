package com.example.levon.ui;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.levon.MainActivity;
import com.example.levon.R;
import com.example.levon.services.AmbulanceDelegate;
import com.example.levon.services.AmbulanceService;
import com.example.levon.services.LogDelegate;
import com.example.levon.utils.AmbulanceInfo;

public class AmbulanceActivity extends Activity {
	
	Button backBtn;
	CheckBox fakeCheckBox;
	Button openPopUp;
	AmbulanceService  ambulance;
	TextView logTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ambulance);
		ambulance = new AmbulanceService(this, logDelegate, ambulanceDelegate);
		ambulance.start();
		addBackButtonListener();
		//addFakeMsgCheckBoxListener();
		//addFakeCertCheckBoxListener();
		 TextView infoText = (TextView) findViewById(R.id.driver_name);
		 AmbulanceInfo ambulanceInfo = getAmbulanceInfoFromString(ambulance.getMessage());
		 infoText = (TextView) findViewById(R.id.driver_name);
		 infoText.setText(ambulanceInfo.getDriverName());
		 infoText = (TextView) findViewById(R.id.license_plate);
		 infoText.setText(ambulanceInfo.getLicensePlate());
		 infoText = (TextView) findViewById(R.id.organization);
		 infoText.setText(ambulanceInfo.getOrganization());
		 infoText = (TextView) findViewById(R.id.signed_by);
		 infoText.setText(ambulanceInfo.getSignedBy());
		 ImageView scanningImage = (ImageView) findViewById(R.id.scanning_image);
		 scanningImage.setBackgroundResource(R.drawable.pic_no_400ok);
		
	}
	

	
	private LogDelegate logDelegate = new LogDelegate() {
		public void log(String msg) {
			TextView logTxt = (TextView) findViewById(R.id.ambulance_log);
			logTxt.append("\n"+msg);
		}		
	};
	
	private AmbulanceDelegate ambulanceDelegate = new AmbulanceDelegate() {
		@Override
		public void onResponseSent(boolean responseSent) {
			//responseSent will control which image to show
			 ImageView scanningImage = (ImageView) findViewById(R.id.scanning_image);
			 scanningImage.setBackgroundResource(R.drawable.pic_yes_400ok);
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
	/*   public void addFakeMsgCheckBoxListener() {
		   
			fakeCheckBox = (CheckBox) findViewById(R.id.check_fake_ambulance_msg);
			fakeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					ambulance.setUseFakeMessage(isChecked);
				}
			});
	 
		}
	    
	    public void addFakeCertCheckBoxListener() {
	    	 
			fakeCheckBox = (CheckBox) findViewById(R.id.check_fake_ambulance_cert);
			fakeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					ambulance.setUseFakeCertificate(isChecked);
				}
			});
	 
		}
	*/
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
		ambulance.stop();
		ambulance=null;
	}

}
