/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.mxro.transferable;

import de.mxro.utils.FileHandler;
import de.mxro.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.LocalStorage;

/**
 *
 * @author mx
 */
public class LocalFileHandler implements FileHandler {

    private final LocalStorage storage;

    @Override
    public String uploadFile(File file) {
        OutputStream is = null;
        try {
            String tempName = "temp"+new Date().getTime() + "." + Utils.getExtension(file.getAbsolutePath());
            
                while (new java.io.File(storage.getDirectory().getAbsolutePath()+"/"+tempName).exists()) {
                    tempName = "temp"+new Date().getTime() + "." + Utils.getExtension(file.getAbsolutePath());
                }
            
            java.io.File newFile = new java.io.File(storage.getDirectory().getAbsolutePath()+"/"+tempName);
            newFile.createNewFile();
            Logger.getLogger(LocalFileHandler.class.getName()).log(Level.INFO, "Attempting to create file ''{0}''", newFile);
            //storage.save("", tempName);
            //storage.
            is = new FileOutputStream(newFile);
            Utils.streamCopy( is, new FileInputStream(file));

            String newPath="";
            if (Utils.getOperatingSystem() == Utils.WINDOWS) {
             newPath = "file:///"+storage.getDirectory().getAbsolutePath().replace("\\", "/")+"/"+tempName;   
            } else {
             newPath = "file://"+storage.getDirectory().getAbsolutePath().replace("\\", "/")+"/"+tempName;
            }


            Logger.getLogger(LocalFileHandler.class.getName()).log(Level.INFO, "Attempting to create file ''{0}''", newFile);
            de.mxro.utils.URI uri = de.mxro.utils.URIImpl.create(newPath);
            Logger.getLogger(LocalFileHandler.class.getName()).log(Level.INFO, "New temporary image at ''{0}''", uri.toString());

            return uri.toString();
        } catch (IOException ex) {
            Logger.getLogger(LocalFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(LocalFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return null;

    }

    @Override
    public boolean canHandle(File file) {
        String extension = Utils.getExtension(file.getAbsolutePath());
		return extension.equals("png") ||
		       extension.equals("jpg") ||
		       extension.equals("jpeg") ||
		       extension.equals("gif");
    }

    public LocalFileHandler(LocalStorage storage) {
        this.storage = storage;
    }




}
