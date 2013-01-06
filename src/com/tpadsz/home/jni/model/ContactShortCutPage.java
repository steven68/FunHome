package com.tpadsz.home.jni.model;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.tpadsz.home.data.ContactManager;
import com.tpadsz.home.data.phone.Contacter;
import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.view.MotionEvent;

public class ContactShortCutPage extends BasePage
{
	List<Contacter> contacts;
	ContactManager mContactManager;

	public ContactShortCutPage(Context context, int pageID, OnSceneChangedListener l, JSONProgram json)
	{
		super(context, pageID, l, json);
		mContactManager = new ContactManager(context);
		contacts = mContactManager.obtainContacters();
	}

	@Override
	protected void onStart(int fromPage)
	{
		showAll(35000);
		playAnimation(82, 62, 2, 0);
		playAnimation(83, 63, 2, 0);
		playAnimation(84, 64, 2, 0);
		playAnimation(85, 65, 2, 0);
		playAnimation(86, 66, 2, 0);
		playAnimation(87, 67, 2, 0);
		playAnimation(88, 68, 2, 0);
		register(playAnimation(89, 69, 2, 0), "CallbackFromUnlock");
	}

	@Override
	protected void onResume(int fromPage)
	{
		getSpiritByid(3).setTouchable(true);
		getSpiritByid(13).setTouchable(true);
	}

	@Override
	protected void onPause(int gotoPage)
	{
		getSpiritByid(3).setTouchable(false);
		getSpiritByid(13).setTouchable(false);
		disappearSpiritByid(35000);
		playAnimation(82, 62, 2, 1);
		playAnimation(83, 63, 2, 1);
		playAnimation(84, 64, 2, 1);
		playAnimation(85, 65, 2, 1);
		playAnimation(86, 66, 2, 1);
		playAnimation(87, 67, 2, 1);
		playAnimation(88, 68, 2, 1);
		register(playAnimation(89, 69, 2, 1), "CallbackToUnlock");
	}

	@Override
	protected void onStop(int gotoPage)
	{

	}

	@Override
	public void onTouch(int spiritid, MotionEvent event)
	{
		if (spiritid == 13 && event.getAction() == MotionEvent.ACTION_UP)
		{
			gotoPage(PAGE_UNLOCKER);
		}
	}

	@Override
	public long getCount(int listid)
	{
		return contacts.size();
	}

	@Override
	public String getWidthAndHeight(int listid)
	{
		return "348 90";
	}

	@Override
	public Bitmap getData(int listViewId, int position)
	{
		Contacter contacter = contacts.get(position);
		String name = contacter.getcName();
		String tel = contacter.getcTelnum();
		Log.e(TAG, String.format("Position[%d] , ContactName[%s] , ContactTel[%s]", position, name, tel));
		return create(name, tel);
	}

	Bitmap create(String name, String tel)
	{
		Bitmap bmp = Bitmap.createBitmap(320, 90, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		// canvas.drawARGB(255, 255, 0, 0);
		Paint p = new Paint();
		p.setTextSize(30);
		p.setColor(Color.RED);
		canvas.drawText(name, 20, 35, p);
		canvas.drawText(tel, 20, 75, p);
		canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
		// canvas.restore();// 存储
		return bmp;
	}

	void CallbackToUnlock()
	{
		handleOutEnd();
	}

	void CallbackFromUnlock()
	{
		showSpiritByid(35000);
		handleIntoEnd();
	}

}
