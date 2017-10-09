/**
 * 
 */
package kdxf_tts;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2017年9月30日
 * @time             上午10:55:47
 * @project_name     kdxf
 * @package_name     kdxf_tts
 * @file_name        Tts_java_demo.java
 * @type_name        Tts_java_demo
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class Tts_java_demo
{
	
	/**
	 * @param args
	 */
	public static void main(String [] args)
	{
		SpeechUtility.createUtility(SpeechConstant.APPID + "=59ce0194");
//		String contentString = "语音合成测试程序";
//		Text2Speech.play();
		Text2SpeechMain.beforeExchange();
	}
	
}
