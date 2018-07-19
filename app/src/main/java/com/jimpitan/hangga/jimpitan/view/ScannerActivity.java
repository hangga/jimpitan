package com.jimpitan.hangga.jimpitan.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.SparseArray;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.db.model.Warga;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class ScannerActivity extends BaseActivity {

    private BarcodeCapture barcodeCapture;
    private Switch swtcFlash;
    private BarcodeRetriever barcodeRetriever = new BarcodeRetriever() {
        @Override
        public void onRetrieved(Barcode barcode) {
            int id = Integer.parseInt(barcode.displayValue);

            Warga warga = Warga.findWithQuery(Warga.class , "Select * from Warga where idwarga = ?", String.valueOf(id)).get(0);

            if (warga!= null){
                Intent intent = new Intent(ScannerActivity.this, InputActivity.class);
                intent.putExtra("id", Integer.parseInt(barcode.displayValue));
                startActivity(intent);
            } else {
                Snackbar.make(findViewById(R.id.relTop), "Omahe sopoe iki?", Snackbar.LENGTH_SHORT).show();
            }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initToolBar();

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

    private void initCamera() {
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
