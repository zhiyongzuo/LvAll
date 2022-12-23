package com.epro.lvall;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.epro.lvall.util.Base64Decoder;
import com.epro.lvall.util.Base64Encoder;
import com.epro.lvall.util.StrUtil;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.epro.lvall.util.Base64Encoder.DEFAULT_CHARSET;

/**
 * @author zzy
 * @date 2020/11/6
 */
public class TEestSHA256 {

    @Test
    public void testHex() {
        System.out.println("AC1915FFFFFFFFFFFFFFFF9E083132333435363738000004EE9E".length());//52
        System.out.println(GsonUtils.toJson(ConvertUtils.hexString2Bytes("AC1915FFFFFFFFFFFFFFFF9E083132333435363738000004EE9E")));//[-84,25,21,-1,-1,-1,-1,-1,-1,-1,-1,-98,8,49,50,51,52,53,54,55,56,0,0,4,-18,-98]
        System.out.println(ConvertUtils.hexString2Bytes("AC1915FFFFFFFFFFFFFFFF9E083132333435363738000004EE9E").length);//26

        System.out.println(GsonUtils.toJson("\u0000".getBytes()));//[0]
        System.out.println(GsonUtils.toJson(ConvertUtils.hexString2Bytes("AA12015D05000374191321280721DA6B010031")));//[-86,18,1,93,5,0,3,116,25,19,33,40,7,33,-38,107,1,0,49]
        System.out.println(GsonUtils.toJson(ConvertUtils.hexString2Bytes("BS+DEVICE")));//java.lang.IllegalArgumentException
        byte[] head = new byte[]{(byte) 0xAA};
        System.out.println(GsonUtils.toJson(head));//[-86]
        System.out.println(0xFF);//255
        System.out.println(0b00001111); //15
        System.out.println((byte) 0xA8);//-88
        System.out.println((byte) 0xa8);//-88
        System.out.println(GsonUtils.toJson(ConvertUtils.hexString2Bytes("a8")));//[-88]
        System.out.println(GsonUtils.toJson(ConvertUtils.hexString2Bytes("A8")));//[-88]
        System.out.println(GsonUtils.toJson(ConvertUtils.bytes2HexString(new byte[] { 0, (byte) 0xa8 })));//"00A8"
        System.out.println(GsonUtils.toJson(ConvertUtils.bytes2HexString(new byte[] { 0, -88 })));//"00A8"
        System.out.println(GsonUtils.toJson(ConvertUtils.hexString2Bytes("00A8")));//[0,-88]
        System.out.println(GsonUtils.toJson(hex2Int("A8".toUpperCase().toCharArray()[0])));//10
        System.out.println(GsonUtils.toJson(hex2Int("A8".toUpperCase().toCharArray()[0])<<4));//160
        System.out.println(GsonUtils.toJson(hex2Int("A8".toUpperCase().toCharArray()[1])));//8
    }

