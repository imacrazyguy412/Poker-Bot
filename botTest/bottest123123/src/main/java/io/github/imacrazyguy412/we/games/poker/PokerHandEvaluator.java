package io.github.imacrazyguy412.we.games.poker;

import java.util.ArrayList;

import io.github.imacrazyguy412.we.games.util.Card;
import io.github.imacrazyguy412.we.games.util.Hand;

/**
 * PokerHandEvaluator
 */
public class PokerHandEvaluator {

  /**
   * Get the strength of the hand
   * <p>
   * This method returns the strength of a hand. The strength is represented
   * as an {@code int} that contains the information of how strong the hand is
   * 
   * <p>
   * 
   * These methods for checking hand strength are split among 9 extra
   * methods (10 total) to check for each type of hand.
   * If the type of hand isn't there (check2Pair() is called with a hand
   * that does not have two pairs), it returns -1.
   * Otherwise, it returns a number based on the strength of them as below:
   * 
   * <hr>
   * 
   * <b>high card:</b> the card's face value (aces are 14)
   * <p>
   * <b>high card range:</b> [2, 14]
   * <p>
   * {@link #checkHighCard(Hand)}
   * 
   * <hr>
   * 
   * <b>pair:</b> the card's face value + 14
   * <p>
   * <b>pair range:</b> [16, 28]
   * <p>
   * {@link #checkPair(Hand)}
   * 
   * <hr>
   * 
   * <b>2pair:</b> a number in the hundreds or thousands. the first two digits
   *  (the thousands and hundreds places) store the strength of the
   *  HIGHER strength pair. The tens and ones place store the strength of
   *  the lower stregth pair, in the same value that high cards are stored
   *  (aces as 14). For example, a 2pair jacks and 4s would be returned as
   *  1104
   * <p>
   * <b>2pair range:</b> [0302, 1413]
   * <p>
   * {@link #check2Pair(Hand)}
   * 
   * <hr>
   * 
   * <b>3 of a kind:</b> The face value of the card multiplied by 1000
   * <p>
   * <b>3 of a kind range:</b> [02000, 14000]
   * <p>
   * {@link #checkTrips(Hand)}
   * 
   * <hr>
   * 
   * <b>straight:</b> Probably the highest card's value multiplied by 1000 + 14000
   * <p>
   * <b>straight range:</b> [20000, 28000]
   * <p>
   * {@link #checkStraight(Hand)}
   * 
   * <hr>
   * 
   * <b>flush:</b> Probably the highest card's value multiplied by 1000 + 28000 +
   *  the suit (so the program can know when saying what the player won
   *  with). The flush value shouldnt affect the game, because their shouldnt
   *  be able to be more than one suit of flush in one game
   * <p>
   * <b>flush range:</b> [060001, 42004]
   * <p>
   * {@link #checkFlush(Hand)}
   * 
   * <hr>
   * 
   * <b>full house:</b> Likely will be similar to 2pair, but instead of the
   *  higher pair its the trips and instead of multiplying by 100, its
   *  multiplied by 100000 + the value of the pair
   * <p>
   * <b>full house range:</b> [0200003, 1400013]
   * <p>
   * {@link #checkFullHouse(Hand)}
   * 
   * <hr>
   * 
   * <b>four of a kind:</b> the value of the cards multiplied by 1000000
   * <p>
   * <b>four of a kind range:</b> [02000000, 14000000]
   * <p>
   * {@link #checkFourOfAKind(Hand)}
   * 
   * <hr>
   * 
   * <b>straight flush:</b> Probablt highest card's value multiplied by 10000000
   *  + suit
   * <p>
   * <b>straight flush range:</b> [060000001, 140000004]
   * <p>
   * {@link #checkStraightFlush(Hand)}
   * 
   * <hr>
   * 
   * <b>royal flush:</b> The suit of the royal flush + 1500000000
   * <p>
   * <b>royal flush range:</b> [1500000001, 1500000004]
   * <p>
   * {@link #checkRoyalFlush(Hand)}
   * 
   * <hr>
   * 
   * The basic goal of the methods of storing the values of hands is that
   * the stronger hand will always return a higher number, making comparisons
   * easier
   * @deprecated
   * evaluation of a hand is now represented by {@link PokerHandEvaluation} to be more
   * comprehensive. All of the methods related are deprecated.
   * @param h -- the hand to find the strength of
   * @return the strength of the hand
   * @see #checkHighCard(Hand)
   * @see #checkPair(Hand)
   * @see #check2Pair(Hand)
   * @see #checkTrips(Hand)
   * @see #checkStraight(Hand)
   * @see #checkFlush(Hand)
   * @see #checkFullHouse(Hand)
   * @see #checkFourOfAKind(Hand)
   * @see #checkStraightFlush(Hand)
   * @see #checkRoyalFlush(Hand)
   */
  @Deprecated
  public static int getHandStrength(Hand h) {
    int handStrength = -1;

    return handStrength;
  }

