package comglebzavyalov.vk.rglass.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import comglebzavyalov.vk.rglass.Adapters.TrainAdapter;
import comglebzavyalov.vk.rglass.Models.TrainModel;
import comglebzavyalov.vk.rglass.R;


public class StatisticFragment extends Fragment {

    ListView listView;
    ArrayList<TrainModel> list = new ArrayList<TrainModel>();

    TextView textViewTitleStat;
    FirebaseDatabase database;
    DatabaseReference reference;


    String mUid;



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_stat, null);


        list.clear();

        Typeface typefaceReg = Typeface.createFromAsset(getContext().getAssets(),"fonts/SSFDReg.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SSFDBold.ttf");

        textViewTitleStat = (TextView) view.findViewById(R.id.textViewTitleStat);

        textViewTitleStat.setTypeface(typefaceBold);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("RGlass", Context.MODE_PRIVATE);

        mUid = sharedPreferences.getString("mUid", "");


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Выполняется вход...");
        progressDialog.show();


        try {
            reference.child(mUid).child("trains").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot training : dataSnapshot.getChildren()) {
                        String date = (String) training.child("date").getValue();
                        String time = (String) training.child("time").getValue();
                        String title = (String) training.child("title").getValue();

                        String[] minutes_and_secs = time.split(":");


                        Integer seconds = 0;


                        seconds = Integer.parseInt(minutes_and_secs[0]) * 60 + Integer.parseInt(minutes_and_secs[1]);

                        list.add( new TrainModel(title, date, seconds, 100000));

                    }

                    setData();
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }catch (Exception ex){
            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }


        listView = (ListView) view.findViewById(R.id.listViewStat);



//        list.add(new TrainModel("11.11.2010", 10000, 1000));
//        list.add(new TrainModel("12.11.2010", 100000, 10000));













        return view;
    }

    public void setData(){
        TrainAdapter adapter = new TrainAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }
}
