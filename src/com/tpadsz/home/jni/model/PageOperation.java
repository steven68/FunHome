package com.tpadsz.home.jni.model;

public interface PageOperation
{
	/**
	 * Go into the current page . It means the page would be started.
	 * 
	 * @param fromPage
	 *            the forward page.
	 */
	public void intoSceneFromPage(int fromPage);

	/**
	 * Go out of the current page . It means the page would be stop.
	 * 
	 * @param gotoPage
	 *            the next page.
	 */
	public void outSceneGotoPage(int gotoPage);
}
