package Networking;

import java.util.ArrayList;

import Character.*;
import Character.Character;
import Item.ArmorItem;
import Item.Item;
import Item.LifeItem;
import Item.MoneyItem;
import Item.WeaponItem;

import Utility.Entity;
import World.*;

import com.esotericsoftware.kryo.Kryo;

import com.esotericsoftware.kryonet.EndPoint;
import javax.swing.*;

public class Network {



	// This class is a convenient place to keep things common to both the client and server.
	static public final int tcpport = 55551;
	static public final int udpport = 55552;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint){
		Kryo kryo = endPoint.getKryo();
		
		kryo.register(java.util.ArrayList.class);
		kryo.register(java.util.Vector.class);
		kryo.register(java.util.HashMap.class);
		
		kryo.register(ClientPacket.class);
		kryo.register(ServerPacket.class);
		
		kryo.register(Entity.class);
		
		kryo.register(AttributeCharacter.class);
		kryo.register(Character.class);
		kryo.register(EnemyCharacter.class);
		kryo.register(Interactable.class);
		kryo.register(PlayerCharacter.class);
		kryo.register(ShopCharacter.class);
		
		kryo.register(ArmorItem.class);
		kryo.register(Item.class);
		kryo.register(LifeItem.class);
		kryo.register(MoneyItem.class);
		kryo.register(WeaponItem.class);
		
		kryo.register(World.class);
		kryo.register(Map.class);
		kryo.register(Tile.class);
		kryo.register(BlockTile.class);
		kryo.register(DoorTile.class);
		
		/*
		kryo.register(GUI.GameView.class);
		kryo.register(Handler.AudioHandler.class);
		kryo.register(java.awt.Color.class);
		kryo.register(float[].class);
		kryo.register(javax.swing.JRootPane.class);
		kryo.register(javax.swing.ActionMap.class);
		
		
		*/
	}
	
	static public class ClientPacket {
		public static String message;
		public static PlayerCharacter player;
		//public String value;
	}
	
	static public class ServerPacket {
		public static String message;
		public static World world;
		public static ArrayList<PlayerCharacter> players;
		public static PlayerCharacter clientPlayer;
	}
}
