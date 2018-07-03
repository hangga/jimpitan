package com.jimpitan.hangga.jimpitan.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.model.Warga;
import com.jimpitan.hangga.jimpitan.presenter.DaoImplementation;

import java.util.List;

public class FrontActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private DaoImplementation daoImplementation;

    private List<Warga> wargas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        daoImplementation = new DaoImplementation(this);

        wargas = daoImplementation.getWargas();
        if (wargas.size() == 0){
            daoImplementation.insert(new Warga(0, "Muhammad bin Abdullah"));
            daoImplementation.insert(new Warga(1, "Ahmad bin Abdurrahim"));
            daoImplementation.insert(new Warga(2, "Abu Ummar Abdillah"));
            daoImplementation.insert(new Warga(3, "Muhammad bin Abdul Ghani"));
            daoImplementation.insert(new Warga(4, "Maryam"));
            daoImplementation.insert(new Warga(5, "Khadijah"));
        }

        //progressbar = (ProgressBar) findViewById(R.id.progressbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressbar.setVisibility(View.VISIBLE);

                startActivity(new Intent(FrontActivity.this, ScannerActivity.class));
                //progressbar.setVisibility(View.INVISIBLE);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

}
