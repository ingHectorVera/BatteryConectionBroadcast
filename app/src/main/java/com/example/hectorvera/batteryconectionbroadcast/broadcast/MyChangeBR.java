package com.example.hectorvera.batteryconectionbroadcast.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.hectorvera.batteryconectionbroadcast.clases.util;
import com.example.hectorvera.batteryconectionbroadcast.db.DaoSession;
import com.example.hectorvera.batteryconectionbroadcast.db.History;
import com.example.hectorvera.batteryconectionbroadcast.db.HistoryDao;
import com.example.hectorvera.batteryconectionbroadcast.db.difference;
import com.example.hectorvera.batteryconectionbroadcast.db.differenceDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by User on 10/16/2016.
 */

public class MyChangeBR extends BroadcastReceiver{
    private static final String KEY_COUNT = "count";
    private static final String KEY_TIME_CONNECTED = "time_connected";
    private int count;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("BROADCAST_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        count = pref.getInt(KEY_COUNT,0);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();

        if(intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED") && count == 0){
            count = 1;

            editor.putInt(KEY_COUNT, count);
            editor.putLong(KEY_TIME_CONNECTED, date.getTime());
            Log.d("Connected: ", dateFormat.format(date));
            editor.commit();
        }else if(intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED") && count == 1){
            count = 0;

            long connected_time = pref.getLong(KEY_TIME_CONNECTED, 0);
            long disconnected_time = date.getTime();
            long difference_time = disconnected_time - connected_time;
            editor.putInt(KEY_COUNT, count);
            date.setTime(disconnected_time);

            Log.d("Disconected: ", dateFormat.format(date));
            Log.d("Interval: ", util.getIntervalString(difference_time));

            editor.commit();

            DaoSession session = util.getSession(context, 1);

            History historyConnected = new History();
            History historyDisconnected = new History();

            historyConnected.setStatus("Connected");
            historyConnected.setDate(connected_time);

            historyDisconnected.setStatus("Disconnected");
            historyDisconnected.setDate(disconnected_time);

            difference difference = new difference();
            difference.setInterval(difference_time);

            HistoryDao historyDao = session.getHistoryDao();
            historyDao.insert(historyConnected);
            historyDao.insert(historyDisconnected);

            differenceDao differenceDao = session.getDifferenceDao();
            differenceDao.insert(difference);
        }
    }
}
