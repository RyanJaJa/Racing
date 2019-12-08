package com.exce.service;


import com.exce.model.Game;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface GameService {
    Optional<Game> save(Game game);

    void delete(BigInteger id);

    void delete(Game game);

    Optional<Game> findOne(BigInteger id);

    List<Game> findAll();
}
