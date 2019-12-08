package com.exce.restful;

import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.model.Game;
import com.exce.dto.ResponsePayload;
import com.exce.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping(value = "rest/game")
public class RestGameController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GameService gameService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Game> get(@PathVariable("id") BigInteger id) {
        try {
            Optional<Game> gameOptional = gameService.findOne(id);
            if (gameOptional.isPresent()) {
                return new ResponsePayload<>(gameOptional.get(), HttpStatus.OK);
            } else {
                return new ResponsePayload<>(HttpStatus.NOT_FOUND);
            }
        } catch (GoldLuckException e) {
            logger.error("get error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("get error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Game> create(@PathVariable("id") BigInteger id, @RequestBody Game request) {
        try {
            // TODO 尚未確認內容
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } catch (GoldLuckException e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Game> update(@PathVariable("id") BigInteger id, @RequestBody Game request) {
        try {
            Optional<Game> gameOptional = gameService.findOne(id);
            if (gameOptional.isPresent()) {
                Game game = gameOptional.get();
                game.setActive(request.getActive());
                game.setName(request.getName());
                gameService.save(game);
                return new ResponsePayload<>(game, HttpStatus.OK);
            } else {
                return new ResponsePayload<>(HttpStatus.NOT_FOUND);
            }
        } catch (GoldLuckException e) {
            logger.error("update error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("update error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Game> delete(@PathVariable("id") BigInteger id) {
        try {
            gameService.delete(id);
            return new ResponsePayload<>(HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("delete error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("delete error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }
}
