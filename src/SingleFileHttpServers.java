/**
 * 
 */

/**
 * @author Administrator
 * @copyright wgcwgc
 * @date 2017��12��27��
 * @time ����10:05:26
 * @project_name tts_kdxf_demo
 * @package_name
 * @file_name SingleFileHttpServers.java
 * @type_name SingleFileHttpServers
 * @enclosing_type
 * @tags
 * @todo
 * @others
 * 
 */

/**
 * 
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

import kdxf_tts.Text2SpeechMain;
import wav2mp3.wav2mp3;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

public class SingleFileHttpServers extends Thread
{
	public static final String rootPath = System.getProperty("user.dir") + "\\";
	
	private byte [] content;
	private byte [] header;
	private String encoding;
	private String MIMEType;
	private int port;
	
	private SingleFileHttpServers(String data , String encoding ,
			String MIMEType , int port) throws UnsupportedEncodingException
	{
		this(data.getBytes(encoding) , encoding , MIMEType , port);
	}
	
	private SingleFileHttpServers(byte [] data , String encoding ,
			String MIMEType , int port) throws UnsupportedEncodingException
	{
		this.encoding = encoding;
		this.content = data;
		this.port = port;
		this.MIMEType = MIMEType;
	}
	
	/**
	 * 
	 * @param MIMEType mp3
	 * @param encoding utf-8
	 * @param port 8088
	 */
	private SingleFileHttpServers(String MIMEType , String encoding , int port)
			throws UnsupportedEncodingException
	{
		this.MIMEType = MIMEType;
		this.encoding = encoding;
		this.port = port;
	}
	
	public void run()
	{
		
		try
		{
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(this.port);
			System.out.println("Accepting connections on port "
					+ server.getLocalPort());
			System.out.println("Data to be sent:");
			
			while(true)
			{
				Socket connection = null;
				try
				{
					connection = server.accept();
					OutputStream out = new BufferedOutputStream(
							connection.getOutputStream());
					InputStream in = new BufferedInputStream(
							connection.getInputStream());
					StringBuffer request = new StringBuffer();
					
					ByteArrayOutputStream contentBytes = new ByteArrayOutputStream();
					
					while(true)
					{
						int c = in.read();
						if(c == '\r' || c == '\n' || c == - 1)
						{
							break;
						}
						contentBytes.write(c);
						request.append((char) c);
					}
					
					String str = contentBytes.toString();
					System.out.println(str);
					if(judge(str , 0))
					{
						String string = str.substring(str.indexOf("=") + 1 ,
								str.indexOf("HTTP") - 1);
						System.out.print(string);
						string = URLDecoder.decode(string , "utf-8");
						System.out.println(string);
						if(judge(string , 1))
						{
							if( ! new File(rootPath + string + ".mp3").exists())
							{
								
								Text2SpeechMain.creat(string , rootPath
										+ string + ".pcm");
								while(true)
								{
									if(new File(rootPath + string + ".pcm")
											.exists())
									{
										break;
									}
								}
								try
								{
									new pcm2wav.Pcm2Wav().convertAudioFiles(
											rootPath + string + ".pcm" ,
											rootPath + string + ".wav");
									wav2mp3.execute(new File(rootPath + string
											+ ".wav") , rootPath + string
											+ ".mp3");
									new File(rootPath + string + ".pcm")
											.delete();
									new File(rootPath + string + ".wav")
											.delete();
								}
								catch(Exception e)
								{
									System.out.println("��Ƶ�ļ������쳣");
									System.out.println(e);
								}
								
							}
							writeRespose(request , string , out , 0);
							
						}
						else
						{
							System.out.println("�����а����Ƿ��ַ�");
							writeRespose(request , "�����а����Ƿ��ַ�" , out , 1);
						}
					}
					else
					{
						System.out.println("�����쳣");
						writeRespose(request , "�����쳣" , out , 1);
					}
				}
				catch(IOException e)
				{
					System.out.println(e);
					System.out.println("�������");
				}
			}
			
		}
		catch(IOException e)
		{
			System.err.println("Could not start server. Port Occupied");
		}
	}
	
	/**
	 * request ����ͷ
	 * string ��Ƶ��
	 * out ���������
	 * 
	 * flag��
	 * 		0 ������Ƶ�ļ�
	 * 		1 ����������
	 * 		2 ������ 
	 */
	private void writeRespose(StringBuffer request , String string ,
			OutputStream out , int flag)
	{
		InputStream localRead = null;
		try
		{
			ByteArrayOutputStream localWrite = new ByteArrayOutputStream();
			if(0 == flag)
			{
				localRead = new FileInputStream(rootPath + string + ".mp3");
				int b;
				while( ( b = localRead.read() ) != - 1)
				{
					localWrite.write(b);
				}
				this.content = localWrite.toByteArray();
				
			}
			else
			{
				localWrite.write(string.getBytes(this.encoding));
				this.content = localWrite.toByteArray();
			}
			// �����⵽��HTTP/1.0���Ժ��Э�飬���չ淶����Ҫ����һ��MIME�ײ�
			String requestContent = request.toString();
			String header = "HTTP/1.0 200 OK\r\n" + "Server: OneFile 1.0\r\n"
					+ "Content-length: "
					+ ( this.content.length ) + "\r\n" + "Content-type: "
					+ MIMEType + "\r\n\r\n";
			this.header = header.getBytes(this.encoding);
			if(requestContent.indexOf("HTTP/") != - 1)
			{
				out.write(this.header);
			}
			
			out.write(this.content);
			out.flush();
		}
		catch(IOException e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		finally
		{
			if(localRead != null)
			{
				try
				{
					localRead.close();
				}
				catch(IOException e)
				{
					System.out.println("IO���쳣");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param string
	 * @exception �ж��ַ��Ƿ�Ϸ�
	 * @return
	 */
	private boolean judge(String string , int flag)
	{
		// �ж��Ƿ�������ض��ַ� ��=���� ��HTTP��
		if(0 == flag)
		{
			if(string.contains("=") && string.contains("HTTP"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		// �ж��Ƿ��зǷ��ַ�
		else if(1 == flag)
		{
			if(string.contains("\\") || string.contains("/")
					|| string.contains(":") || string.contains("*")
					|| string.contains("?") || string.contains("\"")
					|| string.contains("|") || string.contains("<")
					|| string.contains("<"))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String [] args)
	{
		SpeechUtility.createUtility(SpeechConstant.APPID + "=59ce0194");
		try
		{
			String contentType = "audio/mpeg";
			String encoding = "utf-8";
			int port = 8088;
			Thread thread = new SingleFileHttpServers(contentType , encoding ,
					port);
			thread.start();
			System.out.println(System.getProperty("user.dir"));
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out
					.println("Usage:java SingleFileHTTPServer filename port encoding");
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
}
