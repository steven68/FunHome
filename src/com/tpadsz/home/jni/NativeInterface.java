package com.tpadsz.home.jni;

import android.graphics.Bitmap;
import android.util.Log;

import com.tpadsz.home.view.Spirit;

public class NativeInterface
{

	public final static String TAG = "NativeInterface";

	/**
	 * Init the program.
	 * 
	 * @param mProgramPath
	 *            path of the specified program
	 * @return program id
	 */
	native public static int initProgram(String mProgramPath);

	/**
	 * Release the program.
	 * 
	 * @param mProId
	 *            program id
	 */
	native public static void destroyProgram(int mProId);

	/**
	 * Get the spirit object by json formatter.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @return json formatter of data
	 */
	native public static String getSpriteById(int mSpriteId);

	/**
	 * Get the spirit initial info by id
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @return
	 */
	public static native String getSpriteOriginalInfo(int mSpriteId);

	/**
	 * Get the collection of spirit-id and spirit-id matched animation-id.
	 * 
	 * @param mProgramId
	 *            program id
	 * @return json formatter of data
	 */
	native public static String getInfos(int mProgramId);

	/**
	 * Create spirit from config file by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 */
	native public static void createSpriteFromFile(int mSpriteId, boolean isShow);

	/**
	 * Create spirit from buffer (need to specify the spirit id).
	 * 
	 * @param mSprited
	 *            the spirit which specified by invoker
	 * @param mData
	 *            the bitmap buffer
	 */
	public static native void createSpriteFromBuffer(int mSprited, byte[] mData, int mLen, int width, int height, float x, float y, int mLayer, boolean isShown);

	/**
	 * Release the spirit by the specified id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 */
	native public static void destroySprite(int mSpriteId);

	/**
	 * Set the touchable of the spirit.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param isTouchable
	 *            whether can be touch
	 */
	native public static void setSpriteTouchable(int mSpriteId, boolean isTouchable);

	/**
	 * Get the touchable of the spirit by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 * @return if true - the spirit can be touched
	 */
	native public static boolean getSpriteTouchable(int mSpriteId);

	/**
	 * Set the visibility of the spirit by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 * @param isVisible
	 *            whether is visible
	 */
	native public static void setSpriteVisible(int mSpriteId, boolean isVisible);

	/**
	 * Get the visibility of the spirit by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 * @return if true - the spirit is visible
	 */
	native public static boolean getSpriteVisible(int mSpriteId);

	/**
	 * Set the position of the spirit .
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param x
	 *            The x coordinate of the first pixel in source
	 * @param y
	 *            The y coordinate of the first pixel in source
	 * @param mLayer
	 *            The layer of the spirit
	 * 
	 */
	native public static void setSpritePostion(int mSpriteId, float x, float y, int mLayer);

	/**
	 * Set the size of the spirit.
	 * 
	 * @param mSpriteId
	 *            id of spirit.
	 * @param mWidth
	 *            width of spirit.
	 * @param mHeight
	 *            height of spirit.
	 */
	public static native void setSpriteContentSize(int mSpriteId, float mWidth, float mHeight);

	/**
	 * Get the size of the spirit.
	 * 
	 * @param mSpriteId
	 *            id of spirit.
	 * @return the string of the size.
	 */
	public static native String getSpriteContentSize(int mSpriteId);

	/**
	 * Set the texture to the spirit.
	 * 
	 * @param mSptiteId
	 *            id of spirit.
	 * @param mFileName
	 *            texture path.
	 */
	public static native void setSpriteTexture(int mSptiteId, String mFileName);

	/**
	 * Set the texture to the specified spirit.
	 * 
	 * @param mSptiteId
	 *            id of spirit.
	 * @param mBufData
	 *            buffer of the texture data.
	 * @param mLen
	 *            length of data.
	 * @param mWidth
	 *            width of texture.
	 * @param mHeight
	 *            height of texture.
	 */
	public static native void setSpriteTexture(int mSptiteId, byte[] mBufData, int mLen, int mWidth, int mHeight);

	/**
	 * Get the state of the spirit currently.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 * @return the current state
	 */
	native public static int getSptiteState(int mSpriteId);

	/**
	 * Play the animation of the specified spirit
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mAnimationId
	 *            the animation id
	 * @param mAnimationTypeId
	 *            the animation type id
	 * @param mAnimationOrder
	 *            the type of running
	 */
	native public static void runAnimation(int mSpriteId, int mAnimationId, int mAnimationTypeId, boolean mAnimationOrder, int key);

	/**
	 * Run the move action
	 * 
	 * @param mSpriteId
	 * @param mMoveType
	 * @param duration
	 * @param x
	 * @param y
	 * @param key
	 */
	native public static void runActionMove(int mSpriteId, int mMoveType, float duration, float x, float y, float acceleration, int key);

