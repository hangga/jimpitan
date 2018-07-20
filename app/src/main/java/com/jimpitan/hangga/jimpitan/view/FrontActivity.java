package com.jimpitan.hangga.jimpitan.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.api.ApiClient;
import com.jimpitan.hangga.jimpitan.api.model.ApiInterface;
import com.jimpitan.hangga.jimpitan.api.model.PostJimpitan;
import com.jimpitan.hangga.jimpitan.db.model.Nominal;
import com.jimpitan.hangga.jimpitan.db.model.Warga;
import com.nex3z.flowlayout.FlowLayout;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class FrontActivity extends BaseActivity {

    static final int PICK_DATA = 121;
    public ApiInterface mApiInterface;
    private ProgressBar send_progress;
    private LinearLayout layData;
    private ImageButton btnScanner;
    private TextInputLayout inputNominal;
    private EditText edtNominal;
    private FlowLayout flowRp;
    private Button btnSubmit;
    private Switch swtcFlash;
    private TextView txtDay, txtNama;
    private int jam, day, year, month;
    private String hari, sJam;
    private int nominal;
    private BarcodeCapture qrCamera;
    private int id;
    private TextWatcher rpWatcher = new TextWatcher() {
        private String current = "";

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            rupiahHandler(s, current);
        }
    };
    private BarcodeRetriever barcodeRetriever = new BarcodeRetriever() {
        @Override
        public void onRetrieved(final Barcode barcode) {
            Log.d("JIMPITAN-KETEMU", barcode.displayValue);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnDataFound(barcode.displayValue);
                }
            });

            //qrCamera.stopScanning();
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
        setContentView(R.layout.activity_front);
        initToolBar();

        txtDay = (TextView) findViewById(R.id.txtDay);
        txtNama = (TextView) findViewById(R.id.txtNama);
        send_progress = (ProgressBar) findViewById(R.id.send_progress);
        layData = (LinearLayout) findViewById(R.id.layData);
        btnScanner = (ImageButton) findViewById(R.id.btnScanner);
        inputNominal = (TextInputLayout) findViewById(R.id.inputNominal);
        edtNominal = (EditText) findViewById(R.id.edtNominal);
        edtNominal.addTextChangedListener(rpWatcher);
        flowRp = (FlowLayout) findViewById(R.id.flowRp);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        swtcFlash = (Switch) findViewById(R.id.swtcFlash);
        qrCamera = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        //initCamera();
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        swtcFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrCamera.setShowDrawRect(true)
                                .shouldAutoFocus(true)
                                .setShowFlash(swtcFlash.isChecked())
                                .setBarcodeFormat(Barcode.ALL_FORMATS);
                        qrCamera.setRetrieval(barcodeRetriever);
                        qrCamera.refresh();
                    }
                });
            }
        });

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (isValidSend())
                attemptSend();
            }
        });
    }

    private void ShowSnackBar(String message) {
        Snackbar.make(findViewById(R.id.activityRoot), message, Snackbar.LENGTH_SHORT).show();
    }

    private boolean isValidSend() {
        boolean isValid = true;
        if (nominal == 0) {
            isValid = false;
        }
        if (jam >= 0 && jam < 5) {
            isValid = true;
        } else {
            isValid = false;
            ShowSnackBar("Saiki ki ki jam piro e ?");
        }

        return isValid;
    }

    private void attemptSend() {
        //if (!isValidSend()) return;
        btnSubmit.setVisibility(View.GONE);
        send_progress.setVisibility(View.VISIBLE);
        try {
            String sNominal = edtNominal.getText().toString().replace(".", "");
            nominal = Integer.parseInt(sNominal);


            String generatedUniqueId = String.valueOf(id) + String.valueOf(day) + String.valueOf(month)
                    + String.valueOf(year) + hari;

            //Log.d("JIMPITAN", generatedUniqueId);
            mApiInterface.postJimpitan(
                    generatedUniqueId,
                    hari,
                    String.valueOf(day),
                    String.valueOf(month),
                    String.valueOf(year),
                    String.valueOf(sJam),
                    txtNama.getText().toString(),
                    nominal).enqueue(new Callback<PostJimpitan>() {
                @Override
                public void onResponse(Call<PostJimpitan> call, Response<PostJimpitan> response) {
                    //showProgress(false);
                    OnDataSend();
                    btnSubmit.setVisibility(View.VISIBLE);
                    send_progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<PostJimpitan> call, Throwable t) {
                    //showProgress(false);
                }
            });
        } catch (Exception e) {
            btnSubmit.setVisibility(View.VISIBLE);
            send_progress.setVisibility(View.GONE);
        }

    }

    private void rupiahHandler(Editable s, String current) {
        if (!s.toString().equals(current)) {
            edtNominal.removeTextChangedListener(rpWatcher);

            Locale local = new Locale("id", "id");
            String replaceable = String.format("[Rp,.\\s]",
                    NumberFormat.getCurrencyInstance().getCurrency()
                            .getSymbol(local));
            String cleanString = s.toString().replaceAll(replaceable,
                    "");

            double parsed;
            try {
                parsed = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {
                parsed = 0.00;
            }

            NumberFormat formatter = NumberFormat
                    .getCurrencyInstance(local);
            formatter.setMaximumFractionDigits(0);
            formatter.setParseIntegerOnly(true);
            String formatted = formatter.format((parsed));

            String replace = String.format("[Rp\\s]",
                    NumberFormat.getCurrencyInstance().getCurrency()
                            .getSymbol(local));
            String clean = formatted.replaceAll(replace, "");

            current = formatted;
            edtNominal.setText(clean);
            edtNominal.setSelection(clean.length());
            edtNominal.addTextChangedListener(rpWatcher);
        }
    }

    /*private void stopScan(){
        btnScanner.setVisibility(View.VISIBLE);
        qrCamera.stopScanning();
    }*/

    private void startScan() {
        initCamera();
        btnScanner.setVisibility(View.GONE);
    }

    private void initCamera() {
        layData.setVisibility(View.GONE);
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

    private void OnDataFound(String id) {
        layData.setVisibility(View.VISIBLE);
        btnScanner.setVisibility(View.GONE);
        //Warga warga = Warga.findWithQuery(Warga.class, "Select * from Warga where idwarga = ?", id).get(0);

        Warga warga = Warga.find(Warga.class, "idwarga = ?", id).get(0);

        if (warga != null) {
            Log.d("JIMPITAN-KETEMU", warga.getName());
            txtNama.setText(warga.getName());
            initDate();
            Log.d("JIMPITAN-KETEMU-yak", warga.getName());
        } else {
            Log.d("JIMPITAN-ORA-KETEMU", warga.getName());
            Snackbar.make(findViewById(R.id.relTop), "Omahe sopoe iki?", Snackbar.LENGTH_SHORT).show();
        }
        qrCamera.stopScanning();
       // qrCamera.
    }

    private void OnDataSend() {
        layData.setVisibility(View.GONE);
        btnScanner.setVisibility(View.VISIBLE);
        try {
            long size = Nominal.find(Nominal.class, "val = ?", String.valueOf(nominal)).size();
            if (size < 1) {
                new Nominal(String.valueOf(nominal)).save();
            }
        } catch (Exception e) {
        }
        btnScanner.setVisibility(View.VISIBLE);
    }

    private void initDate() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat hariIna = new SimpleDateFormat("EEEE");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        hariIna.setTimeZone(tz);

        SimpleDateFormat sJamFormat = new SimpleDateFormat("kk");
        SimpleDateFormat sJamFormatComplete = new SimpleDateFormat("kk:mm");

        sJam = sJamFormatComplete.format(c.getTime());
        jam = Integer.parseInt(sJamFormat.format(c.getTime()));
        day = c.get(Calendar.DATE);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;

        String[] daynames = getResources().getStringArray(R.array.hari);
        int dayname = c.get(Calendar.DAY_OF_WEEK);

        hari = daynames[dayname - 1]; //hariIna.format(c.getTime());
        txtDay.setText("Dinten " + hari + "\n");
        txtDay.append("Surya kaping " + simpleDateFormat.format(c.getTime()) + "\n");
        txtDay.append("Tabuh " + sJam + " WIB \n");

    }
}
