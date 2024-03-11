package com.tirofortuna.impl;

import com.tirofortuna.entities.Game;
import com.tirofortuna.persistence.IGameDAO;
import com.tirofortuna.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameDAOImpl implements IGameDAO {

    private final GameRepository gameRepository;

    public GameDAOImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> findAll() {
        return (List<Game>) gameRepository.findAll();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }
}
