package sushigame.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401.sushi.AvocadoPortion;
import comp401.sushi.CrabPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.Nigiri.NigiriType;
import comp401.sushi.Plate;
import comp401.sushi.RedPlate;
import comp401.sushi.RicePortion;
import comp401.sushi.Roll;
import comp401.sushi.SalmonPortion;
import comp401.sushi.Sashimi;
import comp401.sushi.Sashimi.SashimiType;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.ShrimpPortion;
import comp401.sushi.Sushi;
import comp401.sushi.TunaPortion;

//my plan: make a drop down menu for color, then type, then add ingredients(drop down and button) and amounts (preset amounts check boxes)

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private Sushi kmp_roll;
	private Sushi crab_sashimi;
	private Sushi eel_nigiri;
	private int belt_size;
	private JFrame pop = new JFrame();
	private double amount_price;
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();
		this.pop = new JFrame();

        pop.pack();
        pop.setVisible(true);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton sashimi_button = new JButton("Add Sashimi Plate!");
		sashimi_button.setActionCommand("sashimi");
		sashimi_button.addActionListener(this);
		add(sashimi_button);

		JButton nigiri_button = new JButton("Add Nigiri Plate!");
		nigiri_button.setActionCommand("nigiri");
		nigiri_button.addActionListener(this);
		add(nigiri_button);

		JButton roll_button = new JButton("Add Roll Plate!");
		roll_button.setActionCommand("roll");
		roll_button.addActionListener(this);
		add(roll_button);

		kmp_roll = new Roll("KMP Roll", new IngredientPortion[] {new EelPortion(1.0), new AvocadoPortion(0.5), new SeaweedPortion(0.2)});
		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
		eel_nigiri = new Nigiri(Nigiri.NigiriType.EEL);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "sashimi":
			//JFrame pop = new JFrame();
			SashimiType[] type_name_list = {SashimiType.TUNA, SashimiType.SALMON, SashimiType.EEL, SashimiType.CRAB, SashimiType.SHRIMP};
			
			Plate.Color[] color_name_list = {Plate.Color.BLUE, Plate.Color.GOLD, Plate.Color.GRAY, Plate.Color.GREEN, Plate.Color.RED};
			
			
			//int[] location = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
			
			String choose_again_again = JOptionPane.showInputDialog(null,
					"Choose a location for your plate (1-20): ");
		 int location = Integer.parseInt(choose_again_again);
		 System.out.println("Location: " + location);
			
			SashimiType choose = (SashimiType) JOptionPane.showInputDialog(null,
					"Sashimi Type:", "Input",
					JOptionPane.QUESTION_MESSAGE, null,
					type_name_list, type_name_list[0]);
			System.out.println("Sashimi: " + choose.name());
			
			
			
			Plate.Color choose_again = (Plate.Color) JOptionPane.showInputDialog(null,
					"Plate Color", "Input",
					JOptionPane.QUESTION_MESSAGE, null,
					color_name_list, color_name_list[0]);
			System.out.println("Color: " + choose_again);
			
			//my problems:
			// 1. uninitialized value i want gone
			// 2. how to only use the get gold plate price when we have a gold plate
			// 3. I can't have decimals in my price and i want decimals
			// 4. how to round my ounces in typeSushiWidget
			
			switch(choose_again) {
			case GOLD :
				JFrame parent = new JFrame();

			    JOptionPane optionPane3 = new JOptionPane();
			    JSlider slider = getGoldSlider(optionPane3);
			    
			    optionPane3.setMessage(new Object[] { "Select a price (in $) for gold plate: ", slider });
			    optionPane3.setMessageType(JOptionPane.QUESTION_MESSAGE);
			    optionPane3.setOptionType(JOptionPane.OK_CANCEL_OPTION);
			    JDialog dialog = optionPane3.createDialog(parent, "My Slider");
			    dialog.setVisible(true);
			    if(amount_price == 0.0) {
			    	amount_price = 5.0;
			    }
			    System.out.println("Price: " + amount_price);	
			    
			    makeGoldPlateRequest(new Sashimi(choose), location, amount_price);
			
			    break;
			    
			case BLUE:
				
				makeBluePlateRequest(new Sashimi(choose), location);
				
				break;
			
			case GREEN:
				
				makeGreenPlateRequest(new Sashimi(choose), location);
				
				break;
			
			case RED:
				
				makeRedPlateRequest(new Sashimi(choose), location);
				
				break;
			
			}
			
			
			 
			    
			    
			//MY QUESTIONS: how do I pull the variable out from the button the user pushes,
			//and I don't understand sliders
			
			
			//makeRedPlateRequest(crab_sashimi, 3);
			break;
		case "nigiri":
			
			NigiriType[] type_name_list2 = {NigiriType.TUNA, NigiriType.SALMON, NigiriType.EEL, NigiriType.CRAB, NigiriType.SHRIMP};
		
			Plate.Color[] color_name_list2 = {Plate.Color.BLUE, Plate.Color.GOLD, Plate.Color.GREEN, Plate.Color.RED};
			
			//int[] location2 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
			
			String choose_again_again2 = JOptionPane.showInputDialog(null,
					"Choose a location for your plate (1-20): ");
		 int location2 = Integer.parseInt(choose_again_again2);
		 System.out.println("Location: " + location2);
			
			NigiriType choose2 = (NigiriType) JOptionPane.showInputDialog(null,
					"Nigiri Type:", "Input",
					JOptionPane.INFORMATION_MESSAGE, null,
					type_name_list2, type_name_list2[0]);
			System.out.println("Nigiri: " + choose2.name());
			
			Plate.Color choose_again2 = (Plate.Color) JOptionPane.showInputDialog(null,
					"Choose a color:", "Input",
					JOptionPane.INFORMATION_MESSAGE, null,
					color_name_list2, color_name_list2[0]);
			System.out.println("Color: " + choose_again2.name());
			
		    
		    switch(choose_again2) {
			case GOLD :
				JFrame parent = new JFrame();

			    JOptionPane optionPane3 = new JOptionPane();
			    JSlider slider = getGoldSlider(optionPane3);
			    
			    optionPane3.setMessage(new Object[] { "Select a price (in $) for gold plate: ", slider });
			    optionPane3.setMessageType(JOptionPane.QUESTION_MESSAGE);
			    optionPane3.setOptionType(JOptionPane.OK_CANCEL_OPTION);
			    JDialog dialog = optionPane3.createDialog(parent, "My Slider");
			    dialog.setVisible(true);
			    if(amount_price == 0.0) {
			    	amount_price = 5.0;
			    }
			    System.out.println("Price: " + amount_price);	
			    
			    makeGoldPlateRequest(new Nigiri(choose2), location2, amount_price);
			
			    break;
			    
			case BLUE:
				
				makeBluePlateRequest(new Nigiri(choose2), location2);
				
				break;
			
			case GREEN:
				
				makeGreenPlateRequest(new Nigiri(choose2), location2);
				
				break;
			
			case RED:
				
				makeRedPlateRequest(new Nigiri(choose2), location2);
				
				break;
			
			}
		    
			break;
		
		
		
		case "roll":
			
			JPanel mother = new JPanel();
			
			mother.setLayout(new GridLayout(0, 1));
			JLabel instructions = new JLabel("Add ingredients to your roll (max 1.5oz of each ingredient):");
			JLabel avacado_in = new JLabel("Avacado:");
			JSpinner avacados = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			JLabel crab_in = new JLabel("Crab:");
			JSpinner crabs = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			JLabel eel_in = new JLabel("Eel:");
			JSpinner eels = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			JLabel rice_in = new JLabel("Rice:");
			JSpinner rice = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			JLabel salmon_in = new JLabel("Salmon:");
			JSpinner salmon = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			JLabel seaweed_in = new JLabel("Seaweed:");
			JSpinner seaweed = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			JLabel shrimp_in = new JLabel("Shrimp:");
			JSpinner shrimp = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			JLabel tuna_in = new JLabel("Tuna:");
			JSpinner tuna = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.5, 0.1));
			//makeGoldPlateRequest(kmp_roll, 5, 5.00);
			
			mother.add(instructions);
			mother.add(tuna_in);
			mother.add(tuna);
			mother.add(crab_in);
			mother.add(crabs);
			mother.add(eel_in);
			mother.add(eels);
			mother.add(salmon_in);
			mother.add(salmon);
			mother.add(shrimp_in);
			mother.add(shrimp);
			mother.add(avacado_in);
			mother.add(avacados);
			mother.add(seaweed_in);
			mother.add(seaweed);
			mother.add(rice_in);
			mother.add(rice);
			
			int roll_idea = JOptionPane.showConfirmDialog(null, mother, "Roll Maker", JOptionPane.OK_CANCEL_OPTION);
			
			Plate.Color[] color_name_list3 = {Plate.Color.BLUE, Plate.Color.GOLD, Plate.Color.GREEN, Plate.Color.RED};
			
			Plate.Color choose_again3 = (Plate.Color) JOptionPane.showInputDialog(null,
					"Choose a color:", "Input",
					JOptionPane.INFORMATION_MESSAGE, null,
					color_name_list3, color_name_list3[0]);
			System.out.println("Color: " + choose_again3.name());
			
			String choose_again_again3 = JOptionPane.showInputDialog(null,
					"Choose a location for your plate (1-20): ");
		 int location3 = Integer.parseInt(choose_again_again3);
		 
		 if (roll_idea == JOptionPane.OK_OPTION) {
			 ArrayList <IngredientPortion> ingreds = new ArrayList<IngredientPortion>();
			 
			 if ((double)tuna.getValue() != 0 && (double)tuna.getValue() <= 1.5) {
				 
				 TunaPortion tuna_portion = new TunaPortion((double)tuna.getValue());
				 ingreds.add(tuna_portion);
				 
			 }
			 
			 if ((double)crabs.getValue() != 0 && (double)crabs.getValue() <= 1.5) {
				CrabPortion crab_portion = new CrabPortion((double)crabs.getValue());
				ingreds.add(crab_portion);
			 }
			 
			 if ((double)eels.getValue() != 0 && (double)eels.getValue() <= 1.5) {
				 EelPortion eel_portion = new EelPortion((double)eels.getValue());
				 ingreds.add(eel_portion);
			 }
			 
			 if ((double)salmon.getValue() != 0 && (double)salmon.getValue() <= 1.5) {
				 SalmonPortion salmon_portion = new SalmonPortion((double)salmon.getValue());
				 ingreds.add(salmon_portion);
			 }
			 
			 if ((double)shrimp.getValue() != 0 && (double)shrimp.getValue() <= 1.5) {
				 ShrimpPortion shrimp_portion = new ShrimpPortion((double)shrimp.getValue());
				 ingreds.add(shrimp_portion);
			 }
			 
			 if ((double)avacados.getValue() != 0 && (double)avacados.getValue() <= 1.5) {
				 AvocadoPortion avacado_portion = new AvocadoPortion((double)avacados.getValue());
				 ingreds.add(avacado_portion);
			 }
			 
			 if ((double)seaweed.getValue() != 0 && (double)seaweed.getValue() <= 1.5) {
				 SeaweedPortion seaweed_portion = new SeaweedPortion((double)seaweed.getValue());
				 ingreds.add(seaweed_portion);
			 }
			 
			 if ((double)rice.getValue() != 0 && (double)rice.getValue() <= 1.5) {
				 RicePortion rice_portion = new RicePortion((double)rice.getValue());
				 ingreds.add(rice_portion);
			 }
			 
			    switch(choose_again3) {
				case GOLD :
					JFrame parent = new JFrame();

				    JOptionPane optionPane3 = new JOptionPane();
				    JSlider slider = getGoldSlider(optionPane3);
				    
				    optionPane3.setMessage(new Object[] { "Select a price (in $) for gold plate: ", slider });
				    optionPane3.setMessageType(JOptionPane.QUESTION_MESSAGE);
				    optionPane3.setOptionType(JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane3.createDialog(parent, "My Slider");
				    dialog.setVisible(true);
				    if(amount_price == 0.0) {
				    	amount_price = 5.0;
				    }
				    System.out.println("Price: " + amount_price);	
				    
				    makeGoldPlateRequest(new Roll("Random Roll", ingreds.toArray(new IngredientPortion[ingreds.size()])), location3, amount_price);
				
				    break;
				    
				case BLUE:
					
					makeBluePlateRequest(new Roll("Random Roll", ingreds.toArray(new IngredientPortion[ingreds.size()])), location3);
					
					break;
				
				case GREEN:
					
					makeGreenPlateRequest(new Roll("Random Roll", ingreds.toArray(new IngredientPortion[ingreds.size()])), location3);
					
					break;
				
				case RED:
					
					makeRedPlateRequest(new Roll("Random Roll", ingreds.toArray(new IngredientPortion[ingreds.size()])), location3);
					
					break;
				
				}
		 }
			
		
		}
	}

	
	
	private JSlider getGoldSlider(JOptionPane optionPane) {
		
		final int gold_plate_min_price = 5;
		final int gold_plate_max_price = 10;
		final int start = 5;
		// TODO Auto-generated method stub
		JSlider slider1 = new JSlider(JSlider.HORIZONTAL,
				gold_plate_min_price, gold_plate_max_price, start);
        slider1.setMajorTickSpacing(1);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        ChangeListener changeListener = new ChangeListener() {
          public void stateChanged(ChangeEvent changeEvent) {
            JSlider theSlider = (JSlider) changeEvent.getSource();
            amount_price = ((JSlider)changeEvent.getSource()).getValue();
           // if (!theSlider.getValueIsAdjusting()) {
            	
            //}
          }
        };
        slider1.addChangeListener(changeListener);
        return slider1;

	}
}
