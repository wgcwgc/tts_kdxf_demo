/**
 * 
 */

/**
 * @author Administrator
 * @copyright wgcwgc
 * @date 2017年12月26日
 * @time 下午5:58:58
 * @project_name tts_kdxf_demo
 * @package_name
 * @file_name JHTTP.java
 * @type_name JHTTP
 * @enclosing_type
 * @tags
 * @todo
 * @others
 * 
 */

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JHTTP extends Thread
{
	
	private File documentRootDirectory;
	private String indexFileName = "index.html";
	private ServerSocket server;
	private int numThreads = 50;
	
	public JHTTP(File documentRootDirectory , int port , String indexFileName)
			throws IOException
	{
		if( ! documentRootDirectory.isDirectory())
		{
			throw new IOException(documentRootDirectory
					+ " does not exist as a directory ");
		}
		this.documentRootDirectory = documentRootDirectory;
		this.indexFileName = indexFileName;
		this.server = new ServerSocket(port);
	}
	
	private JHTTP(File documentRootDirectory , int port) throws IOException
	{
		this(documentRootDirectory , port , "index.html");
	}
	
	public void run()
	{
		for(int i = 0 ; i < numThreads ; i ++ )
		{
			Thread t = new Thread(new RequestProcessor(documentRootDirectory ,
					indexFileName));
			t.start();
		}
		
		System.out.println("Accepting connection on port "
				+ server.getLocalPort());
		System.out.println("Document Root: " + documentRootDirectory);
		while(true)
		{
			try
			{
				Socket request = server.accept();
				RequestProcessor.processRequest(request);
			}
			catch(IOException e)
			{
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String [] args)
	{
		File docroot;
		try
		{
			docroot = new File(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Usage: java JHTTP docroot port indexfile");
			return;
		}
		
		int port;
		try
		{
			port = Integer.parseInt(args[1]);
			if(port < 0 || port > 65535)
			{
				port = 80;
			}
		}
		catch(Exception e)
		{
			port = 80;
		}
		
		try
		{
			JHTTP webserver = new JHTTP(docroot , port);
			webserver.start();
		}
		catch(IOException e)
		{
			System.out.println("Server could not start because of an "
					+ e.getClass());
			System.out.println(e);
		}
		
	}
	
}
