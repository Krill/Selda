import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.*;

public class Map extends JPanel implements Serializable
{
    private ImageIcon defaultIcon = null;

    private int map_row;
    private int map_col;
    private Cell cell;
    private ArrayList<Cell> list;
    
    public Map(int map_row, int map_col)
    {
        list = new ArrayList<Cell>();
        
        for(int i = 0 ; i<map_row ; i++){
            for(int j = 0 ; j<map_col ; j++){
                
                this.map_row = map_row;
                this.map_col = map_col;
                
                cell = new Cell(0,defaultIcon,j,i,false);
                cell.setPreferredSize(new Dimension(32, 32));
                list.add(cell);
            }
        }
    }
    public int getColumns(){
        return(map_col);
    }
    public int getRows(){
        return(map_row);
    }
    
    public ArrayList<Cell> getCells(){
        return(list);
    }
}
