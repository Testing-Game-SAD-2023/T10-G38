package com.project.ProgettoSad.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import com.project.ProgettoSad.exception.ExceptionIllegalParameters;
import com.project.ProgettoSad.exception.ExceptionMandatoryFields;
import com.project.ProgettoSad.model.Game;


/**
*Interfaccia publica per i metodi di GameService
*
*/
public interface GameService {
	
	String createGame(Game game) throws ExceptionIllegalParameters, ExceptionMandatoryFields;
	
	Game endGame(ObjectId GID, String winner) throws IOException, ExceptionIllegalParameters;
	
	List<Game> readPlayerHistory(String PID);
	
	List<Game> getAllGames();
	
	Game getGameById(ObjectId GID);
	
}
