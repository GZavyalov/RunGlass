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
                    try {
                        replace(runFragment);
                    }catch (Exception ex){
                    }
                    break;
                }

                case 2:{
                    try {
                        replace(statisticFragment);
                    }catch (Exception ex){}
                    break;
                }

                case 3:{
                    try {
                        replace(trainFragments);
                    }catch (Exception ex){}
                    break;
                }
            }

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
