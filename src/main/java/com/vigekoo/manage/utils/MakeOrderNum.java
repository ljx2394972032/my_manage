package com.vigekoo.manage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : ljx
 * @ClassName: MakeOrderNum
 * @CreateTime 2016年9月13日 下午4:51:02
 * @Description: 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展
 */
public class MakeOrderNum {
    private MakeOrderNum(){}
    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "lockerOrder";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;
    /**
     * 每 毫秒 生成订单号数量最大值
     */
    private static int maxPerMSECSize = 1000;

    /**
     * 生成非重复订单号，目前1毫秒处理峰值1000个，1秒100万
     */
    public static String makeOrderNum() {
        synchronized (lockObj) {
            // 计数器到最大值归零，可扩展更大
            if (orderNumCount >= maxPerMSECSize) {
                orderNumCount = 0L;
            }
            //组装订单号
            String countStr = maxPerMSECSize + orderNumCount + "";
            orderNumCount++;
            // 取系统当前时间作为订单号变量前半部分，精确到毫秒  加上 订单号
            return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + countStr.substring(1);
        }
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 10000; i++) {
//            System.out.println(makeOrderNum());
//        }
//    }

//    public static void main(String[] args) {
//        // 测试多线程调用订单号生成工具
//        try {
//            for (int i = 0; i < 200; i++) {
//                Thread t1 = new Thread(new Runnable() {
//                    public void run() {
//                        MakeOrderNum makeOrder = new MakeOrderNum();
//                        makeOrder.makeOrderNum();
//                    }
//                }, "at" + i);
//                t1.start();
//                Thread t2 = new Thread(new Runnable() {
//                    public void run() {
//                        MakeOrderNum makeOrder = new MakeOrderNum();
//                        makeOrder.makeOrderNum();
//                    }
//                }, "bt" + i);
//                t2.start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
