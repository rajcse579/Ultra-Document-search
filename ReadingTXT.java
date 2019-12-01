package UltraDocumentSearch;

import java.io.*;

public class ReadingTXT {
    public int readTXT(String filePath,String keyWord[],boolean isCaseSensitive) throws FileNotFoundException, IOException{
        FileReader txtfile = null;
        BufferedReader br = null;
        StringBuilder sb = null;
        
        txtfile = new FileReader(filePath);
        br = new BufferedReader(txtfile);
        sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        String everything = sb.toString();
        br.close();
        txtfile.close();
        //System.out.println(everything);
        KMPPatternSearch kmp = new KMPPatternSearch();
        for(int i=0;i<keyWord.length;i++){
        	kmp = new KMPPatternSearch(everything,keyWord[i],isCaseSensitive);
        	if(kmp.no_of_matches==0)
        		return -2;//do not display file name and no_of_matches in resultpanel
        }
        //if(parsedText.contains(keyWord)) return 1;
        return (keyWord.length==1)?kmp.no_of_matches:-1;
    }
    /*public static void main(String a[]) throws FileNotFoundException,IOException{
        ReadingTXT t = new ReadingTXT();
        t.readTXT("C:\\Users\\hp\\Desktop\\Venu\\index.html","sai");
    }*/
}
