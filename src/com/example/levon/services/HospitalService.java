package com.example.levon.services;

import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_UUID;

import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.levon.actors.FakeHospital;
import com.example.levon.actors.Hospital;
import com.example.levon.utils.SignedMessage;

public class HospitalService extends Service {

	private BluetoothAdapter adapter = null;
	private BroadcastReceiver receiver = null;

	private boolean fakeMessge = false;
	private boolean fakeCertificate = false;
	
	public HospitalService(Activity activity, LogDelegate log) {
		super(activity, log);
	}

	public void start() {
		adapter = BluetoothAdapter.getDefaultAdapter();

		if (adapter == null)
			log("ERROR: no bluetooth adapter found");
		else if (adapter.startDiscovery()) {
			log("discovering...");

			// Create a BroadcastReceiver for ACTION_FOUND
			receiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					String action = intent.getAction();
					if (BluetoothDevice.ACTION_FOUND.equals(action)) {
						BluetoothDevice device = intent
								.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
						log("Discovered: " + device.getName());
						new SendThread(device, fakeMessge, fakeCertificate).start();
					}
				}
			};
			// Register the BroadcastReceiver
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			activity.registerReceiver(receiver, filter); // Don't forget to
															// unregister during
															// onDestroy
		} else {
			adapter = null;
			log("ERROR: could not start discovery");
		}
	}

	public void stop() {
		if (adapter != null) {
			activity.unregisterReceiver(receiver);
			receiver = null;
			adapter.cancelDiscovery();
			adapter = null;
		}
	}

	public void setUseFakeMessage(boolean fake) {
		fakeMessge = fake;
	}

	public void setUseFakeCertificate(boolean fake) {
		fakeCertificate = fake;
	}

	private class SendThread extends Thread {
		private BluetoothDevice device;
		private boolean fakeMessage = false;
		private boolean fakeCertificate = false;

		public SendThread(BluetoothDevice device, boolean fakeMessage, boolean fakeCertificate) {
			this.device = device;
			this.fakeMessage = fakeMessage;
			this.fakeCertificate = fakeCertificate;
		}

		private void send(BluetoothSocket socket, SignedMessage message) throws IOException
		{
			ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
			o.writeObject(message.getMessage());
			o.writeObject(message.getSignature());
			o.close();
		}
		
		public void run() {
			try {
				BluetoothSocket socket = device
						.createInsecureRfcommSocketToServiceRecord(HOSPITAL_SERVICE_UUID);

				if (socket != null) {
					socket.connect();
					log("Connected to " + device.getName());

					if (fakeCertificate)
						send(socket, FakeHospital.getMessageSignedWithFakeCertificate());
					else if (fakeMessage)
						send(socket, FakeHospital.getFakeHospitalMessage());
					else
						send(socket, Hospital.getMessage());
										
					socket.close();
					log("Message sent to " + device.getName());
				}
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
		}
	}
}
