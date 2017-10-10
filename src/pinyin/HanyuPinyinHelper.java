/**
 * 
 */
package pinyin;

/**
 * @author Administrator
 * @copyright wgcwgc
 * @date 2017��10��9��
 * @time ����4:21:38
 * @project_name tts_kdxf_demo
 * @package_name pinyin
 * @file_name HanyuPinyinHelper.java
 * @type_name HanyuPinyinHelper
 * @enclosing_type
 * @tags
 * @todo
 * @others
 * 
 */

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class HanyuPinyinHelper
{
	
	/** 
	 * ������תΪ����ƴ��
	 * @param chineselanguage Ҫת��ƴ��������
	 */
	public String toHanyuPinyin(String ChineseLanguage)
	{
		char [] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// ���ƴ��ȫ��Сд
//		defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);// ������
//		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// ��������
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try
		{
			for(int i = 0 ; i < cl_chars.length ; i ++ )
			{
				if(String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+"))
				{// ����ַ�������,������תΪ����ƴ��
					hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i] , defaultFormat)[0];
				}
				else
				{// ����ַ���������,��ת��
					hanyupinyin += cl_chars[i];
				}
			}
		}
		catch(BadHanyuPinyinOutputFormatCombination e)
		{
			e.printStackTrace();
			System.out.println("�ַ�����ת�ɺ���ƴ��");
		}
		return hanyupinyin;
	}

	public static String getPinYin(String inputString)
	{  
        
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
  
        char[] input = inputString.trim().toCharArray();  
        StringBuffer output = new StringBuffer("");  
  
        try
        {
            for (int i = 0; i < input.length; i++)
            {  
                if (Character.toString(input[i]).matches("[\u4E00-\u9FA5]+"))
                {  
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);  
                    output.append(temp[0]);
                    output.append(" ");  
                }
                else  
                    output.append(Character.toString(input[i]));  
            }  
        }
        catch (BadHanyuPinyinOutputFormatCombination e)
        {  
            e.printStackTrace();  
        }  
        return output.toString();  
    }  
      

	public static String getFirstLettersUp(String ChineseLanguage)
	{
		return getFirstLetters(ChineseLanguage , HanyuPinyinCaseType.UPPERCASE);
	}
	
	public static String getFirstLettersLo(String ChineseLanguage)
	{
		return getFirstLetters(ChineseLanguage , HanyuPinyinCaseType.LOWERCASE);
	}
	
	public static String getFirstLetters(String ChineseLanguage ,
			HanyuPinyinCaseType caseType)
	{
		char [] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(caseType);// ���ƴ��ȫ����д
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// ��������
		try
		{
			for(int i = 0 ; i < cl_chars.length ; i ++ )
			{
				String str = String.valueOf(cl_chars[i]);
				if(str.matches("[\u4e00-\u9fa5]+"))
				{// ����ַ�������,������תΪ����ƴ��,��ȡ��һ����ĸ
					hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(
							cl_chars[i] , defaultFormat)[0].substring(0 , 1);
				}
				else if(str.matches("[0-9]+"))
				{// ����ַ�������,ȡ����
					hanyupinyin += cl_chars[i];
				}
				else if(str.matches("[a-zA-Z]+"))
				{// ����ַ�����ĸ,ȡ��ĸ
					hanyupinyin += cl_chars[i];
				}
				else
				{// ����ת��
					hanyupinyin += cl_chars[i];// ����Ǳ����ŵĻ�������
				}
			}
		}
		catch(BadHanyuPinyinOutputFormatCombination e)
		{
			System.out.println("�ַ�����ת�ɺ���ƴ��");
		}
		return hanyupinyin;
	}
	
	public static String getPinyinString(String ChineseLanguage)
	{
		char [] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// ���ƴ��ȫ����д
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// ��������
		try
		{
			for(int i = 0 ; i < cl_chars.length ; i ++ )
			{
				String str = String.valueOf(cl_chars[i]);
				if(str.matches("[\u4e00-\u9fa5]+"))
				{// ����ַ�������,������תΪ����ƴ��,��ȡ��һ����ĸ
					hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(
							cl_chars[i] , defaultFormat)[0];
				}
				else if(str.matches("[0-9]+"))
				{// ����ַ�������,ȡ����
					hanyupinyin += cl_chars[i];
				}
				else if(str.matches("[a-zA-Z]+"))
				{// ����ַ�����ĸ,ȡ��ĸ
				
					hanyupinyin += cl_chars[i];
				}
				else
				{// ����ת��
				}
			}
		}
		catch(BadHanyuPinyinOutputFormatCombination e)
		{
			System.out.println("�ַ�����ת�ɺ���ƴ��");
		}
		return hanyupinyin;
	}
	
	/**
	 * ȡ��һ�����ֵĵ�һ���ַ�
	* @Title: getFirstLetter 
	* @Description: 
	* @return String   
	* @throws
	 */
	public static String getFirstLetter(String ChineseLanguage)
	{
		char [] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);// ���ƴ��ȫ����д
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// ��������
		try
		{
			String str = String.valueOf(cl_chars[0]);
			if(str.matches("[\u4e00-\u9fa5]+"))
			{// ����ַ�������,������תΪ����ƴ��,��ȡ��һ����ĸ
				hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
						cl_chars[0] , defaultFormat)[0].substring(0 , 1);
				;
			}
			else if(str.matches("[0-9]+"))
			{// ����ַ�������,ȡ����
				hanyupinyin += cl_chars[0];
			}
			else if(str.matches("[a-zA-Z]+"))
			{// ����ַ�����ĸ,ȡ��ĸ
			
				hanyupinyin += cl_chars[0];
			}
			else
			{// ����ת��
			
			}
		}
		catch(BadHanyuPinyinOutputFormatCombination e)
		{
			System.out.println("�ַ�����ת�ɺ���ƴ��");
		}
		return hanyupinyin;
	}
	
	public static void main(String [] args)
	{
		HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper();
		System.out.println(hanyuPinyinHelper.toHanyuPinyin("ĳЩ���־䣨��ĸΪ����ʱ������ת���ɴ�����ĺ���ƴ����������pinyin4j.jar�����⣬Ҳ�������ַ������������..."));
		System.out.println(getPinYin("ĳЩ���־䣨��ĸΪ����ʱ������ת���ɴ�����ĺ���ƴ����������pinyin4j.jar�����⣬Ҳ�������ַ������������..."));
		
//	    {  
//	        String chs = "�����й���! I'm Chinese!";
//	        System.out.println(chs);
//	        System.out.println(getPinYin(chs));
//	    }  
//	    System.err.println("(NOTE)");
//	    System.out.println("asdf");
//	    try
//		{
//			int string = System.in.read();
//			System.out.println(string);
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
	}
}
