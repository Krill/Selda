import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;


public class Editor extends JPanel{
        
    private ImageIcon activeIcon = null;
    private boolean isBlockerActive = false;
    
    private String fileName = "currentMap";
    
    private ImageHandler imageHandler;
    
    private JLayeredPane mapPanelWrapper;
    private JPanel mapTilePanel;
    private JPanel mapDecoratorPanel;
    
    private Map activeMap;
    private Map currentBaseMap;  
    private Map currentDecoMap;
    
    private JButton activeBlock;
    private int activeId;
    private Cell cell;
    
    private int TOOL_ROW = 20;
    private int TOOL_COL = 10;
    private final int MAP_SIZE = 25;
    
    private final int MAP_ROW = 20;
    private final int MAP_COL = 25;
    
    public Editor(){
        imageHandler = new ImageHandler();
        
        currentBaseMap = new Map(20,25);
        currentDecoMap = new Map(20,25);
        
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.BLACK);
        
        JPanel topPanel = createBlockButtons();
            topPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
            
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground( new Color(50,50,50) );
        centerPanel.setBorder(new EmptyBorder(2, 2, 2, 2) );
        centerPanel.setLayout( new BorderLayout() );
        
            mapPanelWrapper = new JLayeredPane();
            mapPanelWrapper.setPreferredSize(new Dimension(MAP_SIZE*32, MAP_SIZE*32));
            mapPanelWrapper.setBackground( new Color(67,67,67) );
            mapPanelWrapper.addMouseMotionListener(new MouseMotionAdapter() {  });

                    mapTilePanel = new JPanel();
                    mapTilePanel.setBorder(BorderFactory.createTitledBorder("BackTiles"));
            
                    addMapToPanel(currentBaseMap,mapTilePanel);
                    mapTilePanel.setBackground( new Color(50,50,50) );
                    //mapTilePanel.setForeground( new Color(255,255,255) );
                    mapTilePanel.setLayout( new GridLayout(20,25) );
                    mapTilePanel.setSize(new Dimension(MAP_COL*32,MAP_ROW*32));
                    mapTilePanel.setLocation(0, 0);
                    
                    mapDecoratorPanel = new JPanel();
                    mapDecoratorPanel.setBorder(BorderFactory.createTitledBorder("ObjectTiles"));
            
                    addDecoMapToPanel(currentDecoMap,mapDecoratorPanel);
                    mapDecoratorPanel.setBackground( new Color(0,0,0,20) );
                    mapDecoratorPanel.setOpaque(false);
                    
                    mapTilePanel.setBackground( new Color(50,50,50) );
                    //mapDecoratorPanel.setForeground( new Color(255,255,255) );
                    mapDecoratorPanel.setLayout( new GridLayout(20,25) );
                    
                    mapDecoratorPanel.setSize(new Dimension(MAP_COL*32,MAP_ROW*32));
                    mapDecoratorPanel.setLocation(0, 0);
                    
            mapPanelWrapper.add(mapTilePanel, new Integer(4) );  
            mapPanelWrapper.add(mapDecoratorPanel, new Integer(5) );
            
            // ToolPanelWrapper
            JPanel toolPanelWrapper = new JPanel();
            toolPanelWrapper.setBackground( new Color(67,67,67) );
                JPanel toolPanel = new JPanel();
                    Tools currentTools = new Tools();
                    toolPanel.setLayout(new GridLayout(TOOL_ROW, TOOL_COL));
                    toolPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
                    toolPanel.setBackground(Color.BLACK);
                    addToolsToPanel(currentTools,toolPanel);
            toolPanelWrapper.add(toolPanel);

