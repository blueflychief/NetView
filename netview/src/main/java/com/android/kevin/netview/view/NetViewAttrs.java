package com.android.kevin.netview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.android.kevin.netview.R;

/**
 * Created by mwy on 2016/5/19.
 */
public class NetViewAttrs {
	private int netColor;
	private int overlayColor;
	private int overlayAlpha;
	private int textColor;
	private float textSize;
	private int count;


	public NetViewAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.net, defStyleAttr, 0);
		netColor = ta.getColor(R.styleable.net_netColor, Color.parseColor("#1c6abb"));
		overlayAlpha = ta.getInteger(R.styleable.net_overlayAlpha, 75);
		overlayColor = ta.getColor(R.styleable.net_overlayColor, Color.parseColor("#465fff"));
		textColor = ta.getColor(R.styleable.net_textColor, Color.parseColor("#ff4c9c"));
		textSize = ta.getDimension(R.styleable.net_textSize, 20);
		count = ta.getInt(R.styleable.net_count, 7);
		ta.recycle();
	}

	public NetViewAttrs() {
	}

	public int getNetColor() {
		return netColor;
	}

	public int getOverlayColor() {
		return overlayColor;
	}

	public int getOverlayAlpha() {
		return overlayAlpha;
	}

	public int getTextColor() {
		return textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public int getCount() {
		return count;
	}
}
