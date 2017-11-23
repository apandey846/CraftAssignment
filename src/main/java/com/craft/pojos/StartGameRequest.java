package com.craft.pojos;

public class StartGameRequest {

	String firstPlayerName;
	String secondPlayerName;
	String gameName;
	String firstPlayerSymbol;
	String secondPlayerSymbol;

	public StartGameRequest() {
		super();
	}

	public StartGameRequest(String firstPlayerName, String secondPlayerName, String gameName, String firstPlayerSymbol,
			String secondPlayerSymbol) {
		super();
		this.firstPlayerName = firstPlayerName;
		this.secondPlayerName = secondPlayerName;
		this.gameName = gameName;
		this.firstPlayerSymbol = firstPlayerSymbol;
		this.secondPlayerSymbol = secondPlayerSymbol;
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

	public String getFirstPlayerSymbol() {
		return firstPlayerSymbol;
	}

	public void setFirstPlayerSymbol(String firstPlayerSymbol) {
		this.firstPlayerSymbol = firstPlayerSymbol;
	}

	public String getSecondPlayerSymbol() {
		return secondPlayerSymbol;
	}

	public void setSecondPlayerSymbol(String secondPlayerSymbol) {
		this.secondPlayerSymbol = secondPlayerSymbol;
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
				+ ", gameName=" + gameName + ", firstPlayerSymbol=" + firstPlayerSymbol + ", secondPlayerSymbol="
				+ secondPlayerSymbol + "]";
	}

}
