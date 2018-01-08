package comglebzavyalov.vk.rglass.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import comglebzavyalov.vk.rglass.Models.TrainModel;
import comglebzavyalov.vk.rglass.R;

/**
 * Created by glebzavalov on 06.01.2018.
 */

public class TrainAdapter extends ArrayAdapter<TrainModel> {

    public TrainAdapter(Context context, ArrayList<TrainModel> arrayList){
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View concertView, ViewGroup parent){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.train_item,parent, false);


        TrainModel trainModel = getItem(position);



        TextView textViewDistance = (TextView) view.findViewById(R.id.textViewTrainDistance);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewTrainDate);
        TextView textViewTime = (TextView) view.findViewById(R.id.textViewTrainTime);


        if(trainModel.runningTime >= 60){
            double timeIn = trainModel.runningTime/60;
            textViewTime.setText(String.valueOf(timeIn) + " минут");
        }else{
            double timeIn = trainModel.runningTime;
            textViewTime.setText(String.valueOf(timeIn) + " секунд");
        }

        if(trainModel.distance >= 1000){
            double distanceIn = trainModel.distance/1000;
            textViewDistance.setText(String.valueOf(distanceIn) + " километров");
        }else{
            double distanceIn = trainModel.distance;
            textViewDistance.setText(String.valueOf(distanceIn) + " метров");
        }


        textViewDate.setText(trainModel.date);

        return view;
    }

}
