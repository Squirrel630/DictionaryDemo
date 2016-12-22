package mainui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import config.QueryButtonConfig;
import po.WordInfo;
import util.DataFactory;
import util.UIFactory;

public class QueryButton extends JButton implements ActionListener{

	private static final long serialVersionUID = -6847227296705939178L;

	public QueryButton() {
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBounds(QueryButtonConfig.LOC_X,QueryButtonConfig.LOC_Y,QueryButtonConfig.WIDTH,QueryButtonConfig.HEIGHT);
		setText("Query");
		setToolTipText("Query the word in dictionary");
		addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		String item = (String)UIFactory.getWordInputFieldInstance().getSelectedItem();
		if(item.equals("")) {
			;//System.out.println("NothingInput");
		} 
		else {
			String word = (String) UIFactory.getWordInputFieldInstance().getWord();
			WordInfo queryresult;
			queryresult=DataFactory.getDataService().query(word);
			if(queryresult!=null){
			UIFactory.getWordContentInstance().updateContent(queryresult);
			}
			else{
				queryresult=new WordInfo(word,"not found");
				UIFactory.getWordContentInstance().updateContent(queryresult);
			}
			UIFactory.getWordListInstance().reDictionary(word);;
		//	int location = 5;
		//	UIFactory.getWordListInstance().receiveQueryResult(location);
		}
	}
	
}
