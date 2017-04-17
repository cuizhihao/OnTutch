package com.bwie.ontutch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ASUS on 2017/4/12.
 */

public class Yuan extends View {
    private Paint pa = new Paint();
    public Yuan(Context context) {
        super(context);
    }

    public Yuan(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        float wi = canvas.getWidth();
        float he = canvas.getHeight();
        pa.setColor(Color.GREEN);


        canvas.drawCircle(wi / 2, he / 2, wi / 2, pa);
        super.onDraw(canvas);

    }
}
