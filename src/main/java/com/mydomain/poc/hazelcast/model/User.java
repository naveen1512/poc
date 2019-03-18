package com.mydomain.poc.hazelcast.model;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String[] STATE = { "KARNATKA", "TAMILNADU", "MAHARASTRA", "DEHLI", "RAJASTHAN", "BIHAR", "TELANGANA",
			"ANDHRAPRADESH" };
	public static int[] CHANNEL = { 1, 2, 3, 4 };
	public static String[] OS = { "Android", "iOS" };
	public static int[] YOB = { 1982, 1983, 1984, 1985, 1986, 1987, 1988 };

	private String userId;
	private String name;
	private String email;
	private String lid;
	private int lcount;
	private int frstCshGmTblSz;
	private String isIdVrfd;
	private boolean isRptPlyr;
	private boolean isTest;
	private String state;
	private String geolocStae;
	private int chnlOfRgstrtn;
	private String osReg;
	private int yob;
	private String scrnSizeLstSsn;
	private boolean macp;

	private String key1 = "value1";
	private String key2 = "value2";
	private String key3 = "value3";
	private String key4 = "value4";
	private String key5 = "value5";
	private String key6 = "value6";
	private String key7 = "value7";
	private String key8 = "value8";
	private String key9 = "value9";
	private String key10 = "value10";
	private String key11 = "value11";
	private String key12 = "value12";
	private String key13 = "value13";
	private String key14 = "value14";
	private String key15 = "value15";
	private String key16 = "value16";
	private String key17 = "value17";
	private String key18 = "value18";
	private String key19 = "value19";
	private String key20 = "value20";
	private String key21 = "value21";
	private String key22 = "value22";
	private String key23 = "value23";
	private String key24 = "value24";
	private String key25 = "value25";
	private String key26 = "value26";
	private String key27 = "value27";
	private String key28 = "value28";
	private String key29 = "value29";
	private String key30 = "value30";
	private String key31 = "value31";
	private String key32 = "value32";
	private String key33 = "value33";
	private String key34 = "value34";
	private String key35 = "value35";
	private String key36 = "value36";
	private String key37 = "value37";
	private String key38 = "value38";
	private String key39 = "value39";
	private String key40 = "value40";
	private String key41 = "value41";
	private String key42 = "value42";
	private String key43 = "value43";
	private String key44 = "value44";
	private String key45 = "value45";
	private String key46 = "value46";
	private String key47 = "value47";
	private String key48 = "value48";
	private String key49 = "value49";
	private String key50 = "value50";
	private String key51 = "value51";
	private String key52 = "value52";
	private String key53 = "value53";
	private String key54 = "value54";
	private String key55 = "value55";
	private String key56 = "value56";
	private String key57 = "value57";
	private String key58 = "value58";
	private String key59 = "value59";
	private String key60 = "value60";
	private String key61 = "value61";
	private String key62 = "value62";
	private String key63 = "value63";
	private String key64 = "value64";
	private String key65 = "value65";
	private String key66 = "value66";
	private String key67 = "value67";
	private String key68 = "value68";
	private String key69 = "value69";
	private String key70 = "value70";
	private String key71 = "value71";
	private String key72 = "value72";
	private String key73 = "value73";
	private String key74 = "value74";
	private String key75 = "value75";

	public User() {
	}

	public User(int userId) {
		this.userId = "" + userId;
		this.name = "User " + userId;
		this.email = "user" + userId + "@example.com";
		this.lid = "User " + userId;
		this.lcount = new Random().nextInt(100);
		this.frstCshGmTblSz = new Random().nextInt(6);
		this.isIdVrfd = "NEW";
		this.isRptPlyr = false;
		this.isTest = false;
		this.state = STATE[new Random().nextInt(STATE.length)];
		this.geolocStae = STATE[new Random().nextInt(STATE.length)];
		this.chnlOfRgstrtn = CHANNEL[new Random().nextInt(CHANNEL.length)];
		this.osReg = OS[new Random().nextInt(OS.length)];
		this.yob = YOB[new Random().nextInt(YOB.length)];
		this.scrnSizeLstSsn = "5.5";
		this.macp = false;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public int getLcount() {
		return lcount;
	}

	public void setLcount(int lcount) {
		this.lcount = lcount;
	}

	public int getFrstCshGmTblSz() {
		return frstCshGmTblSz;
	}

	public void setFrstCshGmTblSz(int frstCshGmTblSz) {
		this.frstCshGmTblSz = frstCshGmTblSz;
	}

	public String getIsIdVrfd() {
		return isIdVrfd;
	}

	public void setIsIdVrfd(String isIdVrfd) {
		this.isIdVrfd = isIdVrfd;
	}

	public boolean isRptPlyr() {
		return isRptPlyr;
	}

	public void setRptPlyr(boolean isRptPlyr) {
		this.isRptPlyr = isRptPlyr;
	}

	public boolean isTest() {
		return isTest;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGeolocStae() {
		return geolocStae;
	}

	public void setGeolocStae(String geolocStae) {
		this.geolocStae = geolocStae;
	}

	public int getChnlOfRgstrtn() {
		return chnlOfRgstrtn;
	}

	public void setChnlOfRgstrtn(int chnlOfRgstrtn) {
		this.chnlOfRgstrtn = chnlOfRgstrtn;
	}

	public String getOsReg() {
		return osReg;
	}

	public void setOsReg(String osReg) {
		this.osReg = osReg;
	}

	public int getYob() {
		return yob;
	}

	public void setYob(int yob) {
		this.yob = yob;
	}

	public String getScrnSizeLstSsn() {
		return scrnSizeLstSsn;
	}

	public void setScrnSizeLstSsn(String scrnSizeLstSsn) {
		this.scrnSizeLstSsn = scrnSizeLstSsn;
	}

	public boolean isMacp() {
		return macp;
	}

	public void setMacp(boolean macp) {
		this.macp = macp;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getKey4() {
		return key4;
	}

	public void setKey4(String key4) {
		this.key4 = key4;
	}

	public String getKey5() {
		return key5;
	}

	public void setKey5(String key5) {
		this.key5 = key5;
	}

	public String getKey6() {
		return key6;
	}

	public void setKey6(String key6) {
		this.key6 = key6;
	}

	public String getKey7() {
		return key7;
	}

	public void setKey7(String key7) {
		this.key7 = key7;
	}

	public String getKey8() {
		return key8;
	}

	public void setKey8(String key8) {
		this.key8 = key8;
	}

	public String getKey9() {
		return key9;
	}

	public void setKey9(String key9) {
		this.key9 = key9;
	}

	public String getKey10() {
		return key10;
	}

	public void setKey10(String key10) {
		this.key10 = key10;
	}

	public String getKey11() {
		return key11;
	}

	public void setKey11(String key11) {
		this.key11 = key11;
	}

	public String getKey12() {
		return key12;
	}

	public void setKey12(String key12) {
		this.key12 = key12;
	}

	public String getKey13() {
		return key13;
	}

	public void setKey13(String key13) {
		this.key13 = key13;
	}

	public String getKey14() {
		return key14;
	}

	public void setKey14(String key14) {
		this.key14 = key14;
	}

	public String getKey15() {
		return key15;
	}

	public void setKey15(String key15) {
		this.key15 = key15;
	}

	public String getKey16() {
		return key16;
	}

	public void setKey16(String key16) {
		this.key16 = key16;
	}

	public String getKey17() {
		return key17;
	}

	public void setKey17(String key17) {
		this.key17 = key17;
	}

	public String getKey18() {
		return key18;
	}

	public void setKey18(String key18) {
		this.key18 = key18;
	}

	public String getKey19() {
		return key19;
	}

	public void setKey19(String key19) {
		this.key19 = key19;
	}

	public String getKey20() {
		return key20;
	}

	public void setKey20(String key20) {
		this.key20 = key20;
	}

	public String getKey21() {
		return key21;
	}

	public void setKey21(String key21) {
		this.key21 = key21;
	}

	public String getKey22() {
		return key22;
	}

	public void setKey22(String key22) {
		this.key22 = key22;
	}

	public String getKey23() {
		return key23;
	}

	public void setKey23(String key23) {
		this.key23 = key23;
	}

	public String getKey24() {
		return key24;
	}

	public void setKey24(String key24) {
		this.key24 = key24;
	}

	public String getKey25() {
		return key25;
	}

	public void setKey25(String key25) {
		this.key25 = key25;
	}

	public String getKey26() {
		return key26;
	}

	public void setKey26(String key26) {
		this.key26 = key26;
	}

	public String getKey27() {
		return key27;
	}

	public void setKey27(String key27) {
		this.key27 = key27;
	}

	public String getKey28() {
		return key28;
	}

	public void setKey28(String key28) {
		this.key28 = key28;
	}

	public String getKey29() {
		return key29;
	}

	public void setKey29(String key29) {
		this.key29 = key29;
	}

	public String getKey30() {
		return key30;
	}

	public void setKey30(String key30) {
		this.key30 = key30;
	}

	public String getKey31() {
		return key31;
	}

	public void setKey31(String key31) {
		this.key31 = key31;
	}

	public String getKey32() {
		return key32;
	}

	public void setKey32(String key32) {
		this.key32 = key32;
	}

	public String getKey33() {
		return key33;
	}

	public void setKey33(String key33) {
		this.key33 = key33;
	}

	public String getKey34() {
		return key34;
	}

	public void setKey34(String key34) {
		this.key34 = key34;
	}

	public String getKey35() {
		return key35;
	}

	public void setKey35(String key35) {
		this.key35 = key35;
	}

	public String getKey36() {
		return key36;
	}

	public void setKey36(String key36) {
		this.key36 = key36;
	}

	public String getKey37() {
		return key37;
	}

	public void setKey37(String key37) {
		this.key37 = key37;
	}

	public String getKey38() {
		return key38;
	}

	public void setKey38(String key38) {
		this.key38 = key38;
	}

	public String getKey39() {
		return key39;
	}

	public void setKey39(String key39) {
		this.key39 = key39;
	}

	public String getKey40() {
		return key40;
	}

	public void setKey40(String key40) {
		this.key40 = key40;
	}

	public String getKey41() {
		return key41;
	}

	public void setKey41(String key41) {
		this.key41 = key41;
	}

	public String getKey42() {
		return key42;
	}

	public void setKey42(String key42) {
		this.key42 = key42;
	}

	public String getKey43() {
		return key43;
	}

	public void setKey43(String key43) {
		this.key43 = key43;
	}

	public String getKey44() {
		return key44;
	}

	public void setKey44(String key44) {
		this.key44 = key44;
	}

	public String getKey45() {
		return key45;
	}

	public void setKey45(String key45) {
		this.key45 = key45;
	}

	public String getKey46() {
		return key46;
	}

	public void setKey46(String key46) {
		this.key46 = key46;
	}

	public String getKey47() {
		return key47;
	}

	public void setKey47(String key47) {
		this.key47 = key47;
	}

	public String getKey48() {
		return key48;
	}

	public void setKey48(String key48) {
		this.key48 = key48;
	}

	public String getKey49() {
		return key49;
	}

	public void setKey49(String key49) {
		this.key49 = key49;
	}

	public String getKey50() {
		return key50;
	}

	public void setKey50(String key50) {
		this.key50 = key50;
	}

	public String getKey51() {
		return key51;
	}

	public void setKey51(String key51) {
		this.key51 = key51;
	}

	public String getKey52() {
		return key52;
	}

	public void setKey52(String key52) {
		this.key52 = key52;
	}

	public String getKey53() {
		return key53;
	}

	public void setKey53(String key53) {
		this.key53 = key53;
	}

	public String getKey54() {
		return key54;
	}

	public void setKey54(String key54) {
		this.key54 = key54;
	}

	public String getKey55() {
		return key55;
	}

	public void setKey55(String key55) {
		this.key55 = key55;
	}

	public String getKey56() {
		return key56;
	}

	public void setKey56(String key56) {
		this.key56 = key56;
	}

	public String getKey57() {
		return key57;
	}

	public void setKey57(String key57) {
		this.key57 = key57;
	}

	public String getKey58() {
		return key58;
	}

	public void setKey58(String key58) {
		this.key58 = key58;
	}

	public String getKey59() {
		return key59;
	}

	public void setKey59(String key59) {
		this.key59 = key59;
	}

	public String getKey60() {
		return key60;
	}

	public void setKey60(String key60) {
		this.key60 = key60;
	}

	public String getKey61() {
		return key61;
	}

	public void setKey61(String key61) {
		this.key61 = key61;
	}

	public String getKey62() {
		return key62;
	}

	public void setKey62(String key62) {
		this.key62 = key62;
	}

	public String getKey63() {
		return key63;
	}

	public void setKey63(String key63) {
		this.key63 = key63;
	}

	public String getKey64() {
		return key64;
	}

	public void setKey64(String key64) {
		this.key64 = key64;
	}

	public String getKey65() {
		return key65;
	}

	public void setKey65(String key65) {
		this.key65 = key65;
	}

	public String getKey66() {
		return key66;
	}

	public void setKey66(String key66) {
		this.key66 = key66;
	}

	public String getKey67() {
		return key67;
	}

	public void setKey67(String key67) {
		this.key67 = key67;
	}

	public String getKey68() {
		return key68;
	}

	public void setKey68(String key68) {
		this.key68 = key68;
	}

	public String getKey69() {
		return key69;
	}

	public void setKey69(String key69) {
		this.key69 = key69;
	}

	public String getKey70() {
		return key70;
	}

	public void setKey70(String key70) {
		this.key70 = key70;
	}

	public String getKey71() {
		return key71;
	}

	public void setKey71(String key71) {
		this.key71 = key71;
	}

	public String getKey72() {
		return key72;
	}

	public void setKey72(String key72) {
		this.key72 = key72;
	}

	public String getKey73() {
		return key73;
	}

	public void setKey73(String key73) {
		this.key73 = key73;
	}

	public String getKey74() {
		return key74;
	}

	public void setKey74(String key74) {
		this.key74 = key74;
	}

	public String getKey75() {
		return key75;
	}

	public void setKey75(String key75) {
		this.key75 = key75;
	}

	@Override
	public String toString() {
		return "UserId: " + this.getUserId() + " Name: " + this.getName() + " Email: " + this.getEmail();
	}
}
