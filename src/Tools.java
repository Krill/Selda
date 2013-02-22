import java.io.*;
import javax.swing.ImageIcon; 
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tools
{
    private int toolCount;
    private int id;
    
    private ArrayList<Cell> toolList;
    private TreeMap<Integer, ImageIcon> idIconMap;
    private String filePath;

    public Tools()
    {
        toolList = new ArrayList<Cell>();
        
        toolCount = countTools();
        addImageIcons();

        for(int i = 0 ; i<toolCount ; i++){
            
            Cell cell = new Cell(i,idIconMap.get(i),0,0,false);
            cell.setPreferredSize(new Dimension(32, 32));
            cell.setToolTipText(filePath);

            toolList.add(cell);
        }
    }

    public int countTools(){
        File f = new File("images/tiles");
        int toolCount = -1;  // Minus one for the actual directory
        for (File file : f.listFiles()) {
            if (file.isFile()) {
                toolCount++;
            }
        }
        System.out.println("Tools: " + toolCount);
        return(toolCount);  
    }

    public void addImageIcons(){
        File f = new File("images/tiles");
        
        idIconMap = new TreeMap<Integer, ImageIcon>();
        
        for (File file : f.listFiles()) {
            if (file.isFile()) {
                
                filePath = file.toString();
                ImageIcon fileIcon = new ImageIcon(this.getClass().getResource(filePath));
                
                String[] name = file.getName().split("\\.");
                
                idIconMap.put(Integer.parseInt(name[0]),fileIcon); 
            }
        }
    }
    
    public ArrayList<Cell> getTools(){
        return(toolList);
    }
}
