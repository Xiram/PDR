package com.example.guillaume2.pdr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Guillaume2 on 15/03/2016.
 */
public class MapView extends View {

    Bitmap bitmap;
    float x = 200;
    float y = 200;

    private Context context;

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        // TODO Auto-generated constructor stub
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
    }

    public boolean move(float dir, float dist) {
        // TODO Auto-generated method stub

        double dx=Math.cos((double)dir)*dist;
        double dy=Math.sin((double)dir)*dist;

        x+=dx;
        y+=dy;

        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
