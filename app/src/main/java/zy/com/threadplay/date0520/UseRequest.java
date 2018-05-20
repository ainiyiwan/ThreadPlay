package zy.com.threadplay.date0520;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class UseRequest {
    //消息队列
    Queue<Request> requestQueue = new LinkedList<Request>();

    public void method(){
        new Thread( new Runnable(){
            public void run(){
                //启动一个新的线程，用一个True的while循环不停的从队列里面获取第一个request并且处理
                while(true){
                    if( !requestQueue.isEmpty() ){
                        Request request = requestQueue.poll();
//                        String response = // 处理request 的 url，这一步将是耗时的操作，省略细节
//                                new Handler( Looper.getMainLooper() ).post( request.callback );
                    }
                }
            }
        }).start();
    }

    //一个消息的类结构，除了runnable，还有一个该Message需要被执行的时间execTime，两个引用，指向该Message在链表中的前任节点和后继节点。
    public class Message{
        public long execTime = -1;
        public Runnable task;
        public Message prev;
        public Message next;
        public Message(Runnable runnable, long milliSec){
            this.task = runnable;
            this.execTime = milliSec;
        }
    }
    public class MessageQueue{
        //维持两个dummy的头和尾作为我们消息链表的头和尾，这样做的好处是当我们插入新Message时，不需要考虑头尾为Null的情况，这样代码写起来更加简洁，也是一个小技巧。
        //头的执行时间设置为-1，尾是Long的最大值，这样可以保证其他正常的Message肯定会落在这两个点之间。
        private Message head = new Message(null,-1);
        private Message tail = new Message(null,Long.MAX_VALUE);
        public void run(){
            new Thread( new Runnable(){
                public void run(){
                    //用死循环来不停处理消息
                    while(true){
                        //这里是关键，当头不是dummy头，并且当前时间是大于或者等于头节点的执行时间的时候，我们可以执行头节点的任务task。
                        if( head.next != tail && System.currentTimeMillis()>= head.next.execTime ){
                            //执行的过程需要把头结点拿出来并且从链表结构中删除
                            Message current = head.next;
                            Message next = current.next;
                            current.task.run();
                            current.next = null;
                            current.prev =null;
                            head.next = next;
                            next.prev = head;
                        }
                    }
                }
            }).start();
        }
        public void post(Runnable task){
            //如果是纯post，那么把消息放在最尾部
            Message message = new Message( task,  System.currentTimeMillis() );
            Message prev = tail.prev;
            prev.next = message;
            message.prev = prev;
            message.next = tail;
            tail.prev = message;
        }
        public void postDelay(Runnable task, long milliSec){
            //如果是延迟消息，生成的Message的执行时间是当前时间+延迟的秒数。
            Message message = new Message( task,  System.currentTimeMillis()+milliSec);
            //这里使用一个while循环去找第一个执行时间在新创建的Message之前的Message，新创建的Message就要插在它后面。
            Message target = tail;
            while(target.execTime>= message.execTime){
                target = target.prev;
            }
            Message next = target.next;
            message.prev = target;
            target.next = message;
            message.next = next;
            next.prev = message;
        }
    }
}