	/**
	 * Stop the animation of the specified spirit
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mAnimationId
	 *            the animation id
	 * @param mAnimationTypeId
	 *            the animation type id
	 * @param mAnimationOrder
	 *            the type of running
	 */
	native public static void stopAnimation(int mSpriteId, int mAnimationId, int mAnimationTypeId, int mAnimationOrder);

	/**
	 * Create the ListView
	 * 
	 * @param id
	 *            the id of the listView
	 * @param x
	 *            the x coordinate of the ListView
	 * @param y
	 *            the y coordinate of the ListView
	 * @param width
	 *            the width of the listView
	 * @param height
	 *            the height of the listView
	 * @param isShown
	 *            whether to show the listView
	 * @return the result of create listView. if true create complete ,otherwise
	 *         error.
	 */
	native public static boolean createListView(int id, float x, float y, float width, float height, boolean isShown);

	// ///////////////////////////////////////////////////////////////////
	// /////////////////////////// CallBack //////////////////////////////
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Call back when load file complete.
	 * 
	 * @param mResult
	 *            the result of load-file
	 * @param mSize
	 *            size of the data
	 */
	public static void onLoadFileCallback(boolean mResult)
	{
		ProgramManager.onLoadFileCallback(mResult);
	}

	/**
	 * Call back when the spirit be created . return if the result truth
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mResult
	 *            if true - created correctly
	 */
	public static void onCreateSpriteCallback(int mSpriteId, boolean mResult)
	{
		Spirit.onCreateSpriteCallback(mSpriteId, mResult);
	}

	/**
	 * Call back when the spirit be released . return whether the result truth
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mResult
	 *            if true - released correctly
	 */
	public static void ondestroySpriteCallback(int mSpriteId, boolean mResult)
	{
		Spirit.ondestroySpriteCallback(mSpriteId, mResult);
	}

	/**
	 * Call back when the state changed of the specified spirit.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mPreState
	 *            the old state
	 * @param mCurState
	 *            the new state
	 */
	public static void onStateChanged(int mSpriteId, int mPreState, int mCurState)
	{
		Spirit.onStateChanged(mSpriteId, mPreState, mCurState);
	}

	/**
	 * Call back when the animation running.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mAnimationId
	 *            the animation id
	 * @param mAnimationTypeId
	 *            the animation type id
	 * @param mResult
	 *            if true - played correctly
	 */
	public static void runAnimationCallback(int spiritid, int mAnimationId, int mAnimationTypeId, int mResult, int key)
	{
		Log.d("NativeInterface", String.format("runAnimationCallback  -->  spid[%d],anid[%d],antype[%d],result[%d]", spiritid, mAnimationId, mAnimationTypeId, mResult));
		Spirit.runAnimationCallback(spiritid, mAnimationId, mAnimationTypeId, mResult, key);
	}

	public static void runActionMoveCallback(int mSpriteId, boolean mResult, int key)
	{
		Log.e(TAG, String.format("spiritid[%d],mResult[%s],key[%d]", mSpriteId, mResult, key));
		Spirit.runActionMoveCallback(mSpriteId, mResult, key);
	}

	/**
	 * Call back when the animation stop.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mAnimationId
	 *            the animation id
	 * @param mAnimationTypeId
	 *            the animation type id
	 * @param mResult
	 *            if true - played correctly
	 */
	public static void stopAnimationCallback(int mSpriteId, int mAnimationId, boolean mResult)
	{
		Spirit.stopCallback(mSpriteId, mAnimationId, mResult);
	}

	/**
	 * Call back when the spirit be touched
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mActionType
	 *            the actionType (
	 *            {@link com.tpadsz.home.jni.widget.Spirit$MotionEvent})
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean handleTouchEvent(int mSpriteId, int mActionType, float x, float y)
	{
		return Spirit.handleTouchEvent(mSpriteId, mActionType, x, y);
	}

	public static void handleFlickEvent(int mSpriteId, float x_begin, float y_begin, float x_end, float y_end, float mSpeed)
	{
		Spirit.handleFlickEvent(mSpriteId, x_begin, y_begin, x_end, y_end, mSpeed);
	}

	public static long getnumberOfCellsInListView(int mListViewId)
	{
		return Spirit.getnumberOfCellsInListView(mListViewId);
	}

	public static String getcellWidthAndHeightForListView(int mListViewId)
	{
		return Spirit.getcellWidthAndHeightForListView(mListViewId);
	}

	public static Bitmap getDataForListView(int mListViewId, int mIndex)
	{
		return Spirit.getDataForListView(mListViewId, mIndex);
	}

	public static void handleListCellTouched(int mListViewId, int mIndex)
	{
		Spirit.handleListCellTouched(mListViewId, mIndex);
	}

}
