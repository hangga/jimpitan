package com.jimpitan.hangga.jimpitan.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.api.ApiClient;
import com.jimpitan.hangga.jimpitan.api.model.ApiInterface;
import com.jimpitan.hangga.jimpitan.api.model.Config;
import com.jimpitan.hangga.jimpitan.api.model.Getconfig;
import com.jimpitan.hangga.jimpitan.api.model.Getwarga;
import com.jimpitan.hangga.jimpitan.db.model.Nominal;
import com.jimpitan.hangga.jimpitan.db.model.Warga;
import com.jimpitan.hangga.jimpitan.util.OwnerInfo;
import com.jimpitan.hangga.jimpitan.util.Utils;
import com.jimpitan.hangga.jimpitan.view.custominterface.OnFinishListener;

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
    public String googleaccount = "";
    public int mubeng = 0, mulih = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //daoImplementation = new DaoImplementation(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        initDummy();
        syncData(new OnFinishListener() {
            @Override
            public void OnFinish() {
                //
            }
        });

        //OwnerInfo ownerInfo = new OwnerInfo(BaseActivity.this);

        googleaccount = Utils.getUsername(BaseActivity.this)+"@gmail.com";
        Log.d("JIMPITAN-AKUNKU", googleaccount);
    }

    private void initDummy() {

        long c = Nominal.count(Nominal.class, null, null);
        if (c < 1){
            new Nominal("500").save();
            new Nominal("1000").save();
            new Nominal("1500").save();
            new Nominal("2000").save();
            new Nominal("5000").save();
        }
    }

    public void syncData(final OnFinishListener finishListener){
        mApiInterface.getConfig().enqueue(new Callback<Getconfig>() {
            @Override
            public void onResponse(Call<Getconfig> call, Response<Getconfig> response) {
                mubeng = response.body().getConfigList().get(0).getMubeng();
                mulih = response.body().getConfigList().get(0).getMulih();
            }

            @Override
            public void onFailure(Call<Getconfig> call, Throwable t) {

            }
        });

        mApiInterface.getWarga().enqueue(new Callback<Getwarga>() {
            @Override
            public void onResponse(Call<Getwarga> call, Response<Getwarga> response) {
                List<com.jimpitan.hangga.jimpitan.api.model.Warga> wargaList = response.body().getListDataWarga();

                if (wargaList.size() == 0) return;

                for (int i = 0; i < wargaList.size(); i++) {
                    String sid = String.valueOf(wargaList.get(i).getId());

                    if (Warga.find(Warga.class, "idwarga = ?", sid).size() == 0)
                        new Warga(wargaList.get(i).getId(), wargaList.get(i).getNama()).save();
                }
                finishListener.OnFinish();
            }

            @Override
            public void onFailure(Call<Getwarga> call, Throwable t) {

            }
        });
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_qr_small);
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
