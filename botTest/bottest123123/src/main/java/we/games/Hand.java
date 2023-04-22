// Damn I miss typedef

package we.games;

import java.util.ArrayList;

/**
 * {@code ArrayList<Card>}
 * 
 * 
 */
public class Hand extends ArrayList<Card>{

  /**
   * Sorts in descending order using {@link #sort(java.util.Comparator)}
   * 
   * @see #sort()
   * @see java.util.Comparator
   */
  public void sort(){
    this.sort(java.util.Collections.reverseOrder());
  }
}