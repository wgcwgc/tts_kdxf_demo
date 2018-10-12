/**
 * 
 */
package amr2mp3;

/**
 * @author Administrator
 * @copyright wgcwgc
 * @date 2017年10月13日
 * @time 下午2:41:46
 * @project_name tts_kdxf_demo
 * @package_name amr2mp3
 * @file_name Amr2Mp3.java
 * @type_name Amr2Mp3
 * @enclosing_type
 * @tags
 * @todo
 * @others
 * 
 */

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class Amr2Mp3
{
	public static void transformation(String sourcePath , String targetPath)
	{
		changeToMp3(sourcePath , targetPath);
	}
	
	public static void changeToMp3(String sourcePath , String targetPath)
	{
		File source = new File(sourcePath);
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();
		
		audio.setCodec("libmp3lame");
		
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		
		try
		{
			encoder.encode(source , target , attrs);
		}
		catch(Exception e)
		{
			System.out.println(e + "\n" + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String [] args)
	{
		String sourcePath = "D:\\2018.04.13.16.09.04.amr";
		String targetPath = "D:\\2018.04.13.16.09.04.mp3";
		changeToMp3(sourcePath ,targetPath);
	}
	
}
