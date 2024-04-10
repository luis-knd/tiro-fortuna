package com.tirofortuna.service.impl;

import com.tirofortuna.entities.Draw;
import com.tirofortuna.persistence.IDrawDAO;
import com.tirofortuna.service.IDrawService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DrawServiceImpl implements IDrawService {

    private final IDrawDAO iDrawDAO;

    public DrawServiceImpl(IDrawDAO iDrawDAO) {
        this.iDrawDAO = iDrawDAO;
    }

    @Override
    public List<Draw> findAll() {
        return iDrawDAO.findAll();
    }

    @Override
    public Optional<Draw> findById(Long id) {
        return iDrawDAO.findById(id);
    }

    @Override
    public void save(Draw draw) {
        iDrawDAO.save(draw);
    }

    @Override
    public void deleteById(Long id) {
        iDrawDAO.deleteById(id);
    }

    @Override
    public List<Draw> findDrawByDateInRange(Date minDate, Date maxDate) {
        return iDrawDAO.findDrawByDateInRange(minDate, maxDate);
    }

    @Override
    public Map<Integer, Integer> findAbsoluteFrequencyByGame(Long gameId) {
        return iDrawDAO.findAbsoluteFrequencyByGame(gameId);
    }

    @Override
    public Map<Integer, Double> findRelativeFrequencyByGame(Long gameId) {
        return iDrawDAO.findRelativeFrequencyByGame(gameId);
    }
}
