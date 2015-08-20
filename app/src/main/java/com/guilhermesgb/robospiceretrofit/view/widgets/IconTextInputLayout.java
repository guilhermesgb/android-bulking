package com.guilhermesgb.robospiceretrofit.view.widgets;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.EditText;

import com.joanzapata.iconify.Iconify;

public class IconTextInputLayout extends TextInputLayout {

    public IconTextInputLayout(Context context) {
        super(context);
    }

    public IconTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHint(CharSequence hint, CharSequence icon) {
//        super.setHint("   " + hint);
        EditText editText = getEditText();
        editText.setHint(Iconify.compute(getContext(), icon + " " + hint, editText));
    }

}
