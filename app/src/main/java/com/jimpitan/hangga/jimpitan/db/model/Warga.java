package com.jimpitan.hangga.jimpitan.db.model;

import com.orm.SugarRecord;

/**
 * Created by sayekti on 7/3/18.
 */
public class Warga extends SugarRecord<Warga> {

    private int id_warga;
    private String name;

    public Warga() {

    }

    public Warga(int id, String name) {
        this.id_warga = id;
        this.name = name;
    }

    public int getIdWarga() {
        return id_warga;
    }

    public void setIdWarga(int id) {
        this.id_warga = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
