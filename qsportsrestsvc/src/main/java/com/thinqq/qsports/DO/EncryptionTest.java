package com.thinqq.qsports.DO;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.geronimo.mail.util.Base64Encoder;

public class EncryptionTest {
	public static void main(String[] args) {
		try {
		String strToEncrypt = "Binoy";
		
		String test = "9wAM4KX1HNKI4CW5oOUIjg==";
		byte[] key = "9wAM4KX1HNKI4CW5oOUIjg==".getBytes();
		System.out.println(key);
		Cipher cipher;
		
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		 String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
		System.out.println(encryptedString);
		//(new Base64Encoder()).en
		/*KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(128);
		System.out.println(Base64.encodeBase64String(gen.generateKey().getEncoded()));*/
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
