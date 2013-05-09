package Engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.esotericsoftware.kryo.*;
import com.esotericsoftware.kryonet.*;
import com.esotericsoftware.minlog.Log;

//import Networking.ClientPacket;
//import Networking.ServerPacket;
import Networking.Network;
import Networking.Network.ClientPacket;
import Networking.Network.ServerPacket;

import World.World;
import Character.PlayerCharacter;

public class GameClient implements Runnable{
	private Client client;
	private ArrayList<PlayerCharacter> players;
	private PlayerCharacter player;
	private World world;
	
	public GameClient(String playerName, String localAddress){
		System.setProperty("java.net.preferIPv4Stack" , "true");
		
		players = new ArrayList<PlayerCharacter>();
		world = new World(1);
		player = new PlayerCharacter(0, 0, 0, 0, 0, "RICHARD", 0, false, 0, 0, 0, 0); // Creates a dummy player, just for the join request
		
		initClient(localAddress);
		client.start();
		joinServer(localAddress);
	}
	
	@Override
	public void run(){
		while(true){	
			updateServer();
			System.out.println("[CLIENT] Client running...");
			System.out.println("[CLIENT] Client connected: " + client.isConnected());
			try {Thread.sleep(7000);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void initClient(String localAddress){
		client = new Client(16384,4096);
		Network.register(client);
		
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
						System.out.println("[CLIENT] Client recieved an unknown response: " + response);
					}
				
				}//----------UPDATE CLIENT PLAYER LIST--------------------
				if (object instanceof ServerPacket){
					ServerPacket receivedPacket = (ServerPacket)object;
					System.out.println("[CLIENT] Client received a ServerPacket: " + receivedPacket.message);
					// Receive a new player upon start
					if(receivedPacket.message.equals("join_request_approved")){
						player = receivedPacket.clientPlayer;
						System.out.println("[CLIENT] Join request approved by server");
					}
					// Receive updated player list
					if(receivedPacket.message.equals("update")){
						System.out.println("[CLIENT] Update packet received");
						player = receivedPacket.clientPlayer;
						players = receivedPacket.players;
						world = receivedPacket.world;
					}
				}//-------------------------------------------------------
			}
		});
	}

	public void joinServer(String address){
		try {client.connect(5000, address, Network.tcpport);} catch (IOException e) {e.printStackTrace();}
		System.out.println("[CLIENT] RTT to Server: " + client.getReturnTripTime() + " ms.");
		ClientPacket joinRequest = new ClientPacket();
		joinRequest.message = "join_request";
		joinRequest.player = player;
		client.sendTCP(joinRequest);
		System.out.println("[CLIENT] Sending join_request... ");
	}
	public void updateServer(){
		System.out.println("[CLIENT] Updating Server... , Dx: " + player.getDx() + ", Dy: " + player.getDy());
		ClientPacket sendPacket = new ClientPacket();
		sendPacket.message = "client_player_update";
		sendPacket.player = player;
		client.sendTCP(sendPacket);
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

	public PlayerCharacter getClientPlayer(){
		return player;
	}
}