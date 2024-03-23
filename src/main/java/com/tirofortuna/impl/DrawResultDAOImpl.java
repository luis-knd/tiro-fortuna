package com.tirofortuna.impl;

import com.tirofortuna.entities.DrawResult;
import com.tirofortuna.persistence.IDrawResultDAO;
import com.tirofortuna.repository.DrawResultRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DrawResultDAOImpl implements IDrawResultDAO {

    private final DrawResultRepository drawResultRepository;

    public DrawResultDAOImpl(DrawResultRepository drawResultRepository) {
        this.drawResultRepository = drawResultRepository;
    }

    @Override
    public List<DrawResult> findAll() {
        return (List<DrawResult>) drawResultRepository.findAll();
    }

    @Override
    public Optional<DrawResult> findById(Long id) {
        return drawResultRepository.findById(id);
    }

    @Override
    public void save(DrawResult drawResult) {
        drawResultRepository.save(drawResult);
    }

    @Override
    public void deleteById(Long id) {
        drawResultRepository.deleteById(id);
    }
}
