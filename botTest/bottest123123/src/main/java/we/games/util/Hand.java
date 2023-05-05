// Damn I miss typedef

package we.games.util;

import java.util.ArrayList;

/**
 * {@code ArrayList<Card>}
 * 
 * @see java.util.ArrayList
 * @see Card
 */
public class Hand extends ArrayList<Card>{

  /**
   * Sorts in descending order using {@link #sort(java.util.Comparator)}
   * 
   * @see #sort(java.util.Comparator)
   * @see java.util.Comparator
   */
  public void sort(){
    this.sort(java.util.Collections.reverseOrder());
  }

  /**
   * Returns a string representation of this hand
   * <p>
   * The string representation consists of a list of the hand's cards in the order
   * they are returned by its iterator. Adjacent elements are separated by the 
   * character {@code '\n'} (new line). Cards are converted to strings as by 
   * {@link String#valueOf(Object)}.
   * 
   * @return a string representation of this hand
   * @see java.util.AbstractCollection#toString()
   */
  @Override
  public String toString(){
    //nevermind lol

    StringBuilder str = new StringBuilder();
    for(Card card : this){
      str.append(card).append('\n');
    }
    str.setLength(str.length() - 1); //remove last end line
    return str.toString(); 
  }
}