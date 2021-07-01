package dontTouchTheWhiteTile;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ComponentInWindow extends JFrame {

    static DontTouchTheWhiteTile dttwt;

    JTextField text;
    JButton button;
    JCheckBox checkBox1, checkBox2, checkBox3;
    JRadioButton[] buttons = new JRadioButton[6];
    ButtonGroup group1, group2;
    JComboBox<Object> comBox;
    JTextArea area;
    JPasswordField password;
    JRadioButton aa;
    String userName;
    int column;
    String speed;
    String songName;

    public ComponentInWindow() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        setLayout(layout);

        add(new JLabel("Username: "));
        text = new JTextField(25);
        add(text);

        

        add(new JLabel("speed:"));
        group2 = new ButtonGroup();
        buttons[2] = new JRadioButton("slow");
        buttons[3] = new JRadioButton("fast");
        buttons[4] = new JRadioButton("super fast");
        buttons[5] = new JRadioButton("hell mode");
        group2.add(buttons[2]);
        group2.add(buttons[3]);
        group2.add(buttons[4]);
        group2.add(buttons[5]);
        add(buttons[2]);
        add(buttons[3]);
        add(buttons[4]);
        add(buttons[5]);

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = buttons[2].getText();
            }
        });
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = buttons[3].getText();
            }
        });
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = buttons[4].getText();
            }
        });
        buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = buttons[5].getText();
            }
        });

        add(new JLabel("Columns:"));
        group1 = new ButtonGroup();
        buttons[0] = new JRadioButton("3");
        buttons[1] = new JRadioButton("4");
        group1.add(buttons[0]);
        group1.add(buttons[1]);
        add(buttons[0]);
        add(buttons[1]);


        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                column = Integer.parseInt(buttons[0].getText());
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                column = Integer.parseInt(buttons[1].getText());
            }
        });

        add(new JLabel("music:"));
        comBox = new JComboBox<>();
        comBox.addItem("Canon");
        comBox.addItem("Turkish March");
        comBox.addItem("Fur Elise");
        comBox.addItem("Ballade pour Adeline");
        add(comBox);

        comBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songName = (String) comBox.getSelectedItem();
            }
        });

        button = new JButton("Play");
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    userName = text.getText();
                    System.out.printf("%s %d %s %s\n", userName, column, speed, songName);
                    setVisible(false);
                    dispose();
                    dttwt = new DontTouchTheWhiteTile(userName, column, speed, songName);

                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
        });
    }
}