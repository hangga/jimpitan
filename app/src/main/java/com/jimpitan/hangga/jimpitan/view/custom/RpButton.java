package com.jimpitan.hangga.jimpitan.view.custom;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.jimpitan.hangga.jimpitan.R;

/**
 * Created by sayekti on 7/18/18.
 */

public class RpButton extends AppCompatButton {

    private long val;

    public RpButton(Context context) {
        super(context, null, R.style.MyButton);
    }

    public RpButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.style.MyButton);
    }

    public RpButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.style.MyButton);
    }

    public long getVal() {
        return val;
    }

    public void setVal(long val) {
        this.val = val;
        this.setText(String.valueOf(val));
    }

    public void setVal(String val) {
        this.val = Long.parseLong(val.replace(".", ""));
        this.setText(String.valueOf(val));
    }

    private void init(Context context) {
        setTextColor(ContextCompat.getColor(context, R.color.putih));
    }
}
