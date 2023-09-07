package com.project.ProgettoSad.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.ProgettoSad.exception.ExceptionIllegalParameters;
import com.project.ProgettoSad.exception.ExceptionMandatoryFields;
import com.project.ProgettoSad.exception.ExceptionResourceNotFound;
import com.project.ProgettoSad.model.ClassUT;
import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.model.Guest;
import com.project.ProgettoSad.model.Host;
import com.project.ProgettoSad.model.Robot;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.Argument;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import groovy.json.*;

class ControllerTest {

	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game valido, lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void givenEverythingIsOk_WhenGameIsCreated_Then200StatusCodeIsReceived()throws ExceptionMandatoryFields {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(200);
	}
	
	
	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game che non contiene l'host, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void givenHostIsNotInserted_WhenGameIsCreated_Then500CodeIsReceived()throws ExceptionMandatoryFields {
		
		
		Game game = new Game();
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);
		
		
		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game che non contiene la classe da testare, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void givenClassUTIsNotInserted_WhenGameIsCreated_Then500CodeIsReceived()throws ExceptionMandatoryFields {
	
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		Robot robot = new Robot("Evosuite");
		game.setRobot(robot);
		
		
		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	
	
	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game che non contiene il robot, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void givenRobotIsNotInserted_WhenGameIsCreated_Then500CodeIsReceived()throws ExceptionMandatoryFields {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		game.setClassUt(classUt);
		
		
		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	
	
	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game in cui lo scenario è diverso
	* dai valori prestabiliti (1, 2 o 3), lo statusCode sia pari a 400 (Bad Request)
	*/
	@Test
	void givenScenarioOutOfBoundary_WhenGameIsCreated_Then400StatusCodeIsReceived()throws ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(5);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(400);
	}

	
	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game in cui il numero di round totali 
	* è negativo, lo statusCode sia pari a 400 (Bad Reaquest)
	*/
	@Test
	void givenNegativeTotalRoundNumber_WhenGameIsCreated_Then400StatusCodeIsReceived()throws ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(2);
		game.setTotalRoundNumber(-1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(400);
	}
	
	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game in cui sono presenti guest anche se lo scenario 
	* è diverso da 3, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void givenIllegalGuests_WhenGameIsCreated_Then500StatusCodeIsReceived()throws ExceptionMandatoryFields,ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		List<Guest> guest = new ArrayList<Guest>();
		Guest boh = new Guest("heh");
		guest.add(boh);
		game.setGuest(guest);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	
	/**
	*
	*Test sul servizio POST ("/games").
	* Controlla che, all'inserimento di un oggetto Game in cui si ha più di un round
	* anche se lo scenario è 1, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void givenIncorrectTotalRoundNumber_WhenGameIsCreated_Then500StatusCodeIsReceived()throws ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(2);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	/**
	*
	*Test sul servizio GET ("/games/{GID}").
	* Controlla che, all'inserimento di un Id partita valido, le informazioni ritornate siano 
	* corrette e corrispondenti alla partita contrassegnata dall'Id indicato, e che lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenGameIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","6488d08bff95c849a16d1f96").when().get("/games/{GID}").then()
		.assertThat()
		.body("host.studentId", equalTo("Ciccio Cecchin"))
		.body("robot.difficulty", equalTo("Normal"))
		.body("robot.robotId", equalTo("Evosuite"))
		.body("classUt.classId", equalTo("boh"))
		.body("classUt.classBody", equalTo("heh"))
		.body("scenario", equalTo(1))
		.statusCode(200);
	}

	/**
	*
	*Test sul servizio GET ("/games/{GID}").
	* Controlla che, all'inserimento di un Id partita non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenGameIsNotPresent_WhenGameIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").when().get("/games/{GID}").then().assertThat().statusCode(500);
	}
	/**
	*
	*Test sul servizio GET ("/rounds/{RID}").
	* Controlla che, all'inserimento di un Id round valido, lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenRoundIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("RID","6488d08bff95c849a16d1f97").when().get("/rounds/{RID}").then().assertThat().statusCode(200);
	}
	
	/**
	*
	*Test sul servizio GET ("/rounds/{RID}").
	* Controlla che, all'inserimento di un Id round non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenRoundIsNotPresent_WhenRoundIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("RID","123456789012345678901234").when().get("/rounds/{RID}").then().assertThat().statusCode(500);
	}
	
	/**
	*
	*Test sul servizio GET ("/rounds/find/{GID}").
	* Controlla che, all'inserimento di un Id partita valido, lo statusCode sia pari a 200(OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenRoundByGameIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","6488d08bff95c849a16d1f96").when().get("/rounds/find/{GID}").then().assertThat().statusCode(200);
	}
	
	/**
	*
	*Test sul servizio GET ("/rounds/find/{GID}").
	* Controlla che, all'inserimento di un Id partita non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenRoundIsNotPresent_WhenRoundByGameIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").when().get("/rounds/find/{GID}").then().assertThat().statusCode(500);
	}

	/**
	*
	*Test sul servizio GET ("/games/rounds/{GID}").
	* Controlla che, all'inserimento di un Id partita valido, le informazioni ritornate siano 
	* corrette e corrispondenti alla partita contrassegnata dall'Id indicato ed ai suoi round, e che lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenGameWithRoundsIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","6489c8c7eb21cc25289e6f2e").when().get("/games/rounds/{GID}").then()
		.assertThat()
		.body("game.host.studentId", equalTo("Ciccio_Cecchin"))
		.body("game.robot.robotId", equalTo("Evosuite"))
		.body("game.classUt.classId", equalTo("boh"))
		.body("game.scenario", equalTo(1))
		.body("rounds[0].turn.Ciccio_Cecchin", equalTo("N/A"))
		.statusCode(200);
	}
	
	/**
	*
	*Test sul servizio GET ("/games/rounds/{GID}").
	* Controlla che, all'inserimento di un Id partita non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenGameIsNotPresent_WhenGameWithRoundsIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").when().get("/games/rounds/{GID}").then().assertThat().statusCode(500);
	}
	
	/**
	*
	*Test sul servizio GET ("/rounds/{GID}/{roundNumber}").
	* Controlla che, all'inserimento di un Id partita e di un numero di round validi, lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenRoundByNumberIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().get("/rounds/{GID}/{roundNumber}","6489c8c7eb21cc25289e6f2e",1).then().assertThat().statusCode(200);
	}
	
	/**
	*
	*Test sul servizio GET ("/rounds/{GID}/{roundNumber}").
	* Controlla che, all'inserimento di un Id partita non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenGameIsNotPresent_WhenRoundByNumberIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").pathParam("roundNumber", 1).when().get("/rounds/{GID}/{roundNumber}").then().assertThat().statusCode(500);
	}

	/**
	*
	*Test sul servizio GET ("/rounds/{GID}/{roundNumber}").
	* Controlla che, all'inserimento di un numero di round non esistente, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenRoundNumberIsNotPresent_WhenRoundByNumberIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","6489c8c7eb21cc25289e6f2e").pathParam("roundNumber", 10).when().get("/rounds/{GID}/{roundNumber}").then().assertThat().statusCode(500);
	}

	/**
	*
	*Test sul servizio GET ("/games/player/{PID}").
	* Controlla che, all'inserimento di un Id studente valido, le informazioni ritornate siano 
	* corrette, e che lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenPlayerHistoryIsRequested_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound {
		given().pathParam("PID","Ciccio_Cecchin").when().get("/games/player/{PID}").then().assertThat()
		.body("_id.toString()",equalTo("[6489c8c7eb21cc25289e6f2e]"))
		.statusCode(200);
	}
	
	
	/**
	*
	*Test sul servizio GET ("/games/player/{PID}").
	* Controlla che, all'inserimento di un Id studente non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenPlayerDoesNotExist_WhenPlayerHistoryIsRequested_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound {
		given().pathParam("PID","Ciccio_Cecchini").when().get("/games/player/{PID}").then().assertThat().statusCode(500);
	}

	
	
	/**
	*
	*Test sul servizio PUT ("/games/end/{GID}").
	* Controlla che, all'inserimento di un Id studente valido come vincitore e di un Id
	* partita valido, le informazioni ritornate siano corrette, e che lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenGameEnds_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound, ExceptionIllegalParameters {
		String winner = new String("cane");
		given().contentType("application/json").body(winner).pathParam("GID","6489ddee4456683a05eefa93").when().put("/games/end/{GID}").then().assertThat()
		.body("winner", equalTo(winner))
		.statusCode(200);
	}
	
	/**
	*
	*Test sul servizio PUT ("/games/end/{GID}").
	* Controlla che, all'inserimento di un Id studente non valido come vincitore, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenWinnerNotPresent_WhenGameEnds_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound, ExceptionIllegalParameters  {
		String winner = new String("boh");
		given().contentType("application/json").body(winner).pathParam("GID","6489ddee4456683a05eefa93").when().put("/games/end/{GID}").then().assertThat().statusCode(500);
	}
	
	
	/**
	*
	*Test sul servizio PUT ("/games/end/{GID}").
	* Controlla che, all'inserimento di un Id partita non valido, 
	* lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenGameNotPresent_WhenGameEnds_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound, ExceptionIllegalParameters  {
		String winner = new String("cane");
		given().contentType("application/json").body(winner).pathParam("GID","6489ddee4456683a05eefa97").when().put("/games/end/{GID}").then().assertThat().statusCode(500);
	}

	
	/**
	*
	*Test sul servizio PUT ("/rounds/{RID}/{PID}").
	* Controlla che, all'inserimento di un Id studente e di un Id round validi,  
	* le informazioni ritornate siano corrette, e che lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenTestIsSaved_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound, ExceptionIllegalParameters {
		String test = new String("Lorem Ipsum");
		given().contentType("application/json").body(test).when().put("/rounds/{RID}/{PID}","6489ddee4456683a05eefa95","Ciccio_Cecchin").then().assertThat()
		.body("turn.Ciccio_Cecchin", equalTo(test))
		.statusCode(200);
	}
	
	
	/**
	*
	*Test sul servizio PUT ("/rounds/{RID}/{PID}").
	* Controlla che, all'inserimento di un Id studente non valido, 
	* lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenPlayerIsMissing_WhenTestIsSaved_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound, ExceptionIllegalParameters {
		String test = new String("Lorem Ipsum");
		given().contentType("application/json").body(test).when().put("/rounds/{RID}/{PID}","6489ddee4456683a05eefa95","Ciccio_Cecchina").then().assertThat().statusCode(500);
	}

	
	/**
	*
	*Test sul servizio PUT ("/rounds/{RID}/{PID}").
	* Controlla che, all'inserimento di un Id round non valido, 
	* lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenRoundNotPresent_WhenTestIsSaved_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound, ExceptionIllegalParameters {
		String test = new String("Lorem Ipsum");
		given().contentType("application/json").body(test).when().put("/rounds/{RID}/{PID}","6489ddee4456683a05eefa90","Ciccio_Cecchin").then().assertThat().statusCode(500);
	}

	
	/**
	*
	*Test sul servizio PUT ("/rounds/result/{RID}").
	* Controlla che, all'inserimento di un Id round valido,  
	* le informazioni ritornate siano corrette, e che lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenRoundResultIsSaved_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound {
		String results = new String("Lorem Ipsum");
		given().contentType("application/json").body(results).when().put("/rounds/result/{RID}","6489ddee4456683a05eefa95").then().assertThat()
		.body("results", equalTo(results))
		.statusCode(200);
	}

	
	/**
	*
	*Test sul servizio PUT ("/rounds/result/{RID}").
	* Controlla che, all'inserimento di un Id round non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenRoundNotPresent_WhenRoundResultIsSaved_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound {
		String results = new String("Lorem Ipsum");
		given().contentType("application/json").body(results).when().put("/rounds/result/{RID}","6489ddee4456683a05eefa90").then().assertThat().statusCode(500);
	}

	
	/**
	*
	*Test sul servizio PUT ("/rounds/robot/{RID}").
	* Controlla che, all'inserimento di un Id round valido,  
	* le informazioni ritornate siano corrette, e che lo statusCode sia pari a 200 (OK)
	*/
	@Test
	void GivenEverythingIsOk_WhenRobotJoins_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound {
		String test = new String("Lorem Ipsum");
		given().contentType("application/json").body(test).when().put("/rounds/robot/{RID}","6489ddee4456683a05eefa95").then().assertThat()
		.body("robotTest", equalTo(test))
		.statusCode(200);
	}
	
	
	/**
	*
	*Test sul servizio PUT ("/rounds/robot/{RID}").
	* Controlla che, all'inserimento di un Id round non valido, lo statusCode sia pari a 500 (Internal Server Error)
	*/
	@Test
	void GivenRoundNotPresent_WhenRobotJoins_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound {
		String test = new String("Lorem Ipsum");
		given().contentType("application/json").body(test).when().put("/rounds/robot/{RID}","6489ddee4456683a05eefa90").then().assertThat().statusCode(500);
	}


}
