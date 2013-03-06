package fr.lifl.iuta.compilator.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author danglotb
 * Réimplementation static de la classe Observable.
 */
public abstract class MyObservable {

	protected static ArrayList<Observer> listOfObserver = new ArrayList<Observer>();
	
	public static void addObserver(Observer o){
		listOfObserver.add(o);
	}
	
	public static void notifyObserver(){
		Iterator<Observer> it = listOfObserver.iterator();
		while (it.hasNext()){
			it.next().update(null, null);
		}
	}
	
	public static void notifyObserver(Object arg1){
		Iterator<Observer> it = listOfObserver.iterator();
		while (it.hasNext()){
			it.next().update(null, arg1);
		}
	}
	
}
