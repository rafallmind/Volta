package com.example.voltagang.Model;


import com.example.voltagang.R;

public class Stamina {
	
	public static String comparerPerformance(double userPerf, double avgPerf, int feeling, double lastPerf, int lastFeeling) {
		int bilan = traiterPerfMoyenne(avgPerf, userPerf) +  traiterEvolutionPerf(lastPerf, userPerf) + traiterRessenti(feeling) + traiterEvolutionRessenti(lastFeeling, feeling);
		System.out.println(bilan);
		return lireTraitement(bilan);
		
	}
	
	
	public static int traiterPerfMoyenne(double avgPerf, double userPerf) {
		double comparaison;
		comparaison = avgPerf/userPerf;
		if (comparaison > 1.2)
			return 8 * 1000;
		if (comparaison > 1.1)
			return 6 * 1000;
		if (comparaison > 1)
			return 4 * 1000;
		if (comparaison > 0.8)
			return 2 * 1000;
		return 0;
	}
	
	public static int traiterRessenti(int ressenti) {
		return (ressenti-1)*10;
	}
	
	public static int traiterEvolutionPerf(double lastPerf, double userPerf) {
		double comparaison;
		comparaison = lastPerf/userPerf;
		if (comparaison > 1.2)
			return 8 * 100;
		if (comparaison > 1.1)
			return 6 * 100;
		if (comparaison > 1)
			return 4 * 100;
		if (comparaison > 0.8)
			return 2 * 100;
		return 0;
	}
	
	public static int traiterEvolutionRessenti(int dernierRessenti, int ressenti) {
		double comparaison;
		comparaison = ressenti/dernierRessenti;
		if (comparaison > 2.5)
			return 8;
		if (comparaison > 2)
			return 6;
		if (comparaison >= 1)
			return 4;
		if (comparaison > 0.5)
			return 2;
		return 0;
	}
	
	public static String lireTraitement(int res) {
		StringBuffer sb = new StringBuffer();
		int thousand = res/1000;
		int hundred = res/100 - thousand*10;
		int tens = res/10 - hundred*10 - thousand*100;
		int unit = res - tens*10 - hundred*100 - thousand*1000;
		switch(thousand) {
		case 0:
			sb.append(context.getResources().getString(R.string.avg1));
			break;
		case 2:
			sb.append(context.getResources().getString(R.string.avg2));
			break;
		case 4:
			sb.append(context.getResources().getString(R.string.avg3));
			break;
		case 6:
			sb.append(context.getResources().getString(R.string.avg4));
			break;
		case 8:
			sb.append(context.getResources().getString(R.string.avg5));
			break;
		}

		sb.append("\n");
		
		switch(hundred) {
		case 0:
			sb.append(context.getResources().getString(R.string.perf1));
			break;
		case 2:
			sb.append(context.getResources().getString(R.string.perf2));
			break;
		case 4:
			sb.append(context.getResources().getString(R.string.perf3));
			break;
		case 6:
			sb.append(context.getResources().getString(R.string.perf4));
			break;
		case 8:
			sb.append(context.getResources().getString(R.string.perf5));
			break;
		}
		
		sb.append("\n");
		
		switch(tens) {
		case 0:
			sb.append(context.getResources().getString(R.string.feel0));
			break;
		case 1:
			sb.append(context.getResources().getString(R.string.feel1));
			break;
		case 2:
			sb.append(context.getResources().getString(R.string.feel2));
			break;
		case 3:
			sb.append(context.getResources().getString(R.string.feel3));
			break;
		case 4:
			sb.append(context.getResources().getString(R.string.feel4));
			break;
		case 5:
			sb.append(context.getResources().getString(R.string.feel5));
			break;
		case 6:
			sb.append(context.getResources().getString(R.string.feel6));
			break;
		case 7:
			sb.append(context.getResources().getString(R.string.feel7));
			break;
		case 8:
			sb.append(context.getResources().getString(R.string.feel8));
			break;
		case 9:
			sb.append(context.getResources().getString(R.string.feel9));
			break;
		}
		
		sb.append("\n");
		
		switch(unit) {
		case 0:
			sb.append(context.getResources().getString(R.string.feelcmp1));
			break;
		case 2:
			sb.append(context.getResources().getString(R.string.feelcmp2));
			break;
		case 4:
			sb.append(context.getResources().getString(R.string.feelcmp3));
			break;
		case 6:
			sb.append(context.getResources().getString(R.string.feelcmp4));
			break;
		case 8:
			sb.append(context.getResources().getString(R.string.feelcmp5));
			break;
		}
		return sb.toString();
	}
}
