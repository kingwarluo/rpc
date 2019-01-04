package com.kingwarluo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
public class InboundHandler2 extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive2");
		// 通知执行下一个InboundHandler
		ctx.fireChannelActive();
	}

	@Override
	// 读取Client发送的信息，并打印出来
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("InboundHandler2.channelRead: ctx :" + ctx);
		ByteBuf result = (ByteBuf) msg;
		byte[] result1 = new byte[result.readableBytes()];
		result.readBytes(result1);
		String resultStr = new String(result1);
		System.out.println("RPCClient said:" + resultStr);
		result.release();

		// ChannelInboundHandler之间的传递，通过调用 ctx.fireChannelRead(msg) 实现；
		// 调用ctx.write(msg) 将传递到ChannelOutboundHandler。
		// ctx.write()方法执行后，需要调用flush()方法才能令它立即执行。
		ctx.write(msg);
	}
 
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("InboundHandler2.channelReadComplete");
		ctx.flush();
	}
 
}
