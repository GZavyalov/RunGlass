package comglebzavyalov.vk.rglass.Models;

import android.content.Intent;

/**
 * Created by glebzavalov on 06.01.2018.
 */

public class TrainModel {
    public String title;
    public String date;
    public Integer runningTime;
    public Integer distance;

    public TrainModel(String title, String date, Integer runningTime, Integer distance){
        this.date = date;//dd.mm.yyyy
        this.runningTime = runningTime;//seconds
        this.title = title;
        this.distance = distance;//metrs
    }




}
