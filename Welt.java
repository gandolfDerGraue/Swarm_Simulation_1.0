import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Random;

public class Welt {
	Fisch[][]zukunftsFische;
	Fisch[][]fische;
	Jaeger[][]jaeger;
	double viskositaet;
	int w,h;
	int fischSichtweite;
	int dichte;
	//////////////////////////////////////////////Konstruktor
	public Welt(int incFischSichtweite, int incDichte){
		dichte=incDichte;
		fischSichtweite=incFischSichtweite;
		viskositaet=1;
		weltErschaffen();
		spawn(100);

	}
	//////////////////////////////////////////////
	void spawn(int n){
		fische[100][100]=new Fisch(10,10);
		
		
	}
	//////////////////////////////////////////////
	void weltErschaffen(){
		Toolkit tool =Toolkit.getDefaultToolkit();
		Dimension d= tool.getScreenSize();
		int width=(int)d.getWidth();
		int height=(int)d.getHeight();
		fische= new Fisch[width][height];
		jaeger= new Jaeger[width][height];
		w=width;
		h=height;
	}
	//////////////////////////////////////////////
	void schwarmSchwimmen(){
		zukunftsFische=new Fisch[w][h];
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
	
				if(fische[i][j]instanceof Fisch){
					schwimmen(i,j);
				}
			}
		}
		fische=zukunftsFische;
	}
	//////////////////////////////////////////////
	void schwimmen(int x,int y){
		//in present schauen wo ich hinschwimme
		//diesen neuen zustand in zukunft speichern

		//schaue mir von den nachbarfischen nur die pos an und vernachlässige deren schwimmrichtung
		//fische haben rundumblick
		LinkedList<Integer> xKoo=new LinkedList<Integer>();
		LinkedList<Integer> yKoo=new LinkedList<Integer>();
		int nFische=0;
		int schwarmVX=0,schwarmVY=0;
		for(int i =-fischSichtweite/2;i<fischSichtweite/2;i++){
			for(int j =-fischSichtweite/2;j<fischSichtweite/2;j++){
				//rundum SIchtweite und ein Fisch ist zu sehen
				if(x+i>w)x=w;
				if(x+i<0)x=0;
				if(y+j>h)y=h;
				if(y+j<0)y=0;
				if(Math.sqrt(i*i+j*j)<fischSichtweite &&Math.sqrt(i*i+j*j)!=0 && fische[x+i][y+j] instanceof Fisch){
					
					xKoo.add(i);
					yKoo.add(j);
					nFische++;
					schwarmVX+=fische[i][j].vX;
					schwarmVY+=fische[i][j].vY;
				}
			}	
		}
		//durschnitts RIchtungsvektor der nachbarfische
		if(nFische==0){
			Random r =new Random();
			int rX = r.nextInt(20)-10;
			int rY = r.nextInt(20)-10;
			int randX=(int)(rX*1d/5);
			int randY=(int)(rY*1d/5);
			x+=randX;
			y+=randY;
			if(x>w)x=w;
			if(x<0)x=0;
			if(y>h)y=h;
			if(y<0)y=0;
			zukunftsFische[x+fische[x][y].vX][y+fische[x][y].vY]=new Fisch(randX+fische[x][y].vX,randY+fische[x][y].vY);
		}
		else{
			schwarmVX/=nFische;
			schwarmVY/=nFische;
		
		
		//richtungsvektor um abstand zu nachbarn anzupassen
		int schwarmX=0,schwarmY=0;
		for(int i =0;i<xKoo.size();i++){
			//abstand zu mittelFIsch
			double dis=Math.sqrt(xKoo.get(i)*xKoo.get(i)+yKoo.get(i)*yKoo.get(i));
			double ratio=dis*1d/dichte;
			schwarmX+=((int) (xKoo.get(i)-xKoo.get(i)/ratio));
			schwarmY+=((int) (yKoo.get(i)-yKoo.get(i)/ratio));
		}
		schwarmX/=nFische;
		schwarmY/=nFische;

	
		Random r =new Random();
		int rX = r.nextInt(20)-10;
		int rY = r.nextInt(20)-10;
		double randX=rX*1d/3;
		double randY=rY*1d/3;
		

		x+=(schwarmVX+schwarmX+randX);
		y+=(schwarmVY+schwarmY+randY);
		
		if(x>w)x=w;
		if(x<0)x=0;
		if(y>h)y=h;
		if(y<0)y=0;
		
		zukunftsFische[x][y]=new Fisch((int)((schwarmVX+schwarmX)*viskositaet),(int)((schwarmVY+schwarmY)*viskositaet));
		}
	}
	//////////////////////////////////////////////

}
