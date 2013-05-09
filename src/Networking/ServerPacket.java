package Networking;

import java.util.ArrayList;

import Character.PlayerCharacter;
import World.World;

public class ServerPacket {
	private String message;
	private World world;
	private ArrayList<PlayerCharacter> players;
	private PlayerCharacter clientPlayer;
	
	public ServerPacket(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public void setPlayers(ArrayList<PlayerCharacter> players){
		this.players = players;
	}
	public ArrayList<PlayerCharacter> getPlayers(){
		return players;
	}
	public void setWorld(World world){
		this.world = world;
	}
	public World getWorld(){
		return world;
	}
	public PlayerCharacter getClientPlayer(){
		return clientPlayer;
	}
	public void setClientPlayer(PlayerCharacter clientPlayer){
		this.clientPlayer = clientPlayer;
	}
}
