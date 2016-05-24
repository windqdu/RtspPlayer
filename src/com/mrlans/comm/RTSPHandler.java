package com.mrlans.comm;

import java.util.Map;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.mrlans.comm.RTSPComm.Status;
import com.mrlans.util.Utils;


public class RTSPHandler implements IoHandler {

	private RTSPComm comm = null;

	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	public void sessionOpened(IoSession session) throws Exception {
		print("sessionOpened");
		comm = new RTSPComm(session);
		comm.doOption();
	}

	public void sessionClosed(IoSession session) throws Exception {
		print("sessionClosed");

	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		print("sessionIdle");

	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg=(String)message;
		print("messageReceived");
		System.out.println(msg);
		switch (comm.getStatus()) {
		case options:
			comm.doDescribe();
			break;
		case describe:
			if (msg.contains("Unauthorized")) {
				Map<String,String> map = Utils.parseUnauth(msg);
				String realm = map.get("realm");
				String nonce = map.get("nonce");
				comm.setMap(map);
				comm.doAuth(nonce, realm);
			}else{
				comm.setStatus(Status.auth);
			}			
			break;
		case auth:
			comm.doSetup();
			break;
		case setup:
			
			String sessionid = Utils.parseSession(msg);
			if (sessionid != null && sessionid.length() > 0) {
				comm.setStatus(Status.setup);
				comm.setSessionid(sessionid);
			}else{
				comm.setStatus(Status.auth);
			}
			comm.doPlay();
			break;
		case play:
			
			break;
		default:
			break;
		}

	}

	public void messageSent(IoSession session, Object message) throws Exception {
//		System.out.println("sent="+message);
	}

	public void print(String txt) {
		System.out.println("++++++++++" + txt + "++++++++++++");
	}

}
