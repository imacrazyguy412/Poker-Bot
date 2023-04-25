// Damn I miss typedef

package we.games;

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
    // This is the exact same code as java.util.AbstractCollection#toString(), with some minor
    // changes:
    // the delimiter here is "\n" to give a new line
    // all instances of square brackets have been removed
    // an empty collection is represented as "no cards" rather than "[]"
    // this collection is represented as "(this Hand)" rather than "(this Collection)"
    // the type of the elements is Card, because that is what E has been given

    Iterator<Card> it = iterator();
    if (! it.hasNext())
      return "no cards";

    StringBuilder sb = new StringBuilder();
    for (;;) {
      Card e = it.next();
      sb.append(e == this ? "(this Hand)" : e);
      if (! it.hasNext())
        return sb.toString();
      sb.append('\n');
    }
  }
}