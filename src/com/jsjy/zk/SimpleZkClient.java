package com.jsjy.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SimpleZkClient {
    //链接信息
    private static final String connectString = "mini1:6666,mini2:6666,mini3:6666";
    //设置回话时间
    private static final int sessionTimeOut = 2000;

    ZooKeeper zkClient = null;

    /**
     * 初始化
     *
     * @throws Exception
     */
    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
                System.out.println("---");
                System.out.println("Type:" + event.getType() + "---Path:" + event.getPath());
                System.out.println("---");
                try {
                    List<String> list = zkClient.getChildren("/", true);
                    System.out.println("通知反馈的节点数："+list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(zkClient);
    }

    /**
     * 创建数据节点到zk中
     */
    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        // 参数1：要创建的节点的路径 参数2：节点大数据 参数3：节点的权限 参数4：节点的类型
        //zkClient.create("/eclipse", "hellozk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zkClient.create("/servers", "appzk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 判断znode是否存在
     *
     * @throws Exception
     */
    @Test
    public void testExit() throws Exception {
        Stat stat = zkClient.exists("/eclipse", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }

    /**
     * 获取子节点
     */
    @Test
    public void getChildren() throws Exception{
        List<String> children = zkClient.getChildren("/",true);
        System.out.println("============所有子节点begin===============");
        for(String child : children){
            System.out.println(child);
        }
        System.out.println("============所有子节点end===============");
        //Thread.sleep(Long.MAX_VALUE);

    }

    /**
     * 获取节点数据
     * @throws Exception
     */
    @Test
    public void getData() throws Exception{
        byte[] data = zkClient.getData("/eclipse",false,null);
        System.out.println("节点数据："+new String(data));
    }

    /**
     * 删除指定znode
     * @throws Exception
     */
    @Test
    public void deleteZnode() throws Exception{
        //参数2：指定要删除的版本，-1表示删除所有版本
        zkClient.delete("/eclipse",-1);
    }

    /**
     * 设置节点数据
     * @throws Exception
     */
    @Test
    public void setData() throws Exception{
        zkClient.setData("/app1","imissyou angelababy".getBytes(),-1);
        byte[] data = zkClient.getData("/app1",false,null);
        System.out.println(new String(data));
    }

}
