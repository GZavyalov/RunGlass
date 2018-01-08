package comglebzavyalov.vk.rglass.Activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import comglebzavyalov.vk.rglass.Fragments.RunFragment;
import comglebzavyalov.vk.rglass.Fragments.StatisticFragment;
import comglebzavyalov.vk.rglass.Fragments.TrainFragments;
import comglebzavyalov.vk.rglass.R;

public class MainActivity extends AppCompatActivity {

    RunFragment runFragment;
    StatisticFragment statisticFragment;
    TrainFragments trainFragments;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
            }catch (Exception ex){}
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
