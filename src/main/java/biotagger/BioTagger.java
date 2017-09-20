package biotagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class BioTagger {

	public static void main(String[] args) {

		if(args.length<2){
			System.err.println("Incorrect usage.");
			System.err.println("USAGE: exec inputFile weightfile outputfile");
			return;
		}
		String inputFile = args[0];
		
		String weightfile = args[1];
		HashMap<String, Double > hmap = new HashMap<String, Double >();
		{
			String line="";
			try {
				BufferedReader br = new BufferedReader(new FileReader(weightfile));
				while ((line=br.readLine())!=null) {
					String[] tok = line.trim().split("\\s+");
					if(tok.length>1)
						hmap.put(tok[0], Double.parseDouble(tok[1]));
				}
				br.close();
			} catch (Exception e) {
				System.err.println(line+"\n"+e);
			}
		}
		
		String outputFile = inputFile+".annotated";
		if(args.length>=3){
			outputFile = args[2];
		}
		
		{
			BufferedReader br;			
			try {
				System.out.println("Reading");
				br = new BufferedReader(new FileReader(inputFile));
				PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
				
				String line;
				ArrayList<Token> sentence = new ArrayList<Token>();
				sentence.add(new Token("<START>"));
				
				while ( (line=br.readLine()) != null ) {
					line = line.trim();
					String[] toks = line.split(" ");
					
					if(!line.equals("")){
						Token tok = new Token(toks[0],toks[1],toks[0],toks[0]);
						sentence.add(tok);
					}
					else if(line.equals("")){
						sentence.add(new Token("<STOP>"));						
						String[] optimalTags = Decoder.decode(sentence, hmap);
						for(int i=1;i<=sentence.size()-2;i++){
							Token t = sentence.get(i);
							writer.println(t.getWord()+"\t"+optimalTags[i]);
						}
						writer.println();
						
						sentence.clear();
						sentence.add(new Token("<START>"));
					}
					else
						System.out.println("Incorrect number of tokens : "+ line);
				}
				br.close();
				writer.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}

}
