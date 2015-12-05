package com.mmga.metroloading;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RectIndicator {
    private int width;
    private int height;
    private int centerPositionX;
    private int centerPositionY;
    private int alpha = 255;
    private int color;

    private int shape;

    private int radius;


    private boolean hasShadow = false;
    private final Paint bodyPaint;
    private Paint shadowPaint;
    private int transformColorMode = MetroLoadingView.COLOR_TRANS_MODE_NONE;

    public RectIndicator(Paint bodyPaint) {
        this.bodyPaint = bodyPaint;
        hasShadow = false;
    }

    public RectIndicator(Paint bodyPaint, Paint shadowPaint) {
        this.bodyPaint = bodyPaint;
        this.shadowPaint = shadowPaint;
        hasShadow = true;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCenterPositionX() {
        return centerPositionX;
    }

    public void setCenterPositionX(int centerPositionX) {
        this.centerPositionX = centerPositionX;
    }

    public int getCenterPositionY() {
        return centerPositionY;
    }

    public void setCenterPositionY(int centerPositionY) {
        this.centerPositionY = centerPositionY;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getAlpha() {

        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void drawItself(Canvas canvas) {
        bodyPaint.setAlpha(alpha);
        if (transformColorMode != MetroLoadingView.COLOR_TRANS_MODE_NONE) {
            bodyPaint.setColor(color);
        }
        if (shadowPaint != null) {
            shadowPaint.setAlpha(alpha);
        }
        switch (shape) {
            case MetroLoadingView.rectangle:
                if (hasShadow) {
                    canvas.drawRect((float) (centerPositionX - 0.25 * width),
                            (float) (centerPositionY - 0.25 * height),
                            (float) (centerPositionX + 0.75 * width),
                            (float) (centerPositionY + 0.75 * height), shadowPaint);
                }
                canvas.drawRect((float) (centerPositionX - 0.5 * width),
                        (float) (centerPositionY - 0.5 * height),
                        (float) (centerPositionX + 0.5 * width),
                        (float) (centerPositionY + 0.5 * height), bodyPaint);
                break;
            case MetroLoadingView.circle:
                if (hasShadow) {
                    canvas.drawCircle((float) (centerPositionX + 0.5 * radius), (float) (centerPositionY + 0.5 * radius), radius, shadowPaint);
                }
                canvas.drawCircle(centerPositionX, centerPositionY, radius, bodyPaint);
                break;
        }

    }


    public void setTransformColorMode(int transformColorMode) {
        this.transformColorMode = transformColorMode;
    }
}