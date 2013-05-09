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

	public void initServer(){
		server = new Server(32768,8192);
		Network.register(server);
		
		server.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				System.out.println("[SERVER] Server received an object.." + object.getClass());
				
				if(object instanceof String){
					System.out.println("[SERVER] Received: " + (String)object);
				}
				
				if (object instanceof ClientPacket) {
					ClientPacket request = (ClientPacket)object;
					String clientMessage = request.message;

					System.out.println("[SERVER] Server recieved ClientPacket: " + clientMessage);

					if( clientMessage.equals("join_request")){
						ServerPacket joinResponse = new ServerPacket();
						joinResponse.message = "join_request_approved";
						//PlayerCharacter createdPlayer = createPlayer(request.getPlayer().getName());
						//joinResponse.setClientPlayer(createdPlayer);
						connection.sendTCP(joinResponse);
						//System.out.println("[SERVER] A player joined the server: " + request.getPlayer().getName());
					}else if( clientMessage.equals("player_update")){
						PlayerCharacter player = (PlayerCharacter)object;
						updatePlayer(player);
					}
				}
			}
		});
		System.out.println("[SERVER] Binding ports, TCP = " + Network.tcpport);
		try {server.bind(Network.tcpport);} catch (IOException e) {e.printStackTrace();}
		try {System.out.println("[SERVER] Server initiated at address: " + InetAddress.getLocalHost().getHostAddress());} catch (UnknownHostException e) {e.printStackTrace();}
	}



	/**
	 * Here goes all things that should constantly get updated
	 */
	@Override
	public void run() {
		while(true){	

			Iterator<PlayerCharacter> it = players.iterator();
			while(it.hasNext()){
				PlayerCharacter player = it.next();
				player.update();
			}
			collision.update();
			updateClients();

			System.out.println("[SERVER] Server running...");
			try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	public void updatePlayer(PlayerCharacter player){
		Iterator<PlayerCharacter> it = players.iterator();
		while(it.hasNext()){
			PlayerCharacter p = it.next();
			if(p.equals(player)){
				p = player;
			}
		}
		System.out.println("[SERVER] Updated a player...");
	}

	public PlayerCharacter createPlayer(String playerName){
		PlayerCharacter player = new PlayerCharacter(0, 350, 420, PLAYER_WIDTH, PLAYER_HEIGHT, playerName, PLAYER_LIFE, true, 1, PLAYER_MONEY, PLAYER_INVENTORY_SIZE, PLAYER_MAXHEALTH);	
		players.add(player);
		System.out.println(playerName + " added");
		System.out.println("	Number of players: " + players.size());
		return player;
	}

	public void updateClients(){
		ServerPacket sendPacket = new ServerPacket();
		sendPacket.message = "update";
		//sendPacket.players = players;
		//sendPacket.world = world;
		server.sendToAllTCP(sendPacket);
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
