package com.tpadsz.home.jni.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.tpadsz.home.jni.ProgramConstant;
import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.view.MotionEvent;
import com.tpadsz.home.view.Spirit;

public class ApplistPage extends BasePage
{

	class TouchPoint
	{
		float x;
		long curtime;

		public TouchPoint(float x, long time)
		{
			this.x = x;
			this.curtime = time;
		}
	}

	private final static int ITEM_MAX_POINTS = 50;

	private final static int A_SPIRIT_INIT_X = 44;
	private final static int Z_SPIRIT_INIT_X = 439;

	private final static int MAX_FLICK_LENGTH = 70;

	private final static int MAX_LIST_SIZE = 4;
	private final static float FLICK_NUM = 0.6f;

	private final List<TouchPoint> points = new ArrayList<TouchPoint>();

	float init_x, up_x;
	boolean isRebound;
	Spirit sp_a;
	Spirit sp_z;
	int move_rank = 0;

	public ApplistPage(Context context,int pageID, OnSceneChangedListener l, JSONProgram json)
	{
		super(context,pageID, l, json);
	}

	@Override
	protected void onStart(int fromPage)
	{
		showAll();
		switch (fromPage)
		{
		case PAGE_HOME:
			playBackground_Move_Posi();
			break;
		}
	}

	@Override
	protected void onResume(int fromPage)
	{
		getSpiritByid(43).setTouchable(true);
		for (int i = 44; i < 70; i++)
		{
			Spirit sp = getSpiritByid(i);
			sp.updateCurrentInfo();
			sp.setTouchable(true);
		}
		sp_a = getSpiritByid(44);
		sp_z = getSpiritByid(69);
	}

	@Override
	protected void onPause(int gotoPage)
	{
		getSpiritByid(43).setTouchable(false);
		for (int i = 44; i < 70; i++)
		{
			getSpiritByid(43).setTouchable(false);
		}
		switch (gotoPage)
		{
		case PAGE_HOME:
			playLabelABCDEF_Move_Versa();
			break;
		}
	}

	@Override
	protected void onStop(int gotoPage)
	{
		for (int i = 39; i < 71; i++)
		{
			Spirit sp = getSpiritByid(i);
			sp.updateInitialInfo();
			sp.setPosition((float) sp.x, (float) sp.y);
			sp.setVisible(false);
		}
		disappearAll();
	}

	@Override
	public void onPlayedEnd(int mSpriteId, int mAnimationId, int mAnimationTypeId, int key)
	{
		super.onPlayedEnd(mSpriteId, mAnimationId, mAnimationTypeId, key);
	}

