package com.games24x7.poc.hbase;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.hbase.util.Bytes;

public class Test {

    public static void main(String[] args) {
//        Random r = new Random();
//
//        String[] list = {"Finland", "Russia", "Latvia", "Lithuania", "Poland"};
//
////		System.out.println(list[r.nextInt(list.length)]);
//
//        Instant instant = Instant.now();
//
//        int noSecondsWeek = 604800;
//        int lowerEpoch = (int) (instant.getEpochSecond() - noSecondsWeek);
//        int upperEpoch = (int) (instant.getEpochSecond() + 1);
//        int timestamp = r.nextInt(upperEpoch - lowerEpoch) + lowerEpoch;
//
//        System.out.println(instant.getEpochSecond());
//        System.out.println(lowerEpoch);
//        System.out.println(timestamp);
//        System.out.println(Thread.currentThread().getId());

//
//        long l = 123456L;
//        byte[] lb = Bytes.toBytes(l);
//        System.out.println("long bytes length: " + lb.length);   // returns 8
//
//        String s = "" + l;
//        byte[] sb = Bytes.toBytes(s);
//        System.out.println("long as string length: " + sb.length);    // returns 10

        System.out.println(new Random().nextInt(100));
    }

    public static long getRandom(int max) {
        return (long) (Math.random() * max);
    }
}
