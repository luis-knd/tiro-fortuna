package com.tirofortuna.service.impl;

import com.tirofortuna.entities.DrawResult;
import com.tirofortuna.persistence.IDrawResultDAO;
import com.tirofortuna.service.IDrawResultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DrawResultServiceImpl implements IDrawResultService {

    private final IDrawResultDAO iDrawResultDAO;

    public DrawResultServiceImpl(IDrawResultDAO iDrawResultDAO) {
        this.iDrawResultDAO = iDrawResultDAO;
    }

    @Override
    public List<DrawResult> findAll() {
        return iDrawResultDAO.findAll();
    }

    @Override
    public Optional<DrawResult> findById(Long id) {
        return iDrawResultDAO.findById(id);
    }

    @Override
    public void save(DrawResult drawResult) {
        iDrawResultDAO.save(drawResult);
    }

    @Override
    public void deleteById(Long id) {
        iDrawResultDAO.deleteById(id);
    }
}
