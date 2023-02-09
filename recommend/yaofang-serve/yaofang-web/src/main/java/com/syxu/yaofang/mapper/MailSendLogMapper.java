package com.syxu.yaofang.mapper;

import com.syxu.yaofang.model.MailSendLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MailSendLogMapper {
    Integer updateMailSendLogStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    Integer insert(MailSendLog mailSendLog);

    List<MailSendLog> getMailSendLogsByStatus();

    Integer updateCount(@Param("msgId") String msgId, @Param("date") Date date);
}
