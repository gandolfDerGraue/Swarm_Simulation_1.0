import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class SchwarmTest extends JFrame implements Runnable{
	
	Welt welt;
	int w,h;
	static SchwarmTest s;
	
	public static void main(String [] args){
		s=new SchwarmTest();
		s.welt=new Welt(20,10);
	}
	public SchwarmTest(){
		Toolkit tool =Toolkit.getDefaultToolkit();
		Dimension d= tool.getScreenSize();
		int width=(int)d.getWidth();
		int height=(int)d.getHeight();
		w=width;
		h=height;
		setSize(width/2,height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		Thread t= new Thread(this);
		t.start();
		
	}
	public void paint(Graphics g){
		super.paintComponents(g);
		g.setColor(Color.BLACK);
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				if(s.welt.fische[i][j]instanceof Fisch){
					g.drawLine(i, j, i, j);
				}
			}
		}
	}
	public void run() {
		warten(100);
		while(true){
			
			s.welt.schwarmSchwimmen();
			warten(100);
			s.repaint();
		}
	}
	void warten(int n ){
		try {
			Thread.sleep(n);
		} 
		catch (InterruptedException e) {}
	}
}
