package com.tpadsz.home.view;

/**
 * Object used to report movement events from spirit.
 */
public final class MotionEvent
{

	/**
	 * Constant for {@link #getAction()}: A pressed gesture has started.
	 */
	public final static int ACTION_DOWN = 0;

	/**
	 * Constant for {@link #getAction()}: A change has happened during a press
	 * gesture (between {@link #ACTION_DOWN} and {@link #ACTION_UP}).
	 */
	public final static int ACTION_MOVE = 1;

	/**
	 * Constant for {@link #getAction()}: A pressed gesture has finished.
	 */
	public final static int ACTION_UP = 2;

	/**
	 * Constant for{@link #getAction()}:
	 */
	public final static int ACTION_CLICK = 3;

	/**
	 * 
	 */
	public final static int ACTION_DOUBLE_CLICK = 4;

	/**
	 * 
	 */
	public final static int ACTION_LONG_PRESS = 5;

	/**
	 * 
	 */
	public final static int ACTION_FLICK = 6;

	/**
	 * Constant for {@link #getAction()}: The current gesture has been aborted.
	 */
	public final static int ACTION_CANCEL = 7;

	/**
	 * The x coordinate of the current spirit's position
	 */
	private float x;

	/**
	 * The y coordinate of the current spirit's position
	 */
	private float y;

	/**
	 * The kind of action being performed
	 */
	private int action;

	public MotionEvent(float x, float y, int actionType)
	{
		this.x = x;
		this.y = y;
		this.action = actionType;
	}

	/**
	 * Returns the X coordinate of the current spirit's position.
	 * 
	 * @return the x coordinate
	 */
	public final float getX()
	{
		return x;
	}

	/**
	 * Returns the Y coordinate of the current spirit's position.
	 * 
	 * @return the y coordinate
	 */
	public final float getY()
	{
		return y;
	}

	/**
	 * Return the kind of action being performed -- one of either
	 * {@link #ACTION_DOWN}, {@link #ACTION_MOVE}, {@link #ACTION_UP}, or
	 * {@link #ACTION_CANCEL}.
	 */
	public final int getAction()
	{
		return action;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return String.format("X[%s] , Y[%s] , ACTION[%d]", x, y, action);
	}
}