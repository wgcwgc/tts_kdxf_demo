/**
 * 
 */
package kdxf_tts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import log.PrintLog;
import pinyin.HanyuPinyinHelper;
import util.Util;
import wav2mp3.wav2mp3;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2017年9月30日
 * @time             上午11:00:55
 * @project_name     kdxf
 * @package_name     kdxf_tts
 * @file_name        Text2Speech.java
 * @type_name        Text2Speech
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class Text2SpeechMain
{
	public static String srcFile;
	public static String targetFile;
	
	public Text2SpeechMain()
	{
		
	}
	
	public static void play(String srcFileTemp , String targetFileTemp)
	{
		if(srcFileTemp != null && targetFileTemp != null)
		{
			srcFile = srcFileTemp;
			targetFile = targetFileTemp;
		}
		else
		{
			srcFile = Util.getSrcFile();
			targetFile = Util.getTargetFile();
		}
//		System.out.println(srcFileTemp + "#" + srcFile + "#" + targetFileTemp + "#" + targetFile);
		try
		{
			File file = new File(srcFile);
			if( ! file.exists())
			{
				System.out.println("源文件目录不存在");
				PrintLog.printLog("源文件目录不存在");
				return;
			}
			List <File> fileList = new ArrayList <File>();
			List <File> list = getFileList(srcFile , fileList , ".txt");
			BufferedReader bufferedReader = null;
			String tempString = null;
			for(int i = 0 , leng = list.size() ; i < leng ; i ++ )
			{
				bufferedReader = new BufferedReader(new FileReader(list.get(i)));
				tempString = null;
				String [] splite = null;
				String path = null;
				for(int j = 0 ; ( tempString = bufferedReader.readLine() ) != null ; j ++ )
				{
					tempString = tempString.replace(" " , "");
					tempString = tempString.replace("　" , "");
					System.out.println( ( i + 1 ) + "/" + leng + "\t"
							+ tempString);
					splite = tempString.trim().split(" ");
					path = list
							.get(i)
							.toString()
							.substring(0 ,
									list.get(i).toString().lastIndexOf("."))
							+ "\\" + tempString + ".pcm";
					if(splite.length > 1)
						path = list
								.get(i)
								.toString()
								.substring(0 ,
										list.get(i).toString().lastIndexOf("."))
								+ "\\" + splite[1] + ".pcm";
					System.out.println("splite[0]:" + list.get(i).toString()
							+ " " + splite[0] + " " + ( j + 1 ) + "\npath:"
							+ path);
					PrintLog.printLog("splite[0]:" + list.get(i).toString()
							+ " " + splite[0] + " " + ( j + 1 ) + "\npath:"
							+ path);
					creat(splite[0] , path);
					Thread.sleep(1 * 1000);
				}
				
				if(leng == i + 1)
				{
					beforeExchange();
				}
			}
			bufferedReader.close();
		}
		catch(Exception e)
		{
			PrintLog.printLog(e.toString());
			e.printStackTrace();
			
		}
	}
	
	public static List <File> getFileList(String srcPath ,
			List <File> filelist , String name) throws Exception
	{
		File [] files = new File(srcPath).listFiles();
		if(files != null)
		{
			for(int i = 0 , leng = files.length ; i < leng ; i ++ )
			{
				String fileName = files[i].getName();
				if(files[i].isDirectory())
					getFileList(files[i].getAbsolutePath() , filelist , name);
//				else if(fileName.endsWith(".c") || fileName.endsWith(".cpp")
//						|| fileName.endsWith(".java")
//						|| fileName.endsWith(".js")
//						|| fileName.endsWith(".txt"))
//					;
				else if(fileName.endsWith(name))
				{
					filelist.add(files[i]);
				}
				else
					continue;
			}
		}
		return filelist;
	}
	
	/**
	 * 
	 */
	public static void beforeExchange()
	{
		try
		{
			File targetFileRoot = new File(targetFile);
			if( ! targetFileRoot.exists() || targetFileRoot.isDirectory())
			{
				targetFileRoot.mkdirs();
			}
			
			File file = new File(srcFile);
			if( ! file.exists())
			{
				System.out.println("源文件目录不存在");
				PrintLog.printLog("源文件目录不存在");
				return;
			}
			/**
			 * pcm2wav
			 */
			List <File> fileList = new ArrayList <File>();
			List <File> list = getFileList(srcFile , fileList , ".pcm");
			String targetPath = null;
			String tempName = null;
			String fileName = null;
			HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper();
			for(int i = 0 , leng = list.size() ; i < leng ; i ++ )
			{
				tempName = list.get(i).toString();
				fileName = tempName.substring(tempName.lastIndexOf("\\") ,
						tempName.lastIndexOf("."));
//				System.out.println(Util.rootFile.toString().length() + "\n" + tempName.length());
				targetPath = targetFile
						+ tempName.substring(tempName.indexOf(srcFile)
								+ srcFile.toString().length() ,
								tempName.lastIndexOf("\\"))
						+ hanyuPinyinHelper.toHanyuPinyin(fileName) + ".wav";
				System.out.println(tempName + "\n" + targetPath);
				if( ! new File(targetPath).exists())
				{
					new File(targetPath).getParentFile().mkdirs();
				}
				exchange(tempName , targetPath);
			}
			/**
			 * wav2mp3
			 */
			List <File> wavFileList = new ArrayList <File>();
			List <File> wavFileLists = getFileList(targetFile , wavFileList ,
					".wav");
			String filepath = null;
			for(int i = 0 , leng = wavFileLists.size() ; i < leng ; i ++ )
			{
				filepath = wavFileLists.get(i).toString();
				System.out.println(filepath.substring(0 ,
						filepath.lastIndexOf("."))
						+ ".mp3");
				wav2mp3.execute(new File(filepath) ,
						filepath.substring(0 , filepath.lastIndexOf("."))
								+ ".mp3");
				new File(filepath).delete();
			}
			/**
			 * delete redundant filefolder
			 */
			String filefolder = null;
			for(int i = 0 ; i < list.size() ; i ++ )
			{
				filefolder = list.get(i).toString();
				filefolder = filefolder.substring(0 ,
						filefolder.lastIndexOf("\\"));
				new File(filefolder).delete();
			}
		}
		catch(Exception e)
		{
			PrintLog.printLog(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public static void creat(String contents , String path)
	{
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
//		mTts.setParameter(SpeechConstant.VOICE_NAME , "xiaoyan");// 设置发音人
//		mTts.setParameter(SpeechConstant.SPEED , "10");// 设置语速
//		mTts.setParameter(SpeechConstant.PITCH , "50");// 设置语调，范围0~100
//		mTts.setParameter(SpeechConstant.VOLUME , "80");// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.VOICE_NAME , Util.getVoiceName());// 设置发音人
		mTts.setParameter(SpeechConstant.SPEED , Util.getSpeed());// 设置语速
		mTts.setParameter(SpeechConstant.PITCH , Util.getPitch());// 设置语调，范围0~100
		mTts.setParameter(SpeechConstant.VOLUME , Util.getVolume());// 设置音量，范围0~100
		mTts.synthesizeToUri(contents , path , synthesizeToUriListener);
//		System.out.println(contents + " " + path );
	}
	
	public static void exchange(String srcPath , String targetPath)
	{
		try
		{
			new pcm2wav.Pcm2Wav().convertAudioFiles(srcPath , targetPath);
		}
		catch(Exception e)
		{
			PrintLog.printLog(e.toString());
			e.printStackTrace();
		}
	}
	
	// 合成监听器
	private static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progress为合成进度0~100
		public void onBufferProgress(int progress)
		{
		}
		
		// 会话合成完成回调接口
		// uri为合成保存地址，error为错误信息，为null时表示合成会话成功
		public void onSynthesizeCompleted(String uri , SpeechError error)
		{
		}
		
		@Override
		public void onEvent(int arg0 , int arg1 , int arg2 , int arg3 ,
				Object arg4 , Object arg5)
		{
			
		}
	};
	
}
