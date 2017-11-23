package com.craft.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.craft.models.CraftUserGameModel;
import com.craft.models.CraftUserGameModelDao;
import com.craft.models.MatrixMoveModel;
import com.craft.models.MatrixMoveModelDao;
import com.craft.pojos.NextMoveRequest;
import com.craft.pojos.StartGameRequest;
import com.craft.utils.ApiResponse;
import com.craft.utils.Utils;

@RestController
public class CraftWebServices {

	@Autowired
	private CraftUserGameModelDao craftUserGameModelDao;

	@Autowired
	private MatrixMoveModelDao matrixMoveModelDao;

	@RequestMapping(method = RequestMethod.POST, value = "/start-game")
	public ApiResponse startTheGame(@RequestBody StartGameRequest startGameRequest) {
		CraftUserGameModel craftUserGameModel = new CraftUserGameModel(startGameRequest.getGameName(),
				startGameRequest.getFirstPlayerName(), startGameRequest.getSecondPlayerName(),
				Utils.GameState.IN_PROGRESS.getState(), startGameRequest.getFirstPlayerSymbol(),
				startGameRequest.getSecondPlayerSymbol());
		craftUserGameModelDao.save(craftUserGameModel);
		return new ApiResponse(200, "SUCCESS",
				"New Game has been created successfully with GAME ID : " + craftUserGameModel.getId());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/current-state")
	public ApiResponse getCurrentState(@RequestParam String gameId) {
		CraftUserGameModel craftUserGameModel = craftUserGameModelDao.findById(Integer.parseInt(gameId));
		if (craftUserGameModel == null) {
			return new ApiResponse(404, "WARNING", "Game ID not found");
		}
		List<MatrixMoveModel> matrixMoveModelList = (List<MatrixMoveModel>) matrixMoveModelDao
				.findByCraftUserGameModel(craftUserGameModel);
		if (matrixMoveModelList.isEmpty()) {
			return new ApiResponse(200, "SUCCESS",
					"Game has just started either " + craftUserGameModel.getFirstPlayerName() + " or "
							+ craftUserGameModel.getSecondPlayerName() + " has to take the first move");
		}
		return new ApiResponse(200, "SUCCESS",
				"New Game has been created successfully with GAME ID : " + craftUserGameModel.getId());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/next-move")
	public ApiResponse makeNextMove(@RequestBody NextMoveRequest nextMoveRequest) {
		CraftUserGameModel craftUserGameModel = craftUserGameModelDao.findById(nextMoveRequest.getGameId());

		// Check game is over or not
		if (!craftUserGameModel.getCurrentStatus().equals(Utils.GameState.IN_PROGRESS.getState())) {
			return new ApiResponse(200, "SUCCESS", "Game already over and " + craftUserGameModel.getCurrentStatus());
		}

		// Check whose turn it is
		if (!craftUserGameModel.getPlayerMove().equalsIgnoreCase(nextMoveRequest.getPlayerName())) {
			return new ApiResponse(200, "WARNING",
					"Its not your turn. Player " + craftUserGameModel.getPlayerMove() + " has to make move now.");
		}

		// Check correct symbol of Player
		if (!(craftUserGameModel.getPlayerMove().equalsIgnoreCase(craftUserGameModel.getFirstPlayerName())
				? craftUserGameModel.getSecondPlayerSymbol() : craftUserGameModel.getFirstPlayerSymbol())
						.equalsIgnoreCase(nextMoveRequest.getValue())) {
			return new ApiResponse(200, "WARNING",
					"Incorrect Symbol used by player " + craftUserGameModel.getPlayerMove());
		}

		// Check block is already used or not.
		MatrixMoveModel matrixMoveModel = ((Collection<MatrixMoveModel>) matrixMoveModelDao
				.findByCraftUserGameModel(craftUserGameModel)).stream()
						.filter(o -> o.getmRow() == nextMoveRequest.getRow())
						.filter(o -> o.getmColumn() == nextMoveRequest.getColumn()).findAny().orElse(null);
		if (matrixMoveModel != null) {
			return new ApiResponse(200, "WARNING", "Bad move as block already used. Please try another block.");
		}

		// Record the player move
		matrixMoveModel = new MatrixMoveModel(nextMoveRequest.getRow(), nextMoveRequest.getColumn(),
				nextMoveRequest.getValue(), nextMoveRequest.getPlayerName(), craftUserGameModel);
		matrixMoveModelDao.save(matrixMoveModel);
		craftUserGameModel.setPlayerMove(
				craftUserGameModel.getPlayerMove().equalsIgnoreCase(craftUserGameModel.getFirstPlayerName())
						? craftUserGameModel.getSecondPlayerName() : craftUserGameModel.getFirstPlayerName());
		craftUserGameModelDao.save(craftUserGameModel);
		return new ApiResponse(200, "SUCCESS", "Other Play turn for Game ID : " + nextMoveRequest.getGameId());
	}

}
