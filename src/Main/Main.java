package Main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import Engine.GameClient;
import Engine.ServerEngine;
import GUI.GameView;
import GUI.StartScreen;
import Networking.Network;

import com.esotericsoftware.kryonet.*;
import com.esotericsoftware.kryo.*;
import com.esotericsoftware.minlog.Log;

/**
 * 
 * @author kristoffer & johan
 */
public class Main {

	// fields:
	private static StartScreen startScreen;
	private static GameView gameView;
	private static ServerEngine serverEngine;
	private static GameClient gameClient;
	private static Thread gameClientThread;
	private static Thread serverEngineThread;
	private static Thread viewThread;
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {	
		Log.set(Log.LEVEL_DEBUG);
		
		Log.info("[MAIN][TESTCLIENT] Discovering servers...");
		Client testClient = new Client(Network.tcpport, Network.udpport);
		
		InetAddress firstAddress = testClient.discoverHost(Network.udpport, 1000);
		/*if(firstAddress.equals(null)){
			System.out.println("[MAIN][TESTCLIENT] No servers were found");
		}else{
			String firstAddressString = firstAddress.toString();
			System.out.println("[MAIN][TESTCLIENT] First server found: " + firstAddressString);
		}*/
		
		try {Log.info("[MAIN] Local IP: " + InetAddress.getLocalHost());} catch (UnknownHostException e) {e.printStackTrace();}
		Log.info("[MAIN] LoopBack IP: " + InetAddress.getLoopbackAddress());
		
		startScreen = new StartScreen();
	}
	
	public static void hostGame(){
		// Start server
		serverEngine = new ServerEngine();
		serverEngineThread = new Thread(serverEngine);
		Log.debug("[MAIN][THREAD] Starting serverEngineThread...");
		serverEngineThread.start();
		// Connect to the server
		//System.out.println("Trying to join Server...");
		//try {joinGame(InetAddress.getLocalHost().getHostAddress());} catch (UnknownHostException e) {e.printStackTrace();}
	}
	
	public static void joinGame(String localAddress){
		Log.debug("[MAIN][GAMECLIENT] Trying to connect to: " + localAddress);
		Log.debug("[MAIN][GAMECLIENT] Creating gameClient");
		gameClient = new GameClient("ClientPlayer", localAddress);
		gameClientThread = new Thread(gameClient);
		
		gameView = new GameView(gameClient);
		viewThread = new Thread(gameView);
		
		Log.debug("[MAIN][THREAD] Starting gameViewThread");
		viewThread.start();
		Log.debug("[MAIN][THREAD] Starting gameClientThread");
		gameClientThread.start();
		
	}
	
	/**
	 * When the Player has loaded a current state of the game old
	 * threads stop and new ones are created. Loads the new state.
	 */
	@SuppressWarnings("deprecation")
	public static void restart(){
		if(inGame){
			engineThread.stop();
			viewThread.stop();
			gameView.dispose();
		}
	
		gameView = new GameView(gameClient);
		
		engineThread = new Thread(gameClient);
		viewThread = new Thread(gameView);
		
		engineThread.start();
		viewThread.start();
	}
}
