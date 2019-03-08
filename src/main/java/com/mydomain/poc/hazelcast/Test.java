package com.mydomain.poc.hazelcast;

import java.util.ArrayList;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.client.impl.clientside.HazelcastClientInstanceImpl;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Test {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();

		networkConfig.addAddress("10.14.24.70");
		clientConfig.setNetworkConfig(networkConfig);

		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

		IMap<Object, Object> imap = client.getMap("customers");
		System.out.println("Size: " + imap.size());

		System.out.println("Members: " + client.getCluster().getMembers());
	}

}
