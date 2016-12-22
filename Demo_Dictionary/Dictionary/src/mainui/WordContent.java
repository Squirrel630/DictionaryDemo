package mainui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.WordContentConfig;
import po.WordInfo;
/**
 * WordContent : 
 * 		To show the information of word
 */
public class WordContent extends JPanel{
	
	private static final long serialVersionUID = -3000677005620925673L;
	
	private WordPanel translation;
	
	private JLabel img;
	
	public WordContent() {
		setLayout(null);
		setBounds(WordContentConfig.LOC_X, WordContentConfig.LOC_Y, WordContentConfig.WIDTH, WordContentConfig.HEIGHT);
		img = new JLabel(new ImageIcon("translation.png"));
		img.setBounds(0, 1, WordContentConfig.WIDTH, 50);
		add(img);
		translation = new WordPanel();
		add(translation);
	}
	
	public void updateContent(WordInfo info) {
		this.translation.updateContent(info.getWord(), info.getDescrption());
		repaint();
	}
	
	private class WordPanel extends JPanel{
		
		private static final long serialVersionUID = -7982365518141482173L;
		
		private JLabel wordName;
		private JLabel wordDescription;
		
		public WordPanel() {
			setBounds(0, 40, WordContentConfig.WIDTH, WordContentConfig.HEIGHT - 40);
			setLayout(null);
			wordName = new JLabel("");
			wordName.setBounds(20, 0, this.getWidth()-40, 50);
			wordDescription = new JLabel("");
			wordDescription.setBounds(10, 50, this.getWidth()-10, this.getHeight() - 50);
			wordDescription.setVerticalAlignment(JLabel.TOP);
			wordDescription.setBorder(BorderFactory.createTitledBorder("[ °Ù¶È´Êµä ]"));
//			wordDescription.setBackground(Color.BLUE);
			Font name = wordName.getFont();
			wordName.setFont(new Font(name.getFontName(),Font.BOLD,name.getSize() + 10));
			wordDescription.setForeground(Color.orange);
			add(wordName);
			add(wordDescription);
		}
		
		private void updateContent(WordInfo te) {
			// TODO Auto-generated method stub
		//	wordName=te.getWord();
		//	wordDescription=te.getDescrption();
		}

		public void updateContent(String name, String description) {
			wordName.setText(name);
			wordDescription.setText(description);
		}
		
	}

}
