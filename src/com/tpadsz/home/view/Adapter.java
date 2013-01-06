package com.tpadsz.home.view;

import android.graphics.Bitmap;

public interface Adapter
{
	/**
	 * How many items are in the data set represented by this Adapter.
	 * 
	 * @return Count of items.
	 */
	public long getCount(int listid);

	/**
	 * Get the item's width and height
	 * 
	 * @return the String of width and height . <br>
	 *         formatter : [width height]
	 */
	public String getWidthAndHeight(int listid);

	/**
	 * Get the data at the specified position in the data set .
	 * 
	 * @param listViewId
	 *            id of the listView .
	 * @param position
	 *            The position of the item within the adapter's data set of the
	 *            item whose view we want.
	 * @return The data to display on the specified item . 
	 */  
	public Bitmap getData(int listViewId, int position);
}
