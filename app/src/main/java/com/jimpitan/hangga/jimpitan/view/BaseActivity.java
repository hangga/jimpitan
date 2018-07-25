package com.jimpitan.hangga.jimpitan.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.api.ApiClient;
import com.jimpitan.hangga.jimpitan.api.model.ApiInterface;
import com.jimpitan.hangga.jimpitan.api.model.Getwarga;
import com.jimpitan.hangga.jimpitan.db.model.Config;
import com.jimpitan.hangga.jimpitan.db.model.Nominal;
import com.jimpitan.hangga.jimpitan.db.model.Warga;
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

    public static String VIBRATE = "vibrate";

    public ApiInterface mApiInterface;
    public String googleaccount = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        initFirst();
        syncData(new OnFinishListener() {
            @Override
            public void OnFinish() {
                //
            }
        });
        googleaccount = Utils.getUsername(BaseActivity.this) + "@gmail.com";
    }

    private void initFirst() {

        long c = Nominal.count(Nominal.class, null, null);
        if (c < 1) {
            new Nominal("0").save();
            new Nominal("500").save();
            new Nominal("1000").save();
            new Nominal("1500").save();
            new Nominal("2000").save();
            new Nominal("5000").save();
        }

        if (Config.find(Config.class, "key = ?", VIBRATE).size() == 0)
            new Config(VIBRATE, "1").save();
    }

    public String getConfig(String key){
        return Config.find(Config.class, "key = ?", key).get(0).getVal();
    }

    public void updateConfig(String key, String val){
        Config config = Config.find(Config.class, "key = ?", key).get(0);
        config.setVal(val);
        config.save();
    }

    public boolean isVibrate(){
        return (getConfig(VIBRATE).equalsIgnoreCase("1"));
    }

    public void syncData(final OnFinishListener finishListener) {

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
                if (finishListener != null) finishListener.OnFinish();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void initToolBar(String title, String subtitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void doVibrating(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(250,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            vibrator.vibrate(250);
        }
    }

    public int getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
