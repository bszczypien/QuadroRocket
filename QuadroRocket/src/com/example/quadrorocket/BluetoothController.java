package com.example.quadrorocket;

import java.util.Set;
import java.util.Vector;

import BFramework.BEventDispatcher;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class BluetoothController extends BEventDispatcher implements IBluetoothImpl
{
	//events
	public static final String ENABLE_BLUETOOTH_REQUEST = "enable_bluetooth_request";
	
	// Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    private MainActivity _view;
    private ConnectionState _connectionState;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private CustomMessageHandler mHandler;
    
    private enum ConnectionState
    {
    	IDLE,
    	IS_CONNECTING,
    	CONNECTED,
    }
    
    public BluetoothController(MainActivity pView)
    {
    	_view = pView;
    	init();
    }
    
    /*
     * just check if adapter exists
     */
    private void init()
    {
    	mHandler = new CustomMessageHandler(this);
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if (mBluetoothAdapter == null) {
            Toast.makeText(_view, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            //finish();
            return;
        }
    }
    
    /*
     * turning on bluetooth if it's turned off
     * */
    public void onCustomStart()
    {
    	if (!mBluetoothAdapter.isEnabled()) 
    	{
    		Toast.makeText(_view, "Trying to enable bluetooth...", Toast.LENGTH_LONG).show();
    		_view.intentRequestEnableBT();
    		/*
    		Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, MainActivity.REQUEST_ENABLE_BT);
            */
    	}else
    	{
    		int lRequestCode = MainActivity.REQUEST_ENABLE_BT;
    		int lResultCode = Activity.RESULT_OK;
    		_view.onActivityResult(lRequestCode, lResultCode, null);
    	}
    }

	@Override
	public void write(String pMessageToBeSent) 
	{
		Log.i("DEBUG", "WRITE: " + pMessageToBeSent + " : " + mConnectedThread.isAlive());
		mConnectedThread.write(pMessageToBeSent.getBytes());
	}
	
	public void showInChatBox(String pMessage, int pMessageType)
	{
		if(pMessageType == CustomMessageHandler.MESSAGE_READ)
		{
			_view.addMessageToMessageBox(pMessage);
		}
	}



	@Override
	public int read() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public String [] getListOfActiveDevices() 
	{
		BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        /*
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
		*/
        Vector<String> lPairedDevices = new Vector<String>();
        if (pairedDevices.size() > 0) 
        {
            //findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) 
            {
            	lPairedDevices.add(device.getName() + "\n" + device.getAddress());
            }
        }
        
		return (String []) lPairedDevices.toArray();
	}


	/*
	 * execute to connect to the device
	 * */
	@Override
	public void connect(String pDevice, String pPin) 
	{
		_view.addInfoToMessageBox("connecting to: " + pDevice);
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(pDevice);
		Log.i("DEBUG", device.getName());
        // Attempt to connect to the device
        mConnectThread = new ConnectThread(device, true, this);
        mConnectThread.start();
	}
	
	public synchronized void showError(String pError)
	{
		_view.addInfoToMessageBox(pError);
	}
	
	public synchronized void startListening(BluetoothSocket socket, String socketType)
	{
		mConnectThread = null;
		Log.i("DEBUG", "startListening");
		mConnectedThread = new ConnectedThread(socket, socketType, mHandler);
        mConnectedThread.start();
        //showError("reciver is ready to use...");
        // Send the name of the connected device back to the UI Activity
        //Message msg = mHandler.obtainMessage(CustomMessageHandler.MESSAGE_DEVICE_NAME);
        //Bundle bundle = new Bundle();
        //bundle.putString(BluetoothChat.DEVICE_NAME, device.getName());
        //msg.setData(bundle);
        //mHandler.sendMessage(msg);
	}
	
	/*
	 * set connection and init chat
	 * */
	@Override
	public void setupChat() 
	{
		//hardcoded mac address of bluetooth device
		connect("00:12:6F:2F:3A:24", "");
	}
}
