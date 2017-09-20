package biotagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class WeightLearner {

	public static void main(String[] args) {

		if(args.length<2){
			System.err.println("Incorrect usage.");
			System.err.println("USAGE: exec inputFile maxIters [initialWeightsFile --addvocabrestrictions --nogazetteer --nopairedfeatures --nonohyphen --nobrown --nopos]");
			return;
		}
		String inputFile = args[0];
		int maxIter = Integer.parseInt(args[1]);
		ArrayList<ArrayList<Token>> sentenceList = new ArrayList<ArrayList<Token> >();
		HashMap<Integer, String[]> hgold = new HashMap<Integer, String[]>();
		HashMap<String,Double> weights = new HashMap<String, Double>();
		HashMap<String,Double> cumulativeweights = new HashMap<String, Double>();
		Boolean slowtaper = false;
		
		//Initialize weight file if argument given
		if(args.length>=3){
			if(!args[2].contains("--")){
				String initializedWeights = args[2],line;
				BufferedReader br;
				try {
					Boolean valid= false;
					br = new BufferedReader(new FileReader(initializedWeights));
					while ((line=br.readLine())!=null) {
						String[] tok = line.trim().split("\\s+");
						if(tok.length==2){
							valid=true;
							weights.put(tok[0], Double.parseDouble(tok[1]));
							cumulativeweights.put(tok[0], 0.0);
						}
					}
					if(valid)
						System.out.println("Initialized weights from file "+initializedWeights);
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for(int k=2;k<args.length;k++){
				if(args[k].contains("--nogazetteer")){
					Features.addgazetteers=false;
					System.out.println("No gazetteer features");
				}
				if(args[k].contains("--nopairedfeatures")){
					Features.addpairedfeatures =false;
					System.out.println("No paired features");
				}	
				if(args[k].contains("--nonohyphen")){
					Features.addnohyphen =false;
					System.out.println("No removing hyphens");
				}
				if(args[k].contains("--nobrown")){
					Features.addbrown =false;
					System.out.println("No brown features");
				}
				if(args[k].contains("--nopos")){
					Features.addpos =false;
					System.out.println("No pos features");
				}
				if(args[k].contains("--addvocabrestrictions")){
					Features.addvocabrestrictions=true;
					System.out.println("Vocabulary restrictions added");
				}
				if(args[k].contains("slowtaper")){
					slowtaper=true;
					System.out.println("Learning with a slow taper!");
				}
			}
		}
		
		{
			BufferedReader br;	
			try {
				br = new BufferedReader(new FileReader(inputFile));				
				String line;
				ArrayList<Token> sentence = new ArrayList<Token>();
				sentence.add(new Token("<START>"));
				int count=0;
				
				while ( (line=br.readLine()) != null ) {
					line = line.trim();
					String[] toks = line.split("\\s+");    //Get individual tokens by splitting on spaces
					
					if(toks.length==3){
						Token tok = new Token(toks[0],toks[1],toks[0],toks[2]);
						sentence.add(tok);
					}
					else if(line.isEmpty()){
						sentence.add(new Token("<STOP>"));
						sentenceList.add(new ArrayList<Token>(sentence)); //Add copy of ArrayList to sentenceList 
						
						String[] actualTags = new String[sentence.size()];
						for(int i=0; i<sentence.size(); i++){
							actualTags[i] = sentence.get(i).getLabel();
						}
						hgold.put(count, actualTags);
						
						count++;
						sentence.clear();
						sentence.add(new Token("<START>"));
					}
					else
						System.out.println("Incorrect number of tokens : "+ line);
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		//int maxIter=20;
		ArrayList<Integer> shfl = new ArrayList<>();
		for(int k=0;k<sentenceList.size();k++){
			shfl.add(k);
		}
		
		double delta=1.0;
		
		for(int iter=1;iter<=maxIter;iter++){
			System.out.println("Iter no. "+ iter+ " ...");
			if(slowtaper)
				delta = 1.0/(iter+1.0);
			System.out.println("Learning rate: "+delta);
			Collections.shuffle(shfl);
			for(int i=0;i<sentenceList.size();i++){
				if(i%500==0)
					System.out.println("sentence num : "+ i);
				
				String[] ygold   = hgold.get(shfl.get(i));
				String[] yoptimum= Decoder.decode(sentenceList.get(shfl.get(i)),weights);
				HashMap<String, Double> featureMapGold = getFeatureMap(getFeatureList(sentenceList.get(shfl.get(i)), ygold ));
				HashMap<String, Double> featureMapOptimal = getFeatureMap(getFeatureList(sentenceList.get(shfl.get(i)), yoptimum ));
				HashSet<String> first = new HashSet<String>(featureMapGold.keySet());
				HashSet<String> second = new HashSet<String>(featureMapOptimal.keySet());
				first.addAll(second);
				
				for(String s:first){
					if(!featureMapGold.containsKey(s))
						featureMapGold.put(s, 0.0);
					if(!featureMapOptimal.containsKey(s))
						featureMapOptimal.put(s, 0.0);
					if(!weights.containsKey(s)){
						weights.put(s, 0.0);
						cumulativeweights.put(s, 0.0);
					}
					weights.put(s, weights.get(s) + delta*(featureMapGold.get(s) - featureMapOptimal.get(s)));
					cumulativeweights.put(s, cumulativeweights.get(s)+weights.get(s));
				}
			}
		
			/*Write weights files*/
			String outputFile = inputFile+".weights."+iter+".Iterations";
			try {
				PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
				for(String s:cumulativeweights.keySet()){
					writer.println(s+" "+cumulativeweights.get(s)/(maxIter)+" "+weights.get(s));
				}
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
					
	}

	private static ArrayList<String> getFeatureList(ArrayList<Token> sentence, String[] ylabel) {
		ArrayList<String> featureVec = new ArrayList<String>();
		for(int i=sentence.size()-1; i>=2; i--){
			featureVec.addAll(FeatureWeights.stateFeatures(sentence, i, ylabel[i], ylabel[i-1]));
		}
		featureVec.addAll(FeatureWeights.stateFeatures(sentence, 1, ylabel[1], "<START>"));
		return featureVec;
	}
	
	
	private static HashMap<String, Double> getFeatureMap( ArrayList<String> featureList) {
		HashMap<String, Double> hmap = new HashMap<String, Double>();
		for(int i=0;i<featureList.size();i++){
			if(!hmap.containsKey(featureList.get(i))){
				hmap.put(featureList.get(i), 1.0);
			}else{
				hmap.put(featureList.get(i), hmap.get(featureList.get(i))+1.0);
			}
		}
		return hmap;
	}

}
