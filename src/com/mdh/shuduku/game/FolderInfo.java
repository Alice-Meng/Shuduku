package com.mdh.shuduku.game;

import com.mdh.shuduku.R;

import android.content.Context;


public class FolderInfo {

	/**
	 * primary key
	 */
	public long id;
	
	/**
	 * name of the folder
	 */
	public String name;
	/**
	 * the number of all puzzles in the folder
	 */
	public int puzzleCount;
	/**
	 * the number of solved puzzles in the folder
	 */
	public int solvedCount;
	/**
	 * the number of playing puzzles in the folder
	 */
	public int playingCount;
	
	public FolderInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public FolderInfo(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getDetail(Context context) {
		return context.getString(R.string.folder_detail, 
				this.puzzleCount, this.playingCount, this.solvedCount);
	}	
	
	
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public int getPuzzleCount() {
//		return puzzleCount;
//	}
//
//	public void setPuzzleCount(int puzzleCount) {
//		this.puzzleCount = puzzleCount;
//	}
//
//	public int getSolvedCount() {
//		return solvedCount;
//	}
//
//	public void setSolvedCount(int solvedCount) {
//		this.solvedCount = solvedCount;
//	}
//
//	public int getPlayingCount() {
//		return playingCount;
//	}
//
//	public void setPlayingCount(int playingCount) {
//		this.playingCount = playingCount;
//	}
//	
}
