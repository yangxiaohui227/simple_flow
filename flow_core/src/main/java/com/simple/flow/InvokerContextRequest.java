package com.simple.flow;

public class InvokerContextRequest {
    /**
     * request param
     */
    private Object param;

    /**
     * response result
     *
     */
    private Object result;

    /**
     *  invoke method spend time million seconds
     *
     */
    private long costTime;

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }
}
