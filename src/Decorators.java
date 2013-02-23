import java.io.*;
import javax.swing.ImageIcon; 
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Decorators
{
    private int decoratorCount; 
    private TreeMap<Integer, ImageIcon> idIconMap;
    private ArrayList<Cell> decoratorList;
    private String filePath;

    public Decorators()
    {
        decoratorList = new ArrayList<Cell>();
           
        decoratorCount = countDecorators();
        addImageIcons();

        for(int i = 0 ; i<decoratorCount ; i++){
            
            Cell cell = new Cell(0,idIconMap.get(i),0,0,false);
            cell.setPreferredSize(new Dimension(32, 32));

            decoratorList.add(cell);
        }
    }

    public int countDecorators(){
        File f = new File("images/decorators");
        int decoratorCount = -1;  // Minus one for the actual directory
        for (File file : f.listFiles()) {
            if (file.isFile()) {
                decoratorCount++;
            }
        }
        System.out.println("Decorators: " + decoratorCount);
        return(decoratorCount);  
    }

    public void addImageIcons(){
        File f = new File("images/decorators");
        
        idIconMap = new TreeMap<Integer, ImageIcon>();
        
        int i = 0;
        
        for (File file : f.listFiles()) {
            if (file.isFile()) {
                
                filePath = file.toString();
                ImageIcon fileIcon = new ImageIcon(filePath);
                idIconMap.put(i,fileIcon);
                i++;
            }
        }
    }
    
    public ArrayList<Cell> getDecorators(){
        return(decoratorList);
    }
}
