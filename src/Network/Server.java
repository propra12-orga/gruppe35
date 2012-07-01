package Network;

import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class Server implements Runnable {
	private Thread thread; // the server thread
	private Selector selector; // selector for multiplexing
	private int keysAdded = 0; // number of keys (connections) added
	// buffer for reading and writing
	private ByteBuffer buffer = ByteBuffer.allocate(1024);
	// Charset and encoder for US-ASCII
	private static Charset charset = Charset.forName("US-ASCII");
	private static CharsetEncoder encoder = charset.newEncoder();

	public Server(int port) {
		init(port);
	}

	/*
	 * init server for given port
	 */
	public void init(int port) {
		// stores all listening server sockets on all network interfaces
		Vector allServerSocketChannels = new Vector();
		try {
			// create a selector for channel multiplexing
			selector = Selector.open();
		} catch (Exception ex) {
			System.err.println("could not open selector!");
			ex.printStackTrace();
		}
		// bind to all network interfaces
		try {
			// get available network interfaces
			Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				// process next network interface
				NetworkInterface ni = (NetworkInterface) interfaces
						.nextElement();
				// get all addresses of selected network interface
				Enumeration addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					// process next address of selected network interface
					InetAddress address = (InetAddress) addresses.nextElement();
					System.out.println("binding to port " + port
							+ " on InetAddress " + address);
					// create a socket address for selected address and port
					InetSocketAddress isa = new InetSocketAddress(address, port);
					System.out
							.println("opening a non-blocking ServerSocketChannel on port "
									+ port + " on InetAddress " + address);
					// open a (listening) ServerSocketChannel
					ServerSocketChannel ssc = ServerSocketChannel.open();
					ssc.configureBlocking(false); // set channel to non-blocking
													// mode
					ssc.socket().bind(isa); // bind socket to socket address
					allServerSocketChannels.add(ssc); // store socket channel in
														// channel list
				}
			}
		} catch (IOException ex) {
			System.err.println("could not start server!");
			ex.printStackTrace();
			System.exit(0);
		}
		// process all listening server sockets
		for (Iterator it = allServerSocketChannels.iterator(); it.hasNext();) {
			// process next ServerSocketChannel
			ServerSocketChannel ssc = (ServerSocketChannel) it.next();
			try {
				// register ServerSocketChannel with selector for multiplexing
				// announce interest for answering incoming connection-requests
				ssc.register(selector, SelectionKey.OP_ACCEPT);
			} catch (IOException ex) {
				System.err.println("could not register new SSC [\"" + ssc
						+ "\"]!");
				ex.printStackTrace();
				System.exit(0);
			}
		}
		System.out.println("server started");
	}

	/*
	 * start the server (if not started already)
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	/*
	 * stop the server
	 */
	public void stop() {
		thread = null;
	}

	/*
	 * processing loop for server
	 */
	public void run() {
		// while not stopped
		while (thread == Thread.currentThread()) {
			try {
				// select ready channels and process them
				while ((keysAdded = selector.select()) > 0) {
					// handle client requests
					handleClientRequests();
					// rest a bit and give time to other threads
					threadSleep(50L);
				}
			} catch (Exception ex) {
				System.err
						.println("unexpected Exception during channel processing!");
				ex.printStackTrace();
				System.exit(0);
			}
		}
	}

	/*
	 * this method is used to handle all kind of requests from clients.
	 */
	private void handleClientRequests() throws Exception {
		// get ready keys (connections) from selector
		Set keys = selector.selectedKeys();
		Iterator i = keys.iterator();
		// process ready keys (connections)
		while (i.hasNext()) {
			SelectionKey key = (SelectionKey) i.next();
			i.remove(); // remove from selected set
			// (key is added automatically with next select() when processing is
			// needed)
			if (key.isAcceptable()) // new connection?
			{
				// get channel and accept connection
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false); // set channel to non-blocking mode
				sc.register(selector, SelectionKey.OP_READ
						| SelectionKey.OP_WRITE);
				// register SocketChannel with selector for multiplexing
				// announce interest for reading and writing
				Socket s = sc.socket();
				System.out.println("new connection (" + s.getInetAddress()
						+ ":" + s.getPort() + ")");
				// send a greeting to the client
				sc.write(encoder.encode(CharBuffer.wrap("Hallo!\r\n")));
			} else if (key.isReadable()) // existing connection, ready for
											// reading input?
			{
				processReadableKey(key); // read input from channel
			} else if (key.isWritable()) // existing connection, ready for
											// sending data?
			{
				processWritableKey(key); // write output to channel
			}
		}
	}

	/*
	 * this method is used to handle input from clients.
	 */
	protected void processReadableKey(SelectionKey key) throws IOException {
		// get the channel for this key
		ReadableByteChannel channel = (ReadableByteChannel) key.channel();
		buffer.clear(); // clear buffer before reading
		// read input data into buffer
		int numBytesRead = channel.read(buffer);
		if (numBytesRead < 0) {
			// this is an undocumented feature - it means the client has
			// disconnected
			closeConnection(key);
			return;
		} else {
			buffer.flip(); // flip buffer for reading
			// process data in buffer here ...
		}
	}

	/*
	 * this method is used for responding directly to client requests. global
	 * game update messages are sent throw game.
	 */
	protected void processWritableKey(SelectionKey key) throws IOException {
		buffer.clear(); // clear buffer before writing
		// write your data in the buffer here ...
		// get the channel for this key
		WritableByteChannel channel = (WritableByteChannel) key.channel();
		channel.write(buffer); // write data in buffer to channel
	}

	/*
	 * close the connection and cancel the related key, so it will be removed
	 * form selector later
	 */
	private void closeConnection(SelectionKey key) {
		try {
			key.channel().close(); // close channel
		} catch (Exception ex) {
		} finally {
			key.cancel(); // cancel key
		}
	}

	/*
	 * utility method to call Thread.sleep()
	 */
	private void threadSleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/*
	 * start server on port 8000
	 */
	public static void main(String[] args) {
		Server server = new Server(8000);
		server.start();
	}
}