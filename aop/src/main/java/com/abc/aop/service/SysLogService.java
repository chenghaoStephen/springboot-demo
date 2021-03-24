package com.abc.aop.service;

import com.abc.aop.bo.SysLogBO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysLogService {

    public boolean save(SysLogBO sysLogBO) {
        log.info(sysLogBO.getParams());
        return true;
    }

}
