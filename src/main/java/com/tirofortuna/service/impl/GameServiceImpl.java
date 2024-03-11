package com.tirofortuna.service.impl;

import com.tirofortuna.entities.Game;
import com.tirofortuna.persistence.IGameDAO;
import com.tirofortuna.service.IGameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements IGameService {

    private final IGameDAO gameDAO;

    public GameServiceImpl(IGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @Override
    public List<Game> findAll() {
        return gameDAO.findAll();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameDAO.findById(id);
    }

    @Override
    public void save(Game game) {
        gameDAO.save(game);
    }

    @Override
    public void deleteById(Long id) {
        gameDAO.deleteById(id);
    }
}
