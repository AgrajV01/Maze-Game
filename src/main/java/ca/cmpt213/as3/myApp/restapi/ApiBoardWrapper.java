package ca.cmpt213.as3.myApp.restapi;
import ca.cmpt213.as3.myApp.model.*;

import java.util.ArrayList;
import java.util.List;

public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    public static MazeGame mazeGame;

    // Accept whatever object(s) you need to populate this object.
    public static ApiBoardWrapper makeFromGame() {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();
        mazeGame = wrapper.getMazeGame();
        wrapper.boardWidth = mazeGame.getMaze().getWidth();
        wrapper.boardHeight = mazeGame.getMaze().getHeight();
        wrapper.mouseLocation = new ApiLocationWrapper(mazeGame.getPlayerLocation().getX(), mazeGame.getPlayerLocation().getY());
        wrapper.cheeseLocation = new ApiLocationWrapper(mazeGame.getCheeseLocation().getX(), mazeGame.getCheeseLocation().getY());
        List<Cat> catList = mazeGame.getCats();
        for(Cat cats: catList){
            wrapper.catLocations.add(new ApiLocationWrapper(cats.getLocation().getX(), cats.getLocation().getY()));
        }
        for(int i =0; i < wrapper.boardHeight;i++){
            for(int j = 0; j < wrapper.boardWidth;j++){
                CellState eachCell = mazeGame.getCellState(new CellLocation(i,j));
                wrapper.isVisible[j][i] =  eachCell.isVisible();
                wrapper.hasWalls[j][i] = eachCell.isWall();
            }
        }
        return wrapper;

    }

    public ApiBoardWrapper() {
        mazeGame = new MazeGame();
        boardWidth = mazeGame.getMaze().getWidth();
        boardHeight = mazeGame.getMaze().getHeight();
        mouseLocation = new ApiLocationWrapper(mazeGame.getPlayerLocation().getX(), mazeGame.getPlayerLocation().getY());
        cheeseLocation = new ApiLocationWrapper(mazeGame.getCheeseLocation().getX(), mazeGame.getCheeseLocation().getY());
        catLocations = new ArrayList<>();
        List<Cat> catList = mazeGame.getCats();
        for(Cat cats: catList){
            catLocations.add(new ApiLocationWrapper(cats.getLocation().getX(), cats.getLocation().getY()));
        }
        hasWalls = new boolean[boardHeight][boardWidth];
        isVisible = new boolean[boardHeight][boardWidth];
        for(int i =0; i < boardHeight;i++){
            for(int j = 0; j < boardWidth;j++){
                CellState cell = mazeGame.getCellState(new CellLocation(i,j));
                isVisible[j][i] =  cell.isVisible();
                hasWalls[j][i] = cell.isWall();

            }
        }
    }
    public void updateBoard(){
        mouseLocation = new ApiLocationWrapper(mazeGame.getPlayerLocation().getX(), mazeGame.getPlayerLocation().getY());
        cheeseLocation = new ApiLocationWrapper(mazeGame.getCheeseLocation().getX(), mazeGame.getCheeseLocation().getY());
        catLocations = new ArrayList<>();
        List<Cat> catList = mazeGame.getCats();
        for(Cat cats: catList){
            catLocations.add(new ApiLocationWrapper(cats.getLocation().getX(), cats.getLocation().getY()));
        }
        hasWalls = new boolean[boardHeight][boardWidth];
        isVisible = new boolean[boardHeight][boardWidth];
        for(int i =0; i < boardHeight;i++){
            for(int j = 0; j < boardWidth;j++){
                CellState cell = mazeGame.getCellState(new CellLocation(i,j));
                isVisible[j][i] =  cell.isVisible();
                hasWalls[j][i] = cell.isWall();
            }
        }
    }

    public MazeGame getMazeGame() {
        return mazeGame;
    }

    public void showAll() {
        for(int i =0; i < boardHeight; i++) {
            for(int j = 0; j < boardHeight; j++) {
                mazeGame.getCellState(new CellLocation(i,j)).makeAllVisible();
            }
        }
    }
}
