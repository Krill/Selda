import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class EditorGUI extends JPanel
{
    // static fields:
    private static final String VERSION = "Version 0.1";

    // fields:
    private String currentDir;
    private String fileName = "unamned map";
    private JFrame frame;
    private Editor editor;
    private JLabel filenameLabel;
    
    public EditorGUI()
    {
        currentDir = System.getProperty("user.dir");
    
        makeFrame();
    }

    private void makeFrame()
    {
        frame = new JFrame("Map Editor, By Richard Norling");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBackground(Color.BLACK);
        contentPane.setLayout(new BorderLayout(6, 6));

        makeMenuBar(frame);


        editor = new Editor();
        editor.setBackground( new Color(67,67,67) );
        editor.setBorder( new EtchedBorder() );
        
        fileName = JOptionPane.showInputDialog ("Enter an awesome mapname");
        if(fileName == "" || fileName == null){
            fileName = "unknown";
        }
        editor.setFileName(fileName);
        
        //JPanel tempPanel = new JPanel();
        //tempPanel.add(editorPanel);
        
        contentPane.add(editor, BorderLayout.CENTER);
        
        
        

        // Create two labels at top and bottom for the file name and status messages
        filenameLabel = new JLabel(fileName);
        filenameLabel.setForeground(Color.WHITE);
        filenameLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(filenameLabel, BorderLayout.SOUTH);
        
        // Create the toolbar with the buttons
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(0, 1));
        
        // Add toolbar into panel with flow layout for spacing
        /*
        JPanel flow = new JPanel();
        flow.add(toolbar);
        
        
        contentPane.add(flow, BorderLayout.WEST);
        */
        // building is done - arrange the components      
        frame.pack();
        
        // place the frame at the center of the screen and show
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setResizable(true);
        frame.setVisible(true);
    }
    
    private void makeMenuBar(JFrame frame)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        
        item = new JMenuItem("Open...");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {}
                           });
        menu.add(item);
        
        item = new JMenuItem("Rename...");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                
                                   fileName = JOptionPane.showInputDialog ("Enter another awesome mapname");
                                   
                                    if(fileName == "" || fileName == null){
                                        fileName = "unknown";
                                    }
                                    editor.setFileName(fileName);
                                    filenameLabel.setText(fileName);
                                
                                }
                           });
        menu.add(item);

        item = new JMenuItem("Close");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {}
                           });
        menu.add(item);
        
        item = new JMenuItem("Save Map");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                   try{ 
                                       editor.saveMap();
                                   }catch(Exception i){
                                       //return null;            // Always must return something
                                   }
                                }
                           });
        menu.add(item);
        
        item = new JMenuItem("Load Map");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                   try{ 
                                       
                                       //Create a file chooser
                                       final JFileChooser fc = new JFileChooser(currentDir + "/maps");
                                       //In response to a button click:
                                       int returnFileName = fc.showOpenDialog(EditorGUI.this);
                                       editor.setFileName(fileName);
                                       
                                       /*String tempName;
                                       if(fileName != (tempName = JOptionPane.showInputDialog ( "Loading what awesome map?" ))) {
                                           fileName = tempName;
                                           editor.setFileName(fileName);
                                       }*/
                                       
                                       editor.loadMap();
                                       
                                       
                                       
                                       
                                   }catch(Exception i){
                                       //return null;            // Always must return something
                                   }
                                }
                           });
        menu.add(item);

        item = new JMenuItem("Export Map");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                   try{ 
                                       editor.exportMap();
                                   }catch(Exception i){
                                       //return null;            // Always must return something
                                   }
                                }
                           });
        menu.add(item);
        menu.addSeparator();
        
        item = new JMenuItem("Import Map");
        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, SHORTCUT_MASK));
        	item.addActionListener(new ActionListener() {
                           public void actionPerformed(ActionEvent e) {
                                   
                                   try{ 
                                       
                                       //Create a file chooser
                                       final JFileChooser fc = new JFileChooser(currentDir + "/maps");
                                       //In response to a button click:
                                       int returnFileName = fc.showOpenDialog(EditorGUI.this);
                                       
                                       File file = fc.getSelectedFile();
                                       editor.importMap(file);
                                       
                                   }catch(Exception i){
                                       //return null;            // Always must return something
                                   }
                            }
                       });
        menu.add(item);
        menu.addSeparator();
        
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {}
                           });
        menu.add(item);
        menu.addSeparator();



        // create the Filter menu
        menu = new JMenu("Modify");
        menubar.add(menu);
        
        
        item = new JMenuItem("Print map");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, SHORTCUT_MASK));
        item.addActionListener(new ActionListener() {
                           public void actionPerformed(ActionEvent e) { 
                               editor.printMap();}
                       });
        menu.add(item);
        menu.addSeparator();
        
        item = new JMenuItem("Set All To Active Icon");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                   
                                   editor.setAllToActive();}
                           });
        menu.add(item);
        
        item = new JMenuItem("Set All To Block");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                   
                                   editor.setAllBlockTo(true);}
                           });
        menu.add(item);
        
        item = new JMenuItem("Set All To No-Block");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                   
                                   editor.setAllBlockTo(false);}
                           });
        menu.add(item);

        // create the Help menu
        menu = new JMenu("Help");
        menubar.add(menu);
        
        item = new JMenuItem("About Map Editor");
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {}
                           });
        menu.add(item);

    }
}