	@Override
	public void onTouch(int spiritid, MotionEvent event)
	{
		if (spiritid == 43 && event.getAction() == MotionEvent.ACTION_UP)
		{
			gotoPage(PAGE_HOME);
		}
		else if (spiritid > 43 && spiritid < 70)
		{
			add(new TouchPoint(event.getX(), System.currentTimeMillis()));
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				// for (int i = 44; i < 70; i++)
				// {
				// getSpiritByid(i).stopAnimation(0, 0);
				// }
				// /TODO setPosition
				init_x = event.getX();
				isRebound = false;
				move_rank = 0;
				break;
			case MotionEvent.ACTION_MOVE:
				float x_distance = event.getX() - init_x;
				float sp_ad = (float) sp_a.x + x_distance;
				float sp_zd = (float) sp_z.x + x_distance;
				isRebound = false;
				if (sp_ad > A_SPIRIT_INIT_X || sp_zd < Z_SPIRIT_INIT_X)
				{
					isRebound = true;
					move_rank = 1;
					if (sp_ad > (A_SPIRIT_INIT_X + ITEM_MAX_POINTS) || sp_zd < (Z_SPIRIT_INIT_X - ITEM_MAX_POINTS)) move_rank = 2;
				}
				for (int i = 44; i < 70; i++)
				{
					move(i, x_distance, move_rank);
				}
				init_x = event.getX();
				break;
			case MotionEvent.ACTION_UP:
				up_x = event.getX();
				if (isRebound) rebound();
				else isFlick();
				clear();
				break;
			}
		}
	}

	void rebound()
	{

		Log.e(TAG, "!!!!!!!!!!!!!!!!!!!!! Rebound !!!!!!!!!!!!!!!!!!!!!!!");

		float xa_cut = (float) sp_a.x - A_SPIRIT_INIT_X;
		float xz_cut = (float) sp_z.x - Z_SPIRIT_INIT_X;
		float x_end_distance = up_x - init_x;
		float sp_end_ad = (float) sp_a.x + x_end_distance;
		float rebound_cut = sp_end_ad > 0 ? xa_cut : xz_cut;
		for (int i = 44; i < 70; i++)
		{
			moveSpiritBy(i, -rebound_cut, 0);
		}
	}

	void add(TouchPoint point)
	{
		if (points.size() > MAX_LIST_SIZE) points.remove(0);
		points.add(point);
	}

	void clear()
	{
		points.clear();
	}

	boolean isFlick()
	{
		int size = points.size();
		if (size < MAX_LIST_SIZE) return false;
		float distance = points.get(size - 1).x - points.get(0).x;
		long usetime = points.get(size - 1).curtime - points.get(0).curtime;

		float speed = distance * 1.0f / usetime;
		float ass = distance * 2.0f / (usetime * usetime);

		Log.w(TAG, String.format("distance[%f] , usetime[%d] , speed[%f] , ass[%f]", distance, (int) usetime, speed, ass));

		int multi = Math.abs((int) (speed / FLICK_NUM));

		if (multi < 1) return false;

		return onFlick(distance > 0, multi);
	}
  
	boolean onFlick(boolean orientation, int ass)
	{

		isRebound = false;
		float duration = (ass * 100) * 1.0f / 1000;
		float x_distance = orientation ? ass * 60 : 0 - ass * 60;
		float acceleration = 0.0f - ass * 1.0f / 2;

		Log.w(TAG, String.format("duration[%f] , x_distance[%f] , acceleration[%f]", duration, x_distance, acceleration));

		if (sp_a.x + x_distance > A_SPIRIT_INIT_X)
		{
			isRebound = true;
			// Log.e(TAG,
			// String.format("sp_a[%f] , x_distance[%f] , A_SPIRIT_INIT_X[%d]",
			// (float) sp_a.x, x_distance, A_SPIRIT_INIT_X));

			if (sp_a.x + x_distance > (A_SPIRIT_INIT_X + MAX_FLICK_LENGTH))
			{
				x_distance = A_SPIRIT_INIT_X + MAX_FLICK_LENGTH + (float) sp_a.x;
				// Log.e(TAG, "manzu a x_distant :" + x_distance);
			}
		}

		if (sp_z.x + x_distance < Z_SPIRIT_INIT_X)
		{
			isRebound = true;
			// Log.e(TAG,
			// String.format("sp_z[%f] , x_distance[%f] , Z_SPIRIT_INIT_X[%d]",
			// (float) sp_z.x, x_distance, Z_SPIRIT_INIT_X));

			if (sp_z.x + x_distance < (Z_SPIRIT_INIT_X - MAX_FLICK_LENGTH))
			{
				x_distance = Z_SPIRIT_INIT_X - MAX_FLICK_LENGTH - (float) sp_z.x;
				// Log.e(TAG, "manzu z x_distant :" + x_distance);
			}
		}

		for (int i = 44; i < 70; i++)
		{
			int key = ProgramConstant.GetIndexForAnimation();
			getSpiritByid(i).playMoveAction(1, duration, (int) x_distance, 0, acceleration, key);
			if (i == 69) register(key, "moveABCDEFOver");
		}
		return true;
	}

	/**
	 * Move the spirit .
	 * 
	 * @param spid
	 *            id of spirit.
	 * @param x_distance
	 *            the distance to move.
	 * @param rank
	 *            0 - normal , 1 - 1/2 , 2 - 1/4
	 */
	void move(int spid, float x_distance, int rank)
	{
		x_distance = rank == 0 ? x_distance : rank == 1 ? x_distance / 2 : x_distance / 4;
		moveSpiritBy(spid, x_distance, 0);
	}

	void moveABCDEFOver()
	{
		Log.e(TAG, "Move Action Over!!!!!!!!");
		for (int i = 44; i < 70; i++)
		{
			getSpiritByid(i).updateCurrentInfo(); 
			Log.e(TAG, String.format("index[%d] , x_coordinate[%f]", i, getSpiritByid(i).x));
		}

		if (isRebound) rebound();
	}

	void playIcon_Move_Posi()
	{
		playAnimation(40, 39, 2, 0);
		playAnimation(41, 40, 2, 0);
		playAnimation(42, 41, 2, 0);
		register(playAnimation(43, 42, 2, 0), "playLabelABCDEF_Move_Posi");
	}

	void playIcon_Move_Versa()
	{
		register(playAnimation(40, 39, 2, 1), "playBackground_Move_Versa");
		playAnimation(41, 40, 2, 1);
		playAnimation(42, 41, 2, 1);
		playAnimation(43, 42, 2, 1);
	}

	void playBackground_Move_Versa()
	{
		register(playAnimation(39, 38, 2, 1), "CallbackOutToHome");
	}

	void playBackground_Move_Posi()
	{
		register(playAnimation(39, 38, 2, 0), "playIcon_Move_Posi");
	}

	void playLabelABCDEF_Move_Posi()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(44, 43, 2, 0);
					// play(8000, 43, 2, 0);
					sleep(50);
					playAnimation(45, 44, 2, 0);
					// playDelay(9000, 44, 2, 0, delay);
					sleep(50);
					playAnimation(46, 45, 2, 0);
					// playDelay(10000, 44, 2, 0, delay);
					sleep(50);
					playAnimation(47, 46, 2, 0);
					// playDelay(11000, 44, 2, 0, delay);
					sleep(50);
					playAnimation(48, 47, 2, 0);
					// playDelay(12000, 44, 2, 0, delay);
					sleep(50);
					// playDelay(13000, 44, 2, 0, delay);
					register(playAnimation(49, 48, 2, 0), "CallbackIntoFromHome");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

			}
		}.start();

	}

	void playLabelABCDEF_Move_Versa()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(49, 48, 2, 1);
					// play(13000, 44, 2, 1);
					sleep(50);
					playAnimation(48, 47, 2, 1);
					// playDelay(12000, 44, 2, 1, delay);
					sleep(50);
					playAnimation(47, 46, 2, 1);
					// playDelay(11000, 44, 2, 1, delay);
					sleep(50);
					playAnimation(46, 45, 2, 1);
					// playDelay(10000, 44, 2, 1, delay);
					sleep(50);
					playAnimation(45, 44, 2, 1);
					// playDelay(9000, 44, 2, 1, delay);
					sleep(50);
					// playDelay(8000, 44, 2, 1, delay);
					register(playAnimation(44, 43, 2, 1), "playIcon_Move_Versa");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

			}
		}.start();
	}

	void CallbackIntoFromHome()
	{
		handleIntoEnd();
	}

	void CallbackOutToHome()
	{
		handleOutEnd();
	}

}
