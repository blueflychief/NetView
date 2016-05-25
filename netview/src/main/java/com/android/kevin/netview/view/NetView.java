package com.android.kevin.netview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mwy on 2016/5/19.
 */
public class NetView extends View {

	private String[] titles = {"A", "B", "C", "D", "E", "F", "G"};
	private double[] data = {1.0, 0.25, 0.80, 0.55, 0.57, 0.57, 0.45};

	private int count = data.length;// 几条边
	private float angle; //角度
	private float radius;//外接半径
	private int centerX; //中心点
	private int centerY;

	private int netColor;//网的颜色
	private int overlayColor; //覆盖物颜色
	private int overlayAlpha; //覆盖物透明色
	private int textColor;   //文字颜色
	private float textSize;  //文件大小
	private int coats ; //画几层 默认最少2层


	private NetViewAttrs netViewAttrs;

	private Paint netPaint;
	private Paint overlayPaint;
	private Paint textPaint;


	public NetView(Context context) {
		this(context, null);
	}

	public NetView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NetView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		netViewAttrs = new NetViewAttrs(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		netColor = netViewAttrs.getNetColor();
		overlayColor = netViewAttrs.getOverlayColor();
		overlayAlpha = netViewAttrs.getOverlayAlpha();
		textColor = netViewAttrs.getTextColor();
		textSize = netViewAttrs.getTextSize();
		count = netViewAttrs.getCount();
		coats = netViewAttrs.getCoats();

		angle = (float) (Math.PI * 2 / count);  //2π = 360度

		netPaint = new Paint();
		netPaint.setAntiAlias(true);
		netPaint.setColor(netColor);
		netPaint.setStyle(Paint.Style.STROKE);

		overlayPaint = new Paint();
		overlayPaint.setAntiAlias(true);
		overlayPaint.setColor(overlayColor);
		overlayPaint.setAlpha(255);
		overlayPaint.setStyle(Paint.Style.FILL);

		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		radius = Math.min(h, w) / 2 * 0.7f;
		//中心坐标
		centerX = w / 2;
		centerY = h / 2;
		postInvalidate();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (count != data.length || count != titles.length){
			throw new IllegalStateException("count, titles.length, data.length must equals");
		}

		drawNet(canvas);
		drawOverlay(canvas);
		drawTtitle(canvas);
	}

	//绘制蜘蛛网
	private void drawNet(Canvas canvas) {
		Path path = new Path();
		float r = radius / (coats - 1);
		for (int i = 1; i < coats; i++) {//不用画圆点
			float curR = r * i;//当前半径
			path.reset();//清空路径
			for (int j = 0; j < count; j++) {
				if (j == 0) {
					path.moveTo(centerX, centerY - curR);
				} else {
					float x = (float) (centerX + curR * Math.sin(angle * j));
					float y = (float) (centerY - curR * Math.cos(angle * j));
					path.lineTo(x, y);
				}
			}
			path.close();//闭合路径
			canvas.drawPath(path, netPaint);
		}

		//画轴线
		for (int i = 0; i < count; i++) {
			path.reset();
			path.moveTo(centerX, centerY);
			float x = (float) (centerX + radius * Math.sin(angle * i));
			float y = (float) (centerY - radius * Math.cos(angle * i));
			path.lineTo(x, y);
			canvas.drawPath(path, netPaint);
		}
	}

	//画覆盖物
	private void drawOverlay(Canvas canvas) {
		Path path = new Path();
		for (int i = 0; i < count; i++) {
			float x = (float) (centerX + radius * Math.sin(angle * i) * data[i]);
			float y = (float) (centerY - radius * Math.cos(angle * i) * data[i]);
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
			//画小圆点
			canvas.drawCircle(x, y, 5, overlayPaint);
		}

		//描边
		overlayPaint.setStyle(Paint.Style.STROKE);
		overlayPaint.setAlpha(255);
		canvas.drawPath(path, overlayPaint);


		//路径填充
		overlayPaint.setAlpha(overlayAlpha);
		overlayPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawPath(path, overlayPaint);


	}

