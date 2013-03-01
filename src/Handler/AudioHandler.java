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
 * Utilizes third-party libraries (Javazoom.net) to play .mp3 files.
 * 
 * @author Alexander Persson
 * @version 2013-02-28
 */
public class AudioHandler {
	
	// The current player. It might be null.
    private AdvancedPlayer player;
    private boolean isPlaying;
    
    /**
     * Constructor for objects of class AudioHandler.
     */
    public AudioHandler()
    {
        player = null;
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
                        player.play();
                    }
                    catch(JavaLayerException e) {
                        reportProblem(filename);
                    }
                    finally {
                        killPlayer();
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
    public void stop()
    {
        killPlayer();
    }
    
    /**
     * Returns boolean showing whether a music track is currently playing or not.
     * @return isPlaying
     */
    public boolean isPlaying()
    {
    	return isPlaying;
    }
    
    /**
     * Sets a new value for isPlaying.
     * @param arg
     */
    public void setPlaying(boolean arg)
    {
    	isPlaying = arg;
    }
    
    /**
     * Set up the player ready to play the given file.
     * @param filename The name of the file to play.
     */
    private void setupPlayer(String filename)
    {
        try {
            InputStream is = getInputStream(filename);
            player = new AdvancedPlayer(is, createAudioDevice());
        }
        catch (IOException e) {
            reportProblem(filename);
            killPlayer();
        }
        catch(JavaLayerException e) {
            reportProblem(filename);
            killPlayer();
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
     */
    private void killPlayer()
    {
        synchronized(this) {
            if(player != null) {
                player.stop();
                player = null;
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
