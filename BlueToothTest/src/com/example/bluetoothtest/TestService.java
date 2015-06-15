package com.example.bluetoothtest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class TestService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new LocalBinder();
	}
	
	public class LocalBinder extends ITestInterface.Stub{

		@Override
		public int addFunction(int a, int b) throws RemoteException {
			// TODO Auto-generated method stub
			return a+b;
		}
		
	}

}
