package com.mrlans.comm;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 
 * @author ljt
 * @date 2014-9-10 20:17:32
 *
 */
public class MyCodecFactory implements ProtocolCodecFactory {

	private DataEncoder encoder;
	private DataDecoder decoder;
	
	public MyCodecFactory(DataEncoder encoder, DataDecoder decoder) {
		super();
		this.encoder = encoder;
		this.decoder = decoder;
	}
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
