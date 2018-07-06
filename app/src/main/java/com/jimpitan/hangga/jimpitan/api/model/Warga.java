package com.jimpitan.hangga.jimpitan.api.model;

/**
 * Created by sayekti on 7/6/18.
 */

import com.google.gson.annotations.SerializedName;

public class Warga {

    @SerializedName("id")
    private int id;
    @SerializedName("nama")
    private String nama;

    public Warga() {
    }
    public Warga(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
