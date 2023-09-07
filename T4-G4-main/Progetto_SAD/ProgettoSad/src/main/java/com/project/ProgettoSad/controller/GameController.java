package com.project.ProgettoSad.controller;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ProgettoSad.exception.ExceptionIllegalParameters;
import com.project.ProgettoSad.exception.ExceptionMandatoryFields;
import com.project.ProgettoSad.exception.ExceptionResourceNotFound;
import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.model.POJOResponse;
import com.project.ProgettoSad.model.Round;
import com.project.ProgettoSad.service.GameService;
import com.project.ProgettoSad.service.RoundService;

import jakarta.validation.Valid;


/**
*
* Controller 
* Gestisce le API REST
*
*/
@RestController
public class GameController {
	
	@Autowired
	private GameService gameService;

	@Autowired
	private RoundService roundService;
	
	/**
	*
	* Servizio POST per la creazione di una nuova partita e dei suoi round nel repository.
	* Controlla se i parametri inseriti dall'utente sono corretti, li salva in un nuovo 
	* documento corrispondente alla partita, e crea i documenti dei round a questa associati.
	* @param	game	(RequestBody) Contiene le informazioni inserite dall'utente come oggetto Game.
	* @return	id		La stringa contenente l'ObjectId del documento game appena creato.
	* @throws	ExceptionMandatoryFields	Nel caso in cui non siano stati inseriti l'host, la classe sotto test, o il robot.
	* @throws	ExceptionIllegalParameters	Nel caso in cui siano stati inseriti dei guest e la partita non sia del terzo scenario
	* oppure il numero di round </td><td> &#232; </td></tr> diverso da 1 e la partita </td><td> &#232; </td></tr> del primo scenario.
	*
	*
	*/
	@PostMapping ("/games")
	public ResponseEntity <String> createGame(@Valid @RequestBody Game game) throws ExceptionIllegalParameters, ExceptionMandatoryFields{
		return ResponseEntity.ok().body(gameService.createGame(game));
	}
	
	/**
	 * 
	*Servizio PUT per inserire le informazioni finali della partita.
	* Permette di inserire nella repository il vincitore della partita, controllando che questo sia
	* corretto, e automaticamente aggiorna la data di fine partita.
	* 
	* @param	GID		(PathVariable) L'ObjectId relativo alla partita.
	* @param	winner	(RequestBody) La stringa che indica il vincitore.
	* @return	game	Il documento JSON Game relativo alla partita.
	* @throws	ExceptionIllegalParameters	Nel caso in cui il vincitore non corrisponda ne'
	*	all'host ne' ai guest (caso terzo scenario) ne' al robot .
	*
	*
	*/
	@PutMapping("/games/end/{GID}")
	public ResponseEntity <Game> endGame(@PathVariable ObjectId GID, @RequestBody String winner) throws IOException, ExceptionIllegalParameters{
		return ResponseEntity.ok().body(this.gameService.endGame(GID,winner));
	}
	
	/**
	*
	*Servizio PUT per inserire nel repository dei round il test case scritto dallo studente.
	* Controlla se l'ObjectId del round richiesto esiste e se lo studente partecipa effettivamente al round, e salva il test case.
	* @param	RID	(PathVariable) L'ObjectId relativo al round.
	* @param	PID	(PathVariable) L'Id realtivo allo studente di cui si vuole salvare il test case.
	* @param	testCase	(RequestBody) La stringa contente il testCase scritto dallo studente.
	* @return	round	Il documento JSON Round relativo al round modificato.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id indicato.
	* @throws	ExceptionIllegalParameters	Nel caso in cui l'Id dello studente non corrisponde a nessuno di quelli
	* degli studenti che partecipano al round.
	*/
	@PutMapping("/rounds/{RID}/{PID}")
	public ResponseEntity<Round> updateTurnTest(@PathVariable ObjectId RID, @PathVariable String PID, @RequestBody String testcase) throws IOException, ExceptionIllegalParameters{
		return ResponseEntity.ok().body(this.roundService.updateTurnTest(RID,PID, testcase));
	}
	
