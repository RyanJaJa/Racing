package com.exce.service.impl;

import com.exce.model.Game;
import com.exce.repository.*;
import com.exce.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Scope("prototype")
@Transactional
public class GameServiceImpl implements GameService {
    private static Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    GameRepository gameRepository;

    @Override
    public Optional<Game> save(Game game) { return Optional.ofNullable(gameRepository.save(game)); }

    @Override
    public void delete(BigInteger id) {
        gameRepository.delete(id);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public Optional<Game> findOne(BigInteger id) {
        return Optional.ofNullable(gameRepository.findOne(id));
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

}
