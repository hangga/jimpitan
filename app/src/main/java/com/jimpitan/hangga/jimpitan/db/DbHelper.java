package com.jimpitan.hangga.jimpitan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jimpitan.hangga.jimpitan.model.Warga;

import java.sql.SQLException;

/**
 * Created by sayekti on 7/3/18.
 */

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "jimpitan.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Warga,Integer> wargas;

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public DbHelper(Context context, String databaseName,
                    SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Warga.class);
        }catch (SQLException ex){
            //ex.getErrorCode();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Warga.class,true);
        }catch (SQLException ex){
            //ex.getErrorCode();
        }
    }

    public Dao<Warga, Integer> getWargas() throws SQLException {
        if (wargas == null) {
            wargas = getDao(Warga.class);
        }
        return wargas;
    }

    @Override
    public void close() {
        wargas = null;
        super.close();
    }
}
