package com.hl.insmanager.vo.time;

import lombok.Data;
import java.util.Date;

/**
 * @author ivan.huang
 * 日期段查询对象
 */
@Data
public class DateRangeQuery {
    private Date start_date;
    private Date end_date;
}
