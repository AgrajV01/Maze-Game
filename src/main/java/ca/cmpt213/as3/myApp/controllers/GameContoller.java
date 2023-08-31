package ca.cmpt213.as3.myApp.controllers;

import ca.cmpt213.as3.myApp.model.MoveDirection;
import ca.cmpt213.as3.myApp.restapi.ApiBoardWrapper;
import ca.cmpt213.as3.myApp.restapi.ApiGameWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This is Game Controller class
 * Author: Agraj Vuppula
 */
@RestController
public class GameContoller {
    private final List<ApiBoardWrapper> boardList = new ArrayList<>();
    private final List<ApiGameWrapper> gamesList = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong();

    @GetMapping("/api/about")
    public String getUserDescription() {
        return "Agraj Vuppula";
    }

    @GetMapping("/api/games")
    //Return a list of ApiGameWrapper objects for the games that the system knows about.
    // Note that once a game is created, the server does not need to ever delete that game:
    // it can exist until the server is restarted.
    //Each ApiGameWrapper stores an ID to uniquely identify the game. This ID is assigned by the backend.
    //TIP: Make the ID the game’s index into the list which you use to store the games.
    public List<ApiGameWrapper> returnGamesList() {
        return gamesList;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED) // this will return HTTP status code 201 (Created)
    //Create a new game by POSTing to this end point. You need not post any data (no body).
    // Returns one fully populated ApiGameWrapper object instance.
    //Returns HTTP status code 201 (Created) when successful.
    public ApiGameWrapper createGame() {
        ApiBoardWrapper game = ApiBoardWrapper.makeFromGame();
        boardList.add(game);
        // Set new quote's ID
        ApiGameWrapper newGame = new ApiGameWrapper(game);
        newGame.setGameNumber(gamesList.size());
        //Store the game
        gamesList.add(newGame);

        // Return the Game so user gets the ID
        return newGame;
    }

    @GetMapping("/api/games/{id}")
    //Return the ApiGameWrapper object for game 1 (where 1 is the game ID; 1 need not be the first ID assigned).
    //Error Handling:
    //Return 404 (File Not Found) if the requested game does not exist.
    public ApiGameWrapper returnTheGameByID(@PathVariable("id") int id) throws FileNotFoundException {
        for (ApiGameWrapper game : gamesList) {
            if (game.getGameNumber() == id) {
                return game;
            }
        }
        throw new FileNotFoundException("There is no game with that ID");
    }

    @GetMapping("/api/games/{id}/board")
    //Return the current state of the board as one ApiBoardWrapper object (in JSON) for game 1
    // (change to the ID you need).
    // Support returning errors to client
    public ApiBoardWrapper returnTheBoard(@PathVariable("id") int id) throws FileNotFoundException {
        for (ApiGameWrapper game : gamesList) {
            if (game.getGameNumber() == id) {
                return boardList.get(id);
            }
        }
        throw new FileNotFoundException("There is no game with that ID");
    }

    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    // Make a move in game 1 (change to the ID you need).
    public void moveInGame(@PathVariable("id") int id, @RequestBody String move) throws FileNotFoundException {
        if(move.equals("MOVE_UP") && boardList.get(id).getMazeGame().isValidPlayerMove(MoveDirection.valueOf(move))) {
            boardList.get(id).getMazeGame().recordPlayerMove(MoveDirection.valueOf(move));
            boardList.get(id).updateBoard();
            gamesList.get(id).updateGame(boardList.get(id));
        }
        else if(move.equals("MOVE_DOWN") && boardList.get(id).getMazeGame().isValidPlayerMove(MoveDirection.valueOf(move))) {
            boardList.get(id).getMazeGame().recordPlayerMove(MoveDirection.valueOf(move));
            boardList.get(id).updateBoard();
            gamesList.get(id).updateGame(boardList.get(id));
        }
        else if(move.equals("MOVE_LEFT") && boardList.get(id).getMazeGame().isValidPlayerMove(MoveDirection.valueOf(move))) {
            boardList.get(id).getMazeGame().recordPlayerMove(MoveDirection.valueOf(move));
            boardList.get(id).updateBoard();
            gamesList.get(id).updateGame(boardList.get(id));
        }
        else if(move.equals("MOVE_RIGHT") && boardList.get(id).getMazeGame().isValidPlayerMove(MoveDirection.valueOf(move))) {
            boardList.get(id).getMazeGame().recordPlayerMove(MoveDirection.valueOf(move));
            boardList.get(id).updateBoard();
            gamesList.get(id).updateGame(boardList.get(id));
        }
        else if(move.equals("MOVE_CATS") ) {
            boardList.get(id).getMazeGame().doCatMoves();
            boardList.get(id).updateBoard();
            gamesList.get(id).updateGame(boardList.get(id));
        }
        else if(gamesList.get(id).getGameNumber() != id)
            throw new FileNotFoundException("There is no game with that ID");
        else
            throw new BadRequest("Please enter valid input");
    }

    @PostMapping("/api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cheatState(@PathVariable("id") int id, @RequestBody String message) throws FileNotFoundException {
        if(message.equals("1_CHEESE")){
            boardList.get(id).getMazeGame().setNumberCheeseToCollect(1);
        }
        else if(message.equals("SHOW_ALL")){
            boardList.get(id).showAll();
        }
        else if(gamesList.get(id).getGameNumber() != id)
            throw new FileNotFoundException("There is no game with that ID");
        else
            throw new BadRequest("Please enter valid input");
    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public  static class BadRequest extends RuntimeException {
        public BadRequest() {}
        public BadRequest(String str) {
            super(str);
        }
    }
}
