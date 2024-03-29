package com.tirofortuna.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tirofortuna.entities.Draw;
import com.tirofortuna.persistence.IDrawDAO;
import com.tirofortuna.repository.DrawRepository;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;

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

    @Override
    public Map<Integer, Integer> findOccurrencesByResultAndGame(Long gameId) {
        try {
            String[] drawResults = drawRepository.findAllDrawByGameId(gameId);
            if (drawResults == null || drawResults.length == 0) {
                return Collections.emptyMap();
            }

            Map<Integer, Integer> occurrenceMap = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();

            for (String drawResult : drawResults) {
                processDrawResult(drawResult, occurrenceMap, objectMapper);
            }
            return occurrenceMap;
        } catch (Exception e) {
            throw new RuntimeException("Error finding occurrences by result and game", e);
        }
    }

    private void processDrawResult(String drawResult, Map<Integer, Integer> occurrenceMap, ObjectMapper objectMapper) {
        try {
            JsonNode rootNode = objectMapper.readTree(drawResult);
            rootNode.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode resultNumber = entry.getValue();

                if (isNumberKey(key) && resultNumber.isInt()) {
                    int number = resultNumber.asInt();
                    occurrenceMap.put(number, occurrenceMap.getOrDefault(number, 0) + 1);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Error counting occurrences by result and game", e);
        }
    }

    private boolean isNumberKey(String key) {
        return key.startsWith("N") && isNumeric(key.substring(1));
    }
}
