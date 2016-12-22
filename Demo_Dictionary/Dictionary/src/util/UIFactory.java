package util;

import mainui.MainFrame;
import mainui.QueryButton;
import mainui.WordContent;
import mainui.WordContent_ICIBA;
import mainui.WordContent_Youdao;
import mainui.WordInputField;
import mainui.WordList;

/**
 * 鐢变簬鎵�鏈塁omponent閮芥槸鍞竴鐨勶紝鍥犳涓嶅Θ鍏ㄩ儴閫氳繃Factory璁块棶锛屼互纭繚鍗曚竴瀵硅薄
 */
public class UIFactory {
	
	private static MainFrame frame;
	private static QueryButton queryButton;
	private static WordContent wordContent;
	private static WordContent_Youdao wordContent_Youdao;
	private static WordContent_ICIBA wordContent_ICIBA;
	private static WordInputField wordInputField;
	private static WordList wordList;
	
	public static MainFrame getMainFrameInstance() {
		if(frame == null) {
			frame = new MainFrame();
		}
		return frame;
	}
	
	public static QueryButton getQueryButtonInstance() {
		if(queryButton == null) {
			queryButton = new QueryButton();
		}
		return queryButton;
	}
	
	public static WordContent getWordContentInstance() {
		if(wordContent == null) {
			wordContent = new WordContent();
		}
		return wordContent;
	}
	
	
	public static WordContent_Youdao getWordContent_Youdao() {
		if(wordContent_Youdao == null) {
			wordContent_Youdao = new WordContent_Youdao();
		}
		return wordContent_Youdao;
	}
	
	public static WordContent_ICIBA getWordContent_ICIBA() {
		if(wordContent_ICIBA == null) {
			wordContent_ICIBA = new WordContent_ICIBA();
		}
		return wordContent_ICIBA;
	}
	
	
	public static WordInputField getWordInputFieldInstance() {
		if(wordInputField == null) {
			wordInputField = new WordInputField();
		}
		return wordInputField;
	}
	
	public static WordList getWordListInstance() {
		if(wordList == null) {
			wordList = new WordList();
		}
		return wordList;
	}
	
}
