package com.mrlans.comm;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.mrlans.util.Utils;



public class RTSPComm {

	private IoSession session;
	private final String address = "rtsp://129.1.6.89/mpeg4cif";
	private int seq = 0;
	private final String username = "admin";
	private final String passwd = "123456";
	private Map<String, String> map = null;
	private String sessionid;
	private String uri = "rtsp://129.1.6.89/mpeg4cif";

	public enum Status {
		options, describe, auth, setup, play, playing, pause, teardown
	}

	private Status status;

	public RTSPComm(IoSession session) {
		super();
		this.session = session;

	}

	public void doOption() {
		status = Status.options;

		StringBuilder sb = new StringBuilder();
		sb.append("OPTIONS " + address + " RTSP/1.0\r\n");
		sb.append("CSeq: " + (seq++) + "\r\n");
		sb.append("User-Agent: LibVLC/2.1.5 (LIVE555 Streaming Media v2014.05.27)\r\n\r\n");
		System.out.println(sb.toString());
		send(sb.toString());

	}

	public void send(String msg) {
		session.write(msg);
	}

	public void doAuth(String nonce, String realm) {
		status = Status.describe;
		String md1 = Utils.makeMD5(username + ":" + realm + ":" + passwd);
		String response = Utils.makeMD5(md1 + ":" + nonce + ":"
				+ Utils.makeMD5("DESCRIBE" + ":" + uri));
		StringBuilder sb = new StringBuilder();
		sb.append("DESCRIBE " + address + " RTSP/1.0\r\n");
		sb.append("CSeq: " + (seq++) + "\r\n");
		sb.append("Authorization: Digest username=\"" + username+ "\", realm=\"" + realm + "\", nonce=\"" + nonce+ "\", uri=\"rtsp://129.1.6.89/mpeg4cif\", response=\""+ response + "\"\r\n");
		sb.append("User-Agent: LibVLC/2.1.5 (LIVE555 Streaming Media v2014.05.27)\r\n");
		sb.append("Accept: application/sdp\r\n\r\n");
		System.out.println("doAuth:" + sb.toString());
		send(sb.toString());

	}

	public void doTeardown() {
		// StringBuilder sb = new StringBuilder();
		// sb.append("TEARDOWN ");
		// sb.append(this.address);
		// sb.append("/");
		// sb.append(VERSION);
		// sb.append("Cseq: ");
		// sb.append(seq++);
		// sb.append("\r\n");
		// sb.append("User-Agent: RealMedia Player HelixDNAClient/10.0.0.11279 (win32)\r\n");
		// sb.append("Session: ");
		// sb.append(sessionid);
		// sb.append("\r\n");
		// send(sb.toString().getBytes());
		// System.out.println(sb.toString());
	}

	public void doPlay() {
		status = Status.play;
		String realm = map.get("realm");
		String nonce = map.get("nonce");
		String md1 = Utils.makeMD5(username + ":" + realm + ":" + passwd);
		String response = Utils.makeMD5(md1 + ":" + nonce + ":"
				+ Utils.makeMD5("PLAY" + ":" + uri));
		StringBuilder sb = new StringBuilder();
		sb.append("PLAY " + address + " RTSP/1.0\r\n");
		sb.append("CSeq: " + (seq++) + "\r\n");
		sb.append("Authorization: Digest username=\"" + username
				+ "\", realm=\"" + realm + "\", nonce=\"" + nonce
				+ "\", uri=\"rtsp://129.1.6.89/mpeg4cif\", response=\""
				+ response + "\"\r\n");
		sb.append("User-Agent: LibVLC/2.1.5 (LIVE555 Streaming Media v2014.05.27)\r\n");
		sb.append("Session: " + sessionid + "\r\n");
		sb.append("Range: npt=0.000-\r\n\r\n");
		System.out.println(sb.toString());
		send(sb.toString());

	}

	public void doSetup() {
		status = Status.setup;
		// String response = "8d53a436f6bb678ba6f7020f515ec28e";
		String realm = map.get("realm");
		String nonce = map.get("nonce");
		String md1 = Utils.makeMD5(username + ":" + realm + ":" + passwd);
		String response = Utils.makeMD5(md1 + ":" + nonce + ":"
				+ Utils.makeMD5("SETUP" + ":" + uri));
		StringBuilder sb = new StringBuilder();
		sb.append("SETUP " + address + "/track1 RTSP/1.0\r\n");
		sb.append("CSeq: " + (seq++) + "\r\n");
		sb.append("Authorization: Digest username=\"" + username
				+ "\", realm=\"" + realm + "\", nonce=\"" + nonce
				+ "\", uri=\"rtsp://129.1.6.89/mpeg4cif\", response=\""
				+ response + "\"\r\n");
		sb.append("User-Agent: LibVLC/2.1.5 (LIVE555 Streaming Media v2014.05.27)\r\n");
		sb.append("Transport: RTP/AVP/TCP;unicast;client_port=54684-54685\r\n\r\n");
		System.out.println(sb.toString());
		send(sb.toString());

	}

	public byte[] toByteArray(String hexStr) {
		String[] s = hexStr.split(" ");
		byte[] bb = new byte[s.length];
		for (int i = 0; i < s.length; i++) {
			bb[i] = Byte.parseByte(s[i], 16);
		}
		return bb;
	}

	public void doDescribe() {
		status = Status.describe;

		StringBuilder sb = new StringBuilder();
		sb.append("DESCRIBE " + address + " RTSP/1.0\r\n");
		sb.append("CSeq: " + (seq++) + "\r\n");
		sb.append("Accept: application/sdp\r\n");
		sb.append("User-Agent: LibVLC/2.1.5 (LIVE555 Streaming Media v2014.05.27)\r\n\r\n");
		System.out.println(sb.toString());
		send(sb.toString());

	}

	public void doPause() {
		StringBuilder sb = new StringBuilder();

		// sb.append("PAUSE " + this.address + " RTSP/1.0\r\n");
		// sb.append("CSeq: "+(seq++)+"\r\n");
		// sb.append("Accept: application/sdp\r\n");
		// sb.append("User-Agent: LibVLC/2.1.5 (LIVE555 Streaming Media v2014.05.27)\r\n\r\n");

		// sb.append("PAUSE ");
		// sb.append(this.address);
		// sb.append("/");
		// sb.append(VERSION);
		// sb.append("Cseq: ");
		// sb.append(seq++);
		// sb.append("\r\n");
		// sb.append("Session: ");
		// sb.append(sessionid);
		// sb.append("\r\n");
		// send(sb.toString().getBytes());
		// System.out.println(sb.toString());
	}

	public Status getStatus() {
		synchronized (status) {
			return status;
		}

	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Map<String, String> getMap() {
		synchronized (map) {
			return map;
		}
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
}
