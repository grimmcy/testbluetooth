package com.example.bluetoothtest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mTextReceive;
    private TextView mTextSend;
    private Button mBtnScan;
    private Button mBtnSend;
    private ListView mListView;

    // ==============
    private BluetoothReceiver receiver;
    private BluetoothAdapter bluetoothAdapter;
    private List<String> devices;
    private Set<String> devicesSet = new HashSet<String>();
    private List<BluetoothDevice> deviceList;
    private Map<String, BluetoothDevice> mDevicesMap;
    // private Bluetooth client;
    private final String lockName = "BOLUTEK";
    private String message = "000001";
    private ArrayList<String> mData;
    
    private ITestInterface mInterface;
    private TestServiceConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initBlueTooth();
        mConnection = new TestServiceConnection();
        Intent intent = new Intent(this,TestService.class);
        this.bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    
    
    private void initWidget() {
        mTextReceive = (TextView) findViewById(R.id.am_receive);
        mTextSend = (TextView) findViewById(R.id.am_send);
        mBtnScan = (Button) findViewById(R.id.am_btn_scan);
        mBtnSend = (Button) findViewById(R.id.am_btn_send);
        mListView = (ListView) findViewById(R.id.am_list);
        mBtnScan.setOnClickListener(mOnClickListener);
        mBtnSend.setOnClickListener(mOnClickListener);

        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private void initBlueTooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!adapter.isEnabled()) {
            adapter.enable();
        }

        // 璁剧疆鎵嬫満鐨勮摑鐗欏彲瑙佹椂闂�
        Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600); // 3600涓鸿摑鐗欒澶囧彲瑙佹椂闂�
        startActivity(enable);

        deviceList = new ArrayList<BluetoothDevice>();
        mDevicesMap = new HashMap<String, BluetoothDevice>();
        devices = new ArrayList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BluetoothReceiver();
        registerReceiver(receiver, filter);

    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.am_btn_scan:
                bluetoothAdapter.startDiscovery();
                break;
            case R.id.am_btn_send:
                break;
            }

        }
    };

    private class BluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // if (isLock(device)) {
                // devices.add(device.getName());
                // }
                String address = device.getAddress();
                if (address != null) {
                    devices.add(device.getName());
                    devicesSet.add(device.getName() + address);
                    deviceList.add(device);
                    mDevicesMap.put(device.getName() + address, device);
                }
            }
            showDevices();
        }
    }

    private void showDevices() {
        mData = new ArrayList<String>(devicesSet);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mData);
        mListView.setAdapter(adapter);
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            // case Bluetooth.CONNECT_FAILED:
            // Toast.makeText(ComminuteActivity.this, "杩炴帴澶辫触",
            // Toast.LENGTH_LONG).show();
            // try {
            // client.connect(message);
            // } catch (Exception e) {
            // Log.e("TAG", e.toString());
            // }
            // break;
            // case Bluetooth.CONNECT_SUCCESS:
            // Toast.makeText(ComminuteActivity.this, "杩炴帴鎴愬姛",
            // Toast.LENGTH_LONG).show();
            // break;
            // case Bluetooth.READ_FAILED:
            // Toast.makeText(ComminuteActivity.this, "璇诲彇澶辫触",
            // Toast.LENGTH_LONG).show();
            // break;
            // case Bluetooth.WRITE_FAILED:
            // Toast.makeText(ComminuteActivity.this, "鍐欏叆澶辫触",
            // Toast.LENGTH_LONG).show();
            // break;
            // case Bluetooth.DATA:
            // Toast.makeText(ComminuteActivity.this, msg.arg1 + "",
            // Toast.LENGTH_LONG).show();
            // break;
            }
        }
    };

    protected void onDestroy() {
        unregisterReceiver(receiver);
        this.unbindService(mConnection);
        super.onDestroy();
    };

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BluetoothDevice device = mDevicesMap.get(mData.get(position));
            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {// 鍒ゆ柇缁欏畾鍦板潃涓嬬殑device鏄惁宸茬粡閰嶅
                
                Log.d("xxx","设备 "+device.getName()+" 未配对");
                try {
                    autoBond(device.getClass(), device,"10086");//璁剧疆pin鍊�
                    createBond(device.getClass(), device);
                    
                   int k =  mInterface.addFunction(3, 4);
                   Log.d("xxx","addFunction all is : "+k); 
                   
                    // remoteDevice = device;
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.d("xxx","设备 "+device.getName()+"朽对失败");
                }
            } else {
                // remoteDevice = device;
                Log.d("xxx","已经配对");
            }
        }
    };
    
    //鑷姩閰嶅璁剧疆Pin鍊� 
    static public boolean autoBond(Class btClass,BluetoothDevice device,String strPin) throws Exception {   
        Method autoBondMethod = btClass.getMethod("setPin",new Class[]{byte[].class});  
        Boolean result = (Boolean)autoBondMethod.invoke(device,new Object[]{strPin.getBytes()});  
        return result;  
    }  
  
//寮�閰嶅  
    static public boolean createBond(Class btClass,BluetoothDevice device) throws Exception {   
        Method createBondMethod = btClass.getMethod("createBond");   
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);   
        return returnValue.booleanValue();   
    }  
    
    //uuid:00001101-0000-1000-8000-00805F9B34FB
    
    private class TestServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			mInterface = ITestInterface.Stub.asInterface(arg1);
			if(mInterface != null){
                int k;
				try {
					k = mInterface.addFunction(3, 4);
	                Log.d("xxx","addFunction all is : "+k); 
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mInterface = null;
		}
    	
    }
}


