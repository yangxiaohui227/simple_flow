package com.simple.invoker;

import com.simple.flow.Invoker;
import com.simple.flow.InvokerContextRequest;
import org.springframework.stereotype.Service;

@Service
public class A extends Invoker {
    @Override
    public void invoker(InvokerContextRequest A) {
        System.out.println("this is A and current threadId is"+Thread.currentThread().getId());
    }
}


