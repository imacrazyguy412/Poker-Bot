package we.games;

import java.util.Random; //TODO: when we do multithreading, maybe change to local thread random

public class DiceRollerGame{
  private int roll1, roll2;
  private int total;
  private String choice, playerName;

  private Random rand = new Random();
  
  public DiceRollerGame(String choice, String name){
    this.choice = choice;
  }
  
  public void play(){
    roll1 = rand.nextInt(6) + 1;
    roll2 = rand.nextInt(6) + 1;
    
    total = roll1 + roll2;
  }
  
  public String getPlayerName(){
    return playerName;
  }

  public boolean didWin(){
    if(choice.equals("high") && total > 7){
      return true;
    } else if(choice.equals("low") && total < 7){
      return true;
    }
    return false;
  }
  
  @Override
  public boolean equals(Object obj){
    DiceRollerGame d = (DiceRollerGame)obj;
    
    if(d.getPlayerName().equals(this.getPlayerName())){
      return true;
    }
    return false;
  }
}
