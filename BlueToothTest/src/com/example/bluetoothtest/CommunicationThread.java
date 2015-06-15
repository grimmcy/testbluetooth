package com.example.bluetoothtest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class CommunicationThread extends Thread {

	BluetoothDevice mDevice;
	
	public CommunicationThread(BluetoothDevice device){
		mDevice = device;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		  try {
              Method m = mDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
              try {
            	  BluetoothSocket socket = (BluetoothSocket) m.invoke(mDevice, 1);
                  //_bluetooth.cancelDiscovery();
                  try {
                      socket.connect();
                  } catch (IOException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }
              } catch (IllegalArgumentException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              } catch (IllegalAccessException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              } catch (InvocationTargetException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          } catch (SecurityException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } catch (NoSuchMethodException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
	}
}
