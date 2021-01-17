package com.abc.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.util.List;

public class ZkClientTest {

    // zk集群
    private static final String CLUSTER = "81.70.77.36:2181";
    // 要操作的节点路径
    private static final String PATH = "/zkclient";

    public static void main(String[] args) {
        // 创建客户端（会话）
        ZkClient zkClient = new ZkClient(CLUSTER);

        // 创建节点
        String nodeName = zkClient.create(PATH, "hello", CreateMode.PERSISTENT);
        System.out.println("创建了节点：" + nodeName);

        // 读取节点的数据
        Object data = zkClient.readData(PATH);
        System.out.println(data);

        // 创建子节点
        String childNode1 = zkClient.create(PATH + "/node1", "aaa", CreateMode.PERSISTENT_SEQUENTIAL);
        String childNode2 = zkClient.create(PATH + "/node2", "bbb", CreateMode.EPHEMERAL);

        // 获取子节点
        List<String> children = zkClient.getChildren(PATH);
        System.out.println(children);

        // 注册watcher，节点数据变化
        zkClient.subscribeDataChanges(PATH, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点" + dataPath + "的数据变更为：" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath + "的数据内容被删除了");
            }
        });

        // 更新数据
        if (zkClient.exists(PATH)) {
            zkClient.writeData(PATH, "world");
        }
    }

}
