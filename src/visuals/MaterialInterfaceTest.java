package visuals;// Austin Patel
// 4/11/2017
// MaterialInterfaceTest.java

import swingmaterial.MaterialComboBox;
import swingmaterial.MaterialTextField;
import theme.MaterialButton;

import javax.swing.*;
import java.awt.*;

public class MaterialInterfaceTest extends JFrame {

    public static void main(String[] args) {
        new MaterialInterfaceTest();
    }

    public MaterialInterfaceTest() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setLocationRelativeTo(null);
//        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//        setLayout(new FlowLayout());

//        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        contentPanel.add(new JLabel("Testiasdfasdfasdfasdfasdfasdfasdfasdfasdf"));
//        add(new MaterialButton("Button"));
//        add(new MaterialButton("Button"));
        contentPanel.add(new MaterialButton("Button"));
//        add(new MaterialButton("Button"));
//        add(new MaterialButton("Button"));
//        add(new MaterialButton("Button"));
//        add(new MaterialButton("Button"));

//        MaterialTextField textField = new MaterialTextField();
//        textField.setLabel("test");
//        add(textField);

        JTextField jTextField = new JTextField();
        jTextField.setText("test");
//        jTextField.setMaximumSize(jTextField.getPreferredSize());
//        jTextField.setPreferredSize(jTextField.getMaximumSize());
        contentPanel.add(jTextField);

//
//        JComboBox<String> comboBox = new JComboBox<>();
//        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) comboBox.getModel();
//        comboBoxModel.addElement("1");
//        comboBoxModel.addElement("2");
//        comboBoxModel.addElement("3");
//        comboBox.setMaximumSize(getPreferredSize());
//        add(comboBox);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
