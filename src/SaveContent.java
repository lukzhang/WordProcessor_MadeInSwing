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
//Allow handling of multiple file types .doc, .rtf, etc.
//*******************************


public class SaveContent {
    String formattedText;
    String filename;
    String prename;

    ReadWriteXML readWriteXML = new ReadWriteXML();
    WriteControllerXML writeControllerXML = new WriteControllerXML();

    public String save(JTextPane text){
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
                return saveCopy(text, chooser);
            } else{
                System.out.println("SAVE CANCCELED");
            }


        }

       return "";
    }

    public String saveCopy(JTextPane text, JFileChooser chooser){
        filename = chooser.getSelectedFile().getName();
        System.out.println(filename);

        //Update XML file containing how many copies for the user's respecitve file
        makeDir("../WordProcessor_MadeInSwing/UserData");

        //Initially, doesn't add extentsion to filename
        String lastFourChars = filename.substring(filename.length()-4);
        if(!lastFourChars.equals(".rtf")){
            filename=filename+".rtf";
        }
        System.out.println("HERE"+filename);

        //I guess I'll save the copy to XML first after nonce is incremented,
        //and then save the file to a folder with the updated nonce
        try{
            writeControllerXML.saveCopy(filename);
        } catch(Exception e){
            System.out.println(e);  //In case file is not found
        }

        //Write the copy with highest nonce (COPY1, COPY2, etc.)
        makeDir("../WordProcessor_MadeInSwing/backups/testDir");    //Method handles if it already has dir. If so, don't mkdir

        int highestNonce = writeControllerXML.highestDocNonce(filename);

        lastFourChars = filename.substring(filename.length()-4);
        String copyName = filename;
        if(lastFourChars.equals(".rtf")){
            String firstPart = filename.substring(0, filename.length() - 4);    //Remove the .rtf... In the future make a moethod to handle all doc types
            prename = firstPart;
            copyName = firstPart+"_COPY"+highestNonce+".rtf";
        }
        else{
            copyName = filename+"_COPY"+highestNonce+".rtf";
        }
        System.out.println("COPYNAME: "+copyName);
        saveFile(text, "../WordProcessor_MadeInSwing/backups/testDir/"+copyName);

        return prename;
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
            //System.out.println("ALREADY EXISTS");
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
