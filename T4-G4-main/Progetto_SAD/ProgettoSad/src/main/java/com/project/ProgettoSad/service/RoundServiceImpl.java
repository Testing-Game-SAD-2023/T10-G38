package com.project.ProgettoSad.service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ProgettoSad.model.*;
import com.project.ProgettoSad.exception.ExceptionIllegalParameters;
import com.project.ProgettoSad.exception.ExceptionResourceNotFound;
import com.project.ProgettoSad.repository.GameRepository;
import com.project.ProgettoSad.repository.RoundRepository;

/**
*Implementazione dell'interfaccia RoundService
*
*/
@Service
@Transactional
public class RoundServiceImpl implements RoundService {
	@Autowired
	private RoundRepository roundRepository;
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	/**
	*
	* Funzione che permette di inserire nel repository dei round il test case scritto dallo studente.
	* Controlla se l'ObjectId del round richiesto esiste, e se lo studente partecipa effettivamente al round, salva il test case,
	* ed inserisce nella directory del round un file .class con il test case
	* @param	RID	L'ObjectId relativo al round.
	* @param	studentID	L'Id realtivo allo studente di cui si vuole salvare il test case.
	* @param	testCase	La stringa contente il testCase scritto dallo studente.
	* @return	roundUpdate	L'oggetto Round relativo al round modificato.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id indicato.
	* @throws	ExceptionIllegalParameters	Nel caso in cui l'Id dello studente non corrisponde a nessuno di quelli
	* degli studenti che partecipano al round.
	*/
	
