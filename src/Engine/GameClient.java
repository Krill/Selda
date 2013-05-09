package Engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.esotericsoftware.kryo.*;
import com.esotericsoftware.kryonet.*;

import Networking.ClientPacket;
import Networking.ServerPacket;
import Networking.Network;

import World.World;
import Character.PlayerCharacter;

public class GameClient implements Runnable{
	private Client client;
	private ArrayList<PlayerCharacter> players;
	private PlayerCharacter player;
	private World world;
	
	public GameClient(String playerName, String localAddress){
		
		world = new World(1);
		player = new PlayerCharacter(0, 0, 0, 0, 0, playerName, 0, false, 0, 0, 0, 0); // Creates a dummy player, just for the join request
		initClient(localAddress);
		joinServer();
	}
	
	@Override
	public void run(){
		while(true){	
			updateServer();
			System.out.println("[CLIENT] Client running...");
			try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void initClient(String localAddress){
		// Creates a client
		client = new Client();
		Network.register(client);
		client.start();
		try {client.connect(5000, localAddress, Network.tcpport, Network.udpport);} catch (IOException e) {e.printStackTrace();}
		System.out.println("[CLIENT] Client Initiated!");
		System.out.println("[CLIENT] RTT to Server: " + client.getReturnTripTime() + "ms.");
		
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				
				
				if (object instanceof String) {
					String response = (String)object;
					// If join request was approved, and a connection is established, ask for a playerCharacter.
					if( response.equals("join_request_approved")){
						System.out.println("Client join_request approved"); 
						if( connection.isConnected()){
							System.out.println("	Connection established with " + connection.getRemoteAddressTCP().getHostName()); 
						}
					}else{
						System.out.println("Client recieved an unknown response");
					}
				
				}//----------UPDATE CLIENT PLAYER LIST--------------------
				if (object instanceof ServerPacket){
					ServerPacket receivedPacket = (ServerPacket)object;
					// Receive a new player upon start
					if(receivedPacket.getMessage().equals("new_player")){
						player = receivedPacket.getClientPlayer();
					}
					// Receive updated player list
					if(receivedPacket.getMessage().equals("update")){
						System.out.println("Update packet received");
						player = receivedPacket.getClientPlayer();
						players = receivedPacket.getPlayers();
						world = receivedPacket.getWorld();
					}
				}//-------------------------------------------------------
			}
		});
	}

	public void joinServer(){
		// Attempt to join a server
		ClientPacket joinRequest = new ClientPacket("join_request",player);
		client.sendTCP(joinRequest);
		System.out.println("[CLIENT] Attempting to retreive character... ");
	}

	public void connectPlayer(){
		client.sendTCP(player);
	}
	
	public void updateServer(){
		ClientPacket sendPacket = new ClientPacket("player_update",player);
		client.sendTCP(sendPacket);
		System.out.println("[CLIENT] Updating Server... ");
	}
	

	/**
	 * Returns the world
	 * @return world The world
	 */
	public World getWorld(){
		return world;
	}

	/**
	 * Sets the World when you have loaded a previous game
	 * @param world The world
	 */
	public void setWorld(World world){
		this.world = world;
	}

	public ArrayList<PlayerCharacter> getPlayers(){
		return players;
	}

	public PlayerCharacter getThisPlayer(){
		return player;
	}
}