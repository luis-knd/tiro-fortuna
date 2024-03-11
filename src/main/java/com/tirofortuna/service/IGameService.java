package com.tirofortuna.service;

import com.tirofortuna.entities.Game;

import java.util.List;
import java.util.Optional;

public interface IGameService {

    List<Game> findAll();

    Optional<Game> findById(Long id);

    void save(Game game);

    void deleteById(Long id);
}
