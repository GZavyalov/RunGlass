package comglebzavyalov.vk.rglass.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import comglebzavyalov.vk.rglass.Activities.StartRunActivity;
import comglebzavyalov.vk.rglass.R;

/**
 * Created by glebzavalov on 05.01.2018.
 */

public class RunFragment extends Fragment {

    Button buttonStartRun;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_run, null);


        buttonStartRun = (Button) view.findViewById(R.id.buttonGoingToStartRun);


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
