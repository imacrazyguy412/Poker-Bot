package we.games;

import java.util.Random; //TODO: when we do multithreading, maybe change to local thread random

public class DiceRollerGame{
  private int roll1, roll2
  private int total;
  private String playerName;
  
  public DiceRollerGame(String name){
    playerName = name;
  }
  
  public void play(){
    roll1 = java.util.Random.nextInt(6) + 1;
    roll2 = java.util.Random.nextInt(6) + 1;
    
    total = roll1 + roll2;
  }
  
  public int getTotal(){
    return total;
  }
  
  public String getPlayerName(){
    return playerName;
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
