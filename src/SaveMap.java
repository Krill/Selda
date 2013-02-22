import java.io.*;
import java.util.*;

public class SaveMap{
    File file;
    public SaveMap(Map map, String fileName){
        try{
            file = new File("maps/" + fileName + ".remap");

            if( !file.exists() ){
                file.createNewFile();
                System.out.println("New file " + fileName + ".remap has been created to the current directory");
            }

            // Create file 
            FileWriter fstream = new FileWriter("maps/" + fileName + ".remap");
            BufferedWriter out = new BufferedWriter(fstream);
            
            ArrayList<Cell> cellList = map.getCells();
            int colCount = 0;
            
            for(Cell c : cellList){
                out.write(" [" + c.getBlockable() + "]");
                if( c.getCol() == map.getColumns()-1 ){
                    out.write(" \n");
                }
                
            }
            
            
            
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}