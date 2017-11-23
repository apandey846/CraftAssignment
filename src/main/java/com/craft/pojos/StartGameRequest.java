package com.craft.pojos;

public class StartGameRequest {

	String firstPlayerName;
	String secondPlayerName;
	String gameName;
	char firstPlayerChar;
	char secondPlayerChar;

	public StartGameRequest() {
		super();
	}

	public StartGameRequest(String firstPlayerName, String secondPlayerName, String gameName, char firstPlayerChar,
			char secondPlayerChar) {
		super();
		this.firstPlayerName = firstPlayerName;
		this.secondPlayerName = secondPlayerName;
		this.gameName = gameName;
		this.firstPlayerChar = firstPlayerChar;
		this.secondPlayerChar = secondPlayerChar;
	}

	public String getFirstPlayerName() {
		return firstPlayerName;
	}

	public void setFirstPlayerName(String firstPlayerName) {
		this.firstPlayerName = firstPlayerName;
	}

	public String getSecondPlayerName() {
		return secondPlayerName;
	}

	public void setSecondPlayerName(String secondPlayerName) {
		this.secondPlayerName = secondPlayerName;
	}

	public char getFirstPlayerChar() {
		return firstPlayerChar;
	}

	public void setFirstPlayerChar(char firstPlayerChar) {
		this.firstPlayerChar = firstPlayerChar;
	}

	public char getSecondPlayerChar() {
		return secondPlayerChar;
	}

	public void setSecondPlayerChar(char secondPlayerChar) {
		this.secondPlayerChar = secondPlayerChar;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	@Override
	public String toString() {
		return "StartGameRequest [firstPlayerName=" + firstPlayerName + ", secondPlayerName=" + secondPlayerName
				+ ", firstPlayerChar=" + firstPlayerChar + ", secondPlayerChar=" + secondPlayerChar + "]";
	}

}
