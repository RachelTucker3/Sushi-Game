package comp401.sushi;

import sushigame.model.Chef;

public interface Plate {
	 public enum Color {RED, GREEN, BLUE, GOLD, GRAY}

     Sushi getContents();
     double getPrice();
     Plate.Color getColor();
     double getProfit();
     Chef getChef();     
}

