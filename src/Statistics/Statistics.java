package Statistics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;




/**
 * Class responsible for keeping statistics for a player.
 * It saves the number of quests completed and monsters killed by the player.
 * Also it will calculate the worth of these two combined, into a common score.
 * @author Johan
 *
 */
public class Statistics implements Serializable{

	private static final long serialVersionUID = 912938;
	private int monsterKilled;
	private int questsCompleted;
	private String name;
	
	/**
	 * Creates a clean statistics with 0 in all statistics.
	 * Also contains a name to connect to the statistics.
	 * @param name Name of the player.
	 */
	public Statistics(String name)
	{
		monsterKilled = 0;
		questsCompleted = 0;
		this.name = name;
	}
	
	/**
	 * Retuns a score combined of both monsters killed and quests completed.
	 * Monsters count as 1 and quests as 5.
	 * @return totalscore The combined score.
	 */
	public int getTotalScore()
	{
		return monsterKilled + (questsCompleted * 5);
	}
	
	
	/**
	 * Increases the number of monsters killed with 1.
	 */
	public void incMonstersKilled()
	{
		monsterKilled++;
	}
	
	
	/**
	 * Increases the number of quests completed with 1.
	 */
	public void incQuestsCompleted()
	{
		questsCompleted++;
	}
	
	
	/**
	 * Returns the number of monsters killed.
	 * @return monsterKilled The number of monsters killed.
	 */
	public int getMonstersKilled()
	{
		return monsterKilled;
	}
	
	
	/**
	 * Returns the number of quests completed.
	 * @return QuestsCompleted The number of quests completed
	 */
	public int getQuestsCompleted()
	{
		return questsCompleted;
	}
	
	
	
	/**
	 * This method will get statistics from a server, then sort 
	 * the highscores according to highest points, and after that return the top 3. 
	 * @return Top3 A String[] containing the top 3 highscores. First element is the best score.
	 */
	public String[] refreshScore()
	{	
		try
		{
			//Creates data that makes the server send back the databas containing all highscore entries.
			String data = URLEncoder.encode("player_name", "UTF-8") + "=" + URLEncoder.encode("refresh", "UTF-8");
			data += "&" + URLEncoder.encode("player_score", "UTF-8") + "=" + URLEncoder.encode(""+ 0, "UTF-8");
			data += "&" + URLEncoder.encode("identifier", "UTF-8") + "=" + URLEncoder.encode("gamecontroler", "UTF-8");
			data += "&" + URLEncoder.encode("submit", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8");

			URLConnection connection = openUrl();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());


			out.write(data);
			out.flush();
			out.close();

			return updateScore(connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Send the current statistics to a server. The data send contains the name and score of the player.
	 */
	public void submitScore()
	{
		try
		{
			String data = URLEncoder.encode("player_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
			data += "&" + URLEncoder.encode("player_score", "UTF-8") + "=" + URLEncoder.encode("" + getTotalScore(), "UTF-8");
			data += "&" + URLEncoder.encode("identifier", "UTF-8") + "=" + URLEncoder.encode("gamecontroler", "UTF-8");
			data += "&" + URLEncoder.encode("submit", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8");

			URLConnection connection = openUrl();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());


			out.write(data);
			out.flush();

			updateScore(connection);

			out.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Reads the return data from the server, then sorts the highscores it read, and then return the top 3.
	 * @param connection The stream from wich to read the data.
	 * @return Top3 The top 3 highscores.
	 */
	private String[] updateScore(URLConnection connection)
	{
		try
		{
			BufferedReader rd = new BufferedReader(new
					InputStreamReader(connection.getInputStream()));

			ArrayList<Score> list = new ArrayList<>();

			String line = rd.readLine();
			line = rd.readLine();
			while (line != null && line.startsWith("Player")) {
				String lines[] = line.split("--");

				String name = lines[0];
				int score = Integer.parseInt(lines[1].split(": ")[1].split(" ")[0]);
				String time = lines[2];

				list.add(new Score(name, score, time));



				line = rd.readLine();
			}
			rd.close();


			Score[] topScores = sortScore(list);
			
			String[] scores = new String[3];
			scores[0] = topScores[0].getText();
			scores[1] = topScores[1].getText();
			scores[2] = topScores[2].getText();
			
			return scores;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Sorts all the lists scores according to highest points, then returns top 3.
	 * @param list The list to be sorted.
	 * @return Top3 The top 3 highscores.
	 */
	private Score[] sortScore(ArrayList<Score> list)
	{
		Score[] topScores = new Score[3];
		int max = 0;

		//Checks if the list got 3 elements to fill the topscores list, else create new scores to fill it out
		if(list.size() >= 3)
		{
			max = 3;
		}
		else
		{
			max = list.size();
			for(int i = 0; i < 3 -list.size(); i++)
			{
				topScores[2-i] = new Score("No score", 0, "No time");
			}
		}
		//Iterate out the best 3 scores
		for(int i = 0; i < max; i++)
		{
			Iterator<Score> it = list.iterator();
			int savedIndex = 0;
			if(it.hasNext())
			{
				topScores[i] = it.next();
				int index = 1;

				while(it.hasNext())
				{
					Score score = it.next();
					if(score.getScore() > topScores[i].getScore())
					{
						topScores[i] = score;
						savedIndex = index;
					}
					index++;
				}
			}
			list.remove(savedIndex);
		}
		return topScores;
	}
	
	
	/**
	 * Opens an URLConnection to the server, then returns it.
	 * @return URLConnection The URLConnection from the server.
	 */
	private URLConnection openUrl()
	{
		try
		{
			URL url = new URL("http://jpv-net.dyndns.org:1337/red_elephant/submitt_score.php");
			return url.openConnection();

		}
		catch(Exception e)
		{
			System.out.println("Error loading url");
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Class responsible for keeping score. Keeps track of name, score and date.
	 * @author Johan
	 *
	 */
	public class Score
	{
		private String name;
		private int score;
		private String time;

		/**
		 * Initiates the class.
		 * @param name The name of the player
		 * @param score The score of the player
		 * @param time Time of submit.
		 */
		public Score(String name, int score, String time)
		{
			this.name = name;
			this.score = score;
			this.time = time;
		}

		/**
		 * Returns the players name
		 * @return name The players name
		 */
		public String getName()
		{
			return name;
		}

		
		/**
		 * Returns the score
		 * @return score The players score
		 */
		public int getScore()
		{
			return score;
		}

		
		/**
		 * Returns the date of submit
		 * @return time The date
		 */
		public String getTime()
		{
			return time;
		}

		
		/**
		 * Returns a text containing the score.
		 * @return text A text with all info
		 */
		public String getText()
		{
			return "<html>" + name + " scored: " + score + ".<br>" + time +"</html>";
		}
	}
	
}
