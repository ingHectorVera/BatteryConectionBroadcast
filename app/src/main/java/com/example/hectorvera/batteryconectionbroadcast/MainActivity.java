package com.example.hectorvera.batteryconectionbroadcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hectorvera.batteryconectionbroadcast.clases.util;
import com.example.hectorvera.batteryconectionbroadcast.db.DaoSession;
import com.example.hectorvera.batteryconectionbroadcast.db.History;
import com.example.hectorvera.batteryconectionbroadcast.db.HistoryDao;
import com.example.hectorvera.batteryconectionbroadcast.db.differenceDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DaoSession session;
    TextView tAverage, tHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tAverage = ((TextView) findViewById(R.id.tAverage));
        tHistory = ((TextView) findViewById(R.id.tHistory));

        session = util.getSession(getBaseContext(), 0);
    }

    public void getHistory(View view) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        HistoryDao historyDao = session.getHistoryDao();
        ArrayList<History> histories = historyDao.loadAll();
        if(histories != null) {
            for (History history : histories) {
                date.setTime(history.getDate());
                tHistory.setText(tHistory.getText().toString()
                + history.getStatus()+" "+ dateFormat.format(date)+"\n");
            }
        }
    }

    public void getAverage(View view) {
        differenceDao differenceDao = session.getDifferenceDao();
        tAverage.setText(util.getIntervalString(differenceDao.getAverage()));
    }
}