  /**
   * returns the index of the card with the highest value
   * @param hand -- the hand to check in
   * @return the highest card's index
   */
  public static int highCard(Hand hand){
    int highCard = 0;
    int max = -1;
    for(int i = 0; i < hand.size(); i++){
      if(hand.get(i).rank > max){
        highCard = i;
        max = hand.get(i).rank;
      }
    }
    return highCard;
  }

  /**
   * Check the various sets of same-rank cards
   * <p>
   * Checks for
   * <ul>
   *  <li>pairs</li>
   *  <li>two pair</li>
   *  <li>3 of a kind</li>
   *  <li>full house</li>
   *  <li>four of a kind</li>
   * </ul>
   * <hr>
   * @param hand -- the hand to check
   * @return a {@link PokerHandEvaluation} with the best of the above sets, 
   * or {@code null} if no set is found
   * @see PokerHandEvaluation
   */
  public static PokerHandEvaluation sets(Hand hand){

    ArrayList<Hand> sets = new ArrayList<Hand>();

    Hand highSet = new Hand(); // the highest set present

    int highSetFace = -1;
    for(int first = 0; first < hand.size(); first++){

      boolean addedFirst = false;
      Card firstCard = hand.get(first);

      for(int sec = first + 1; sec < hand.size(); sec++){
        Card secCard = hand.get(sec);

        if(firstCard.rank == secCard.rank){
    
          if(!addedFirst){
            ArrayList<Hand> set = new ArrayList<Hand>();
            set.add(null);
            addedFirst = true;
          }

          if(firstCard.rank > highSetFace){
            highSetFace = firstCard.rank;

            highSet.clear();
            highSet.add(firstCard); highSet.add(secCard);
          }
        }
      }
    }

    return null;
  }

  /**
   * gets the highest card in the hand
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the highest card
   */
  @Deprecated
  private static int checkHighCard(Hand h) {
    // int highCard = -1;
    // for(int i = 0; i < h.size(); i++){
    // if(h.get(i).getFace() > highCard){
    // //checks through the hand for the highest card
    // highCard = h.get(i).getFace();
    // }
    // }
    int highCard = h.get(0).rank; // assuming the hand is sorted
    return highCard;
  }

