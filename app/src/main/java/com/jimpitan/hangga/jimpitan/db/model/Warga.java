package com.jimpitan.hangga.jimpitan.db.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by sayekti on 7/3/18.
 */

@Table
public class Warga extends SugarRecord {

    private int idwarga;
    private String name;

    public Warga() {

    }

    public Warga(int id, String name) {
        this.idwarga = id;
        this.name = name;
    }

    public int getIdWarga() {
        return idwarga;
    }

    public void setIdWarga(int id) {
        this.idwarga = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
