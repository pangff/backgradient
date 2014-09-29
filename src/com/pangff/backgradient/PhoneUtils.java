package com.pangff.backgradient;

import android.content.Context;

/**
 * 系统相关工具
 * 
 * @author pangff
 */
public class PhoneUtils {

	

	// dip转像素
	public static int dipToPixels(Context context,float dip) {

		final float SCALE = context.getResources()
				.getDisplayMetrics().density;
		float valueDips = dip;
		int valuePixels = (int) (valueDips * SCALE + 0.5f);
		return valuePixels;
	}

	// 像素转dip
	public static float pixelsToDip(Context context,int pixels) {

		final float SCALE = context.getResources()
				.getDisplayMetrics().density;
		float dips = pixels / SCALE;
		return dips;
	}

}
