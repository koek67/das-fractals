package bleh;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import processing.core.PApplet;
import toxi.geom.Vec3D;

public class DisplayComponent extends JComponent{

	Rule r;
	boolean hasChanged = false;
	PApplet p;
	JFrame f;

	JPanel selectorPanel = new JPanel();
	JLabel typeLabel = new JLabel("Current Type");
	JComboBox typeSelector = new JComboBox();
	JLabel axiomLabel = new JLabel("Axiom");
	JComboBox axiomSelector = new JComboBox();
	String currentType = "";
	String axiom = "";

	JPanel adPanel = new JPanel();
	JButton add = new JButton("Add Type");
	JButton remove = new JButton("Remove this Type");

	JPanel xPanel = new JPanel();
	JLabel xLabel, yLabel, zLabel;
	JSlider x = new JSlider(JSlider.HORIZONTAL,-360,360,10);
	JPanel yPanel = new JPanel();
	JSlider y = new JSlider(JSlider.HORIZONTAL,-360,360,10);
	JPanel zPanel = new JPanel();
	JSlider z = new JSlider(JSlider.HORIZONTAL,-360,360,10);

	JPanel spawnPanel = new JPanel();
	JTextField spawnField = new JTextField();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DisplayComponent(Rule r, PApplet p, JFrame f){
		this.r = r;
		this.p = p;
		this.f = f;
		// fill type selector with all different types
		ArrayList<String> typesList = r.getTypes();
		typeSelector = new JComboBox(typesList.toArray());
		axiomSelector = new JComboBox(typesList.toArray());
		axiom = r.getAxiom().getName();
		if(currentType.equals(""))
			currentType = typesList.get(0);
		Type current = r.getTypesObj().get(r.getTypes().indexOf(currentType));
		xLabel = new JLabel("X Rotation: " + current.getVelocity().x);
		yLabel = new JLabel("Y Rotation: " + current.getVelocity().y);
		zLabel = new JLabel("Z Rotation: " + current.getVelocity().z);
		spawnField.setColumns(10);
		resetSpawnField();
		
	}

	public void resetSpawnField(){
		Type current = r.getTypesObj().get(r.getTypes().indexOf(currentType));
		String text = "";
		int i = 0;
		for(Type n: current.getSpawn()){
			if(i != current.getSpawn().size() -1)
			text += n.getName() + ", ";
			else text += n.getName();
			i++;
		}
		spawnField.setText(text);
	}
	
	public void paintComponent(Graphics g){

		if(hasChanged){

			r.start(p);
			p.draw();
			//System.out.println("Current Type: " + currentType);
			//System.out.println("Axiom: " + axiom);
			Type current = r.getTypesObj().get(r.getTypes().indexOf(currentType));
			xLabel.setText("X Rotation: " + current.getVelocity().x);
			yLabel.setText("Y Rotation: " + current.getVelocity().y);
			zLabel.setText("Z Rotation: " + current.getVelocity().z);
			resetSpawnField();
			hasChanged = false;

		}

	}
	
	public void parseString(String s){
		
		StringTokenizer stx = new StringTokenizer(s, " ");
		
		while(stx.hasMoreTokens()){
			
			String j = stx.nextToken();
			
			
		}
		
	}

	public void init(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// start with the selector panel
		typeSelector.addActionListener(new ActionListener(){

			// when type is selected, change current type
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
				String type = (String)cb.getSelectedItem();
				currentType = type;
				hasChanged = true;
				repaint();
			}

		});

		// axiom selector
		axiomSelector.addActionListener(new ActionListener(){

			// when axiom is selected, change axiom string
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
				String ax = (String)cb.getSelectedItem();
				axiom = ax;
				r.setAxiom(axiom);
				hasChanged = true;
				repaint();
			}

		});
		// add button
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Type> typesList = r.getTypesObj();
				String str = JOptionPane.showInputDialog(f, "Enter name : ", 
						"Add new type", 1);
				if(str != null){
					typesList.add(new Type(p,str));
					typeSelector.addItem(str);
					axiomSelector.addItem(str);
				}
			}

		});
		// remove button
		remove.addActionListener(new ActionListener(){


			public void actionPerformed(ActionEvent e) {
				ArrayList<Type> typesList = r.getTypesObj();
				if(typesList.size() != 1 && !currentType.equals(axiom)){
					typeSelector.removeItem(currentType);
					axiomSelector.removeItem(currentType);
					currentType = axiom;
					typeSelector.setSelectedItem(axiom);
				}
				else{
					JOptionPane.showMessageDialog(f,
							"Cannot remove");
				}
			}

		});

		// x slider
		x.addChangeListener(new ChangeListener(){


			public void stateChanged(ChangeEvent arg0) {
				int value = ((JSlider) arg0.getSource()).getValue();
				Type current = r.getTypesObj().get(r.getTypes().indexOf(currentType));
				Vec3D oldV = current.getVelocity();
				Vec3D newV = new Vec3D(value,oldV.y,oldV.z);
				current.setVelocity(newV);
				xLabel.setText("X Rotation: " + current.getVelocity().x);
				hasChanged = true;
				repaint();
			}

		});

		// y slider
		y.addChangeListener(new ChangeListener(){


			public void stateChanged(ChangeEvent arg0) {
				int value = ((JSlider) arg0.getSource()).getValue();
				Type current = r.getTypesObj().get(r.getTypes().indexOf(currentType));
				Vec3D oldV = current.getVelocity();
				Vec3D newV = new Vec3D(oldV.x,value,oldV.z);
				yLabel.setText("Y Rotation: " + current.getVelocity().y);
				current.setVelocity(newV);
				hasChanged = true;
				repaint();
			}

		});

		// z slider
		z.addChangeListener(new ChangeListener(){


			public void stateChanged(ChangeEvent arg0) {
				int value = ((JSlider) arg0.getSource()).getValue();
				Type current = r.getTypesObj().get(r.getTypes().indexOf(currentType));
				Vec3D oldV = current.getVelocity();
				Vec3D newV = new Vec3D(oldV.x,oldV.y,value);
				zLabel.setText("Z Rotation: " + current.getVelocity().z);
				current.setVelocity(newV);
				hasChanged = true;
				repaint();
			}

		});
		
		// spawnField editor
		spawnField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String text = spawnField.getText();
				
				
			}
			
		});
		
		
		selectorPanel.add(typeLabel);
		selectorPanel.add(typeSelector);
		selectorPanel.add(axiomLabel);
		selectorPanel.add(axiomSelector);

		adPanel.add(add);
		//adPanel.add(remove);

		xPanel.add(xLabel);
		xPanel.add(x);
		yPanel.add(yLabel);
		yPanel.add(y);
		zPanel.add(zLabel);
		zPanel.add(z);
		
		spawnPanel.add(spawnField);

		this.add(selectorPanel);
		this.add(adPanel);
		this.add(xPanel);
		this.add(yPanel);
		this.add(zPanel);
		this.add(spawnPanel);

	}

}
