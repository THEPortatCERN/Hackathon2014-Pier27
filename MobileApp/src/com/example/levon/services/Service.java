package com.example.levon.services;

import android.app.Activity;

public class Service {

	protected Activity activity;
	private LogDelegate log;

	public Service(Activity a, LogDelegate l) {
		activity = a;
		log = l;
	}

	protected void log(final String msg) {
		if (log != null) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					log.log(msg);
				}
			});
		}
	}

	public void stop() {
		log = null;
	}
}
