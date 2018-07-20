package com.jimpitan.hangga.jimpitan.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sayekti on 7/21/18.
 */

public class Config {
    @SerializedName("mubeng")
    private int mubeng;
    @SerializedName("mulih")
    private int mulih;

    public Config(){}

    public Config(int mubeng, int mulih){
        this.mubeng = mubeng;
        this.mulih = mulih;
    }

    public int getMubeng() {
        return mubeng;
    }

    public int getMulih() {
        return mulih;
    }
}