	//画文字
	private void drawTtitle(Canvas canvas) {
		Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
		float fontHeight = fontMetrics.descent - fontMetrics.ascent; //文字的高度

		for (int i = 0; i < count; i++) {
			float fontWidth = textPaint.measureText(titles[i]);//获取文本长度
			//从90 度开始画的 所以从第1象限开始画  顺时针  1 4 3 2
			float x = (float) (centerX + radius * Math.sin(angle * i));
			float y = (float) (centerY - radius * Math.cos(angle * i));

			//偏移量
			int offSetpx = DensityUtils.dpToPx(3, getContext());

			//转动的角度
			if (angle * i == 0 || angle * i == Math.PI * 2) {// 转动了 0 度 或者360度

				canvas.drawText(titles[i], x - fontWidth / 2, y - offSetpx, textPaint);

			} else if (angle * i > 0 && angle * i < Math.PI * 0.5) { //第一象限

				canvas.drawText(titles[i], x + offSetpx, y, textPaint);

			} else if (angle * i == Math.PI * 0.5) { //转动了 90 度 x >0 y = 0

				canvas.drawText(titles[i], x + offSetpx, y + fontHeight / 2, textPaint);

			} else if (angle * i > Math.PI * 0.5 && angle * i < Math.PI) {//第4象限

				canvas.drawText(titles[i], x + offSetpx, y + fontHeight / 2, textPaint);

			} else if (angle * i == Math.PI) {//转动了 180 度 x = 0 y < 0

				canvas.drawText(titles[i], x - fontWidth / 2, y + offSetpx, textPaint);

			} else if (angle * i > Math.PI && angle * i < 1.5 * Math.PI) {//第3象限

				canvas.drawText(titles[i], x - fontWidth - offSetpx, y + fontHeight /2, textPaint);

			} else if (angle * i == Math.PI * 1.5) {//转动了 270 度 x < 0  y = 0

				canvas.drawText(titles[i], x - fontWidth, y + fontHeight / 2, textPaint);

			} else if (angle * i > 1.5 * Math.PI && angle * i < 2 * Math.PI) {//第2象限

				canvas.drawText(titles[i], x - offSetpx - fontWidth, y, textPaint);

			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		int px = DensityUtils.dpToPx(200, getContext());

		if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(px, px);
		} else if (widthSpecMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(px, heightSpecSize);
		} else if (heightSpecMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthSpecSize, px);
		}
	}

	/**
	 * 设置层数
	 * @param coats 画的层数
	 */
	public void setCoats(int coats) {
		this.coats = coats;
	}

	/**
	 * 设置标题
	 * @param titles 标题
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
		setCount(titles.length);
	}

	/**
	 * 设置数据 小于1 大于0
	 * @param data 数据
	 */
	public void setData(double[] data) {
		this.data = data;
		setCount(titles.length);
	}

	public void setOverlayColor(int color) {
		this.overlayColor = color;
	}

	public void setOverlayAlpha(int alpha) {
		if (alpha < 0) {
			this.overlayAlpha = 0;
		} else if (alpha > 255) {
			this.overlayAlpha = 255;
		} else {
			this.overlayAlpha = alpha;
		}
	}

	public void setTextColor(int color) {
		this.textColor = color;
	}

	public void setTextSize(int textsize) {
		if (DensityUtils.dpToPx(textsize, getContext()) < 10) {
			this.textSize = DensityUtils.dpToPx(10, getContext());
		} else if (DensityUtils.dpToPx(textsize, getContext()) > 50) {
			this.textSize = DensityUtils.dpToPx(50, getContext());
		} else {
			this.textSize = DensityUtils.dpToPx(textsize, getContext());
		}
	}

	public void setCount(int count){
		this.count = count;
		angle = (float) (Math.PI * 2 / count);
	}
}
