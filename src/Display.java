import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Display extends JPanel implements ActionListener {

    private JTextPane textArea;
    private JButton saveButton;
    private JComboBox colorCombo;
    private JComboBox fontCombo;
    private JLabel processorLabel;
    private JSlider fontSize;
    private JTextField docNameField;
    private JLabel docNameLabel;
    private JTextField nonceField;
    private JLabel nonceLabel;
    private JButton retrieveBtn;

    //Image, videos....
    private ImageIcon clouds;
    private JLabel label1;
    private ImageIcon image2;
    private JLabel label2;
    private JLabel chooseImageLabel;
    private JComboBox imageCombo;

    //HashMap to display name of file, and location
    Map<String, String> imageMap = new HashMap<>();

    //Displays what the current doc is
    private String currDoc = "";

    //Create some method objects
    SaveContent saveFile = new SaveContent();
    ColorManagement colorClass = new ColorManagement();
    FontManagement fontClass = new FontManagement();
    LoadContent loadContent = new LoadContent();
    Main main = new Main();

    //Create some arrays
    String[] colorItems = {"Red", "Blue", "Green", "Purple", "Orange", "Black"};
    String[] fontItems = {"Monospaced", "Serif", "Sans Serif"};
    String[] bgItems = {"clouds", "cosmos", "London", "avacado"};
    String[] bgLocations = {"/clouds.png", "/cosmos.png", "/London.png", "/avacado.gif"};

    public Display(){
        init();
    }

    public void init(){
        Font font;
        Color color;

        color = colorClass.getColor();
        font = fontClass.getFont();

        //Construct Components
        textArea = new JTextPane();
        saveButton = new JButton("Save");
        colorCombo = new JComboBox(colorItems);
        fontCombo = new JComboBox(fontItems);
        processorLabel = new JLabel("Current Doc: "+currDoc);
        fontSize = new JSlider(10,30);
        docNameField = new JTextField(100);
        docNameLabel = new JLabel("Select Doc Name");
        nonceField = new JTextField(100);
        nonceLabel = new JLabel("Select Copy Version");
        retrieveBtn = new JButton("Retrieve Copy");
        clouds = new ImageIcon(getClass().getResource("/clouds.png"));
        label1 = new JLabel(clouds);
        chooseImageLabel = new JLabel("Choose Background :)");
        imageCombo = new JComboBox(bgItems);

        //add images and their locations to hashmap
        for(int i=0; i<bgItems.length; i++){
            imageMap.put(bgItems[i], bgLocations[i]);
        }


        //Work with slider
        fontSize.setOrientation(JSlider.HORIZONTAL);
        fontSize.setMinorTickSpacing(1);
        fontSize.setMajorTickSpacing(5);
        fontSize.setPaintTicks(true);
        fontSize.setPaintLabels(true);

        //Make the text area look presentable
        textArea.setBackground(Color.WHITE);
        textArea.setOpaque(false);  //Make transparent

        //Adjust size and layout
        setPreferredSize(new Dimension(1000, 473));
        setLayout(null);

        //Add components
        add (textArea);
        add (saveButton);
        add (colorCombo);
        add (fontCombo);
        add (processorLabel);
        add (fontSize);
        add (docNameField);
        add (docNameLabel);
        add (nonceField);
        add (nonceLabel);
        add (retrieveBtn);
        add (label1);
        add (chooseImageLabel);
        add (imageCombo);

        //Set boundaries
        textArea.setBounds(50, 50, 600, 400);
        saveButton.setBounds(670, 270, 140, 35);
        colorCombo.setBounds(670, 205, 140, 53);
        fontCombo.setBounds(670, 150, 140, 35);
        processorLabel.setBounds(670, 20, 140, 35);
        fontSize.setBounds(670, 95, 140, 49);
        docNameField.setBounds(670, 345, 140, 30);
        docNameLabel.setBounds(670, 320, 140, 30);
        nonceField.setBounds(670, 395, 140, 30);
        nonceLabel.setBounds(670, 370, 140, 30);
        retrieveBtn.setBounds(670, 430, 140, 35);
        label1.setBounds(10, 10, 650, 450);
        imageCombo.setBounds(850, 150, 140, 35);
        chooseImageLabel.setBounds(850, 120, 140, 35);

        //Add action listeners
        saveButton.addActionListener(this);
        colorCombo.addActionListener(this);
        fontCombo.addActionListener(this);
        retrieveBtn.addActionListener(this);
        imageCombo.addActionListener(this);
        fontSize.addChangeListener(this::fontSizeChanged);
    }

    public void fontSizeChanged(ChangeEvent e){
        Font font = new Font(fontCombo.getSelectedItem().toString(), Font.PLAIN, fontSize.getValue());
        textArea.setFont(font);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==saveButton){
            currDoc = saveFile.save(textArea);    //Store the current doc name after you save or load...
            processorLabel.setText("Current Doc: "+currDoc);
        } if(e.getSource()==colorCombo){
            colorClass.selectColor(colorCombo.getSelectedItem().toString());
            textArea.setForeground(colorClass.getColor());
        } if(e.getSource() == fontCombo){
            fontClass.selectFont(fontCombo.getSelectedItem().toString(), fontSize.getValue());
            textArea.setFont(fontClass.getFont());
        } if(e.getSource() == fontSize){

        } if(e.getSource() == imageCombo){
            //Maybe use a hashmap for name -> location?
            String currFile = imageMap.get(imageCombo.getSelectedItem().toString());
            try{
                label1.setIcon(new ImageIcon(getClass().getResource(currFile)));
            } catch(NullPointerException ne){
                System.out.println("file wasn't found");
            }

        } if(e.getSource() == retrieveBtn){
            String home = "../WordProcessor_MadeInSwing/backups/testDir/";
            String doc = docNameField.getText();
            String version = nonceField.getText();

            //If user enters field, use that. Otherwise, use whatever current doc is
            if(doc.isEmpty()){
                doc = currDoc;
            }

            String currPath = home + doc + "_COPY" +version+".rtf";
            loadContent.getSpan(textArea, currPath);
        }
    }

    public JTextPane getText(){
        return textArea;
    }
}
