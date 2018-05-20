package zy.com.threadplay.date0518;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/5/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class InterruptReset extends Object {
    public static void main(String[] args) {
        System.out.println(
                "Point X: Thread.interrupted()=" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println(
                "Point Y: Thread.interrupted()=" + Thread.interrupted());
        System.out.println(
                "Point Z: Thread.interrupted()=" + Thread.interrupted());
    }
}
