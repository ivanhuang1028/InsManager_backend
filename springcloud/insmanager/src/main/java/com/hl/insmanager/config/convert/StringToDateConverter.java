package com.hl.insmanager.config.convert;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import java.util.Date;

/**
 * 时间戳转日期
 *
 * @author Administrator
 */
@Slf4j
public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        DateTime dateTime;
        try {
            dateTime = new DateTime(Long.valueOf(s));
        } catch (Exception e) {
            log.error("【日期转化失败】时间戳：" + s, e);
            return null;
        }
        return dateTime.toDate();
    }

}
