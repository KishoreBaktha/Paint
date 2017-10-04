package com.example.kishorebaktha.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by KISHORE BAKTHA on 7/29/2017.
 */

public class CanvasView extends View{
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath,mPath2;
    private ColoredPath coloredPath;
    private ArrayList<ColoredPath> coloredPaths;
    private ErasePath erasePath;
    MainActivity mainActivity;
  int set;
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        set=mainActivity.getSet();
    }
    private ArrayList<ErasePath> erasePaths;
    private int lastColor,lastColor2;
    private Paint mPaint;
    float lastsize;
    int setColour=0,paintcolour=0;
    private float mX,mY;
    private static final float TOLERANCE=5;
    Context context;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        lastColor = Color.BLACK;
        lastsize=6f;
        mPaint = createPaint();
        coloredPaths = new ArrayList<ColoredPath>();
        coloredPath = new ColoredPath(new Path(), mPaint);
        coloredPaths.add(coloredPath);
        erasePaths = new ArrayList<ErasePath>();
        erasePath = new ErasePath(new Path(), createPaint2());
        erasePaths.add(erasePath);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(setColour==1)
        {
            check();
            canvas.drawColor(Color.BLUE);
        }

        if(setColour==2) {
            check();
            canvas.drawColor(Color.RED);

        }
        if(setColour==3) {
            check();
            canvas.drawColor(Color.GREEN);
        }
        if(setColour==4) {
            check();
            canvas.drawColor(Color.YELLOW);
        }
        if(setColour==5) {
            check();
            canvas.drawColor(Color.CYAN);
        }
        for (ColoredPath p : coloredPaths) {
            canvas.drawPath(p.path, p.paint);
        }
        for (ErasePath p : erasePaths) {
            canvas.drawPath(p.path, p.paint);
        }
    }

    //called to measure size or when size is changed
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //AGRG_8888- EACH PIXEL IS STORED IN 4 BYTES
        mBitmap= Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);
    }
    private void StartTouch(float x,float y)
    {
        set=mainActivity.getSet();
        if(set==0) {
            coloredPath.path.moveTo(x, y);
            mX = x;
            mY = y;
        }
        else
        {
            erasePath.path.moveTo(x, y);
            mX = x;
            mY = y;
        }
    }
    private void moveTouch(float x,float y)
    {
        set=mainActivity.getSet();
        if(set==0)
        coloredPath.path.lineTo(x,y);
        else
        {
            erasePath.path.lineTo(x,y);
        }
    }
    public void clearCanvas()
    {
        coloredPaths.clear();
        invalidate();
        mPath = new Path();
        coloredPath = new ColoredPath(mPath, createPaint());
        coloredPaths.add(coloredPath);
        erasePaths.clear();
        invalidate();
        mPath2 = new Path();
        erasePath = new ErasePath(mPath2, createPaint2());
        erasePaths.add(erasePath);
    }
    private void upTouch()
    {
        set=mainActivity.getSet();
        if(set==0) {
            mPath = new Path();
            coloredPath = new ColoredPath(mPath, createPaint());
            coloredPaths.add(coloredPath);
        }
       else
        {
            mPath2 = new Path();
            erasePath = new ErasePath(mPath2, createPaint2());
            erasePaths.add(erasePath);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: StartTouch(x,y);
                                            invalidate();
                                             break;
            case MotionEvent.ACTION_MOVE: moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP: upTouch();
                invalidate();
                break;
        }
        return true;
    }
    public void changecolour(int colour)
    {
        switch(colour)
        {
            case 1:coloredPath.paint.setColor(Color.BLUE);
                lastColor = Color.BLUE;
                break;
            case 2:coloredPath.paint.setColor(Color.RED);
                lastColor = Color.RED;
                break;
            case 3:coloredPath.paint.setColor(Color.GREEN);
                lastColor = Color.GREEN;
                break;
            case 4:coloredPath.paint.setColor(Color.YELLOW);
                lastColor = Color.YELLOW;
                break;
            case 5:coloredPath.paint.setColor(Color.CYAN);
                lastColor = Color.CYAN;
                break;
        }
    }
    public void changebgcolour(int colour)
    {
        switch(colour)
        {
            case 1:setColour=1;
                break;
            case 2: setColour=2;
                break;
            case 3: setColour=3;
                break;
            case 4:setColour=4;
                break;
            case 5: setColour=5;
                break;
        }
    }
    public void changeSize(int size)
    {
        switch(size)
        {
            case 1:coloredPath.paint.setStrokeWidth(2f);
                lastsize=2f;
                break;
            case 2:coloredPath.paint.setStrokeWidth(6f);
                lastsize=6f;
                break;
            case 3:coloredPath.paint.setStrokeWidth(10f);
                lastsize=10f;
                break;
        }
    }
    private Paint createPaint() {
        Paint temp = new Paint();
        temp.setAntiAlias(true);
        temp.setStyle(Paint.Style.STROKE);
        temp.setStrokeJoin(Paint.Join.ROUND);
        temp.setStrokeWidth(lastsize);
        temp.setColor(lastColor);
        return temp;
    }
    private Paint createPaint2() {
        Paint temp = new Paint();
        temp.setAntiAlias(true);
        temp.setStyle(Paint.Style.STROKE);
        temp.setStrokeJoin(Paint.Join.ROUND);
        temp.setStrokeWidth(20f);
        if(setColour==0)
            temp.setColor(Color.WHITE);
        if(setColour==1)
            temp.setColor(Color.BLUE);
        if(setColour==2)
            temp.setColor(Color.RED);
        if(setColour==3)
            temp.setColor(Color.GREEN);
        if(setColour==4)
            temp.setColor(Color.YELLOW);
        if(setColour==5)
            temp.setColor(Color.CYAN);
        return temp;
    }
    public void check()
    {
        for (ErasePath p : erasePaths) {
            if(setColour==0)
            p.paint.setColor(Color.WHITE);
            if(setColour==1)
                p.paint.setColor(Color.BLUE);
            if(setColour==2)
                p.paint.setColor(Color.RED);
            if(setColour==3)
                p.paint.setColor(Color.GREEN);
            if(setColour==4)
                p.paint.setColor(Color.YELLOW);
            if(setColour==5)
                p.paint.setColor(Color.CYAN);
        }
    }
}
class ColoredPath {
    Paint paint;
    Path path;

    public ColoredPath(Path path, Paint paint) {
        this.path = path;
        this.paint = paint;
    }
}
    class ErasePath {
        Paint paint;
        Path path;
        public ErasePath(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
}
