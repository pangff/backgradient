package com.pangff.backgradient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView);
		
		listView.setAdapter(new BaseAdapter() {
			
			@SuppressLint("NewApi")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder viewHolder;
				if(convertView==null){
					viewHolder = new ViewHolder();
					convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item,  parent,false);
					convertView.setTag(viewHolder);
					viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
				}else{
					viewHolder = (ViewHolder) convertView.getTag();
				}

		        int[] gColor = {0x76000000,0x60000000,0x50000000,0x40000000,0x30000000,0x20000000,0x10000000,0x09000000,0x08000000,0x07000000,0x06000000,0x05000000,0x04000000,0x03000000,0x02000000,0x01000000,0x00ffffff};
		        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,gColor );
		        gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		        BitmapFactory.Options options = new BitmapFactory.Options();
				  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				    options.inMutable = true;
				  }
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap source = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.tst,options);
				Log.e("ddd","$$$$$$$$"+viewHolder.imageView.getMeasuredHeight());
//				int w = Math.min(source.getWidth(), PhoneUtils.dipToPixels(MainActivity.this, 150));
//				int h = Math.min(source.getHeight(), PhoneUtils.dipToPixels(MainActivity.this, 150));
				Bitmap mask = drawableToBitmap(source.getWidth(),source.getHeight(),gradient);
				viewHolder.imageView.setScaleType(ScaleType.CENTER_CROP);
			    viewHolder.imageView.setImageBitmap(getMaskedBitmap(MainActivity.this.getResources(),source,mask));   
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public int getCount() {
				return 10;
			}
		});
	}
	
	
	@SuppressLint("NewApi")
	public static Bitmap getMaskedBitmap(Resources res, Bitmap source, Bitmap mask) {
		  Bitmap bitmap;
		  if (source.isMutable()) {
		    bitmap = source;
		  } else {
		    bitmap = source.copy(Bitmap.Config.ARGB_8888, true);
		    source.recycle();
		  }
		  bitmap.setHasAlpha(true);
		  Canvas canvas = new Canvas(bitmap);
		  Paint paint = new Paint();
		  paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		  canvas.drawBitmap(mask, 0, 0, paint);
		  mask.recycle();
		  return bitmap;
	}
	
	public static Bitmap drawableToBitmap (int w,int h,Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, w, h);
	    drawable.draw(canvas);

	    return bitmap;
	}
	
	
	static class ViewHolder {
		ImageView imageView;
	}
}
