import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.*;

public class LoadContent {

    //String path = "../WordProcessor_MadeInSwing/backups/testDir/cool_COPY5.rtf";

//    public void load(JTextPane jTextPane){
//        try {
//            FileReader fr = new FileReader(path);
//            BufferedReader reader = new BufferedReader(fr);
//            jTextPane.read(reader, path);
//
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        catch(IOException e){
//
//        }
//    }

    //Must use HTML tag parser to get contents correctly
    public void getSpan(JTextPane jTextPane, String path){

        try{
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);

            // Parse the HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument)kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
            kit.read(reader, doc, 0);

            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.SPAN);

            JTextPane pText1 = new JTextPane();

            while (it.isValid())
            {
                int start = it.getStartOffset();
                int end = it.getEndOffset();
                String text = doc.getText(start, end - start);
                System.out.println(  text );
                pText1.setText(pText1.getText() + text + "\n");
                it.next();
            }
            jTextPane.setText(pText1.getText());
        } catch(FileNotFoundException e){

        } catch(IOException e){

        } catch(BadLocationException e){

        }
    }


//    // If 'uri' begins with "http:" treat as a URL,
//    // otherwise, treat as a local file.
//    static Reader getReader(String uri)
//            throws IOException
//    {
//        // Retrieve from Internet.
//        if (uri.startsWith("http"))
//        {
//            URLConnection conn = new URL(uri).openConnection();
//            return new InputStreamReader(conn.getInputStream());
//        }
//        // Retrieve from file.
//        else
//        {
//            return new FileReader(uri);
//        }
//    }



}
