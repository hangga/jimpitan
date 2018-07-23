package com.jimpitan.hangga.jimpitan.api.model;

/**
 * Created by sayekti on 7/6/18.
 */

import com.google.gson.annotations.SerializedName;

public class PostJimpitan {
    @SerializedName("status")
    String status;
    @SerializedName("nama")
    String nama;
    @SerializedName("nominal")
    int nominal;

    public String getNama() {
        return nama;
    }

    public int getNominal() {
        return nominal;
    }
}
