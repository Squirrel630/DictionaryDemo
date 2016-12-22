package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import po.WordInfo;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 单词Trie树
 * </p>
 * 
 * @createDate：2013-10-17
 * @author
 * @version 1.0
 */
public class Trie {
	class TrieNode {
		int count = 0;//trie tree word count
		int prefixCount = 0;//trie tree prefix count
		TrieNode[] next ;//指向各个子树的指针,存储26个字母[a-z]&其他字符
		int nodeState = 0;//当前TrieNode状态 ,默认 0 , 1表示从根节点到当前节点的路径表示一个词,即叶子节点
		String word;
		String interpretation;
		TrieNode() {
			count = 0;
			prefixCount = 0;
			next = new TrieNode[27];
			nodeState = 0;
		}
	}

	/** trie树根 */
	private TrieNode root = new TrieNode();

	/** 英文字符串正则匹配 */
	static String englishPattern = "^[A-Za-z]+$";
	/** 中文正则匹配 */
	static String chinesePattern = "[\u4e00-\u9fa5]";

	static int ARRAY_LENGTH = 27;

	static String zeroString = "";

	/**
	 * 插入字串，用循环代替迭代实现
	 * 
	 * @param words
	 */
	public void insert(String word,String interpretation) {
		insert(this.root, word,interpretation);
	}

	/**
	 * 插入字串，用循环代替迭代实现 
	 * 
	 * @param root
	 * @param words
	 */
	private void insert(TrieNode root, String word,String interpretation) {
		word = word.toLowerCase();// //转化为小写
		char[] chrs = word.toCharArray();

		for (int i = 0; i < chrs.length; i++) {
			int index=chrs[i] - 'a';
			if(index>25||index<0)
				index=26;
			if (root.next[index] != null) {
					root.next[index].prefixCount++;
				} 
			else {
					root.next[index] = new TrieNode();
					root.next[index].prefixCount++;
				}
				// /如果到了字串结尾，则做标记
			if (i == chrs.length - 1) {
					root.next[index].nodeState = 1;
					root.next[index].count++;
					root.next[index].interpretation= interpretation;
					root.next[index].word=word;

				}
				// /root指向子节点，继续处理
				root = root.next[index];
			
		}

	}

	public 	WordInfo getQuery(String word) {
		//getDescription(word)
		if (word == null || "".equals(word.trim())) {
			return null;
		}
//		if (!word.matches(englishPattern)) {
//			return null;
//		}
		char c = word.charAt(0);
		c = Character.toLowerCase(c);
		int index = c - 'a';
		if(index>25||index<0)
			index=26;
		if (root.next != null && root.next[index] != null) {
			return getdepthSearch(root.next[index],word.substring(1),  word);
		} else {
			return null;
			//return new ArrayList<String>();
		}
	}
	
	private WordInfo getdepthSearch(TrieNode node,String word, String inputWord) {
		if (node.nodeState == 1 && word.length() == 0) {
			//list.add(node.word);
			WordInfo getword=new WordInfo(node.word,node.interpretation);
		//	System.out.println(getword.getWord()+" "+getword.getDescrption());
			return getword;
		//	return ;
		}
		if (word.length() != 0) {
			char c = word.charAt(0);
			c = Character.toLowerCase(c);
			int index = c - 'a';
			if(index>25||index<0)
				index=26;
			// 继续完全匹配,直到word为空串,否则未找到
			if (node.next[index] != null) {
				return getdepthSearch(node.next[index],  word.substring(1),inputWord);
			}
		} else {
			if (node.prefixCount > 0) {// 若匹配单词结束,但是trie中的单词并没有完全找到,需继续找到trie中的单词结束.
				// node.prefixCount>0表示trie中的单词还未结束
				for (int i = 0; i < ARRAY_LENGTH; i++) {
					if (node.next[i] != null) {
						getdepthSearch(node.next[i], zeroString, inputWord);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: prefixSearchWord
	 * @Description: 前缀搜索
	 * @param @param word
	 * @param @return
	 * @return List<String>
	 * @throws
	 */
	public List<String> prefixSearchWord(String word) {
		if (word == null || "".equals(word.trim())) {
			return new ArrayList<String>();
		}
		if (!word.matches(englishPattern)) {
			return new ArrayList<String>();
		}
		char c = word.charAt(0);
		c = Character.toLowerCase(c);
		int index = c - 'a';
		if(index>25||index<0)
			index=26;
		if (root.next != null && root.next[index] != null) {
			return depthSearch(root.next[index], new ArrayList<String>(),
					word.substring(1),  word);
		} else {
			return new ArrayList<String>();
		}
	}


	/**
	 * 
	 * @Title: depthSearch
	 * @Description: 深度遍历子树
	 * @param @param node
	 * @param @param list 保存搜索到的字符串
	 * @param @param word 搜索的单词.匹配到第一个则减去一个第一个,连续匹配,直到word为空串.若没有连续匹配,则恢复到原串。
	 * @param @param matchedWord 匹配到的单词
	 * @param @return
	 * @return List<String>
	 * @throws
	 */
	private List<String> depthSearch(TrieNode node, List<String> list,
			String word, String inputWord) {
		if (node.nodeState == 1 && word.length() == 0) {
			list.add(node.word);
		}
		if (word.length() != 0) {
			char c = word.charAt(0);
			c = Character.toLowerCase(c);
			int index = c - 'a';
			if(index>25||index<0)
				index=26;
			// 继续完全匹配,直到word为空串,否则未找到
			if (node.next[index] != null) {
				depthSearch(node.next[index], list, word.substring(1),inputWord);
			}
		} else {
			if (node.prefixCount > 0) {// 若匹配单词结束,但是trie中的单词并没有完全找到,需继续找到trie中的单词结束.
				// node.prefixCount>0表示trie中的单词还未结束
				for (int i = 0; i < ARRAY_LENGTH; i++) {
					if (node.next[i] != null) {
						depthSearch(node.next[i], list, zeroString, inputWord);
					}
				}
			}
		}
		return list;
	}

}

