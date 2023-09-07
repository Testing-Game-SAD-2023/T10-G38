package com.project.ProgettoSad.model;

import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
*
* Classe che contiene le infromazioni relative al round.
* @param	roundId	L'ObjectId del round.
* @param	 gameId	L'ObjectId della partita a cui il round appartiene
* @param	roundNumber	Il numero dell'attuale round da giocare.
* @param	turn	HashMap contenente l'associazione tra i giocatori del round e le classi di test che questi hanno scritto.
* @param	results	La stringa che indica i risultati del round.
*/
@Document (collection = "RoundDB")
public class Round {
	@Id
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId roundId;
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId gameId;
	private int roundNumber;
	private HashMap<String,String> turn;
	private String robotTest;
	private String results;
	
	//CONSTRUCTORS
	public Round() {
		super();
		this.roundId = new ObjectId();
		this.turn = new HashMap<String,String>();
	}
	
	public Round(int roundNumber) {
		super();
		this.roundId = new ObjectId();
		this.roundNumber = roundNumber;
		this.turn = new HashMap<String,String>();
	}
	
	public Round(ObjectId gameId, int roundNumber) {
		super();
		this.gameId = gameId;
		this.roundNumber = roundNumber;
		this.turn = new HashMap<String,String>();
	}

	public Round(ObjectId roundId, ObjectId gameId, int roundNumber, HashMap<String,String> turn, String robotTest, String result) {
		super();
		this.roundId = roundId;
		this.gameId = gameId;
		this.roundNumber = roundNumber;
		this.turn = turn;
		this.robotTest = robotTest;
		this.results = result;
	}
	//GETTERSETTER
	public ObjectId getRoundId() {
		return roundId;
	}

	public void setRoundId(ObjectId roundId) {
		this.roundId = roundId;
	}
	
	public ObjectId getGameId() {
		return gameId;
	}

	public void setGameId(ObjectId gameId) {
		this.gameId = gameId;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public HashMap<String, String> getTurn() {
		return turn;
	}

	public void setTurn(HashMap<String, String> turn) {
		this.turn = turn;
	}

	public String getRobotTest() {
		return robotTest;
	}

	public void setRobotTest(String robotTest) {
		this.robotTest = robotTest;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}
	
}