	@Override
	public Round updateTurnTest(ObjectId RID, String studentId, String testCase) throws IOException, ExceptionIllegalParameters {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			roundUpdate.setRoundId(RID);
			if(!roundUpdate.getTurn().containsKey(studentId)) {
				throw new ExceptionIllegalParameters("No student with the selected id has joined the game!");
			}
			roundUpdate.getTurn().replace(studentId, testCase);
			this.roundRepository.save(roundUpdate);
			
			Optional <Game> GameDB = this.gameRepository.findById(roundUpdate.getGameId());
			Game gameTmp = GameDB.get();
			
			Path path = Paths.get("C:\\Users\\Public\\AUTName\\" + studentId + "\\" + gameTmp.getId().toString() + "\\Round " + roundUpdate.getRoundNumber() + "\\Test Source Code");
			File fileHost = new File(path.toString());
			fileHost.mkdirs();
			
			  try { 
				  String fileName = new String("Test.class"); 
				  File test = new File(path.toString(), fileName); 
				  test.createNewFile();
				  
				  Writer writer = new BufferedWriter (new FileWriter(test)); 
				  writer.write(testCase);
				  
				  writer.flush(); 
				  writer.close();
			  
			  } catch (Exception e) { e.printStackTrace(); }
			  
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id : " + RID);
		}	
	}
	
	
	/**
	*
	* Funzione che permette di inserire nel repository dei round il risultato del round.
	* Controlla se l'ObjectId del round richiesto esiste, salva il risultato, ed inserisce 
	* nella directory del round un file .txt con il risultato.
	* @param	RID	L'ObjectId relativo al round.
	* @param	results	La stringa contenente i risultati della partita
	* @return	roundUpdate	L'oggetto Round relativo al round modificato.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id indicato.
	*/
	
	@Override
	public Round updateRoundResult(ObjectId RID, String results) throws IOException {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			Optional <Game> GameDB = this.gameRepository.findById(roundUpdate.getGameId());
			Game gameTmp = GameDB.get();
			
			roundUpdate.setRoundId(RID);
			roundUpdate.setResults(results);
			this.roundRepository.save(roundUpdate);
			
			Path path = Paths.get("C:\\Users\\Public\\AUTName\\" + gameTmp.getHost().getStudentId()+"\\"+ gameTmp.getId().toString() + "\\Round " + roundUpdate.getRoundNumber() + "\\Test Report");
			File file = new File(path.toString());
			file.mkdirs();
			
			try {
				String fileName = new String("Result.txt");
				File report = new File(path.toString(),fileName);
				report.createNewFile();
				
				String fileToWrite = new String(path+"\\Result.txt");
				Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
				writer.write(results);
				
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(gameTmp.getScenario() == 3) {
				for(int i = 0; i < gameTmp.getGuest().size(); i++) {
					path = Paths.get("C:\\Users\\Public\\AUTName\\" + gameTmp.getGuest().get(i).getStudentId()+"\\"+ gameTmp.getId().toString() + "\\Round " + roundUpdate.getRoundNumber() + "\\Test Report");
					file = new File(path.toString());
					file.mkdirs();
					try {
						String fileName = new String("Result.txt");
						File report = new File(path.toString(),fileName);
						report.createNewFile();

						String fileToWrite = new String(path+"\\Result.txt");
						Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
						writer.write(results);

						writer.flush();
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id: " + RID);
		}	
	}
	
	
	/**
	*
	* Funzione che permette di inserire nel repository dei round il test case del robot.
	* Controlla se l'ObjectId del round richiesto esiste e salva il risultato.
	* @param	RID	L'ObjectId relativo al round.
	* @param	robotTest	La stringa contenente il test case del robot.
	* @return	roundUpdate	L'oggetto Round relativo al round modificato.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id indicato.
	*/
	
	@Override
	public Round joinRobot(ObjectId RID, String robotTest) {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			roundUpdate.setRoundId(RID);
			roundUpdate.setRobotTest(robotTest);
			this.roundRepository.save(roundUpdate);
			
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id:" + RID);
		}
	}
	
	/**
	*
	* Funzione che ritorna la lista degli oggetti Round appartenenti alla partita 
	* contrassegnata da un determinato id, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	GID	L'ObjectId relativo alla partita di cui si vogliono conoscere le informazioni.
	* @return	List<Round>	La lista degli oggetti Round associati alla partita.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository una partita contrassegnata dall'Id indicato.
	*/
		
	@Override
	public List<Round> getRoundByGID(ObjectId GID) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameId").is(GID));
		List<Round> round = this.mongoTemplate.find(query, Round.class);
		if(round.isEmpty()) {
			throw new ExceptionResourceNotFound("No Round document exists for the Game with id: " + GID);
		}
		return round;
	}
	
	/**
	*
	* Funzione che ritorna l'oggetto Round contrassegnato da un determinato id, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	RID	L'ObjectId relativo al round di cui si vogliono conoscere le informazioni.
	* @return	round	L'oggetto Round richiesto.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id indicato.
	*/
		
	@Override
	public Round getRoundById(ObjectId RID) {
		Optional<Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			return RoundDB.get();
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id: " + RID);
		}
	}
	
	
	/**
	*
	* Funzione che ritorna la stringa contenente l'ObjectId del round contrassegnato
	*  da un determinato numero di round e dall'Id della partita a cui appartiene, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	GID	L'ObjectId relativo alla partita di cui fa parte il round.
	* @return	round	La stringa contenente l'ObjectId dell'oggetto Round richiesto.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id partita indicato e da quel numero di round.
	*/
	@Override
	public String getRoundByNumber(ObjectId GID, int roundNumber) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("gameId").is(GID),Criteria.where("roundNumber").is(roundNumber));
		Query query = new Query(criteria);
		Round round = this.mongoTemplate.findOne(query, Round.class);
		if(round.getRoundId()== null) {
			throw new ExceptionResourceNotFound("No Round document exists for the Game with id: " + GID + "and with round number: " + roundNumber);
		}
		return round.getRoundId().toString();
	}
	
	/**
	*
	* Funzione che ritorna gli oggetti relativi a tutti i round presenti nel repository.
	* @return	List<Round>	La lista di tutti gli oggetti Round relativi ai round.
	*/
	
	
	@Override
	public List<Round> getAllRounds(){
		return this.roundRepository.findAll();
	}
}
