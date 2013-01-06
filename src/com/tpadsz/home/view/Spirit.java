package com.tpadsz.home.view;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.tpadsz.home.jni.NativeInterface;

public class Spirit
{

	public final static String TAG = Spirit.class.getName();

	public final static boolean _DEBUG_ = false;

	// /////////////////////////////////////////////////////////////////////////
	// //////////////////////////<<<Inner Interface>>>//////////////////////////
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Interface definition for a call back to be invoked when a spirit be
	 * playing .
	 */
	public interface PlayAnimationCallback
	{
		/**
		 * Called when played begin
		 * 
		 * @param mAnimation
		 */
		public void onPlayedBegin(int mSpriteId, int mAnimationId, int mAnimationTypeId, int key);

		/**
		 * Called when played end
		 * 
		 * @param mAnimation
		 */
		public void onPlayedEnd(int mSpriteId, int mAnimationId, int mAnimationTypeId, int key);

		/**
		 * Called when played error
		 * 
		 * @param mAnimation
		 */
		public void onPlayedError(int mSpriteId, int mAnimationId, int mAnimationTypeId, int key);
	}

	/**
	 * Interface definition for a call back to be invoked when a spirit be stop
	 */
	public interface StopAnimationCallback
	{
		/**
		 * Called when the animation be stop
		 * 
		 * @param mAnimation
		 */
		public void onStop(int mAnimationId, boolean result);
	}

	public interface OnActionMoveCallback
	{
		public void onMoveCallback(int spiritid, boolean result, int key);
	}

	/**
	 * Interface definition for a call back to be invoked when a touch event is
	 * dispatched to this spirit.
	 */
	public interface OnTouchListener
	{
		/**
		 * Call back when a touch event is dispatched to a spirit.
		 * 
		 * @param spiritid
		 *            SpiritId of the spirit that was clicked.
		 */
		public void onTouch(int spiritid, MotionEvent event);
	}

	public interface OnFlickListener
	{
		public void onFlick(int mSpriteId, float x_begin, float y_begin, float x_end, float y_end, float mSpeed);
	}

	public interface OnItemClickListener
	{
		public void onItemClick(int listid, int index);
	}

	private static PlayAnimationCallback mPlayAnimationCallback;

	private static OnTouchListener mTouchListener;

	private static OnActionMoveCallback mActionMoveCallback;

	private static StopAnimationCallback mStopAnimationCallback;

	private static OnFlickListener mFlickListener;

	private static OnItemClickListener mItemClickListener;

	// Spirit id
	public int spirit_id;

	// Spirit name
	public String spirit_name;

	// Spirit resource path
	public String spirit_path;

	// The x coordinate of the first pixel in source
	public double x;

	// The y coordinate of the first pixel in source
	public double y;

	// Spirit width
	public double spirit_width;

	// Spirit height
	public double spirit_height;

	// Spirit layer
	public int spirit_layer;

	// Whether the spirit can be touch
	public int touch_enable;

	// Whether the spirit can be move
	public int move_enable;

	// Whether the spirit is visible
	public boolean isvisible;

	// Reservation state block
	public String state_reserve;

	public Spirit(int spiritid)
	{
		this.spirit_id = spiritid;
		NativeInterface.createSpriteFromFile(spirit_id, false);
		updateInitialInfo();
	}

	public Spirit(int spiritid, Adapter a, OnItemClickListener l)
	{
		this.spirit_id = spiritid;
		mAdapter = a;
		mItemClickListener = l;
		NativeInterface.createSpriteFromFile(spirit_id, false);
		updateInitialInfo();
	}

	public Spirit(int spiritid, boolean show)
	{
		this.spirit_id = spiritid;
		this.isvisible = show;
		NativeInterface.createSpriteFromFile(spirit_id, show);
		updateInitialInfo();
	}

	public Spirit(int mSpriteid, byte[] mData, int mLen, int width, int height, float x, float y, int mLayer, boolean isShown)
	{
		this.spirit_id = mSpriteid;
		this.isvisible = isShown;
		NativeInterface.createSpriteFromBuffer(mSpriteid, mData, mLen, width, height, x, y, mLayer, isShown);
		updateInitialInfo();
	}

