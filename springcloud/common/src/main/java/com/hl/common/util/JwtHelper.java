package com.hl.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

public class JwtHelper {

	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 *
	 * @return
	 */
	public static String createJWT(String user_name,  String user_id,
			String audience, String issuer, int  expDates, String base64Security) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
											.claim("user_name", user_name)
											.claim("user_id", user_id)
											.setIssuer(issuer)
											.setAudience(audience)
											.signWith(signatureAlgorithm, signingKey);
		// 添加Token过期时间，设置为当前日期之后3日的凌晨2点
		if (expDates >= 0) {
			Date currdate = DateUtil.parseDate(DateUtil.formatDate(now), "yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			ca.setTime(currdate);
			ca.add(Calendar.DATE, expDates);
			Date expTmp = ca.getTime();

			String expTmpStr = DateUtil.formatDate(expTmp);
			Date exp = DateUtil.parseDate(expTmpStr + " 02:00:00", "yyyy-MM-dd HH:mm:ss");

			builder.setExpiration(exp).setNotBefore(now);
		}

		// 生成JWT
		return builder.compact();
	}

	public static void main(String[] args) {
		System.out.println(JwtHelper.createJWT("小黄人", "c9c4756847584a999cfc91b8e2a78c89", "", "",7,"L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg=="));
//		System.out.println(JwtHelper.createJWT("小陈", "3c2856506c9948b1a6f46be5bdb99817", "", "",7,"L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg=="));
	}
}
