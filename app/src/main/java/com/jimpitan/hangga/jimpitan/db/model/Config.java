package com.jimpitan.hangga.jimpitan.db.model;

import com.orm.SugarRecord;

/**
 * Created by sayekti on 7/24/18.
 */

public class Config extends SugarRecord {

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

    private String key;

    public void setVal(String val) {
        this.val = val;
    }

    private String val;

    public Config(){}

    public Config(String key, String val){
        this.key = key;
        this.val = val;
    }

}
