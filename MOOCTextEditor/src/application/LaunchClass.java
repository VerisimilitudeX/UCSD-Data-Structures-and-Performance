package application;

import java.util.Random;

public class LaunchClass {

	public String dictFile = "data/dict.txt";

	public LaunchClass() {
		super();
	}

	public document.Document getDocument(final String text) {
		// Change this to BasicDocument(text) for week 1 only
		return new document.EfficientDocument(text);
	}

	public textgen.MarkovTextGenerator getMTG() {
		return new textgen.MarkovTextGeneratorLoL(new Random());
	}

	public spelling.WordPath getWordPath() {
		return new spelling.WPTree();
	}

	public spelling.AutoComplete getAutoComplete() {
		final spelling.AutoCompleteDictionaryTrie tr = new spelling.AutoCompleteDictionaryTrie();
		spelling.DictionaryLoader.loadDictionary(tr, dictFile);
		return tr;
	}

	public spelling.Dictionary getDictionary() {
		final spelling.Dictionary d = new spelling.DictionaryBST();
		spelling.DictionaryLoader.loadDictionary(d, dictFile);
		return d;
	}

	public spelling.SpellingSuggest getSpellingSuggest(final spelling.Dictionary dic) {
		// return new spelling.SpellingSuggestNW(new spelling.NearbyWords(dic));
		return new spelling.NearbyWords(dic);

	}
}
