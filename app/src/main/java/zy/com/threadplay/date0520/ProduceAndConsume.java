package zy.com.threadplay.date0520;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ProduceAndConsume {
    /**
     * 生产者生产出来的产品交给店员
     */
//    public synchronized void produce()
//    {
//        if(this.product >= MAX_PRODUCT)
//        {
//            try
//            {
//                wait();
//                System.out.println("产品已满,请稍候再生产");
//            }
//            catch(InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//            return;
//        }
//
//        this.product++;
//        System.out.println("生产者生产第" + this.product + "个产品.");
//        notifyAll();   //通知等待区的消费者可以取出产品了
//    }
//
//    /**
//     * 消费者从店员取产品
//     */
//    public synchronized void consume()
//    {
//        if(this.product <= MIN_PRODUCT)
//        {
//            try
//            {
//                wait();
//                System.out.println("缺货,稍候再取");
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//            return;
//        }
//
//        System.out.println("消费者取走了第" + this.product + "个产品.");
//        this.product--;
//        notifyAll();   //通知等待去的生产者可以生产产品了
//    }
}
