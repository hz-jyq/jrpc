/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.jyq.jrpc.model.Response;

import java.io.Serializable;

/**
 *
 * @author
 * @version $Id: Response.java, v 0.1 2019年12月13日 16:55 Exp $
 */
public class Response implements Serializable {

    /**
     * id
     */
    String id;


    /**

     * 返回结果
     */
    Object result;

    private String mErrorMsg;

    long timeout;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getmErrorMsg() {
        return mErrorMsg;
    }

    public void setmErrorMsg(String mErrorMsg) {
        this.mErrorMsg = mErrorMsg;
    }

}