package com.jimpitan.hangga.jimpitan.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.jimpitan.hangga.jimpitan.R;

import static java.lang.Integer.parseInt;

public class FrontActivity extends BaseActivity {

    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        initToolBar();

        //progressbar = (ProgressBar) findViewById(R.id.progressbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                di blok dulu sementara
                startActivity(new Intent(FrontActivity.this, ScannerActivity.class));
                */

                // nah yg ini dummy
                Intent intent = new Intent(FrontActivity.this, InputActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });
    }

}
