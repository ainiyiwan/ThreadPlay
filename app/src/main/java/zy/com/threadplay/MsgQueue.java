package zy.com.threadplay;

import java.util.Vector;

public class MsgQueue {
	
	private static final int INBOUND_QUEUE_SIZE = 100;

	private Vector<String> messageQueue = new Vector<String>();

	private Object getLock = new Object();

	public boolean push(String msg){
		if(messageQueue.size() >= INBOUND_QUEUE_SIZE)
			return false;

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

		return null;
	}

}
