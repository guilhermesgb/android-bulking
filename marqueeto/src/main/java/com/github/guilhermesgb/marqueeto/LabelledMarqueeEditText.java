package com.github.guilhermesgb.marqueeto;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.widget.IconTextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class LabelledMarqueeEditText extends FrameLayout {

    private static final String TAG = LabelledMarqueeEditText.class.getSimpleName();
    public static final int MODE_EDIT = 0;
    public static final int MODE_MARQUEE = 1;

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private AttributeSet mAttrs;

    private TextInputLayout textInputLayout;
    private AppCompatEditText editText;
    private IconTextView textView;
    private RippleView rippleView;

    private String mText;
    private int mTextColor;
    private float mTextSize;
    private String mHint;
    private int mBaseColor;
    private int mHighlightColor;
    private int mIconColor;
    private String mIconKey;
    private IconDrawable mIconDrawable;
    private CharSequence mIconCharacter;
    private int mLabelColor;
    private int mErrorColor;
    private String mError;
    private boolean mErrorEnabled;
    private String mErrorCached;
    private int mPreferredMode;
    private int mCurrentMode;
    private int mInputType;

    private boolean mTextChanged = true;
    private boolean mTextColorChanged = true;
    private boolean mHintChanged = true;
    private boolean mIconChanged = true;
    private boolean mErrorChanged = true;
    private boolean mInputTypeChanged = true;
    private boolean mStyleColorsChanged = true;

    public LabelledMarqueeEditText(Context context) {
        this(context, null);
    }

    public LabelledMarqueeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Resources.Theme contextTheme;

    public LabelledMarqueeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAttrs = attrs;
        TypedArray customAttributes = context.obtainStyledAttributes(mAttrs,
                R.styleable.LabelledMarqueeEditText);
        int customLabelledMarqueeEditTextStyle = customAttributes
                .getResourceId(R.styleable.LabelledMarqueeEditText_labelledMarqueeEditTextStyle, -1);
        Resources.Theme theme = overrideThemeWithCustomStyle(context, customLabelledMarqueeEditTextStyle);
        final TypedArray themeAttributes = theme.obtainStyledAttributes(mAttrs, new int[]{
                R.attr.baseColor, R.attr.highlightColor, R.attr.iconColor,
                R.attr.labelColor, R.attr.errorColor
        }, R.attr.colorPrimary, 0);
        retrieveAttributesValues(customAttributes, themeAttributes);
        buildEditAndMarqueeViews(context);
        initEditAndMarqueeViews(true);
        resetContextTheme(theme);
    }

    private void resetContextTheme(Resources.Theme theme) {
        theme.setTo(getResources().newTheme());
        theme.setTo(contextTheme);
        contextTheme = null;
    }

    private Resources.Theme overrideThemeWithCustomStyle(Context context, int customAttributesStyle) {
        final Resources.Theme theme = context.getTheme();
        contextTheme = getResources().newTheme();
        contextTheme.setTo(theme);
        theme.applyStyle(R.style.LabelledMarqueeEditTextTheme, true);
        if (customAttributesStyle != -1) {
            theme.applyStyle(customAttributesStyle, true);
        }
        return theme;
    }

    private void retrieveAttributesValues(TypedArray customAttributes, TypedArray themeAttributes) {
        retrieveThemeAttributeValues(themeAttributes);
        retrieveCustomAttributeValues(customAttributes);
    }

    private void retrieveThemeAttributeValues(TypedArray themeAttributes) {
        TypedValue typedValue = new TypedValue();
        if (themeAttributes.getValue(themeAttributes.getIndex(0), typedValue)) {
            mBaseColor = typedValue.data;
        }
        else {
            mBaseColor = getResources().getColor(android.R.color.black);
        }
        if (themeAttributes.getValue(themeAttributes.getIndex(1), typedValue)) {
            mHighlightColor = typedValue.data;
        }
        else {
            mHighlightColor = getResources().getColor(android.R.color.black);
        }
        if (themeAttributes.getValue(themeAttributes.getIndex(2), typedValue)) {
            mIconColor = typedValue.data;
        }
        else {
            mIconColor = mBaseColor;
        }
        if (themeAttributes.getValue(themeAttributes.getIndex(3), typedValue)) {
            mLabelColor = typedValue.data;
        }
        else {
            mLabelColor = mHighlightColor;
        }
        if (themeAttributes.getValue(themeAttributes.getIndex(4), typedValue)) {
            mErrorColor = typedValue.data;
        }
        else {
            mErrorColor = mHighlightColor;
        }
        themeAttributes.recycle();
    }

    private void retrieveCustomAttributeValues(TypedArray customAttributes) {
        mText = customAttributes.getString(R.styleable.LabelledMarqueeEditText_android_text);
        mTextColor = customAttributes.getColor(R.styleable.LabelledMarqueeEditText_android_textColor,
                getResources().getColor(android.R.color.black));
        float maxSize = getResources().getDimension(R.dimen.labelled_marquee_edit_text_max_text_size);
        float maxSizeConverted = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, maxSize,
                getResources().getDisplayMetrics());
        mTextSize = customAttributes.getDimension(R.styleable.LabelledMarqueeEditText_android_textSize,
                getResources().getDimension(R.dimen.labelled_marquee_edit_text_default_text_size));
        float textSizeConverted = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize,
                getResources().getDisplayMetrics());
        if (textSizeConverted > maxSizeConverted) {
            mTextSize = maxSize;
        }
        mHint = customAttributes.getString(R.styleable.LabelledMarqueeEditText_android_hint);
        mIconKey = customAttributes.getString(R.styleable.LabelledMarqueeEditText_iconKey);
        if (mIconKey == null) {
            mIconDrawable = null;
            mIconCharacter = "";
        }
        else {
            mIconDrawable = new IconDrawable(getContext(), mIconKey)
                    .color(mIconColor).sizeRes(R.dimen.labelled_marquee_edit_text_default_icon_size_big);
            mIconCharacter = "   " + String.format(getResources()
                            .getString(R.string.labelled_marquee_edit_text_layout_icon_definition_template),
                    mIconKey, String.format("#%06X", (0xFFFFFF & mIconColor)),
                    "@dimen/labelled_marquee_edit_text_default_icon_size_small");
        }
        mErrorEnabled = customAttributes.getBoolean(R.styleable.LabelledMarqueeEditText_errorEnabled, true);
        String error = customAttributes.getString(R.styleable.LabelledMarqueeEditText_error);
        if (mErrorEnabled) {
            mError = error;
        }
        else {
            mError = "";
            mErrorCached = error;
        }
        mPreferredMode = customAttributes.getInt(R.styleable.LabelledMarqueeEditText_mode, MODE_MARQUEE);
        mInputType = customAttributes.getInt(R.styleable.LabelledMarqueeEditText_android_inputType,
                EditorInfo.TYPE_CLASS_TEXT);
        customAttributes.recycle();
    }

    private void buildEditAndMarqueeViews(Context context) {
        View rippleViewSource = LayoutInflater.from(context).inflate(R.layout.layout_ripple, this, false);
        rippleView = (RippleView) rippleViewSource.findViewById(R.id.labelled_marquee_edit_text_layout_ripple);
        rippleView.setId(doGenerateViewId());
        addView(rippleView);
        View editViewSource = LayoutInflater.from(context).inflate(R.layout.layout_edit, this, false);
        textInputLayout = (TextInputLayout) editViewSource
                .findViewById(R.id.labelled_marquee_edit_text_layout_label_wrapper);
        editText = (AppCompatEditText) editViewSource
                .findViewById(R.id.labelled_marquee_edit_text_layout_edit_text);
        editText.setId(doGenerateViewId());
        addView(textInputLayout);
        View marqueeViewSource = LayoutInflater.from(context).inflate(R.layout.layout_marquee, this, false);
        textView = (IconTextView) marqueeViewSource
                .findViewById(R.id.labelled_marquee_edit_text_layout_marquee_text);
        textView.setId(doGenerateViewId());
        addView(textView, new ViewGroup
                .LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private static synchronized int doGenerateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1;
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }
        else {
            return View.generateViewId();
        }
    }

    private void initEditAndMarqueeViews(boolean firstLoad) {
        if (mTextChanged || mIconChanged || mStyleColorsChanged) {
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, mIconDrawable, null);
            setText();
            setTextSize();
            setLabelColor();
            setCursorDrawableColor();
            mTextChanged = false;
            mIconChanged = false;
            mStyleColorsChanged = false;
        }
        if (mTextColorChanged) {
            setTextColor();
            mTextColorChanged = false;
        }
        if (mHintChanged) {
            setHint();
            mHintChanged = false;
        }
        if (mErrorChanged) {
            setErrorEnabled();
            setError();
        }
        if (mInputTypeChanged) {
            setInputType();
            mInputTypeChanged = false;
        }
        textView.setSelected(true);
        textInputLayout.setVisibility(getVisibility());
        if (mPreferredMode == MODE_MARQUEE) {
            enableMarqueeMode(mIconCharacter, firstLoad);
        }
        else if (mPreferredMode == MODE_EDIT) {
            enableEditMode();
        }
        final GestureDetectorCompat detector = new GestureDetectorCompat(getContext(),
                new GestureDetector.OnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {}

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return false;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        enableEditMode();
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        return false;
                    }

                });
        textView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return detector.onTouchEvent(event);
            }

        });
        final View.OnFocusChangeListener existingListener = editText.getOnFocusChangeListener();
        if (!(existingListener instanceof DisableEditModeOnFocusChangeListener)) {
            editText.setOnFocusChangeListener(
                    DisableEditModeOnFocusChangeListener.newInstance(this, existingListener)
            );
        }
    }

    private void setText() {
        editText.setText(mText);
        textView.setText((mText == null ? "" : mText) + mIconCharacter);
    }

    private void setTextSize() {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
    }

    private void setTextColor() {
        editText.setTextColor(mTextColor);
        textView.setTextColor(mTextColor);
    }

    private void setHint() {
        textInputLayout.setHint(mHint);
    }

    private void setLabelColor() {
        editText.setHighlightColor(mLabelColor);
    }

    private void setError() {
        textInputLayout.setError(mError);
    }

    private void setErrorEnabled() {
        textInputLayout.setErrorEnabled(mErrorEnabled);
    }

    private void setCursorDrawableColor() {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            final Drawable[] drawables = new Drawable[2];
            drawables[0] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            drawables[1] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            drawables[0].setColorFilter(mHighlightColor, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(mHighlightColor, PorterDuff.Mode.SRC_IN);
            try {
                Field fEditor = TextView.class.getDeclaredField("mEditor");
                fEditor.setAccessible(true);
                Object editor = fEditor.get(editText);
                Field fCursorDrawable = editor.getClass().getDeclaredField("mCursorDrawable");
                fCursorDrawable.setAccessible(true);
                fCursorDrawable.set(editor, drawables);
            }
            catch (Throwable ignored) {
                Field fCursorDrawable = TextView.class.getDeclaredField("mCursorDrawable");
                fCursorDrawable.setAccessible(true);
                fCursorDrawable.set(editText, drawables);
            }
        }
        catch (Throwable ignored) {
            Log.d(TAG, "Could not override EditText's cursor drawable color via reflection: "
                    + ignored.getMessage());
        }
    }

    private void setInputType() {
        editText.setInputType(mInputType);
    }

    private static final class DisableEditModeOnFocusChangeListener implements View.OnFocusChangeListener {

        private final WeakReference<LabelledMarqueeEditText> labelledMarqueeEditTextWeakReference;
        private final View.OnFocusChangeListener previousOnFocusChangeListener;

        public static DisableEditModeOnFocusChangeListener newInstance(final LabelledMarqueeEditText target,
                                                                       final View.OnFocusChangeListener listener) {
            WeakReference<LabelledMarqueeEditText> reference = new WeakReference<>(target);
            return new DisableEditModeOnFocusChangeListener(reference, listener);
        }

        private DisableEditModeOnFocusChangeListener(final WeakReference<LabelledMarqueeEditText> reference,
                                                     final View.OnFocusChangeListener listener) {
            labelledMarqueeEditTextWeakReference = reference;
            previousOnFocusChangeListener = listener;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            previousOnFocusChangeListener.onFocusChange(view, hasFocus);
            LabelledMarqueeEditText labelledMarqueeEditText = labelledMarqueeEditTextWeakReference.get();
            if (labelledMarqueeEditText != null) {
                if (!hasFocus) {
                    labelledMarqueeEditText.tintIconWithIconColor();
                    labelledMarqueeEditText.enableMarqueeMode(labelledMarqueeEditText.getIconCharacter(), false);
                    labelledMarqueeEditText.invalidate();
                    labelledMarqueeEditText.requestLayout();
                    labelledMarqueeEditText.rippleView
                            .setRippleColor(labelledMarqueeEditText.mBaseColor);
                }
                else {
                    labelledMarqueeEditText.tintIconWithHighlightColorIfApplicable();
                    labelledMarqueeEditText.tintSelectorDrawableWithHighlightColor();
                    labelledMarqueeEditText.rippleView.clearAnimation();
                    labelledMarqueeEditText.rippleView
                            .setRippleColor(labelledMarqueeEditText.mHighlightColor);
                    int x = labelledMarqueeEditText.rippleView.getWidth() -
                            (labelledMarqueeEditText.editText.getCompoundPaddingRight() / 2);
                    int y = labelledMarqueeEditText.rippleView.getHeight() -
                            (labelledMarqueeEditText.editText.getBaseline() +
                                    (labelledMarqueeEditText.editText
                                            .getCompoundDrawables()[2].getIntrinsicHeight() / 2));
                    labelledMarqueeEditText.rippleView.animateRipple(x, y);
                }
            }
        }

    }

    private void tintIconWithIconColor() {
        setIconColorTemporarily(mIconColor);
    }

    private void tintIconWithHighlightColorIfApplicable() {
        if (mIconColor != mHighlightColor) {
            setIconColorTemporarily(mHighlightColor);
        }
    }

    private void setIconColorTemporarily(int color) {
        final int oldIconColor = mIconColor;
        mIconColor = color;
        setIcon(mIconKey, false);
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, mIconDrawable, null);
        mIconColor = oldIconColor;
    }

    private void tintSelectorDrawableWithHighlightColor() {
        setSelectorDrawableColorTemporarily(mHighlightColor);
    }

    private void setSelectorDrawableColorTemporarily(int color) {
        Drawable middleHandle = ContextCompat.getDrawable(editText.getContext(),
                R.drawable.text_select_handle_middle_material);
        Drawable middleHandleWrapper = DrawableCompat.wrap(middleHandle);
        DrawableCompat.setTint(middleHandleWrapper, color);

        Drawable leftHandle = ContextCompat.getDrawable(editText.getContext(),
                R.drawable.text_select_handle_left_material);
        Drawable leftHandleWrapper = DrawableCompat.wrap(leftHandle);
        DrawableCompat.setTint(leftHandleWrapper, color);

        Drawable rightHandle = ContextCompat.getDrawable(editText.getContext(),
                R.drawable.text_select_handle_right_material);
        Drawable rightHandleWrapper = DrawableCompat.wrap(rightHandle);
        DrawableCompat.setTint(rightHandleWrapper, color);
    }

    private void enableEditMode() {
        mCurrentMode = MODE_EDIT;
        editText.setVisibility(View.VISIBLE);
        editText.setEnabled(true);
        textView.setVisibility(View.INVISIBLE);
        rippleView.clearAnimation();
        int x = rippleView.getWidth() - (editText.getCompoundPaddingRight() / 2);
        int y = rippleView.getHeight() - (editText.getBaseline() +
                (editText.getCompoundDrawables()[2].getIntrinsicHeight() / 2));
        rippleView.animateRipple(x, y);
    }

    private void enableMarqueeMode(final CharSequence iconCharacter, boolean firstLoad) {
        if (editText.getText().toString().trim().isEmpty()) {
            if (mPreferredMode == MODE_MARQUEE) {
                enableEditMode();
            }
            return;
        }
        if (firstLoad) {
            fadeLabelInIfTextNotEmpty();
        }
        mCurrentMode = MODE_MARQUEE;
        editText.setVisibility(View.INVISIBLE);
        editText.setEnabled(false);
        textView.setVisibility(View.VISIBLE);
        textView.setSelected(true);
        mText = editText.getText().toString();
        textView.setText(mText + iconCharacter);
    }

    private void fadeLabelInIfTextNotEmpty() {
        if (!isEmpty(true)) {
            textInputLayout.setVisibility(View.INVISIBLE);
            int duration = 500;
            final AnimationSet fadeIn = new AnimationSet(true);
            {
                fadeIn.setDuration(duration);
                fadeIn.setInterpolator(new AccelerateInterpolator());
                fadeIn.addAnimation(new AlphaAnimation(0, 1));
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textInputLayout.setVisibility(View.VISIBLE);
                    textInputLayout.startAnimation(fadeIn);
                }
            }, duration);
        }
    }

    public boolean isEmpty(boolean trim) {
        return (trim ? mText.trim().isEmpty() : mText.isEmpty());
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        mTextChanged = true;
        reloadEditAndMarqueeViews();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int color) {
        mTextColor = getResources().getColor(color);
        mTextColorChanged = true;
        reloadEditAndMarqueeViews();
    }

    public String getHint() {
        return mHint;
    }

    public void setHint(String hint) {
        mHint = hint;
        mHintChanged = true;
        reloadEditAndMarqueeViews();
    }

    public int getBaseColor() {
        return mBaseColor;
    }

    public int getHighlightColor() {
        return mHighlightColor;
    }

    public int getIconColor() {
        return mIconColor;
    }

    public String getIcon() {
        return mIconKey;
    }

    public IconDrawable getIconDrawable() {
        return mIconDrawable;
    }

    public CharSequence getIconCharacter() {
        return mIconCharacter;
    }

    public void setIcon(String iconKey) {
        setIcon(iconKey, true);
    }

    private void setIcon(String iconKey, boolean shouldReload) {
        mIconKey = iconKey;
        if (mIconKey == null) {
            mIconDrawable = null;
            mIconCharacter = "";
        } else {
            mIconDrawable = new IconDrawable(getContext(), mIconKey)
                    .color(mIconColor).sizeRes(R.dimen.labelled_marquee_edit_text_default_icon_size_big);
            mIconCharacter = "   " + String.format(getResources()
                            .getString(R.string.labelled_marquee_edit_text_layout_icon_definition_template),
                    mIconKey, String.format("#%06X", (0xFFFFFF & mIconColor)),
                    "@dimen/labelled_marquee_edit_text_default_icon_size_small");
        }
        mIconChanged = true;
        if (shouldReload) {
            reloadEditAndMarqueeViews();
        }
    }

    public int getLabelColor() {
        return mLabelColor;
    }

    public int getErrorColor() {
        return mErrorColor;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        if (mErrorEnabled) {
            mError = error;
        }
        else {
            mError = "";
            mErrorCached = error;
        }
        mErrorChanged = true;
        reloadEditAndMarqueeViews();
    }

    public boolean getErrorEnabled() {
        return mErrorEnabled;
    }

    public void setErrorEnabled(boolean errorEnabled) {
        mErrorEnabled = errorEnabled;
        if (!errorEnabled) {
            mErrorCached = mError;
            mError = "";
        }
        else {
            mError = mErrorCached;
        }
        mErrorChanged = true;
        reloadEditAndMarqueeViews();
    }

    public int getPreferredMode() {
        return mPreferredMode;
    }

    public void setMode(int mode) throws IllegalArgumentException {
        switch (mode) {
            case MODE_EDIT:
                mPreferredMode = MODE_EDIT;
                break;
            case MODE_MARQUEE:
                mPreferredMode = MODE_MARQUEE;
                break;
            default:
                throw new IllegalArgumentException(String
                        .format("LabelledMarqueeEditText doesn't support this mode (%d).", mode));
        }
        reloadEditAndMarqueeViews();
    }

    public int getCurrentMode() {
        return mCurrentMode;
    }

    public int getInputType() {
        return mInputType;
    }

    public void setInputType(int inputType) {
        mInputType = inputType;
        mInputTypeChanged = true;
        reloadEditAndMarqueeViews();
    }

    public void setCustomStyle(int customStyle) {
        Context context = getContext();
        Resources.Theme theme = overrideThemeWithCustomStyle(context, customStyle);
        final TypedArray themeAttributes = theme.obtainStyledAttributes(mAttrs, new int[]{
                R.attr.baseColor, R.attr.highlightColor, R.attr.iconColor,
                R.attr.labelColor, R.attr.errorColor
        }, R.attr.colorPrimary, 0);
        retrieveThemeAttributeValues(themeAttributes);
        removeAllViews();
        buildEditAndMarqueeViews(context);
        mStyleColorsChanged = true;
        reloadEditAndMarqueeViews();
    }

    public void reloadEditAndMarqueeViews() {
        //initEditAndMarqueeViews(false);
        invalidate();
        requestLayout();
    }

}
