package Handler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;



/**
 * The class responsible for playing all audio in the game. Plays files in separate threads.
 * Separates music and sound players because of extensive length of music files.
 * Utilizes third-party libraries (Javazoom.net) to play .mp3 files.
 * 
 * @author Alexander Persson
 * @version 2013-03-01
 */
public class AudioHandler{
	
	// The current players (null if no player currently connected)
    private AdvancedPlayer musicPlayer;
    private AdvancedPlayer soundPlayer;
        
    // Flag used to determine if background music is currently playing
    private boolean isPlaying;
    
    /**
     * Constructor for objects of class AudioHandler.
     */
    public AudioHandler()
    {
        musicPlayer = soundPlayer = null;
        isPlaying = false;
    }  
    
    /**
     * Start playing the given audio file.
     * The method returns once the playing has been started.
     * @param filename The file to be played.
     */
    public void startPlaying(final String filename)
    {
        try {
            setupPlayer(filename);            
            Thread playerThread = new Thread() {
                public void run()
                {
                    try {                        
                    	if(filename.contains("music/") && musicPlayer != null){                        	
                    		musicPlayer.play();                    		
                        }
                        else if(filename.contains("sounds/") && soundPlayer != null){                        	
                        	soundPlayer.play();
                        }
                    }
                    catch(JavaLayerException e) {
                        reportProblem(filename);
                    }
                    finally {
                        killPlayer(filename);
                        if(filename.contains("music/"))
                        {
                        	isPlaying = false;
                        }
                    }
                }
            };
            playerThread.start();
        }
        catch (Exception ex) {
            reportProblem(filename);
        }
    }
    
    /**
     * Stops the audio being played.
     */
    public void stopMusic()
    {
        killPlayer("music/");
    }
    
    /**
     * Returns boolean showing whether a music track is currently playing or not.
     * @return True if music is playing, false otherwise.
     */
    public boolean isPlaying()
    {
    	return isPlaying;
    }
    
    /**
     * Sets a new value for isPlaying.
     * @param arg The new value for isPlaying.
     */
    public void setPlaying(boolean arg)
    {
    	isPlaying = arg;
    }
    
    /**
     * Set up a player to play the given file.
     * @param filename The name of the file to play.
     */
    private void setupPlayer(String filename)
    {   	   	
    	try {
            InputStream is = getInputStream(filename);
            if(filename.contains("music/")){            
            	musicPlayer = new AdvancedPlayer(is, createAudioDevice());
            }
            else if(filename.contains("sounds/")){            	
            	soundPlayer = new AdvancedPlayer(is, createAudioDevice());            	
            }
        }
        catch (IOException e) {
            reportProblem(filename);
            killPlayer(filename);
        }
        catch(JavaLayerException e) {
            reportProblem(filename);
            killPlayer(filename);
        }    	    	
    }

    /**
     * Return an InputStream for the given file.
     * @param filename The file to be opened.
     * @throws IOException If the file cannot be opened.
     * @return An input stream for the file.
     */
    private InputStream getInputStream(String filename)
        throws IOException
    {
        return new BufferedInputStream(
                    new FileInputStream(filename));
    }

    /**
     * Create an audio device.
     * @throws JavaLayerException if the device cannot be created.
     * @return An audio device.
     */
    private AudioDevice createAudioDevice()
        throws JavaLayerException
    {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }

    /**
     * Terminate the player, if there is one.
     * @param filename The directory of the specified file.
     */
    private void killPlayer(String filename)
    {
        synchronized(this) {
            if(musicPlayer != null && filename.contains("music/")) {
                musicPlayer.stop();
                musicPlayer = null;
            }
            else if(soundPlayer != null && filename.contains("sounds/")) {
            	soundPlayer.stop();
            	soundPlayer = null;
            }
        }
    }
    
    /**
     * Report a problem playing the given file.
     * @param filename The file being played.
     */
    private void reportProblem(String filename)
    {
        System.out.println("There was a problem playing: " + filename);
    }
}
