package com.jimpitan.hangga.jimpitan.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.j256.ormlite.stmt.query.In;
import com.jimpitan.hangga.jimpitan.R;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class ScannerActivity extends AppCompatActivity  {

    private BarcodeCapture barcodeCapture;
    private Switch swtcFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        swtcFlash = (Switch) findViewById(R.id.swtcFlash);

        barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);

        swtcFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                initCamera();
            }
        });

        initCamera();
    }

    private BarcodeRetriever barcodeRetriever = new BarcodeRetriever() {
        @Override
        public void onRetrieved(Barcode barcode) {
            Intent intent = new Intent(ScannerActivity.this, InputActivity.class);
            intent.putExtra("id", Integer.parseInt(barcode.displayValue));
            startActivity(intent);
        }

        @Override
        public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

        }

        @Override
        public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

        }

        @Override
        public void onRetrievedFailed(String reason) {

        }

        @Override
        public void onPermissionRequestDenied() {

        }
    };

    private void initCamera(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                barcodeCapture.setShowDrawRect(true)
                        //.setSupportMultipleScan(supportMultiple.isChecked())
                        //.setTouchAsCallback(touchBack.isChecked())
                        .shouldAutoFocus(true)
                        .setShowFlash(swtcFlash.isChecked())
                        .setBarcodeFormat(Barcode.ALL_FORMATS);
                //.setCameraFacing(frontCam.isChecked() ? CameraSource.CAMERA_FACING_FRONT : CameraSource.CAMERA_FACING_BACK)
                //.setShouldShowText(drawText.isChecked());

                barcodeCapture.setRetrieval(barcodeRetriever);
                barcodeCapture.refresh();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
