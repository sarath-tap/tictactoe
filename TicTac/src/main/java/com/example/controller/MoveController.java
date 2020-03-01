package com.example.controller;

import com.example.DTO.CreateMoveDTO;
import com.example.DTO.MoveDTO;
import com.example.domain.Game;
import com.example.domain.Move;
import com.example.service.GameService;
import com.example.service.MoveService;
import com.example.service.PlayerService;
import com.example.domain.Position;
import com.example.repository.GameRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created by pdybka on 24.06.16.
 */
@RestController
@RequestMapping("/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    GameRepository gameRepository;

    Logger logger = LoggerFactory.getLogger(MoveController.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Move createMove(@RequestBody CreateMoveDTO createMoveDTO) {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        logger.info("move to insert:" + createMoveDTO.getBoardColumn() + createMoveDTO.getBoardRow());

        Optional<Game> optional = gameRepository.findById(gameId);
        
    	Game g = optional.get();

        Move move = moveService.createMove(g, playerService.getLoggedUser(), createMoveDTO);
        Game game = g;
        gameService.updateGameStatus(g, moveService.checkCurrentGameStatus(game));

        return move;
    }

    @RequestMapping(value = "/autocreate", method = RequestMethod.GET)
    public Move autoCreateMove() {
        Long gameId = (Long) httpSession.getAttribute("gameId");

        logger.info("AUTO move to insert:" );
        
        
        Optional<Game> optional = gameRepository.findById(gameId);
        
    	Game g = optional.get();

        Move move = moveService.autoCreateMove(g);

        Game game = g;
        gameService.updateGameStatus(g, moveService.checkCurrentGameStatus(game));

        return move;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<MoveDTO> getMovesInGame() {

        Long gameId = (Long) httpSession.getAttribute("gameId");

        Optional<Game> optional = gameRepository.findById(gameId);
        
    	Game g = optional.get();

      return moveService.getMovesInGame(g);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public List<Position> validateMoves() {
        Long gameId = (Long) httpSession.getAttribute("gameId");

        Optional<Game> optional = gameRepository.findById(gameId);
        
    	Game g = optional.get();
        return moveService.getPlayerMovePositionsInGame(g, playerService.getLoggedUser());
    }

    @RequestMapping(value = "/turn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPlayerTurn() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        
        
        
        Optional<Game> optional = gameRepository.findById(gameId);
        
    	Game g = optional.get();
        return moveService.isPlayerTurn(g, g.getFirstPlayer(),
                g.getSecondPlayer());
    }



}