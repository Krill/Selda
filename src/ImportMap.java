import java.io.BufferedReader;
import java.io.IOException;

import World.BlockTile;
import World.Tile;


public class ImportMap {

	public ImportMap(){
		
		
		
	}
	
	public void readBackTiles(BufferedReader reader)
			throws IOException
			{
				String totLine = null;
				int x = 0;
				int y = 0;
				
				while((totLine = reader.readLine()) != null)
				{	
					if(totLine.equals("[BLOCKTILES]"))
					{
						break;
					}
					
					String[] lines = totLine.split(" ");
					
					for(String line : lines)
					{
						backTiles.add(new Tile(Integer.parseInt(line), x, y , width, height));
						x += width;
					}
					
					x = 0;
					y += height;
				}
			}

			/**
			 * Reads the blockTiles. Ends when it reads EOF.
			 * @param reader
			 * @throws IOException
			 * @Deprecated
			 */
			public void readBlockTiles(BufferedReader reader)
			throws IOException
			{
				String totLine = null;
				int x = 0;
				int y = 0;
				
				while((totLine = reader.readLine()) != null)
				{	
					if(totLine.equals("[DOORTILES]"))
					{
						break;
					}
					String[] lines = totLine.split(" ");
					
					for(String line : lines)
					{
						//Hur ska man läsa in ishabitable och is pushable??
						if(Integer.parseInt(line) != 0)
						{
							currentBase.getCells();
							blockTiles.add(new BlockTile(Integer.parseInt(line), x, y , width, height, false));
						}
						x += width;
					}
					
					x = 0;
					y += height;
				}
			}
	
}
