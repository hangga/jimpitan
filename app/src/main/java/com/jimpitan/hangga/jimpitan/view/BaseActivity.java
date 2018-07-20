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
        syncData();
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("JIMPITAN-NOMERKU", tMgr.getLine1Number().toString());
        //String mPhoneNumber = tMgr.getLine1Number();
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

    public void syncData(){
        Call<Getwarga> getwargaCall = mApiInterface.getWarga();
        getwargaCall.enqueue(new Callback<Getwarga>() {
            @Override
            public void onResponse(Call<Getwarga> call, Response<Getwarga> response) {
                List<com.jimpitan.hangga.jimpitan.api.model.Warga> wargaList = response.body().getListDataWarga();

                if (wargaList.size() == 0) return;

                for (int i = 0; i < wargaList.size(); i++) {
                    String sid = String.valueOf(wargaList.get(i).getId());

                    if (Warga.find(Warga.class, "idwarga = ?", sid).size() == 0)
                        new Warga(wargaList.get(i).getId(), wargaList.get(i).getNama()).save();
                }
            }

            @Override
            public void onFailure(Call<Getwarga> call, Throwable t) {

            }
        });
    }

    protected Warga getWarga(int id) {
        return Warga.find(Warga.class, "idwarga = ?", String.valueOf(id)).get(0);
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
