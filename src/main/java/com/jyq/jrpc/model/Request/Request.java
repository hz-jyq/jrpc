/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.jyq.jrpc.model.Request;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author
 * @version $Id: Request.java, v 0.1 2019年12月31日 16:14
 */
public class Request implements Serializable {

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    private final long mId = INVOKE_ID.getAndIncrement();

    private String mVersion;

    private Object mData;


    public long getmId() {
        return mId;
    }

    public String getmVersion() {
        return mVersion;
    }

    public void setmVersion(String mVersion) {
        this.mVersion = mVersion;
    }

    public Object getmData() {
        return mData;
    }

    public void setmData(Object mData) {
        this.mData = mData;
    }



}