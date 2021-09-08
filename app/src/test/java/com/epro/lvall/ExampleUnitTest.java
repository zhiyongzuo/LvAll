package com.epro.lvall;

import android.graphics.Matrix;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testDivide() {
        System.out.println(0%2);//0
        System.out.println(1%2);//1
        System.out.println(2%2);//0
        System.out.println(3%2);//1
        System.out.println(4%2);//0
    }


    @Test
    public void hidePhoneNumber() {
        String phone = "jddlsfj18124878057";//jddlsfj181****8057
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        System.out.println(phone_s);

        System.out.println(RegexUtils.isZh("4"));//false
        System.out.println(RegexUtils.isZh("4.0"));//false
        System.out.println(RegexUtils.isZh("的肌肤4"));//false
        System.out.println(RegexUtils.isZh("的肌肤4.00"));//false
        System.out.println(RegexUtils.isZh("的肌肤"));//true
        System.out.println(RegexUtils.isZh("对应银币余额不足！"));//false
    }

    @Test
    public void testAddOneDay() {
        String s = TimeUtils.getString("2021-12-31 10:36:35", 1, TimeConstants.DAY);
        System.out.println(s);
    }

    public static boolean isS1GreaterThanS2(String s1, String s2) {
        if(TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)) {
            return false;
        }
        if(new BigDecimal(s1).subtract(new BigDecimal(s2)).doubleValue()>0) {
            return true;
        } else {
            return false;
        }
    }

    @Test
    public void testIsS1GreaterThanS2() {
        System.out.println(isS1GreaterThanS2("0.0", "0"));//false
        System.out.println(isS1GreaterThanS2("0", "0.0"));//false
        System.out.println(isS1GreaterThanS2("0", "0.2"));//false
        System.out.println(isS1GreaterThanS2("0", "-0.2"));//true
        System.out.println(isS1GreaterThanS2("0.1", "0.2"));//false
        System.out.println(isS1GreaterThanS2("0.2", "0.1"));//true
        System.out.println(isS1GreaterThanS2("2", "1"));//true
        System.out.println(isS1GreaterThanS2("1", "2"));//false
        System.out.println(isS1GreaterThanS2("11", "2"));//true

        String s = "我的手机号是18837112195，曾经用过18888888888，还用过18812345678";
        String regex = "1[35789]\\d{9}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);

        while (m.find()) { //一定需要先查找再调用group获取电话号码
            System.out.println(m.group());
        }
    }



    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) return "00";
        StringBuilder sb = new StringBuilder();
        String[] units = {":", ":", ""};
        int[] unitLen = {3600000, 60000, 1000};
        precision = Math.min(precision, 3);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                if (mode<10) {
                    sb.append("0" + mode).append(units[i]);
                } else {
                    sb.append(mode).append(units[i]);
                }
            } else {
                sb.append("00").append(units[i]);
            }
        }
        return sb.toString();
    }

    @Test
    public void testTimeConvert() {
        long s = com.blankj.utilcode.util.TimeUtils.getTimeSpanByNow(com.blankj.utilcode.util.TimeUtils.string2Date("2021-08-13 09:10:01"), TimeConstants.MSEC);
        System.out.println(s);

//        String dateString1 = com.blankj.utilcode.util.TimeUtils.millis2String(s, "yyyy-MM-dd HH:mm:ss");
        String dateString1 = com.blankj.utilcode.util. ConvertUtils.millis2FitTimeSpan(s, 5);
        System.out.println(dateString1);

        String dateString = millis2FitTimeSpan(s, 3);
        System.out.println(dateString);
    }

    @Test
    public void testLength() {
        System.out.println("10.00".length());
    }

    /**
     * drawable 转 base64
     */
    @Test
    public void File2base64() throws FileNotFoundException {
//        File file  = new File("C:\\Users\\zuo81\\Desktop\\compare\\gas.joystar.com_2.1.6_216.apk");
        File file  = new File("C:\\Users\\zuo81\\Desktop\\compare\\picFirst.png");
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = ConvertUtils.inputStream2Bytes(inputStream);

//        System.out.println(EncodeUtils.base64Encode2String(bytes));
        System.out.println(EncodeUtils.base64Encode2String("test".getBytes()));
        System.out.println(Arrays.toString(EncodeUtils.base64Encode("test".getBytes())));
    }

    /**
     * for循环比while耗时很多。。
     */
    @Test
    public void testWhileOrFor() {
//       System.out.println((4>>1));
        log2_while();//                 (Integer.MAX_VALUE)  //705ms
//        log2_for2();//                (Integer.MAX_VALUE)    //708ms
    }

    void log2_while(){
        for(int i=0;i<10;i++) {
            for(int j=0;j<100;j++) {
                int k = 0;
                while(k<100) {
                    System.out.println(k++);
                }
            }
        }
    }
    void log2_for2()
    {
        for(int i=0;i<10;i++) {
            for(int j=0;j<100;j++) {
                for(int k=0;k<100;k++) {
                    System.out.println(k);
                }
            }
        }
    }

    class Solution {
        public int lengthOfLongestSubstring(String s) {

            int longestNum = 0;
            boolean isNotRepeatStr;
            int betweenIAndJ;
            for (int i = 0; i < s.length(); i++) {
                longestNum = Math.max(longestNum, 1);
                isNotRepeatStr = true;
                for(int j=i+1; j<s.length(); j++) {
                    betweenIAndJ = i;
                    while (betweenIAndJ<j) {
                        if(s.charAt(betweenIAndJ)==s.charAt(j)) {
                            isNotRepeatStr = false;
                            break;
                        }
                        betweenIAndJ++;
                    }
                    if(isNotRepeatStr) {
                        longestNum = Math.max(longestNum, j - i + 1);
                    } else {
                        break;
                    }
                }
            }
            return longestNum;
        }
    }

    @Test
    public void test() {
        System.out.println(new Solution().lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    public void testSubString() {
        String s= "hello";
        System.out.println(s.substring(0, 4));//hell
        System.out.println(s.substring(0, 5));//hello
        System.out.println(s.substring(0, 6));//java.lang.StringIndexOutOfBoundsException: String index out of range: 6
    }


    @Test
    public void testStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("test");
        stringBuffer.append(" ");
        stringBuffer.append("test");
        stringBuffer.append("");
        stringBuffer.append("test");
        System.out.println(stringBuffer.toString());//test testtest
    }

    @Test
    public void testChar() {
        System.out.println("5".equals('5'));//false
        char c = 97;
        System.out.println(c);//a

        char cccchar = 0;
        System.out.println(cccchar + "test");

    }


    /**
     * Object的hashCode()默认是返回内存地址的，但是hashCode()可以重写，所以hashCode()不能代表内存地址的不同
     *
     * System.identityHashCode(Object)方法可以返回对象的内存地址,不管该对象的类是否重写了hashCode()方法。
     */
    @Test
    public void testMemoryAddress() {
        String s1 = "hello";
        String s2 = "world";
        String s3 = "helloworld";
        String s4 = s1+s2;
        System.out.println(s3==s4);         //false
        System.out.println(s3.hashCode());  //-1524582912
        System.out.println(s4.hashCode());  //-1524582912
        System.out.println(System.identityHashCode(s3));//254413710
        System.out.println(System.identityHashCode(s4));//1496724653
        System.out.println(s3);                         //helloworld
        System.out.println(s4);                         //helloworld

        Object object1 = new Object();
        Object object2 = new Object();
        System.out.println(object1);            //java.lang.Object@20fa23c1
        System.out.println(object1.hashCode()); //553264065
        System.out.println(object1.toString()); //java.lang.Object@20fa23c1
        System.out.println(object2);                //java.lang.Object@3581c5f3
        System.out.println(object2.hashCode());     //897697267
        System.out.println(object2.toString());     //java.lang.Object@3581c5f3

    }

    @Test
    public void testStringAt() {
//        System.out.println("123".charAt(1));
        System.out.println("123".charAt(0));
        char[] a = new char[3];
        System.out.println(a);
    }

    @Test
    public void testMultiple() {
        int i = 5;
        int j = i / 3;
        int k = (i / 3) * 2;
        int m = (int) (1.9 * 2);
        System.out.println("j=" + j + "   k=" + k + "   m=" + m);//j=1   k=2   m=3
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /**
     * 必须要项目编译无错误才能运行  or clean一下
     */
    @Test
    public void t() {
        //157 ASCII码十进制
//        System.out.print('I' + 'T');
//        System.out.print(Integer.valueOf("5.55")); // 类型转换exception

        //true
//        && 优先级高于 ||
        if ("tdy".equals("tdy")&&"6".equals("6")||"tdy".equals("tdy")&&"6".equals("")) {
            System.out.print("true");
        } else {
            System.out.print("false");
        }
    }


    @Test
    public void getSortedMap() {
        JSONObject object=new JSONObject();
        object.put("p_startdate","2018-07-21");
        object.put("p_enddate", "2019-07-21");
        object.put("p_yhh", "3079713220");
        object.put("ddd",444);
        object.put("aaa",111);
        object.put("p_eaddate",111);

//        System.out.print(JSONObject.toJSONString(object, SerializerFeature.SortField));

        Map<String,String> map = JSONObject.parseObject(JSONObject.toJSONString(object), Map.class);
        System.out.print(sortMap(map));
    }

    public static Map<String, String> sortMap(Map<String, String> map) {
        if(map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new MapComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    static class MapComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            // TODO Auto-generated method stub
            return str1.compareTo(str2);
        }

    }



    @Test
    public void testCalendar() {
//        formatDateDefault("2019-10-31");
        formatDateInChina("2019-10-31");
    }

    @Test
    public void testCalendar2() {
        formatDateDefault("2019-10-30");
    }

    public static void formatDateDefault(String s) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
        String[] datalist = s.split("-");
        int currentYear = Integer.valueOf(datalist[0]);
        int currentMonth = Integer.valueOf(datalist[1]);
        int currentDay = Integer.valueOf(datalist[2]);
        calendar.set(currentYear, currentMonth - 1, currentDay);

        System.out.println(calendar);

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * @param s
     * 和时区没关系
     */
    public static void formatDateInChina(String s) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.clear();

        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
        String[] datalist = s.split("-");
        int currentYear = Integer.valueOf(datalist[0]);
        int currentMonth = Integer.valueOf(datalist[1]);
        int currentDay = Integer.valueOf(datalist[2]);
        calendar.set(currentYear, currentMonth - 1, currentDay);

        System.out.println(calendar);

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void convert() {
        HttpResonses<Integer> httpResonses = new HttpResonses();
        httpResonses.setData(1);
        System.out.println(new Gson().toJson(httpResonses));

        HttpResonses<Object> httpResonses1 = new Gson().fromJson(new Gson().toJson(httpResonses), HttpResonses.class);
        System.out.print(httpResonses1.getData());
        System.out.println(new Gson().toJson(httpResonses1));
        System.out.println("    ");

        HttpResonsesSec httpResonsesSec = new Gson().fromJson(new Gson().toJson(httpResonses), HttpResonsesSec.class);
        System.out.println("new Gson().toJson(httpResonsesSec) " + new Gson().toJson(httpResonsesSec));
//
//        HttpResonsesSec httpResonsesSec1 = new Gson().fromJson(new Gson().toJson(httpResonses1), HttpResonsesSec.class);
//        System.out.println("new Gson().toJson(httpResonsesSec)1 " + new Gson().toJson(httpResonsesSec1));
    }

    @Test
    public void testStaticFunString() {
        String a = "hello";
        String b = "world";
        changeStr(a, b);
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    }

    private void changeStr(String a, String b) {
        a = "1";
        b = "2";
    }

    class AS {
        String a;
        String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

    @Test
    public void testStaticFunObject() {
        AS as = new AS();
        as.setA("hello");
        as.setB("world");
        changeAS(as);
        System.out.println("a=" + as.getA());
        System.out.println("b=" + as.getB());
    }

    private void changeAS(AS as) {
        as.setA("1");
        as.setB("2");
    }


    /**
     * 测试ArrayList遍历时增删，concurrentModificationException
     */
    @Test
    public void TestConcurrentList() {
        List<String> myList = new CopyOnWriteArrayList<>();
//        List<String> myList = new ArrayList<>();
        myList.add( "1");
        myList.add( "2");
        myList.add( "3");
        myList.add( "4");
        myList.add( "5");
        //不会报并发错误
//        for ( int i = 0; i < myList.size(); i++) {
//            String value = myList.get(i);
//            System. out.println( "List Value:" + value);
//            if (value.equals( "3")) {
//                myList.remove(i);
////                myList.remove(value);  // ok
////                i--; // 因为位置发生改变，所以必须修改i的位置
//            }
//        }

        //下面的会报并发错误
        for (String value : myList) {
            System. out.println( "List Value:" + value);
            if (value.equals( "3")) {
                myList.remove(value);  // ok
//                i--; // 因为位置发生改变，所以必须修改i的位置
            }
        }

        //下面的会报并发错误
//        Iterator<String> iterator = myList.iterator();
//        while(iterator.hasNext()){
//            String integer = iterator.next();
//            if(integer.equals("2"))
//                myList.remove(integer);
//        }

        System. out.println( "List Value:" + myList.toString());
    }

    /**
     * 测试只try不catch 报错情况
     */
    @Test
    public void testTryCatch() {
        try {
            int i = 0;
            throw new NullPointerException();
        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        finally {
            System.out.println("1");
        }
    }

    /**
     * NumberFormatException: Bad offset/length: offset=0 len=0 in.length=0
     */
    @Test
    public void testBigDecimal() {
//        String[] history = new String[] {"0", " 1"};
        BigDecimal bigDecimal = new BigDecimal("");
//        BigDecimal bigDecimal = new BigDecimal(history[history.length - 1].substring(0, history[history.length - 1].length() - 1));
        System.out.println(bigDecimal);
    }

    /**
     * java中没有int转数组方法，得自己写
     */
    @Test
    public void testSplit() {
        String str = "12";
    }

    @Test
    public void testClearRepeat() {
        int[] oldArray = new int[]{1, 2, 2, 3, 3, 3, 4};
        Arrays.asList(oldArray);

    }

    /**
     * 以前正常，现在报错了，不打印 2021.07.20
     */
    @Test
    public void testMD5() {
        //E8933D85A8BAD2B964E64B108E715C68
//        System.out.println(FileUtils.getFileMD5ToString("C:\\Users\\zuo81\\Desktop\\compare\\ranqiyidonggongdanxitong.apk"));
        //E8933D85A8BAD2B964E64B108E715C68
//        System.out.println(FileUtils.getFileMD5ToString("C:\\Users\\zuo81\\Desktop\\compare\\gas.joystar.com_2.1.6_216.apk"));
        System.out.println(FileUtils.getFileMD5ToString("E:\\d\\wcqsj.keystore.jks"));
        System.out.println(AppUtils.getAppSignatureMD5());
    }

    @Test
    public void testBit() {
        int testIntA = 1 | 1;
        int testIntB = 1 | 0;
        int testIntC = 0 & 1;
        System.out.println("testIntA=" + testIntA + "   testIntB=" + testIntB + "   testIntC=" + testIntC);
    }

    @Test
    public void testInputStream() {
        byte[] bytes = new byte[]{1,2,3,4,5,6,7,8,9,10};
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            System.out.println(inputStream.read());
            System.out.println("tttttttttttest");
            System.out.println(inputStream.read());
            System.out.println(inputStream.read());

            int[] ints = new int[]{0, 1};
            int a = ints.length;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 二进制、十进制转换
     * 2147483647=2^31-1            原码
     *
     *-2147483648                   原码
     *                              反码1
     *                              补码10000000000000000000000000000000
     *
     * -2147483647   =1-2^31     原码11111111111111111111111111111111
     *                           反码10000000000000000000000000000000
     *                           补码10000000000000000000000000000001
     *
     *                 -1        原码10000000000000000000000000000001
     *                           反码11111111111111111111111111111110
     *                           补码11111111111111111111111111111111
     *
     *                 -3              原码10000000000000000000000000000011
     *                                 反码11111111111111111111111111111100
     *                                 补码11111111111111111111111111111101
     */
    @Test
    public void binaryTest() {
        System.out.println(Integer.toBinaryString(3));
        System.out.println(Integer.MAX_VALUE + "=" + Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.MIN_VALUE + "=" +Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(Integer.toBinaryString(-3));//11111111111111111111111111111101  打印的是补码
        //0011 0010
        //062
        //50
        //0x32
        //2
        //字符2
        System.out.println(Integer.toBinaryString('3'));
    }


    /**
     *这里打印全是0，和Matrix构造方法调用native有关吧。。
     */
    @Test
    public void matrixtTest() {
        Matrix matrix = new Matrix();

        System.out.println(GsonUtils.toJson(getMatrixFloats(matrix)));
        matrix.preTranslate(2.0f, 3.0f);
//        matrix.postTranslate(2, 3);
//        matrix.postScale(2, 3);
        System.out.println(GsonUtils.toJson(getMatrixFloats(matrix)));
    }

    private float[] getMatrixFloats(Matrix matrix) {
        float[] floats = new float[9];
        matrix.getValues(floats);
        return floats;
    }

//    private float getMatrixFloats(Matrix matrix) {
//        float[] floats = new float[9];
//        matrix.getValues(floats);
//        return floats[Matrix.MSCALE_X];
//    }


    String[] testArray;
    @Test
    public void testArray() {
        System.out.println("---begin---");
        for (String s : testArray) {//java.lang.NullPointerException
            System.out.println(s);
        }
        System.out.println("---end---");
    }


    @Test
    public void testTrim() {
        String s = "    669";
        System.out.println(s.trim());
    }


    @Test
    public void testCompareTo() {
        System.out.println("3".compareTo("4"));
        System.out.println("3".compareTo("14"));
    }

}