    public static byte[] hexString2Bytes(String hexString) {
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            int ai = hex2Int(hexBytes[i]) << 4;
            int bi = hex2Int(hexBytes[i + 1]);
            ret[i >> 1] = (byte) ( ai | bi);     //  10100000 | 00001000    10101000   128 + 32 + 8 = 168            反01010111 补01011000   64+16+8
        }
        return ret;
    }

    private static int hex2Int(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void test() {
        System.out.println(0xffffffffL); //4294967295
        System.out.println(0b00001111);//15
        System.out.println(0<<30);//0
        System.out.println(1<<30);//1073741824
        System.out.println(2<<30);//-2147483648

        //最大值 01****11 2^0 + 2^1 + ... + 2^30 = 2^31-1
        //最小值 10****00  01****11 10****00      = -2^31

//        234的二进制位：
//        11101010   128 + 64 + 32 + 8 + 2 = 234
        //00010101 反码
        //00010110 补码 2 + 4 + 16 = 22

        //184dc51b0a4983ba5b9bac4cb1ca48f54
//        String btAddress = "b4:0f:b3:56:53:43";
//        System.out.println(GsonUtils.toJson(btAddress.getBytes()));
//        System.out.println(UUID.nameUUIDFromBytes(btAddress.getBytes()));

        // 1
        System.out.println('1' - '0');
        //49
        System.out.println('a' - '0');
        //17
        System.out.println('A' - '0');
        //BF67314FF91F39952A267FB4BF3F9ED2B53F813CDCFBA610F9D8643038A17326
        System.out.println(EncryptUtils.encryptSHA256ToString("apiKeytest_api_keyeggspamfoobartimestamp1514736000000test_api_secret"));
        //[0,-88]
        System.out.println(GsonUtils.toJson(hexString2Bytes("00A8")));
        //
        System.out.println(0>>1);
        System.out.println(1>>1);
        System.out.println(2>>1);
        //00001010  <<4     10100000  2^5 + 2^7 = 32 + 128 = 160
        //160
        System.out.println(10<<4);
        System.out.println((char)168);
        System.out.println((byte)168);
    }

    @Test
    public void testAucAES() {
//        String dataAES      = "111111111111111111111111111111111";
        String dataAES      = "12345";
        String keyAES       = "11111111111111111111111111111111";
        byte[] encyptArr = EncryptUtils.encryptAES(ConvertUtils.hexString2Bytes(dataAES), ConvertUtils.hexString2Bytes(keyAES), "AES/ECB/PKCS5Padding", null);
        //393FBBBC2C774BE50A106A50393E623AC3790781D015BB854359587256581F6D
        String hexStr = ConvertUtils.bytes2HexString(encyptArr);
        System.out.println(hexStr);

//        hexStr = "K9jf37DOkY5OwyK7jWIIXTya33Z8VgFJ GhZMNpAdTJFpSHdQYZHWW0NrJJrW2d3NrCS3nDlspp1IsQn9wfMSyi972TcpbdcK6nJ67uWB1usojtyS vwrRnU P8XlyCueafTbH3WaMMZGHB16hhkDi2z4KP8rvPsjzGJ6F3X583iIGCgj2ycAHzzRjpkvFRiQ87ON1WKVNdTidoB1hLXgQfFBSeZBIYNHpXmzrPy Uqcm9r09G1z0zvd6gwP9miOdepZi1IIEJdFZaGR42JVA6twMg7JdOl7cwDbBfnXdts6/hD3an5o3C2/p FjbcXrvPZaHmCarSwgegeiz9d2KP2J95yhUgDxOG0Al/NWLh46XFYGRdvtDxDhO8 4pbH3KC3pure9SQc3buKRJNXff0Aa Rd8AQpmeE2Vrl3vLn3KN8wpPOfZSdI5fz iGqCyWdYoo5Eu7u3A7JJElti/IFdWQnJpqlMpwgLCpz3dazuvPplKiGMIJn/wVkgtne/o95KKfZrSruxxdvGjS4Me9OKlbHbVyvEQESQ5Kze53Q0n4ktcAJ1PZb 3KJFrxDlV5rYKCR1n8Qa380B1fIHo4zXiURKmKorDq2ruPciCh 30WQrEx5xMeDteRDdUaJ/gsuM1yigst UUupokSD2FPi049iudNHhBmhVuL4bNpbUh2j36Wb3bsyxuoeHvbBlmINhyig8DUWtVra9hjVeWD9sUVzfYd0KoRVDUEPe2S9zGMqJbykbuaTwikXvd//uBpiHU fGkWuwrD4gjNtvA93Qa2nmiBI2hWT6I0jvVpPtGQL8J baA9930BQF31wDTrmO2Dk0KIalk2F8uFYVkCO7Fld1 WJoc57vm7GsLvFCschRzklMVXfUqvT8EsBBS2iA13uGS4GTrD//4E2BfxSh9OL47NhIPf6ccQvVgvwveXS2Dn68pyE/SDYB5B3qD3ZQz8g5SkYB1paPU/8MpS5E9G96bOu0lMPL2e9JsaJTuxZXdfliaHOe75uxrC7xQ71chXU/vfDUqGBp  EWmrHhkr4S5MwepFo4/nS8O/AeGF0v6KhrSffP2sBbFiIoQ21mFMCy2TvDyvjReH7LT31ivI6FPmZvV15eQmj3QzgDuzz2JNOxr UuaGeQ74EHm";
//        keyAES = "sosqazwsxedcrfvtgbplmokijh123456";

        byte[] decryptArr = EncryptUtils.decryptAES(ConvertUtils.hexString2Bytes(hexStr), ConvertUtils.hexString2Bytes(keyAES), "AES/ECB/PKCS5Padding", null);
        System.out.println("111:" + ConvertUtils.bytes2HexString(decryptArr));
    }


    //    %2FnAiEcyVubrHPCbgJJigLg%3D%3D
    //    /nAiEcyVubrHPCbgJJigLg==
    private static final String KEY_ALGORITHM = "AES";
    private static String encodeKey = "thanks";
    @Test
    public void testAES() {

//        String encryptString = "/nAiEcyVubrHPCbgJJigLg==";
//        byte[] decryptArray = EncryptUtils.decryptAES(encryptString.getBytes(), encodeKey.getBytes(), KEY_ALGORITHM, null);
//        System.out.println(GsonUtils.toJson(decryptArray));


        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            //16
            System.out.println("cipher.getBlockSize():" + cipher.getBlockSize());
            AlgorithmParameterSpec params = new IvParameterSpec(encodeKey.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encodeKey.getBytes(), "AES"), params);
            //16
            System.out.println("cipher.getBlockSize():" + cipher.getBlockSize());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = getEncryptString("214213412");

        byte[] encyptArray = EncryptUtils.encryptAES(str.getBytes(), encodeKey.getBytes(), "AES/CBC/NoPadding", encodeKey.getBytes());
        System.out.println(GsonUtils.toJson(encyptArray));
//        //base64编码auc和hutool不一致，会导致加密结果不同
//        System.out.println(Base64Encoder.encode(encyptArray));

//        byte[] encyptArray = EncryptUtils.encryptAES2Base64(str.getBytes(), encodeKey.getBytes(), "AES/CBC/NoPadding", encodeKey.getBytes());
//        System.out.println(StrUtil.str(encyptArray, DEFAULT_CHARSET));
    }

    public static String getEncryptString(String psw) {
        try {
            String in = psw;
            while (in.length()%16 != 0){ in = in + "\u0000"; }
            System.out.println("in:" + in);
            //[98,97,105,115,104,101,110,103,49,50,51,0,0,0,0,0]  0:空字符
            System.out.println(GsonUtils.toJson(in.getBytes()));
            System.out.println("11:" + new String(in.getBytes()));
            return in;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Test
    public void enCrypt() {
        String in = "K9jf37DOkY5OwyK7jWIIXTya33Z8VgFJ GhZMNpAdTJFpSHdQYZHWW0NrJJrW2d3NrCS3nDlspp1IsQn9wfMSyi972TcpbdcK6nJ67uWB1usojtyS vwrRnU P8XlyCueafTbH3WaMMZGHB16hhkDi2z4KP8rvPsjzGJ6F3X583iIGCgj2ycAHzzRjpkvFRiQ87ON1WKVNdTidoB1hLXgQfFBSeZBIYNHpXmzrPy Uqcm9r09G1z0zvd6gwP9miOdepZi1IIEJdFZaGR42JVA6twMg7JdOl7cwDbBfnXdts6/hD3an5o3C2/p FjbcXrvPZaHmCarSwgegeiz9d2KP2J95yhUgDxOG0Al/NWLh46XFYGRdvtDxDhO8 4pbH3KC3pure9SQc3buKRJNXff0Aa Rd8AQpmeE2Vrl3vLn3KN8wpPOfZSdI5fz iGqCyWdYoo5Eu7u3A7JJElti/IFdWQnJpqlMpwgLCpz3dazuvPplKiGMIJn/wVkgtne/o95KKfZrSruxxdvGjS4Me9OKlbHbVyvEQESQ5Kze53Q0n4ktcAJ1PZb 3KJFrxDlV5rYKCR1n8Qa380B1fIHo4zXiURKmKorDq2ruPciCh 30WQrEx5xMeDteRDdUaJ/gsuM1yigst UUupokSD2FPi049iudNHhBmhVuL4bNpbUh2j36Wb3bsyxuoeHvbBlmINhyig8DUWtVra9hjVeWD9sUVzfYd0KoRVDUEPe2S9zGMqJbykbuaTwikXvd//uBpiHU fGkWuwrD4gjNtvA93Qa2nmiBI2hWT6I0jvVpPtGQL8J baA9930BQF31wDTrmO2Dk0KIalk2F8uFYVkCO7Fld1 WJoc57vm7GsLvFCschRzklMVXfUqvT8EsBBS2iA13uGS4GTrD//4E2BfxSh9OL47NhIPf6ccQvVgvwveXS2Dn68pyE/SDYB5B3qD3ZQz8g5SkYB1paPU/8MpS5E9G96bOu0lMPL2e9JsaJTuxZXdfliaHOe75uxrC7xQ71chXU/vfDUqGBp  EWmrHhkr4S5MwepFo4/nS8O/AeGF0v6KhrSffP2sBbFiIoQ21mFMCy2TvDyvjReH7LT31ivI6FPmZvV15eQmj3QzgDuzz2JNOxr UuaGeQ74EHm";
        in = in.replaceAll(" +", "+");
        //15
        System.out.println(in.length()%16);
        int count = 15;

        while (in.length()%16 != 0){ in = in + "\u0015"; }
        byte[] data = Base64.getDecoder().decode(in);
//        for(int i=0; i<15; i++) {
//            data.
//        }

        byte[] encyptArray = EncryptUtils.decryptAES(data, "sosqazwsxedcrfvtgbplmokijh123456".getBytes(), "AES/ECB/PKCS5Padding", null);
        System.out.println(new String(encyptArray));
    }
}
