package com.company.test.IotRestProject.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTUtil {
	@Value("${jwt_secret}")
	private String secret;
	public String generateToken(String serialNumber) {
		return JWT.create()
				.withSubject("Serial number")
				.withClaim("Serial number", serialNumber)
				.withIssuedAt(new Date())
				.withIssuer("Avanesyan")
				.sign(Algorithm.HMAC256(secret));
			
	}
	public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException{
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
		.withSubject("Serial number")
		.withIssuer("Avanesyan").build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt.getClaim("Serial number").asString();
	}

}
