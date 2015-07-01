package com.mdh.shuduku;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Handler;

import com.mdh.shuduku.db.ShudukuDatabase;
import com.mdh.shuduku.game.FolderInfo;

public class FolderDetailLoader {

	private ExecutorService mLoadService = Executors.newSingleThreadExecutor();
	private Handler mHandler = null;
	private ShudukuDatabase mDatabase = null;
	
	public FolderDetailLoader(Context context) {
		mHandler = new Handler();
		mDatabase = new ShudukuDatabase(context);
	}
	
	public void loadFolderDetailsSync (long folderId, FolderDetailCallback detailCallback) {
		final long id = folderId;
		final FolderDetailCallback callback = detailCallback;
		mLoadService.execute(new Runnable() {
			@Override
			public void run() {
				final FolderInfo folderInfo = mDatabase.getFolderInfoFull(id);
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						callback.onLoad(folderInfo);
					}
				});
			}
		});
		
	}
	
	public void destroy() {
		if (mLoadService != null) {
			mLoadService.shutdown();
		}
		if (mDatabase != null) {
			mDatabase.close();
		}
	}
	
	public interface FolderDetailCallback {
		void onLoad(FolderInfo folderInfo);
	}
}
