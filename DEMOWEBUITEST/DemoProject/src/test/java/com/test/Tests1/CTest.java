package com.test.Tests1;

import java.util.UUID;

import com.automation.TestDataHandler;

public class CTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
			    UUID uniqueKey = UUID.randomUUID();
			    System.out.println (uniqueKey);
			    String [] s=uniqueKey.toString().split("-");
			    System.out.println(s[0]);
			
	}

}
