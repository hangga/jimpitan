package com.jimpitan.hangga.jimpitan.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.jimpitan.hangga.jimpitan.util.Utils;
import com.jimpitan.hangga.jimpitan.view.custom.MySwitch;
import com.jimpitan.hangga.jimpitan.view.custom.RpButton;
import com.jimpitan.hangga.jimpitan.view.custominterface.OnFinishListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class FrontActivity extends BaseActivity {

    public ApiInterface mApiInterface;
    boolean isDataFound = false;
    private ProgressBar send_progress;
    private RelativeLayout layData;
    private ImageButton btnScanner;
    private TextInputLayout inputNominal;
    private EditText edtNominal;
    private HorizontalScrollView flowRp;
    private LinearLayout layRp;
    private Button btnSubmit;
    private MySwitch swtcFlash;
    private TextView txtDay, txtNama;
    private int jam, day, year, month;
    private String hari, sJam;
    private int dayNum = 0;
    private int nominal = 0;
    private BarcodeCapture qrCamera;
    private int id = 0;
    private TextWatcher rpWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            rupiahHandler(s);
        }
    };

    private BarcodeRetriever barcodeRetriever = new BarcodeRetriever() {
        @Override
        public void onRetrieved(final Barcode barcode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (layData.getVisibility() == View.GONE)
                        findDataById(barcode.displayValue);
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        initToolBar();

        txtDay = (TextView) findViewById(R.id.txtDay);
        txtNama = (TextView) findViewById(R.id.txtNama);
        send_progress = (ProgressBar) findViewById(R.id.send_progress);
        layData = (RelativeLayout) findViewById(R.id.layData);
        btnScanner = (ImageButton) findViewById(R.id.btnScanner);
        inputNominal = (TextInputLayout) findViewById(R.id.inputNominal);
        edtNominal = (EditText) findViewById(R.id.edtNominal);
        edtNominal.addTextChangedListener(rpWatcher);
        flowRp = (HorizontalScrollView) findViewById(R.id.flowRp);
        layRp = (LinearLayout) findViewById(R.id.layRp);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        swtcFlash = (MySwitch) findViewById(R.id.swtcFlash);
        qrCamera = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        layData.setVisibility(View.GONE);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        initMain();

        swtcFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swtcFlash.setChecked(!swtcFlash.isChecked());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrCamera.setShowDrawRect(false)
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
                initCamera();
                btnScanner.setVisibility(View.GONE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSend();
            }
        });

        findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layData.getVisibility() == View.VISIBLE) {
                    layData.setVisibility(View.GONE);
                    id = 0;
                    nominal = 0;
                    edtNominal.setText("");
                } else {
                    btnScanner.setVisibility(View.VISIBLE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qrCamera.setShowDrawRect(false)
                                    .shouldAutoFocus(true)
                                    .setShowFlash(false)
                                    .setBarcodeFormat(Barcode.ALL_FORMATS);
                            qrCamera.setRetrieval(barcodeRetriever);
                            qrCamera.refresh();
                        }
                    });
                    swtcFlash.setChecked(false);
                }
            }
        });
    }

    private void ShowSnackBar(String message, int background) {
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.activityRoot), message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(FrontActivity.this, R.color.putih));
        textView.setTextSize(20);

        View sbView = snackbarView;
        sbView.setBackgroundColor(ContextCompat.getColor(FrontActivity.this, background));
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            startActivity(new Intent(FrontActivity.this, UserInfoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void OnDataSend(int status, String message) {
        if (status == 200) {
            layData.setVisibility(View.GONE);
            btnScanner.setVisibility(View.VISIBLE);
            try {
                long size = Nominal.find(Nominal.class, "val = ?", String.valueOf(nominal)).size();
                if (size < 1) {
                    new Nominal(String.valueOf(nominal)).save();
                }
            } catch (Exception e) {
            }
            ShowSnackBar(message, R.color.color_green_seger);
            isDataFound = false;
            edtNominal.setText("");
            btnSubmit.setEnabled(false);
            initCamera();
        } else {
            ShowSnackBar(message, R.color.colorRed);
        }
    }

    private void attemptSend() {
        btnSubmit.setVisibility(View.INVISIBLE);
        send_progress.setVisibility(View.VISIBLE);
        try {
            String sNominal = edtNominal.getText().toString().replace(".", "");
            nominal = Integer.parseInt(sNominal);

            String generatedUniqueId = String.valueOf(id) + String.valueOf(day) + String.valueOf(month)
                    + String.valueOf(year) + String.valueOf(dayNum);

            mApiInterface.postJimpitanNew(
                    generatedUniqueId, hari,
                    String.valueOf(day),
                    String.valueOf(month),
                    String.valueOf(year),
                    String.valueOf(sJam), jam,
                    txtNama.getText().toString(),
                    nominal, googleaccount).enqueue(new Callback<PostJimpitan>() {
                @Override
                public void onResponse(Call<PostJimpitan> call, Response<PostJimpitan> response) {
                    OnDataSend(response.body().getStatus(), response.body().getMessage());
                    btnSubmit.setVisibility(View.VISIBLE);
                    send_progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<PostJimpitan> call, Throwable t) {
                    btnSubmit.setVisibility(View.VISIBLE);
                    send_progress.setVisibility(View.GONE);
                    ShowSnackBar(getString(R.string.cek_koneksi), R.color.colorBlackGimana);
                }
            });
        } catch (Exception e) {
            btnSubmit.setVisibility(View.VISIBLE);
            send_progress.setVisibility(View.GONE);
        }
    }

    private void rupiahHandler(Editable s) {
        if (!s.toString().isEmpty()) {
            btnSubmit.setEnabled(isDataFound);
            edtNominal.removeTextChangedListener(rpWatcher);
            String rupiah = Utils.rupiah(s.toString());
            edtNominal.setText(rupiah);
            edtNominal.setSelection(rupiah.length());
            edtNominal.addTextChangedListener(rpWatcher);
        } else {
            btnSubmit.setEnabled(false);
        }
    }

    private void initMain() {

        RelativeLayout.LayoutParams prm = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        prm.addRule(RelativeLayout.CENTER_IN_PARENT);
        prm.width = (getScreenWidth() / 2) + 80;
        findViewById(R.id.viewFinder).setLayoutParams(prm);
        btnScanner.setVisibility(View.VISIBLE);
        send_progress.setVisibility(View.GONE);
        btnSubmit.setEnabled(false);
        layRp.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.leftMargin = 12;
        List<Nominal> noms = Nominal.listAll(Nominal.class);
        for (int i = 0; i < noms.size(); i++) {
            RpButton btn = new RpButton(FrontActivity.this);
            int nom = Integer.parseInt(noms.get(i).getVal());
            if (nom == 0) {
                btn.setText("KOSONG");
                btn.setBackgroundResource(R.drawable.selector_btn_nominal_kosong);
            } else if (nom < 500) {
                btn.setText("Rp. " + Utils.rupiah(noms.get(i).getVal()));
                btn.setBackgroundResource(R.drawable.selector_btn_nominal_kosong);
            } else if (nom > 1000) {
                btn.setText("Rp. " + Utils.rupiah(noms.get(i).getVal()));
                btn.setBackgroundResource(R.drawable.selector_btn_nominal_banyak);
            } else {
                btn.setText("Rp. " + Utils.rupiah(noms.get(i).getVal()));
                btn.setBackgroundResource(R.drawable.selector_btn_nominal);
            }
            btn.setVal(noms.get(i).getVal());
            btn.setTextSize(18);
            btn.setTextColor(ContextCompat.getColor(FrontActivity.this, R.color.putih));
            btn.setPadding(22, 14, 24, 14);
            btn.setLayoutParams(params);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtNominal.setText(String.valueOf(((RpButton) view).getVal()));
                }
            });
            layRp.addView(btn);
        }
    }

    private void initCamera() {
        layData.setVisibility(View.GONE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qrCamera.setShowDrawRect(false)
                        .shouldAutoFocus(true)
                        .setShowFlash(swtcFlash.isChecked())
                        .setBarcodeFormat(Barcode.ALL_FORMATS);
                qrCamera.setRetrieval(barcodeRetriever);
                qrCamera.refresh();
            }
        });
    }

    private void findDataById(final String sid) {
        try {
            id = Integer.parseInt(sid);
            layData.setVisibility(View.VISIBLE);
            btnScanner.setVisibility(View.GONE);

            List<Warga> warga = Warga.find(Warga.class, "idwarga = ?", sid);

            if (warga.size() > 0) {
                txtNama.setText(warga.get(0).getName());
                initDate();
                isDataFound = true;
                btnSubmit.setEnabled(isDataFound);
                if (isVibrate()) doVibrating();
            } else {
                send_progress.setVisibility(View.VISIBLE);
                syncData(new OnFinishListener() {
                    @Override
                    public void OnFinish() {
                        findDataById(sid);
                        send_progress.setVisibility(View.GONE);
                    }
                });
            }
        } catch (Exception e) {

        }
    }


    private void initDate() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat hariIna = new SimpleDateFormat("EEEE");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        hariIna.setTimeZone(tz);

        SimpleDateFormat sJamFormat = new SimpleDateFormat("kk");
        SimpleDateFormat sJamFormatComplete = new SimpleDateFormat("kk:mm");

        sJam = sJamFormatComplete.format(c.getTime());
        jam = Integer.parseInt(sJamFormat.format(c.getTime()));
        if (jam == 24) jam = 0;
        day = c.get(Calendar.DATE);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;

        String[] daynames = getResources().getStringArray(R.array.hari);
        dayNum = c.get(Calendar.DAY_OF_WEEK);

        hari = daynames[dayNum - 1];
        txtDay.setText(hari + ", " + simpleDateFormat.format(c.getTime()) + "\n");
        txtDay.append("Pukul, " + sJam + " WIB\n");

    }
}
