package com.abc.aop;

import com.abc.aop.anno.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @SysLog("test")
    @GetMapping("/test")
    public String test(@RequestParam("name") String name){
        return name;
    }


}
