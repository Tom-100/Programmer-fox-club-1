package hu.gyakorlas.foxacademy;

import java.util.ArrayList;
import java.util.List;

public class Fox implements Comparable<Fox> {
    private String name;
    private List<String> listOfTricks;
    private String food;
    private String drink;
    private List<String> actionHistory;


    public Fox(String name){
        this.name = name;
        listOfTricks = new ArrayList<>();
        this.food = "pizza";
        this.drink = "lemonade";
        actionHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getListOfTricks() {
        return listOfTricks;
    }

    public void setListOfTricks(List<String> listOfTricks) {
        this.listOfTricks = listOfTricks;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public int getNumberOfTricks() {
        return listOfTricks.size();
    }

    public int getNumberOfActions(){ return actionHistory.size(); }

    public List<String> getActionHistory() {
        return actionHistory;
    }

    public void setActionHistory(List<String> actionHistory) {
        this.actionHistory = actionHistory;
    }

    @Override
    public int compareTo(Fox anotherFox) {
        return this.name.compareTo(anotherFox.getName());
    }
}
