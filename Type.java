package bleh;

import java.util.ArrayList;

import processing.core.PApplet;
import toxi.geom.Vec3D;


/*
 * This class stores the different types of Sticks
 */
public class Type {

	String name;
	Vec3D velocity;
	ArrayList<Type> spawn;
	
	PApplet p;
	
	public Type(PApplet p, String name, Vec3D velocity){
		this.name = name;
		this.velocity = velocity;
		this.p = p;
		spawn = new ArrayList<Type>();
	}
	
	public Type(PApplet p, String name){
		this.name = name;
		this.velocity = new Vec3D(0,0,0);
		this.p = p;
		spawn = new ArrayList<Type>();
	}
	
	public String getName(){ return name; }
	public Vec3D getVelocity(){ return velocity; }
	
	public void setName(String name){ this.name = name; }
	
	public void setVelocity(Vec3D velocity) { this.velocity = velocity; }
	
	// set by angles + magnitude. note: theta is in degrees
	public void setVelocity(Vec3D vel, float thetaX, float thetaY, float thetaZ){
		
		//Vec3D vel = new Vec3D(mag, 0, 0);
		float angleX = PApplet.radians(thetaX);
	    float angleY = PApplet.radians(thetaY);
	    float angleZ = PApplet.radians(thetaZ);
	      
	    vel.rotateX(angleX);
	    vel.rotateY(angleY);
	    vel.rotateZ(angleZ);
		
	}
	
	public ArrayList<Type> getSpawn(){ return spawn; }
	public void setSpawn( ArrayList<Type> spawn ){ this.spawn = spawn; }
	

	public String toString(){
		return name;
	}
}
