package com.craft.controllers;

import java.util.Collection;
import java.util.HashMap;
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

import ch.qos.logback.core.net.SyslogOutputStream;

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

		if (craftUserGameModel.getCurrentStatus().contains(Utils.GameState.WON.getState())) {
			return new ApiResponse(200, "SUCCESS", "Match over and " + craftUserGameModel.getCurrentStatus());
		}
		String[][] matrix = new String[3][3];
		for (MatrixMoveModel obj : matrixMoveModelList) {
			matrix[obj.getmRow()][obj.getmColumn()] = obj.getmValue();
		}

		String matrixBoard = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrixBoard = matrixBoard + (matrix[i][j].isEmpty() ? "_" : matrix[i][j]) + "  ";
			}
			matrixBoard = matrixBoard + "  ";
		}
		return new ApiResponse(200, "SUCCESS", "Current Game board looks like " + matrixBoard);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/next-move")
	public ApiResponse makeNextMove(@RequestBody NextMoveRequest nextMoveRequest) {

		// Check row and col values
		if (nextMoveRequest.getColumn() > 2 || nextMoveRequest.getRow() > 2) {
			return new ApiResponse(200, "WARNING",
					"Invalid Column & Row value. It should be less than or equal to 2 and -ve not allowed");
		}
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
		if (!((craftUserGameModel.getPlayerMove().equalsIgnoreCase(craftUserGameModel.getFirstPlayerName())
				? craftUserGameModel.getFirstPlayerSymbol() : craftUserGameModel.getSecondPlayerSymbol())
						.equalsIgnoreCase(nextMoveRequest.getValue()))) {
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

		// Check for rightDiagonal in matrix
		long rightDiagonalCount = ((Collection<MatrixMoveModel>) matrixMoveModelDao
				.findByCraftUserGameModel(craftUserGameModel)).stream()
						.filter(o -> o.getmValue().equals(nextMoveRequest.getValue()))
						.filter(o -> o.getmRow() + o.getmColumn() == 2).count();
		if (rightDiagonalCount == 3) {
			craftUserGameModel.setCurrentStatus(nextMoveRequest.getPlayerName() + " " + Utils.GameState.WON.getState());
			craftUserGameModelDao.save(craftUserGameModel);
			return new ApiResponse(200, "SUCCESS",
					nextMoveRequest.getPlayerName() + " won the match for Game id : " + nextMoveRequest.getGameId());
		}

		// Check for leftDiagonal in matrix
		long leftDiagonalCount = ((Collection<MatrixMoveModel>) matrixMoveModelDao
				.findByCraftUserGameModel(craftUserGameModel)).stream()
						.filter(o -> o.getmValue().equals(nextMoveRequest.getValue()))
						.filter(o -> o.getmRow() == o.getmColumn()).count();
		if (leftDiagonalCount == 3) {
			craftUserGameModel.setCurrentStatus(nextMoveRequest.getPlayerName() + " " + Utils.GameState.WON.getState());
			craftUserGameModelDao.save(craftUserGameModel);
			return new ApiResponse(200, "SUCCESS",
					nextMoveRequest.getPlayerName() + " won the match for Game id : " + nextMoveRequest.getGameId());
		}

		// Check for vertical and horizontal in matrix
		List<MatrixMoveModel> mList = (List<MatrixMoveModel>) matrixMoveModelDao
				.findByCraftUserGameModel(craftUserGameModel);
		HashMap<Integer, Integer> rCountMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> cCountMap = new HashMap<Integer, Integer>();
		for (MatrixMoveModel matrixMoveModelObj : mList) {
			if (nextMoveRequest.getValue().equalsIgnoreCase(matrixMoveModelObj.getmValue())) {
				if (rCountMap.get(matrixMoveModelObj.getmRow()) == null) {
					rCountMap.put(matrixMoveModelObj.getmRow(), 0);
				} else {
					rCountMap.put(matrixMoveModelObj.getmRow(), rCountMap.get(matrixMoveModelObj.getmRow()) + 1);
					if (rCountMap.get(matrixMoveModelObj.getmRow()) == 3) {
						craftUserGameModel.setCurrentStatus(
								nextMoveRequest.getPlayerName() + " " + Utils.GameState.WON.getState());
						craftUserGameModelDao.save(craftUserGameModel);
						return new ApiResponse(200, "SUCCESS", nextMoveRequest.getPlayerName()
								+ " won the match for Game id : " + nextMoveRequest.getGameId());
					}
				}

				if (cCountMap.get(matrixMoveModelObj.getmColumn()) == null) {
					cCountMap.put(matrixMoveModelObj.getmColumn(), 1);
				} else {
					cCountMap.put(matrixMoveModelObj.getmColumn(), cCountMap.get(matrixMoveModelObj.getmColumn()) + 1);
					if (cCountMap.get(matrixMoveModelObj.getmColumn()) == 3) {
						craftUserGameModel.setCurrentStatus(
								nextMoveRequest.getPlayerName() + " " + Utils.GameState.WON.getState());
						craftUserGameModelDao.save(craftUserGameModel);
						return new ApiResponse(200, "SUCCESS", nextMoveRequest.getPlayerName()
								+ " won the match for Game id : " + nextMoveRequest.getGameId());
					}
				}
			}
		}

		// check total blocks filled
		long blockUsed = ((Collection<MatrixMoveModel>) matrixMoveModelDao.findByCraftUserGameModel(craftUserGameModel))
				.stream().count();
		if (blockUsed == 9) {
			return new ApiResponse(200, "SUCCESS", " Match Draw for Game id : " + nextMoveRequest.getGameId());
		}

		return new ApiResponse(200, "SUCCESS", "Other Play turn for Game ID : " + nextMoveRequest.getGameId());
	}

}
