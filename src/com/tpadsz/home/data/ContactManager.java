package com.tpadsz.home.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.Data;
import android.util.Log;

import com.tpadsz.home.R;
import com.tpadsz.home.data.phone.ContactUtils;
import com.tpadsz.home.data.phone.Contacter;

public class ContactManager extends ContactUtils
{
	public ContactManager(Context context)
	{
		super(context);
	}

	Context mContext;

	@Override
	public String obtainNamebyTelnum(String str)
	{
		String name = null;
		Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + str);
		Cursor cursor = getContentResolver().query(uri, new String[] { Data.DISPLAY_NAME }, null, null, null);
		if (cursor.moveToFirst()) name = cursor.getString(0);
		return name;
	}

	@Override
	public Contacter obtainContacter(int id)
	{

		return null;
	}

	@Override
	public List<Contacter> obtainContacters()
	{
		Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
		List<Contacter> contacters = null;
		// 循环遍历
		if (cur.moveToFirst())
		{
			contacters = new ArrayList<Contacter>();

			int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);

			int displayNameColumn = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

			int photoColumn = cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);

			do
			{
				Contacter contacter = new Contacter();

				// 获得联系人的ID号
				long contactId = cur.getLong(idColumn);

				// 获得联系人姓名
				String disPlayName = cur.getString(displayNameColumn);
				contacter.setcName(disPlayName);
				//
				Bitmap contactPhoto = null;

				if (photoColumn > 0)
				{
					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				}
				else
				{
					contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				}

				contacter.setcPhoto(contactPhoto);

				//
				// 查看该联系人有多少个电话号码。如果没有这返回值为0
				int phoneCount = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				Log.i("username", disPlayName);
				if (phoneCount > 0)
				{
					// 获得联系人的电话号码
					Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
					if (phones.moveToFirst())
					{
						do
						{
							// 遍历所有的电话号码
							String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							String phoneType = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
							Log.i("phoneNumber", phoneNumber);
							Log.i("phoneType", phoneType);
							contacter.setcTelnum(phoneNumber);
						}
						while (phones.moveToNext());
					}
				}

				// 获取该联系人邮箱
				Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
				if (emails.moveToFirst())
				{
					do
					{
						// 遍历所有的电话号码
						String emailType = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
						String emailValue = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

						Log.i("emailType", emailType);
						Log.i("emailValue", emailValue);
					}
					while (emails.moveToNext());
				}

				// 获取该联系人IM
				Cursor IMs = getContentResolver().query(Data.CONTENT_URI, new String[] { Data._ID, Im.PROTOCOL, Im.DATA }, Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='" + Im.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(contactId) }, null);
				if (IMs.moveToFirst())
				{
					do
					{
						String protocol = IMs.getString(IMs.getColumnIndex(Im.PROTOCOL));
						String date = IMs.getString(IMs.getColumnIndex(Im.DATA));
						Log.i("protocol", protocol);
						Log.i("date", date);
					}
					while (IMs.moveToNext());
				}

				// 获取该联系人地址
				Cursor address = getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
				if (address.moveToFirst())
				{
					do
					{
						// 遍历所有的地址
						String street = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
						String city = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
						String region = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
						String postCode = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
						String formatAddress = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
						Log.i("street", street);
						Log.i("city", city);
						Log.i("region", region);
						Log.i("postCode", postCode);
						Log.i("formatAddress", formatAddress);
					}
					while (address.moveToNext());
				}

				// 获取该联系人组织
				Cursor organizations = getContentResolver().query(Data.CONTENT_URI, new String[] { Data._ID, Organization.COMPANY, Organization.TITLE }, Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='" + Organization.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(contactId) }, null);
				if (organizations.moveToFirst())
				{
					do
					{
						String company = organizations.getString(organizations.getColumnIndex(Organization.COMPANY));
						String title = organizations.getString(organizations.getColumnIndex(Organization.TITLE));
						Log.i("company", company);
						Log.i("title", title);
					}
					while (organizations.moveToNext());
				}

				// 获取备注信息
				Cursor notes = getContentResolver().query(Data.CONTENT_URI, new String[] { Data._ID, Note.NOTE }, Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='" + Note.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(contactId) }, null);
				if (notes.moveToFirst())
				{
					do
					{
						String noteinfo = notes.getString(notes.getColumnIndex(Note.NOTE));
						Log.i("noteinfo", noteinfo);
					}
					while (notes.moveToNext());
				}

				// 获取nickname信息
				Cursor nicknames = getContentResolver().query(Data.CONTENT_URI, new String[] { Data._ID, Nickname.NAME }, Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='" + Nickname.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(contactId) }, null);
				if (nicknames.moveToFirst())
				{
					do
					{
						String nickname_ = nicknames.getString(nicknames.getColumnIndex(Nickname.NAME));
						Log.i("nickname_", nickname_);
					}
					while (nicknames.moveToNext());
				}
				contacters.add(contacter);
			}
			while (cur.moveToNext());

		}
		return contacters;
	}
}
