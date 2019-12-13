/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.jyq.jrpc.factory;

/**
 *
 * @author
 * @version $Id: AbstractProxyFactory.java, v 0.1 2019年12月13日 15:29
 */
public interface AbstractProxyFactory {

    public <T> T getProxy(T invoker);
}