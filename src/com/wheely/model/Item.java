package com.wheely.model;

import com.google.gson.annotations.SerializedName;

/**
 * Предназначен для работы с json-объектом
 */

public class Item extends Entity {

	@SerializedName("id")
	private String mId;
	
	@SerializedName("title")
	private String mTitle;
	
	@SerializedName("text")
	private String mText;
	
	
	public String getId() {
		if(mId == null)
			mId = "";
		return mId;
	}
	
	public void setId(String id) {
		mId = id;
	}
	
	public String getTitle() {
		if(mTitle == null)
			mTitle = "";
		return mTitle;
	}
	
	public void setTitle(String title) {
		mTitle = title;
	}
	
	public String getText() {
		if(mText == null)
			mText = "";
		return mText;
	}
	
	public void setText(String text) {
		mText = text;
	}
}