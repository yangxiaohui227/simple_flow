package com.simple.flow;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InvokerHolder
{


    private List<Invoker> invokerList;

    private InvokerHolder next;

    public List<Invoker> getInvokerList() {
        return invokerList;
    }

    public void setInvokerList(List<Invoker> invokerList) {
        this.invokerList = invokerList;
    }

    public InvokerHolder getNext() {
        return next;
    }

    public void setNext(InvokerHolder next) {
        this.next = next;
    }

    public void doInvoker(ThreadPoolTaskExecutor executor, InvokerContextRequest invokerContextRequest){

        //多个时执行并发
        if(invokerList.size()>1 && null!=executor){
            List<CompletableFuture> list=new ArrayList<>();
            for (Invoker invoker : invokerList) {
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> invoker.invoker(invokerContextRequest), executor)
                        .exceptionally(ex->{
                            throw new RuntimeException(ex);
                        });
                list.add(completableFuture);
            }
            CompletableFuture.allOf(list.toArray(new CompletableFuture[]{})).join();


        }else {
            Invoker invoker = invokerList.get(0);
            invoker.invoker(invokerContextRequest);


        }
        if(next!=null){
            next.doInvoker(executor,invokerContextRequest);
        }
    }



}