	/**
	*
	*Servizio PUT per inserire nel repository dei round il risultato del round.
	* Controlla se l'ObjectId del round richiesto esiste e salva il risultato.
	* @param	RID	(PathVariable) L'ObjectId relativo al round.
	* @param	results	(RequestBody) La stringa contenente i risultati della partita
	* @return	round	Il documento JSON Round relativo al round modificato.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id indicato.
	*/
	@PutMapping("/rounds/result/{RID}")
	public ResponseEntity<Round> updateRoundResult(@PathVariable ObjectId RID, @RequestBody String results) throws IOException{
		return ResponseEntity.ok().body(this.roundService.updateRoundResult(RID, results));
	}
	
	
	/**
	*
	*Servizio PUT per inserire nel repository dei round il test case del robot.
	* Controlla se l'ObjectId del round richiesto esiste e salva il risultato.
	* @param	RID	(PathVariable) L'ObjectId relativo al round.
	* @param	robotTest	(RequestBody) La stringa contenente il test case del robot.
	* @return	round	Il documento JSON Round relativo al round modificato.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id indicato.
	*/
	@PutMapping("/rounds/robot/{RID}")
	public ResponseEntity<Round> joinRobot(@PathVariable ObjectId RID, @RequestBody String robotTest){
		return ResponseEntity.ok().body(this.roundService.joinRobot(RID, robotTest));
	}
	
	
	/**
	*
	* Servizio GET che ritorna in formato JSON i documenti relativi a tutte le partite presenti nel repository.
	* @return	List<Game>	JSON con tutti i documenti Game relativi alle partite.
	*/
	@GetMapping("/games")
	public ResponseEntity <List <Game>> getAllGames(){
		return ResponseEntity.ok().body(gameService.getAllGames());
	}
	
	
	/**
	*
	* Servizio GET che ritorna in formato JSON i documenti relativi a tutti i round presenti nel repository.
	* @return	List<Round>	JSON con tutti i documenti Round relativi ai round.
	*/
	@GetMapping("/rounds")
	public ResponseEntity <List <Round>> getAllRounds(){
		return ResponseEntity.ok().body(roundService.getAllRounds());
	}
	
	
	/**
	*
	* Servizio GET che ritorna in formato JSON il documento Game contrassegnato da un determinato Id, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	GID	(PathVariable) L'ObjectId relativo alla partita di cui si vogliono conoscere le informazioni.
	* @return	game	Il documento in formato JSON Game richiesto.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository una partita contrassegnata dall'Id indicato.
	*/
	@GetMapping("/games/{GID}")
	public ResponseEntity <Game> getGameById(@PathVariable ObjectId GID){
		return ResponseEntity.ok().body(gameService.getGameById(GID));
	}

	
	/**
	*
	* Servizio GET che ritorna in formato JSON il documento Round contrassegnato da un determinato Id, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	RID	(PathVariable) L'ObjectId relativo al round di cui si vogliono conoscere le informazioni.
	* @return	game	Il documento in formato JSON Round richiesto.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnata dall'Id indicato.
	*/
	@GetMapping("/rounds/{RID}")
	public ResponseEntity <Round> getRoundById(@PathVariable ObjectId RID){
		return ResponseEntity.ok().body(roundService.getRoundById(RID));
	}
	
	
	/**
	*
	* Servizio GET che ritorna in formato JSON i documenti relativi a tutte le partite giocate da un determinato studente.
	* @param	PID	(PathVariable) La stringa che indica l'Id dello studente di cui si vuole conoscere lo storico
	* @return	List<Game>	JSON con tutti i documenti Game relativi alle partite giocate dallo studente.
	*/
	@GetMapping("/games/player/{PID}")
	public ResponseEntity <List<Game>> readPlayerHistory(@PathVariable String PID){
		return ResponseEntity.ok().body(this.gameService.readPlayerHistory(PID));
	}
	
	
	/**
	*
	* Servizio GET che ritorna in formato JSON i documenti relativi a tutti i Round appartenenti alla partita 
	* contrassegnata da un determinato Id, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	GID	(PathVariable) L'ObjectId relativo alla partita di cui si vogliono conoscere le informazioni.
	* @return	List<Round>	JSON con tutti i documenti Round associati alla partita.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository una partita contrassegnata dall'Id indicato.
	*/
	@GetMapping("/rounds/find/{GID}")
	public  ResponseEntity<List<Round>> getRoundByGID(@PathVariable ObjectId GID){
		return ResponseEntity.ok().body(this.roundService.getRoundByGID(GID));
	}
	
	
	/**
	*
	* Servizio GET che ritorna in formato JSON i documenti relativi ad una partita contrassegnata da un determinato Id (se questo </td><td> &#232; </td></tr> 
	* presente nel repository) e a tutti i suoi Round.
	* @param	GID	(PathVariable) L'ObjectId relativo alla partita di cui si vogliono conoscere le informazioni.
	* @return	POJOResponse	JSON con il documento Game e tutti i documenti Round associati alla partita.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository una partita contrassegnata dall'Id indicato.
	*/	
	@GetMapping("/games/rounds/{GID}")
    public ResponseEntity<POJOResponse> readGame(@PathVariable ObjectId GID){
        POJOResponse response = new POJOResponse(); 
        response.setGame(this.gameService.getGameById(GID));
        response.setRounds(this.roundService.getRoundByGID(GID));

        return ResponseEntity.ok().body(response);

    }
	
	/**
	*
	* Servizio GET che ritorna in formato JSON il documento Round contrassegnato da un determinato Id partita e da un determinato numero del round, se questo </td><td> &#232; </td></tr> presente nel repository.
	* @param	GID	(PathVariable) L'ObjectId relativo al game a cui il round appartiene.
	* @param	roundNumber	(PathVariable) Il numero del round corrispondente al round richiesto. 
	* @return	round	La stringa contenente l'ObjectId del documento Round richiesto.
	* @throws	ExceptionResourceNotFound	Nel caso in cui non esista nel repository un round contrassegnato dall'Id partita indicato e da quel numero di round.
	*/	
	@GetMapping("/rounds/{GID}/{roundNumber}")
	public ResponseEntity <String> getRoundByNumber(@PathVariable ObjectId GID, @PathVariable int roundNumber){
		return ResponseEntity.ok().body(this.roundService.getRoundByNumber(GID, roundNumber));
	}


}
