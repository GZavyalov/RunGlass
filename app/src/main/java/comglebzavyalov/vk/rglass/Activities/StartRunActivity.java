package comglebzavyalov.vk.rglass.Activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import comglebzavyalov.vk.rglass.R;

import static android.provider.Settings.Global.DEVICE_NAME;

public class StartRunActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    String final_result = "";

    private final String DEVICE_NAME="scLamp";
    //private final String DEVICE_ADDRESS="98:D3:31:70:4E:CF";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    Button startButton, sendButton,clearButton,stopButton;
    TextView textView;
    EditText editText;
    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_run);

        connectGlass();

        BTinit();
        BTconnect();




    }

    public void clickStartRun(View view){
        Toast.makeText(getApplicationContext(), "Run started", Toast.LENGTH_SHORT).show();

    }

    public void connectGlass(){
        final ProgressDialog progressDialog = new ProgressDialog(StartRunActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Получаем данные с Марса...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        //-----




        //-----
        progressDialog.dismiss();
    }

    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableAdapter);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getName().equals(DEVICE_NAME))
                {
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }


    public boolean BTconnect()
    {
        boolean connected=true;
//        try {
//            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
//            socket.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//            connected=false;
//        }
//        if(connected)
//        {
//            try {
//                outputStream=socket.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                inputStream=socket.getInputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }


        return connected;
    }


    public void onClickStart(View view) {
        if(BTinit())
        {
            if(BTconnect())
            {
                deviceConnected=true;
                beginListenForData();
                textView.append("\nConnection Opened!\n");
            }

        }
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[2048];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {


                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {

                    SystemClock.sleep(3000);

                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0) {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);

                            final String string = new String(rawBytes, "UTF-8");

                            //if (string != null && string.length() > 0 && string.charAt(string.length() - 1) == '\n') {
//                                string = string.substring(0, string.length() - 1);
//                            }

                            //final_result = string;

                            handler.post(new Runnable() {
                                public void run() {

//                                    Intent intent = new Intent(NowPService.this, OnMessageComActivity.class);
//                                    intent.putExtra("str", string);
//                                    startActivity(intent);
//
//
//                                    Toast.makeText(NowPService.this, string, Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                    catch (IOException ex)
                    {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                        stopThread = true;
                    }
                }
            }
        });
        thread.start();
    }

    public void onClickSend(View view) {
        String string = editText.getText().toString();
        string.concat("\n");
        try {
            outputStream.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.append("\nSent Data:"+string+"\n");

    }

    public void onClickStop(View view) throws IOException {
        stopThread = true;
        outputStream.close();
        inputStream.close();
        socket.close();
        deviceConnected=false;
        textView.append("\nConnection Closed!\n");
    }

    public void onClickClear(View view) {
        textView.setText("");
    }


    public IBinder onBind(Intent intent) {
        return null;
    }

    void someTask() {
        BTinit();
        BTconnect();

        beginListenForData();






    }


}
