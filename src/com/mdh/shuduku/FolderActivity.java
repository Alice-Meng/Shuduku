package com.mdh.shuduku;

import com.mdh.shuduku.FolderDetailLoader.FolderDetailCallback;
import com.mdh.shuduku.db.FolderColumns;
import com.mdh.shuduku.db.ShudukuDatabase;
import com.mdh.shuduku.game.FolderInfo;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class FolderActivity extends ListActivity {

	private static final int ID_NAME_EDITTEXT = 0x1;
	
	private ShudukuDatabase mDatabase;
	private FolderListViewBinder mFolderListViewBinder;
	private ListView mFolderListView;
	private SimpleCursorAdapter mCursorAdapter;
	private Cursor mFolderListCursor = null;
	private EditText mAddFolderNameInput = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.folder_activity_main);
		
		mFolderListView = (ListView)findViewById(android.R.id.list);
		registerForContextMenu(mFolderListView);
		
		mDatabase = new ShudukuDatabase(getApplicationContext());
		mFolderListCursor = mDatabase.getFolderList();
		
		mCursorAdapter = new SimpleCursorAdapter(this, R.layout.folder_list_item, 
				mFolderListCursor, new String[]{FolderColumns.NAME, FolderColumns._ID}, 
				new int[]{R.id.folderName, R.id.detail});
		mFolderListViewBinder = new FolderListViewBinder(this);
		mCursorAdapter.setViewBinder(mFolderListViewBinder);
		
		setListAdapter(mCursorAdapter);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = 
								(AdapterView.AdapterContextMenuInfo)menuInfo;
		Cursor cursor = (Cursor)mCursorAdapter.getItem(adapterContextMenuInfo.position);
		if (cursor == null) {
			return;
		}
		
		menu.setHeaderTitle(cursor.getString(cursor.getColumnIndex(FolderColumns.NAME)));
		
		getMenuInflater().inflate(R.menu.folder_context_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.add_folder:
			{
//				AlertDialog.Builder builder = new 
				LinearLayout contentViewLayout = new LinearLayout(this);
				contentViewLayout.setOrientation(LinearLayout.VERTICAL);
				TextView nameTextView = new TextView(this);
				nameTextView.setText(R.string.name_colon);
				contentViewLayout.addView(nameTextView, 
						new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				mAddFolderNameInput = new EditText(this);
				mAddFolderNameInput.setId(ID_NAME_EDITTEXT);
				contentViewLayout.addView(mAddFolderNameInput, 
						new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
				new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_menu_add)
				.setTitle(R.string.menu_title_add_folder)
				.setView(contentViewLayout)
				.setPositiveButton(R.string.save, saveClickListener)
				.setNegativeButton(R.string.cancel, null)
				.show();
			}
			break;
		case R.id.import_item:
			
			break;
		case R.id.export_all_folders:
			
			break;
		case R.id.about:
			
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.folderactivity_options_menu, menu);
		
//		Intent intent = new Intent();
//	    intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
//
//	    // Search and populate the menu with acceptable offering applications.
//	    menu.addIntentOptions(
//	         R.id.intent_group,  // Menu group to which new items will be added
//	         0,      // Unique item ID (none)
//	         0,      // Order for the items (none)
//	         this.getComponentName(),   // The current activity name
//	         null,   // Specific items to place first (none)
//	         intent, // Intent created above that describes our requirements
//	         0,      // Additional flags to control items (none)
//	         null);  // Array of MenuItems that correlate to specific items (none)
	    
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
	
	DialogInterface.OnClickListener saveClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			String nameString = mAddFolderNameInput.getText().toString();
			
			mDatabase.insertFolder(nameString, System.currentTimeMillis());
			mFolderListCursor.requery();
		}
	};
	

}
