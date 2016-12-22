package runner;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.title.FlatTitlePainter;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import util.DataFactory;
import util.UIFactory;

public class DictionaryRunner {
	
	public DictionaryRunner() {
		this.run();
	}
	
	private void run() {
		/*
		 * A initial of dictionary , 
		 * make it load first,
		 * then, it will be much faster
		 */
		DataFactory.getDataService().readDictionary();
		UIFactory.getMainFrameInstance();
	}
	
	public static void main(String[] args){
		 try {
			 UIManager.setLookAndFeel(new SubstanceLookAndFeel());
	            JFrame.setDefaultLookAndFeelDecorated(true);
	            JDialog.setDefaultLookAndFeelDecorated(true);
	            SubstanceLookAndFeel.setCurrentTheme(new SubstanceTerracottaTheme());
	          SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
	          SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
	            SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
	            SubstanceLookAndFeel.setCurrentTitlePainter(FlatTitlePainter.DISPLAY_NAME);
	        } catch (Exception e) {
	            System.err.println("Something went wrong!");
	        }
		new DictionaryRunner();
	}
	
}
