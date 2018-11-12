package com.yonglinzhong.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class ShadePanel extends JPanel {
    
    private static final long serialVersionUID = -2644424271663261406L;
    
    public ShadePanel() {
        super();
        setLayout(null);
    }
    
    @Override
    protected void paintComponent(Graphics g1) {// repain the component UI
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        GradientPaint paint = new GradientPaint(0, 0, Color.CYAN, 0, height,Color.MAGENTA);// color gradient
        g.setPaint(paint);
        g.fillRect(0, 0, width, height);
    }
}
 
public class About extends JDialog {
    private static final long serialVersionUID = 4693799019369193520L;
    private JPanel contentPane;
    private Font f1 = new Font("Arial",Font.PLAIN,15);
	private Font f2 = new Font("Arial",Font.PLAIN,20);
	private ImageIcon icon;
	private JLabel label;
    public About() {
        setTitle("About");
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");
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
        
        JTextArea J1 = new JTextArea("");
        J1.setFocusable(false);
    	J1.setFont(f2);
    	J1.setEditable(false);
    	J1.setOpaque(false);//背景透明
    	shadePanel.add(J1);
    	J1.setBounds(10, 10, 400, 180);
    	icon = new ImageIcon("title.png");
    	icon.setImage(icon.getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH));//保持图片的清晰
    	label = new JLabel(icon);
    	shadePanel.add(label);
    	label.setBounds(270, 0, 130, 130);
    	
    	JPanel p = new JPanel();
    	p.setBounds(5, 130, 395, 1);
	    p.setBorder(BorderFactory.createLineBorder(Color.black));
	    shadePanel.add(p);
	    
	    JLabel J2 = new JLabel("");
	    J2.setBounds(10, 145, 200, 30);
	    J2.setFont(f2);
	    shadePanel.add(J2);
	    
    	JLabel MyGithub_Label = new JLabel("");
    	MyGithub_Label.setFont(f2);
    	final JLabel MyGithub = new JLabel("");
    	MyGithub.setFont(f2);
    	MyGithub.setBackground(Color.white);
    	MyGithub.addMouseListener(new InternetMonitor());
    	JLabel MyCnBlog_Label = new JLabel("");
    	MyCnBlog_Label.setFont(f2);
    	final JLabel MyCnBlog = new JLabel("");
    	MyCnBlog.setFont(f2);
    	MyCnBlog.addMouseListener(new InternetMonitor());
    	JTextArea Copyright = new JTextArea("");
    	Copyright.setFocusable(false);
    	Copyright.setOpaque(false);
    	Copyright.setFont(f1);
    	Copyright.setEditable(false);
    	
    	shadePanel.add(MyGithub_Label);
    	MyGithub_Label.setBounds(10, 180, 400, 20);
    	shadePanel.add(MyGithub);
    	MyGithub.setBounds(10, 200, 400, 30);
    	shadePanel.add(MyCnBlog_Label);
    	MyCnBlog_Label.setBounds(10, 240, 400, 25);
    	shadePanel.add(MyCnBlog);
    	MyCnBlog.setBounds(10, 265, 400, 30);
    	shadePanel.add(Copyright);
    	Copyright.setBounds(10, 300, 400, 50);
       
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new About();
	}
}

class InternetMonitor extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		String J_temp = JLabel_temp.getText();
		System.out.println(J_temp);
		URI uri ;
			try {
				uri = new URI(J_temp);
				Desktop desk=Desktop.getDesktop();
				if(Desktop.isDesktopSupported() && desk.isSupported(Desktop.Action.BROWSE)){
					try {
						desk.browse(uri);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
	}
	public void mouseEntered(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.red);
	}
	public void mouseExited(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.blue);
	}
}