	private void parseJSON(int spiritid, int type)
	{
		String json = type == 1 ? NativeInterface.getSpriteById(spiritid) : NativeInterface.getSpriteOriginalInfo(spiritid);
		try
		{
			JSONObject spirit_obj = new JSONObject(json);
			this.spirit_id = spiritid;
			this.spirit_name = spirit_obj.getString("spirit_name");
			this.spirit_path = spirit_obj.getString("spirit_path");
			this.x = spirit_obj.getDouble("x");
			this.y = spirit_obj.getDouble("y");
			this.spirit_width = spirit_obj.getDouble("spirit_width");
			this.spirit_height = spirit_obj.getDouble("spirit_height");
			this.spirit_layer = spirit_obj.getInt("spirit_layer");
			this.touch_enable = spirit_obj.getInt("touch_enable");
			this.move_enable = spirit_obj.getInt("move_enable");
			this.state_reserve = spirit_obj.getString("state_reserve");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	private static Adapter mAdapter;

	public void setAdapter(Adapter t)
	{
		mAdapter = t;
	}

	/**
	 * Update the instant info of spirit.
	 */
	public void updateCurrentInfo()
	{
		parseJSON(spirit_id, 1);
	}

	/**
	 * Update the initial info of spirit.
	 */
	public void updateInitialInfo()
	{
		parseJSON(spirit_id, 0);
	}

	public int playAnimation(int animId, int animType, int order, int key)
	{
		NativeInterface.runAnimation(spirit_id, animId, animType, order == 0, key);
		return key;
	}

	public void stopAnimation(int animationid, int manimationtype)
	{
		NativeInterface.stopAnimation(spirit_id, animationid, manimationtype, 0);
	}

	public void playMoveAction(int mMoveType, float duration, float x, float y, float acceleration, int key)
	{
		NativeInterface.runActionMove(spirit_id, mMoveType, duration, x, y, acceleration, key);
	}

	public void setVisible(boolean isvisible)
	{
		this.isvisible = isvisible;
		NativeInterface.setSpriteVisible(spirit_id, isvisible);
	}

	public void getVisible()
	{
		NativeInterface.getSpriteVisible(spirit_id);
	}

	public void setTouchable(boolean touchable)
	{
		NativeInterface.setSpriteTouchable(spirit_id, touchable);
	}

	public void getTouchable()
	{
		NativeInterface.getSpriteTouchable(spirit_id);
	}

	public void setPosition(float x, float y, int mlayer)
	{
		this.x = x;
		this.y = y;
		NativeInterface.setSpritePostion(spirit_id, x, y, mlayer);
	}

	public void setPosition(float x, float y)
	{
		setPosition(x, y, spirit_layer);
	}

	public boolean release()
	{
		try
		{
			NativeInterface.destroySprite(spirit_id);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	// /////////////////////////////////////////////////////////////////////////
	// //////////////////////////<<<Set Listeners>>>////////////////////////////
	// /////////////////////////////////////////////////////////////////////////

	public void setOnTouchListener(OnTouchListener l)
	{
		mTouchListener = l;
	}

	public void setPlayAnimationCallback(PlayAnimationCallback call)
	{
		mPlayAnimationCallback = call;
	}

	public void setStopAnimationCallback(StopAnimationCallback call)
	{
		mStopAnimationCallback = call;
	}

	public void setOnActionMoveCallback(OnActionMoveCallback call)
	{
		mActionMoveCallback = call;
	}

	public void setOnFlickListener(OnFlickListener l)
	{
		mFlickListener = l;
	}

	// /////////////////////////////////////////////////////////////////////////
	// //////////////////////////<<<NativeInterface>>>//////////////////////////
	// /////////////////////////////////////////////////////////////////////////

	public static void onCreateSpriteCallback(int mSpriteId, boolean mResult)
	{
		if (_DEBUG_) Log.e(TAG, String.format("SpiritID[%d] create %s", mSpriteId, mResult));
	}

	public static void ondestroySpriteCallback(int mSpriteId, boolean mResult)
	{
		if (_DEBUG_) Log.e(TAG, String.format("SpiritID[%d] destroy %s", mSpriteId, mResult));
	}

	public static void onStateChanged(int mSpriteId, int mPreState, int mCurState)
	{

	}

	public static void runAnimationCallback(int mSpriteId, int mAnimationId, int mAnimationTypeId, int mResult, int key)
	{
		if (mPlayAnimationCallback != null)
		{
			switch (mResult)
			{
			case -1:
				mPlayAnimationCallback.onPlayedError(mSpriteId, mAnimationId, mAnimationTypeId, key);
				break;
			case 0:
				mPlayAnimationCallback.onPlayedBegin(mSpriteId, mAnimationId, mAnimationTypeId, key);
				break;
			case 1:
				mPlayAnimationCallback.onPlayedEnd(mSpriteId, mAnimationId, mAnimationTypeId, key);
				break;
			}
		}
	}

	public static void stopCallback(int mSpriteId, int mAnimationId, boolean mResult)
	{
		if (mStopAnimationCallback != null)
		{
			mStopAnimationCallback.onStop(mAnimationId, mResult);
		}
	}

	public static boolean handleTouchEvent(int mSpriteId, int mActionType, float x, float y)
	{
		boolean result = mTouchListener != null;
		if (result) mTouchListener.onTouch(mSpriteId, new MotionEvent(x, y, mActionType));
		return result;
	}

	public static void runActionMoveCallback(int mSpriteId, boolean mResult, int key)
	{
		if (mActionMoveCallback != null) mActionMoveCallback.onMoveCallback(mSpriteId, mResult, key);
	}

	public static void handleFlickEvent(int mSpriteId, float x_begin, float y_begin, float x_end, float y_end, float mSpeed)
	{
		if (mFlickListener != null) mFlickListener.onFlick(mSpriteId, x_begin, y_begin, x_end, y_end, mSpeed);
	}

	public static long getnumberOfCellsInListView(int mListViewId)
	{
		return mAdapter.getCount(mListViewId);
	}

	public static String getcellWidthAndHeightForListView(int mListViewId)
	{
		return mAdapter.getWidthAndHeight(mListViewId);
	}

	public static Bitmap getDataForListView(int mListViewId, int mIndex)
	{
		return mAdapter.getData(mListViewId, mIndex);
	}

	public static void handleListCellTouched(int mListViewId, int mIndex)
	{
		mItemClickListener.onItemClick(mListViewId, mIndex);
	}

}
