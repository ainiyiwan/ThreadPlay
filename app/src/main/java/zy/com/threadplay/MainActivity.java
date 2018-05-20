package zy.com.threadplay;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final User user = new User();
    public static final String TAG = "MainActivity1";
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        synchronized (user){
//            user.notify();
//        }
//
//        Thread thread = new Thread();
//        thread.setName("myThread");
//        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//
//            }
//        });
//
//        Log.i(TAG, thread.getName());

        new Thread(new Runnable(){

            public void run(){
                while( !Thread.interrupted() ){
                    try{
                        String msg = QueuedThreads.getInstance().get();
                        if(msg != null){
                            ////
                            Log.i("tag", "收到消息啦===" + msg);
                        }
                    }catch(Exception e){

                    }
                }
            }

        }).start();

    }

    public void toast(View view) {
        Log.i("tag", "放数据啦");
        QueuedThreads.getInstance().push("Hello word");
    }
}
