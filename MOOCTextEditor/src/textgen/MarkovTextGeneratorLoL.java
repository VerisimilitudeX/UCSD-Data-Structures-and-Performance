package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

  // The list of words with their next words
  private List<ListNode> wordList;

  // The starting "word"
  private String starter;

  // The random number generator
  private Random rnGenerator;

  public MarkovTextGeneratorLoL(Random generator) {
    this.wordList = new LinkedList<ListNode>();
    this.starter = "";
    this.rnGenerator = generator;
  }

  /** Train the generator by adding the sourceText */
  @Override
  public void train(String sourceText) {
    // TODO: Implement this method
    if (this.wordList.size() == 0) {
      String[] words = sourceText.split(" +");
      // set "starter" to be the first word in the text
      this.starter = words[0];
      // set "prevWord" to be starter
      String prevWord = this.starter;

      int len = words.length;

      // for each word "w" in the source text starting at the second word
      for (int index = 1; index <= len; index++) {
        String w = (index == len) ? null : words[index];

        // check to see if "prevWord" is already a node in the list
        ListNode prevNode = checkNode(prevWord);
        // if "prevWord" is a node in the list
        if (prevNode != null) {
          // add "w" as a nextWord to the "prevWord" node
          prevNode.addNextWord(w);
        } else {
          prevNode = new ListNode(prevWord);
          // add "w" as a nextWord to the "prevWord" node
          prevNode.addNextWord(w);
          // add a node to the list with "prevWord" as the node's word
          this.wordList.add(prevNode);
        }

        // set "prevWord" = "w"
        prevWord = w;
      }

      // add starter to be a next word for the last word in the source text.
      String lastWord = words[len - 1];
      ListNode lastNode = checkNode(lastWord);
      lastNode.addNextWord(starter);
    }
  }

  /** Generate the number of words requested. */
  @Override
  public String generateText(int numWords) {
    // TODO: Implement this method
    String output = "";

    if (this.wordList.size() > 0 && numWords > 0) {
      String currWord = this.starter;
      int loop = 0;

      output += currWord + " ";

      while (loop < numWords - 1) {
        ListNode node = this.checkNode(currWord);
        String w = node.getRandomNextWord(this.rnGenerator);
        output += w + " ";
        currWord = w;
        loop++;
      }
    }

    return output;
  }

  /** Retrain the generator from scratch on the source text */
  @Override
  public void retrain(String sourceText) {
    // TODO: Implement this method.
    this.wordList = new LinkedList<ListNode>();
    this.train(sourceText);
  }

  // Can be helpful for debugging
  @Override
  public String toString() {
    String toReturn = "";

    for (ListNode n : this.wordList) toReturn += n.toString();

    return toReturn;
  }

  // TODO: Add any private helper methods you need here.
  private ListNode checkNode(String wordToCheck) {
    for (ListNode node : this.wordList) {
      String w = node.getWord();
      if (wordToCheck.equals(w)) return node;
    }

    return null;
  }

  /**
   * This is a minimal set of tests. Note that it can be difficult to test methods/classes with
   * randomized behavior.
   *
   * @param args
   */
  public static void main(String[] args) {
    // feed the generator a fixed random value for repeatable behavior
    MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
    String textString =
        "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";

    System.out.println(textString);

    gen.train(textString);
    System.out.println(gen);

    System.out.println(gen.generateText(20));

    String textString2 =
        "You say yes, I say no, "
            + "You say stop, and I say go, go, go, "
            + "Oh no. You say goodbye and I say hello, hello, hello, "
            + "I don't know why you say goodbye, I say hello, hello, hello, "
            + "I don't know why you say goodbye, I say hello. "
            + "I say high, you say low, "
            + "You say why, and I say I don't know. "
            + "Oh no. "
            + "You say goodbye and I say hello, hello, hello. "
            + "I don't know why you say goodbye, I say hello, hello, hello, "
            + "I don't know why you say goodbye, I say hello. "
            + "Why, why, why, why, why, why, "
            + "Do you say goodbye. "
            + "Oh no. "
            + "You say goodbye and I say hello, hello, hello. "
            + "I don't know why you say goodbye, I say hello, hello, hello, "
            + "I don't know why you say goodbye, I say hello. "
            + "You say yes, I say no, "
            + "You say stop and I say go, go, go. "
            + "Oh, oh no. "
            + "You say goodbye and I say hello, hello, hello. "
            + "I don't know why you say goodbye, I say hello, hello, hello, "
            + "I don't know why you say goodbye, I say hello, hello, hello, "
            + "I don't know why you say goodbye, I say hello, hello, hello,";

    System.out.println(textString2);

    gen.retrain(textString2);
    System.out.println(gen);

    System.out.println(gen.generateText(20));
  }
}

/** Links a word to the next words in the list You should use this class in your implementation. */
class ListNode {

  // The word that is linking to the next words
  private String word;

  // The next words that could follow it
  private List<String> nextWords;

  ListNode(String word) {
    this.word = word;
    this.nextWords = new LinkedList<String>();
  }

  public String getWord() {
    return word;
  }

  public void addNextWord(String nextWord) {
    if (nextWord != null) this.nextWords.add(nextWord);
  }

  public String getRandomNextWord(Random generator) {
    // TODO: Implement this method
    // The random number generator should be passed from
    // the MarkovTextGeneratorLoL class

    int min = 0, max = this.nextWords.size() - 1;

    int randomIdx = min + generator.nextInt((max - min) + 1);

    String result = this.nextWords.get(randomIdx);

    return result;
  }

  public String toString() {
    String toReturn = word + " : ";

    for (String s : this.nextWords) toReturn += s + " -> ";

    toReturn += "\n";
    return toReturn;
  }
}
