package com.skn.admin.sample.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import javax.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author 	vegaaltair77@happyict.co.kr
 * @since	2017년 10월 10일
 * @version	1.0
 */
public final class CommonUtil {

	private static final String SEPERATOR_HYPHEN = "-";
	private static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String REGEX_PHONE_NORMAL = "(01[016789])(\\d{3,4})(\\d{4})";
	private static final String REGEX_PHONE_HYPHEN = "(01[016789])([-\\t\\n\\x0B\\f\\r]*)(\\d{3,4})([-\\t\\n\\x0B\\f\\r]*)(\\d{4})";
	private static final String REGEX_JIMIN = "(\\d{6})([-\\t\\n\\x0B\\f\\r])[1234]\\d{6}";
	private static final String REGEX_BIRTH_SIX = "(\\d{4})\\d{2}";
	private static final String REGEX_BIRTH_EIGHT = "(\\d{6})\\d{2}";
	private static final String REGEX_KR_NAME_FIRTH = "([가-힣]){1}";
	private static final String REGEX_KR_NAME_SECOND = "([가-힣])\\W{1}";
	private static final String REGEX_KR_NAME_THIRD = "([가-힣])\\W{1}([가-힣])";
	private static final String REGEX_KR_NAME_OTHER = "([가-힣])\\W{1}([가-힣]+)";
	private static final String REGEX_EN_NAME_FIRTH = "\\w{1}([a-zA-Z])";
	private static final String REGEX_EN_NAME_SECOND = "\\w{2}([a-zA-Z])";
	private static final String REGEX_EN_NAME_OTHER = "([a-zA-Z]+)\\w{2}([a-zA-Z])([a-zA-Z])";
	private static final String REGEX_MASKINGID = "([0-9]{3})([0-9]{5})";
	private static final String REGEX_SPECIAL = "!@#$%^&*?_~";
	private static final String REGEX_NUMBER = "^[0-9]*$";
	private static final String REGEX_PASSWORD = "^[_A-Za-z0-9|!|@|#|$|%|^|&|*|?|_|~]*$";
	private static final String REGEX_KR_NAME = "^[ㄱ-ㅎㅏ-ㅣ가-힣]*$";
	private static final String REGEX_EN_NAME = "^[a-zA-Z]*$";
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * <pre>
	 * 	문자가 Null이거나 empty String일 경우 true, 아닐 경우 false
	 * </pre>
	 * @author 	vegaaltair77@happyict.co.kr
	 * @param 	str
	 * @return	boolean
	 */
	public static boolean stringIsNull(String str) {
		return (str == null || str.trim().length() == 0) ? true : false;
	}

	/**
	 * <pre>
	 * 	Object가 Null일 경우 true, Null이 아닐 경우 false
	 * </pre>
	 * @author 	vegaaltair77@happyict.co.kr
	 * @param 	obj
	 * @return	boolean
	 */
	public static boolean objectIsNull(Object obj) {
		return obj == null ? true : false;
	}

	/**
	 * <pre>
	 * 	문자가 Null이거나 empty String일 경우 "", 아닐 경우 입력받은 문자를 가공하지 않고 리턴
	 * </pre>
	 * @author 	vegaaltair77@happyict.co.kr
	 * @param 	str
	 * @return	String
	 */
	public static String checkString(String str) {
		return (stringIsNull(str) || str.length() == 0) ? "" : str.trim();
	}

	/**
	 * <pre>
	 * 	국가코드가 Null이거나 empty String일 경우 "K" 한국으로 설정, 아닐 경우 입력받은 문자를 가공하지 않고 리턴
	 * </pre>
	 * @author 	vegaaltair77@happyict.co.kr
	 * @param 	str
	 * @return	String
	 */
	public static String checkCodeString(String str) {
		return (stringIsNull(str) || str.length() == 0) ? "K" : str;
	}

	/**
	 * <pre>
	 * 	이메일 형식이 맞는지 확인
	 * </pre>
	 * @param email
	 * @return true : 이메일 형식임, false : 이메일 형식이 아님
	 */

	public static boolean isEmail(final String email) {
		if (stringIsNull(email)) {
			return false;
		} else {
			return isValid(REGEX_EMAIL, email);
		}
	}

	/**
	 * <pre>
	 * 	핸드폰 형식 검사
	 * </pre>
	 * @param phone
	 * @return true : 핸드폰 형식임, false : 핸드폰 형식이 아님
	 */
	public static boolean isPhoneNum(final String phone) {
		if (stringIsNull(phone)) {
			return false;
		} else {
			return phone.indexOf(SEPERATOR_HYPHEN) == -1 ? isValid(REGEX_PHONE_NORMAL, phone) : isValid(REGEX_PHONE_HYPHEN, phone);
		}
	}

	/**
	 * <pre>
	 * 	숫자만 있는지 검사
	 * </pre>
	 * @param number
	 * @return true : 숫자만으로 구성 됨, false : 숫자 이외의 값 포함 됨
	 */
	public static boolean isNumber(final String number) {
		if (stringIsNull(number)) {
			return false;
		} else {
			return isValid(REGEX_NUMBER, number);
		}
	}

	/**
	 * <pre>
	 * 	비밀번호 생성에 사용할 수 있는 값 사용 유무
	 * </pre>
	 * @param password
	 * @return return true : 사용 가능, false : 사용 불가능
	 */
	public static boolean isPassword(final String password) {
		if (stringIsNull(password)) {
			return false;
		} else {
			return isValid(REGEX_PASSWORD, password);
		}
	}

	public static boolean isKoreaName(final String name) {
		if (stringIsNull(name)) {
			return false;
		} else {
			return isValid(REGEX_KR_NAME, name);
		}
	}

	public static boolean isEnglishName(final String name) {
		if (stringIsNull(name)) {
			return false;
		} else {
			return isValid(REGEX_EN_NAME, name);
		}
	}

	/**
	 * <pre>
	 * 	패턴 검사
	 * </pre>
	 * @param regex 패턴 형식
	 * @param target 패턴 검사 대상
	 * @return true : 정상, false : 비정상
	 */
	private static boolean isValid(final String regex, final String target) {
		return Pattern.compile(regex).matcher(target).matches();
	}

	/**
	 * <pre>데이터가 없을 경우 '-'표시</pre>
	 * @author 	vegaaltair77@happyict.co.kr
	 * @param 	val
	 * @return	String
	 */
	public static String noData(String val) {
		return stringIsNull(val) ? SEPERATOR_HYPHEN : val;
	}

