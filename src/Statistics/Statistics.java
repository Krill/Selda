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
import java.util.Observable;
import java.util.Observer;




/**
 * Class responsible for keeping statistics for a player.
 * It saves the number of quests completed and monsters killed by the player.
 * Also it will calculate the worth of these two combined, into a common score.
 * @author Johan
 *
 */
public class Statistics implements Serializable{
	private int monsterKilled;
	private int questsCompleted;
	private String name;
	
	/**
	 * Creates a clean statistics with 0 in all statistics.
	 */
	public Statistics(String name)
	{
		monsterKilled = 0;
		questsCompleted = 0;
		this.name = name;
	}
	
	public int getTotalScore()
	{
		return monsterKilled + (questsCompleted * 5);
	}
	
	
	/**
	 * Increases the number of monsters killed.
	 */
	public void incMonstersKilled()
	{
		monsterKilled++;
	}
	
	
	/**
	 * Increases the number of quests completed.
	 */
	public void incQuestsCompleted()
	{
		questsCompleted++;
	}
	
	
	/**
	 * Returns the number of monsters killed.
	 * @return Monsters killed
	 */
	public int getMonstersKilled()
	{
		return monsterKilled;
	}
	
	
	/**
	 * Returns the number of quests completed.
	 * @return Quests completed
	 */
	public int getQuestsCompleted()
	{
		return questsCompleted;
	}
	
	
	
	
	
	
	
	public String[] refreshScore()
	{	
		try
		{
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
	
	
	public class Score
	{
		private String name;
		private int score;
		private String time;

		public Score(String name, int score, String time)
		{
			this.name = name;
			this.score = score;
			this.time = time;
		}

		public String getName()
		{
			return name;
		}

		public int getScore()
		{
			return score;
		}

		public String getTime()
		{
			return time;
		}

		public String getText()
		{
			return "<html>" + name + " scored: " + score + ".<br>" + time +"</html>";
		}
	}
	
}
