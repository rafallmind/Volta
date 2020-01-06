package com.example.voltagang.Model;


public class SemiMarathon {

	public final static double AVGMALE = 90;
	public final static double AVGFEMALE = 100;


	
	public static String comparerPerformance(Gender gender, double perf, int feeling, int lastFeeling, double lastPerf) {
		if (gender.equals(Gender.Male)) {
			return Stamina.comparerPerformance(perf, AVGMALE, feeling, lastPerf, lastFeeling);
		} else {
			return Stamina.comparerPerformance(perf, AVGFEMALE, feeling, lastPerf, lastFeeling);
		}
		
		
	}
	
	

}
