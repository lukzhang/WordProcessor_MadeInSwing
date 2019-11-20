import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


//***********TO DO****************
//Find a way to store file data and nonce for each file saved...
//Then, dynamically increment the copy index (copy1, copy2, etc.)
//*******************************


public class SaveContent {
    String formattedText;

    ReadWriteXML readWriteXML = new ReadWriteXML();

    public void save(JTextPane text){
        if(text.getText().length() > 0){
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("RICH TEXT FORMAT", "rtf", "rtf");
            chooser.setFileFilter(filter);

            int option = chooser.showSaveDialog(null);
            String filePath = chooser.getSelectedFile().getPath();

            if(!chooser.getSelectedFile().getPath().toLowerCase().endsWith(".rtf")){
                filePath=chooser.getSelectedFile().getPath() + ".rtf";
            }

            if(option == JFileChooser.APPROVE_OPTION){
                saveFile(text, filePath);
            } else{
                System.out.println("SAVE CANCCELED");
            }
        }

        makeDir("../MyJavaProjects/backups/testDir");
        saveFile(text, "../MyJavaProjects/backups/testDir/copy1.rtf");

        //Update XML file containing how many copies for the user's respecitve file
        makeDir("../MyJavaProjects/UserData");
        try{
            readWriteXML.writeXML();
        } catch(ParserConfigurationException e){

        } catch (TransformerConfigurationException e){

        } catch (TransformerException e){

        }

    }


    public void makeDir(String dirPath){
        //Add a folder
        File f = new File(dirPath);
        if(!f.exists()) {
            boolean success = (new File(dirPath)).mkdirs();
            if (!success) {
                // Directory creation failed
                System.out.println("FAIL CREATE DIR");
            }
        }
        else {
            System.out.println("ALREADY EXISTS");
        }
    }

    public void saveFile(JTextPane text, String filePath){
        StyledDocument doc = (StyledDocument)text.getDocument();
        HTMLEditorKit kit = new HTMLEditorKit();
        BufferedOutputStream out;

        try{
            out = new BufferedOutputStream(new FileOutputStream(filePath));
            kit.write(out,doc,doc.getStartPosition().getOffset(), doc.getLength());
        } catch(FileNotFoundException e){

        } catch(IOException e){

        } catch(BadLocationException e){

        }
    }
}
