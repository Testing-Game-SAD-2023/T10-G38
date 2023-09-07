package com.project.ProgettoSad.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
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
import com.opencsv.CSVWriter;
import com.project.ProgettoSad.exception.ExceptionIllegalParameters;
import com.project.ProgettoSad.exception.ExceptionMandatoryFields;
import com.project.ProgettoSad.exception.ExceptionResourceNotFound;
import com.project.ProgettoSad.repository.GameRepository;
import com.project.ProgettoSad.repository.RoundRepository;

/**
*Implementazione dell'interfaccia GameService
*
*/
@Service
@Transactional
public class GameServiceImpl implements GameService {
	 
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private RoundRepository roundRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	*
	* Funzione di utility per controllare che i parametri che l'utente 
	* inserisce nella funzione createGame siano corretti e completi.
	* 
	* @param	game	Contiene le informazioni inserite dall'utente come oggetto Game.
	* @throws	ExceptionMandatoryFields	Nel caso in cui non siano stati inseriti l'host, la classe sotto test, o il robot.
	* @throws	ExceptionIllegalParameters	Nel caso in cui siano stati inseriti dei guest e la partita non sia del terzo scenario
	* oppure il numero di round </td><td> &#232; </td></tr> diverso da 1 e la partita </td><td> &#232; </td></tr> del primo scenario.
	*
	*
	*/
	
	private void check(Game game) throws ExceptionMandatoryFields, ExceptionIllegalParameters {
		if(game.getHost() == null || game.getClassUt() == null || game.getRobot() == null) {
			throw new ExceptionMandatoryFields("Host, Robot and ClassUT are mandatory!");
		}
		
		if(game.getScenario() != 3 && game.getGuest() != null) {
			throw new ExceptionIllegalParameters("Game doesn't allow for Guests!");
		}
		else if((game.getScenario() == 1 && game.getTotalRoundNumber() != 1)) {
			throw new ExceptionIllegalParameters("First Scenario cannot have more than one round!");
		}
	}
	
	
	/**
	*
	* Funzione di utility per controllare che i parametri che l'utente 
	* inserisce nella funzione endGame siano corretti e completi.
	* 
	* @param	game	Contiene le informazioni inserite dall'utente come oggetto Game.
	* @param	winner	La stringa che indica il vincitore.
	* @throws	ExceptionIllegalParameters	Nel caso in cui il vincitore non corrisponda n</td><td> &#233; </td></tr>
	*	all'host n</td><td> &#233; </td></tr> ai guest (caso terzo scenario) n</td><td> &#233; </td></tr> al robot 
	*/
	
	private void checkWinner(String winner, Game game) throws ExceptionIllegalParameters{
		if(game.getScenario() != 3) {
			if(!game.getHost().getStudentId().equals(winner) && !game.getRobot().getRobotId().equals(winner)) {
				throw new ExceptionIllegalParameters("Winner hasn't played the game!");
			}
		}
		else {
			if(!game.getHost().getStudentId().equals(winner) && !game.getRobot().getRobotId().equals(winner)) {
				Boolean found = false;
				int i = 0;
				while(found.equals(false) && i < game.getGuest().size()) {
					if(game.getGuest().get(i).getStudentId().equals(winner)) {
						found = true;
					}
					i++;
				}
				if(found.equals(false)) {
					throw new ExceptionIllegalParameters("Winner hasn't played the game!");
				}
			}
		}
	}
	
	
	/**
	*
	* Funzione che controlla se i parametri inseriti dall'utente sono corretti, li salva in un nuovo 
	* documento corrispondente alla partita, crea i documenti dei round a questa associati, e crea, se queste 
	* non sono gia' presenti, le directory relative ai giocatori nel file system.
	* 
	* @param	game	Contiene le informazioni inserite dall'utente come oggetto Game.
	* @return	id		La stringa contenente l'ObjectId del documento game appena creato.
	* @throws	ExceptionMandatoryFields	Nel caso in cui non siano stati inseriti l'host, la classe sotto test, o il robot.
	* @throws	ExceptionIllegalParameters	Nel caso in cui siano stati inseriti dei guest e la partita non sia del terzo scenario
	* oppure il numero di round </td><td> &#232; </td></tr> diverso da 1 e la partita </td><td> &#232; </td></tr> del primo scenario.
	*
	*
	*/
	
