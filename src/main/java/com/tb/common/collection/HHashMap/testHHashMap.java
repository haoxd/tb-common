package com.tb.common.collection.HHashMap;

public class testHHashMap {
	 public static void main(String[] args) {

	        HHashMap<Integer, Integer> map = new HHashMap<Integer, Integer>();
	        map.put(1, 90);
	        map.put(2, 95);
	        map.put(17, 85);
	    
	        System.out.println(map.get(1));
	        System.out.println(map.get(2));
	        System.out.println(map.get(17));
	        System.out.println(map.get(null));
	    }

}
