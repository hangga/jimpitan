package com.jimpitan.hangga.jimpitan.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.jimpitan.hangga.jimpitan.R;

/**
 * Created by sayekti on 7/22/18.
 */

public class MySwitch extends android.support.v7.widget.AppCompatImageView {

    boolean isChecked = false;

    public MySwitch(Context context) {
        super(context);
        setChecked(false);
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChecked(false);
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChecked(false);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (isChecked) {
            this.setBackgroundResource(R.mipmap.sun);
        } else {
            this.setBackgroundResource(R.mipmap.ic_mati);
        }
    }
}
