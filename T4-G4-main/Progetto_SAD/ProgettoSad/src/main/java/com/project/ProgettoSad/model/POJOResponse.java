package com.project.ProgettoSad.model;

import java.util.List;
/**
*
* Classe utility per l'implementazione di readGame
*/

public class POJOResponse {

    private Game game;
    private List<Round> rounds;

    public POJOResponse () {


    }

    public POJOResponse(Game game, List<Round> rounds) {
        this.setGame(game);
        this.rounds= rounds; 
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

}
	
