package zy.com.threadplay.date0520;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ThreadPool{
    //用一个Set或者其他数据结构把创建的线程保存起来，为的是方便以后获取线程的handle，做其他操作。
//    Set<WorkerThread> set = null;
//    private Queue<Runnable> queue;
//    //初始化线程池，创建内部类WorkerThread并且启动它
//    public ThreadPool(int size){
//        for( int i = 0 ;i < size ;i++ ){
//            WorkerThread t = new WorkerThread();
//            t.start();
//            set.add( t );
//        }
//        queue = new LinkedList<Runnable>();
//    }
//    //submit一个runnable进线程池
//    public void submit(Runnable runnable){
//        synchronized (queue){
//            queue.add(runnable);
//        }
//    }
//    //WorkerThread用一个死循环不停的去向Runnable队列拿Runnable执行。
//    public class  WorkerThread extends Thread{
//        @Override
//        public void run() {
//            super.run();
//            while(true){
//                synchronized (queue){
//                    Runnable current = queue.poll();
//                    current.run();
//                }
//            }
//        }
//    }
    Set<WorkerThread> set = null;
    private Queue<Runnable> queue;
    public ThreadPool(int size) {
        for (int i = 0; i < size; i++) {
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            set.add(workerThread);
        }
        queue = new LinkedList<>();
    }

    public void submit(Runnable runnable) {
        synchronized (queue) {
            queue.add(runnable);
        }
    }

    public class WorkerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                synchronized (queue) {
                    Runnable current = queue.poll();
                    current.run();
                }
            }
        }
    }
}