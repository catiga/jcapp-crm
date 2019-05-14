package com.jeancoder.crm.ready.security

import java.security.Key;
import java.security.Security

import javax.crypto.Cipher;

class DESPlus {
	private static String strDefaultKey = "national";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 *
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 *
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 *
	 * @throws Exception
	 */
	public DESPlus() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 *
	 * @param strKey
	 *            指定的密钥
	 * @throws Exception
	 */
	public DESPlus(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 加密字节数组
	 *
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 *
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 解密字节数组
	 *
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 *
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 *
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> token_key = new LinkedHashMap<String, String>();
		token_key.put("988990804b2db12e8bd33010fbaf515e0fa69610a275e7fa5dc918c02263ca66cc683366b91ecb67f6890de02ad21e8b64f7cb2765ab5866", "guangchang");
		token_key.put("7ebba6459769df6c9735b99c06914e166186111177baca8a48a07a51fdd22a099b418dd85c5434869980cc2f2f96ccb8ffa103281e8c9963", "longnan");
		token_key.put("1e829dc52af36e5536d2f133b4a47c33fc92b8b82ea5a654f96713f88401af361c4b10a4fe5e54cace4fa8c09952b4772cdeb4fb9b117541", "lz97gjdyc");
		token_key.put("32cf970fc0761f796082b19d7f9fd3c1797fbce1c24e00eb505af2509f6333b45799950fbae3ccd96d6e68edb88a3763fd82d3a8cd77ce9e", "ahknxkyc");
		token_key.put("0900bfa5c8b2b9d5b0484537491e4f7c88f0cf9dba2d9e5b408c82981b4f2bb4ccd79227fef7a17560102b7f760d77ac8883e2cc326e01d9", "HNSDYGS");
		token_key.put("0900bfa5c8b2b9d5b0484537491e4f7c88f0cf9dba2d9e5b408c82981b4f2bb4ccd79227fef7a17560102b7f760d77ac8883e2cc326e01d9", "HNSDYGS");
		token_key.put("0900bfa5c8b2b9d5b0484537491e4f7c88f0cf9dba2d9e5b408c82981b4f2bb4ccd79227fef7a175d2fb1279b23b68f4bc200de72e4ca82a", "HNSDYGS");
		token_key.put("32aa045c0372f0901c24073774f75f47909ca4224521fb4076f0bda8bd54cf73731d3840bb431ed452d82918e0a7e9a8bfca47bdf86ed48c", "hlknxk");
		token_key.put("360c946942d5d0c4d48dfdd7daf4db1d258daea98cd3731b8c4744f04edec2a78cdefead5a1d8e3871033e7c7301ce972217f987b42691e3", "szwz");     //横岗万众
		token_key.put("98e83edfe94fd70ba77a25cc90692653c042dfc20ed8c254f79aeaf5acb5e6f708c7d52f87086db19563b1ab572243ca2c3433007f5f0755", "gh&%85@!"); //根河
		token_key.put("80e67e825e9fc9ee6a9d571bf6531843b55e37d11d91fc39e6925dd4b52755da660aeb3ad7ee7a8fbf845b033177dcae1c2720f0a8a867e3", "slrtl"); // 东旗
		token_key.put("de534dae7c04338c257bf118f2b521775e2de967640371e6fcf5f00026f77fc2ba14700630640e52bd70de26dbf92a15422429024d2d14e9", "szwznep"); //万众neo店
		token_key.put("ad29dec11ad6c849296b91514999c8ff73a1ef9ec002cd8f34426ea1706f6af378747ec5ae0a4c6c2c0956f2c0832b3f0eca3d4c1dce8758", "赣州");
		token_key.put("f50d68dbb0a5e0433c6defd9e20fa031fb2b089860dedf18ae6245a19c240aa9d02b4d205885f6e1ee3028d7c45750a3d7ff7bbf9e4cb5f7", "lxblgjyc"); //临沂保利
		
		try {
			// DESPlus des = new DESPlus();//默认密钥
			String test = "ceshw30000031" + "|" + new Date().getTime();
			DESPlus des = new DESPlus("m@#ac123qeqw");// 自定义密钥
//			System.out.println("加密前的字符：" + test);
			System.out.println("加密后的字符：" + des.encrypt(test));
			println des.encrypt(test).length();
			System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));
			String[]  sytr = test.split("\\|");
			println sytr;
//			for(String token : token_key.keySet()) {
//				String key = token_key.get(token);
//				try {
//					des = new DESPlus(key);
//					token = des.decrypt(token);
//					System.out.println(token + "---" + key + "---可以解出来");
//				} catch(Exception e) {
//					System.out.println(token + "---" + key + "---解完蛋了");
//				}
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}