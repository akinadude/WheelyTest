package com.wheely.activity;

import com.google.gson.Gson;
import com.wheely.R;
import com.wheely.model.Item;
import com.wheely.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	
	ImageButton mBackButton;
	
	TextView mCaptionBarTitle;
	
	TextView mTitle;
	TextView mText;
	
	String mTitleString;
	String mTextString;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			if (extras.containsKey(Utils.DETAILS_ACTIVITY_TAG)) {
				String jsonSerilizedItem = extras.getString(Utils.DETAILS_ACTIVITY_TAG);
				Item deserializedItem = new Gson().fromJson(jsonSerilizedItem, Item.class);
								
				mTitleString = deserializedItem.getTitle();
				mTextString = deserializedItem.getText();
			}
			else {				
				mTitleString = Utils.NO_DATA;
				mTextString = Utils.NO_DATA;
			}
		}	
	
		initViews();
	}
	
	
	private void initViews() {
		mBackButton = (ImageButton) findViewById(R.id.caption_bar_back_button);
		mBackButton.setVisibility(View.VISIBLE);
		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mCaptionBarTitle = (TextView) findViewById(R.id.caption_bar_title);
		mCaptionBarTitle.setText(mTitleString);		
		if (mCaptionBarTitle.getText().length() > Utils.TITLE_MAX_LENGHT) {
			String clipString = mCaptionBarTitle.getText().toString();
			clipString = clipString.substring(0, Utils.TITLE_MAX_LENGHT + 1);
			clipString += "...";
			mCaptionBarTitle.setText(clipString);
		}
		
		mTitle = (TextView) findViewById(R.id.activity_details_item_title);
		mText = (TextView) findViewById(R.id.activity_details_item_text);		
		mTitle.setText(mTitleString);
		mText.setText(mTextString);
	}
}