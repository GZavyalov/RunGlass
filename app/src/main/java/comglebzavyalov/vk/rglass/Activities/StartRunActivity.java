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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_run);






    }

    public void clickStartRun(View view){
        Toast.makeText(getApplicationContext(), "Run started", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(StartRunActivity.this, NowRunningActivity.class);
        startActivity(intent);

    }



}