	@Override
	public String createGame(Game game) throws ExceptionIllegalParameters, ExceptionMandatoryFields {
		
		check(game);
		
		Game GameDB = gameRepository.save(game);
		if (game.getScenario() == 1) {
			Round roundTmp = new Round(GameDB.getId(),1);
			roundTmp.getTurn().put(game.getHost().getStudentId(), "N/A");
			this.roundRepository.save(roundTmp );
		}
		else if(game.getScenario() == 2) {
			for(int i = 1; i <= game.getTotalRoundNumber(); i++) {
				Round roundTmp = new Round(GameDB.getId(),i);
				roundTmp.getTurn().put(game.getHost().getStudentId(), "N/A");
				this.roundRepository.save(roundTmp);
			}
		}
		else {
			for(int i = 1; i <= game.getTotalRoundNumber(); i++) {
				Round roundTmp = new Round(GameDB.getId(),i);
					roundTmp.getTurn().put(game.getHost().getStudentId(), "N/A");
					for(int j = 0; j < game.getGuest().size(); j++) {
						roundTmp.getTurn().put(game.getGuest().get(j).getStudentId(), "N/A");
					}				
					
				this.roundRepository.save(roundTmp);
			}
		}
		
		Path path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getHost());
		if(!Files.exists(path)) {
			File fileHost = new File(path.toString());
			fileHost.mkdirs();
		}
		
