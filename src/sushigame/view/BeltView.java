package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comp401.sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver {

	private Belt belt;
	private TypeSushiWidget[] belt_labels;
	

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridLayout(belt.getSize(), 1));
		belt_labels = new TypeSushiWidget[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			TypeSushiWidget plabel = new TypeSushiWidget(belt, i);
//			plabel.setMinimumSize(new Dimension(300, 20));
//			plabel.setPreferredSize(new Dimension(300, 20));
//			plabel.setOpaque(true);
//			plabel.setBackground(Color.GRAY);
			
			//plabel.addActionListener(TypeSushiWidget(belt, i));
			add(plabel);
			belt_labels[i] = plabel;
		}
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			TypeSushiWidget plabel = belt_labels[i];
			plabel.setActionCommand("display");
			//plabel.addActionListener(this);

			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.GRAY);
			} else {
				plabel.setText(p.getContents().getName());
				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED); break;
				case GREEN:
					plabel.setBackground(Color.GREEN); break;
				case BLUE:
					plabel.setBackground(Color.BLUE); break;
				case GOLD:
					plabel.setBackground(Color.YELLOW); break;
				}
			}
		}
	}
}
