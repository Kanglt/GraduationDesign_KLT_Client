package lyu.klt.frame.util;

import java.net.InetAddress;
import java.security.SecureRandom;

/**
 * 生成UUID
 * @author YangYang
 *
 */
public class UuidUtil {

    private static SecureRandom seederStatic = null;
    private static byte addr[] = null;
    private static String midValueStatic = null;
    private SecureRandom seeder = null;

    static {
        try {
            addr = InetAddress.getLocalHost().getAddress();
            StringBuffer buffer = new StringBuffer(8);
            buffer.append(toHex(toInt(addr), 8));
            midValueStatic = buffer.toString();
            seederStatic = new SecureRandom();
            seederStatic.nextInt();
        } catch (Exception ex) {
        }
    }

    public UuidUtil() {
        StringBuffer buffer = new StringBuffer(16);
        buffer.append(midValueStatic);
        buffer.append(toHex(System.identityHashCode(this), 8));
        seeder = new SecureRandom();
        seeder.nextInt();
    }



    /**
     * 获得32UUID
     * @return
     */
    public static String getKey() {
        StringBuffer uid = new StringBuffer(32);

        //get the system time
        long currentTimeMillis = System.currentTimeMillis();
        uid.append(toHex((int)(currentTimeMillis & -1L), 8));

        // get the internet address
        uid.append(midValueStatic);

        //get the random number
        uid.append(toHex(getRandom(), 8));

        return uid.toString();
    }
 
    private static String toHex(int value, int length) {
        char hexDigits[] = 
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 
          'E', 'F' };
        StringBuffer buffer = new StringBuffer(length);
        int shift = length - 1 << 2;
        for (int i = -1; ++i < length; ) {
            buffer.append(hexDigits[value >> shift & 0xf]);
            value <<= 4;
        }

        return buffer.toString();
    }
 
    private static int toInt(byte[] bytes) {
        int value = 0;
        for (int i = -1; ++i < bytes.length; ) {
            value <<= 8;
            value |= 0x00FF & bytes[i];
        }

        return value;
    }

    private static synchronized int getRandom() {
        return seederStatic.nextInt();
    }
    public static void main(String[] args) {
		System.out.println(UuidUtil.getKey());
	}
    public static int getWordName()   
    {  
        int num = (int) Math.round(Math.random()*90000+1);  
        return num;    
    } 
}
