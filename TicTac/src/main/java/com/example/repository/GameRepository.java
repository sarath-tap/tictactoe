package com.example.repository;

import com.example.domain.Game;
import com.example.enums.GameStatus;
import com.example.enums.GameType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by pdybka on 30.05.16.
 */

public interface GameRepository extends CrudRepository<Game, Long>{
    
	List<Game> findByGameTypeAndGameStatus(GameType GameType, GameStatus GameStatus);
    List<Game> findByGameStatus(GameStatus gameStatus);
    
//    @Query(value = "SELECT * FROM game req WHERE req.game = :id", nativeQuery = true)
//    public Game GetGameById(@Param("id") String id);
	
    //Game findOne(Long id);
}