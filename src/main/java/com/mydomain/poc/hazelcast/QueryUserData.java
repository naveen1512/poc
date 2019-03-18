package com.mydomain.poc.hazelcast;

import java.util.Collection;
import java.util.HashMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;

public class QueryUserData {
	private static HazelcastInstance client = null;
	private static IMap<String, HashMap<String, String>> imap = null;
	private static String clusterAddress = "master-slave-0";
	private static boolean compressData = true;

	public static void main(String[] args) {
		connect();

		try {

//			getAllUsers();
			getUserOnEmail("singh@gamil.com");
//			getUsingKey();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownConnection();
		}

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

	private static void getAllUsers() {
//		IMap<String, UserData> imap = client.getMap("users");

		Collection<HashMap<String, String>> values = imap.values();

		System.out.println("All data: ");
		for (HashMap<String, String> value : values) {
			System.out.println(value);
		}
	}

	private static void getUserOnEmail(String email) {
		EntryObject eo = new PredicateBuilder().getEntryObject();
		Predicate<String, String> predicate = eo.key().equal(email);
		
		Collection<HashMap<String, String>> userDatas = imap.values(predicate);

		System.out.println("Quering DB: ");
		for (HashMap<String, String> value : userDatas) {
			System.out.println(value);
		}
	}

	private static void getUsingKey() {
		System.out.println("Data: " + imap.get("1"));
	}

	private static void shutdownConnection() {
		client.shutdown();
//		client.getLifecycleService().shutdown();
	}
}
