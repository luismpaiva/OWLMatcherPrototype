package uninova.cts.arrowhead.owlmatcher.utils;

import java.awt.TextArea;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class TextAreaOutputStream extends OutputStream {
	
	private final JTextArea textArea;
	private StringBuilder sb = new StringBuilder();
	String title;
	private int counter = 0;

	public TextAreaOutputStream(final JTextArea textArea, String title){
		super();
		this.textArea = textArea;
		this.title = title;
		sb.append(title + "> ");
		counter = 0;
		System.out.println("TextAreaOutputStream created!");
	}
	
//	public TextAreaOutputStream(final JTextPane textPane, String title){}

	@Override
	public void write(int b) throws IOException {
		if (b == '\r')
			return;
		
		if (b=='\n'){
			final String text = sb.toString() + "\n";
			System.out.println("Text before is: "+text);
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("The current text is: "+text);
					System.out.println("<"+counter+">getting text"+textArea.getText());
					textArea.append(text);
//					textArea.setText(text+"\n");
					counter++;

				}
			});
			
			return;
		}

		sb.append((char) b);

	}
	
//	@Override
//	public void flush(){}

}
