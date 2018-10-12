/**
 * 
 */
package util;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2017��9��30��
 * @time             ����3:08:08
 * @project_name     kdxf
 * @package_name     util
 * @file_name        Util.java
 * @type_name        Util
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

import java.io.File;
import java.io.IOException;

/**
 * 
 */

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2018��1��3��
 * @time             ����2:06:26
 * @project_name     TtsServer
 * @package_name     
 * @file_name        Util.java
 * @type_name        Util
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class Util
{
	public static ReadIniFile readIniFile;
	public static String SECRETKEY = "8848@jzb";
	public static String rootPath = null;
	public static String ttsRootPath = null;
	public static String ttsPath = null;
	
//	public static final String srcFile = "C:\\Users\\Administrator\\Desktop\\wgc\\JZB\\�α걾\\�����ı���1\\���꼶\\";
//	public static final String targetFile = "C:\\Users\\Administrator\\Desktop\\wgc\\t2s2\\out\\t2s\\���꼶�ϲ�����\\��һ��Ԫ\\����дд\\";
	
//	public static final String srcFile = "C:\\Users\\Administrator\\Desktop\\wgc\\t2s\\test\\";
//	public static final String targetFile = "C:\\Users\\Administrator\\Desktop\\wgc\\t2s2\\out\\";
	
//	public static final String srcFile = "C:\\Users\\Administrator\\Desktop\\JZB\\";
//	public static final String targetFile = "C:\\Users\\Administrator\\Desktop\\out\\";
	
//	public static final String logPath = targetFile + "log.log";
	
	/**
	 * Ŀ���ļ�·��
	 */
	public static String getTargetFile()
	{
		readIniFile = new ReadIniFile("config");
		String targetFile = readIniFile.getValue("ttspath" , "targetFile");
		return targetFile;
	}
	
	/**
	 * �������ļ�·��
	 */
	public static String getSrcFile()
	{
		readIniFile = new ReadIniFile("config");
		String srcFile = readIniFile.getValue("ttspath" , "srcFile");
		return srcFile;
	}
	
	/**
	 * ��ȡ��Ŀtts·��
	 * @return
	 */
	public static String getTtsPath()
	{
		readIniFile = new ReadIniFile("config");
		ttsRootPath = readIniFile.getValue("path" , "ttsRootPath");
//		System.out.println("ttsRootPath:" + ttsRootPath + "*");
		if(ttsRootPath.trim().equals(""))
		{
			ttsRootPath = getRootPath() + "tts" + File.separatorChar;
			if(creatPath(ttsRootPath))
			{
				return ttsRootPath;
			}
			return null;
		}
		
		ttsPath = readIniFile.getValue("path" , "ttsPath");
//		System.out.println("ttsPath:" + ttsPath + "*");
		if(ttsPath.trim().equals(""))
		{
			if(creatPath(ttsRootPath))
			{
				return ttsRootPath;
			}
			return null;
		}
		else
		{
//			System.out.println(ttsRootPath + ttsPath);
			if(creatPath(ttsRootPath + ttsPath))
			{
				return ttsRootPath + ttsPath;
			}
			return null;
		}
	}
	
	/**
	 * ��ȡ��Ŀ��·��
	 * @return
	 */
	public static String getRootPath()
	{
		try
		{
			rootPath = new File(".").getCanonicalPath().toString() + File.separatorChar;
		}
		catch(IOException e)
		{
			rootPath = System.getProperty("user.dir") + File.separatorChar;
		}
		
		if(creatPath(rootPath))
		{
			return rootPath;
		}
		return null;
	}
	
	private static Boolean creatPath(String path)
	{
		File file = new File(path);
//		System.out.println(path);
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
		}
		return true;
	}
	
	/**
	 * ��ȡ���������ն˿�
	 * @return
	 */
	public static String getAcceptPort()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("port" , "acceptPort");
	}
	/**
	 * ��ȡ���������Ͷ˿�
	 * @return
	 */
	public static String getSendPort()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("port" , "sendPort");
	}
	/**
	 * ��ȡ����������IP
	 * @return
	 */
	public static String getServerIP()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("ip" , "sendIP");
	}
	/**
	 * ��ȡ�ƴ�Ѷ����Ŀid
	 * @return
	 */
	public static String getKDXFid()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("id" , "kdxfID");
	}
	/**
	 * ��ȡ��Ƶ����ʱ��
	 * @return
	 */
	public static String getDELTime()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("time" , "delTime");
	}
	/**
	 * ��ȡ������
	 * @return
	 */
	public static String getVoiceName()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("voicename" , "voicename");
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public static String getSpeed()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("speed" , "speed");
	}
	/**
	 * ��ȡ���
	 * @return
	 */
	public static String getPitch()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("pitch" , "pitch");
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public static String getVolume()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("volume" , "volume");
	}
	/**
	 * ��ȡ��λ�����
	 * @return
	 */
	public static String getEncryptFileName()
	{
		String content = "";
		long currentTime = System.currentTimeMillis();
		content += currentTime;
		int random = (int) ( 10 * Math.random() );
		content += "#" + random;
		int num = 3;
		while(num -- != 0)
		{
			random = (int) ( 10 * Math.random() );
			content += random;
		}
		return content;
	}
	/**
	 * ����
	 * @param args
	 */
	public static void main(String [] args)
	{
//		System.out.println("ttsPath00:" + getTtsPath());
//		System.out.println("rootPath00:" + getRootPath());
	}
	
}