		if(game.getScenario() == 3) {
			for(int i = 0; i < game.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getGuest().get(i).getStudentId());
				if(!Files.exists(path)) {
					File fileGuest = new File(path.toString());
					fileGuest.mkdirs();
				}
			}
		}

		
		path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getHost() + "\\" + game.getId().toString());
		File fileHostGame = new File(path.toString());
		fileHostGame.mkdirs();
		
		if(game.getScenario() == 3) {
			for(int i = 0; i < game.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getGuest().get(i).getStudentId() + "\\" + game.getId().toString());
				File fileGuestGame = new File(path.toString());
				fileGuestGame.mkdirs();
			}	
		}

		return GameDB.getId().toString();		
	}
	
	/**
	*
	* Funzione che permette di inserire nella repository il vincitore della partita, controllando che questo sia
	* corretto. Inoltre crea un file .csv nella directory di tutti i giocatori contenente tutte le informazioni
	* della partita appena finita.
	* 
	* @param	GID		L'ObjectId relativo alla partita.
	* @param	winner	La stringa che indica il vincitore.
	* @return	gameUpdate	L'oggetto Game relativo alla partita.
	* @throws	ExceptionIllegalParameters	Nel caso in cui il vincitore non corrisponda ne'
	*	all'host ne' ai guest (caso terzo scenario) ne' al robot .
	*
	*
	*/
	
	@Override
	public Game endGame(ObjectId GID,String winner) throws IOException,ExceptionIllegalParameters {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		if(GameDB.isPresent()) {
			Game gameUpdate = GameDB.get();
			gameUpdate.setId(GID);
			checkWinner(winner,gameUpdate);
			gameUpdate.setWinner(winner);
			gameUpdate.setEndDate(LocalDateTime.now());
			this.gameRepository.save(gameUpdate);
			
			Path path = Paths.get("C:\\Users\\Public\\AUTName\\" + gameUpdate.getHost() + "\\" + gameUpdate.getId().toString()+"\\Game.csv");
			File file = new File(path.toString());
			
			try {
				FileWriter writer = new FileWriter(file);
				CSVWriter csvWriter = new CSVWriter(writer);
				
				String[] header = {"ID", "Host", "Guest","Robot","Difficulty","Scenario","Number of Rounds","ClassUT ID","ClassUT Path","Started At","Ended At","Winner"};
				csvWriter.writeNext(header);
				csvWriter.flush();

				if(gameUpdate.getScenario()==3) {
					String[] data = {gameUpdate.getId().toString(), gameUpdate.getHost().getStudentId(), gameUpdate.getGuest().toString(), gameUpdate.getRobot().getRobotId(), gameUpdate.getRobot().getDifficulty(), ""+gameUpdate.getScenario()+"", ""+gameUpdate.getTotalRoundNumber()+"", gameUpdate.getClassUt().getClassId(), gameUpdate.getClassUt().getClassBody(), gameUpdate.getStartDate().toString(), gameUpdate.getEndDate().toString(), gameUpdate.getWinner()};
					csvWriter.writeNext(data);
					csvWriter.flush();				

					for(int i = 0; i < gameUpdate.getGuest().size(); i++) {
						path = Paths.get("C:\\Users\\Public\\AUTName\\" + gameUpdate.getGuest().get(i).getStudentId() + "\\" + gameUpdate.getId().toString()+"\\Game.csv");
						file = new File(path.toString());

						writer = new FileWriter(file);
						csvWriter = new CSVWriter(writer);

						csvWriter.writeNext(header);
						csvWriter.writeNext(data);
					}
				}
				else {
					String[] data = {gameUpdate.getId().toString(), gameUpdate.getHost().getStudentId(), "N/A", gameUpdate.getRobot().getRobotId(), gameUpdate.getRobot().getDifficulty(), ""+gameUpdate.getScenario()+"", ""+gameUpdate.getTotalRoundNumber()+"", gameUpdate.getClassUt().getClassId(), gameUpdate.getClassUt().getClassBody(), gameUpdate.getStartDate().toString(), gameUpdate.getEndDate().toString(), gameUpdate.getWinner()};
					csvWriter.writeNext(data);
					csvWriter.flush();
				}
				csvWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return gameUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("Game document not found with id : " + GID);
		}
	}

	
	/**
	*
	* Funzione che ritorna gli oggetti relativi a tutte le partite presenti nel repository.
	* @return	List<Game>	La lista di tutti gli oggetti Game relativi alle partite.
	*/
	
	@Override
	public List<Game> getAllGames(){
		return this.gameRepository.findAll();
	}
	
	/**
	*
	* Funzione che ritorna gli oggetti relativi a tutte le partite giocate da un determinato studente.
	* @param	PID	La stringa che indica l'Id dello studente di cui si vuole conoscere lo storico
	* @return	List<Game>	La lista di tutti gli oggetti Game relativi alle partite giocate dallo studente.
	*/
	
	@Override
	public List<Game> readPlayerHistory(String PID) {
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("host.studentId").is(PID),Criteria.where("guest.studentId").is(PID));
		Query query = new Query(criteria);
		List<Game> playerHistory = mongoTemplate.find(query,Game.class);
		if(playerHistory.isEmpty()) {
			throw new ExceptionResourceNotFound("No player exists within the collection with Id:"+PID);
		}
		return playerHistory;
	}

	/**
	*
	* Funzione che ritorna l'oggetto Game contrassegnato da un determinato id, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	GID	L'ObjectId relativo alla partita di cui si vogliono conoscere le informazioni.
	* @return	game	L'oggetto Game richiesto.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository una partita contrassegnata dall'Id indicato.
	*/
		
	@Override
	public Game getGameById (ObjectId GID) {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		
		if(GameDB.isPresent()) {
			return GameDB.get();
		}
		else {
			throw new ExceptionResourceNotFound("No Game document exists with Id:" + GID);
		}
	}
}
