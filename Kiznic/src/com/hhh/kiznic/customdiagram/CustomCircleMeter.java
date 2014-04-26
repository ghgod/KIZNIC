package com.hhh.kiznic.customdiagram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

public class CustomCircleMeter extends View {
	
	private String colorCode;
	private String text;
	private int gage;
	
	public CustomCircleMeter(Context context, ImageView imageview, String colorCode, String text, int gage){
		super(context);
	
		this.colorCode = colorCode;
		this.text = text;
		this.gage = gage;
	}
	
public void onDraw(Canvas canvas){

		Paint pnt = new Paint();
		
		RectF r = new RectF(50, 50, 150, 150);
		
		pnt.setStrokeWidth(10);
		pnt.setStyle(Paint.Style.STROKE);
		pnt.setAntiAlias(true);
		
		pnt.setColor(Color.parseColor(colorCode));
		canvas.drawArc(r, 0, 360, true, pnt);
		
		pnt.setColor(Color.parseColor(colorCode));
		canvas.drawArc(r, -90, gage, true, pnt);
		
	}
}
