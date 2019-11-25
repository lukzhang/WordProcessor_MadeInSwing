import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    //Create some method objects
    SaveContent saveFile = new SaveContent();
    ColorManagement colorClass = new ColorManagement();
    FontManagement fontClass = new FontManagement();
    LoadContent loadContent = new LoadContent();
    Main main = new Main();

    //Create some arrays
    String[] colorItems = {"Red", "Blue", "Green", "Purple", "Orange", "Black"};
    String[] fontItems = {"Monospaced", "Serif", "Sans Serif"};

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
        processorLabel = new JLabel("Nya Writer");
        fontSize = new JSlider(10,30);
        docNameField = new JTextField(100);
        docNameLabel = new JLabel("Select Doc Name");
        nonceField = new JTextField(100);
        nonceLabel = new JLabel("Select Copy Version");
        retrieveBtn = new JButton("Retrieve Copy");

        //Work with slider
        fontSize.setOrientation(JSlider.HORIZONTAL);
        fontSize.setMinorTickSpacing(1);
        fontSize.setMajorTickSpacing(5);
        fontSize.setPaintTicks(true);
        fontSize.setPaintLabels(true);

        //Make the text area look presentable
        textArea.setBackground(Color.LIGHT_GRAY);
        //textArea.setForeground(color);

        //Adjust size and layout
        setPreferredSize(new Dimension(817, 473));
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

        //Set boundaries
        textArea.setBounds(10, 10, 650, 450);
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

        //Add action listeners
        saveButton.addActionListener(this);
        colorCombo.addActionListener(this);
        fontCombo.addActionListener(this);
        retrieveBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==saveButton){
            System.out.println("HERE" +saveFile.save(textArea));    //Store the current doc name after you save or load...
        } if(e.getSource()==colorCombo){
            colorClass.selectColor(colorCombo.getSelectedItem().toString());
            textArea.setForeground(colorClass.getColor());
        } if(e.getSource() == fontCombo){
            fontClass.selectFont(fontCombo.getSelectedItem().toString(), fontSize.getValue());
            textArea.setFont(fontClass.getFont());
        } if(e.getSource() == retrieveBtn){
            String home = "../WordProcessor_MadeInSwing/backups/testDir/";
            String doc = docNameField.getText();
            String version = nonceField.getText();
            String currPath = home + doc + "_COPY" +version+".rtf";
            loadContent.getSpan(textArea, currPath);
        }
    }

    public JTextPane getText(){
        return textArea;
    }
}
