package com.jimpitan.hangga.jimpitan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jimpitan.hangga.jimpitan.api.ApiClient;
import com.jimpitan.hangga.jimpitan.api.model.ApiInterface;
import com.jimpitan.hangga.jimpitan.api.model.Getwarga;
import com.jimpitan.hangga.jimpitan.db.model.Warga;
import com.jimpitan.hangga.jimpitan.presenter.DaoImplementation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sayekti on 7/3/18.
 */

public class BaseActivity extends AppCompatActivity {
    public DaoImplementation daoImplementation;
    public List<Warga> wargas;
    public ApiInterface mApiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        daoImplementation = new DaoImplementation(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        initDummy();
    }

    private void initDummy() {
        wargas = daoImplementation.getWargas();
        if (wargas.size() == 0) {

            Call<Getwarga> getwargaCall = mApiInterface.getWarga();
            getwargaCall.enqueue(new Callback<Getwarga>() {
                @Override
                public void onResponse(Call<Getwarga> call, Response<Getwarga> response) {
                    List<com.jimpitan.hangga.jimpitan.api.model.Warga> wargaList = response.body().getListDataWarga();
                    for (int i = 0; i < wargaList.size(); i++){
                        daoImplementation.insert(new Warga(wargaList.get(i).getId(), wargaList.get(i).getNama()));
                    }
                }

                @Override
                public void onFailure(Call<Getwarga> call, Throwable t) {

                }
            });

            /*daoImplementation.insert(new Warga(0, "Muhammad bin Abdullah"));
            daoImplementation.insert(new Warga(1, "Ahmad bin Abdurrahim"));
            daoImplementation.insert(new Warga(2, "Abu Ummar Abdillah"));
            daoImplementation.insert(new Warga(3, "Muhammad bin Abdul Ghani"));
            daoImplementation.insert(new Warga(4, "Maryam"));
            daoImplementation.insert(new Warga(5, "Khadijah"));*/
            Log.d("JIMPITAN", "yaksip...");
        } else {
            for (int i = 0; i < wargas.size(); i++) {
                Log.d("JIMPITAN", wargas.get(i).getName());
            }
        }
    }

    protected Warga getWarga(int id) {
        return daoImplementation.getWarga(id);
    }
}
