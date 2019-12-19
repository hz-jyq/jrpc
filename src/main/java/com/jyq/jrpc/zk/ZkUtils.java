package com.jyq.jrpc.zk;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * Created by kai.yang on 2018/9/13.
 */
public class ZkUtils {

    private static CuratorFramework client = null;

    private static final String NAME_SPACE = "zook";



    static {
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            client = CuratorFrameworkFactory.builder().namespace(NAME_SPACE).retryPolicy(retryPolicy)
                    .sessionTimeoutMs(5000).connectionTimeoutMs(3000).connectString("127.0.0.1:2181").build();
            client.start();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static void createNode(String nodePath) throws Exception {

        Stat stat = client.checkExists().forPath(getNodePath(nodePath));
        if (stat == null) {
            client.create().forPath(getNodePath(nodePath), null);
        } else {
            System.out.println("节点已存在");
        }
    }

    public static void revomeNode(String nodePath) throws Exception {
        client.delete().forPath(getNodePath(nodePath));
    }

    public static void revomeAllChildNode(String nodePath) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(getNodePath(nodePath));
    }

    public static void updateeNode(String nodePath, String data) throws Exception {
        client.setData().forPath(getNodePath(nodePath), data.getBytes());
    }

    public static List<String> getNodes(String nodePath) throws Exception {

        return client.getChildren().forPath(getNodePath(nodePath));
    }

    public static String getNodePath(String nodePath) {
        return nodePath.startsWith("/") ? nodePath : "/" + nodePath;
    }

}
