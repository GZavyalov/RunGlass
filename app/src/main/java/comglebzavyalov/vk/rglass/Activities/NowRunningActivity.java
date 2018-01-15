package comglebzavyalov.vk.rglass.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import comglebzavyalov.vk.rglass.R;

public class NowRunningActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "NowRunningActivity";


    public static TextView textViewCurrentSpeed;
    public static boolean HaveRunStartedBefore = false;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10; // 10 sec
    private static int FATEST_INTERVAL = 5; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    public String titleStr = "";
    public String wishes = "";
    TextView textViewStatus;
    Button buttonStartStop;
    Chronometer chronometer;
    FirebaseDatabase database;
    DatabaseReference reference;
    String mUid = "";


    boolean modeButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_running);


        textViewStatus = findViewById(R.id.textViewStatus);
        chronometer = findViewById(R.id.chronometer);
        buttonStartStop = findViewById(R.id.buttonStartStopRun);

        textViewCurrentSpeed = findViewById(R.id.textViewCurrentSpeed);

        SharedPreferences sharedPreferences = getSharedPreferences("RGlass", Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString("mUid", "");

//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        try {
//
//            List<String> providers = locationManager.getProviders(true);
//            for (String provider : providers) {
//                Location l = locationManager.getLastKnownLocation(provider);
//                if (l != null) {
//                    locationManager.requestLocationUpdates(provider, 0, 0, this);
//
//                    break;
//                }
//
//            }
//
//        } catch (SecurityException ex) {
//            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
//        }
//        this.updateSpeed(null);


        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();


        }


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


        titleStr = getIntent().getStringExtra("title");
        wishes = getIntent().getStringExtra("wishes");


        Typeface typefaceReg = Typeface.createFromAsset(this.getAssets(), "fonts/SSFDReg.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(this.getAssets(), "fonts/SSFDBold.ttf");


        textViewStatus.setTypeface(typefaceBold);
        buttonStartStop.setTypeface(typefaceBold);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();

                if (elapsedMillis > 5000) {
//                    String strElapsedMillis = "Прошло больше 5 секунд";
//                    Toast.makeText(getApplicationContext(),
//                            strElapsedMillis, Toast.LENGTH_SHORT)
//                            .show();
                }
            }
        });

        buttonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modeButton == false) {
                    buttonStartStop.setText("СТОП");
                    textViewStatus.setText("Идёт пробежка...");
                    modeButton = true;
                    togglePeriodicLocationUpdates();
                    startRun();
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();

                } else {
                    buttonStartStop.setText("СТАРТ");
                    textViewStatus.setText("Пробежка окончена");
                    modeButton = false;
                    stopRun();
                    chronometer.stop();
                }
            }
        });


    }

    private void updateSpeed(Location location) {
        float nCurrentSpeed = 0;
        if (location != null) {
            nCurrentSpeed = location.getSpeed();
        }

        Log.d(TAG, "updateSpeed: " + location.getAltitude() + "  " + location.getLongitude());

        Log.d(TAG, "updateSpeed " + String.valueOf(nCurrentSpeed));
        Toast.makeText(getApplicationContext(), "updateSpeed " + String.valueOf(nCurrentSpeed), Toast.LENGTH_SHORT).show();
        try {
            Log.d(TAG, "updateSpeed " + String.valueOf(location.getProvider()));
        } catch (Exception ex) {
            Log.d(TAG, "updateSpeed: " + ex.toString());
        }


        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", nCurrentSpeed);
        String strCurrenttSpeed = fmt.toString();
        textViewCurrentSpeed.setText(strCurrenttSpeed + " m/s");
    }


    public void stopRun() {
        String time = chronometer.getText().toString();

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        String todayDate = df2.format(c.getTime());

        Toast.makeText(getApplicationContext(), chronometer.getText(), Toast.LENGTH_SHORT).show();

        reference.child(mUid).child("trains").child(titleStr + todayDate).child("time").setValue(time);
        reference.child(mUid).child("trains").child(titleStr + todayDate).child("title").setValue(titleStr);
        reference.child(mUid).child("trains").child(titleStr + todayDate).child("wishes").setValue(wishes);
        reference.child(mUid).child("trains").child(titleStr + todayDate).child("date").setValue(todayDate);

        Intent intent = new Intent(NowRunningActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void startRun() {

    }

    public void finish() {

    }

    private void displayLocation() {

        try {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT);
            Log.d(TAG, "displayLocation: " + ex.getMessage());
        }

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            updateSpeed(mLastLocation);

//            lblLocation.setText(latitude + ", " + longitude);

        } else {

//            lblLocation
//                    .setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();


    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
        Log.d(TAG, "onConnected: CONNECTED!1!");

        createLocationRequest();

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            // Changing the button text
//            btnStartLocationUpdates
//                    .setText(getString(R.string.btn_stop_location_updates));

            mRequestingLocationUpdates = true;

            // Starting the location updates
            startLocationUpdates();

            Log.d(TAG, "Periodic location updates started!");

        } else {
            // Changing the button text
//            btnStartLocationUpdates
//                    .setText(getString(R.string.btn_start_location_updates));

            mRequestingLocationUpdates = false;

            // Stopping the location updates
            stopLocationUpdates();

            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT); // 10 meters
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
        updateSpeed(location);

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation();
    }


}
