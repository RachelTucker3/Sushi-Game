package sushigame.view;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.Plate;
import comp401.sushi.Plate.Color;
import comp401.sushi.Roll;
import comp401.sushi.Sashimi;
import comp401.sushi.Sushi;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;


public class TypeSushiWidget extends JButton implements ActionListener {

	private Belt belt;
	private int location;
	private Color plate_color;
	private JFrame parent = new JFrame();
	private JLabel info;
	private String name;
	private StringBuilder final_ingreds;
	
	public TypeSushiWidget(Belt belt, int location) {
		this.belt = belt;
		this.location = location;
    	this.parent = new JFrame();

	        parent.pack();
	        parent.setVisible(true);
	        this.setActionCommand("display");
	        this.addActionListener(this);
			this.setMinimumSize(new Dimension(300, 20));
			this.setPreferredSize(new Dimension(300, 20));
			this.setOpaque(true);
			this.setBackground(java.awt.Color.GRAY);
		
			final_ingreds = new StringBuilder();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("woo");
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "display") {
			String plate_color = belt.getPlateAtPosition(location).getColor().name();
			Sushi type_sushi = belt.getPlateAtPosition(location).getContents();
			IngredientPortion[] ingredient_list = belt.getPlateAtPosition(location).getContents().getIngredients();
			//String[] ingredient_names = new String[ingredient_list.length];
			String[] ingredient_amounts = new String[ingredient_list.length];
			//String ingredients_and_amounts = "";
			if(type_sushi instanceof Roll) {
				for(int i = 0; i < ingredient_list.length; i++) {
					//ingredient_names[i] = ingredient_list[i].getIngredient().getName();
					ingredient_amounts[i] = ingredient_list[i].getAmount() + " ounce of " + ingredient_list[i].getIngredient().getName() + "\n";
					
				}
				for(int i = 0; i < ingredient_amounts.length; i++) {
					if(ingredient_amounts[i] != null) {
					final_ingreds.append(ingredient_amounts[i]) ;
					}
				}
				//final_ingreds.append("a") ;
				//final_ingreds ;
				//System.out.println("roll");
			}
			else if(type_sushi instanceof Sashimi) {
				final_ingreds.append("Sashimi");
			}
			else if(type_sushi instanceof Nigiri) {
				final_ingreds.append("Nigiri");
			}
			
			String chef_name = belt.getPlateAtPosition(location).getChef().getName();
			int age = belt.getAgeOfPlateAtPosition(location);
			
			JOptionPane.showMessageDialog(parent,
			      "Plate Color: " + plate_color + "\n" +
			      "Type of Sushi: " + type_sushi.getName() + "\n" +
			      "Ingredients: \n" + final_ingreds.toString() + "\n" +
			      "Chef: " + chef_name + "\n" +
			      "Age of Plate: " + age);
				
			
			
		}
		
		
	}
	
	//public String getName() {
	//	return name;
		
	//}
	

	

}
