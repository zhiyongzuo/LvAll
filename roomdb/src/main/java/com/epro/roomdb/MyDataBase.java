package com.epro.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserInfoBean.class/*Gdbean.class, ChaoBiaoHistory.class, RqXx.class*/}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    public abstract UserInfoBeanDao getUserInfoBeanDao();

    private static volatile MyDataBase INSTANCE;

    public static MyDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            MyDataBase.class, "gzrq")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
