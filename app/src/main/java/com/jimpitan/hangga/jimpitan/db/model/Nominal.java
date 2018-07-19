package com.jimpitan.hangga.jimpitan.db.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

/**
 * Created by sayekti on 7/18/18.
 */

@Table
public class Nominal extends SugarRecord {

    @Unique
    private String val;

    public Nominal() {

    }

    public Nominal(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
