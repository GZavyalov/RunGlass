package comglebzavyalov.vk.rglass.Fragments;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.result.DailyTotalResult;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import comglebzavyalov.vk.rglass.Activities.MainActivity;
import comglebzavyalov.vk.rglass.Activities.StartRunActivity;
import comglebzavyalov.vk.rglass.R;

/**
 * Created by glebzavalov on 05.01.2018.
 */

public class RunFragment extends Fragment {

    private final String TAG = "Fit";
    private final String LOG_TAG = "Fit";

    public long total = 0;


    TextView textViewSubTitle1, textViewTitle, textViewDescrp2, textViewDescrp1, textViewSubTitle2;




    Button buttonStartRun;
    Button buttonShowPodscazki;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_run, null);



        Typeface typefaceReg = Typeface.createFromAsset(getContext().getAssets(),"fonts/SSFDReg.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SSFDBold.ttf");

        buttonStartRun = (Button) view.findViewById(R.id.buttonGoingToStartRun);
        buttonShowPodscazki = (Button) view.findViewById(R.id.buttonShowPodscazki);
        textViewDescrp1 = (TextView) view.findViewById(R.id.textViewDescrp1);
        textViewDescrp2 = (TextView) view.findViewById(R.id.textViewDescrp2);
        textViewSubTitle1 = (TextView) view.findViewById(R.id.textViewSubTitle1);
        textViewSubTitle2 = (TextView) view.findViewById(R.id.textViewSubTitle2);
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);



        buttonStartRun.setTypeface(typefaceBold);
        buttonShowPodscazki.setTypeface(typefaceBold);
        textViewTitle.setTypeface(typefaceBold);
        textViewSubTitle1.setTypeface(typefaceBold);
        textViewSubTitle2.setTypeface(typefaceBold);
        textViewDescrp1.setTypeface(typefaceReg);
        textViewDescrp2.setTypeface(typefaceReg);



        buttonShowPodscazki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("intFr", 3);

                startActivity(intent);
            }
        });

        buttonStartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), StartRunActivity.class);
                startActivity(intent);

            }
        });



        return view;
    }





}
