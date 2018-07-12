package com.jimpitan.hangga.jimpitan.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.jimpitan.hangga.jimpitan.R;

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
                //progressbar.setVisibility(View.VISIBLE);

                startActivity(new Intent(FrontActivity.this, ScannerActivity.class));
                //progressbar.setVisibility(View.INVISIBLE);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

}
