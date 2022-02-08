package com.varArgs;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DynamicVariables {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer,List<String>> map=new LinkedHashMap<>();
		map.put(1, Arrays.asList("a","b","c"));
		map.put(2, Arrays.asList("d","e","f"));
		map.put(3, Arrays.asList("g","h","i"));
		createArray(map,"Column1","Column2","Column3");
	}
	
	public static void createArray(Map<Integer,List<String>> map,String... strings ) {
		int a=strings.length;
		System.out.println(a);
		String [][] array= new String [map.size()+1][strings.length];
		for(int i=0;i<strings.length;i++) {
			array[0][i]=strings[i];
			
		}
		System.out.print(array[0][0]);
		System.out.print(array[0][1]);
		System.out.print(array[0][2]);
		
		int j=1;
		for(Map.Entry<Integer, List<String>> entry: map.entrySet()) {
			
			for(int i=0;i<strings.length;i++) {
				array[j][i]=entry.getValue().get(i);
			}
			j++;
			
		}
		System.out.println("\n");
		System.out.print(array[0][0]);
		System.out.print(array[0][1]);
		System.out.print(array[0][2]);
		System.out.println("\n");
		System.out.print(array[1][0]);
		System.out.print(array[1][1]);
		System.out.print(array[1][2]);
		System.out.println("\n");
		System.out.print(array[2][0]);
		System.out.print(array[2][1]);
		System.out.print(array[2][2]);
		System.out.println("\n");
		System.out.print(array[3][0]);
		System.out.print(array[3][1]);
		System.out.print(array[3][2]);
//		for (String s:strings) {
//			System.out.println(s);
//			System.out.println(s.length());
//			
//		}
		
	}

}
