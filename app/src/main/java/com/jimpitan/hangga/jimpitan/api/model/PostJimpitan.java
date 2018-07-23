package com.jimpitan.hangga.jimpitan.api.model;

/**
 * Created by sayekti on 7/6/18.
 */

import com.google.gson.annotations.SerializedName;

public class PostJimpitan {
    public String getMessage() {
        return message;
    }

    @SerializedName("message")
    String message;

    public int getStatus() {
        return status;
    }

    @SerializedName("status")
    int status;

}