	/**
	 * <pre>전화번호 분리</pre>
	 * @param phone
	 * @return String[]
	 */
	public static String[] phoneParse(String phone) {
		return (stringIsNull(phone)) ? new String[0] : phone.split(SEPERATOR_HYPHEN);
	}

	/**
     * <pre>
     * 	문자열이 길 때 어떤 특정한 길이에서 짜른 후 뒤에 "..."를 붙임, 단, 한글이나 영문이나 모두 1글자로 취급한다.
     * </pre>
     * @param val 자르고 싶은 문장
     * @param cutLength 자르고 싶은 길이
     * @return 일정길이로 자른 문자열에 "..."를 붙여 반환한다.
     */
    public static String cutString(String val, int cutLength) {
    	return 	stringIsNull(val)
    				? checkString(val) : val.length() > cutLength
    					? val.substring(0, cutLength) + "..." : val;
    }

    /**
     * 문자열이 길 때 어떤 특정한 길이에서 짜른 후 뒤에 "..."를 붙임, 단, 한글은 2바이트, 영문은 1바이트로 취급하여 반올림해서 자른다ㅏ.
     *
     * @param val 자르고 싶은 문장
     * @param cutLength 자르고 싶은 길이
     * @return 일정길이로 자른 문자열에 "..."를 붙여 반환한다.
     */
    public static String cutStringToByte(String val, int cutLength) {
        if (cutLength < 4) return val;

        int j = 0;
        int len = val.length();
        StringBuilder stringbuffer = new StringBuilder();

        for (int idx = 0; idx < len; idx++) {
            char c = val.charAt(idx);

            if (c < '\uAC00' || '\uD7A3' < c) j++;
            else j += 2;

            stringbuffer.append(c);

            if (j <= cutLength - 3) continue;

            stringbuffer.append("...");

            break;
        }

        return stringbuffer.toString();
    }

	/**
	 * <pre>성명 마스킹 처리</pre>
	 * @author 	vegaaltair77@happyict.co.kr
	 * @param	name 성명
	 * @param	type en일경우 영문이며 그외는 모두 국문의 형식
	 * @return	마스킹 처리된 성명
	 */
	public static String maskingName(String name, String type) {
		name = checkString(name);
		type = checkString(type);
		char[] arrayValue = name.toCharArray();
		int valLength = arrayValue.length;

		switch (type) {
			case "en" :
				switch (valLength) {
					case 0: case 1: case 2:
						break;
					case 3:
						name = name.replaceAll(REGEX_EN_NAME_FIRTH,"*$1");
						break;
					case 4:
						name = name.replaceAll(REGEX_EN_NAME_SECOND,"**$1");
						break;
					case 5:
						name = name.replaceAll(REGEX_EN_NAME_FIRTH,"*$1");
						break;
					default:
						if (valLength > 5) {
							name = name.replaceAll(REGEX_EN_NAME_OTHER,"$1**$2$3");
						}

						break;
				}
				break;
			default :
				switch (valLength) {
					case 0:
						break;
					case 1:
						name = name.replaceAll(REGEX_KR_NAME_FIRTH,"$1*");
						break;
					case 2:
						name = name.replaceAll(REGEX_KR_NAME_SECOND,"$1*");
						break;
					case 3:
						name = name.replaceAll(REGEX_KR_NAME_THIRD,"$1*$2");
						break;
					default:
						if (valLength > 3) {
							name = name.replaceAll(REGEX_KR_NAME_OTHER,"$1*$2");
						}
						break;
				}
				break;
		}

		return name;
	}

	public static String maskingName2(String name) {
		name = checkString(name);
		char[] arrayValue = name.toCharArray();
		int valLength = arrayValue.length;
		String type = isEnglishName(name)
					? "en" : isKoreaName(name)
							? "ko" : "";

		switch (type) {
			case "en" :
				switch (valLength) {
					case 0: case 1: case 2:
						break;
					case 3:
						name = name.replaceAll(REGEX_EN_NAME_FIRTH,"*$1");
						break;
					case 4:
						name = name.replaceAll(REGEX_EN_NAME_SECOND,"**$1");
						break;
					case 5:
						name = name.replaceAll(REGEX_EN_NAME_FIRTH,"*$1");
						break;
					default:
						if (valLength > 5) {
							name = name.replaceAll(REGEX_EN_NAME_OTHER,"$1**$2$3");
						}
	
						break;
				}
				break;
			case "ko" :
				switch (valLength) {
					case 0:
						break;
					case 1:
						name = name.replaceAll(REGEX_KR_NAME_FIRTH,"$1*");
						break;
					case 2:
						name = name.replaceAll(REGEX_KR_NAME_SECOND,"$1*");
						break;
					case 3:
						name = name.replaceAll(REGEX_KR_NAME_THIRD,"$1*$2");
						break;
					default:
						if (valLength > 3) {
							name = name.replaceAll(REGEX_KR_NAME_OTHER,"$1*$2");
						}
						break;
				}
				break;
			default :
				switch (valLength) {
					case 0: case 1: case 2:
						name = name + "*";
						break;
					case 3:
						if (name != null && name.length() == 3) {
						    name = name.substring(0, 1) + "*" + name.substring(2);
						}
						break;
					default:
						String lastName = (name != null && valLength > 0) ? name.substring(valLength-1, valLength) : "";
						
						if (name != null) {
						    name = name.substring(0, 1);
						}

						for (int idx = 0; idx < valLength -1; idx++) {
							name += "*";
						}

						name = name + lastName;
						break;
				}
				break;
		}

		return name;
	}

	public static String maskingIp(String ip) {
		if (!CommonUtil.stringIsNull(ip)) {
			if (ip.indexOf(".") > -1) {
				String[] ips = ip.split("\\.");

				if (ips.length == 4) {
					ip = ips[0] + ".";
					int len = ips[1].length();

					for (int idx = 0; idx < len; idx++) {
						ip += "*";
					}

					len = ips[2].length();
					ip += ".";

					for (int idx = 0; idx < len; idx++) {
						ip += "*";
					}

					ip = ip + "." + ips[3];
				}
			}
		}

		return ip;
	}
	/**
	 * <pre>
	 * 	생년월일 마스킹(마지막 두자리 마스킹 처리)
	 * </pre>
	 * @param 	birthday : 문자형 생년월일
	 * @return	마스킹 처리된 생년월일
	 */
	public static String maskingBirthday(String birthday) {
		if (stringIsNull(birthday)) {
			return birthday;
		} else {
			return birthday.length() == 6
					? birthday.replaceAll(REGEX_BIRTH_SIX,"$1**") : birthday.length() == 8
					? birthday.replaceAll(REGEX_BIRTH_EIGHT,"$1**") : birthday;
		}
	}

