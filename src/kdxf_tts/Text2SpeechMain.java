/**
 * 
 */
package kdxf_tts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import mp3.wav2mp3;
import pinyin.HanyuPinyinHelper;
import util.Util;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2017��9��30��
 * @time             ����11:00:55
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
	public Text2SpeechMain()
	{
		
	}
	
	public static void play()
	{
		
		try
		{
			File file = new File(Util.srcFile);
			if( ! file.exists())
			{
				System.out.println("Դ�ļ�Ŀ¼������");
				return;
			}
			List <File> fileList = new ArrayList <File>();
			List <File> list = getFileList(Util.srcFile , fileList , ".txt");
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
					splite = tempString.split(" ");
					path = list
							.get(i)
							.toString()
							.substring(0 ,
									list.get(i).toString().lastIndexOf("\\"))
							+ "\\" + tempString + ".pcm";
					if(splite.length > 1)
						path = list
								.get(i)
								.toString()
								.substring(
										0 ,
										list.get(i).toString()
												.lastIndexOf("\\"))
								+ "\\" + splite[1] + ".pcm";
					System.out.println(list.get(i).toString() + " " + splite[0]
							+ " " + ( j + 1 ) + "\n" + path);
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
			e.printStackTrace();
		}
	}
	
	public static List <File> getFileList(String srcPath , List <File> filelist ,
			String name) throws Exception
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
			File targetFileRoot = new File(Util.targetFile);
			if( ! targetFileRoot.exists() || targetFileRoot.isDirectory())
			{
				targetFileRoot.mkdirs();
			}
			
			File file = new File(Util.srcFile);
			if( ! file.exists())
			{
				System.out.println("Դ�ļ�Ŀ¼������");
				return;
			}
			/**
			 * pcm2wav
			 */
			List <File> fileList = new ArrayList <File>();
			List <File> list = getFileList(Util.srcFile , fileList , ".pcm");
			String targetPath = null;
			String tempName = null;
			String fileName = null;
			HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper();
//			System.out.println(hanyuPinyinHelper.toHanyuPinyin("�෢�ķ����ؿշ�������򷢵�����"));
			for(int i = 0 , leng = list.size() ; i < leng ; i ++ )
			{
				tempName = list.get(i).toString();
				fileName = tempName.substring(tempName.lastIndexOf("\\") , tempName.lastIndexOf("."));
//				System.out.println(Util.rootFile.toString().length() + "\n" + tempName.length());
				targetPath = Util.targetFile + tempName.substring(tempName.indexOf("t2s"),	tempName.lastIndexOf("\\")) + hanyuPinyinHelper.toHanyuPinyin(fileName) + ".wav";
				System.out.println(tempName + "\n" + targetPath);
				if(! new File(targetPath).exists())
				{
					new File(targetPath).getParentFile().mkdirs();
				}
				exchange(tempName , targetPath);
			}
			/**
			 * wav2mp3
			 */
			List <File> wavFileList = new ArrayList<File>();
			List <File> wavFileLists = getFileList(Util.targetFile , wavFileList , ".wav");
			String filepath = null;
			for(int i = 0 , leng = wavFileLists.size() ; i < leng ; i ++ )
			{
				filepath = wavFileLists.get(i).toString();
				System.out.println(filepath.substring( 0 , filepath.lastIndexOf(".")) + ".mp3");
				wav2mp3.execute(new File(filepath) , filepath.substring( 0 , filepath.lastIndexOf(".")) + ".mp3");
				new File(filepath).delete();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private static void creat(String contents , String path)
	{
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		mTts.setParameter(SpeechConstant.VOICE_NAME , "xiaoyan");// ���÷�����
		mTts.setParameter(SpeechConstant.SPEED , "50");// ��������
		mTts.setParameter(SpeechConstant.PITCH , "50");// �����������Χ0~100
		mTts.setParameter(SpeechConstant.VOLUME , "80");// ������������Χ0~100
		mTts.synthesizeToUri(contents , path , synthesizeToUriListener);
	}
	
	public static void exchange(String srcPath , String targetPath)
	{
		try
		{
			new pcm2wav.Pcm2Wav().convertAudioFiles(srcPath , targetPath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// �ϳɼ�����
	private static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progressΪ�ϳɽ���0~100
		public void onBufferProgress(int progress)
		{
		}
		
		// �Ự�ϳ���ɻص��ӿ�
		// uriΪ�ϳɱ����ַ��errorΪ������Ϣ��Ϊnullʱ��ʾ�ϳɻỰ�ɹ�
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
