package zy.com.threadplay.date0518;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/5/18
 * 描    述：这里需要注意的是，如果只是单纯的调用interrupt（）方法，线程并没有实际被中断，会继续往下执行。
 * 修订历史：https://blog.csdn.net/ns_code/article/details/17091267
 * ================================================
 */

public class SleepInterrupt extends Object implements Runnable{
    public void run(){
        try{
            System.out.println("in run() - about to sleep for 20 seconds");
            Thread.sleep(20000);
            System.out.println("in run() - woke up");
        }catch(InterruptedException e){
            System.out.println("in run() - interrupted while sleeping");
            //处理完中断异常后，返回到run（）方法人口，
            //如果没有return，线程不会实际被中断，它会继续打印下面的信息
            return;
        }
        System.out.println("in run() - leaving normally");
    }


    public static void main(String[] args) {
        SleepInterrupt si = new SleepInterrupt();
        Thread t = new Thread(si);
        t.start();
        //主线程休眠2秒，从而确保刚才启动的线程有机会执行一段时间
        try {
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("in main() - interrupting other thread");
        //中断线程t
        t.interrupt();
        System.out.println("in main() - leaving");
    }
}