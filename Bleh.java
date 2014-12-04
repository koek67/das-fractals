package bleh;

import processing.core.PApplet;
import peasy.*;
import peasy.*;
import toxi.geom.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;

/*
 * This class is the runner class for the LSystem Project
 */
public class Bleh extends PApplet {

	PeasyCam cam;
	public static ArrayList<Stick> sticks;
	
	//set initial position to the origin
	Rule r;
	boolean changed = true;
	public void setup() {
		size(1000,800,P3D);
		cam = new PeasyCam(this,1000);
		sticks = new ArrayList<Stick>();
		Vec3D origin = new Vec3D(0,0,0);
		Vec3D iniVel = new Vec3D(100,0,100);
		
		
		
		Type A = new Type(this, "A", new Vec3D(10,-10,10));
		Type B = new Type(this, "B", new Vec3D(-10,10,10));
		Type C = new Type(this, "C", new Vec3D(10,10,-10));
		
		ArrayList<Type> allTypes = new ArrayList<Type>();
		allTypes.add(A);
		allTypes.add(B);
		allTypes.add(C);
		
		r = new Rule(origin, iniVel, 9);
		r.setTypes(allTypes);
		r.setAxiom(A);
		String[] a_spawn = {"A", "B"};
		String[] b_spawn = {"A", "C"};
		String[] c_spawn = {"B", "A","C"};
		r.addSpawn("A", a_spawn);
		r.addSpawn("B", b_spawn);
		r.addSpawn("C", c_spawn);
		
		JFrame f = new JFrame("Control Panel");
		f.setSize(400,700);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DisplayComponent d = new DisplayComponent(r, this, f);
		d.init();
		f.add(d);
		f.setVisible(true);
		
		
	}

	public void draw() {
		background(255);
		if(changed){
			r.start(this);
			changed = false;
		}
		
		for(Stick s: sticks){
			
			s.display();
		}
			
		//sticks.clear();
	}
	
	
	public static void main(String _args[]) {
		
		PApplet.main(new String[] { bleh.Bleh.class.getName() });
		
	}
}