	/**
	 * <pre>
	 * 	주민번호 뒷자리 마스킹 처리 (예: 111111-*******)
	 * </pre>
	 * @param 	jumin 주민번호
	 * @return	마스킹 처리된 주민번호
	 */
	public static String maskingJumin(String jumin) {
		if (stringIsNull(jumin)) {
			return jumin;
		} else {
			return jumin.replaceAll(REGEX_JIMIN,"$1$2*******");
		}
	}

	/**
	 * <pre>
	 * 	전화번호 마스킹 처리
	 *  사용 예)
	 *  String phone1 = "01026292701";
		String phone2 = "010-2629-2701";
		System.out.println(maskingPhone2(phone1, 2)); // 010****2701
		System.out.println(maskingPhone2(phone1, 3)); // 0102629****
		System.out.println(maskingPhone2(phone2, 3)); // 010-****-2701
		System.out.println(maskingPhone2(phone2, 5)); // 010-2629-****
	 * </pre>
	 * @param 	phone '-'가 포함된 전화번호
	 * @param 	type 전화번호 마스킹의 위치 타입(m이면 가운데자리, 그 외에는 모두 마지막 자리)
	 * @return	마스킹 처리된 전화번호
	 */
	public static String maskingPhone(String phone, int location) {
		Matcher matcher = null;

		if (phone.indexOf("-") == -1) {
			matcher = Pattern.compile(REGEX_PHONE_NORMAL).matcher(phone);

			if (matcher.find()) {
				String replaceTarget = matcher.group(location);
				char[] c = new char[replaceTarget.length()];
				Arrays.fill(c, '*');
				return phone.replace(replaceTarget, String.valueOf(c));
			} else {
				return phone;
			}
		} else {
			matcher = Pattern.compile(REGEX_PHONE_HYPHEN).matcher(phone);

			if (matcher.find()) {
				String replaceTarget = matcher.group(location);
				char[] c = new char[replaceTarget.length()];
				Arrays.fill(c, '*');
				return phone.replace(replaceTarget, String.valueOf(c));
			} else {
				return phone;
			}
		}
	}

	public static String maskingddd(String jumin) {
		String REGEX_JIMIN = "(\\d{6})([-\\t\\n\\x0B\\f\\r])[1234]\\d{6}";
		if (stringIsNull(jumin)) {
			return jumin;
		} else {
			return jumin.replaceAll(REGEX_JIMIN,"$1$2*******");
		}
	}

	/**
	 * <pre>
	 * 	
	 * </pre>
	 * @param phone
	 * @return
	 */
	public static String maskingId(String id) {
		Matcher matcher = Pattern.compile(REGEX_MASKINGID).matcher(id);
		if (matcher.find()) {
			String replaceTarget = matcher.group(2);
			char[] c = new char[replaceTarget.length()];
			Arrays.fill(c, '*');
			return id.replace(replaceTarget, String.valueOf(c));
		} else {
			return id;
		}
	}

	/**
	 * <pre>
	 * 	이메일 마스킹
	 * 		userId의 길이를 기준으로 세글자 초과인 경우 뒤 세자리를 마스킹 처리하고,
	 * 		세글자인 경우 뒤 두글자만 마스킹, 세글자 미만인 경우 모두 마스킹 처리
	 * </pre>
	 * @param email
	 * @return 마스킹 처리된 이메일
	 */
	public static String maskingEmail(String email) {
		String regex = "\\b(\\S+)+@(\\S+.\\S+)";
		Matcher matcher = Pattern.compile(regex).matcher(email);

		if (matcher.find()) {
			String id = matcher.group(1);
			int length = id.length();

			if (length < 3) {
				char[] c = new char[length];
				Arrays.fill(c, '*');
	            email = id + email.replace(id, String.valueOf(c));
			} else if (length == 3) {
				char[] c = new char[length];
				Arrays.fill(c, '*');
				email = id + email.replace(id, String.valueOf(c));
			} else {
				char[] c = new char[length - 3];
				Arrays.fill(c, '*');
				email = email.replace(id, String.valueOf(c));
				email = id.substring(0,3) + email;
			}
		}

		return email;
	}

	/**
	 * <pre>
	 * 	Get으로 전송되는 Parameter 값 검증
	 * </pre>
	 * @param 	parameter Get으로 전송되는 파라미터 값
	 * @return	xss에 위배되는 파라미터를 사용 불가로 변조한 문자열 파라미터
	 */
	public static String xssFilte(String parameter) {
    	final String[] arrFilters = new String[] {
    			"<script>", "%3Cscript", "%3Ealert", "ja%0Av%0Aa%0As%0Ac%0Aript", "JaVaScRiPt", "javascript",
        		"ScRiPt%20%0a%0d", "JaVaScRiPt", "ScRiPt%20%0a%0d", "javascript", "vbscript", 
        		"applet", "meta", "xml", "blink", "style","script", "iframe", "frame",
        		"frameset", "ilayer", "layer", "bgsound", "eval", "innerHTML", "charset", 
        		"string", "alert", "msgbox", "refresh", "embed", "ilayer", "applet",
        		"cookie", "void", "href", "nabort", "@import", "aim:", "%0da=eval", "allowscriptaccess", "xmlns:html",
        		"<html xmlns", "xmlns:html=", "http-equiv=refresh", "http-equiv=refresh", "x-scriptlet", "0%0d%0a%00",
        		"moz-binding", "res://", "#exec", "background=", "&#x", "%u0", "string.fromcharcode", "firefoxurl",
        		"<br size=", "list-style-image", "acunetix_wvs", "wvs-xss", "lowsrc", "dynsrc", "behavior", "activexobject",
        		"microsoft.xmlhttp", "clsid:cafeefac-dec7-0000-0000-abcdeffedcba", "application/npruntime-scriptable-plugin;deploymenttoolkit",
        		"onactivae", "onafterprint", "onafterupdate", "onbefore", "onbeforeactivate", "onbeforecopy", "onbeforecut",
        		"onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste", "onbeforeprint", "onbeforeunload", "onbeforeupdate",
        		"onblur",  "onbounce", "oncellchange", "onchange", "onclick", "oncontextmenu", "oncontrolselect", "oncopy",
        		"oncut", "ondataavailable", "ondatasetchanged", "ondatasetcomplete", "ondblclick", "ondeactivate", "ondrag",
        		"ondragend", "ondragenter", "ondragleave", "ondragover", "ondragstart", "ondrop", "onerror", "onerrorupdate",
        		"onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout", "onhelp", "onkeydown", "onkeypress", "onkeyup",
        		"onlayoutcomplete", "onload", "onlosecapture", "onmousedown", "onmouseenter", "onmouseleave", "onmousemove", "onmouseout",
        		"onmouseover", "onmouseup", "onmousewheel", "onmove", "onmoveend", "onmovestart", "onpaste", "onpropertychange",
        		"onreadystatechange", "onreset", "onresize", "onresizeend", "onresizestart", "onrowenter", "onrowexit", "onrowsdelete",
        		"onrowsinserted", "onscroll", "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", "onunload"
    	};

    	int len 	= arrFilters.length;
    	String tmp 	= "";

    	if (parameter != null) {
    		for (int idx = 0; idx < len; idx++) {
    			tmp = "(?i)" + arrFilters[idx].trim();
    			parameter = parameter.replaceAll(tmp, "_");
    		}

    		return parameter;
		} else {
			return parameter;
		}
	}

