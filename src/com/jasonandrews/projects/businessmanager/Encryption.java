package com.jasonandrews.projects.businessmanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	//Get a SHA1 hashed string.
	public static String hash(String input) throws NoSuchAlgorithmException {
		MessageDigest msgDigest = MessageDigest.getInstance("SHA1");
		byte[] result = msgDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		return sb.toString();	
	}
	
}
