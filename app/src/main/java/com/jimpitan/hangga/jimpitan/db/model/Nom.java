package com.jimpitan.hangga.jimpitan.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sayekti on 7/18/18.
 */

@DatabaseTable(tableName = Nom.TABLE_NAME)
public class Nom {
    public static final String TABLE_NAME = "nom";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "val";

    @DatabaseField(generatedId = true, columnName = COLUMN_ID)    // For Autoincrement
    private Integer id;

    @DatabaseField(columnName = COLUMN_NAME)
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
