package com.craft.utils;

public class Utils {

	public enum GameState {
		IN_PROGRESS("IN-PROGRESS"), WON("WON THE GAME"), DRAW("GAME DRAW");

		private final String state;

		private GameState(final String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}
	}

}
