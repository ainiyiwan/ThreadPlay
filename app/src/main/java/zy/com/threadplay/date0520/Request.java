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

/**
 简化版本的请求类，包含请求的Url和一个Runnable 回调
 **/
class Request{
    public String requestUrl;
    public Runnable callback;
    public Request(String url, Runnable callback)
    {
        this.requestUrl = url;
        this.callback = callback;
    }

}
