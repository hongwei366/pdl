package com.jsjy.zkdist;

import org.apache.zookeeper.*;

import java.util.ArrayList;
import java.util.List;

public class DistributedServer {
    //链接信息
    private static final String connectString = "mini1:6666,mini2:6666,mini3:6666";
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
                System.out.println(event.getType() + "---" + event.getPath());
                try {
                    //更新服务器列表，并注册监听
                    zk.getChildren("/",true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 向zk集群注册服务信息
     * @param hostname
     * @throws Exception
     */
    public void registerServer(String hostname) throws Exception{
        String create = zk.create(parentNode + "/server",hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + "is online..." + create);
    }

    /**
     * 业务功能
     * @param hostname
     * @throws Exception
     */
    public void handleBussiness(String hostname) throws Exception{
        System.out.println(hostname + "Start work...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(args);
        DistributedServer server =  new DistributedServer();
        server.getConnect();
        //利用zk连接注册服务信息
        server.registerServer(args[0]);
        //启动业务功能
        server.handleBussiness(args[0]);
    }




}
