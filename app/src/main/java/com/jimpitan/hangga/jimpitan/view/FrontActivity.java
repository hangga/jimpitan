package com.jimpitan.hangga.jimpitan.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.db.model.Warga;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

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
    private Switch swtcFlash;

    private BarcodeCapture qrCamera;

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
        swtcFlash = (Switch) findViewById(R.id.swtcFlash);
        qrCamera = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        initCamera();


        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void startScan(){
        btnScanner.setVisibility(View.GONE);
        qrCamera.refresh(true);
    }

    private void stopScan(){
        btnScanner.setVisibility(View.VISIBLE);
        qrCamera.stopScanning();
    }

    private void initCamera() {
        //layData.setVisibility(View.GONE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qrCamera.setShowDrawRect(true)
                        //.setSupportMultipleScan(supportMultiple.isChecked())
                        //.setTouchAsCallback(touchBack.isChecked())
                        .shouldAutoFocus(true)
                        .setShowFlash(swtcFlash.isChecked())
                        .setBarcodeFormat(Barcode.ALL_FORMATS);
                //.setCameraFacing(frontCam.isChecked() ? CameraSource.CAMERA_FACING_FRONT : CameraSource.CAMERA_FACING_BACK)
                //.setShouldShowText(drawText.isChecked());

                qrCamera.setRetrieval(barcodeRetriever);
                qrCamera.refresh();
            }
        });
    }

    private void OnDataFound(String id){
        layData.setVisibility(View.VISIBLE);
        Warga warga = Warga.findWithQuery(Warga.class , "Select * from Warga where idwarga = ?", id).get(0);

        if (warga!= null){
            /*    Intent intent = new Intent(ScannerActivity.this, InputActivity.class);
                intent.putExtra("id", Integer.parseInt(barcode.displayValue));
                startActivity(intent);*/
        } else {
            Snackbar.make(findViewById(R.id.relTop), "Omahe sopoe iki?", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void OnDataSend(){
        layData.setVisibility(View.GONE);
    }

    private BarcodeRetriever barcodeRetriever = new BarcodeRetriever() {
        @Override
        public void onRetrieved(Barcode barcode) {
            qrCamera.stopScanning();
            OnDataFound(barcode.displayValue);
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
}
