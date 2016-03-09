package wecc.cal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TypePanel extends JPanel implements KeyListener{
	 
	//Individual keyboard rows  
	String firstRow[] = {"~","1","2","3","4","5","6","7","8","9","0","-","+","<<"};//BackSpace
	String secondRow[] = {"Q","W","E","R","T","Y","U","I","O","P","[","]","\\"};
	String thirdRow[] = {"A","S","D","F","G","H","J","K","L",":","\"","ENT"};
	String fourthRow[] = {"Z","X","C","V","B","N","M",",",".","?"};

	//all keys without shift key press
	String noShift="`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
	//special charactors on keyboard that has to be addressed duing keypress
	String specialChars ="~-+[]\\;',.?";

	//Jbuttons corresponting to each individual rows 
	JButton first[];

	JButton second[];

	JButton third[];

	JButton fourth[];

	JLabel statusLbl;
	 JTextField  text;
	//default color of the button to be repainted when key released 
	Color cc = new JButton().getBackground();
	String bufferStr="";
	SystemSetting sys = new SystemSetting();
	/*
	 * Invoked when a key has been pressed.
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent evt) 
	{


	}//end of keypressed

	/**
	 * Invoked when a key has been released.
	 */
	public void keyReleased(KeyEvent evt)
	{

	}
	public TypePanel()
	{
	    
	    initWidgets();
	}
	public void initParameter(){
		statusLbl.setText("");
		bufferStr = "";
		text.setText(bufferStr);
	}
	/**
	 * Method to initialize frame component 
	 */
	private void initWidgets()
	{
		setBackground(sys.sysSettingMenuTitleBack);
	    //set the text area on top 
	    text = new JTextField();
	    text.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 14));
	    text.setBounds(219, 37, 200, 40);
	    text.setPreferredSize(new Dimension(200,200));
	    //JScrollPane scrollPane = new JScrollPane(text);
	    //scrollPane.setPreferredSize(new Dimension(800, 200));

	   // add(typingArea, BorderLayout.PAGE_START);
	   // add(scrollPane, BorderLayout.CENTER);
	    //set the info label on top 
	    
	    JLabel info = new JLabel("Setting" );
	    //set the bold font for info
	    info.setFont(new Font("Verdana",Font.BOLD,40));
	    info.setForeground(sys.sysWhite);
	    info.setBackground(sys.sysSettingMenuTitleBack);
	    /*set the layout and place compomnet in place and pack it */
	    setLayout(new BorderLayout());
	    /*Various panel for the layout */
	    JPanel jpNorth = new JPanel();
	    JPanel jpCenter = new JPanel();
	    JPanel jpKeyboard = new JPanel();
	    JPanel jpNote = new JPanel();
	    jpNorth.setBackground(sys.sysSettingMenuTitleBack);
	    add( jpNorth, BorderLayout.NORTH);
	    add( jpNote);
	    jpCenter.setBackground(sys.sysSettingMenuBackground);
	    add( jpCenter, BorderLayout.CENTER);
	    jpKeyboard.setBackground(sys.sysSettingMenuBackground);
	    add(jpKeyboard, BorderLayout.SOUTH);

	    jpNorth.setLayout(new BorderLayout());
	    jpNorth.add(info, BorderLayout.WEST);
	    jpNorth.add(info, BorderLayout.SOUTH);

	    jpCenter.setLayout(null);
	    //jpCenter.add(text, BorderLayout.WEST);
	    jpCenter.add(text, BorderLayout.CENTER);
	    
	    JLabel lblNewLabel = new JLabel("Password:");
	    lblNewLabel.setForeground(sys.sysWhite);
	    lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 23));
	    lblNewLabel.setBounds(219, 10, 191, 27);
	    jpCenter.add(lblNewLabel);
	    
	    JButton cancelBtn = sys.sysCancelBtn;
	    cancelBtn.setLocation(165, 87);
	    jpCenter.add(cancelBtn);
	    
	    JButton confirmBtn = sys.sysLoginBtn;
	    confirmBtn.setLocation(353, 87);
	    jpCenter.add(confirmBtn);
	    
	    statusLbl = new JLabel("");
	    statusLbl.setForeground(Color.RED);
	    statusLbl.setBounds(438, 50, 200, 27);
	    jpCenter.add(statusLbl);

	    //add(text,BorderLayout.WEST);
	   // add(scrollPane,BorderLayout.CENTER);

	    //layout for keyboard 
	    jpKeyboard.setLayout(new GridLayout(4,1));
	    //pack the components
	    //pack();

	    /*paint first keyboard row  and add it to the keyboard*/
	    first = new JButton[firstRow.length];
	    //get the panel for the  row
	    JPanel p = new JPanel(new GridLayout(1, firstRow.length));
	    for(int i = 0; i < firstRow.length; ++i) 
	    {
	        JButton b= new JButton(firstRow[i]);
	        b.setPreferredSize(new Dimension(100,65));
	        first[i] = b;
	        first[i].setActionCommand(firstRow[i]);
	        p.add(first[i]);
	    }
	    jpKeyboard.add(p);

	    /*paint second keyboard row  and add it to the keyboard*/
	    second = new JButton[secondRow.length];
	    //get the panel for the  row
	    p = new JPanel(new GridLayout(1, secondRow.length));
	    for(int i = 0; i < secondRow.length; ++i) 
	    {
	        second[i] = new JButton(secondRow[i]);
	        second[i].setActionCommand(secondRow[i]);
	        p.add(second[i]);

	    }
	    jpKeyboard.add(p);

	    /*paint third keyboard row  and add it to the keyboard*/

	    third = new JButton[thirdRow.length];
	    //get the panel for the  row
	    p = new JPanel(new GridLayout(1, thirdRow.length));
	    for(int i = 0; i < thirdRow.length; ++i)
	    {
	        third[i] = new JButton(thirdRow[i]);
	        third[i].setActionCommand(thirdRow[i]);
	        p.add(third[i]);
	    }
	    jpKeyboard.add(p);

	    /*paint fourth keyboard row  and add it to the keyboard*/
	    fourth = new JButton[fourthRow.length];
	    //get the panel for the  row
	    p = new JPanel(new GridLayout(1, fourthRow.length));
	    for(int i = 0; i < fourthRow.length; ++i)
	    {
	        fourth[i] = new JButton(fourthRow[i]);
	        p.add(fourth[i]);


	    }
	    p.add(new JPanel());
	    jpKeyboard.add(p);



	    /*add listeners */
	    addKeyListener(this);
	    text.addKeyListener(this);
	    
	    cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "main");
			}
		});
	    
	    confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkPass();
				
			}
		});
	    /*add listeners to all the button */
	    for(JButton b : first){
	    	b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand()=="<<")
						if(bufferStr.length()!=0)
							bufferStr=bufferStr.substring(0,bufferStr.length()-1);
						else
							bufferStr="";
					else
						bufferStr += e.getActionCommand();
					text.setText(bufferStr);
				}
			});
	    }

	    for(JButton b : second)
	    	b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					bufferStr += e.getActionCommand();
					text.setText(bufferStr);
				}
			});
	    for(JButton b : third)
	    	b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand()=="ENT")
						checkPass();
					else
						bufferStr += e.getActionCommand();
					text.setText(bufferStr);
				}
			});

	    for(JButton b : fourth)
	    	b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					bufferStr += e.getActionCommand();
					text.setText(bufferStr);
				}
			});



	    } //end of initWidgets   

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void checkPass(){
		if(bufferStr.compareTo(sys.password)==0){
			MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "settingMenu");
		}
		else{
			statusLbl.setText("Wrong Password!!");
			bufferStr="";
			text.setText(bufferStr);
		}
	}
}
