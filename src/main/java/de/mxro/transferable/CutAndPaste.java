package de.mxro.transferable;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;

import de.mxro.string.filter.Filter;

public class CutAndPaste {
	
	public static void insertUnformatted(String text, JEditorPane target) throws IOException, BadLocationException {
		//final int offset =target.getCaretPosition();
		
		//ByteArrayInputStream os=new ByteArrayInputStream();
		
		
		//new FileOutputStream(new java.io.File("/umlaut.txt")).write(text.getBytes());
		//System.out.println("paste1: _"+text+"_");
		text = Filter.regExReplace("\n", "mustbebr1417", 
				Filter.regExReplace("<p>", "", 
						Filter.regExReplace("</p>", "<br>", 
								Filter.regExReplace("š", "š", // don't ask why: this helps if pasting from pdf
								Filter.regExReplace("…", "…", 
								Filter.regExReplace("Š", "Š",
								Filter.regExReplace("€", "€",
								Filter.regExReplace("Ÿ", "Ÿ",
								Filter.regExReplace("†", "†",
								Filter.regExReplace("§", "§",
								Filter.identity)))))))))).perform(text);
		//System.out.println("paste2: _"+text+"_");
		//os.read(text.getBytes());
		CutAndPaste.deleteSelection(target);
		
		//((HTMLEditorKit) target.getEditorKit()).read(new ByteArrayInputStream(text.getBytes()), (HTMLDocument) target.getDocument(), offset);
		//target.getDocument().insertString(offset, text, null);
		final Caret caret = target.getCaret();
        int p0 = Math.min(caret.getDot(), caret.getMark());
        //int p1 = Math.max(caret.getDot(), caret.getMark());
        
        //((HTMLEditorKit) target.getEditorKit()).read(new ByteArrayInputStream(text.getBytes()), (HTMLDocument) target.getDocument(), p0);
        //((HTMLDocument) target.getDocument()).render(null);
        //((HTMLDocument) target.getDocument()).
		 ((HTMLDocument) target.getDocument()).insertString(p0, text, null);
		// target.getText().
		
		 // okay, it's a bit of a hack ...
		 String s=target.getText();
		while (s.contains("mustbebr1417")) {
			s = s.replace("mustbebr1417", "<br>");
			text = text.replace("mustbebr1417", "<br>");
			p0 = p0 - ("mustbebr1417").length()+("<br>").length();
		}
		 target.setText(s);
		//((HTMLEditorKit) TextItemPanel.this.jTextField.getEditorKit()).insertHTML((HTMLDocument) TextItemPanel.this.jTextField.getDocument(), offset, "<br>", 0, 0, HTML.Tag.BR);
		
		target.setCaretPosition(p0+text.length());
		
	}
	
	public static int deleteSelection(JEditorPane target)
    throws BadLocationException {
        final Document doc = target.getDocument();
        
        int start = -1;
        
            
        final Caret caret = target.getCaret();
        final int p0 = Math.min(caret.getDot(), caret.getMark());
        final int p1 = Math.max(caret.getDot(), caret.getMark());
        doc.remove(p0, p1 - p0);
        
        //replace selected text by the inserted one
        start = caret.getDot();
        doc.insertString(start, "", null);
        
        
        return start;
    }
}
