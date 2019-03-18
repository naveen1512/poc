package com.mydomain.poc.hazelcast;

import java.util.HashMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class ProduceUserData {
	private static HazelcastInstance client = null;
	private static IMap<String, HashMap<String, String>> imap = null;
	private static String clusterAddress = "master-slave-0";
	private static boolean compressData = true;

	public static void main(String[] args) {
		connect();

		generateData();

		shutdownConnection();
	}

	private static void connect() {
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();

		networkConfig.addAddress(clusterAddress);
		clientConfig.setNetworkConfig(networkConfig);

		// Set the UserData Serializer
//		SerializerConfig userDataSerializer = new SerializerConfig().setTypeClass(UserData.class)
//				.setImplementation(new UserDataSerializer(compressData));
//		clientConfig.getSerializationConfig().addSerializerConfig(userDataSerializer);

		client = HazelcastClient.newHazelcastClient(clientConfig);
		System.out.println("Members: " + client.getCluster().getMembers());

		imap = client.getMap("users1");
	}

	private static void generateData() {

//		imap.set("1", new UserData("1", "Naveen", "naveen@gamil.com"));
//		imap.set("2", new UserData("2", "Prakash", "prakash1@gamil.com"));
//		imap.set("3", new UserData("3", "Singh", "singh@gamil.com"));
//		imap.set("4", new UserData("4", "Amit", "amit@gamil.com"));
//		imap.set("5", new UserData("5", "Prakash", "prakash2@gamil.com"));

		HashMap<String, String> value = new HashMap<>();
		value.put("name", "Naveen");
		value.put("email", "naveen@gmail.com");
		
		imap.set("1", value);
		
		System.out.println("Size: " + imap.size());
	}

	private static void shutdownConnection() {
		client.shutdown();
	}
}
