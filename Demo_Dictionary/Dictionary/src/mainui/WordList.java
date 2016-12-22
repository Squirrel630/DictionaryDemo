package mainui;

import java.awt.Point;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import config.WordListConfig;
import util.DataFactory;
import util.UIFactory;

public class WordList extends JScrollPane{
	
	private static final long serialVersionUID = -733435356073881138L;
	
	private JList<String> wordList;
	//private JList wordList = new JList();
	
	
	public WordList() {
		setBounds(WordListConfig.LOC_X, WordListConfig.LOC_Y, WordListConfig.WIDTH, WordListConfig.HEIGHT);
		initDictionary();
	}
	
	private void initDictionary() {
		wordList = new JList<>();
		wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Iterator<String> dictionary = DataFactory.getDataService("txt").readDictionary();
		DefaultListModel<String> model = new DefaultListModel<>();
		while (dictionary.hasNext()){
			//System.out.println("Own");
			model.addElement(dictionary.next());
			};
		wordList.setModel(model);
		add(wordList);
		setViewportView(wordList);
		wordList.addListSelectionListener(new WordSelected());
		
	}
	
	public void reDictionary(String word) {
		wordList = new JList<>();
		wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Iterator<String> dictionary = DataFactory.getDataService().lenovo(word);
		DefaultListModel<String> model = new DefaultListModel<>();
		if(!dictionary.hasNext()){
			dictionary=DataFactory.getDataService().lenovo_list(word);
//			Set<String> sed=DataFactory.getDataService().lenovo_list(word);
		}
		while (dictionary.hasNext()){
			model.addElement(dictionary.next());
			};
		wordList.setModel(model);
		add(wordList);
		setViewportView(wordList);
		wordList.addListSelectionListener(new WordSelected());
	}

	public void receiveQueryResult(int location) {
		wordList.setSelectedIndex(location);
		Point point = wordList.indexToLocation(location);
		getVerticalScrollBar().setValue(point.y);
	}
	
	private class WordSelected implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			//Update Word Content
			UIFactory.getWordContentInstance().updateContent(
					DataFactory.getDataService().query
					(WordList.this.wordList.getSelectedValue())
			);
		}
		
	}
	
}
