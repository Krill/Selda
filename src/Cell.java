import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon; 
import javax.swing.border.LineBorder;
import java.io.*;

public class Cell extends JButton implements Serializable
{
    private ImageIcon activeIcon = new ImageIcon("images/tiles/0.gif");

    int col;
    int row;
    int id;
    boolean isBlocker;

    /**
     * Constructor for objects of class Cell
     */
    public Cell(int id, Icon icon, int col, int row, boolean isBlocker)
    {
        super(icon);
        this.id = id;
        this.col = col;
        this.row = row;
        this.isBlocker = isBlocker;
        
        setBorder( new LineBorder(Color.BLACK, 1) );
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }

    public int getCol()
    {
        return col;
    }

    public int getRow()
    {
        return row;
    }

    public void changeActiveIcon(Cell cell){
        activeIcon = (ImageIcon) cell.getIcon();
    }

    public ImageIcon getActiveIcon(){
        return(activeIcon);
    }

    public void setActiveIcon(ImageIcon icon){
        activeIcon = icon;
    }

    public void setBlockable(boolean bool){
        isBlocker = bool;
        if(isBlocker == true){
            setBorder( new LineBorder(Color.RED, 1) );
        }else{
            setBorder( new LineBorder(Color.BLACK, 1) );
        }
    }
    
    public int getBlockable(){    // Returns 1 or 0 for the Save and Load
        if(isBlocker == true){
            return(1);
        }else{
            return(0);
        }
    }
}
