package comglebzavyalov.vk.rglass.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comglebzavyalov.vk.rglass.Adapters.TrainAdapter;
import comglebzavyalov.vk.rglass.Models.TrainModel;
import comglebzavyalov.vk.rglass.R;


public class StatisticFragment extends Fragment {

    ListView listView;
    ArrayList<TrainModel> list = new ArrayList<TrainModel>();

    TextView textViewTitleStat;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_stat, null);


        list.clear();

        Typeface typefaceReg = Typeface.createFromAsset(getContext().getAssets(),"fonts/SSFDReg.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SSFDBold.ttf");

        textViewTitleStat = (TextView) view.findViewById(R.id.textViewTitleStat);

        textViewTitleStat.setTypeface(typefaceBold);




        listView = (ListView) view.findViewById(R.id.listViewStat);
        list.add(new TrainModel("10.10.2010", 1000000,100000));
        list.add(new TrainModel("11.11.2010", 10000, 1000));
        list.add(new TrainModel("12.11.2010", 100000, 10000));


        TrainAdapter adapter = new TrainAdapter(getContext(), list);
        listView.setAdapter(adapter);








        return view;
    }
}
