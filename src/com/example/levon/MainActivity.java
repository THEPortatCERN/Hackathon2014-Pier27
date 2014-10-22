package com.example.levon;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String SERVICE_NAME = "BluetoothTest";
	private static final UUID SERVICE_UUID = UUID.fromString("2fb33440-5a04-11e4-a33a-0002a5d5c51b");
	
	private String text = "";
	private TextView textView;
	
	private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btn_listen).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	listen();
            }
        });
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	send();
            }
        });
        textView = (TextView)findViewById(R.id.txt_result);
        
     // Create a BroadcastReceiver for ACTION_FOUND
        BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    addMsg("Discovered: " + device.getName() + " " + device.getAddress());
                    new ConnectThread(device).run();
                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter); // Don't forget to unregister during onDestroy
    }

    private void addMsg(String msg)
    {
    	text += "\n";
    	text += msg;
    	textView.setText(text);
    }
    
    private class ConnectThread extends Thread {
        private BluetoothDevice device;

        public ConnectThread(BluetoothDevice device)
        {
        	this.device = device;
        }
        
        public void run() {     
            try {
                BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(SERVICE_UUID);
                
                if (socket != null)
                {
                    // Cancel discovery because it will slow down the connection
                    adapter.cancelDiscovery();

                    socket.connect();
	                addMsg("Connected");
	                socket.getOutputStream().write("abc123".getBytes());
	                socket.close();
	                addMsg("Message sent");
                }
            } catch (IOException connectException) {}
        }
    }

    private void send()
    {
    	if (adapter.startDiscovery())
    	{
    		addMsg("Start discovery");
    		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					adapter.cancelDiscovery();
		    		addMsg("End discovery");
				}}, 30000);
    	}
    }
    
    private class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;
     
        public AcceptThread() {
        	try {
	            serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(SERVICE_NAME, SERVICE_UUID);
	            addMsg("Waiting for connection");
        	} catch(IOException e) {}
        }
     
        public void run() {
        	try {
	            BluetoothSocket socket = serverSocket.accept();
	            serverSocket.close();
	            addMsg("Connected");

	            byte[] buffer = new byte[16];
	            socket.getInputStream().read(buffer);
	            socket.close();
	            addMsg("Message received: " + new String(buffer));
        	} catch(IOException e) {}
        }
    }
    
    private void listen()
    {
    	// TODO: pass 0 for always discoverable in ambulances and hospitals
    	// TODO: add listener to re-enable discoverable if it is cancelled
    	
    	Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
		startActivity(discoverableIntent);
		
		new AcceptThread().run();
	}
}
