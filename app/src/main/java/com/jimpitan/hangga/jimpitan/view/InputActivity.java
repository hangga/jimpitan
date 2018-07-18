package com.jimpitan.hangga.jimpitan.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.api.model.PostJimpitan;
import com.jimpitan.hangga.jimpitan.db.model.Warga;
import com.jimpitan.hangga.jimpitan.util.Utils;
import com.jimpitan.hangga.jimpitan.view.custom.RpButton;
import com.nex3z.flowlayout.FlowLayout;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class InputActivity extends BaseActivity /*implements LoaderCallbacks<Cursor> */ {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private sendJimpitanTask mAuthTask = null;

    // UI references.
    /*private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    */

    private EditText mNominal;
    private View mProgressView;
    /*private View mFormView;*/
    private RelativeLayout activityRoot;

    private TextView txtDay, txtNama;
    private int id;
    private Warga warga;

    private int jam, day, year, month;
    private String hari, sJam;
    private int nominal;
    private FlowLayout flowRp;

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



    private void initMain() {
        flowRp.removeAllViews();
        //noms = daoImplementation.getNoms();
        if (noms != null && noms.size() > 0) {
            for (int i = 0; i < noms.size(); i++) {

                final RpButton rpButton = new RpButton(this, null, R.style.MyButton);
                rpButton.setVal(noms.get(i).getVal());
                rpButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mNominal.setText(String.valueOf(rpButton.getVal()));
                    }
                });
                flowRp.addView(rpButton);

            }
        } else {

        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void rupiahHandler(Editable s, String current) {
        if (!s.toString().equals(current)) {
            mNominal.removeTextChangedListener(rpWatcher);

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
            mNominal.setText(clean);
            mNominal.setSelection(clean.length());
            mNominal.addTextChangedListener(rpWatcher);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        initToolBar();

        activityRoot = (RelativeLayout) findViewById(R.id.activityRoot);
        txtDay = (TextView) findViewById(R.id.txtDay);
        mNominal = (EditText) findViewById(R.id.nominal);
        flowRp = (FlowLayout) findViewById(R.id.flowRp);

        initDate();
        initMain();

        mNominal.addTextChangedListener(rpWatcher);

        txtNama = (TextView) findViewById(R.id.txtNama);

        id = getIntent().getIntExtra("id", -1);

        if (id > -1) {
            warga = getWarga(id); //daoImplementation.getWarga(id);
            txtNama.setText(warga.getName());
        }

        findViewById(R.id.fabSend).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSend();
            }
        });

        //mFormView = findViewById(R.id.send_form);
        mProgressView = findViewById(R.id.send_progress);
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

    private void ShowSnackBar(String message) {
        Snackbar.make(findViewById(R.id.activityRoot), message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSend() {
        if (!isValidSend()) return;
        try {
            String sNominal = mNominal.getText().toString().replace(".", "");
            nominal = Integer.parseInt(sNominal);

            String generatedUniqueId = String.valueOf(day) + String.valueOf(month)
                    + String.valueOf(year) + hari;

            mApiInterface.postJimpitan(Utils.SpreedsheetId,
                    "jimpit",
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
                    onBackPressed();
                    //finish();
                    //response.message();
                }

                @Override
                public void onFailure(Call<PostJimpitan> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            /*mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });*/

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class sendJimpitanTask extends AsyncTask<Void, Void, Boolean> {

        private final int id;
        private final int nominal;

        sendJimpitanTask(int id, int nominal) {
            this.id = id;
            this.nominal = nominal;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            /*for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mNominal.setError(getString(R.string.error_field_required));
                mNominal.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

