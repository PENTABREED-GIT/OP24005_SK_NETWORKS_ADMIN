package com.skn.admin.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class NTUtil {
    public NTUtil() {
        // TODO Auto-generated constructor stub
    }

    public static String copyFile(String src, String dest){
        try{
            File fsrc = new File(src);
            File fdest = new File(dest);
            InputStream in = new FileInputStream(fsrc);


            OutputStream out = new FileOutputStream(fdest);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

        }
        catch(FileNotFoundException ex){
            return (ex.getMessage() + " in the specified directory.");

        }
        catch(IOException e){
            return e.getMessage();
        }
        return "";
    }
    public static boolean deleteDirectory(File path) {
        if(!path.exists()) {
            return false;
        }

        File[] files = path.listFiles();
        for (int i=0;i<files.length;i++) {
            File file=files[i];
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }

        return path.delete();
    }

    public static void moveDirectory(File sourceLocation, File targetLocation)
    {
        if(sourceLocation.isDirectory()) {
            if(!targetLocation.exists())
            {
                targetLocation.mkdir();
            }

            String[] child = sourceLocation.list();
            for(int i=0;i<child.length;i++)
            {
                moveDirectory(new File(sourceLocation, child[i]), new File(targetLocation, child[i]));
            }
        } else {
            copyFile(sourceLocation.getPath(), targetLocation.getPath());
        }

    }
    ////////HTTP METHOD///////////////////////////////

    public static String getWebSource(String sUrl){
        return getWebSource(sUrl, "UTF-8");
    }
    public static String getWebSourcePost(String sUrl, String data){
        return getWebSourcePost(sUrl,data, "UTF-8");
    }

    public static String getWebSource(String sUrl, String charset){
        BufferedReader in = null;
        StringBuffer sb = null;
        String strurl = sUrl;
        sb = new StringBuffer();

        try{

            URL url = new URL(strurl);
            URLConnection urlConnection = url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));
            //byte[] bufRead = new byte[4096];
            //int lenRead = 0;
            String rline = null;

            while((rline = in.readLine()) != null ){
                sb.append(rline);
                sb.append("\n");
            }

        } catch (IOException ioe){

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ioe.printStackTrace(pw);
            return "errorX:"+sw.toString().replaceAll("\n", "<br/>");

        }
        return sb.toString();
    }
    public static String getWebSourcePost(String sUrl, String data, String charset){
        BufferedReader in = null;
        StringBuffer sb = null;
        String strurl = sUrl;
        sb = new StringBuffer();

        try{

            URL url = new URL(strurl);
            URLConnection urlConnection = url.openConnection();

            urlConnection.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream(), charset);
            wr.write(data);
            wr.flush();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));
            //byte[] bufRead = new byte[4096];
            //int lenRead = 0;
            String rline = null;

            while((rline = in.readLine()) != null ){
                sb.append(rline);
                sb.append("\n");
            }

        } catch (IOException ioe){

            return "errorX:"+ioe.toString();
        }
        return sb.toString();
    }

    public static String getHttpBoundary(String str)
    {
        int index = str.lastIndexOf("boundary=");
        if (index == -1) {
            return null;
        }
        String boundary = str.substring(index + 9);  // 9 "boundary="
        if (boundary.charAt(0) == '"') {
            index = boundary.lastIndexOf('"');
            boundary = boundary.substring(1, index);
        }

        boundary = boundary;

        return boundary;
    }
    public static String getRequestBody(HttpServletRequest req) throws IOException
    {
        //return "";

        StringBuffer stringBuilder = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {

            InputStream inputStream = req.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        return stringBuilder.toString();

    }

    /**
     * request로 넘어온 파라미터를 Map으로 변환 - 배열은 무시
     * @param request
     * @return Map<String, String>
     * @throws Exception
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) throws Exception {
        Map<String, String> returnMap = new HashMap();
        Map<String, String[]> paramMap = request.getParameterMap();
        Iterator iterator = paramMap.keySet().iterator();

        String key = null;
        String paramsKeySet = "";

        while (iterator.hasNext()) {
            key = iterator.next().toString();
            paramsKeySet += key + "^";
            returnMap.put(key, (String) (isEmpty(paramMap.get(key)) ? "" : paramMap.get(key)));
        }

        returnMap.put("paramsKeySet", paramsKeySet.length() == 0 ? "" : paramsKeySet.substring(0, paramsKeySet.length() - 1));

        return returnMap;
    }

    //////HTTP METHOD END///////////////////////////////

    //////ENCODE & DECODE //////////////////////////////
    public static String getRandomGUID() {
        String valueBeforeMD5 = "";
        String valueAfterMD5 = "";
        Random myRand;
        SecureRandom mySecureRand;
        String s_id="";
        boolean secure=false;
        /*
         * Static block to take care of one time secureRandom seed.
         * It takes a few seconds to initialize SecureRandom.  You might
         * want to consider removing this static block or replacing
         * it with a "time since first loaded" seed to reduce this time.
         * This block will run only once per JVM instance.
         */


        mySecureRand = new SecureRandom();
        long secureInitializer = mySecureRand.nextLong();
        myRand = new Random(secureInitializer);
        try {
            s_id = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            System.out.println("ERROR:NTUtil:알 수없는 호스트 예외발생:getRandomGUID()");

        }

        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer();

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            //System.out.println("Error: " + e);
            System.out.println("ERROR:NTUtil:NoSuch 알고리즘 예외발생:getRandomGUID()");
        }

        try {
            long time = System.currentTimeMillis();
            long rand = 0;

            if (secure) {
                rand = mySecureRand.nextLong();
            } else {
                rand = myRand.nextLong();
            }

            // This StringBuffer can be a long as you need; the MD5
            // hash will always return 128 bits.  You can change
            // the seed to include anything you want here.
            // You could even stream a file through the MD5 making
            // the odds of guessing it at least as great as that
            // of guessing the contents of the file!
            sbValueBeforeMD5.append(s_id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));

            valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());

            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }

            valueAfterMD5 = sb.toString();

        } catch (Exception e) {
            System.out.println("Error:" + e);
        }

        String raw = valueAfterMD5.toUpperCase();
        StringBuffer sb = new StringBuffer();
        sb.append(raw.substring(0, 8));
        sb.append("-");
        sb.append(raw.substring(8, 12));
        sb.append("-");
        sb.append(raw.substring(12, 16));
        sb.append("-");
        sb.append(raw.substring(16, 20));
        sb.append("-");
        sb.append(raw.substring(20));

        return sb.toString();
    }
    public static String decodeToken(String signedReq) throws Exception
    {
        //Once user authorized the application, FB returns the following parameter
        //String signedReq = ;

        if(signedReq == null)
        {
            System.out.println("ERROR: Unable to retrieve signed_request parameter");
            return "ERROR";
        }

        int count = 0;
        String payload = null;

        //The parameter contains encoded signature and payload separated by '.'
        StringTokenizer st = new StringTokenizer(signedReq, ".");

        //Retrieve payload (Note: encoded signature is used for internal verification and it is optional)
        while (st.hasMoreTokens())
        {
            if(count == 1)
            {
                payload = st.nextToken();
                break;
            }
            else
                st.nextToken();

            count++;
        }

        //Initialize Base64 decoder

        //Replace special character in payload as indicated by FB
        payload = payload.replace('-', '+').replace('_', '/').trim();

        //Decode payload
        try
        {
            byte[] decodedPayload = Base64.getDecoder().decode(payload);
            payload = new String(decodedPayload, "UTF8");
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Unable to perform Base64 Decode");
            return "ERROR";
        }
				/*
		        //JSON Decode - payload
		        try
		        {
		            JSONObject payloadObject = new JSONObject(payload);
		            String facebookId = "" + payloadObject.get("user_id"); //Retrieve user ID
		            String oauthToken = "" + payloadObject.get("oauth_token"); //Retrieve oauth token
		        }
		        catch (JSONException e)
		        {
		            System.out.println("ERROR: Unable to perform JSON decode");
		            return ERROR;
		        }
				*/
        return payload;
    }
    public static String urlEncode(String original)
    {
        try
        {
            String t= URLEncoder.encode(original, "utf-8");
            return t.replaceAll("\\+", "%20");
        }
        catch(UnsupportedEncodingException e)
        {
            System.out.println("UnsupportedEncodingException:"+e);
        }
        return null;
    }
    public static String urlDecode(String original)
    {
        try
        {
            String t=URLDecoder.decode(original, "utf-8");
            return t;
        }
        catch(UnsupportedEncodingException e)
        {
            System.out.println("UnsupportedEncodingException:"+e);
        }
        return null;
    }
    public static String uni2Str(String uni)
    {
        String str = "" ;

        StringTokenizer str1 = new StringTokenizer(uni,"\\u") ;

        while(str1.hasMoreTokens())
        {
            String str2 = str1.nextToken() ;
            int i = Integer.parseInt(str2,16) ;
            str += (char)i ;
        }
        return str ;
    }
    public static String base64Encode(String data)
    {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    public static String base64Encode(byte[] data)
    {
        return Base64.getEncoder().encodeToString(data);
    }
    public static String base64Decode(String data)  throws IOException
    {
        String retval="";
        retval = new String(Base64.getDecoder().decode(data));
        return retval;
    }
    public static byte[] base64DecodeByte(String data)  throws IOException
    {
        String retval="";
        return Base64.getDecoder().decode(data);

    }
    public static byte[] calcSHA1(String signKey, String data) throws Exception
    {
        byte[] keyBytes = signKey.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        // Get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());

        return rawHmac;


    }
    public static byte[] encryptAES(String data, String key) throws Exception
    {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted =
                    cipher.doFinal(data.getBytes());
            return encrypted;
        } catch (Exception e) {
            throw e;
        }


    }

    public static String decryptAES(byte[] data, String key) throws Exception
    {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(data);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            throw e;
        }

    }
    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
    //////ENCODE & DECODE END//////////////////////////////

    //////SHELLCOMMAND START//////////////////////////////
    public static String shellExecute(String cmd) throws IOException, Exception
    {
        //cmd "./test.sh"


        return shellExecute(cmd, false);
    }
    public static String shellExecute(String cmd, boolean silent) throws IOException, Exception
    {
        //cmd "./test.sh"

        String resultString="";
        Runtime r=Runtime.getRuntime();
        Process p =null;
        p=r.exec(cmd);
        if(!silent)
        {
		 		/*
		    	BufferedReader br=new BufferedReader(new InputStreamReader(new SequenceInputStream(p.getInputStream(), p.getErrorStream()), "utf-8"));//new InputStreamReader(p.getInputStream());

		    	String line=null;
		    	while((line=br.readLine())!=null){
		    		resultString+=line;
		    		resultString+="\n";
		    	}
		    	*/
            p.waitFor();

            return Integer.toString(p.exitValue());
        }
        p.waitFor();


        return Integer.toString(p.exitValue());
    }
    //////SHELLCOMMAND END//////////////////////////////


    //////BINARY SEARCH START//////////////////////////////
    public static int indexOf(byte[] tbyte, String fval, int begin) {
        byte[] strbyte=fval.getBytes();
        if(tbyte.length-strbyte.length<0)
        {
            return -1;
        }
        if(begin>=tbyte.length-(strbyte.length-1) || begin<0)
            return -1;


        for(int i=begin;i<tbyte.length-(strbyte.length-1);i++)
        {
            boolean bfind=true;
            for(int j=0;j<strbyte.length;j++)
            {
                if(strbyte[j]!=tbyte[i+j])
                {
                    bfind=false;
                    break;
                }
            }
            if(bfind)
                return i;

        }
        return -1;
    }
    public static int indexOf(byte[] tbyte, String fval) {

        return indexOf(tbyte, fval, 0);
    }
    public static int patternCheck(byte[] tbyte, String fval, int begin)
    {
        byte[] strbyte=fval.getBytes();

        for(int j=0;j<strbyte.length;j++)
        {
            if(j>0)
            {
                if(begin+j>=tbyte.length)
                {
                    return 1;//일치할지도 모름
                }
            }
            if(strbyte[j]!=tbyte[begin+j])
            {
                return -1;//일치하지 않음
            }
        }
        return 0;//100%일치함
    }
    public static int lastIndexOf(byte[] tbyte, String fval, int begin) {

        byte[] strbyte=fval.getBytes();
        if(tbyte.length-strbyte.length<0)
        {
            return -1;
        }
        if(begin>tbyte.length-strbyte.length || begin<0)
            return -1;

        for(int i=tbyte.length-strbyte.length-begin;i>=0;i--)
        {
            boolean bfind=true;
            for(int j=0;j<strbyte.length;j++)
            {
                if(strbyte[j]!=tbyte[i+j])
                {
                    bfind=false;
                    break;
                }
            }
            if(bfind)
                return i;

        }


        return -1;
    }
    public static int lastIndexOf(byte[] tbyte, String fval) {
        return lastIndexOf(tbyte, fval, 0);
    }


    public static int indexOfIG(byte[] tbyte, String fval, int begin) {


        if(tbyte.length-fval.length()<0)
        {
            return -1;
        }
        if(begin>=tbyte.length-(fval.length()-1) || begin<0)
            return -1;

        for(int i=begin;i<tbyte.length-(fval.length()-1);i++)
        {
            String t=new String(tbyte, i, fval.length());
            if(t.toLowerCase().equals(fval.toLowerCase()))
            {
                return i;
            }

        }
        return -1;
    }
    public static int indexOfIG(byte[] tbyte, String fval) {

        return indexOfIG(tbyte, fval, 0);
    }
    public static int lastIndexOfIG(byte[] tbyte, String fval, int begin) {
        if(tbyte.length-fval.length()<0)
        {
            return -1;
        }
        if(begin>tbyte.length-fval.length() || begin<0)
            return -1;
        for(int i=tbyte.length-fval.length()-begin;i>=0;i--)
        {
            String t=new String(tbyte, i, fval.length());
            if(t.toLowerCase().equals(fval.toLowerCase()))
            {
                return i;
            }

        }
        return -1;
    }
    public static int lastIndexOfIG(byte[] tbyte, String fval) {
        return lastIndexOfIG(tbyte, fval, 0);
    }
    //////BINARY SEARCH END//////////////////////////////

    ////FILE//////////////////
    static public String getFileExt(String fileName)
    {
        String ext = "";
        int slash =
                Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (slash > -1) {
            fileName = fileName.substring(slash + 1);  // past last slash
        }

        if (fileName.indexOf(".") != -1) {
            ext=fileName.split("\\.")[fileName.split("\\.").length-1];
        }

        return ext;
    }
    ////FILE END////////////////


    ////STRING/////////////////
    static public String isNull(Object checkStr, String value)
    {
        return (checkStr==null?(value==null?"":value):checkStr.toString());
    }
    static public String isNull(Object checkStr)
    {
        return isNull(checkStr, null);
    }
    static public String isNull(String checkStr, String value)
    {
        return isNull((Object)checkStr,value);

    }
    static public String isNull(String checkStr)
    {
        return isNull((Object)checkStr,null);
    }
    public static String cutString(String t, int len)
    {
        String src=NTUtil.isNull(t);
        if(len<src.length())
        {
            return (src.substring(0, len-1)+"...");
        }
        return src;
    }
    public static String cutString(String t, int len, String tail)
    {
        String src=NTUtil.isNull(t);
        if(len<src.length())
        {
            return (src.substring(0, len-1)+tail);
        }
        return src;
    }
    public static boolean isEmpty(Object obj)  {
        if (obj == null || obj.toString().equals("")) return true;
        return false;
    }
    //////////////////////////


    ////DATE////////////////
    public static String getTimestamp()
    {
        return getTimestamp(1000);
    }

    public static String getTimestamp(int pDiv)
    {
        long ts = 0;
        if(pDiv>0)
        {
            ts = (long)(Calendar.getInstance().getTime().getTime()/pDiv);
        }

        return Long.toString(ts);
    }

    /**
     * DATE FORMAT
     *   ex1) yyyyMMdd : 99991231
     *   ex2) yyyy/MM/dd : 9999/12/31
     *   ex3) yyyy-MM-dd : 9999-12-31
     *   ex3) yyyy-MM-dd HH:mm:ss.SSS : 9999-12-31 11:59:12.235
     * @param format
     * @return
     */
    public static String getDateTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }


    public static Date strToDate(String t, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                format, Locale.ENGLISH);
        dateFormat.setLenient(false);
        Date created = null;
        try {
            created = dateFormat.parse(t);
        } catch (Exception e) {
            return null;
        }
        return created;
    }
    public static String dateToStr(Date t, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(t);
    }
    public static String getShowDate(String dateStr, String format, String lang)
    {
        //"EEE MMM dd HH:mm:ss ZZZZZ yyyy"
        // parse Twitter date

        Date created = strToDate(dateStr, format);
        // today
        Date today = new Date();

        // how much time since (ms)
        long duration = today.getTime() - created.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        if(lang.equals("en"))
        {
            if (duration < second * 7) {
                return "right now";
            }

            if (duration < minute) {
                int n = (int) Math.floor(duration / second);
                return n + " seconds ago";
            }

            if (duration < minute * 2) {
                return "1 minute ago";
            }

            if (duration < hour) {
                int n = (int) Math.floor(duration / minute);
                return n + " minutes ago";
            }

            if (duration < hour * 2) {
                return "1 hour ago";
            }

            if (duration < day) {
                int n = (int) Math.floor(duration / hour);
                return n + " hours ago";
            }
            if (duration > day && duration < day * 2) {
                return "yesterday";
            }

            if (duration < day * 365) {
                int n = (int) Math.floor(duration / day);
                return n + " days ago";
            } else {
                return "over a year ago";
            }
        }
        else
        {
            if (duration < second * 7) {
                return "지금";
            }

            if (duration < minute) {
                int n = (int) Math.floor(duration / second);
                return n + " 초 전";
            }

            if (duration < minute * 2) {
                return "약 1 분 전";
            }

            if (duration < hour) {
                int n = (int) Math.floor(duration / minute);
                return n + " 분 전";
            }

            if (duration < hour * 2) {
                return "약 1 시간 전";
            }

            if (duration < day) {
                int n = (int) Math.floor(duration / hour);
                return n + " 시간 전";
            }
            if (duration > day && duration < day * 2) {
                return "어제";
            }

            if (duration < day * 365) {
                int n = (int) Math.floor(duration / day);
                return n + " 일 전";
            } else {
                return "1년 이상 전";
            }
        }
    }
    ////DATE////////////////


    public static long getDateDiff(String dateType, Date date1, Date date2)
    {
        // how much time since (ms)
        long duration = date1.getTime() - date2.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;

        if(dateType.equals("s"))	// 밀리초 단위
        {
            return duration;
        }

        if(dateType.equals("S"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/second) );
        }

        if(dateType.equals("m"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/minute) );
        }

        if(dateType.equals("H"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/hour) );
        }

        if(dateType.equals("d"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/day) );
        }

        return 0;
    }
    ////DATE////////////////


    public static long getDateDiff(String dateType, Date date1)
    {

        Date date2 = Calendar.getInstance().getTime();

        long duration = date1.getTime() - date2.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;

        if(dateType.equals("s"))	// 밀리초 단위
        {
            return duration;
        }

        if(dateType.equals("S"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/second) );
        }

        if(dateType.equals("m"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/minute) );
        }

        if(dateType.equals("H"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/hour) );
        }

        if(dateType.equals("d"))	// 초 단위
        {
            return ( (long) Math.ceil(duration/day) );
        }

        return 0;
    }

    /**
     * 날짜형식 변경, 시스템 기본
     * @param value
     * @param type  ex) yyyy.MM.dd
     * @param type1 ex) yyyy-MM-dd
     * @return
     */
    public static String getChangeDateFormat(String value, String type, String type1){
        String result = "";
        try {

            DateFormat tempFormat = new SimpleDateFormat(type);
            DateFormat tempFormat2 = new SimpleDateFormat(type1);

            String tempDate  = NTUtil.isEmpty(value) ? "": value;
            Date tempDt = tempFormat.parse(tempDate);
            result = tempFormat2.format(tempDt);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    ////DATE////////////////

    public static String getCookieByName(String cookieName, HttpServletRequest req)
    {
        String cookieValue = "";

        Cookie[] c = req.getCookies();

        for(int i=0;i<c.length;i++)
        {
            if((c[i].getName()).equals(cookieName))
            {
                cookieValue = c[i].getValue();
                break;
            }
        }

        return cookieValue;
    }

    public static String removeXSS(String data)
    {
        String value=data;
        value = value.replaceAll("(?i)((?!\\\\)\\\")[\\s]*javascript:[^>]*((?!\\\\)\\\")", "\"\"");
        value = value.replaceAll("(?i)((?!\\\\)')[\\s]*javascript:[^>]*((?!\\\\)')", "''");
        value = value.replaceAll("(?i)=[\\s]*javascript:([^>]*)>", "=\"\">");

        //value = value.replaceAll("(?i)onmouseover\\s*=[\\\"\\\']([^\\\"\\\']*)[\\\"\\\']([^>]*)>", ">");
        value = value.replaceAll("(?i)onmouseover\\s*=","xxonmouseover=");
        value = value.replaceAll("(?i)onclick\\s*=","xxonclick=");
        value = value.replaceAll("(?i)onblur\\s*=","xxonblur=");
        value = value.replaceAll("(?i)onfocus\\s*=","xxonfocus=");
        value = value.replaceAll("(?i)onload\\s*=","xxonload=");

        value = value.replaceAll("(?i)<\\s*script([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*object([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*embed([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*applet([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*form([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*iframe([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*frame([^>]*)>", "");


        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("(?i)eval\\((.*)\\)", "");


        return value;
    }
    public static String removeXSSSimple(String data)
    {
        String value=data;
        value = value.replaceAll("(?i)((?!\\\\)\\\")[\\s]*javascript:[^>]*((?!\\\\)\\\")", "\"\"");
        value = value.replaceAll("(?i)((?!\\\\)')[\\s]*javascript:[^>]*((?!\\\\)')", "''");
        value = value.replaceAll("(?i)=[\\s]*javascript:([^>]*)>", "=\"\">");

        //value = value.replaceAll("(?i)onmouseover\\s*=[\\\"\\\']([^\\\"\\\']*)[\\\"\\\']([^>]*)>", ">");
        value = value.replaceAll("(?i)onmouseover\\s*=","xxonmouseover=");
        value = value.replaceAll("(?i)onclick\\s*=","xxonclick=");
        value = value.replaceAll("(?i)onblur\\s*=","xxonblur=");
        value = value.replaceAll("(?i)onfocus\\s*=","xxonfocus=");
        value = value.replaceAll("(?i)onload\\s*=","xxonload=");

        value = value.replaceAll("(?i)<\\s*script([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*object([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*embed([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*applet([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*form([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*iframe([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*frame([^>]*)>", "");
        value = value.replaceAll("(?i)eval\\((.*)\\)", "");


        return value;
    }

    public static String removeXSSSimplePlay(String data)
    {
        String value=data;
        value = value.replaceAll("(?i)((?!\\\\)\\\")[\\s]*javascript:[^>]*((?!\\\\)\\\")", "\"\"");
        value = value.replaceAll("(?i)((?!\\\\)')[\\s]*javascript:[^>]*((?!\\\\)')", "''");
        value = value.replaceAll("(?i)=[\\s]*javascript:([^>]*)>", "=\"\">");

        //value = value.replaceAll("(?i)onmouseover\\s*=[\\\"\\\']([^\\\"\\\']*)[\\\"\\\']([^>]*)>", ">");
        value = value.replaceAll("(?i)onmouseover\\s*=","xxonmouseover=");
        value = value.replaceAll("(?i)onclick\\s*=","xxonclick=");
        value = value.replaceAll("(?i)onblur\\s*=","xxonblur=");
        value = value.replaceAll("(?i)onfocus\\s*=","xxonfocus=");
        value = value.replaceAll("(?i)onload\\s*=","xxonload=");

        value = value.replaceAll("(?i)<\\s*script([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*object([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*embed([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*applet([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*form([^>]*)>", "");
        value = value.replaceAll("(?i)<\\s*frame([^>]*)>", "");
        value = value.replaceAll("(?i)eval\\((.*)\\)", "");


        return value;
    }

    public static boolean isNumeric(String str){
        boolean result = Pattern.matches("^[0-9]*$", str);
        return result;
    }

    /**
     * request =>  Map
     * @param request
     * @return
     */
    public static Map getParamMap(HttpServletRequest request) {
        Map paramMap = new HashMap();
        Enumeration paramNames = request.getParameterNames();

        while(paramNames.hasMoreElements()) {
            String name	= paramNames.nextElement().toString();
            String value = request.getParameter(name);
            paramMap.put(name, value);
        }

        return paramMap;
    }


    public static void getMapLogger(Map paramMap){
        Iterator it = paramMap.keySet().iterator();
        System.out.println("===== START VIEW DATA ["+  NTUtil.getDateTime("yyyy-mm-dd hh:mm:ss a")+"] =====");
        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println(key + " / " + paramMap.get(key));
        }
        System.out.println("===== END VIEW DATA =====");

    }
    /*
     * 전화번호 중간자리 masking 처리 (walkerhill)
     * 2017 01 11 hjkim
     */
    public static String getMaskedPhoneNo(String phoneNo){
        String p = NTUtil.isNull(phoneNo, "");
        if(p.equals("")) return p;

        p = phoneNo;
        String pre = "";
        String post = p.substring(p.length()-4, p.length());
        if(p.substring(0, 1).equals("0")){
            p = p.substring(1, p.length());
            pre = "0";
        }
        p = p.substring(0, p.length()-4);

        if(p.length() > 5){
            pre += p.substring(0, p.length()-4);
            p = pre+"****"+post;
            return p;
        }else if(p.length()==5){
            if(!p.substring(0, 1).equals("2")){
                pre += p.substring(0, p.length()-3);
                p = pre+"***"+post;
                return p;
            }else{
                pre += p.substring(0, p.length()-4);
                p = pre+"****"+post;
                return p;
            }
        }else if(p.length()==4){
            pre += p.substring(0, p.length()-3);
            p = pre+"***"+post;
            return p;
        }
        return phoneNo;
    }

    /*
     * sjkim 보안관련 마스킹처리
     */
    public static String getMask(String value, String type)
    {
        value = NTUtil.isNull(value.trim(), "");
        int len = value.length();
        String pre = "";
        String center = "";
        String post = "";
        String maskVal = "";

        String[] email = null;
        if(!NTUtil.isEmpty(value) && value != "" && value != null)
        {
            switch(type){
                case "name":
                    if(Pattern.matches("^[a-zA-Z]*$", value.substring(0,1))){
                        if(len >= 3){
                            pre = value.substring(0, len-3);
                            center = value.substring(len-3,len-1);
                            post = value.substring(len-1, len);
                            maskVal = getMaskVal(center.length());
                            value = pre+maskVal+post;
                        }
                    } else if(Pattern.matches("^[가-힣]*$", value.substring(0,1))){
                        pre = value.substring(0, 1);
                        if(len == 2){
                            value = pre+"*";
                        } else if(len > 2){
                            center = value.substring(1,len-1);
                            post = value.substring(len-1, len);
                            maskVal = getMaskVal(center.length());
                            value = pre+maskVal+post;
                        }
                    } else{

                    }
                    break;
                case "phone":
                    value = NTUtil.isNull(value.trim(), "");
                    if(value.contains("-"))
                        value = value.replace("-", "");
                    else if(value.contains("."))
                        value = value.replace(".", "");
                    len = value.length();
                    if(len >= 10){
                        pre = value.substring(0,len-8);
                        //center = value.substring(5,9);
                        post = value.substring(len-4, len);
                        maskVal = getMaskVal(4);
                        value = pre+maskVal+post;
                    } else {
                        value = "****";
                    }

                    break;
                case "email":
                    if(value.contains("@")){
                        email = value.split("@");
                        value = email[0];
                        if(value.length() >= 4){
                            pre = value.substring(0,3);
                            post = value.substring(3,value.length());
                            maskVal = getMaskVal(post.length());
                            value = pre+maskVal+"@"+email[1];
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        return value;
    }

    public static String getMaskVal(int length)
    {
        String value ="";
        for(int i=0;i<length;i++)
        {
            value += "*";
        }
        return value;
    }

    /*
    핸드폰번호 확인 함수 (아래는 테스트케이스 )
    "11044445555", "101044445555",
    "1044445555","01044445555","821044445555",
    "811044445555", "8201044445555", "8101044445555",
    "010-4444-5555", "10-4444-5555", "", null
     */
    public static String checkPhoneNo(String phoneNo){
        String result = "";
        try {
            if(phoneNo != null && phoneNo.length() > 0){
                String tPhoneNo = phoneNo.trim().replace("-", "");
                tPhoneNo = phoneNo.trim().replace(".", "");
                int phoneNoLen = tPhoneNo.length(); //전화번호 자릿수 11자리
                String tempSubPhone = tPhoneNo.substring(0, 1); //나라코드 82, 1, 등등

                if(phoneNoLen == 11){
                    if(!tempSubPhone.equals("0")){
                        result = "0"+tPhoneNo.substring(1, phoneNoLen);
                    } else {
                        result = tPhoneNo;
                    }
                } else {

                    if(phoneNoLen > 11){ //11자리보다 크면
                        if(!tempSubPhone.equals("0")){
                            if(phoneNoLen == 12){
                                result = "0"+tPhoneNo.substring(2, phoneNoLen);
                            } else if(phoneNoLen == 13){
                                result = "0"+tPhoneNo.substring(3, phoneNoLen);
                            } else {
                                if(phoneNoLen > 12){
                                    result = "0"+tPhoneNo.substring(1, 12);
                                }
                            }
                        } else {
                            result = tPhoneNo;
                        }
                    } else{ //11자리보다 작으면
                        result = "0"+tPhoneNo;
                    }
                }
            } else {
                result = phoneNo;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkPassword(String password) {
        return password.matches("^(?=.*[a-zA-Z])((?=.*\\d)(?=.*\\W)).{8,20}");
    }

    /**
     * Client IP 조회
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = NTUtil.isNull(request.getHeader("X-Forwarded-For"));    // AWS LB, Azure App Gateway 사용 시 헤더 문자열

        if (ip != null && ip.contains(",")) {
            // 첫 번째 IP가 클라이언트의 실제 IP
            ip = ip.split(",")[0].trim();
        }

        if (NTUtil.isEmpty(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (NTUtil.isEmpty(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (NTUtil.isEmpty(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (NTUtil.isEmpty(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (NTUtil.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String cleanXSS(String value) {
        String filstr = "<script>, %3Cscript, %3Ealert, ja%0Av%0Aa%0As%0Ac%0Aript, JaVaScRiPt, ScRiPt%20%0a%0d, JaVaScRiPt, ScRiPt%20%0a%0d, javascript, vbscript, expression,applet, ";
        filstr += "meta, xml, blink, link, style, script, embed, object, iframe, frame, frameset, ilayer, layer, bgsound, title, base, eval, innerHTML, charset, document, string, ";
        filstr += "create, append, binding, alert, msgbox, refresh, embed, ilayer, applet, cookie, void, href, nabort, @import, \\+ADw, \\+AD4, aim:, %0da=eval, allowscriptaccess, ";
        filstr += "xmlns:html, <html xmlns, xmlns:html=, http-equiv=refresh, http-equiv=refresh, x-scriptlet, echo\\(, 0%0d%0a%00, moz-binding, res://, #exec, background=, &#x,";
        filstr += "%u0, string.fromcharcode, firefoxurl, <br size=, list-style-image, acunetix_wvs, wvs-xss, lowsrc, dynsrc, behavior, activexobject, microsoft.xmlhttp, ";
        filstr += "clsid:cafeefac-dec7-0000-0000-abcdeffedcba, application/npruntime-scriptable-plugin;deploymenttoolkit, onactivae, onafterprint, onafterupdate, onbefore, ";
        filstr += "onbeforeactivate, onbeforecopy, onbeforecut, onbeforedeactivate, onbeforeeditfocus, onbeforepaste, onbeforeprint, onbeforeunload, onbeforeupdate, onblur,  ";
        filstr += "onbounce, oncellchange, onchange, onclick, oncontextmenu, oncontrolselect, oncopy, oncut, ondataavailable, ondatasetchanged, ondatasetcomplete, ondblclick, ";
        filstr += "ondeactivate, ondrag, ondragend, ondragenter, ondragleave, ondragover, ondragstart, ondrop, onerror,  onerrorupdate, onfilterchange, onfinish, onfocus, ";
        filstr += "onfocusin, onfocusout,onhelp, onkeydown, onkeypress, onkeyup, onlayoutcomplete, onload, onlosecapture, onmousedown, onmouseenter, onmouseleave, onmousemove, ";
        filstr += "onmouseout, onmouseover, onmouseup, onmousewheel, onmove, onmoveend, onmovestart, onpaste, onpropertychange, onreadystatechange, onreset, onresize, onresizeend, ";
        filstr += "onresizestart, onrowenter, onrowexit, onrowsdelete, onrowsinserted, onscroll, onselect, onselectionchange, onselectstart, onstart,onstop, onsubmit, onunload "; //필터링 할 문자열


        if (!filstr.equals("")) {
            filstr = filstr.replaceAll(" ","");
            String [] st = filstr.split(",");

            for( int x = 0; x < st.length; x++ ) {
                value = value.replaceAll(st[x], "");
            }
        }


        if(NTUtil.isEmpty(value)) return value;
        value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
        value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
        value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
//        value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
        value = value.replaceAll("<script>", "");
         value = value.replaceAll("</script>", "");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

        return value;
    }

    public static String removeHtml(String html) {
        if (isEmpty(html)) return isNull(html);

        return Jsoup.parse(html).text();
    }
}
