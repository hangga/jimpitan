package com.jimpitan.hangga.jimpitan.db.model;

import com.orm.SugarRecord;

/**
 * Created by sayekti on 7/18/18.
 */

public class Nom extends SugarRecord<Nom> {
    private long val;

    public Nom() {

    }

    public Nom(long val) {
        this.val = val;
    }

    public long getVal() {
        return val;
    }

    public void setVal(long val) {
        this.val = val;
    }
}