	/**
	 * <pre>
	 * 	XSS 특수 문자(태그) 필터링
	 * </pre>
	 * @param 	val : 특수 문자로 변활 할 태그
	 * @return	특수 문자로 변환 된 문자 열
	 */
	public static String specialTranslate(String val) {
		final String[] whiteList = new String[] {"p", "br"};
		final String[] tags = new String[] {"<", ">", "\0", "</", "/>", "\"", "&", "\'"};
		final String[] specials = new String[] {"&lt;", "&gt;", " ", "&lt;/", "/&gt;", "&quot;", "&amp;", "&#039"};
		final int len = whiteList.length;
		int idx = 0;

		if (CommonUtil.checkString(val).length() > 0) {
			for (String tag : tags) {
				val = val.replaceAll(tag, specials[idx++]);
			}

			for (idx = 0; idx < len; idx++) {
				val = val.replaceAll(specials[0] + val + specials[2], tags[0] + val + specials[2]);
				val = val.replaceAll(specials[0] + val + specials[1], tags[0] + val + tags[1]);
				val = val.replaceAll(specials[2] + val + specials[1], specials[2] + val + tags[1]);
				val = val.replaceAll(val + specials[4], val + tags[4]);
				val = val.replaceAll(specials[3] + val, tags[3] + val);
			}
		}

		return val;
	}

