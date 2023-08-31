package ca.cmpt213.as3.myApp.restapi;

import ca.cmpt213.as3.myApp.model.MazeGame;

public class ApiGameWrapper {
    public int gameNumber;      // Same as the ID
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    public ApiGameWrapper( ApiBoardWrapper apiBoardWrapper) {
        MazeGame mazeGame = apiBoardWrapper.getMazeGame();
        this.isGameWon = mazeGame.hasUserWon();
        this.isGameLost = mazeGame.hasUserLost();
        this.numCheeseFound = mazeGame.getNumCheeseCollected();
        this.numCheeseGoal = mazeGame.getNumCheeseToCollect();
    }

    public int getGameNumber() {
        return gameNumber;
    }
    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }
    public void updateGame(ApiBoardWrapper apiBoardWrapper){
        MazeGame mazeGame = apiBoardWrapper.getMazeGame();
        this.isGameWon = mazeGame.hasUserWon();
        this.isGameLost = mazeGame.hasUserLost();
        this.numCheeseFound = mazeGame.getNumCheeseCollected();
        this.numCheeseGoal = mazeGame.getNumCheeseToCollect();
    }
}
