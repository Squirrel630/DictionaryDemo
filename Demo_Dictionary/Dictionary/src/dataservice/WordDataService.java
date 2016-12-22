package dataservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import data.BKTree;
import data.LevensteinDistance;
import data.MetricSpace;
import data.Trie;
import po.WordInfo;


public abstract class  WordDataService{
	
	protected final static Trie WORD_TIRE;
	protected final static BKTree<String> WORK_BK;
	protected final static double RADIUS=3;
	/**
	 * The initial of dictionary
	 */
	static {
		MetricSpace<String> ms = new LevensteinDistance();
		WORK_BK = new BKTree<String>(ms);
	    
		WORD_TIRE = new Trie();
		Scanner input = null;
		try {
			input = new Scanner(new File("data/dictionary"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		};
		
		while(input.hasNext()){
			String line=input.nextLine();
			String[] lineArr = line.split("\\t"); 
			String word=lineArr[1];
			String interpretation=lineArr[2];
			for(int i=3;i<lineArr.length;i++){
			interpretation=interpretation+lineArr[i];
			}
			WORD_TIRE.insert(word,interpretation);
			WORK_BK.put(word);
			}
	}
	
	public abstract Iterator<String> lenovo(String word);
	public abstract Iterator<String> readDictionary();
	public abstract Iterator<String> lenovo_list(String word);
	public abstract WordInfo query(String word);
	public abstract String description(String word);
}
