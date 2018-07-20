package com.jimpitan.hangga.jimpitan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.api.ApiClient;
import com.jimpitan.hangga.jimpitan.api.model.ApiInterface;
import com.jimpitan.hangga.jimpitan.api.model.Getwarga;
import com.jimpitan.hangga.jimpitan.db.model.Nominal;
import com.jimpitan.hangga.jimpitan.db.model.Warga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sayekti on 7/3/18.
 */

public class BaseActivity extends AppCompatActivity {
    //public DaoImplementation daoImplementation;
    //public List<Warga> wargas;
    //public List<Nominal> noms;
    public ApiInterface mApiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //daoImplementation = new DaoImplementation(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        initDummy();
    }

    private void initDummy() {

        long c = Nominal.count(Nominal.class, null, null);
        if (c < 1){
            new Nominal("500").save();
            new Nominal("1000").save();
            new Nominal("2000").save();
        }
        /*List<Nominal> noms = Nominal.listAll(Nominal.class);

        if (noms != null || noms.size() == 0) {
            new Nominal("500").save();
            new Nominal("1000").save();
            new Nominal("2000").save();
        }*/

        List<Warga> wargas = Warga.listAll(Warga.class);

        long cWarga = Warga.count(Warga.class, null, null);

        if (cWarga < 1) {

            Call<Getwarga> getwargaCall = mApiInterface.getWarga();
            getwargaCall.enqueue(new Callback<Getwarga>() {
                @Override
                public void onResponse(Call<Getwarga> call, Response<Getwarga> response) {
                    List<com.jimpitan.hangga.jimpitan.api.model.Warga> wargaList = response.body().getListDataWarga();


                    for (int i = 0; i < wargaList.size(); i++) {
                        new Warga(wargaList.get(i).getId(), wargaList.get(i).getNama()).save();
                    }
                }

                @Override
                public void onFailure(Call<Getwarga> call, Throwable t) {

                }
            });
        }
    }

    protected Warga getWarga(int id) {
        return Warga.find(Warga.class, "idwarga = ?", String.valueOf(id)).get(0);
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
