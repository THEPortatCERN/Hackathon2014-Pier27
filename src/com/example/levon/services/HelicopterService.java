package com.example.levon.services;

import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_NAME;
import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_UUID;

import java.io.IOException;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.example.levon.actors.TrustedParty;
import com.example.levon.utils.BluetoothUtils;
import com.example.levon.utils.SignUtils;
import com.example.levon.utils.SignedMessage;

public class HelicopterService extends Service {

	private BluetoothAdapter adapter = null;
	private HelicopterDelegate delegate = null;

	public HelicopterService(Activity activity, LogDelegate log, HelicopterDelegate d) {
		super(activity, log);
		delegate = d;
	}

	private SignedMessage read(BluetoothSocket socket) throws IOException {
		try {
			ObjectInputStream i = new ObjectInputStream(socket.getInputStream());
			String message = (String) i.readObject();
			byte[] signature = (byte[]) i.readObject();
			return new SignedMessage(message, signature);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private class ReceiveThread extends Thread {
		private BluetoothServerSocket serverSocket;

		public void run() {
			try {
				serverSocket = adapter
						.listenUsingInsecureRfcommWithServiceRecord(
								HOSPITAL_SERVICE_NAME, HOSPITAL_SERVICE_UUID);
				log("Waiting for connection ...");
				BluetoothSocket socket = serverSocket.accept();
				serverSocket.close(); // TODO: continue listen for connections
				log("Connected");

				SignedMessage msg = read(socket);
				log("Message received");

				// TODO: post to UI thread
				delegate.onHospitalDiscovered(SignUtils.verify(
						msg.getMessage(), msg.getSignature(),
						TrustedParty.PUBLIC_KEY), msg.getMessage());
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
		}
	}

	public void start() {
		adapter = BluetoothAdapter.getDefaultAdapter();

		if (adapter != null) {
			BluetoothUtils.beginDiscoverable(activity);
			new ReceiveThread().start();
		} else {
			log("ERROR: no bluetooth adapter found");
		}
	}

	public void stop() {
		if (adapter != null) {
			BluetoothUtils.endDiscoverable(activity);
			// TODO: stop thread listening for messages
		}
	}
}
