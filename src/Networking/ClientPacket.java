package Networking;

import Character.PlayerCharacter;

public class ClientPacket {
	
	private PlayerCharacter player;
	private String message;
	private String value;
	
	public ClientPacket(String message, PlayerCharacter player){
		this.player = player;
		this.message = message;
	}
	public String getMessage(){
		return message;
	}
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public PlayerCharacter getPlayer(){
		return player;
	}
	public void setPlayer(PlayerCharacter player){
		this.player = player;
	}
}
