package com.appgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class appgui extends JPanel {
    /*private JPanel mainField;
    private JButton readyButton;
    private JLabel statusLabel;*/
    private Image image;

    public appgui() {
        try {
            image = ImageIO.read(new File("resources\\field.png"));
        } catch (IOException ex) {
            String message = "Unable to show the field. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null,message,"FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }

        /*readyButton=new JButton("Ready");
        statusLabel=new JLabel("Status");*/
        /*readyButton.setHorizontalAlignment(SwingConstants.LEFT);
        readyButton.setVerticalAlignment(SwingConstants.BOTTOM);*/
        /*readyButton.setAlignmentX(10.5f);
        readyButton.setAlignmentY(10.5f);
        this.setBounds(100,100,618,670);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(20,19,3,3));
        readyButton.addActionListener(new ButtonClickListener());
        container.add(readyButton);
        container.add(statusLabel);
        for(int i=0;i<19*19;i++){
            container.add(new JLabel(""));
        }*/
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g = (Graphics2D) g;
        //image = new ImageIcon("C:\\Users\\User\\IntelliJIDEAProjects\\LabConnect6\\src\\resources\\field.png").getImage();
        g.drawImage(image, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LabConnect6");
        frame.setContentPane(new appgui());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(617, 630);
        frame.setVisible(true);
        /*appgui app = new appgui();
        app.setVisible(true);*/
    }
}
