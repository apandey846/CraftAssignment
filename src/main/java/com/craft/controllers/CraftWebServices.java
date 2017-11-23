package com.craft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craft.models.CraftUserGameModel;
import com.craft.models.CraftUserGameModelDao;
import com.craft.pojos.StartGameRequest;


@RestController
public class CraftWebServices {
	
	@Autowired
	private CraftUserGameModelDao craftUserGameModelDao;
	
	@RequestMapping(method = RequestMethod.POST, value = "/start-game")
	public String getByProcessinstanceId(@RequestBody StartGameRequest startGameRequest) {
		//Create user entry in game table
		CraftUserGameModel craftUserGameModel = new CraftUserGameModel(startGameRequest.getGameName(), startGameRequest.getFirstPlayerName(), startGameRequest.getSecondPlayerName(), "IN-PROCESS");
		craftUserGameModelDao.save(craftUserGameModel);
		return craftUserGameModel.toString();
	}

}
