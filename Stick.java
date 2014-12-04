package bleh;

import java.util.ArrayList;

import processing.core.PApplet;
import toxi.geom.Vec3D;

/*
 * a recursive class
 */
public class Stick {

	Vec3D initialPos; // where this Stick starts
	Vec3D finalPos; // where this Stick ends
	int generations;
	Type type; // what type of Stick this is
	Vec3D vel;
	
	PApplet p; // reference to parent PApplet
	
	public Stick(PApplet p, Vec3D initialPos, Vec3D v, Type type, int generations){
		
		this.p = p;
		this.initialPos = initialPos;
		this.type = type;
		this.generations = generations;
		this.vel = v.copy();
		// get velocity from Type and add it to initial position
		//finalPos = initialPos.copy();
		Vec3D velocity = type.getVelocity();
		finalPos = initialPos.copy();
		vel.rotateX( PApplet.radians(velocity.x));
		vel.rotateY( PApplet.radians(velocity.y));
		vel.rotateZ( PApplet.radians(velocity.z));
		
		finalPos.addSelf(vel);
		
		Bleh.sticks.add(this);
		
		// now spawn the other child Sticks
		spawn();
		
	}
	
	public void spawn(){
		
		// create new Sticks based on the references of the other Types
		ArrayList<Type> spawn = type.getSpawn();
	
		if(generations > 1)
			for(Type t: spawn){
				Stick temp = new Stick(p,finalPos.copy(),vel.copy(),t,generations-1);
			}
		
	}
	
	public void display(){
		
		p.stroke(255,0,0);
		p.strokeWeight(4);
		p.point(initialPos.x,initialPos.y,initialPos.z);
		
		p.stroke(0,67,255);
		p.strokeWeight(1);
		p.line(initialPos.x,initialPos.y,initialPos.z, finalPos.x, finalPos.y, finalPos.z);
		
	}
	
	
	
	
	
	
	
	
}
