/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.jyq.jrpc.reference.impl;


import com.jyq.jrpc.netty.NettyServer;
import com.jyq.jrpc.reference.Portocol;



/**
 *
 * @author
 * @version $Id: DubboPortocol.java, v 0.1 2019年12月13日 15:09
 */
public class DubboPortocol  implements Portocol {


    @Override
    public void export() {
        //创建代理类

        //创建节点
       // ZkUtils.createNode();
        //开启netty
        new NettyServer(2181);
    }

}