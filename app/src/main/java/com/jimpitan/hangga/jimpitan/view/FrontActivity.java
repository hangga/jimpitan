package com.jimpitan.hangga.jimpitan.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jimpitan.hangga.jimpitan.R;
import com.nex3z.flowlayout.FlowLayout;

import static java.lang.Integer.parseInt;

public class FrontActivity extends BaseActivity {

    static final int PICK_DATA = 121;

    private ProgressBar send_progress;
    private LinearLayout layData;
    private ImageButton btnScanner;
    private TextInputLayout inputNominal;
    private EditText edtNominal;
    private FlowLayout flowRp;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        initToolBar();

        send_progress = (ProgressBar) findViewById(R.id.send_progress);
        layData = (LinearLayout) findViewById(R.id.layData);
        btnScanner = (ImageButton) findViewById(R.id.btnScanner);
        inputNominal = (TextInputLayout) findViewById(R.id.inputNominal);
        edtNominal = (EditText) findViewById(R.id.edtNominal);
        flowRp = (FlowLayout) findViewById(R.id.flowRp);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickDataIntent = new Intent(FrontActivity.this, ScannerActivity.class);
                startActivityForResult(pickDataIntent, PICK_DATA);

                //startActivity(new Intent(FrontActivity.this, ScannerActivity.class));

                // nah yg ini dummy
                /*Intent intent = new Intent(FrontActivity.this, InputActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_DATA) {
            if (resultCode == RESULT_OK) {

            }
        }
    }
}
