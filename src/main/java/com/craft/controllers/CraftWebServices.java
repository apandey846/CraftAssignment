package com.craft.controllers;

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
import com.craft.pojos.StartGameRequest;
import com.craft.utils.ApiResponse;


@RestController
public class CraftWebServices {
	
	@Autowired
	private CraftUserGameModelDao craftUserGameModelDao;
	
	@Autowired
	private MatrixMoveModelDao matrixMoveModelDao;
	
	@RequestMapping(method = RequestMethod.POST, value = "/start-game")
	public ApiResponse getByProcessinstanceId(@RequestBody StartGameRequest startGameRequest) {
		CraftUserGameModel craftUserGameModel = new CraftUserGameModel(startGameRequest.getGameName(), startGameRequest.getFirstPlayerName(), startGameRequest.getSecondPlayerName(), "IN-PROCESS");
		craftUserGameModelDao.save(craftUserGameModel);
		return new ApiResponse(200, "SUCCESS", "New Game has been created successfully with GAME ID : "+craftUserGameModel.getId());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/current-state")
	public ApiResponse getByProcessinstanceId(@RequestParam String gameId) {
		CraftUserGameModel craftUserGameModel = craftUserGameModelDao.findById(Integer.parseInt(gameId));
		if(craftUserGameModel==null){
			return new ApiResponse(404, "WARNING", "Game ID not found");
		}
		List<MatrixMoveModel> matrixMoveModelList = (List<MatrixMoveModel>) matrixMoveModelDao.findByCraftUserGameModel(craftUserGameModel);
		if(matrixMoveModelList.isEmpty()){
			return new ApiResponse(200, "SUCCESS", "Game has just started either "+craftUserGameModel.getFirstPlayerName()+" or "+craftUserGameModel.getSecondPlayerName()+" has to take the first move");
		}
		return new ApiResponse(200, "SUCCESS", "New Game has been created successfully with GAME ID : "+craftUserGameModel.getId());
	}
	

}
