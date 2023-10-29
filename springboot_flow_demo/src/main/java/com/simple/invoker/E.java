package com.simple.invoker;

import com.simple.flow.Invoker;
import com.simple.flow.InvokerContextRequest;
import org.springframework.stereotype.Service;

@Service
public class E extends Invoker {
    @Override
   public void invoker(InvokerContextRequest t) {
        System.out.println("this is E and current threadId is"+Thread.currentThread().getId());
    }
}
