package server;

import java.math.BigDecimal;
import java.util.Arrays;

public class Changedegital {
	public static byte[] getCHKSUM(byte[] bt) {
		int sum = 0;
		for (int i = 1; i < bt.length; i++) {
			sum += bt[i];
		}
		String st = Long
				.toHexString(
						Long.parseLong(
								((Integer.valueOf(qufan(buwei(Integer.toBinaryString(Math.floorMod(sum, 65536)), 16)),
										2)
										+ 1) + ""),
								10));// 计算CHKSUM
		byte[] ascii = { (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x35, (byte) 0x36,
				(byte) 0x37, (byte) 0x38, (byte) 0x39, (byte) 0x41, (byte) 0x42, (byte) 0x43, (byte) 0x44, (byte) 0x45,
				(byte) 0x46 };
		byte[] chksum = new byte[5];
		for (int i = 0; i < st.length(); i++) {
			chksum[i] = ascii["0123456789abcdef".indexOf(st.charAt(i))];
		}
		chksum[chksum.length - 1] = (byte) 0x0D;
		byte[] res = Arrays.copyOf(bt, bt.length + chksum.length);
		System.arraycopy(chksum, 0, res, bt.length, chksum.length);
		return res;

	}

	public static String qufan(String st) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < st.length(); i++) {
			sb.append((st.charAt(i) == '0' ? 1 : 0));
		}
		return sb.toString();

	}
	/**
	 * 十六进制的字符串转换为byte数组
	 **/
	public static byte[] conver16HexToByte(String hex16Str) {
		char[] c = hex16Str.toCharArray();
		byte[] b = new byte[c.length / 2];
		for (int i = 0; i < b.length; i++) {
			int pos = i * 2;
			b[i] = (byte) ("0123456789ABCDEF".indexOf(c[pos]) << 4 | "0123456789ABCDEF".indexOf(c[pos + 1]));
		}
		return b;
	}

	/**
	 * byte数组转换为二进制字符串,每个字节以","隔开
	 **/
	public static String conver2HexStr(byte[] b) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			result.append(Long.toString(b[i] & 0xff, 2) + ",");
		}
		return result.toString().substring(0, result.length() - 1);
	}

	public static String buwei(String er, int n) {
		StringBuilder sb = new StringBuilder();
		if (er.length() < n) {
			int count = n - er.length();
			for (int i = 0; i < count; i++) {
				sb.append("0");
			}
		}
		sb.append(er);
		return sb.toString();

	}

	/**
	 * 16进制字符串转10进制数字（支持范围【0000~ffff】->【-32768~32767】）.<br>
	 * 例:hex=FF3D,precise=0.1返回为-19.5
	 * 
	 * @param hex
	 *            16进制字符串(长度4位)
	 * @param precise
	 *            数字精度
	 * @return 10进制数字
	 */
	public static Number hexToNumber(String hex, Double precise) {
		Integer ii = hexToInt(hex);
		if (precise.equals(1.0d)) {
			return ii;
		}
		return BigDecimal.valueOf(ii).multiply(BigDecimal.valueOf(precise)).doubleValue();
	}

	/**
	 * 16进制字符串转10进制整数.<br>
	 * 例:hex=0064->100、ff9b->65435、ff9c->-100、ffff->-1；
	 * 
	 * @param hex
	 *            16进制字符串(长度4位)
	 * @return 10进制整数
	 */
	public static Integer hexToInt(String hex) {
		String binaryString = "";
		String bs1 = appendZeroFirst(Integer.toBinaryString(Integer.valueOf(hex.substring(0, 2), 16)));
		String bs2 = appendZeroFirst(Integer.toBinaryString(Integer.valueOf(hex.substring(2, 4), 16)));
		binaryString = bs1 + bs2;
		if (binaryString.startsWith("1")) { // 二进制第一位为1，所以是负数
			// 1, 将第一位替换为0
			StringBuilder newBinaryString = new StringBuilder("0");
			// 2, 后15位取反得
			for (int i = 1; i < binaryString.length(); i++) {
				newBinaryString.append(binaryString.charAt(i) == '0' ? "1" : "0");
			}
			return (Integer.valueOf(newBinaryString.toString(), 2) + 1) * -1;
		} else {
			return Integer.valueOf(hex, 16) & 0xffff;
		}
	}

	private static String appendZeroFirst(String bs) {
		int bslen = bs.length();
		for (int i = 0; i < 8 - bslen; i++) {
			bs = "0" + bs;
		}
		return bs;
	}
	public static String intToHex(int n) {
		String[] hexArray = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		if (n < 0) {
			n += 256;
		}
		int d2 = n % 16;
		return hexArray[d2];
	}

	public static int fanandadd(int n, int bit) {
		StringBuilder sb = new StringBuilder();
		for (int i = bit - 1; i >= 0; i--) {
			sb.append(n >>> i & 1);
		}
		String er = sb.toString();
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < er.length(); i++) {
			sb1.append((er.charAt(i) == '0' ? 1 : 0));
		}
		String fan = sb1.toString();

		long s = Long.valueOf(fan);
		int sum = 0;
		int i = 0;
		while (s != 0) {
			sum = (int) (sum + s % 10 * (Math.pow(2, i)));
			s = s / 10;
			i++;
		}
		sum++;
		return sum;
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toLowerCase().toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}

	public static String str2Hex(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 这里的第二个参数16表示十六进制
			sb.append(Integer.toString(c, 16));
			// 或用toHexString方法直接转成16进制
			// sb.append(Integer.toHexString(c));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param ascii
	 * @return 十六进制
	 */
	public static String hex2Str(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}

	/**
	 * 十六进制字符串装十进制
	 * 
	 * @param hex
	 *            十六进制字符串
	 * @return 十进制数值
	 */
	public static int hexStringToAlgorism(String hex) {
		hex = hex.toUpperCase();
		int max = hex.length();
		int result = 0;
		for (int i = max; i > 0; i--) {
			char c = hex.charAt(i - 1);
			int algorism = 0;
			if (c >= '0' && c <= '9') {
				algorism = c - '0';
			} else {
				algorism = c - 55;
			}
			result += Math.pow(16, max - i) * algorism;
		}
		return result;
	}

	/**
	 * 十进制数据转换为十六进制字符串数
	 * 
	 * @param dec
	 * @return
	 */
	public static String decToHex(String dec) {
		int data = Integer.parseInt(dec, 10);
		return Integer.toString(data, 16);
	}

	public static boolean checkCHKSUM(String s) {
		int sum = 0;
		for (int i = 1; i < s.length() - 4; i++) {
			sum += s.charAt(i);
		}
		int module = Math.floorMod(sum, 65536);
		int fan = Changedegital.fanandadd(module, 16);
		return Changedegital.decToHex("" + fan).toUpperCase().equals(s.substring(s.length() - 4));
	}

}
