package com.xyzcorp.ecommerce.thhs.util;

import org.apache.commons.codec.binary.Base64;

import com.xyzcorp.ecommerce.thhs.helper.CSVFileHelper;
import com.xyzcorp.ecommerce.thhs.model.GeneratedToken;
import com.xyzcorp.ecommerce.thhs.model.User;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TokenUtil {

	static final String HEXES = "0123456789ABCDEF";
	private static String encryption = "AES";
	private static byte[] keyBytes = { 38, 74, -22, -83, -128, -105, 34, -31, -5, 35, -36, 120, 74, 91, -1, -44 };

	public static GeneratedToken createToken(User userDetails) throws UnsupportedEncodingException {
		GeneratedToken tokenDetails = new GeneratedToken();
		try {
			int expires = 24 * 60 * 60;
			CSVFileHelper csvhandler = new CSVFileHelper();
			String token = encrypt(userDetails, expires);
			csvhandler.addToken(userDetails.getUserName() + ":" + expires + ":" + token);
			tokenDetails.setToken(token);
			tokenDetails.setExpireTimestamp(expires);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenDetails;
	}

	public static String encrypt(User userDetails, int expires) throws Exception {
		String encryptedText = "";

		try {
			if (null != userDetails) {

				StringBuilder signatureBuilder = new StringBuilder();
				signatureBuilder.append(userDetails.getUserName().substring(0,2)).append(":");
				signatureBuilder.append(Math.random() * 98 + 1);
				String tokenString = signatureBuilder.toString();

				String text = new String(Base64.encodeBase64(tokenString.getBytes("UTF8")));
				SecretKeySpec key = new SecretKeySpec(keyBytes, encryption);
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byte[] encryptedBytes = cipher.doFinal(text.getBytes(Charset.forName("UTF-8")));
				encryptedText = byteToHex(encryptedBytes);
				encryptedText = encryptedText + "==";

			}
		} catch (Exception e) {
			throw new Exception();
		}

		return encryptedText;
	}

	public static String byteToHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
}
