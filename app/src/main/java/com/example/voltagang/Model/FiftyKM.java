package com.example.voltagang.Model;

public class FiftyKM {

	public final static double AVGMALE = 360;
	public final static double AVGFEMALE = 390;


	
	public static String comparerPerformance(Gender gender, double perf, int feeling, int lastFeeling, double lastPerf) {
		if (gender.equals(Gender.Male)) {
			return Stamina.comparerPerformance(perf, AVGMALE, feeling, lastPerf, lastFeeling);
		} else {
			return Stamina.comparerPerformance(perf, AVGFEMALE, feeling, lastPerf, lastFeeling);
		}
		
		
	}
	
	

}