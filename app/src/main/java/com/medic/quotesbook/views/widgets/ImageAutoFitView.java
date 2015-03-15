package com.medic.quotesbook.views.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.medic.quotesbook.R;

/**
 * Created by capi on 14/03/15.
 */
public class ImageAutoFitView extends ImageView {


    private Drawable img;

    private int heightHolder = -1;

    public ImageAutoFitView(Context context) {
        super(context);
        img = context.getResources().getDrawable(R.drawable.author_pic_test);
        //setBackgroundDrawable(img);
    }

    public ImageAutoFitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        img = context.getResources().getDrawable(R.drawable.author_pic_test);
        //setBackgroundDrawable(img);
    }

    public ImageAutoFitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        img = context.getResources().getDrawable(R.drawable.author_pic_test);
        //setBackgroundDrawable(img);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //int width = MeasureSpec.getSize(widthMeasureSpec);
        //int height = width * img.getIntrinsicHeight() / img.getIntrinsicWidth();
        //setMeasuredDimension(width, height);

        Bitmap bitmap = ( (BitmapDrawable) img).getBitmap();

        int diff = this.getMeasuredHeight() - bitmap.getWidth();

        if (this.getMeasuredHeight() > 0){
            this.setImageBitmap(bitmap.createScaledBitmap(bitmap, bitmap.getWidth() + diff, this.getMeasuredHeight(), false));

        }
     //       this.setImageBitmap(bitmapHolder);

        Log.d("FitView", "Se llama al onMeasure " + new Integer(this.getMeasuredHeight()).toString());

    }
}
