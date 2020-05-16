package com.hl.insmanager.scheduler;

import com.hl.common.constants.Constants;
import com.hl.common.util.Formatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ivan.huang
 */
@Slf4j
@Component
public class TestSchedulerController {

//    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info( "=====> " + Formatter.parseDate2Str(new Date(), Constants.DATE_PATTERN1));
    }
//
//    @Scheduled(fixedRate = 5000)
//    public void scheduled1(){
//        log.info( "=====> " + Formatter.parseDate2Str(new Date(), Constants.DATE_PATTERN1));
//    }
//
//    @Scheduled(fixedDelay = 5000)
//    public void scheduled2(){
//        log.info( "=====> " + Formatter.parseDate2Str(new Date(), Constants.DATE_PATTERN1));
//    }

}
