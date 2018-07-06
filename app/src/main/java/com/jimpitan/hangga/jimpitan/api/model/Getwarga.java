package com.jimpitan.hangga.jimpitan.api.model;

/**
 * Created by sayekti on 7/6/18.
 */


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Getwarga {
    @SerializedName("status")
    String status;
    @SerializedName("result") // kalo pake result error ganti records
    List<Warga> listDataWarga;
    @SerializedName("message")
    String message;
}
