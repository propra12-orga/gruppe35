package Network;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class Client implements Runnable {
	private Thread thread; // the client thread
	// server address and port
	private String host;
	private int port;
	// SocketChannel for client (connection to server)
	private SocketChannel channel = null;
	// buffer for reading and writing
	private ByteBuffer buffer = ByteBuffer.allocate(1000);
	// Charset and encoder for US-ASCII
	private static Charset charset = Charset.forName("US-ASCII");
	private static CharsetEncoder encoder = charset.newEncoder();

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		init();
	}

	/*
	 * connect client to given host and port
	 */
	private void init() {
		try {
			// create address object for given host and port
			InetSocketAddress isa = new InetSocketAddress(
					InetAddress.getByName(host), port);
			channel = SocketChannel.open(); // open a channel
			channel.configureBlocking(false); // set channel to non-blocking
												// mode
			channel.connect(isa); // connect channel to given address
			// set a timeout of 5 seconds and wait for connect to finish
			long timeout = System.currentTimeMillis() + 5000;
			while (!channel.finishConnect()) {
				threadSleep(250);
				if (System.currentTimeMillis() > timeout) {
					throw new Exception("connection timout!");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			cleanup();
		}
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	/*
	 * start the client (if not started already)
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	/*
	 * stop the client
	 */
	public void stop() {
		thread = null;
	}

	/*
	 * processing loop for client
	 */
	public void run() {
		try {
			// while not stopped
			while (thread == Thread.currentThread()) {
				processIncomingMessages();
				// do game related things like rendering here ...
				// send output to server here ...
				// rest a bit and give time to other threads
				threadSleep(50L);
			}
		} catch (Exception ex) {
			System.out.println("error occured! connection terminated!");
			ex.printStackTrace();
		} finally {
			// cleanup();
		}
	}

	/*
	 * close channel (if existing) and exit
	 */
	private void cleanup() {
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception ex) {
			}
		}
		System.exit(0);
	}

	private void processIncomingMessages() throws IOException {
		ReadableByteChannel rbc = (ReadableByteChannel) channel;
		buffer.clear(); // clear buffer before reading
		// read input data into buffer
		int numBytesRead = rbc.read(buffer);
		if (numBytesRead < 0) {
			// this is an undocumented feature - it means the client has
			// disconnected
			System.out.println("connection terminated");
			cleanup();
			return;
		} else if (numBytesRead > 0) // is there some input?
		{
			try {
				buffer.flip(); // flip buffer for reading
				CharBuffer cb = charset.decode(buffer); // read from buffer and
														// decode
				System.out.println(cb.toString()); // display message on console
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * utility method to call Thread.sleep()
	 */
	private void threadSleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/*
	 * start client and connect to server on localhost port 8000
	 */
	public static void main(String[] args) {
		Server server = new Server(8000);
		server.start();

		Client client = new Client("localhost", 8000);
		client.start();
	}
}