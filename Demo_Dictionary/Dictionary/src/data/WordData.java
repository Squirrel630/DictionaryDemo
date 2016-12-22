package data;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

//import Dictionary.ReadDictionary;
import dataservice.WordDataService;

public class WordData {

	public Iterator<String> WordData(String word) {
		List<String> rea=ReadDictionary(word );
		return rea.iterator();
	}
	
	public List<String> ReadDictionary(String searchword){
		Trie word_tire=new Trie();
		@SuppressWarnings({ "resource", "null" })

		Scanner input=null;
		try{
				input=new Scanner(new File("data/dictionary"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		while(input.hasNext()){
			String line=input.nextLine();
			String[] lineArr = line.split("\\t"); 
			String word=lineArr[1];
			String interpretation=lineArr[2];
			for(int i=3;i<lineArr.length;i++){
			interpretation=interpretation+lineArr[i];
			}
			word_tire.insert(word,interpretation);
			}
		List<String> li=word_tire.prefixSearchWord(searchword);
		return li;
	}

}
