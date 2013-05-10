package Engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import Character.PlayerCharacter;
import Character.Character;
import World.World;
import Engine.Collision;
import Main.Main;

import Networking.Network;
import Networking.Network.ClientPacket;
import Networking.Network.ServerPacket;
//import Networking.ClientPacket;
//import Networking.ServerPacket;

import com.esotericsoftware.kryonet.*;
import com.esotericsoftware.kryo.*;
import com.esotericsoftware.minlog.Log;

import org.objectweb.*;

/**
 * GameEngine
 */
public class ServerEngine implements Runnable, Serializable{

	// networking
	Server server;

	// fields:
	private static final long serialVersionUID = 12L;
	private World world;
	private ArrayList<PlayerCharacter> players;
	private Collision collision;	

	// constants:
	private static final int PLAYER_WIDTH = 22;
	private static final int PLAYER_HEIGHT = 28;
	private static final int PLAYER_LIFE = 100;
	private static final int PLAYER_MONEY = 100;
	private static final int PLAYER_INVENTORY_SIZE = 6;
	private static final int PLAYER_MAXHEALTH = 100;

	/**
	 * Constructor
	 */
	public ServerEngine(){

		world = new World(1);
		players = new ArrayList<PlayerCharacter>();
		collision = new Collision(players, world.getCurrentMap().getBlockTiles());

		initServer();
		server.start();
	}

	public synchronized void initServer(){
		server = new Server(32768,8192);
		Network.register(server);

		server.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				Log.trace("[SERVER] Server received an object.." + object.getClass());

				if(object instanceof String){
					Log.trace("[SERVER] Received string: " + (String)object);
				}

				if (object instanceof ClientPacket) {
					ClientPacket request = (ClientPacket)object;

					Log.debug("[SERVER] Server recieved a ClientPacket: " + ClientPacket.message + ", from: " + connection.getRemoteAddressTCP());

					if( ClientPacket.message.equals("join_request")){
						ServerPacket joinResponse = new ServerPacket();
						ServerPacket.message = "join_request_approved";
						PlayerCharacter createdPlayer = createPlayer(ClientPacket.player.getName());
						ServerPacket.message = "join_request_approved";
						ServerPacket.clientPlayer = createdPlayer;
						connection.sendTCP(joinResponse);
						Log.debug("[SERVER] A player joined the server: " + ClientPacket.player.getName());
					}else if( ClientPacket.message.equals("client_player_update")){
						PlayerCharacter player = ClientPacket.player;
						updatePlayer(player);
					}
				}
			}
		});
		Log.info("[SERVER] Binding ports, TCP = " + Network.tcpport);
		try {server.bind(Network.tcpport);} catch (IOException e) {e.printStackTrace();}
		try {Log.info("[SERVER] Server initiated at address: " + InetAddress.getLocalHost().getHostAddress());} catch (UnknownHostException e) {e.printStackTrace();}
	}



	/**
	 * Here goes all things that should constantly get updated
	 */
	@Override
	public void run() {
		while(true){	
			if( !players.isEmpty()){
				Log.debug("[SERVER][RUN] Updating players...");
				Iterator<PlayerCharacter> it = players.iterator();
				while(it.hasNext()){
					PlayerCharacter player = it.next();
					Log.debug(":---[SERVER][RUN] Before update: " + player.getName() + ", " + player.getX() + ", " + player.getY() + ", " + player.getDx() + ", " + player.getDy());
					player.update();
					Log.debug(":------[SERVER][RUN] Updating " + player.getName());
					Log.debug(":---[SERVER][RUN] After update:" + player.getName() + ", " + player.getX() + ", " + player.getY() + ", " + player.getDx() + ", " + player.getDy());

				}
				Log.debug("[SERVER][RUN] Done updating players.");

				collision.update();
				updateClients();
			}else{
				Log.debug("[SERVER] No players..");
			}
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	public synchronized void updatePlayer(PlayerCharacter player){
		Log.debug("[SERVER][CLIENTUPDATE] Server received a client player to update...");
		Log.debug("[SERVER][CLIENTUPDATE]  " + player.getName() + "," + player.getX() + "," + player.getY() + "," + player.getDx() + "," + player.getDy());
		Iterator<PlayerCharacter> it = players.iterator();
		while(it.hasNext()){
			PlayerCharacter p = it.next();
			if(p.equals(player)){
				Log.debug("[SERVER][CLIENTUPDATE] Player: " + p.getName() + " was replaced with " + player.getName());
				p = player;
			}
		}

	}

	public synchronized PlayerCharacter createPlayer(String playerName){
		PlayerCharacter player = new PlayerCharacter(0, 350, 420, PLAYER_WIDTH, PLAYER_HEIGHT, playerName, PLAYER_LIFE, true, 1, PLAYER_MONEY, PLAYER_INVENTORY_SIZE, PLAYER_MAXHEALTH);	
		players.add(player);
		Log.debug("[SERVER][CREATEPLAYER] " + playerName + " created");
		Log.debug("[SERVER][CREATEPLAYER] Players on the server: " + players.size());
		Iterator<PlayerCharacter> it = players.iterator();
		while(it.hasNext()){
			PlayerCharacter p = it.next();
			Log.debug(":---[SERVER] " + p.getName());
		}
		return player;
	}		

	public synchronized void updateClients(){
		if( !server.getConnections().equals(null)){
			Log.debug("[SERVER][UPDATECLIENTS] Sending server state to all clients...");
			ServerPacket sendPacket = new ServerPacket();
			ServerPacket.message = "update";
			ServerPacket.players = players;
			ServerPacket.world = world;
			Log.debug("[SERVER][UPDATECLIENTS] sendPacket contains:  MSG = " + ServerPacket.message + ", Players = " + ServerPacket.players + ", World = " + world.getID());
			server.sendToAllTCP(sendPacket);

		}else{
			Log.debug("[SERVER][UPDATECLIENTS] No connections to the server...");
		}
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

	/**
	 * Returns the Collision object
	 * @return collision The collision manager
	 */
	public Collision getCollision(){
		return collision;
	}

	/**
	 * Sets the Collision when you have loaded a previous game
	 * @param collision The collision manager
	 */
	public void setCollision(Collision collision){
		this.collision = collision;
	}

	/**
	 * Returns a List of characters, thats currently in the map
	 * @return characters List of Characters
	 */
	public ArrayList<PlayerCharacter> getPlayers(){
		return players;
	}
}
