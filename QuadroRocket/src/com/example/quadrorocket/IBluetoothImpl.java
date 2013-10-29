package com.example.quadrorocket;

import android.content.Intent;

public interface IBluetoothImpl 
{
	public void write(String pMessageToBeSent);
	public int read();
	public String [] getListOfActiveDevices();
	public void connect(String pDevice, String pPin);
	public void setupChat();
}
