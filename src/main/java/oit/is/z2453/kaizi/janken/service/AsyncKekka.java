package oit.is.z2453.kaizi.janken.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2453.kaizi.janken.model.Match;
import oit.is.z2453.kaizi.janken.model.MatchInfo;
import oit.is.z2453.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2453.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  public void syncMatch() {
    if (matchMapper.selectAllMatchByisActive(true) == null) {
      this.dbUpdated = false;
    }
  }

  @Async
  public void asyncShowMatch(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        this.syncMatch();

        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }

        Match match = matchMapper.selectAllMatchByisActive(true);
        MatchInfo matchInfo = matchInfoMapper.selectByisActiveId(match.getUser2());

        emitter.send(match);
        TimeUnit.MILLISECONDS.sleep(1000);

        match.setActive(false);
        matchInfo.setActive(false);

        matchMapper.updateById(match);
        matchInfoMapper.updateById(matchInfo);

        dbUpdated = false;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowMatch complete");
  }

}
