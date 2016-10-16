package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by User on 10/16/2016.
 */

public class main {

    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(1, "com.example.hectorvera.batteryconectionbroadcast.db");

        Entity history = schema.addEntity("History");
        history.addIdProperty().autoincrement();
        history.addStringProperty("Status");
        history.addLongProperty("Date");


        Entity difference = schema.addEntity("difference");
        difference.addIdProperty().autoincrement();
        difference.addLongProperty("Interval");

        DaoGenerator generator = new DaoGenerator();
        generator.generateAll(schema, "./app/src/main/java");
    }
}
