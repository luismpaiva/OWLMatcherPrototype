package uninova.cts.arrowhead.owlmatcher.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TextPaneOutputStream extends OutputStream {

	private final JTextPane textPane;
	private final StringBuilder sb = new StringBuilder();
	String title;

	
	public TextPaneOutputStream(final JTextPane textArea, String title){
		super();
		this.textPane = textArea;
		this.title = title;
		sb.append(title + "> ");
	}
	
//	public TextAreaOutputStream(final JTextPane textPane, String title){}

	@Override
	public void write(int b) throws IOException {
		if (b == '\r')
			return;
		
		if (b=='\n'){
			final String text = sb.toString() + "\n";
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						Document doc = textPane.getDocument();
						doc.insertString(doc.getLength(), text,null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			});
			
			return;
		}
		
		sb.append((char) b);

	}
}
