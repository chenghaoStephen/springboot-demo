package com.abc.aop.bo;

import lombok.Data;

/**
 * 系统日志
 */
@Data
public class SysLogBO {

    private String className;

    private String methodName;

    private String params;

    private Long exeuTime;

    private String remark;

    private String createDate;

}
