package com.ruoyi.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

	private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);


	/**
	 * 校验token是否正确
	 * 
	 * @param token
	 *            密钥
	 * @param secret
	 *            用户的密码
	 * @return
	 */
	public static boolean verify(String token, String username, String secret) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			DecodedJWT jwt = verifier.verify(token);
			Date exiprted = jwt.getExpiresAt();

			/**
			 * 过期时间不在这边判断，在缓存中有过期时间，每次请求缓存的时候可以刷新过期时间
			 */
			// 过期
			// if (exiprted.getTime() < System.currentTimeMillis()) {
			// return false;
			// }

			return true;
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * 
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名,10天后过期
	 * 
	 * @param username
	 *            用户名
	 * @param secret
	 *            用户的密码
	 * @return 加密的token
	 */
	public static String sign(String username, String secret) {
		try {
			Date date = new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60* 1000);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带username信息
			return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
