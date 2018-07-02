package com.jimpitan.hangga.jimpitan.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.jimpitan.hangga.jimpitan.R;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class ScannerActivity extends AppCompatActivity implements BarcodeRetriever {

    private BarcodeCapture barcodeCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);


    }

    @Override
    protected void onStart() {
        super.onStart();
        barcodeCapture.setShowDrawRect(true)
                //.setSupportMultipleScan(supportMultiple.isChecked())
                //.setTouchAsCallback(touchBack.isChecked())
                //.shouldAutoFocus(true)
                //.setShowFlash(flash.isChecked())
                .setBarcodeFormat(Barcode.ALL_FORMATS);
        //.setCameraFacing(frontCam.isChecked() ? CameraSource.CAMERA_FACING_FRONT : CameraSource.CAMERA_FACING_BACK)
        //.setShouldShowText(drawText.isChecked());

        barcodeCapture.setRetrieval(this);
        barcodeCapture.refresh();
    }

    @Override
    public void onRetrieved(final Barcode barcode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText input = new EditText(ScannerActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.requestFocus();

                AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this)
                        .setTitle("Yak, Masyuuuk.. ")
                        .setTitle("Hangga Aji Sayekti \n Masukkan Nominal \n ")
                        .setView(input)
                        .setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });


                        //.setMessage();
                builder.show();
                //input.setText(barcode.displayValue);
            }
        });
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
}
