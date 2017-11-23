package com.craft.pojos;

public class NextMoveRequest {

	String playerName;
	int row;
	int column;
	String value;
	long gameId;

	public NextMoveRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NextMoveRequest(String playerName, int row, int column, String value, long gameId) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.value = value;
		this.gameId = gameId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

}
