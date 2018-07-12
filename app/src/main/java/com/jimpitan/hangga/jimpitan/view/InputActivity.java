package com.jimpitan.hangga.jimpitan.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jimpitan.hangga.jimpitan.R;
import com.jimpitan.hangga.jimpitan.db.model.Warga;

import java.text.NumberFormat;
import java.util.Locale;

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
    private View mFormView;

    private TextView txtNama;
    private int id;
    private Warga warga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        initToolBar();
        // Set up the login form.
        mNominal = (EditText) findViewById(R.id.nominal);
        mNominal.addTextChangedListener(new TextWatcher() {
            private String current = "";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    mNominal.removeTextChangedListener(this);

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
                    mNominal.addTextChangedListener(this);
                }
            }
        });

        txtNama = (TextView) findViewById(R.id.txtNama);

        id = getIntent().getIntExtra("id", -1);

        if (id > -1){
            warga = getWarga(id); //daoImplementation.getWarga(id);
            txtNama.setText(warga.getName());
        }

        Button mSendButton = (Button) findViewById(R.id.sendBtn);
        mSendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSend();
            }
        });

        mFormView = findViewById(R.id.send_form);
        mProgressView = findViewById(R.id.send_progress);
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSend() {
        if (mAuthTask != null) {
            return;
        }

        mNominal.setError(null);

        // Store values at the time of the login attempt.
        String nominal = mNominal.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(nominal)) {
            mNominal.setError(getString(R.string.kososng));
            focusView = mNominal;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);/*
            mAuthTask = new sendJimpitanTask(id, nominal);
            mAuthTask.execute((Void) null);*/
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

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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

