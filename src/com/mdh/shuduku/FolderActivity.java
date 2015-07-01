package com.mdh.shuduku;

import com.mdh.shuduku.FolderDetailLoader.FolderDetailCallback;
import com.mdh.shuduku.db.FolderColumns;
import com.mdh.shuduku.db.ShudukuDatabase;
import com.mdh.shuduku.game.FolderInfo;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class FolderActivity extends ListActivity {

	private ShudukuDatabase mDatabase;
	private Cursor mCursor;
	private FolderListViewBinder mFolderListViewBinder;
//	private ListView mFolderListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.folder_activity_main);
		
//		mFolderListView = (ListView)findViewById(android.R.id.list);
		
		mDatabase = new ShudukuDatabase(getApplicationContext());
		mCursor = mDatabase.getFolderList();
		
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.folder_list_item, 
				mCursor, new String[]{FolderColumns.NAME, FolderColumns._ID}, 
				new int[]{R.id.folderName, R.id.detail});
		mFolderListViewBinder = new FolderListViewBinder(this);
		cursorAdapter.setViewBinder(mFolderListViewBinder);
		
		setListAdapter(cursorAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mDatabase.close();
		mFolderListViewBinder.destroy();
		super.onDestroy();
	}



	class FolderListViewBinder implements ViewBinder {

		private Context mContext = null;
		private FolderDetailLoader mFolderDetailLoader = null;
		
		public FolderListViewBinder(Context context) {
			// TODO Auto-generated constructor stub
			mContext = context;
			mFolderDetailLoader = new FolderDetailLoader(mContext);
		}
			
		@Override
		public boolean setViewValue(View arg0, Cursor arg1, int arg2) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.folderName:
				((TextView)arg0).setText(arg1.getString(arg2));
				break;
			case R.id.detail:
			{
				long folderId = arg1.getInt(arg2);
				final TextView detailTextView = (TextView)arg0;
//				detailTextView.setText(mDatabase.getFolderInfoFull(folderId).getDetail(mContext));
				
				detailTextView.setText(R.string.loading);
				mFolderDetailLoader.loadFolderDetailsSync(folderId, new FolderDetailCallback() {
					
					@Override
					public void onLoad(FolderInfo folderInfo) {
						// TODO Auto-generated method stub
						detailTextView.setText(folderInfo.getDetail(mContext));
					}
				});
				
			}
				break;
			}
			return true;
		}
		
		public void destroy() {
			mFolderDetailLoader.destroy();
		}
	}
}
