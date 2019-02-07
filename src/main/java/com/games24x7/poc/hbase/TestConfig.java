package com.games24x7.poc.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.KeyValue;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

public class TestConfig {
	private static final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	private static final String HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT = "hbase.zookeeper.property.clientPort";

	private static final String hbaseZookeeeperHost = "localhost";
	private static final String hbaseZookeeeperPort = "2181";

	private static final String tablename = "currentwithdrawable";

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration hbaseConf = HBaseConfiguration.create();

		hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_QUORUM, hbaseZookeeeperHost);
		hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT, hbaseZookeeeperPort);

		HBaseAdmin.available(hbaseConf);

		Connection hbaseConn = ConnectionFactory.createConnection(hbaseConf);
		Admin hbaseAdmin = hbaseConn.getAdmin();
		Table table = hbaseConn.getTable(TableName.valueOf(tablename));

		System.out.println("Master Server Name: " + hbaseAdmin.getMaster());

		// All table description
//		List<TableDescriptor> tableList = hbaseAdmin.listTableDescriptors();
//
//		for (TableDescriptor tableDesc : tableList) {
//			System.out.println(tableDesc);
//		}

		// Insertion of data
//		Put put = new Put(Bytes.toBytes("1"));
//
//		put.addColumn(Bytes.toBytes("userinfo"), Bytes.toBytes("userId"), Bytes.toBytes(1234));
//
//		put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("orderId"), Bytes.toBytes("KU9H13RXRT"));
//		put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"), Bytes.toBytes(105.0));
//		put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("paymentMode"), Bytes.toBytes(0));
//		put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("paymentModeOption"), Bytes.toBytes("UPI"));
//		put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("channelId"), Bytes.toBytes(3));
//		put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("status"), Bytes.toBytes(1));
//		put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("timestamp"), Bytes.toBytes(1549275838));
//
//		put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("amount"), Bytes.toBytes(127.69));
//		put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("depositAmt"), Bytes.toBytes(100000.0));
//		put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("withdrawAmt"), Bytes.toBytes(27.69));
//		put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("nonWithdrawAmt"), Bytes.toBytes(0.0));
//
//		table.put(put);

		// Deletion of data
		for (int i = 0; i < 10; i++) {
			Delete delete = new Delete(Bytes.toBytes("1"));
			delete.addFamily(Bytes.toBytes("userinfo"));
			delete.addFamily(Bytes.toBytes("transactioninfo"));
			delete.addFamily(Bytes.toBytes("amountinfo"));

			table.delete(delete);
		}

		// Scanning the table.
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("userinfo"), Bytes.toBytes("userId"));
		scan.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"));

		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println("Found Row: " + result);
			System.out.println("txnAmount: "
					+ Bytes.toDouble(result.getValue(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"))));
		}

		// Reading of data
//		Get get = new Get(Bytes.toBytes("1"));
//
//		Result result = table.get(get);
//		double txnAmount = Bytes
//				.toDouble(result.getValue(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount")));
//		System.out.println("txnAmount: " + txnAmount);
	}

}
