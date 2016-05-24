package com.mrlans.comm;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
/**
 * 
 * @author ljt
 * @date 2014-9-10 20:16:29
 *
 */
public class DataEncoder implements ProtocolEncoder{

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		if(message instanceof String){
			String msg=(String)message;			
			IoBuffer buf=IoBuffer.allocate(128).setAutoExpand(true);
			buf.put(msg.getBytes("UTF-8"));
			buf.flip();
			out.write(buf);
			out.flush();
		}
	}

	public void dispose(IoSession session) throws Exception {
		
	}

}
