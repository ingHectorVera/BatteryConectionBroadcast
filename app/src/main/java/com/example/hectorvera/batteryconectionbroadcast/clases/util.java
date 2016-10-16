package com.example.hectorvera.batteryconectionbroadcast.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hectorvera.batteryconectionbroadcast.db.DaoMaster;
import com.example.hectorvera.batteryconectionbroadcast.db.DaoSession;

/**
 * Created by User on 10/16/2016.
 */

public class util {

    public static String getIntervalString(long difference){
        long seconds = difference / 1000 % 60;
        long minutes = difference / (60 * 1000) % 60;
        long hour = difference / (60 * 60 * 1000);
        int day = (int)difference / (1000 * 60 * 60 * 24);

        return "Days: "+ day +" hours: " + hour + " minutes: "
                + minutes + " seconds: "+seconds;
    }

    public static DaoSession getSession(Context context, int action){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "GreenDAO-DB", null);
        SQLiteDatabase db;
        if(action == 1){
            db = helper.getWritableDatabase();
        }else {
            db = helper.getReadableDatabase();
        }
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }
}