  /**
   * check pairs
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkPair(Hand h) {
    int pair = -1;

    for (int first = 0; first < h.size() - 1; first++) {
      for (int sec = first + 1; sec < h.size(); sec++) {
        // check if it is a pair
        if (h.get(first).rank == h.get(sec).rank) {
          pair = Math.max(h.get(first).rank, pair); // always get the highest pair
        }
      }
    }

    return pair;
  }

  /**
   * check 2pairs
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int check2Pair(Hand h) {
    int tPair = -1;
    int firstFirst = -1, secFirst = -1;

    // high pair
    for (int first = 0; first < h.size(); first++) {
      for (int sec = first + 1; sec < h.size(); sec++) {
        if (h.get(first).rank == h.get(sec).rank
            && firstFirst != -1
            && h.get(first).rank > h.get(firstFirst).rank) {
          firstFirst = first;
          secFirst = sec;
        }
      }
    }

    // low pair
    for (int first = 0; first < h.size(); first++) {
      for (int sec = first + 1; sec < h.size(); sec++) {
        if (first == firstFirst)
          break; // go to next iteration of first
        if (sec == secFirst)
          continue; // go to next iteration of sec

        if (h.get(first).rank == h.get(sec).rank) {
          tPair = Math.max(100 * h.get(firstFirst).rank + h.get(first).rank,
              100 * h.get(first).rank + h.get(firstFirst).rank);
        }
      }
    }

    return tPair;
  }

  /**
   * check 3 of a kind
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkTrips(Hand h) {
    int trips = -1;

    for (int first = 0; first < h.size() - 2; first++) {
      for (int sec = first + 1; sec < h.size() - 1; sec++) {
        for (int third = sec + 1; third < h.size(); third++) {
          if (h.get(first).rank == h.get(sec).rank
              && h.get(first).rank == h.get(third).rank) {
            trips = Math.max(trips, h.get(first).rank);
          }
        }
      }
    }

    return trips;
  }

  /**
   * check straights
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkStraight(Hand h) {
    int straight = -1;

    if (h.get(0).rank == 14) {

    }

    int count = 1;
    for (int i = 1; i < h.size(); i++) {
      if (h.get(i).rank == h.get(i - 1).rank - 1) {
        count++;
        if (count >= 5) {
          straight = Math.max(straight, h.get(i - 4).rank * 1000 + 14000);
        }
      } else if (h.get(i).rank == h.get(i - 1).rank) {
        // nothing I guess
        continue;
      } else {
        count = 1;
      }
    }

    return straight;
  }

  /**
   * check flushs
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkFlush(Hand h) {
    int flush = -1;
    int suit = -1;
    int[] numSuit = { 0, 0, 0, 0 };
    // 0 is clubs, 1 is diamonds, 2 is hearts, 3 is spades

    for (int i = 0; i < h.size(); i++) {
      numSuit[h.get(i).suit - 1]++;
      // System.out.println(h.get(i).getSuit()-1);
    }
    // checks through the hand to check if there even are five cards of the
    // same suit

    if (numSuit[3] >= 5) {
      suit = 4;
    } else if (numSuit[2] >= 5) {
      suit = 3;
    } else if (numSuit[1] >= 5) {
      suit = 2;
    } else if (numSuit[0] >= 5) {
      suit = 1;
    }
    // stores the suit of which cards have the same suit

    for (int i = 0; i < h.size(); i++) {
      if (h.get(i).suit == suit && h.get(i).rank > flush) {
        flush = h.get(i).rank;
      }
    }
    // checks through the cards for the highest card of the suit of the flush

    if (flush != -1) {
      flush = flush * 1000 + 28000 + suit;
    }

    return flush;
  }

  /**
   * check full house
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkFullHouse(Hand h) {
    int fullHouse = -1;
    int p = -1, q = -1, r = -1, pair = -1, trips = -1;

    for (int i = 0; i < h.size(); i++) {
      for (int j = 0; j < h.size(); j++) {
        for (int k = 0; k < h.size(); k++) {
          if (i != j && i != k && j != k
          // checks to make sure all cards are unique
              && h.get(i).rank == h.get(j).rank &&
              h.get(i).rank == h.get(k).rank
          // checks if both of the other cards have the same value as
          ) {
            if (h.get(i).rank > trips) {
              trips = h.get(i).rank;
              p = i;
              q = j;
              r = k;
            }
          }
        }
      }
    }
    // should set p and q to the index values of the two cards of the
    // highest pair
    // System.out.println("testing: " + trips);

    for (int i = 0; i < h.size(); i++) {
      for (int j = 0; j < h.size(); j++) {
        if (i != j && i != p && i != q && i != r && j != p && j != q && j != r
        // checks to make sure that all of the cards are different
        // indexes in the hand
            && h.get(i).rank == h.get(j).rank
        // checks the cards have the same face value
        ) {
          if (h.get(i).rank > pair) {
            pair = h.get(i).rank;
          }
        }
      }
    }

    // System.out.println("testing: " + pair);

    if (trips != -1 && pair != -1) {
      fullHouse = trips * 100000 + pair;
    }

    return fullHouse;
  }

  /**
   * check four of a kind
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkFourOfAKind(Hand h) {
    int fours = -1;

    for (int i = 0; i < h.size(); i++) {
      for (int j = 0; j < h.size(); j++) {
        for (int k = 0; k < h.size(); k++) {
          for (int l = 0; l < h.size(); l++) {
            if (i != j && i != k && i != l &&
                j != k && j != l &&
                k != l
            // checks that all of the cards are unique
            ) {
              if (h.get(i).rank == h.get(j).rank &&
                  h.get(i).rank == h.get(k).rank &&
                  h.get(i).rank == h.get(l).rank
              // checks that all of the cards are the same face
              ) {
                fours = h.get(i).rank * 1000000;
              }
            }
          }
        }
      }
    }
    return fours;
  }

  /**
   * check straight flush
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkStraightFlush(Hand h) {
    int straightFlush = -1;

    // I don't know what to put here

    return straightFlush;
  }

  /**
   * check ryoal flush
   * @deprecated see {@link #getHandStrength(Hand)} for why
   * @param h the hand
   * @return the evaluation
   */
  @Deprecated
  private static int checkRoyalFlush(Hand h) {
    int royalFlush = -1;

    // I don't know what to put here

    return royalFlush;
  }
}