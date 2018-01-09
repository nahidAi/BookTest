package com.test.booktestnotification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by USER on 13/12/2017.
 */

@SuppressLint("AppCompatCustomView")
public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        super(context);
        if (!isInEditMode()){
            setUpTextView();

        }

    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            setUpTextView();

        }
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()){
            setUpTextView();

        }
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()){
            setUpTextView();

        }
    }

    private  void  setUpTextView (){
        G g = (G) getContext().getApplicationContext();
        setTypeface(g.getFont());
    }
}
