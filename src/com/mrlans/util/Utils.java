package com.mrlans.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils
 * 
 * @author ljt
 * @date 2014-9-10 08:42:22
 * 
 */
public class Utils {

	
	public static String username="admin";
	
	public static void main(String[] args) {
		// makeMD5("栗军�?");

		String realm = "LIVE555 Streaming Media";
		String nonce = "0b3745a726f6efc3b811ce9d637011ec";// "c5349121762fb911994d337c4f126662";

		String username = "admin";
		String passwd = "123456";
		String uri = "rtsp://129.1.6.89/mpeg4cif";

		String response = "8d53a436f6bb678ba6f7020f515ec28e";

		String md1 = makeMD5(username + ":" + realm + ":" + passwd);
		String resp = makeMD5(md1 + ":" + nonce + ":"
				+ makeMD5("DESCRIBE" + ":" + uri));
		System.out.println("resp" + resp);
		System.out.println("result=" + response.equals(resp));
		// String tmp = "RTSP/1.0 401 Unauthorized\r\n"
		// + "CSeq: 5\r\n"
		// + "Date: Fri, Sep 05 2014 00:52:31 GMT\r\n"
		// +
		// "WWW-Authenticate: Digest realm=\"LIVE555 Streaming Media\", nonce=\"c5349121762fb911994d337c4f126662\"\r\n\r\n";
		//
		// parseUnauth(tmp);
	}

	public static String makeMD5(String text) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
			md.update(text.getBytes());
			byte[] b = md.digest();
			String hex = new BigInteger(1, b).toString(16);
			// System.out.println("hex=" + hex);
			return hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("deprecation")
	public static Map<String, String> parseUnauth(String tmp) {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new StringBufferInputStream(tmp)));
		String line = "";
		Map<String, String> map = new HashMap<String, String>();
		try {
			while ((line = br.readLine()) != null) {
				if (line.startsWith("WWW-Authenticate")) {
					String realm = line.substring(line.indexOf("realm=\"")
							+ "realm=\"".length(), line.indexOf("\", nonce"));
					// System.out.println("realm="+realm);
					map.put("realm", realm);
					String nonce = line.substring(line.indexOf("nonce=\"")
							+ "nonce=\"".length(), line.length() - 1);
					// System.out.println("nonce="+nonce);
					map.put("nonce", nonce);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}
	
	@SuppressWarnings("deprecation")
	public static String parseSession(String tmp){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new StringBufferInputStream(tmp)));
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Session")) {					
					String session = line.substring(line.indexOf("Session: ")
							+ "Session: ".length(), line.length());
					// System.out.println("nonce="+nonce);
					return session;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
