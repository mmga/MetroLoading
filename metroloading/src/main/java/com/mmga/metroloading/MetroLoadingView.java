package com.mmga.metroloading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MetroLoadingView extends View {

    private int mShape;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int mColor;
    private int mNumber;
    private boolean mHasShadow;
    private int mShadowColor;
    private int mDuration;
    private int mDelay;
    private Paint shadowPaint, bodyPaint;
    private boolean mFadeInOut;
    private boolean isAnimating = false;


    private final List<Animator> valueAnimators = new ArrayList<>();

    private List<RectIndicator> rectIndicators;

    public static final int rectangle = 0;
    public static final int circle = 1;
    public static final int COLOR_TRANS_MODE_NONE = 0;
    public static final int COLOR_TRANS_MODE_LINEAR = 1;
    public static final int COLOR_TRANS_MODE_SYMMETRY = 2;
    private boolean needTransform;
    private int mTransformHeight;
    private int mTransformWidth;
    private int mTransformRadius;
    private int centerPositionY;
    private AnimatorSet animatorSet;
    private int mTransformColor;
    private int mTransformColorMode;

    public MetroLoadingView(Context context) {
        super(context);
        initView(context, null);
    }

    public MetroLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MetroLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        bodyPaint = new Paint();
        bodyPaint.setColor(mColor);
        bodyPaint.setAntiAlias(true);
        shadowPaint = new Paint();
        shadowPaint.setColor(mShadowColor);
        shadowPaint.setAntiAlias(true);
        createIndicators();

    }

    private void createIndicators() {
        rectIndicators = new ArrayList<>();
        for (int i = 0; i < mNumber; i++) {
            if (mHasShadow) {
                rectIndicators.add(new RectIndicator(bodyPaint, shadowPaint));
            } else {
                rectIndicators.add(new RectIndicator(bodyPaint));
            }
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MetroLoadingView);
            mColor = a.getColor(R.styleable.MetroLoadingView_indicator_color, Color.parseColor("#007bff"));
            mTransformColor = a.getColor(R.styleable.MetroLoadingView_transform_color, Color.parseColor("#007bff"));
            mTransformColorMode = a.getInt(R.styleable.MetroLoadingView_transform_color_mode, COLOR_TRANS_MODE_NONE);
            mWidth = a.getDimensionPixelSize(R.styleable.MetroLoadingView_indicator_width, dp2px(4));
            mHeight = a.getDimensionPixelSize(R.styleable.MetroLoadingView_indicator_height, dp2px(9));
            mRadius = a.getDimensionPixelSize(R.styleable.MetroLoadingView_indicator_radius, dp2px(4));
            mNumber = a.getInt(R.styleable.MetroLoadingView_number, 5);
            mHasShadow = a.getBoolean(R.styleable.MetroLoadingView_has_shadow, false);
            mShadowColor = a.getColor(R.styleable.MetroLoadingView_shadow_color, Color.DKGRAY);
            mDuration = a.getInt(R.styleable.MetroLoadingView_duration_in_mills, 2000);
            mDelay = a.getInt(R.styleable.MetroLoadingView_interval_in_mills, 200);
            mShape = a.getInt(R.styleable.MetroLoadingView_indicator, rectangle);
            mTransformHeight = a.getDimensionPixelSize(R.styleable.MetroLoadingView_transform_height, mHeight);
            mTransformWidth = a.getDimensionPixelSize(R.styleable.MetroLoadingView_transform_width, mWidth);
            mTransformRadius = a.getDimensionPixelSize(R.styleable.MetroLoadingView_transform_radius, mRadius);
            needTransform = a.getBoolean(R.styleable.MetroLoadingView_transform, false);
            mFadeInOut = a.getBoolean(R.styleable.MetroLoadingView_fade, false);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        centerPositionY = size / 2;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (RectIndicator rectIndicator : rectIndicators) {
            rectIndicator.drawItself(canvas);
        }
    }

    public void start() {
//        Log.d("mmga", "start");
        this.setVisibility(VISIBLE);
        valueAnimators.clear();
        for (int i = 0; i < mNumber; i++) {
            createAnimator(rectIndicators.get(i), i * mDelay);
        }
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimators);
        animatorSet.setDuration(mDuration);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isAnimating) {
                    animatorSet.start();
//                    Log.d("mmga", "restart");

                } else {
//                    Log.d("mmga", "end");
                }
            }
        });
        animatorSet.start();

        isAnimating = true;
    }

    private void createAnimator(final RectIndicator indicator, int startDelay) {
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(new CustomInterpolator());
        animator.setFloatValues(0, 1f);
        animator.setDuration(mDuration);
        animator.setStartDelay(startDelay);
        animator.addUpdateListener(new RectIndicatorUpdateListener(indicator));
        indicator.setShape(mShape);
        indicator.setTransformColorMode(mTransformColorMode);
        valueAnimators.add(animator);
    }

    private class RectIndicatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private RectIndicator rectIndicator;

        public RectIndicatorUpdateListener(RectIndicator rectIndicator) {
            this.rectIndicator = rectIndicator;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float fraction = animation.getAnimatedFraction();
            float symmetry;
            if (fraction < 0.5) {
                symmetry = 2 * fraction;
            } else {
                symmetry = 2 * (1 - fraction);
            }
            int windowWidth = getWindowWidth();


            if (mTransformColorMode == COLOR_TRANS_MODE_LINEAR) {
                colorEvaluator(fraction, rectIndicator, mColor, mTransformColor);
            } else if (mTransformColorMode == COLOR_TRANS_MODE_SYMMETRY) {
                colorEvaluator(symmetry, rectIndicator, mColor, mTransformColor);
            } else {
                //mTransformColorMode == COLOR_TRANS_MODE_NONE   do noting;
            }


            rectIndicator.setCenterPositionY(centerPositionY);

            if (mFadeInOut) {
                rectIndicator.setAlpha((int) (255 * symmetry));
            }

            switch (mShape) {
                case (circle):
                    rectIndicator.setCenterPositionX((int) (fraction * (windowWidth + 2 * mRadius) - mRadius));

                    if (needTransform) {
                        rectIndicator.setRadius((int) (mRadius +  symmetry * (mTransformRadius - mRadius)));
                    } else {
                        rectIndicator.setRadius(mRadius);
                    }
                    break;

                case (rectangle):
                    rectIndicator.setCenterPositionX((int) (fraction * (windowWidth + mWidth) - 0.5 * mWidth));

                    if (needTransform) {
                        rectIndicator.setHeight((int) (mHeight + symmetry * (mTransformHeight - mHeight)));
                        rectIndicator.setWidth((int) (mWidth + symmetry * (mTransformWidth - mWidth)));
                    } else {
                        rectIndicator.setHeight(mHeight);
                        rectIndicator.setWidth(mWidth);
                    }
                    break;
            }
            invalidate();
        }
    }

    public void stop() {
        isAnimating = false;
        animatorSet.end();
        this.setVisibility(GONE);
//        Log.d("mmga", "canceled");
    }

    public boolean isAnimating() {
        return isAnimating;
    }


    private int getWindowWidth() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    private class CustomInterpolator implements TimeInterpolator {
        @Override
        public float getInterpolation(float input) {
            return (float) (Math.asin(2 * input - 1) / Math.PI + 0.5);
        }
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    private ArgbEvaluator evaluator = new ArgbEvaluator();

    private void colorEvaluator(float fraction, RectIndicator indicator, int startColor, int EndColor) {
        int a = (int) evaluator.evaluate(fraction, startColor, EndColor);
        indicator.setColor(a);
    }

}

