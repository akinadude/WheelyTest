package com.wheely.activity;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wheely.model.Item;
import com.wheely.utils.Utils;
import com.wheely.network.NetworkUtils;
import com.wheely.services.UpdateService;
import com.wheely.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	static MainActivity mInstance;
	
	ArrayList<Item> mItemsList;
	
	ListView mListView;
	
	ItemsAdapter mItemsAdapter;
	
	ImageButton mRefreshButton;
	
	TextView mCaptionBarTitle;
	
	TextView mNoDataLoadedStub;

	ProgressBar mProgressBar;
	
	/**
	 * Холдер для ListView
	 */
	static class ItemViewHolder
	{
		public TextView title;
		public TextView text;		
	}
	
	
	public static MainActivity getInstance() {
		return mInstance;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInstance = this;
	
		initViews();
		
		new LoadDataFromServerTask().execute();		
	
		startService(new Intent(this, UpdateService.class));
	}
	
	
	/**
	 * Найти все вью из xml
	 */
	private void initViews() {
		mItemsList = new ArrayList<Item>();
		
		mRefreshButton = (ImageButton) findViewById(R.id.caption_bar_update_button);
		mRefreshButton.setVisibility(View.VISIBLE);
		mRefreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new LoadDataFromServerTask().execute();
			}
		});
		
		mCaptionBarTitle = (TextView) findViewById(R.id.caption_bar_title);
		mCaptionBarTitle.setText(getString(R.string.main_activity_title));
		if (mCaptionBarTitle.getText().length() > Utils.TITLE_MAX_LENGHT) {
			String clipString = mCaptionBarTitle.getText().toString();
			clipString = clipString.substring(0, Utils.TITLE_MAX_LENGHT + 1);
			clipString += "...";
			mCaptionBarTitle.setText(clipString);
		}
		
		mListView = (ListView) findViewById(R.id.items_list_view);
		
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Item pressedItem = (Item) adapterView.getAdapter().getItem(position);
				
				String jsonSerializedItem = pressedItem.toString();
				
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
				intent.putExtra(Utils.DETAILS_ACTIVITY_TAG, jsonSerializedItem);				
				startActivity(intent);
				overridePendingTransition(R.anim.activity_open, R.anim.activity_exit);
			}
		});
		
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);		
		mNoDataLoadedStub = (TextView) findViewById(R.id.no_data_loaded);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * Адаптер для листВью item'ов
	 */
	public class ItemsAdapter extends BaseAdapter {

		LayoutInflater mAdapterInflater;
		ArrayList<Item> mAdapterItemsList;
		
		public ItemsAdapter(ArrayList<Item> list) {
			mAdapterItemsList = list;
			mAdapterInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			if (mItemsList == null)
				return 0;

			return mItemsList.size();
		}

		@Override
		public Object getItem(int position) {			
			return mAdapterItemsList.get(position);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = mAdapterInflater.inflate(R.layout.listitem_item, null);
				
				viewHolder = new ItemViewHolder();
				viewHolder.title = (TextView) convertView.findViewById(R.id.item_title);
				viewHolder.text = (TextView) convertView.findViewById(R.id.item_text);
				
				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ItemViewHolder) convertView.getTag();
			}			
			
			viewHolder.title.setText(((Item) getItem(position)).getTitle());
			viewHolder.text.setText(((Item) getItem(position)).getText());

			return convertView;
		}
	}
	
	
	/**
	 * Таcк загрузки данных c cервера
	 */
	public class LoadDataFromServerTask extends AsyncTask<Void, Void, String> {
		String jsonStringFromServer = "";
						
		@Override
		protected void onPreExecute() {			
			mProgressBar.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
			mNoDataLoadedStub.setVisibility(View.GONE);
		}
		
		@Override
		protected synchronized String doInBackground(Void... params) {
			jsonStringFromServer = NetworkUtils.getJsonString();		
			return jsonStringFromServer;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {			
				mNoDataLoadedStub.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);				
				
				return;
			}
			
			mItemsList = new Gson().fromJson(result, (new TypeToken<ArrayList<Item>>() {}).getType());			
			mItemsAdapter = new ItemsAdapter(mItemsList);
			mListView.setAdapter(mItemsAdapter);
			
			mProgressBar.setVisibility(View.GONE);			
			mListView.setVisibility(View.VISIBLE);
		}		
	}
}