            // DecoratorPanelWrapper
            JPanel decoratorPanelWrapper = new JPanel();
            decoratorPanelWrapper.setBackground( new Color(67,67,67) );
                JPanel decoratorPanel = new JPanel();
                    decoratorPanel.setLayout(new GridLayout(TOOL_ROW, TOOL_COL));
                    decoratorPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
                    decoratorPanel.setBackground(Color.BLACK);
                    Decorators currentDecorators = new Decorators();
                    addDecoratorsToPanel(currentDecorators,decoratorPanel);
            decoratorPanelWrapper.add(decoratorPanel);
                
                
            centerPanel.add(decoratorPanelWrapper,BorderLayout.EAST);    
            centerPanel.add(toolPanelWrapper,BorderLayout.WEST);
            centerPanel.add(mapPanelWrapper,BorderLayout.CENTER);
            
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        System.out.println("mapPanel Layer index: " + mapPanelWrapper.getIndexOf(mapTilePanel) );
        System.out.println("decoratorPanel Layer index: " + mapPanelWrapper.getIndexOf(mapDecoratorPanel) );
        System.out.println("Highest Layer index: " + mapPanelWrapper.highestLayer() );
    }
    
    
    /* Removes currentMap from a panel (mapPanel */
    public void removeMap(JPanel jpanel){
        ArrayList<Cell> list = new ArrayList<Cell>();
        list = activeMap.getCells();
        for(Cell c : list){
            jpanel.remove(c);
        }
        
    }
    
    /* After loading a map run update() to make sure
       map is properly shown in mapPanel */
    public void updateMap(){
        ArrayList<Cell> list;
        list = activeMap.getCells();
        for(Cell c : list){
            ImageIcon tempIcon = (ImageIcon) c.getIcon();
            c.setIcon(tempIcon);
            c.setVisible(false);
            c.setVisible(true);
        }
    }
        
    /* Test method to print all Cells in a currentMap */
    public void printMap(){
        ArrayList<Cell> list = new ArrayList<Cell>();
        list = activeMap.getCells();
        
        int count = 0;
         for(Cell c : list){
             System.out.print("[" + count + "] ");
             if( c.getCol() == activeMap.getColumns()-1 ){
                    System.out.println("");
             }
             count++;
            }
        
    }
    
    
    /* Adds a map to a specified panel (mapPanel) */
    public void addMapToPanel(Map currentMap,JPanel jpanel){
        ArrayList<Cell> list = new ArrayList<Cell>();
        list = currentMap.getCells();
        for(Cell c : list){
            
            c.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    Cell tempCell = (Cell) e.getSource();           // Find the source of event, needs to be cast but we can be sure its a Cell
                    
                    if(isBlockerActive == true){
                        tempCell.setBlockable(isBlockerActive);
                    }else{
                        tempCell.setIcon(activeIcon);
                        tempCell.setBlockable(false);
                        tempCell.setId(activeId);
                    }
                    updateActiveBlock();
                    
                    System.out.println(tempCell.getId());
                    System.out.println(tempCell.getCol());
                    System.out.println(tempCell.getRow());
                }
            });
            
            jpanel.add(c);  // Adds the Cell c to the specified Panel
     
        }
    }
    
        /* Adds a map to a specified panel (mapPanel) */
    public void addDecoMapToPanel(Map currentMap,JPanel jpanel){
        ArrayList<Cell> list = new ArrayList<Cell>();
        list = currentMap.getCells();
        for(Cell c : list){
            
            c.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    Cell tempCell = (Cell) e.getSource();           // Find the source of event, needs to be cast but we can be sure its a Cell
                    
                    if(isBlockerActive == true){
                        tempCell.setBlockable(isBlockerActive);
                    }else{
                        tempCell.setIcon(activeIcon);
                        tempCell.setBlockable(false);
                    }
                    updateActiveBlock();
                }
            });
            
           c.setContentAreaFilled(false);
           c.setOpaque(false);
            
           jpanel.add(c);  // Adds the Cell c to the specified Panel
     
        }
    }
    
    /* Adds a Toolbox to a specified panel (mapPanel) */
    public void addToolsToPanel(Tools tools,JPanel jpanel){
        ArrayList<Cell> toolList = new ArrayList<Cell>();
        toolList = tools.getTools();
        for(Cell c : toolList){
            
           c.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e) {
                   Cell tempCell = (Cell) e.getSource();     // Find the source of event, needs to be cast but we can be sure its a Cell
                   activeIcon = (ImageIcon) tempCell.getIcon();
                   activeId = tempCell.getId();
                   //changeActiveIcon(tempCell);
                   updateActiveBlock();
                   
                   mapPanelWrapper.setLayer(mapDecoratorPanel, new Integer(-1) );
                   mapPanelWrapper.setLayer(mapTilePanel, new Integer(0) );
                   activeMap = currentBaseMap;
                   
                   System.out.println("---------------------------------");
                   System.out.println("Active ID: " + activeId);
                   System.out.println("---------------------------------");
               }
           });
            
           jpanel.add(c);
           c.updateUI();
        }
    }
    
        public void addDecoratorsToPanel(Decorators decorators,JPanel jpanel){
        ArrayList<Cell> decoratorList = new ArrayList<Cell>();
        decoratorList = decorators.getDecorators();
        for(Cell d : decoratorList){
            
           d.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e) {
                   Cell tempCell = (Cell) e.getSource();     // Find the source of event, needs to be cast but we can be sure its a Cell
                   activeIcon = (ImageIcon) tempCell.getIcon();
                   //changeActiveIcon(tempCell);
                   updateActiveBlock();
                   
                   
                   mapPanelWrapper.setLayer(mapDecoratorPanel, new Integer(0) );
                   mapPanelWrapper.setLayer(mapTilePanel, new Integer(-1) );
                   activeMap = currentDecoMap;
                   
                   System.out.println("---------------------------------");
                   System.out.println("Active ID: " + activeId);
                   System.out.println("---------------------------------");
               }
           });
           
           d.setBorder(null);
           d.setBorderPainted(false);
           d.setContentAreaFilled(false);
           d.setOpaque(false);
            
           jpanel.add(d);
           d.updateUI();
        }
    }
    
    public void setAllToActive(){
        ArrayList<Cell> list;
        list = activeMap.getCells();
        for(Cell c : list){
            c.setIcon(activeIcon);
        }
    }
    
    public void setAllBlockTo(boolean bool){
        ArrayList<Cell> list;
        list = activeMap.getCells();
        for(Cell c : list){
            c.setBlockable(bool);
        }
    }
    
    /* Creates a new JPanel with Tool-buttons inside, then returns the panel */
    public JPanel createBlockButtons(){
        JPanel blockPanel = new JPanel();
        blockPanel.setBackground( new Color(67,67,67) );
        
            /* If this button is pressed, all tiles after will be in BLOCK-state */
            JButton block = new JButton("Block");
            block.setIcon(null);
            block.setBackground( new Color(110,110,110) );
            block.setForeground(Color.WHITE);
            block.setSize(new Dimension(100, 64));
            block.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e) {
                   isBlockerActive = true;
                   updateActiveBlock();
               }
           });
            
           /* If this button is pressed, all tiles after will NOT be in BLOCK-state */
            JButton noBlock = new JButton("No Block");
            noBlock.setIcon(null);
            noBlock.setBackground( new Color(110,110,110) );
            noBlock.setForeground(Color.WHITE);
            noBlock.setSize(new Dimension(100, 64));
            
            noBlock.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e) {
                   isBlockerActive = false;
                   updateActiveBlock();
               }
           });
           
           /* This buttons Icon shows the current active tool */
            activeBlock = new JButton("Active Icon ");
            activeBlock.setBackground( new Color(110,110,110) );
            activeBlock.setForeground(Color.WHITE);
            activeBlock.setSize(new Dimension(120, 32));
            updateActiveBlock();
            
        /* Adds our buttons to the panel */
        blockPanel.add(activeBlock);  
        blockPanel.add(block);
        blockPanel.add(noBlock);
        /* Returns out panel */
        return(blockPanel);
    }
    
    public void updateActiveBlock(){
        activeBlock.setIcon(activeIcon);
        if(isBlockerActive == true){
            activeBlock.setBorder( new LineBorder(Color.RED, 1) );
        }else{
            activeBlock.setBorder( new LineBorder(Color.BLACK, 1) );
        }
    }
    
    public void exportMap() throws IOException{
        
        fileName = JOptionPane.showInputDialog ("Enter another awesome mapname");
                                   
        if(fileName == "" || fileName == null){
            fileName = "unknown";
        }
        
        ExportMap export = new ExportMap(currentBaseMap,currentDecoMap,fileName);
    }
    
    public Map getMap(){
        return(activeMap);
    }
    
    public void saveMap() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("maps/" + fileName));
            out.writeObject(activeMap);
            out.close();
            System.out.println("Successfully saved: " + fileName);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
     public void loadMap() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("maps/" + fileName));
            
            removeMap(mapTilePanel);
            activeMap = (Map)in.readObject();
            addMapToPanel(activeMap,mapTilePanel);
            updateMap();
            
            in.close();
            System.out.println("Successfully loaded: " + fileName);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Couldnt Load For Some Good Reason");
        }
        System.out.println("After Load");
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public String getFileName(){
        return(fileName);
    }
}



