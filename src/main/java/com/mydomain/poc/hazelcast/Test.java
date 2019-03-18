package com.mydomain.poc.hazelcast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.client.impl.clientside.HazelcastClientInstanceImpl;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;
import com.mydomain.poc.hazelcast.model.User;
import com.mydomain.poc.hazelcast.model.UserData;

public class Test {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();

		networkConfig.addAddress("master-slave-0");
		clientConfig.setNetworkConfig(networkConfig);

		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		System.out.println("Members: " + client.getCluster().getMembers());

		IMap<String, User> imap = client.getMap("user");

//		imap.set("1", new UserData("1", "Naveen", "naveen@gamil.com"));
//		imap.set("2", new UserData("2", "Prakash", "prakash@gamil.com"));
//		imap.set("3", new UserData("3", "Amit", "amit@gamil.com"));
//		imap.set("4", new UserData("4", "Singh", "singh@gamil.com"));

		System.out.println("Size: " + imap.size());

//		Collection<Motorbike> values = imap.values();

//		Predicate p = Predicates.equal("name", "Prakash");
//		Collection<UserData> values = imap.values(p);
		
		Collection<User> values = imap.values(new SqlPredicate("name != 'User 10'"));
		
		for (User value : values) {
			System.out.println(value);

		}

	}

}
