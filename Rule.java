package bleh;

import java.util.ArrayList;

import processing.core.PApplet;
import toxi.geom.Vec3D;



public class Rule {

	ArrayList<Type> allTypes; // holds alphabet of this grammar
	
	Type axiom; // start of the fractal
	
	Vec3D origin, iniVel;
	
	int generations;
	
	public Rule(Vec3D origin, Vec3D iniVel, int generations){
		this.origin = origin;
		this.iniVel = iniVel;
		this.generations = generations;
	}
	
	public void start(PApplet p){
		Bleh.sticks.clear();
		Stick start = new Stick(p,origin, iniVel, axiom, generations);
	}
	
	public Type getAxiom() { return axiom; }
	
	public void setAxiom(String a){
		int index = getIndexByName(a);
		axiom = allTypes.get(index);
	}
	
	public ArrayList<String> getTypes(){
		ArrayList<String> names = new ArrayList<String>();
		for(Type t: allTypes)
			names.add(t.toString());
		return names;
	}
	
	public ArrayList<Type> getTypesObj() {return allTypes;}
	
	public void setTypes(ArrayList<Type> type) {this.allTypes = type;}
	
	public void setAxiom(Type axiom){ this.axiom = axiom; }
	
	// spawn is an array of the names on the Types involved
	public void addSpawn(String name, String[] spawn){
		
		Type temp = null; // the Type we will be adding spawn to
		for(Type t: allTypes){
			if(t.getName().equals(name)){
				temp = t;
			}
		}
		ArrayList<Type> spawnList = new ArrayList<Type>();
		for(String s: spawn){
			int index = getIndexByName(s);
			if( index > -1)
				spawnList.add(allTypes.get(index));
		}
		
		if(temp != null)
			temp.setSpawn(spawnList);
		
	}
	
	public int getIndexByName(String name){
		for(int i = 0; i < allTypes.size(); i++){
			Type t = allTypes.get(i);
			if(t.getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	
}
