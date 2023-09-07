package com.project.ProgettoSad.model;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.bson.types.ObjectId;

/**
*
* Classe che contiene le infromazioni relative alla partita.
* @param	_id	L'ObjectId della partita.
* @param	 startDate	La data formato "yyyy-MM-dd HH:mm:ss" di inizio della partita.
* @param	 endDate	La data formato "yyyy-MM-dd HH:mm:ss" di fine della partita.
* @param	host	Lo studente che inzia la partita.
* @param	guest	La lista degli studenti che partecipano alla partita.
* @param	robot	Il robot che partecipa alla partita.
* @param	scenario	Lo scenario di gioco della partita (puo' essere 1, 2 o 3).
* @param	totalRoundNumber	Il numero totale di round da giocare nella partita.
* @param	classUt	La classe under test scelta.
* @param	winner	La stringa che indica il vincitore della partita.
*/

@Document (collection = "GameDB")
public class Game{
	
	@Id
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId _id;
	@CreatedDate
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;
	private Host host;
	private List<Guest> guest; 
	Robot robot;
	@Min(value = 1)
	@Max(value = 3)
	private int scenario;
	@Min(value = 1)
	private int totalRoundNumber;
	private ClassUT classUt;
	private String winner;
	
	//CONSTRUCTORS
	public Game() {
		super();
	}
	
	public Game(ObjectId _id, LocalDateTime startDate, LocalDateTime endDate, Host host,
			List<Guest> guest, Robot robot, int scenario, int totalRoundNumber, ClassUT classUt, String winner) {
		super();
		this._id = ObjectId.get();
		this.startDate = startDate;
		this.endDate = null;
		this.host = host;
		this.guest = guest;
		this.robot = robot;
		this.scenario = scenario;
		this.totalRoundNumber = totalRoundNumber;
		this.classUt = classUt;
		this.winner = null;
	}
	
	public Game(ObjectId _id, LocalDateTime startDate, LocalDateTime endDate, Host host,
			List<Guest> guest, Robot robot, int scenario, ClassUT classUt, String winner) {
		super();
		this._id = ObjectId.get();
		this.startDate = startDate;
		this.endDate = null;
		this.host = host;
		this.guest = guest;
		this.robot = robot;
		this.scenario = scenario;
		this.totalRoundNumber = 1;
		this.classUt = classUt;
		this.winner = null;
	}
	//GETTERSETTER
	public ObjectId getId() {
		return _id;
	}
	public void setId(ObjectId id) {
		this._id = id;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public Host getStudente() {
		return host;
	}
	public void setStudente(Host host) {
		this.host = host;
	}
	public int getScenario() {
		return scenario;
	}
	public void setScenario(int scenario) {
		this.scenario = scenario;
	}
	public ClassUT getClassUt() {
		return classUt;
	}
	public void setClassUt(ClassUT classUt) {
		this.classUt = classUt;
	}
	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}
		
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<Guest> getGuest() {
		return guest;
	}

	public void setGuest(List<Guest> guest) {
		this.guest = guest;
	}

	public int getTotalRoundNumber() {
		return totalRoundNumber;
	}

	public void setTotalRoundNumber(int totalRoundNumber) {
		this.totalRoundNumber = totalRoundNumber;
	}
	
}
