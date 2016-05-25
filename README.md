#NET VIEW
---
### 雷达控件 多种属性 支持多边形 自由定制

##效果图
---
![img](https://github.com/vinyumao/NetView/blob/master/netview/src/main/res/raw/screenshot/1951.png)

---
##说明
* 在 [xsfelvis](https://github.com/xsfelvis/NetView) 的基础上做了修改， 增加了可自定义多边形，自定义画的层数，画图起点是从90度开始画
等等 其它修改

* 这是个lib 不可直接运行 但可以当做lib导入 
##使用方法
> ###支持属性：
>> 1. netColor 网的颜色
2. overlayColor 生成覆盖区域的颜色
3. overlayAlpha 覆盖区域的透明度
4. textColor 文本的字体颜色
5. textSize 文本的大小
6. count 边的条数
7. coats 网的层数
#  #
> ###支持方法：
>>1. setNetColor
2. setCount
3. textsize
4. setTextColor
5. setOverlayAlpha
6. setOverlayColor
7. setData(double[] data)  设置数据
8. setTitles(String[] titles) 设置标题
9. setCoats


##Example
###xml

	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    xmlns:netview="http://schemas.android.com/apk/res-auto"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:paddingBottom="@dimen/activity_vertical_margin"
	    android:paddingLeft="@dimen/activity_horizontal_margin"
	    android:paddingRight="@dimen/activity_horizontal_margin"
	    android:paddingTop="@dimen/activity_vertical_margin"
	    tools:context="com.example.kevin.scaletest.NetViewActivity">

	    <com.android.kevin.netview.view.NetView
			android:id="@+id/netView"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        netview:netColor="#123456"
	        netview:overlayColor="#51ff83"
	        netview:overlayAlpha="155"
	        netview:textColor="#ff4c9c"
	        netview:textSize="20dp"
	        netview:count="7"
	        netview:coast="5"/>

	</RelativeLayout>

###Activity
	private netView netView;
	//count titles.length data.length 必须相等
    private String[] titles = {"android", "javascript", "java", "python", "c++", "ios","swift"};
    private double[] data = {1, 0.4, 0.6, 0.5, 0.8, 0.3,0.2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        netView = (NetView.netView) findViewById(R.id.netView);
        netView.setTitles(titles);
        netView.setPercent(percent);
		netView.setTextSize(20);
		netView.setcoast="5"
    }

##Download  添加依赖

 Gradle:

	compile 'com.kevinmao.iview:netview:0.2'