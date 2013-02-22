import java.io.*;
import java.util.*;

public class ExportMap{
    public ExportMap(Map currentBase, Map currentDecoMap, String fileName){
        try{
            File file = new File("maps/" + fileName + ".txt");

            if( !file.exists() ){
                file.createNewFile(); // Create file 

                FileWriter fstream = new FileWriter("maps/" + fileName + ".txt");
                BufferedWriter out = new BufferedWriter(fstream);

                ArrayList<Cell> cellList = currentBase.getCells();

                System.out.println("New file " + fileName + ".remap has been created to the current directory");

                out.write(fileName + "\n");
                out.write("[BACKTILES] \n");
                for(Cell c : cellList){
                    out.write(c.getId() + " ");
                    if( c.getCol() == currentBase.getColumns()-1 ){
                        out.write(" \n");
                    }

                }
                out.write("[BLOCKTILES] \n");
                                for(Cell c : cellList){
                    out.write(c.getBlockable() + " ");
                    if( c.getCol() == currentBase.getColumns()-1 ){
                        out.write(" \n");
                    }

                }
                
                out.close();  //Close the output stream
            }else{
                System.out.println("File with same name already exists");
            }
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}