package com.android.kevin.netview.view;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by mwy on 2016/5/20.
 */
public class DensityUtils {
	public static int dpToPx(int dp,Context context){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}

	public static int spToPx(int sp,Context context){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
	}
}
