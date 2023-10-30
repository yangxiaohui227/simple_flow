package com.simple.flow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlowInvokerChainUtil
{
    private  static  final Map<String, InvokerHolder> invokerChainMap=new ConcurrentHashMap<>(16);

    public static void addInvokerHolder(String chainName, InvokerHolder invokerHolder){
        invokerChainMap.put(chainName,invokerHolder);
    }


    public static InvokerHolder getInvokerHolder(String chainName){
        return invokerChainMap.get(chainName);
    }



}
