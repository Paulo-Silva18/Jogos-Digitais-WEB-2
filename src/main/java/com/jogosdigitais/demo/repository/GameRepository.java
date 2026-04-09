package com.jogosdigitais.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jogosdigitais.demo.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
