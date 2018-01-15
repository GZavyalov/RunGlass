package comglebzavyalov.vk.rglass.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comglebzavyalov.vk.rglass.Activities.MainActivity;
import comglebzavyalov.vk.rglass.Activities.NowRunningActivity;
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.alert_dialog_mockup, null);
                final EditText editTextTitle = (EditText) mView.findViewById(R.id.editTextTitle);
                final EditText editTextWishes = (EditText) mView.findViewById(R.id.editTextWishes);
                Button button = (Button) mView.findViewById(R.id.buttonApplyDescrp);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getContext(), NowRunningActivity.class);
                        intent.putExtra("title", editTextTitle.getText().toString());
                        intent.putExtra("wishes", editTextWishes.getText().toString());
                        startActivity(intent);

                    }
                });

                builder.setView(mView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });



        return view;
    }





}
