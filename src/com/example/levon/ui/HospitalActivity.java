package com.example.levon.ui;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.levon.MainActivity;
import com.example.levon.R;
import com.example.levon.services.HospitalService;
import com.example.levon.services.LogDelegate;

public class HospitalActivity extends Activity {

	Button backBtn;
	Button openPopUp;
	CheckBox fakeCheckBox;
	HospitalService hospital;
	TextView logTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital);
		hospital = new HospitalService(this, new LogDelegate() {
			public void log(String msg) {
				logTxt = (TextView) findViewById(R.id.hospital_log);
				logTxt.append("\n"+msg);
			}
		});
		hospital.start();
		addBackButtonListener();
		addFakeMsgCheckBoxListener();
		addFakeCertCheckBoxListener();
		addPopUpButtonListener();
	}
	
    public void addPopUpButtonListener() {
		openPopUp = (Button) findViewById(R.id.hospital_info);
		openPopUp.setOnClickListener(new Button.OnClickListener(){

			   @Override
			   public void onClick(View arg0) {
			    LayoutInflater layoutInflater 
			     = (LayoutInflater)getBaseContext()
			      .getSystemService(LAYOUT_INFLATER_SERVICE);  
			    View popupView = layoutInflater.inflate(R.layout.popup, null);  
			             final PopupWindow popupWindow = new PopupWindow(
			               popupView, 
			               LayoutParams.WRAP_CONTENT,  
			                     LayoutParams.WRAP_CONTENT);  
						 TextView popUpTxt = (TextView) popupView.findViewById(R.id.popup_info);
						 popUpTxt.setText(hospital.getMessage());
			             Button closePopUp = (Button)popupView.findViewById(R.id.dismiss_popup);
			             closePopUp.setOnClickListener(new Button.OnClickListener(){

						     @Override
						     public void onClick(View v) {
						      // TODO Auto-generated method stub
						      popupWindow.dismiss();
						     }});
						               
			             popupWindow.showAsDropDown(openPopUp, 50, -30);
			         
			   }});
	}
	
    public void addFakeMsgCheckBoxListener() {
 
		fakeCheckBox = (CheckBox) findViewById(R.id.check_fake_hospital_msg);
		fakeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				hospital.setUseFakeMessage(isChecked);
			}
		});
 
	}
    
    public void addFakeCertCheckBoxListener() {
    	 
		fakeCheckBox = (CheckBox) findViewById(R.id.check_fake_hospital_cert);
		fakeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				hospital.setUseFakeCertificate(isChecked);
			}
		});
 
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
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		hospital.stop();
		hospital=null;
	}

}
