package comglebzavyalov.vk.rglass.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import comglebzavyalov.vk.rglass.Activities.MainActivity;
import comglebzavyalov.vk.rglass.Activities.NowRunningActivity;
import comglebzavyalov.vk.rglass.R;

/**
 * Created by glebzavalov on 05.01.2018.
 */

public class TrainFragments extends Fragment {

    Button buttonGoingToStatistics;
    Button buttonGoingToStartRun;
    Button buttonGoingToStartRun2;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_train, null);

        buttonGoingToStatistics = (Button) view.findViewById(R.id.buttonGoingToStatistics);
        buttonGoingToStartRun = (Button) view.findViewById(R.id.buttonGoingToStartRun);
        buttonGoingToStartRun2 = (Button) view.findViewById(R.id.buttonGoingToStartRun2);

        buttonGoingToStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("intFr", 2);
                startActivity(intent);
            }
        });

        buttonGoingToStartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        buttonGoingToStartRun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });




        return view;
    }

    public void showAlertDialog(){


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
}

