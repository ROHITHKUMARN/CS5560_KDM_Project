import java.awt.*;
import java.awt.event.*;

public class AWT {

    private Frame mainFrame;
   // private Label headerLabel;
    private Label statusLabel;
    private Panel controlPanel;
    private TextArea text;
    private  TextArea possiblequestions;

    public AWT(){
        prepareGUI();
    }

    public static void main(String[] args){
        AWT  awtControlDemo = new AWT();
        awtControlDemo.showButtonDemo();
    }

    private void prepareGUI(){
        mainFrame = new Frame("CS5560 KDM Project Demonstration");
        mainFrame.setSize(1000,800);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        statusLabel = new Label();
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350,600);

        possiblequestions = new TextArea();
        possiblequestions.setSize(200,500);
        mainFrame.add(possiblequestions);

        possiblequestions.setText(

                "List of Questions\n"+
                        "which IsA Disease\n" +
                "what WereProtectedFrom HFDinducedWeightGain\n" +
                "\n" +
                "which IsWith EstablishedObesity\n" +
                "\n" +
                "Which Causes LungNeutrophilia\n" +
                "\n" +
                "which are Targeting ThrombinActivity\n" +
                "\n" +
                "which Avoid AttributionOfCausality\n" +
                "\n" +
                "which  Should be ShouldDescriptive\n" +
                "\n" +
                "which IsIn FibγMice\n" +
                "\n" +
                "which WereProtectedFrom WeightGain\n" +
                "\n" +
                "what IsWith IncreasedPrevalenceOfSinonasalDisease");

        controlPanel = new Panel();
        controlPanel.setLayout(new GridBagLayout());
        text =new TextArea();
        mainFrame.add(text);
       // mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showButtonDemo(){
        //headerLabel.setText("Control in action: Button");

        //Button okButton = new Button("OK");
        Button submitButton = new Button("Submit");

        Button cancelButton = new Button("Cancel");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Question2Query  ans = new Question2Query();
                String val= "Sorry, we are unable to answer";
                try {
                    val = ans.ask(text.getText());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                statusLabel.setText("\n"+val);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Cancel Button clicked.");
            }
        });

        //controlPanel.add(okButton);
        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);
        mainFrame.setVisible(true);
    }
}