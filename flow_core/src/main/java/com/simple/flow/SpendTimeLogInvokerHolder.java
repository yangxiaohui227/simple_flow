package com.simple.flow;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class SpendTimeLogInvokerHolder extends InvokerHolder{

    @Override
    public void doInvoker(ThreadPoolTaskExecutor executor, InvokerContextRequest invokerContextRequest) {
        long start = System.currentTimeMillis();
        if(super.getNext()!=null){
            super.getNext().doInvoker(executor,invokerContextRequest);
        }
        long end = System.currentTimeMillis();
        invokerContextRequest.setCostTime(end-start);
    }
}
