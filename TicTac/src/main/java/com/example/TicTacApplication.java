package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.domain.Player;
import com.example.repository.PlayerRepository;

@SpringBootApplication
public class TicTacApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicTacApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner demo(PlayerRepository playerRepository) {
        return (args) -> {

            //save a couple of players
            playerRepository.save(new Player("ala", "ala@ala.com", new BCryptPasswordEncoder().encode("ala")));
            playerRepository.save(new Player("mary", "mary@mary.com",  new BCryptPasswordEncoder().encode("mary")));

        };
    }

}
