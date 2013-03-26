package fr.lifl.iuta.compilator.graphics;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import fr.lifl.iuta.compilator.ip.AbstractIP;
import fr.lifl.iuta.compilator.ip.BusIP;

public class GraphicsIpLoad extends JPanel implements Observer{

	public GraphicsIpLoad() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Iterator<AbstractIP> it = BusIP.getIP().iterator();
		while (it.hasNext()){
			this.add(new GraphicsIp(it.next().getName()));
		}
	}
	
	public void update(Observable o, Object arg) {
		this.removeAll();
		Iterator<AbstractIP> it = BusIP.getIP().iterator();
		while (it.hasNext()){
			this.add(new GraphicsIp(it.next().getName()));
		}
	}
}
