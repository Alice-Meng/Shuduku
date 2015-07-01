package com.mdh.shuduku.game;


public class ShudukuGame {

	public static final int GAME_STATE_NOT_STARTED = 0;
	public static final int GAME_STATE_PLAYING = 1;
	public static final int GAME_STATE_COMPLETED = 2;
	
	public long mId;
	public long mCreated;
	public long mTime;
	public long mLastPlayed;
	public int mState;
	public String mPuzzleNoteString;
	public String mCells;
	
	
	public long getId() {
		return mId;
	}
	public void setId(long mId) {
		this.mId = mId;
	}
	public long getCreated() {
		return mCreated;
	}
	public void setCreated(long mCreated) {
		this.mCreated = mCreated;
	}
	public int getState() {
		return mState;
	}
	public void setState(int mState) {
		this.mState = mState;
	}
	public long getTime() {
		return mTime;
	}
	public void setTime(long mTime) {
		this.mTime = mTime;
	}
	public long getLastPlayed() {
		return mLastPlayed;
	}
	public void setLastPlayed(long mLastPlayed) {
		this.mLastPlayed = mLastPlayed;
	}
	public String getCells() {
		return mCells;
	}
	public void setCells(String mDataString) {
		this.mCells = mDataString;
	}
	public String getPuzzleNoteString() {
		return mPuzzleNoteString;
	}
	public void setPuzzleNoteString(String mPuzzleNoteString) {
		this.mPuzzleNoteString = mPuzzleNoteString;
	}
	
	
}
