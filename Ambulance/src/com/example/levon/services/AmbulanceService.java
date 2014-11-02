package com.example.levon.services;

import static com.example.levon.utils.BluetoothUtils.AMBULANCE_SERVICE_NAME;
import static com.example.levon.utils.BluetoothUtils.AMBULANCE_SERVICE_UUID;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.example.levon.actors.Ambulance;
import com.example.levon.actors.FakeAmbulance;
import com.example.levon.utils.BluetoothUtils;
import com.example.levon.utils.Challenge;
import com.example.levon.utils.Response;

public class AmbulanceService extends Service {

	private BluetoothAdapter adapter = null;
	private ReceiveThread thread = null;
	private boolean fakeMessage = false;
	private AmbulanceDelegate delegate;

	public AmbulanceService(Activity activity, LogDelegate log, AmbulanceDelegate d) {
		super(activity, log);
		delegate = d;
	}

	private Challenge read(BluetoothSocket socket) throws IOException {
		try {
			ObjectInputStream i = new ObjectInputStream(socket.getInputStream());
			String randomString = (String) i.readObject();
			return new Challenge(randomString);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void send(BluetoothSocket socket, Response response) throws IOException {
		ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
		o.writeObject(response.getChallengeSignature());
		o.writeObject(response.getMessage());
		o.writeObject(response.getSignature());
		o.writeObject(response.getCertificate());
		o.flush();
	}

	private class ReceiveThread extends Thread {
		private BluetoothServerSocket serverSocket;

		public void close() {
			try {
				serverSocket.close();
				serverSocket = null;
			} catch (IOException e) {
				// Ignore exceptions
			}
		}

		public void run() {
			try {
				serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(AMBULANCE_SERVICE_NAME,
						AMBULANCE_SERVICE_UUID);

				while (true) {
					BluetoothServerSocket ss = serverSocket;
					if (ss == null) {
						break;
					}

					log("Waiting for connection ...");
					BluetoothSocket socket = ss.accept();
					log("Connected");

					Challenge challenge = read(socket);
					log("Challenge received");

					if (fakeMessage)
						send(socket, FakeAmbulance.createFakeMessageResponse(challenge));
					else
						send(socket, Ambulance.createResponse(challenge));
					log("Response sent");

					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							delegate.onResponseSent(true);
						}
					});
					socket.close();
				}
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
		}
	}

	public void start() {
		adapter = BluetoothAdapter.getDefaultAdapter();

		if (adapter != null) {
			BluetoothUtils.beginDiscoverable(activity);
			thread = new ReceiveThread();
			thread.start();
		} else {
			log("ERROR: no bluetooth adapter found");
		}
	}

	public void stop() {
		super.stop();
		if (adapter != null) {
			BluetoothUtils.endDiscoverable(activity);
		}
		if (thread != null) {
			thread.close();
		}
	}

	public String getMessage() {
		return Ambulance.getMessage();
	}

	public void setUseFakeMessage(boolean fake) {
		this.fakeMessage = fake;
	}
	
	public boolean getUseFakeMessage() {
		return this.fakeMessage;
	}

}
