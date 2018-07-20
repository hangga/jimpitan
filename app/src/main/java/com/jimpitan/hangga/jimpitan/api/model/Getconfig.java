package com.jimpitan.hangga.jimpitan.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sayekti on 7/21/18.
 */

public class Getconfig {
    @SerializedName("status")
    String status;
    @SerializedName("records")
    List<Config> configList;
    @SerializedName("message")
    String message;

    public List<Config> getConfigList() {
        return configList;
    }
}
