package fr.lifl.iuta.compilator.processor;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class BufferOut {
	
	private LinkedBlockingQueue<Character> buffer;
	private Semaphore sem;

	private static BufferOut instance = null;
	
	public static BufferOut getInstance() {
		if(instance == null)
			init();
		return instance;
	}
	
	public static void init() {
		instance = new BufferOut();
		instance.buffer = new LinkedBlockingQueue<Character>();
		instance.sem = new Semaphore(1, true);
	}
	
	public synchronized static void add(char value) {
		try {
			getInstance().sem.acquire();
			getInstance().buffer.offer(value);
			getInstance().sem.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized static  char next() {
		char n = 0;
		try {
			getInstance().sem.acquire();
			n = getInstance().buffer.poll();
			getInstance().sem.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		return n;
	}
	
	public static  boolean ready() {
		return !getInstance().buffer.isEmpty();
	}

}
