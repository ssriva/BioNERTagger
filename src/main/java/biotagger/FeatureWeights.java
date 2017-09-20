package biotagger;

import java.util.ArrayList;
import java.util.HashMap;

public class FeatureWeights {

	public FeatureWeights() {
		// TODO Auto-generated constructor stub
	}
	
	public static Boolean verbose = false;
	
	public static double stateScore(ArrayList<Token> sentence, int index, String state, String prevstate, HashMap<String,Double> weights){
		
		/*Build FeatureVector*/
		ArrayList<String> featureVec = new ArrayList<String>();
		featureVec.addAll(Features.generate(sentence, index, state, prevstate) );
		
		/*Calculate w.f(x,y,z) */
		double val = 0;
		for(String s:featureVec){
			if(weights.containsKey(s)){
				if(verbose)
					System.out.println(s); //+" Weight:"+hmap.get(s));
				val += weights.get(s);
			}
			else{
				if(verbose)
					System.out.println(s);
			}
		}
	
		return val;
	}
	
	public static ArrayList<String> stateFeatures(ArrayList<Token> sentence, int index, String state, String prevstate){
		return Features.generate(sentence, index, state, prevstate);
	}
	
}