	/**
	 * <pre>
	 * 	태그를 특수문자로 치환
	 * </pre>
	 * @param val
	 * @return 특수 문자로 치환된 문자열 반환
	 */
	public static String tagTranslate(String val) {
        if ("".equals(checkString(val))) return "";

        StringBuilder stringbuffer = new StringBuilder(val.length());
        int len = val.length();

        for (int idx = 0; idx < len; idx++) {
            char c = val.charAt(idx);

            if (c == '<') stringbuffer.append("&lt;");
            else if (c == '>') stringbuffer.append("&gt;");
            else if (c == '"') stringbuffer.append("&quot;");
            else if (c == '&') stringbuffer.append("&amp;");
            else if (c == '\'') stringbuffer.append("&#039");
            else stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

	/**
	 * <pre>
	 * 	Token 문자열 분리
	 * </pre>
	 * @param sourceString '[' + token + ']'로 구분된 문자열
	 * @param token 구분자
	 * @return 분리된 문자열을 List<String>에 저장하여 리턴
	 */
	public static List<String> tokenSplit(String sourceString, String token) {
        if (sourceString == null || sourceString.trim().equals("")) return null;

        List<String> list = new ArrayList<String>();
        String[] arrData = sourceString.split("[" + token + "]");
        int len = arrData.length;

        for (int idx = 0; idx < len; idx++) {
            list.add(arrData[idx]);
        }

        return list;
    }

	/**
	 * <pre>
	 * 	문자열 내용 중 enter가 있을 경우 <br>로 변환. 단, 변환 후 제일 마지막 <br>은 삭제
	 * </pre>
	 * @param val
	 * @return
	 */
    public static String enterToBrTag(String val) {
    	if (val == null || val.trim().equals("")) return "";

    	val = tagTranslate(val);
    	StringTokenizer stringtokenizer = new StringTokenizer(val, "\n");
    	StringBuilder stringbuffer = new StringBuilder();

    	for (; stringtokenizer.hasMoreTokens(); stringbuffer.append(stringtokenizer.nextToken() + "<br>")) ;

    	stringbuffer.replace(stringbuffer.lastIndexOf("<br>"), stringbuffer.length(), "");

    	return stringbuffer.toString();
    }

    /**
     * <pre>
     * 	String형 숫자를 int형 숫자로 반환한다. 변환이 안되는 String은 0으로 반환한다.
     * </pre>
     * @param requestParameter int로 변환한 문자
     * @return int형으로 바뀐 String
     */
    public static int parseInt(String requestParameter) {
    	try {
    		return Integer.parseInt(requestParameter);
    	} catch (Exception exception) {
    		return 0;
    	}
    }

    /**
     * <pre>
     * 	String형 숫자를 long형 숫자로 반환한다. 변환이 안되는 String은 0으로 반환한다.
     * </pre>
     * @param requestParameter long으로 변환한 문자
     * @return long형으로 바뀐 String
     */
    public static long parseLong(String requestParameter) {
    	try {
        	return Long.parseLong(requestParameter);
    	} catch (Exception exception) {
    		return 0;
    	}
    }

    /**
     * <pre>
     * 	String형 숫자를 float형 숫자로 반환한다. 변환이 안되는 String은 0.0f으로 반환한다.
     * </pre>
     * @param requestParameter float으로 변환한 문자
     * @return float형으로 바뀐 String
     */
    public static float parseFloat(String requestParameter) {
    	try {
    		return Float.parseFloat(requestParameter);
    	} catch (Exception exception) {
    		return 0;
    	}
    }

    /**
     * <pre>
     * 	String형 숫자를 double형 숫자로 반환한다. 변환이 안되는 String은 0.0d으로 반환한다.
     * </pre>
     * @param requestParameter double으로 변환한 문자
     * @return double형으로 바뀐 String
     */
    public static double parseDouble(String requestParameter) {
    	try {
    		return Double.valueOf(requestParameter).doubleValue();
    	} catch (Exception e) {
    		return 0.0D;
    	}
    }

    /**
     * <pre>
     * 	request로 받은 문자가 null이면 target(int)값을 반환한다.
     * </pre>
     * @param requestParameter
     * @param target
     * @return
     */
    public static int convertNullToInt(String requestParameter, int target) {
    	return stringIsNull(requestParameter) ? target : parseInt(requestParameter);
    }

    /**
     * <pre>
     * 	통화형식처럼 숫자 3자리미다 ,(콤마)를 찍는다. double(int)형 인자를 받는다.
     * </pre>
     * @param d double(int)형 통화형식
     * @return 3자리마다 ,(콤마)가 찍힌 형식
     */
    public static String moneyFormat(double requestParameter) {
    	try {
	        return NumberFormat.getNumberInstance().format(requestParameter);
    	} catch(NumberFormatException e) {
    		return "0";
    	}
    }

    /**
     * 통화형식처럼 숫자 3자리미다 ,(콤마)를 찍는다. String형 인자를 받는다.
     *
     * @param money String형 통화형식
     * @return 3자리마다 ,(콤마)가 찍힌 형식
     */
    public static String moneyFormat(String money) {
        NumberFormat numberformat = NumberFormat.getNumberInstance();

        try {
            return numberformat.format(numberformat.parse(money));
        } catch (ParseException parseexception) {
            return "0";
        }
    }

    /**
     * <pre>
     * 	대상문자열안의 논리적 값이 숫자를 표현하면 true, 아니면 false를 반환한다.
     * </pre>
     *
     * @param str 대상문자열
     * @return 대상문자열의 논리적 값이 숫자이면 true, 아니면 false
     */
    public static String isStringIsNumeric(String str) {
    	 NumberFormat nf = NumberFormat.getInstance();
    	
        try {
            nf.parse(str);
            return str;
        } catch (ParseException parseexception) {
            return "";
        }
    }

    /**
     * 대상문자열안의 논리적 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환
     *
     * @param str  대상문자열
     * @param size 원하는 자릿수
     * @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
     */
    public static String zerofill(String str, int size) {
        try {
            NumberFormat nf = NumberFormat.getInstance();
            return zerofill(nf.parse(str), size);
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * int형 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환
     *
     * @param num  대상 int형 숫자
     * @param size 원하는 자릿수
     * @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
     */
    public static String zerofill(int num, int size) {
        return zerofill(new Integer(num), size);
    }

    /**
     * double형 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환
     *
     * @param num  대상 double형 숫자
     * @param size 원하는 자릿수
     * @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
     */
    public static String zerofill(double num, int size) throws Exception {
        return zerofill(new Double(num), size);
    }

    /**
     * Number형 숫자앞에 원하는 size의 자릿수에 맞게 '0'를 붙인다. 예) 000000000 (일억자리수)에 345를 넣었을 경우 '000000345'반환
     *
     * @param num  대상 Number형 숫자
     * @param size 원하는 자릿수
     * @return 대상문자열에 자릿수 만큼의 '0'를 붙인 문자열
     */
    public static String zerofill(Number num, int size) {
        String zero = "";
        for (int i = 0; i < size; i++) {
            zero += "0";
        }
        DecimalFormat df = new DecimalFormat(zero);
        return df.format(num);
    }

    public static Timestamp makeTimestamp(String string) throws Exception {
        return makeTimestamp(string, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp makeTimestamp(String string, String format) throws Exception {
        SimpleDateFormat sourceFormatter = new SimpleDateFormat(format);
        Date date = sourceFormatter.parse(string);
        return new Timestamp(date.getTime());
    }

    /**
     * java.sql.Timestamp형태의 날짜를 "yyyy-mm-dd hh:mm:ss"형의 날짜로 변환하여 반환한다.
     *
     * @param ts java.sql.Timestamp형태의 날짜
     * @return "yyyy-MM-dd hh:mm:ss"형의 날짜
     */
    public static String getSQLDate(Timestamp ts) {
        if (ts == null) return "";
        else return ts.toString();
    }

    public static String getDate(Timestamp ts) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpledateformat.format(ts);
    }

    public static String getDate(Timestamp ts, String format) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.format(ts);
    }

    /**
     * "yyyy-MM-dd hh:mm:ss"형태의 날짜를 java.sql.Timestamp형의 날짜로 변환하여 반환한다.
     *
     * @param date "yyyy-MM-dd hh:mm:ss"형태의 날짜
     * @return java.sql.Timestamp형의 날짜
     */
    public static Timestamp setSQLDate(String date) {

        if (stringIsNull(date)) return null;
        else if (isYMDHMSF(date)) return Timestamp.valueOf(date);
        else if (isYMDHMS(date)) return Timestamp.valueOf(date + ".0");
        else if (isYMD(date)) return Timestamp.valueOf(date + " 00:00:00.0");
        else return null;

    }

    public static boolean isYMDHMSF(String date) {
        Pattern p = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{1}");
        Matcher m = p.matcher(date);
        return m.find();
    }

    public static boolean isYMDHMS(String date) {
        Pattern p = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}");
        Matcher m = p.matcher(date);
        return m.find();
    }

    public static boolean isYMD(String date) {
        Pattern p = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}");
        Matcher m = p.matcher(date);
        return m.find();
    }

    public static boolean validateDate(String dayOfBirth) {
        Pattern pattern =  Pattern.compile("^((19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$");
        Matcher matcher = pattern.matcher(dayOfBirth);

        return matcher.find();
    }

    /**
     * "yyyy-MM-dd hh:mm:ss"형의 날짜를 반환한다.
     *
     * @return yyyy-MM-dd hh:mm:ss 형의 날짜
     */
    public static String getThisDate() {
        return getThisDate("yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 인자로 들어오는 형식의 날짜를 반환한다.
     *
     * @param s 날짜 형식
     * @return 인자로 들어오는 형식의 날짜
     */
    public static String getThisDate(String s) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(s, Locale.US);
        return simpledateformat.format(new Date());
    }

    /**
     * 날짜 데이터가 오늘보다 과거인지 미래인지 체크한다.
     *
     * @param s yyyy-MM-dd 형식의 문자열 날짜 데이터
     * @return 과거면 false, 같거나 미래면 true
     */
    public static boolean isAway(String s) {
        return isAway(s.substring(0, 4), s.substring(5, 7), s.substring(8));
    }

    /**
     * 날짜 데이터가 오늘보다 과거인지 미래인지 체크한다.
     *
     * @param yyyy 년(年)
     * @param mm   월(月)
     * @param dd   일(日)
     * @return 과거면 false, 같거나 미래면 true
     */
    public static boolean isAway(String yyyy, String mm, String dd) {
        int y = parseInt(yyyy);
        int m = parseInt(mm);
        int d = parseInt(dd);
        return isAway(y, m, d);
    }

    /**
     * 날짜 데이터가 오늘보다 과거인지 미래인지 체크한다.
     *
     * @param y 년(年)
     * @param m 월(月)
     * @param d 일(日)
     * @return 과거면 false, 같거나 미래면 true
     */
    public static boolean isAway(int y, int m, int d) {

        boolean dateChk = false;

        GregorianCalendar rightNow = new GregorianCalendar();
        GregorianCalendar fromDate = new GregorianCalendar(y, (m - 1), d);

        if (fromDate.before(rightNow)) {
            dateChk = false; // date가 rightNow보다 과거에있다.
        } else {
            dateChk = true; // date가 rightNow보다 미래에있다.
        }

        return dateChk;
    }

    /**
     * 배열을 format 에 맞는 문자열로 변환.
     *
     * @return String
     */
    public static String delimitObjectsToString(String format, String[] arrayStr) throws Exception {

        StringBuilder sb = new StringBuilder();
        int arrayLength = arrayStr.length;

        for (int i = 0; i < arrayLength; i++) {
            sb.append(arrayStr[i]);

            if (arrayLength != (i + 1)) {
                sb.append(format);
            }
        }
        return sb.toString();
    }

 // --------------------------------------------------------------------------
    // CRYPTO 관련 UTIL
    // --------------------------------------------------------------------------

    public static String getMD5HexString(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        byte[] byteArray = md.digest();
        String hexString = "";
        String eip;

        for (int i = 0; i < byteArray.length; i++) {
            eip = "" + Integer.toHexString(byteArray[i] & 0x000000ff);
            if (eip.length() < 2) eip = "0" + eip;
            hexString = hexString + eip;
        }

        return hexString;
    }

    public static String decodeURL(String source) throws Exception {
        return URLDecoder.decode(source, "UTF-8");
    }

    public static String encodeURL(String source) throws Exception {
        return URLEncoder.encode(source, "UTF-8");
    }

    public static String encodeURL(String source, String characterSet) throws Exception {
        return URLEncoder.encode(source, characterSet);
    }

    public static String decodeHTMLEscapeCharacter(String source) {

        final String ESC_LT = "&lt;";
        final String ESC_GT = "&gt;";
        final String ESC_AMP = "&amp;";

        source.replaceAll(ESC_LT, "<");
        source.replaceAll(ESC_GT, ">");
        source.replaceAll(ESC_AMP, "&");

        return source;
    }

    // --------------------------------------------------------------------------
    // FILE 관련 UTIL
    // --------------------------------------------------------------------------

    public static String readFile(String filePath) throws Exception {

        BufferedReader in = null;

        try {

            in = new BufferedReader(new FileReader(filePath));
            String str;
            StringBuilder source = new StringBuilder();
            while ((str = in.readLine()) != null) {
                source.append(str).append("\n");
            }

            return source.toString();

        } catch (FileNotFoundException e) {
            throw new Exception("Reading File Not Found: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Readomg File Read Failed: " + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException ignored) { }
        }
    }

    public static void writeFile(String source, String filePath) throws Exception {
        writeFile(source, new File(filePath));
    }

    public static void writeFile(String source, File f) throws Exception {
        PrintWriter pw = null;

        try {

            pw = new PrintWriter(new FileWriter(f, false), true);
            pw.println(source);
            pw.flush();

        } catch (IOException e) {
            throw new Exception("File Create Failed: " + e.getMessage());
        } finally {
            pw.close();
        }
    }

    public static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException {

        Method method = null;
        try {
            method = Thread.class.getMethod("getContextClassLoader");
        } catch (NoSuchMethodException e) {
            return null;
        }
        return (ClassLoader) method.invoke(Thread.currentThread());
    }

    public static File getFileInClassPath(String filename) {

        URL url = null;
        try {
            ClassLoader classLoader = CommonUtil.getTCL();
            url = classLoader.getResource(filename);
        } catch (Exception e) {
            url = ClassLoader.getSystemResource(filename);
        }
        
        if (url == null) {
            return null;
        }

        return new File(url.getFile());
    }

    public static void copy(String source, String target) throws FileNotFoundException, IOException {
        copy(new File(source), new File(target));
    }

    @SuppressWarnings("resource")
	public static void copy(File source, File target) throws FileNotFoundException, IOException {
    	FileChannel srcChannel = null;
    	FileChannel dstChannel = null;
    	try {
    	    srcChannel = new FileInputStream(source).getChannel();
    	    dstChannel = new FileOutputStream(target).getChannel();
    	    dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
    	} catch (IOException e) {
    	    logger.error("srcChannel/dstChannel transferFrom IOException error " + e.getMessage());
    	} finally {
    	    if (srcChannel != null) {
    	        try {
    	            srcChannel.close();
    	        } catch (IOException e) {
    	        	logger.error("srcChannel close IOException error " + e.getMessage());
    	        }
    	    }
    	    if (dstChannel != null) {
    	        try {
    	            dstChannel.close();
    	        } catch (IOException e) {
    	        	logger.error("dstChannel close IOException error " + e.getMessage());
    	        }
    	    }
    	}
    }

    /**
     * 파일의 확장자를 구합니다.
     *
     * @param fileName 파일 이름
     */
    public static String getFileExtention(String fileName) {
        if (CommonUtil.stringIsNull(fileName)) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        }
    }

    // --------------------------------------------------------------------------
    // Stream 관련 UTIL
    // --------------------------------------------------------------------------

    public static String getReader2String(Reader reader) throws IOException {

        int bytesRead = 0;
        char[] buffer = new char[1024];
        StringBuilder sb = new StringBuilder();
        while ((bytesRead = reader.read(buffer, 0, 1024)) != -1) {
            sb.append(buffer, 0, bytesRead);
        }
        reader.close();
        reader = null;
        return sb.toString();
    }

    public static final byte[] stream2bytes(InputStream is) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int size;
            while ((size = is.read(buffer)) > 0) {
                out.write(buffer, 0, size);
                out.flush();
            }
            return out.toByteArray();
        } catch (IOException e) {
        } finally {
            if (out != null) try {
                out.close();
                out = null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

	/*
     * String url =
	 * "http://www.freeegg.com/contents/mov/i/e/_view.egg?contentsIdx=114579&chart=d&stDate=20080428&type=10"
	 * ; getStringBetween(url, 2, 3, "/"); -> www.freeegg.com
	 * getStringBetween(url, 3, 0, "/"); ->
	 * contents/mov/i/e/_view.egg?contentsIdx
	 * =114579&chart=d&stDate=20080428&type=10
	 */

    public static String getStringBetween(String str, int first, int end, String seperator) {
        int pos = 0;
        int loopCount = 0;
        int startPos = 0;
        int endPos = 0;
        while (true) {
            pos = str.indexOf(seperator, pos);
            loopCount++;
            if (loopCount == first) {
                if (first == 0) {
                    startPos = 0;
                    endPos = pos;
                    break;
                } else if (end == 0) {
                    startPos = pos;
                    endPos = str.length();
                    break;
                } else {
                    startPos = pos;
                }
            } else if (loopCount == end) endPos = pos;
            if (pos < 0) break;
            else pos++;
        }

        return str.substring(startPos + 1, endPos);
    }

    /**
     * 로케일을 받아서 korea 로케일인지 여부를 판단합니다.
     */
    public static boolean isKoreaLocale(Enumeration<Locale> locales) {
        for (; locales.hasMoreElements(); ) {
            Locale locale = locales.nextElement();
            if (Locale.KOREA.toString().equals(locale.toString()) || Locale.KOREAN.toString().equals(locale.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * null이거나 isEmpty()가 true 또는 length가 0 일때 true 를 반환
     *
     * @param obj Collection, Map 이나 String[]
     * @return null이거나 공백이면 true반환, 아니면 false 반환, Collection 이나 Map 이아닌경우 null 로 판단
     */
    @SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {

        if (obj == null) {
            return true;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj instanceof String[]) {
            return ((String[]) obj).length <= 0;
        }
        return true;
    }

    /**
     * 특수문자 제거
     */
    public static String removeSpecialLetter(String str) {
//        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        // Percent-encoding < !#$&'()*+,/:;=?@[] > 문자 모두 스페이스로 변경
        String match = "[!#$&'()*+,/:;=?@\\[\\]]";
        str = str.replaceAll(match, " ");
        return str;
    }

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
    
    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName();
    }

	public static <T> List<List<T>> partition(final List<T> list, final int size) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        return new Partition<T>(list, size);
    }

    private static class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int size;

        private Partition(final List<T> list, final int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(final int index) {
            final int listSize = size();
            if (listSize < 0) {
                throw new IllegalArgumentException("negative size: " + listSize);
            }
            if (index < 0) {
                throw new IndexOutOfBoundsException("Index " + index + " must not be negative");
            }
            if (index >= listSize) {
                throw new IndexOutOfBoundsException("Index " + index + " must be less than size " +
                        listSize);
            }
            final int start = index * size;
            final int end = Math.min(start + size, list.size());
            return list.subList(start, end);
        }

        @Override
        public int size() {
            return (list.size() + size - 1) / size;
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }
    }

    public static String mapToString(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }

    public static String getValue(HashMap<String, String> map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    public static void copyNonNullProperties(Object src, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src, ignoreProperties));
    }

    public static String[] getNullPropertyNames(Object source, String... ignoreProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null && Arrays.stream(ignoreProperties).noneMatch(p -> p.equals(pd.getName()))) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

	/**
	 * <pre>
	 * 	XSS 관련 제거
	 * </pre>
	 * @param 	val : 파라미터
	 * @return	XSS 관련 제거된 문자열
	 */
	public String cleanXSS(String val) {
		val = val.replaceAll(" ", "");

        Pattern scriptPattern = Pattern.compile("<script>[.*?]</script>", Pattern.CASE_INSENSITIVE);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
        val = scriptPattern.matcher(val).replaceAll("");

        scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        val = scriptPattern.matcher(val).replaceAll("");

        return val;
	}

	/**
	 * <pre>
	 * 	SQL Injection 방어를 위한 쿼리 관련 parameter 필터링
	 * </pre>
	 * @param 	val : parameter
	 * @return	불필요한 parameter를 제거된 쿼리 문자열
	 */
	public String sqlFilter(String val) {
		final String[] filters = new String[] {"'", "\"", "--", ",", "\\(", "\\)", "#", ">", "<", "=", "\\*/", "/\\*", "\\+", "%", ",", "@", ";", "\\\\", "|", "[", "]", "&"};

		for (String string : filters) {
			val = val.replaceAll(string, "");
		}

		return val;
	}

	
	
	/**
	 * <pre>
	 * 	영문대문자, 영문소문자, 숫자, 특수문자 중 2종류 조합인 경우 10자리 이상 15자리 이내의 길이로 구성 여부 확인
	 *  영문대문자, 영문소문자, 숫자, 특수문자 중 3종류 조합인 경우 8자리 이상 15자리 이내의 길이로 구성 여부 확인
	 *  2021.06.23 보안정책변경 수정
	 * </pre>
	 * @param 	val 비밀번호
	 * @return 	true : 사용가능, false : 사용 불가
	 */
	public static boolean checkUnion(String val) {
		val = CommonUtil.checkString(val);
		final int valLength = val.length();
		boolean NumberCH = false;
		boolean CharCH = false;
		boolean SpecialCharCH = false;
		final int specialLength = REGEX_SPECIAL.length();
		String chNumber ="0123456789";
		char tmp;
		for (int idx = 0; idx < valLength; idx++) {
			tmp = val.charAt(idx);
			
	        for(int j=0; j<chNumber.length();j++){
	            if(chNumber.charAt(j) == tmp){
	                NumberCH = true;
	            }
	        }
	        
	        if(((tmp >='a') && (tmp <='z')) || ((tmp >='A') && (tmp <='Z'))){
	            CharCH = true;
	        }
			
			for (int idx2 = 0; idx2 < specialLength; idx2++) {
				if (REGEX_SPECIAL.charAt(idx2) == tmp) {
					SpecialCharCH = true;
				}
			}
		}
		
	    if((NumberCH == true) && (CharCH == true) && (SpecialCharCH == false) && (valLength >= 10)){
			return true;
		}else if((NumberCH == true) && (CharCH == true) && (SpecialCharCH == true) && (valLength >= 8)){
	        return true;
	    }else {
			return false;
		}
	}

//	/**
//	 * <pre>
//	 * 	영문대문자, 영문소문자, 숫자, 특수문자 중 3종류 이상을 조합하여 8자리 이상 15자리 이내의 길이로 구성 여부 확인 (이전사용)
//	 * </pre>
//	 * @param 	val 비밀번호
//	 * @return 	true : 사용가능, false : 사용 불가
//	 */
//	public static boolean checkUnion(String val) {
//		val = CommonUtil.checkString(val);
//		final int valLength = val.length();
//		final int specialLength = REGEX_SPECIAL.length();
//		int sum = 0;
//		char tmp;
//
//		for (int idx = 0; idx < valLength; idx++) {
//			tmp = val.charAt(idx);
//
//			if (tmp >= '0' && tmp <= '9') {
//				sum += 1;
//			}
//
//			if (tmp >= 'a' && tmp <= 'z') {
//				sum += 1;
//			}
//
//			if (tmp >= 'A' && tmp <= 'Z') {
//				sum += 1;
//			}
//
//			for (int idx2 = 0; idx2 < specialLength; idx2++) {
//				if (REGEX_SPECIAL.charAt(idx2) == tmp) {
//					sum += 1;
//				}
//			}
//		}
//		if (sum > 2) {
//			if(sum > 3) {
//				return valLength > 7 && valLength < 16 ? true : false;
//			}
//			return valLength > 7 && valLength < 16 ? true : false;
//		} else {
//			return false;
//		}
//	}
	
	/**
	 * <pre>
	 * 	3회 이상 반복 연속된 문자와 숫자 사용 여부 확인(특수문자 제외)
	 * </pre>
	 * @param 	val 비밀번호
	 * @return	true : 사용 가능, false : 사용 불가
	 */
	public static boolean oneStringRepetition(String val) {
		val = CommonUtil.checkString(val);
		final int valLength = val.length() - 2;
		String[] tmpArray = new String[3];

		for (int idx = 0; idx < valLength; idx++) {
			tmpArray[0] = val.substring(idx, idx + 1);
			tmpArray[1] = val.substring(idx + 1, idx + 2);
			tmpArray[2] = val.substring(idx + 2, idx + 3);

			if ((tmpArray[0].charAt(0) >= 'a' && tmpArray[0].charAt(0) <= 'z')
				|| (tmpArray[0].charAt(0) >= 'A' && tmpArray[0].charAt(0) <= 'Z')
				|| (tmpArray[0].charAt(0) >= '0' && tmpArray[0].charAt(0) <= '9')
				|| (tmpArray[1].charAt(0) >= 'a' && tmpArray[1].charAt(0) <= 'z')
				|| (tmpArray[1].charAt(0) >= 'A' && tmpArray[1].charAt(0) <= 'Z')
				|| (tmpArray[1].charAt(0) >= '0' && tmpArray[1].charAt(0) <= '9')
				|| (tmpArray[2].charAt(0) >= 'a' && tmpArray[2].charAt(0) <= 'z')
				|| (tmpArray[2].charAt(0) >= 'A' && tmpArray[2].charAt(0) <= 'Z')
				|| (tmpArray[2].charAt(0) >= '0' && tmpArray[2].charAt(0) <= '9')) {
				if (tmpArray[0].equals(tmpArray[1])) {
					if (tmpArray[1].equals(tmpArray[2])) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * <pre>
	 * 	두 자 이상의 동일문자 연속성 확인(두 번 이상 중복될 경우)
	 * </pre>
	 * @param 	val 비밀번호
	 * @return	true : 사용 가능, false : 사용 불가
	 */
	public static boolean twoStringRepetition(String val) {
		val = CommonUtil.checkString(val);
		final int valLength = val.length() - 2;
		final int valLength2 = val.length() - 1;
		String[] tmpArray = new String[2];
		int count = 0;

		for (int idx = 0; idx < valLength; idx++) {
			count = 0;
			tmpArray[0] = val.substring(idx, idx + 2);

			for (int idx2 = 0; idx2 < valLength2; idx2++) {
				tmpArray[1] = val.substring(idx2, idx2 + 2);

				if (tmpArray[1].equals(tmpArray[0])) {
					count++;

					if (count >= 2) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * <pre>
	 * 	문자 또는 숫자의 연속성 확인
	 * </pre>
	 * @param val 비밀번호
	 * @return true: 사용 가능, false : 사용 불가능
	 */
	public static boolean checkContinuity(String val) {
		int int1, int2, int3;
		int1 = int2 = int3 = 0;

		for (int idx = 0; idx < val.length() - 2; idx++) {
			int1 = (int) val.charAt(idx);
			int2 = (int) val.charAt(idx + 1);
			int3 = (int) val.charAt(idx + 2);

			if ((int1 - int2) == 1 && (int2 - int3) == 1) {
				return false;
			}

			if ((int1 - int2) == -1 && (int2 - int3) == -1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * <pre>
	 * 	아이디가 비밀번호에 사용되었는지 여부
	 * </pre>
	 * @param 	val 비밀번호
	 * @param 	val2 사용자 아이디
	 * @return	true : 사용 가능, false : 사용 불가
	 */
	public static boolean personalInformation(String val, String val2) {
		return val.indexOf(val2) > -1 ? false : true;
	}

	/**
	 * <pre>
	 * 	특정 패턴을 갖는 문자열 확인
	 * </pre>
	 * @param 	val 비밀번호
	 * @return	true : 사용 가능, false : 사용 불가
	 */
	public static boolean keyboardContinuity(String val) {
		final String[] keyboard = {
			"0123456789", "9876543210"
			, "QWERTYUIOP", "POIUYTREWQ"
			, "ASDFGHJKL", "LKJHGFDSA"
			, "ZXCVBNM", "MNBVCXZ"
			, "1QAZ", "ZAQ1"
			, "2WSX", "XSW2"
			, "3EDC", "CDE3"
			, "4RFV", "VFR4"
			, "5TGB", "BGT5"
			, "6YHN", "NHY6"
			, "7UJM", "MJU7"
			, "0OKM", "MKO0"
			, "9IJN", "NJI9"
			, "8UHB", "BHU8S"
			, "7YGV", "VGY7"
			, "6TFC", "CFT6"
			, "5RRDX", "XDR5"
			, "4ESZ", "ZSE4"
		};

		for(int idx = 0; idx < keyboard.length; idx++){
			if (val.toUpperCase().indexOf(keyboard[idx]) > -1) {
				return false;
			}
	    }

		return true;
	}

	/**
	 * <pre>숫자 , 출력</pre>
	 * @param money
	 * @return
	 */
	public static String numberFormat(long money) {
		try {
			if (money > 0) {
				NumberFormat numberFormat = NumberFormat.getInstance();
				return numberFormat.format(money);
			} else {
				return "";
			}
		} catch(NumberFormatException nfe) {
			return money + "";
		}
	}
	
	/*
	 * 프로퍼티 메세지를 반환한다.
	 */
	public static String keyPropStr(String str) {
		String message="";
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream is = cl.getResourceAsStream("encryption.properties");
		try {
		    Properties props = new Properties();
		    props.load(is);
		    message = (String)props.get(str);
		} catch (IOException e) {
			logger.error("keyPropStr IOException Error : " + e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("keyPropStr IOException Error : " + e.getMessage());
			}
		}
		return message;
	}
}