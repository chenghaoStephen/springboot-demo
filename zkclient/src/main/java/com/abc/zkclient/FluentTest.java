package com.abc.zkclient;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class FluentTest {

    public static void main(String[] args) throws Exception {
        // 创建会话
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("81.70.77.36:2181")
                .sessionTimeoutMs(15000)
                .connectionTimeoutMs(30000)
                .retryPolicy(retryPolicy)
                .namespace("logs")
                .build();
        client.start();

        String nodePath = "/host";

        // 创建节点
        String nodeName = client.create().forPath(nodePath, "myhost".getBytes());
        System.out.println("创建节点：" + nodeName);

        // 获取数据内容并注册watcher
        byte[] data = client.getData().usingWatcher((CuratorWatcher) event -> {
            System.out.println(event.getPath() + "数据内容变化");
        }).forPath(nodePath);
        System.out.println("节点数据为：" + new String(data));

        // 更新节点数据
        client.setData().forPath(nodePath, "newhost".getBytes());
        byte[] newData = client.getData().forPath(nodePath);
        System.out.println("节点数据更新为：" + new String(newData));

        // 删除节点
        client.delete().forPath(nodePath);
        if (client.checkExists().forPath(nodePath) == null) {
            System.out.println("节点已删除");
        }

    }

}
