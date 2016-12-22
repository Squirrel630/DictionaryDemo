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
 * Description: ����Trie��
 * </p>
 * 
 * @createDate��2013-10-17
 * @author
 * @version 1.0
 */
public class Trie {
	class TrieNode {
		int count = 0;//trie tree word count
		int prefixCount = 0;//trie tree prefix count
		TrieNode[] next ;//ָ�����������ָ��,�洢26����ĸ[a-z]&�����ַ�
		int nodeState = 0;//��ǰTrieNode״̬ ,Ĭ�� 0 , 1��ʾ�Ӹ��ڵ㵽��ǰ�ڵ��·����ʾһ����,��Ҷ�ӽڵ�
		String word;
		String interpretation;
		TrieNode() {
			count = 0;
			prefixCount = 0;
			next = new TrieNode[27];
			nodeState = 0;
		}
	}

	/** trie���� */
	private TrieNode root = new TrieNode();

	/** Ӣ���ַ�������ƥ�� */
	static String englishPattern = "^[A-Za-z]+$";
	/** ��������ƥ�� */
	static String chinesePattern = "[\u4e00-\u9fa5]";

	static int ARRAY_LENGTH = 27;

	static String zeroString = "";

	/**
	 * �����ִ�����ѭ���������ʵ��
	 * 
	 * @param words
	 */
	public void insert(String word,String interpretation) {
		insert(this.root, word,interpretation);
	}

	/**
	 * �����ִ�����ѭ���������ʵ�� 
	 * 
	 * @param root
	 * @param words
	 */
	private void insert(TrieNode root, String word,String interpretation) {
		word = word.toLowerCase();// //ת��ΪСд
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
				// /��������ִ���β���������
			if (i == chrs.length - 1) {
					root.next[index].nodeState = 1;
					root.next[index].count++;
					root.next[index].interpretation= interpretation;
					root.next[index].word=word;

				}
				// /rootָ���ӽڵ㣬��������
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
			// ������ȫƥ��,ֱ��wordΪ�մ�,����δ�ҵ�
			if (node.next[index] != null) {
				return getdepthSearch(node.next[index],  word.substring(1),inputWord);
			}
		} else {
			if (node.prefixCount > 0) {// ��ƥ�䵥�ʽ���,����trie�еĵ��ʲ�û����ȫ�ҵ�,������ҵ�trie�еĵ��ʽ���.
				// node.prefixCount>0��ʾtrie�еĵ��ʻ�δ����
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
	 * @Description: ǰ׺����
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
	 * @Description: ��ȱ�������
	 * @param @param node
	 * @param @param list �������������ַ���
	 * @param @param word �����ĵ���.ƥ�䵽��һ�����ȥһ����һ��,����ƥ��,ֱ��wordΪ�մ�.��û������ƥ��,��ָ���ԭ����
	 * @param @param matchedWord ƥ�䵽�ĵ���
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
			// ������ȫƥ��,ֱ��wordΪ�մ�,����δ�ҵ�
			if (node.next[index] != null) {
				depthSearch(node.next[index], list, word.substring(1),inputWord);
			}
		} else {
			if (node.prefixCount > 0) {// ��ƥ�䵥�ʽ���,����trie�еĵ��ʲ�û����ȫ�ҵ�,������ҵ�trie�еĵ��ʽ���.
				// node.prefixCount>0��ʾtrie�еĵ��ʻ�δ����
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

