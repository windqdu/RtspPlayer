package com.mrlans.comm;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
/**
 * 
 * @author ljt
 * @date 2014-9-10 20:16:09
 *
 */
public class DataDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		byte [] bb=new byte[in.remaining()];
		in.get(bb);
		String str=new String(bb,0,bb.length,"UTF-8");
		out.write(str);
		return false;
	}

}
