package cl.rhsso;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cl.rhsso.utils.CypherBCryptPass;

public class PasswordEncoderGenerator {

	public static void main(String[] args) {

//		int i = 0;
//		while (i < 1) {
//			String password = "asdqwe123";
//			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//			String hashedPassword = passwordEncoder.encode(password);
//
//			System.out.println(hashedPassword);
//			i++;
//		}
		
//		$2a$10$S2QSLTIG.HZFHWlG5DnMOuaquHo0KTICnOk.wxmbiJpyfDRFwaBo.
//		$2a$10$VWngjCjW2OvA35XvoUmPyO6qwkT.6tXFV8Um7ZD7boZdxqw9kesK2
//		$2a$10$5LsvtS6AdaoVYBGUvEuEuu7yJOg.jgwOjRsOxMigsuCwtZ3Q2tTuy
//		$2a$10$kSVqiJ3m3Ro8HtzoCpxXtuqWCFvw8/nOdr3tQtiFeAPzMNHt0ux8e
//		$2a$10$uNnluLnl3Pv/8H2COT6usO52xFn9GwBHZfqAty8HTzAwJ2V/Z1xb.
		
		
//		
/*		System.out.println("--------------------------------------------------------------------------");
		
		String test_passwd = "asdqwe123";
		String test_hash = "$2a$10$kSVqiJ3m3Ro8HtzoCpxXtuqWCFvw8/nOdr3tQtiFeAPzMNHt0ux8es";

		System.out.println("Testing BCrypt Password hashing and verification");
		System.out.println("Test password: " + test_passwd);
		System.out.println("Test stored hash: " + test_hash);
		System.out.println("Hashing test password...");
		System.out.println();

		String computed_hash = CypherBCryptPass.hashPassword(test_passwd);
		System.out.println("Test computed hash: " + computed_hash);
		System.out.println();
		System.out.println("Verifying that hash and stored hash both match for the test password...");
		System.out.println();

		String compare_test = CypherBCryptPass.checkPassword(test_passwd, test_hash)
			? "Passwords Match" : "Passwords do not match";

		System.out.println("Verify against stored hash:   " + compare_test);*/
	}
}

//$2a$10$2NxZelbr35Wuw5VaaN2IGOpJ4MJoqtTwmQyhnKtFxaQSygRia9PV.
//$2a$10$cp9y8jFMqjznYFnDBb3bmOeKZLhCwGxzCvGb1z1s1KiNUWrrSRvKS
//$2a$10$UPdvTNfpYKj5Zo5NewcCOuKpV7cu.asCksCHaU3M68.6YkAcljVla
//$2a$10$VHcusKhhcRa4DWUxYxmXqumBCpXQ.ElYRIiKm4ytA7yPtykIQzyPO
//$2a$10$PSKMGZKzAIIRVwevoaZHY.SBpLTNDW2yp7jz4gr/YTXye5bHXmd8O
//$2a$10$payc3zve2p/TRtRcXh8LaudPwl.gKqlKj5CMsQ510rQuLp0oX/EYi
//$2a$10$ZyjEUoE5Zs.zWRUVXQFjx.iFao.O6a0SQm3uJv.O7RK0qgVGERAL6
//$2a$10$JD9jl84XQN9I1164aXSjresQ3Z0DV0xV9ThvG7QrJlBxLW5mXrfG.
//$2a$10$5Mx/4qwbgxQc47FF8VVzzuo9NwjfMKgO5Gwnhx9o3bPuavezrmnTa
//$2a$10$F91Yryo8ImML9nQBjuBVHeI0RdnhOHLTMt.9L8NgiofL/phrLSdTS