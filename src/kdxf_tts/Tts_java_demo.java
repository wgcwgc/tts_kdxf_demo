/**
 * 
 */
package kdxf_tts;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2017��9��30��
 * @time             ����10:55:47
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
//		String contentString = "�����ϳɲ��Գ���";
//		Text2Speech.play();
		Text2SpeechMain.beforeExchange();
	}
	
}
