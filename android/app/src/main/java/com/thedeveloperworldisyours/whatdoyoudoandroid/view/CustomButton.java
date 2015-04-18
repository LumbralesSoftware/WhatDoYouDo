package com.thedeveloperworldisyours.whatdoyoudoandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.thedeveloperworldisyours.whatdoyoudoandroid.MyApp;
import com.thedeveloperworldisyours.whatdoyoudoandroid.R;

/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class CustomButton extends Button {
    public CustomButton(Context context) {
        super(context);
        initButton(null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton(attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButton(attrs);
    }

    private void initButton(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditTextForm);
            //String fontName = a.getString(R.styleable.EditTextForm_fontName);
            //if (fontName!=null) {
            setTypeface(MyApp.Fonts.PENCIL);
            //}
            a.recycle();
        }
    }
}
