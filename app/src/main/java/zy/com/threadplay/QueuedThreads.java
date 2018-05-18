package zy.com.threadplay;

import java.util.Vector;
import java.lang.Runnable;

public class QueuedThreads {
	
	private static final int INBOUND_QUEUE_SIZE = 100;

	private Vector<String> messageQueue = new Vector<String>();

	private Object getLock = new Object();
	private Object putLock = new Object();

	private QueuedThreads(){
		
		new Thread(new Runnable(){

			public void run(){
				while( !Thread.interrupted() ){
					try{
						String msg = QueuedThreads.this.get();
						if(msg != null){
							////
							System.out.println(msg);
						}
					}catch(Exception e){

					}
				}
			}

		}
		).start();

	}
	
	private static QueuedThreads instance = new QueuedThreads();

	public static QueuedThreads getInstance(){
		return instance;
	}

	public boolean push(String msg){
		if(messageQueue.size() >= INBOUND_QUEUE_SIZE){
			try {
				synchronized (putLock) {
					putLock.wait();
				}
			}catch(InterruptedException e){

			}
		}

		messageQueue.addElement(msg);

		synchronized (getLock) {
			getLock.notifyAll();
		}

		return true;
	}

	public String get(){
		
		synchronized ( messageQueue ) {
		    if ( !messageQueue.isEmpty() ) {
			    String msg = messageQueue.elementAt(0);

				messageQueue.removeElementAt(0);

				return msg;
		    }
		}


		try {
			synchronized (getLock) {
				if ( messageQueue.isEmpty() ) {
					getLock.wait(1000*20);
				}
			}
		} catch (InterruptedException e) {
		}


		synchronized ( messageQueue ) {
		    if ( !messageQueue.isEmpty() ) {
			    String msg = messageQueue.elementAt(0);

				messageQueue.removeElementAt(0);

				return msg;
		    }
		}

		synchronized (putLock) {
			if(this.messageQueue.size() < INBOUND_QUEUE_SIZE)
				putLock.notifyAll();
		}

		return null;
	}
	
	public static void main(String[] args){
		
		
		new Thread(new Runnable(){
			
			public void run(){
				
				int i = 0;
				
				while(!Thread.interrupted()){
					
					QueuedThreads.getInstance().push("Hello word "+(i++));
					
					try{Thread.sleep(10);}catch(Exception e){}
				}
			}
			
		}).start();
		
		
		new Thread(new Runnable(){
			
			public void run(){
				
				int i = 0;
				
				while(!Thread.interrupted()){
					
					QueuedThreads.getInstance().push("Hello word(1) "+(i++));
					
					try{Thread.sleep(10);}catch(Exception e){}
				}
			}
			
		}).start();
		
	}

}
