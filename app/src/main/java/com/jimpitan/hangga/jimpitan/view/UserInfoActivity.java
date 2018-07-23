package com.jimpitan.hangga.jimpitan.view;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimpitan.hangga.jimpitan.R;

public class UserInfoActivity extends BaseActivity {

    LinearLayout linInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        linInfo = (LinearLayout) findViewById(R.id.linInfo);
        linInfo.removeAllViews();
        initToolBar(getResources().getString(R.string.app_name), "Informasi Pengguna");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 16, 10, 16);

        TextView textView = new TextView(UserInfoActivity.this);
        textView.setLayoutParams(params);
        textView.setText("AKUN ANDA:\n" + googleaccount);
        linInfo.addView(textView);

        TextView ttx = new TextView(UserInfoActivity.this);
        ttx.setText("DEVICE MODEL:\n" + android.os.Build.MODEL);
        ttx.setLayoutParams(params);
        linInfo.addView(ttx);

        TextView ttcredit1 = new TextView(UserInfoActivity.this);
        ttcredit1.setText("Contributed:\n" + "~Wahyu Jamaludin (Pelindung)\n~Henry Handoko (Backend Admin) \n~Febrianto (Promotor) \n~Hangga Aji Sayekti (Developer)");
        ttcredit1.setLayoutParams(params);
        linInfo.addView(ttcredit1);

        TextView ttInfo = new TextView(UserInfoActivity.this);
        ttInfo.setText("Jimpitan Pintar © 2018");
        ttInfo.setLayoutParams(params);
        linInfo.addView(ttInfo);
    }
}
