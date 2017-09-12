package com.jsjy.zkdist;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class DistributedClient {
    //链接信息
    private static final java.lang.String connectString = "mini1:6666,mini2:6666,mini3:6666";
    //设置回话时间
    private static final int sessionTimeOut = 2000;
    private static final String parentNode = "/servers";

    private  volatile List<String> serverList;
    private ZooKeeper zk = null;

    public void getConnect() throws Exception{
        zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                //收到事件通知后的回调函数
                try {
                    //更新服务器列表，并注册监听
                    getServerList();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 获取服务器信息列表
     * @throws Exception
     */
    private void getServerList() throws Exception{
        // 获取服务器子节点信息，并且对父节点进行监听
        List<String> children = zk.getChildren(parentNode,true);
        //创建一个局部的list来存储服务器信息
        List<String> servers = new ArrayList<String>();
        for (String child : children) {
            byte[] data = zk.getData(parentNode+"/"+child,false,null);
            servers.add(new String(data));
        }
        serverList = servers;
        //打印服务器列表
        System.out.println(serverList);
    }

    public void handleBussiness() throws Exception{
        System.out.println("Client start working...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {

        // 获取zk连接
        DistributedClient client = new DistributedClient();
        client.getConnect();
        // 获取servers的子节点信息（并监听），从中获取服务器信息列表
        client.getServerList();

        // 业务线程启动
        client.handleBussiness();

    }

}
