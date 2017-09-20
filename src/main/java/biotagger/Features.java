package biotagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Features {

	public Features() {
		// TODO Auto-generated constructor stub
	}
	
	static boolean addpos = true;
	static boolean addbrown = true;
	static boolean addnohyphen = true;
	static boolean addpairedfeatures = true;
	static boolean addgazetteers = true;
	static boolean addvocabrestrictions = false;
	
	static HashMap<String, ArrayList<String> > hmap = new HashMap<String, ArrayList<String> >();
	static HashMap<String, String > brownmap = new HashMap<String, String>();
	
	static HashSet<String> dnaunary = new HashSet<String>();
	static HashSet<String> dnabinary = new HashSet<String>();
	static HashSet<String> rnaunary = new HashSet<String>();
	static HashSet<String> rnabinary = new HashSet<String>();
	static HashSet<String> celllineunary = new HashSet<String>();
	static HashSet<String> celllinebinary = new HashSet<String>();
	static HashSet<String> celltypeunary = new HashSet<String>();
	static HashSet<String> celltypebinary = new HashSet<String>();
	static HashSet<String> geneatlasunary = new HashSet<String>();
	static HashSet<String> geneatlasbinary = new HashSet<String>();
	static HashSet<String> proteinunary = new HashSet<String>();
	static HashSet<String> proteinbinary = new HashSet<String>();
	static HashSet<String> vocabulary = new HashSet<String>();
	
	static{
		try {
			
			String brownclustersdoc = "src/main/resources/sentences.50.paths";
			//String brownclustersdoc = "/Users/shashans/Documents/Courses/LSP/Assignments/Assign_4/brown-cluster-master/sentences-c50-p1.out/paths";
			if(addnohyphen)
				brownclustersdoc="src/main/resources/sentencesnohyphen.50.paths";   
				//brownclustersdoc="/Users/shashans/Documents/Courses/LSP/Assignments/Assign_4/brown-cluster-master/sentencesnohyphen-c20-p1.out/paths";
			BufferedReader br = new BufferedReader(new FileReader(brownclustersdoc));
			String line="";
			while ((line=br.readLine())!=null) {
				line = line.trim();
			    String[] tok = line.split("\\s+");
			    if(tok.length!=3)
			    	System.err.println("Incorrect number of tokens in brownclustersdoc.");
			    else
			    	brownmap.put(tok[1].toLowerCase(), tok[0]);
			}
			br.close();
			
			String vocabset = "src/main/resources/vocabset";
			br = new BufferedReader(new FileReader(vocabset));
			while ((line=br.readLine())!=null) {
				String[] tok = line.trim().split("\\s+");
				if(tok.length!=1)
					System.err.println("Incorrect number of tokens in vocabularyList.");
				else
					vocabulary.add(tok[0]);
			}
			br.close();
			
			if(addgazetteers){
				String dnaunigrams = "src/main/resources/DNA.unary.dict";
				br = new BufferedReader(new FileReader(dnaunigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=1)
						System.err.println("Incorrect number of tokens in dnaunigrams.");
					else
						dnaunary.add(tok[0]);
				}
				br.close();
				String dnabigrams = "src/main/resources/DNA.binary.dict";
				br = new BufferedReader(new FileReader(dnabigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=2)
						System.err.println("Incorrect number of tokens in dnabigrams.");
					else
						dnabinary.add(tok[0]+" "+tok[1]);
				}
				br.close();
				
				String rnaunigrams = "src/main/resources/RNA.unary.dict";
				br = new BufferedReader(new FileReader(rnaunigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=1)
						System.err.println("Incorrect number of tokens in rnaunigrams.");
					else
						rnaunary.add(tok[0]);
				}
				br.close();
				String rnabigrams = "src/main/resources/RNA.binary.dict";
				br = new BufferedReader(new FileReader(rnabigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=2)
						System.err.println("Incorrect number of tokens in rnabigrams.");
					else
						rnabinary.add(tok[0]+" "+tok[1]);
				}
				br.close();
				
				String celllineunigrams = "src/main/resources/cell_line.unary.dict";
				br = new BufferedReader(new FileReader(celllineunigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=1)
						System.err.println("Incorrect number of tokens in celllineunigrams.");
					else
						celllineunary.add(tok[0]);
				}
				br.close();
				String celllinebigrams = "src/main/resources/cell_line.binary.dict";
				br = new BufferedReader(new FileReader(celllinebigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=2)
						System.err.println("Incorrect number of tokens in celllinebigrams.");
					else
						celllinebinary.add(tok[0]+" "+tok[1]);
				}
				br.close();
				
				String celltypeunigrams = "src/main/resources/cell_type.unary.dict";
				br = new BufferedReader(new FileReader(celltypeunigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=1)
						System.err.println("Incorrect number of tokens in celltypeunigrams.");
					else
						celltypeunary.add(tok[0]);
				}
				br.close();
				String celltypebigrams = "src/main/resources/cell_type.binary.dict";
				br = new BufferedReader(new FileReader(celltypebigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=2)
						System.err.println("Incorrect number of tokens in celltypebigrams.");
					else
						celltypebinary.add(tok[0]+" "+tok[1]);
				}
				br.close();
				
				String proteinunigrams = "src/main/resources/protein.unary.dict";
				br = new BufferedReader(new FileReader(proteinunigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=1)
						System.err.println("Incorrect number of tokens in proteinunigrams.");
					else
						proteinunary.add(tok[0]);
				}
				br.close();
				String proteinbigrams = "src/main/resources/protein.binary.dict";
				br = new BufferedReader(new FileReader(proteinbigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=2)
						System.err.println("Incorrect number of tokens in proteinbigrams.");
					else
						proteinbinary.add(tok[0]+" "+tok[1]);
				}
				br.close();
				
				String geneatlasunigrams = "src/main/resources/geneatlas.unigrams";
				br = new BufferedReader(new FileReader(geneatlasunigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=1)
						System.err.println("Incorrect number of tokens in geneatlasunigrams.");
					else
						geneatlasunary.add(tok[0]);
				}
				br.close();
				String geneatlasbigrams = "src/main/resources/geneatlas.bigrams";
				br = new BufferedReader(new FileReader(geneatlasbigrams));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length!=2)
						System.err.println("Incorrect number of tokens in geneatlasbigrams.");
					else
						geneatlasbinary.add(tok[0]+" "+tok[1]);
				}
				br.close();
				
				System.out.println("Gazetteers read.");

			}
			br.close();
		} catch (Exception e) {
			System.err.println(e+"Could not open gazetteer txt file.");
		}
	}
	
	public static ArrayList<String> generate( ArrayList<Token> sentence, int index, String state, String prevstate){
		
		ArrayList<String> featureVec = new ArrayList<String>();
		
		//Current Word
		String word_i= getWord(sentence, index, "Wi=", state);
		String lower_i= getLowercasedWord(sentence, index,"Oi=", state);
		if(!word_i.contains("NIL")){
			featureVec.add(word_i+":Ti="+state);
			featureVec.add(lower_i+":Ti="+state);
		}
		
		String pos_i= getPOS(sentence, index, "Pi=",state);
		if(addpos){
			featureVec.add(pos_i+":Ti="+state);
		}
		String shape_i=getShape(sentence, index, "Si=", state);
		featureVec.add(shape_i+":Ti="+state);
		
		//For tokens except start state, add features depending on previous state
		if(index>0){
			String word_iM=getWord(sentence, index-1, "Wi-1=",state);
			String lower_iM=getLowercasedWord(sentence, index-1, "Oi-1=", state);
			if(!word_iM.contains("NIL")){
				featureVec.add(word_iM+":Ti="+state);
				featureVec.add(lower_iM+":Ti="+state);
			}
			String pos_iM=getPOS(sentence, index-1, "Pi-1=", state);
			if(addpos){
				featureVec.add(pos_iM+":Ti="+state);
			}
			String shape_iM=  getShape(sentence, index-1, "Si-1=", state);
			featureVec.add(shape_iM+":Ti="+state);
			
			//16 features based on previous token
			if(!word_i.contains("NIL")){
				featureVec.add(word_i+":"+shape_iM+":Ti="+state);	
				featureVec.add(lower_i+":"+shape_iM+":Ti="+state);
				if(!word_iM.contains("NIL")){
					featureVec.add(word_i+":"+word_iM+":Ti="+state);
					featureVec.add(word_i+":"+lower_iM+":Ti="+state);
					featureVec.add(lower_i+":"+word_iM+":Ti="+state);
					featureVec.add(lower_i+":"+lower_iM+":Ti="+state);
				}
			}

			if(addpos){
				//String pos_iM=getPOS(sentence, index-1, "Pi-1=", state);
				if(!word_i.contains("NIL")){
					featureVec.add(lower_i+":"+pos_iM+":Ti="+state);
					featureVec.add(word_i+":"+pos_iM+":Ti="+state);
				}
				if(!word_iM.contains("NIL")){
					featureVec.add(pos_i+":"+word_iM+":Ti="+state);
					featureVec.add(pos_i+":"+lower_iM+":Ti="+state);
				}
				featureVec.add(pos_i+":"+pos_iM+":Ti="+state);
				featureVec.add(pos_i+":"+shape_iM+":Ti="+state);
				featureVec.add(shape_i+":"+pos_iM+":Ti="+state);
			}
			
			if(!word_iM.contains("NIL")){
				featureVec.add(shape_i+":"+word_iM+":Ti="+state);
				featureVec.add(shape_i+":"+lower_iM+":Ti="+state);
			}
			featureVec.add(shape_i+":"+shape_iM+":Ti="+state);
			
			//4 features based on previous token and previous state
			featureVec.add("Ti-1="+prevstate+":Ti="+state);
			if(!word_iM.contains("NIL")){
				featureVec.add(word_iM+":"+"Ti-1="+prevstate+":Ti="+state);
				featureVec.add(lower_iM+":"+"Ti-1="+prevstate+":Ti="+state);
			}
			if(addpos){
				featureVec.add(pos_iM+":"+"Ti-1="+prevstate+":Ti="+state);
			}
			featureVec.add(shape_iM+":"+"Ti-1="+prevstate+":Ti="+state);
		}
		
		//For tokens except stop state, add features depending on next state
		if(index<=sentence.size()-2){
			String word_iP=getWord(sentence, index+1, "Wi+1=", state);
			String lower_iP=getLowercasedWord(sentence, index+1, "Oi+1=",state);	
			if(!word_iP.contains("NIL")){
				featureVec.add(word_iP+":Ti="+state);
				featureVec.add(lower_iP+":Ti="+state);
			}
			String pos_iP=getPOS(sentence, index+1, "Pi+1=",state);
			if(addpos){
				featureVec.add(pos_iP+":Ti="+state);
			}
			String shape_iP=getShape(sentence, index+1, "Si+1=", state);
			featureVec.add(shape_iP+":Ti="+state);
			
			//16 features based on net token
			if(!word_i.contains("NIL")){
				featureVec.add(word_i+":"+shape_iP+":Ti="+state);	
				featureVec.add(lower_i+":"+shape_iP+":Ti="+state);
				if(!word_iP.contains("NIL")){
					featureVec.add(word_i+":"+word_iP+":Ti="+state);
					featureVec.add(word_i+":"+lower_iP+":Ti="+state);
					featureVec.add(lower_i+":"+word_iP+":Ti="+state);
					featureVec.add(lower_i+":"+lower_iP+":Ti="+state);
				}
			}

			if(addpos){
				if(!word_i.contains("NIL")){
					featureVec.add(lower_i+":"+pos_iP+":Ti="+state);
					featureVec.add(word_i+":"+pos_iP+":Ti="+state);
				}
				if(!word_iP.contains("NIL")){
					featureVec.add(pos_i+":"+word_iP+":Ti="+state);
					featureVec.add(pos_i+":"+lower_iP+":Ti="+state);
				}
				featureVec.add(pos_i+":"+pos_iP+":Ti="+state);
				featureVec.add(pos_i+":"+shape_iP+":Ti="+state);
				featureVec.add(shape_i+":"+pos_iP+":Ti="+state);
			}
			
			if(!word_iP.contains("NIL")){
				featureVec.add(shape_i+":"+word_iP+":Ti="+state);
				featureVec.add(shape_i+":"+lower_iP+":Ti="+state);
			}
			featureVec.add(shape_i+":"+shape_iP+":Ti="+state);
			
			//4 features based on next token and previous tag and current state
			if(!word_iP.contains("NIL")){
				featureVec.add(word_iP+":"+"Ti-1="+prevstate+":Ti="+state);
				featureVec.add(lower_iP+":"+"Ti-1="+prevstate+":Ti="+state);
			}
			if(addpos){
				featureVec.add(pos_iP+":"+"Ti-1="+prevstate+":Ti="+state);
			}
			featureVec.add(shape_iP+":"+"Ti-1="+prevstate+":Ti="+state);
		}
		
		//4 features based on current token, previous state and current state
		if(!word_i.contains("NIL")){
			featureVec.add(word_i+":"+"Ti-1="+prevstate+":Ti="+state);
			featureVec.add(lower_i+":"+"Ti-1="+prevstate+":Ti="+state);
		}
		if(addpos){
			featureVec.add(pos_i+":"+"Ti-1="+prevstate+":Ti="+state);
		}
		featureVec.add(shape_i+":"+"Ti-1="+prevstate+":Ti="+state);
		
		//8. Prefix features
		if(!(state.matches("<START>")||(state.matches("<STOP>")))){
			for(int i=1; i<=4;i++){
				if(word_i.length()-3>=i){
					featureVec.add("PREi="+word_i.substring(3, i+3)+":Ti="+ state);
				}
			}
		}
		else 
			featureVec.add("PREi="+state+":Ti="+ state);
		
		//9. Suffix features
		if(!(state.matches("<START>")||(state.matches("<STOP>")))){
			for(int i=1; i<=4;i++){
				if(word_i.length()-3>=i){
					featureVec.add("SFXi="+word_i.substring(word_i.length()-i,word_i.length())+":Ti="+ state);
				}
			}
		}
		
		//Found in gazetteer in same role?
		/*////*/
		{
			if(addbrown){
				String w = word_i.substring(3).toLowerCase();
				if(brownmap.containsKey(w)){
					//System.out.println("Found "+word_i.substring(3)+" in brownmap, with clusterid " + brownmap.get(w));
					featureVec.add("Brown_cluster="+brownmap.get(w)+":Ti="+state);
					if(addpairedfeatures && index>0){
						String word_iM=getWord(sentence, index-1, "Wi-1=",state);
						String w_m = word_iM.substring(5).toLowerCase();
						if(brownmap.containsKey(w_m))
							featureVec.add("Brown_cluster="+brownmap.get(w)+":Ti="+state+":Brown_cluster="+brownmap.get(w_m)+":Ti-1="+prevstate);
					}
				}
			}
			
			if(addgazetteers){
				String w = word_i.substring(3).toLowerCase();
				String w_m = getWord(sentence, index-1, "Wi-1=",state).substring(5).toLowerCase();
				
				if(dnaunary.contains(w)){ 
					featureVec.add("Gaz:DNAunary"+":Ti="+state);
					//System.out.println("DNAunary:"+w);
				}
				if(rnaunary.contains(w)){ 
					featureVec.add("Gaz:RNAunary"+":Ti="+state);
					//System.out.println("RNAunary:"+w);
				}
				if(celllineunary.contains(w)){ 
					featureVec.add("Gaz:celllineunary"+":Ti="+state);
					//System.out.println("CELLLINEunary:"+w);
				}
				if(celltypeunary.contains(w)){
					featureVec.add("Gaz:celltypeunary"+":Ti="+state);
					//System.out.println("CELLTYPEunary:"+w);
				}
				if(proteinunary.contains(w)){ 
					featureVec.add("Gaz:proteinunary"+":Ti="+state);
					//System.out.println("PROTEINunary:"+w);
				}
			
				if(geneatlasunary.contains(w)){ 
					featureVec.add("Gaz:geneatlasunary"+":Ti="+state);
					//System.out.println("GENETLASunary:"+w);
				}
				/**/
				
				if(index>0){
					
					if(dnabinary.contains(w_m+" "+w)){ 
						featureVec.add("Gaz:dnabinary"+":Ti="+state);
						featureVec.add("Gaz:dnabinary"+":Ti="+state+":Ti-1="+prevstate);
						//System.out.println("Found in DNAbinary:"+w_m+" "+w);
					}
					if(rnabinary.contains(w_m+" "+w)){
						featureVec.add("Gaz:rnabinary"+":Ti="+state);
						featureVec.add("Gaz:rnabinary"+":Ti="+state+":Ti-1="+prevstate);
						//System.out.println("Found in RNAbinary:"+w_m+" "+w);
					}
					if(celllinebinary.contains(w_m+" "+w)){
						featureVec.add("Gaz:celllinebinary"+":Ti="+state);
						featureVec.add("Gaz:celllinebinary"+":Ti="+state+":Ti-1="+prevstate);
						//System.out.println("Found in Celllinebinary:"+w_m+" "+w);
					}
					if(celltypebinary.contains(w_m+" "+w)){ 
						featureVec.add("Gaz:celltypebinary"+":Ti="+state);
						featureVec.add("Gaz:celltypebinary"+":Ti="+state+":Ti-1="+prevstate);
						//System.out.println("Found in Celltypebinary:"+w_m+" "+w);
					}
					if(proteinbinary.contains(w_m+" "+w)){ 
						featureVec.add("Gaz:proteinbinary"+":Ti="+state); 
						featureVec.add("Gaz:proteinbinary"+":Ti="+state+":Ti-1="+prevstate);
						//System.out.println("Found in Proteinbinary:"+w_m+" "+w);
					}
					
					if(geneatlasbinary.contains(w_m+" "+w)){ 
						featureVec.add("Gaz:geneatlasbinary"+":Ti="+state);
						featureVec.add("Gaz:geneatlasbinary"+":Ti="+state+":Ti-1="+prevstate);
						//System.out.println("Found in Geneatlasbinary:"+w_m+" "+w);	
					}/**/
					
				}
			}
			
		}
		
		//First char capital
		if((state.matches("<START>")||(state.matches("<STOP>"))))
			featureVec.add("CAPi=False"+":Ti="+state);
		else if(word_i.charAt(3)>='A' && word_i.charAt(3)<='Z')
			featureVec.add("CAPi=True"+":Ti="+state);
		else
			featureVec.add("CAPi=False"+":Ti="+state);		
		//Word position
		featureVec.add("POSi="+index+":Ti="+state);

		return featureVec;
	}

	private static String getShape(ArrayList<Token> sentence, int index, String symbol, String state) {
		
		if(index==0) return symbol+"<START>";
		if(index==sentence.size()-1) return symbol+"<STOP>";
		
		// Shape of string based on Lowercase, uppercase,Digits in it
		String word = sentence.get(index).getWord();
		String shapeOfString ="";
		for(int i=0;i<word.length();i++){
			if(Character.isUpperCase(word.charAt(i)))
				shapeOfString= shapeOfString+"A";
			else if(Character.isLowerCase(word.charAt(i)))
				shapeOfString= shapeOfString+"a";
			else if(Character.isDigit(word.charAt(i)))
				shapeOfString= shapeOfString+"d";
			else
				shapeOfString= shapeOfString+word.charAt(i);
		}
		return symbol+shapeOfString;
	}

	private static String getPOS(ArrayList<Token> sentence, int index, String symbol, String state) {
		if(index==0) return symbol+"<START>";
		if(index==sentence.size()-1) return symbol+"<STOP>";
		return symbol+sentence.get(index).getPos();
	}

	private static String getLowercasedWord(ArrayList<Token> sentence, int index, String symbol, String state) {
		if(index==0) return symbol+"<START>";
		if(index==sentence.size()-1) return symbol+"<STOP>";
		if(!addnohyphen || sentence.get(index).getWord().replace("-","").equals(""))
			return symbol+sentence.get(index).getWord().toLowerCase();
		else
			return symbol+sentence.get(index).getWord().replace("-","").toLowerCase();
	}

	private static String getWord(ArrayList<Token> sentence, int index, String symbol, String state) {
		if(index==0) return symbol+"<START>";
		if(index==sentence.size()-1) return symbol+"<STOP>";
		String str;
		if(!addvocabrestrictions){
			if(!addnohyphen || sentence.get(index).getWord().replace("-","").equals(""))
				str= symbol+sentence.get(index).getWord();
			else{
				str = symbol+sentence.get(index).getWord().replace("-","");
			}
		}else{
			if(symbol.length()==3)
				symbol="NIL";
			else
				symbol="NILLL";
			if(!addnohyphen || sentence.get(index).getWord().replace("-","").equals(""))
				str= symbol+sentence.get(index).getWord();
			else{
				str = symbol+sentence.get(index).getWord().replace("-","");
			}
		}
		return str;
	}
	
}
