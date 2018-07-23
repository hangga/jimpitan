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

        geterateTextItem("AKUN ANDA:\n" + googleaccount, params);
        geterateTextItem("DEVICE MODEL:\n" + android.os.Build.MODEL, params);
        geterateTextItem("Contributed:\n" + "~Wahyu Jamaludin (Pelindung)\n~Henry Handoko (Backend Admin) \n~Febrianto (Promotor) \n~Hangga Aji Sayekti (Developer)", params);
        geterateTextItem("Jimpitan Pintar © 2018", params);
    }

    private void geterateTextItem(String title, LinearLayout.LayoutParams params){
        TextView ttInfo = new TextView(UserInfoActivity.this);
        ttInfo.setText(title);
        ttInfo.setLayoutParams(params);
        linInfo.addView(ttInfo);
    }

}
