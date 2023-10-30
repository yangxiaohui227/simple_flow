package com.simple.controller;

import com.simple.flow.FlowInvokerChainUtil;
import com.simple.flow.InvokerContextRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ThreadPoolTaskExecutor shopTaskExecutor;

    @RequestMapping("/test")
    public String doInvoker(){
        InvokerContextRequest invokerContextRequest = new InvokerContextRequest();
        invokerContextRequest.setParam("this is test ....");
        FlowInvokerChainUtil.getInvokerHolder("chain1").doInvoker(shopTaskExecutor,invokerContextRequest);
        System.out.println("total cost:"+invokerContextRequest.getCostTime());
        return "ok";
    }


}
