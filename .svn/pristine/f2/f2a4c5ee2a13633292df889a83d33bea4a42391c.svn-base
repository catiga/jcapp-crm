package com.jeancoder.crm.ready.util

class CoderConstants {
	public static final String _orde_type_common_ = "0";
	
	public static final Integer[] _checkcode_11_ = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 17] as Integer[];
	
	public static final Integer[] _checkcode_9_ = [7, 9, 10, 5, 8, 4, 6, 3, 17] as Integer[];
	
	public static final Integer[] _checkcode_15_ = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 17, 23, 37, 43, 59] as Integer[];
	
	public static final Integer[] _checkcode_17_ = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 17, 23, 37, 43, 59, 88, 99] as Integer[];
	
	public static Integer[] getModeArray(int length) {
		if(length==12) {
			return _checkcode_11_;
		}else if(length==16) {
			return _checkcode_15_;
		}else if(length==10){
			return _checkcode_9_;
		}else if(length==18){
			return _checkcode_17_;
		}else{
			throw new RuntimeException();
		}
	}
}
