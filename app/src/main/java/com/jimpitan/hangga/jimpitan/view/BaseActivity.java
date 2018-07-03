package com.jimpitan.hangga.jimpitan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jimpitan.hangga.jimpitan.model.Warga;
import com.jimpitan.hangga.jimpitan.presenter.DaoImplementation;

import java.util.List;

/**
 * Created by sayekti on 7/3/18.
 */

public class BaseActivity extends AppCompatActivity {
    public DaoImplementation daoImplementation;
    public List<Warga> wargas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        daoImplementation = new DaoImplementation(this);
        initDummy();
    }

    private void initDummy() {
        wargas = daoImplementation.getWargas();
        if (wargas.size() == 0) {
            daoImplementation.insert(new Warga(0, "Muhammad bin Abdullah"));
            daoImplementation.insert(new Warga(1, "Ahmad bin Abdurrahim"));
            daoImplementation.insert(new Warga(2, "Abu Ummar Abdillah"));
            daoImplementation.insert(new Warga(3, "Muhammad bin Abdul Ghani"));
            daoImplementation.insert(new Warga(4, "Maryam"));
            daoImplementation.insert(new Warga(5, "Khadijah"));
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
