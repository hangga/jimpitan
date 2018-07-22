package com.jimpitan.hangga.jimpitan.view.custom;

import android.content.Context;
import android.widget.CompoundButton;

import com.jimpitan.hangga.jimpitan.R;

/**
 * Created by sayekti on 7/22/18.
 */

public class MySwitch extends android.support.v7.widget.AppCompatImageView {

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (isChecked){
            this.setBackgroundResource(R.mipmap.ic_murup);
            onCheckedChangeListener.onCheckedChanged(null, true);
        } else {
            this.setBackgroundResource(R.mipmap.ic_mati);
            onCheckedChangeListener.onCheckedChanged(null, false);
        }
    }

    boolean isChecked = false;

    public MySwitch(Context context) {
        super(context);
        setChecked(false);
    }

}
