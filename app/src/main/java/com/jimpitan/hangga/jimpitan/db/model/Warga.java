package com.jimpitan.hangga.jimpitan.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sayekti on 7/3/18.
 */

@DatabaseTable(tableName = Warga.TABLE_NAME)
public class Warga {

    public static final String TABLE_NAME = "warga";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    @DatabaseField(columnName = COLUMN_ID)
    private int id;
    @DatabaseField(columnName = COLUMN_NAME)
    private String name;

    public Warga(){

    }

    public Warga(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
