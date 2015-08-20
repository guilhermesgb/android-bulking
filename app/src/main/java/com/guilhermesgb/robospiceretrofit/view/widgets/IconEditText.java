package com.guilhermesgb.robospiceretrofit.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.joanzapata.iconify.Iconify;

public class IconEditText extends EditText {

    public IconEditText(Context context) {
        super(context);
        init();
    }

    public IconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setTransformationMethod(null);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(Iconify.compute(getContext(), text, this), type);
    }

}
