package comglebzavyalov.vk.rglass.Activities;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import comglebzavyalov.vk.rglass.Fragments.RunFragment;
import comglebzavyalov.vk.rglass.Fragments.StatisticFragment;
import comglebzavyalov.vk.rglass.Fragments.TrainFragments;
import comglebzavyalov.vk.rglass.R;

import static java.text.DateFormat.getDateInstance;
import static java.text.DateFormat.getInstance;
import static java.text.DateFormat.getTimeInstance;

public class MainActivity extends AppCompatActivity {

    RunFragment runFragment;
    StatisticFragment statisticFragment;
    TrainFragments trainFragments;


    final String LOG_TAG = "Fit";
    final String TAG = "Fit";


    public long total = 0;


    private GoogleApiClient mClient = null;
    private OnDataPointListener mListener;




    public Task<DataReadResponse> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int intent_mode = getIntent().getIntExtra("intFr", 0);









        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);


        //--------
        runFragment = new RunFragment();
        statisticFragment = new StatisticFragment();
        trainFragments = new TrainFragments();
        //--------

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.run_navigation:{

                        try{
                            replace(runFragment);
                        }catch (Exception ex){

                        }
                        return true;

                    }


                    case R.id.stat_navigation:{

                        try{
                            replace(statisticFragment);
                        }catch (Exception ex){

                        }
                        return true;

                    }

                    case R.id.train_navigation:{
                        try{
                            replace(trainFragments);
                        }catch (Exception ex){

                        }
                        return true;
                    }

                }
                return false;
            }
        });


        if(savedInstanceState == null){
                try {
                    replace(runFragment);
                } catch (Exception ex) {
                }

        }



            switch (intent_mode){
                case 1:{
                    replace(runFragment);
                    break;
                }

                case 2:{
                    replace(statisticFragment);
                    break;
                }

                case 3:{
                    replace(trainFragments);
                    break;
                }
            }

    }


    @Override
    protected void onResume() {
        super.onResume();
        connectFitness();
    }


    private void connectFitness() {
        if (mClient == null){
            mClient = new GoogleApiClient.Builder(this)
                    .addApi(Fitness.SENSORS_API)
                    .addApi(Fitness.HISTORY_API)
                    .addScope(new Scope(Scopes.FITNESS_LOCATION_READ)) // GET STEP VALUES
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                                @Override
                                                public void onConnected(Bundle bundle) {
                                                    Log.e(LOG_TAG, "Connected!!!");
                                                    // Now you can make calls to the Fitness APIs.
                                                    findFitnessDataSources();

                                                }

                                                @Override
                                                public void onConnectionSuspended(int i) {
                                                    // If your connection to the sensor gets lost at some point,
                                                    // you'll be able to determine the reason and react to it here.
                                                    if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                                        Log.i(LOG_TAG, "Connection lost.  Cause: Network Lost.");
                                                    } else if (i
                                                            == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                                        Log.i(LOG_TAG,
                                                                "Connection lost.  Reason: Service Disconnected");
                                                    }
                                                }
                                            }
                    )
                    .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.e(LOG_TAG, "!_@@ERROR :: Google Play services connection failed. Cause: " + result.toString());
                        }
                    })
                    .build();
        }else{
            findFitnessDataSources();
        }

    }


    private void findFitnessDataSources() {
        Fitness.SensorsApi.findDataSources(
                mClient,
                new DataSourcesRequest.Builder()
                        .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                        .setDataSourceTypes(DataSource.TYPE_DERIVED)
                        .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        Log.e(LOG_TAG, "Result: " + dataSourcesResult.getStatus().toString());
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            Log.e(LOG_TAG, "Data source found: " + dataSource.toString());
                            Log.e(TAG, "Data Source type: " + dataSource.getDataType().getName());

                            //Let's register a listener to receive Activity data!
                            if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA) && mListener == null) {
                                Log.i(TAG, "Data source for TYPE_STEP_COUNT_DELTA found!  Registering.");
                                Log.i(TAG, dataSource.toString());

                                registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_DELTA);
                            }
                        }
                    }
                });

        final Handler handler = new Handler();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {


                    PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_STEP_COUNT_DELTA);
                    DailyTotalResult totalResult = result.await(30, TimeUnit.SECONDS);
                    if (totalResult.getStatus().isSuccess()) {
                        DataSet totalSet = totalResult.getTotal();
                        total = totalSet.isEmpty()
                                ? 0
                                : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    } else {
                        Log.w(TAG, "There was a problem getting the step count.");
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), String.valueOf(total), Toast.LENGTH_SHORT).show();

                        }
                    });


            }
        };

        Thread thread = new Thread(runnable);
        thread.start();




    }





    private void registerFitnessDataListener(final DataSource dataSource, DataType dataType) {


        // [START register_data_listener]
        mListener = new OnDataPointListener() {
            @Override
            public void onDataPoint(DataPoint dataPoint) {
                for (Field field : dataPoint.getDataType().getFields()) {
                    Value val = dataPoint.getValue(field);
                    Log.e(TAG, "Detected DataPoint field: " + field.getName());
                    Log.e(TAG, "Detected DataPoint value: " + val);

                }
            }
        };

        Fitness.SensorsApi.add(
                mClient,
                new SensorRequest.Builder()
                        .setDataSource(dataSource) // Optional but recommended for custom data sets.
                        .setDataType(dataType) // Can't be omitted.
                        .setSamplingRate(1, TimeUnit.SECONDS)
                        .build(),
                mListener).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                if (status.isSuccess()) {
                    Log.i(TAG, "Listener registered!");
                } else {
                    Log.i(TAG, "Listener not registered.");
                }
            }
        });

    }


    public void replace(Fragment newFragment){
            try {
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment);
                fragmentTransaction.commit();
            }catch (Exception ex){}
    }


}
