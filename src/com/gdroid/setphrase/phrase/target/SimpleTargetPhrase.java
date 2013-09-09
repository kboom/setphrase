package com.gdroid.setphrase.phrase.target;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SimplePhrase;
import com.gdroid.setphrase.phrase.SpeechPart;

/**
 * 
 * @author kboom
 */
public class SimpleTargetPhrase implements TargetPhrase {

	// needed for serialization
	private static final long serialVersionUID = 1L;

	private transient Phrase phrase;

	private int id;
	private SpeechPart partOfSpeech;
	private ArrayList<Phrase> synonymList;
	private String definition;

	public SimpleTargetPhrase(Phrase phrase, SpeechPart part) {
		this.phrase = phrase;
		partOfSpeech = part;
	}

	@Override
	public List<Phrase> getSynonymList() {
		if (synonymList == null)
			synonymList = new ArrayList<Phrase>();

		return synonymList;
	}

	/**
	 * Adds multiple synonyms to this phrase.
	 * 
	 * @param synonyms
	 */
	public void addSynonym(List<Phrase> synonymList) {
		getSynonymList().addAll(synonymList);
	}

	/**
	 * Adds single synonym to this phrase.
	 * 
	 * @param synonyms
	 */
	public void addSynonym(Phrase synonym) {
		getSynonymList().add(synonym);
	}

	/**
	 * Adds definition to this phrase.
	 * 
	 * @param definition
	 */
	@Override
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public String getPhrase() {
		return phrase.getPhrase();
	}

	@Override
	public SpeechPart getType() {
		return partOfSpeech;
	}

	@Override
	public String getSynonyms() {
		if (synonymList == null)
			return "-none-";

		String synonyms = "";
		for (int i = 0; i < synonymList.size(); i++) {
			synonyms += synonymList.get(i).getPhrase();
			if (i < synonymList.size() - 1)
				synonyms += ", ";
		}

		return synonyms;
	}

	@Override
	public String getDefinition() {
		if (definition == null)
			return "-none-";
		else
			return definition;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeInt(id);
		oos.writeObject(phrase.getPhrase());
		oos.writeObject(partOfSpeech);
		oos.writeObject(synonymList);
		oos.writeObject(definition);
	}

	private void readObject(ObjectInputStream ois) throws IOException,
			ClassNotFoundException {
		ois.defaultReadObject();
		id = ois.readInt();
		phrase = new SimplePhrase((String) ois.readObject());
		partOfSpeech = (SpeechPart) ois.readObject();
		synonymList = (ArrayList<Phrase>) ois.readObject();
		definition = (String) ois.readObject();
	}

	@Override
	public int getID() {
		return id;
	}

}
