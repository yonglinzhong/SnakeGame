package com.yonglinzhong.game;

import java.awt.*;
import javax.swing.*;

public class Help extends JDialog {
    private static final long serialVersionUID = 4693799019369193520L;
    private JPanel contentPane;
    private Font f = new Font("Arial",Font.PLAIN,15);
    private JScrollPane scroll;
	
    public Help() {
        setTitle("Game Instruction");
        Image img=Toolkit.getDefaultToolkit().getImage("image//title.png");
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);
        setSize(410,380);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();
        contentPane.add(shadePanel, BorderLayout.CENTER);
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("Game rules: \n" +
                "- You can control the snake direction by pressing the arrow keys or WASD keys. \n" +
                "- Long press will speed up the snake.\n" +
                "- Press “ESC” key will restart the game.\n" +
                "- Press blank key will pause or begin the game.\n" +
                "- In the menu, you can set the head, body, speed, background, and grid visibility.\n" +
                "- Different foods have different points.\n" +
                "- Obstacles will be generated randomly.\n" +
                "- The snake can destroy the obstacles by firing bullets which total amount is 20.");
        J1.setFocusable(false);
    	J1.setFont(f);
    	J1.setEditable(false);
    	J1.setOpaque(false);
    	J1.setLineWrap(true);
    	
    	scroll = new JScrollPane(J1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scroll.setBorder(BorderFactory.createTitledBorder("How to play"));
    	scroll.setOpaque(false);
    	scroll.getViewport().setOpaque(false);
    	shadePanel.add(scroll);
    	scroll.setBounds(10, 10, 385, 330);
    	
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new Help();
	}
}
