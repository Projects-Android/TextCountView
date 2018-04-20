package com.ev.travelcard.library;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;

/**
 * counting text size
 * Created by EV on 2018/4/19.
 */

public class TextCountView extends AppCompatTextView {

    private static final String TAG = TextCountView.class.getSimpleName();

    public static final int TIP_STYLE_STOCK = 0x01;
    public static final int TIP_STYLE_HIGHLIGHT = 0x02;
    public static final int TIP_STYLE_ANIMATE = 0x04;

    private int mCurCount, mMaxCount;
    private String mSeperator;

    private @ColorInt int mHighlightColor;
    private int mOorTipStyle;

    private AnimatorSet mAnimator;

    public TextCountView(Context context) {
        super(context);
    }

    public TextCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextCountView);
        mHighlightColor = ta.getColor(R.styleable.TextCountView_highlightColor, Color.RED);
        mOorTipStyle = ta.getInt(R.styleable.TextCountView_oor_tip_style, 0x02);
        mMaxCount = ta.getInt(R.styleable.TextCountView_maxCount, 100);
        mSeperator = ta.getString(R.styleable.TextCountView_numSeperator);
        if (TextUtils.isEmpty(mSeperator)) {
            mSeperator = " / ";
        }

        initAnimator((int) getTextSize());
        setCount(0);
    }

    private void initAnimator(int minSize) {
        ObjectAnimator sizeAnimator = ObjectAnimator.ofInt(this, "AnimatorScale", minSize, minSize * 2, minSize);
        sizeAnimator.setRepeatMode(ValueAnimator.RESTART);
        sizeAnimator.setRepeatCount(0);
        mAnimator = new AnimatorSet();
        mAnimator.play(sizeAnimator);
        mAnimator.setDuration(200);
        mAnimator.setInterpolator(new AccelerateInterpolator());
    }

    /**
     * method for object animation
     * @param size
     * @hide
     */
    public void setAnimatorScale(int size) {
        SpannableString spannableString = new SpannableString(getText());
        int end = String.valueOf(mCurCount).length();
        if ((mOorTipStyle & TIP_STYLE_HIGHLIGHT) == TIP_STYLE_HIGHLIGHT) { // high light
            spannableString.setSpan(
                    new ForegroundColorSpan(mHighlightColor),
                    0,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableString.setSpan(new AbsoluteSizeSpan(size), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannableString);
    }

    /**
     * set current count of text
     * @param count
     */
    public void setCount(int count) {

        boolean outOfRange = false;
        if (count > mMaxCount) { // out of range
            if ((mOorTipStyle & TIP_STYLE_STOCK) != TIP_STYLE_STOCK) { // no stock style
                this.mCurCount = count;
            }
            outOfRange = true;
        } else {
            this.mCurCount = count;
        }

        SpannableString spannableString = new SpannableString(mCurCount + mSeperator + mMaxCount);
        if (outOfRange) {
            if ((mOorTipStyle & TIP_STYLE_HIGHLIGHT) == TIP_STYLE_HIGHLIGHT) { // high light
                spannableString.setSpan(
                        new ForegroundColorSpan(mHighlightColor),
                        0,
                        String.valueOf(mCurCount).length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if ((mOorTipStyle & TIP_STYLE_ANIMATE) == TIP_STYLE_ANIMATE && null != mAnimator) { // show animation
                mAnimator.start();
            }
        }
        setText(spannableString);
    }

    public void setCount(String count) {
        if (TextUtils.isEmpty(count)) {
            return;
        }

        try {
            int iCount = Integer.parseInt(count);
            setCount(iCount);
        } catch (NumberFormatException e) {
            Log.i(TAG, "Method setCount() only supports numbers!", e);
        }
    }

    public void setCurCount(int curCount) {
        this.mCurCount = curCount;
    }

    public void setMaxCount(int maxCount) {
        this.mMaxCount = maxCount;
    }

    public void setSeperator(String seperator) {
        this.mSeperator = seperator;
    }

    public void setHighlightColor(int highlightColor) {
        this.mHighlightColor = highlightColor;
    }

    public void setOorTipStyle(int oorTipStyle) {
        this.mOorTipStyle = oorTipStyle;
    }
}
