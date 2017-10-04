package com.example.kishorebaktha.paint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
   private CanvasView canvasView;
    Random r;
    int set=0;

    public int getSet() {
        return set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvasView=(CanvasView)findViewById(R.id.view);
        canvasView.setMainActivity(this);

        r=new Random();
    }
    public void clearCanvas(View v)
    {
        canvasView.clearCanvas();
        //canvasView.setErase(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_1:set=0;
                              canvasView.changecolour(1);
                              break;
            case R.id.menu_2:set=0;
                canvasView.changecolour(2);
                break;
            case R.id.menu_3:set=0;
                canvasView.changecolour(3);
                break;
            case R.id.menu_4:set=0;
                canvasView.changecolour(4);
                break;
            case R.id.menu_5:set=0;
                canvasView.changecolour(5);
                break;
            case R.id.menu_6:set=0;
                canvasView.changebgcolour(1);
                break;
            case R.id.menu_7:canvasView.changebgcolour(2);
                break;
            case R.id.menu_8:canvasView.changebgcolour(3);
                break;
            case R.id.menu_9:canvasView.changebgcolour(4);
                break;
            case R.id.menu_10:canvasView.changebgcolour(5);
                break;
            case R.id.menu_12:canvasView.changeSize(1);
                break;
            case R.id.menu_13:canvasView.changeSize(2);
                break;
            case R.id.menu_14:canvasView.changeSize(3);
                break;
            case R.id.menu_15:set=1;
                break;
            case R.id.menu_11:   final RelativeLayout layout=(RelativeLayout)findViewById(R.id.rel2);
                layout.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap pic=Screenshot(layout);
                        try
                        {
                            if(pic!=null)
                                sendScreenshot(pic);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
        return true;
    }
    private Bitmap Screenshot(View v)
    {
        Bitmap image=null;
        try
        {
            int widrh=v.getMeasuredWidth();
            int height=v.getMeasuredHeight();
            image=Bitmap.createBitmap(widrh,height, Bitmap.Config.ARGB_8888);
            //draw to canvas
            Canvas c=new Canvas(image);
            v.draw(c);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return image;
    }
    //SAVE TO EXTERNAL STORAGE
    private void sendScreenshot(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=null;
        File file=null;
        try
        {
            byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,40,byteArrayOutputStream);
            int m = r.nextInt(20000000);
            String s = String.valueOf(m);
            file = new File("/sdcard/" + s + ".png");
            file.createNewFile();
            FileOutputStream fout=new FileOutputStream(file);
            fout.write(byteArrayOutputStream.toByteArray());
            fout.close();
        }
        catch(Exception e)
        {

        }
    }
}
