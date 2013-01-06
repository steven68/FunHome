package com.tpadsz.home.data.phone;

import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.ContactsContract;

public abstract class ContactUtils
{
	public final static String MIME_TYPE_BASE = "vnd.android.cursor.item/";

	public final static String MIME_TYPE_EMAIL = MIME_TYPE_BASE + "email_v2";

	public final static String MIME_TYPE_IM = MIME_TYPE_BASE + "im";

	public final static String MIME_TYPE_POSTAL_ADDRESS = MIME_TYPE_BASE + "postal-address_v2";

	public final static String MIME_TYPE_PHOTO = MIME_TYPE_BASE + "photo";

	public final static String MIME_TYPE_PHONE = MIME_TYPE_BASE + "phone_v2";

	public final static String MIME_TYPE_NAME = MIME_TYPE_BASE + "name";

	public final static String MIME_TYPE_ORGANIZATION = MIME_TYPE_BASE + "organization";

	public final static String MIME_TYPE_NICKNAME = MIME_TYPE_BASE + "nickname";

	public final static String MIME_TYPE_GROUP_MEMBERSHIP = MIME_TYPE_BASE + "group_membership";

	public final static String MIME_TYPE_WEBSITE = MIME_TYPE_BASE + "website";

	public final static String MIME_TYPE_NOTE = MIME_TYPE_BASE + "note";

	public final static Uri URI_CONTACTS = ContactsContract.Contacts.CONTENT_URI;

	public final static Uri URI_DATA = ContactsContract.Data.CONTENT_URI;

	private ContentResolver mContentResolver;

	private Resources mResources;

	public ContactUtils(Context context)
	{
		mContentResolver = context.getContentResolver();
		mResources = context.getResources();
	}

	public ContentResolver getContentResolver()
	{
		return mContentResolver;
	}

	public Resources getResources()
	{
		return mResources;
	}

	abstract public String obtainNamebyTelnum(String str);

	abstract public Contacter obtainContacter(int id);

	abstract public List<Contacter> obtainContacters();
}
