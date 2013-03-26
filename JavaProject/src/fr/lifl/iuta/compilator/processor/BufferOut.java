package fr.lifl.iuta.compilator.processor;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class BufferOut {
	
	private LinkedBlockingQueue<Character> buffer;

	private static BufferOut instance = null;
	
	public static BufferOut getInstance() {
		if(instance == null)
			init();
		return instance;
	}
	
	public static void init() {
		instance = new BufferOut();
		instance.buffer = new LinkedBlockingQueue<Character>();
	}
	
	public static void add(char value) {
		getInstance().buffer.offer(value);
	}
	
	public synchronized static  char next() {
		char n = 0;
		n = getInstance().buffer.poll();
		return n;
	}
	
	public static  boolean ready() {
		return !getInstance().buffer.isEmpty();
	}

}
