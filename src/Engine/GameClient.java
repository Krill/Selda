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
		player = new PlayerCharacter(0, 0, 0, 0, 0, "RICHARD", 0, false, 0, 0, 0, 0); // Creates a dummy player, just for the join_request and to fool the GamePanel
		
		initClient(localAddress);
		client.start();
		joinServer(localAddress);
	}
	
	@Override
	public synchronized void run(){
		while(true){	
			updateServer();
			Log.debug("[CLIENT][RUN] Connection status to: " + client.getRemoteAddressTCP() + " is " + client.isConnected());
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public synchronized void initClient(String localAddress){
		client = new Client(16384,4096);
		Network.register(client);
		
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof String) {
					String response = (String)object;
					// If join request was approved, and a connection is established, ask for a playerCharacter.
					if( response.equals("join_request_approved")){
						Log.debug("[CLIENT] Client join_request approved"); 
						if( connection.isConnected()){
							Log.debug("[CLIENT]	Connection established with " + connection.getRemoteAddressTCP().getHostName()); 
						}
					}else{
						Log.debug("[CLIENT] Client recieved an unknown response: " + response);
					}
				
				}//----------UPDATE CLIENT PLAYER LIST--------------------
				if (object instanceof ServerPacket){
					ServerPacket receivedPacket = (ServerPacket)object;
					Log.debug("[CLIENT] Client received a ServerPacket: " + ServerPacket.message);
					// Receive a new player upon start
					if(ServerPacket.message.equals("join_request_approved")){
						player = ServerPacket.clientPlayer;
						Log.debug("[CLIENT] Join request approved by server");
					}
					// Receive updated player list
					if(ServerPacket.message.equals("update")){
						Log.debug("[CLIENT] Update packet received");
						player = ServerPacket.clientPlayer;
						players = ServerPacket.players;
						world = ServerPacket.world;
						
						
						/*Iterator<PlayerCharacter> it = players.iterator();
						while(it.hasNext()){
							PlayerCharacter listPlayer = it.next();
							if(listPlayer.equals(player)){
								player = listPlayer;
								it.remove();
								Log.debug("[CLIENT] " + listPlayer.getName() + " removed.");
							}
						}
						
						Log.debug("[CLIENT] Playerlist: ");
						for( PlayerCharacter p : players){
							Log.debug("  -" + p.getName());
						}
						Log.debug("[CLIENT] CLientPlayer name :" + player.getName());
						*/
					}
				}//-------------------------------------------------------
			}
		});
	}

	public synchronized void joinServer(String address){
		try {client.connect(5000, address, Network.tcpport);} catch (IOException e) {e.printStackTrace();}
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("[CLIENT] RTT to Server: " + client.getReturnTripTime() + " ms.");
		ClientPacket joinRequest = new ClientPacket();
		ClientPacket.message = "join_request";
		ClientPacket.player = player;
		client.sendTCP(joinRequest);
		Log.debug("[CLIENT] Sending join_request... ");
	}
	public synchronized void  updateServer(){
		Log.debug(":---[CLIENT] Updating Server");
		Log.debug(":------[CLIENT] " + player.getName() + ", " + player.getX() + ", " + player.getY() + ", " + player.getDx() + ", " + player.getDy());
		ClientPacket sendPacket = new ClientPacket();
		ClientPacket.message = "client_player_update";
		ClientPacket.player = player;
		client.sendTCP(sendPacket);
		Log.debug(":---[CLIENT] Client Update sent. ");
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