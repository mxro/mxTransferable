package de.mxro.transferable;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import de.mxro.utils.log.UserError;



/**
 *  convienience class for clipboard operations 
 *  
 *  **/
public class ClipboardFacade {
	public static BufferedImage getImage() {
        final Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
    
        try {
            if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                final BufferedImage text = (BufferedImage)t.getTransferData(DataFlavor.imageFlavor);
                return text;
            }
        } catch (final UnsupportedFlavorException e) {
        	UserError.singelton.log(e);
        } catch (final IOException e) {
        	UserError.singelton.log(e);
        }
        return null;
    }
	
	
	  public static String getText() {
	   
	    final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    //odd: the Object param of getContents is not currently used
	    final Transferable contents = clipboard.getContents(null);
	    final boolean hasTransferableText =
	      (contents != null) &&
	      contents.isDataFlavorSupported(DataFlavor.stringFlavor);
	    
	    if ( hasTransferableText ) {
	      try {
	        return (String)contents.getTransferData(DataFlavor.stringFlavor);
	      }
	      catch (final UnsupportedFlavorException ex){
	        UserError.singelton.log(ex);
	      }
	      catch (final IOException ex) {
	    	  UserError.singelton.log(ex);
	      }
	    }
	    return null;
	  }
	  
	  public static void setText(String s) { 
		   final StringSelection stringSelection = new StringSelection( s);
		   final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		   clipboard.setContents(stringSelection, null );

	  }
}
