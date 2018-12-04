package com.stylefeng.guns;

/**
 * @author young
 * @description
 * @date 2018/12/5 1:02
 **/
public class TestDemo extends Thread {
    public void run(){
        System.out.println("im running");
    }

    public static void main(String[] args) {
        TestDemo testDemo = new TestDemo();
        testDemo.start();
        InterfaceThread interfaceThread = new InterfaceThread();
        Thread thread = new Thread(interfaceThread);
        thread.start();
    }
}

class InterfaceThread implements Runnable{

    @Override
    public void run() {
        System.out.println("im interface");
    }
}