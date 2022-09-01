package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private final List<ListNode> wordList;

	// The starting "word"
	private final String starter;

	// The random number generator
	private final Random rnGenerator;

	public MarkovTextGeneratorLoL(final Random generator) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}

	/** Train the generator by adding the sourceText */
	@Override
	public void train(final String sourceText) {
		// TODO: Implement this method
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(final int numWords) {
		// TODO: Implement this method
		return null;
	}

	// Can be helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (final ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(final String sourceText) {
		// TODO: Implement this method.
	}

	// TODO: Add any private helper methods you need here.

	/**
	 * This is a minimal set of tests. Note that it can be difficult
	 * to test methods/classes with randomized behavior.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		final MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		final String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		final String textString2 = "You say yes, I say no, " +
				"You say stop, and I say go, go, go, " +
				"Oh no. You say goodbye and I say hello, hello, hello, " +
				"I don't know why you say goodbye, I say hello, hello, hello, " +
				"I don't know why you say goodbye, I say hello. " +
				"I say high, you say low, " +
				"You say why, and I say I don't know. " +
				"Oh no. " +
				"You say goodbye and I say hello, hello, hello. " +
				"I don't know why you say goodbye, I say hello, hello, hello, " +
				"I don't know why you say goodbye, I say hello. " +
				"Why, why, why, why, why, why, " +
				"Do you say goodbye. " +
				"Oh no. " +
				"You say goodbye and I say hello, hello, hello. " +
				"I don't know why you say goodbye, I say hello, hello, hello, " +
				"I don't know why you say goodbye, I say hello. " +
				"You say yes, I say no, " +
				"You say stop and I say go, go, go. " +
				"Oh, oh no. " +
				"You say goodbye and I say hello, hello, hello. " +
				"I don't know why you say goodbye, I say hello, hello, hello, " +
				"I don't know why you say goodbye, I say hello, hello, hello, " +
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
	// The word that is linking to the next words
	private final String word;

	// The next words that could follow it
	private final List<String> nextWords;

	ListNode(final String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord() {
		return word;
	}

	public void addNextWord(final String nextWord) {
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(final Random generator) {
		// TODO: Implement this method
		// The random number generator should be passed from
		// the MarkovTextGeneratorLoL class
		return null;
	}

	public String toString() {
		String toReturn = word + ": ";
		for (final String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}
