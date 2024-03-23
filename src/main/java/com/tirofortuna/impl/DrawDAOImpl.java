package com.tirofortuna.impl;

import com.tirofortuna.entities.Draw;
import com.tirofortuna.persistence.IDrawDAO;
import com.tirofortuna.repository.DrawRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class DrawDAOImpl implements IDrawDAO {

    private final DrawRepository drawRepository;

    public DrawDAOImpl(DrawRepository drawRepository) {
        this.drawRepository = drawRepository;
    }

    @Override
    public List<Draw> findAll() {
        return (List<Draw>) drawRepository.findAll();
    }

    @Override
    public Optional<Draw> findById(Long id) {
        return drawRepository.findById(id);
    }

    @Override
    public void save(Draw draw) {
        drawRepository.save(draw);
    }

    @Override
    public void deleteById(Long id) {
        drawRepository.deleteById(id);
    }

    @Override
    public List<Draw> findDrawByDateInRange(Date minDate, Date maxDate) {
        return drawRepository.findDrawByDateBetween(minDate, maxDate);
    }
}
