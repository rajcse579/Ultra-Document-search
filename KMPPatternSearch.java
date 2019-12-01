package UltraDocumentSearch;


import java.util.ArrayList;

public class KMPPatternSearch {
    private ArrayList<Integer> foundAtIndex;
    public int no_of_matches;
    
    public KMPPatternSearch(String content,String pattern,boolean isCaseSensitive){
        KMPSearch(content,pattern,isCaseSensitive);
        no_of_matches = foundAtIndex.size();
        //System.out.println(foundAtIndex.size());
    }
    public KMPPatternSearch(){
    	
    }
    /*public static void main(String a[]){
        KMPPatternSearch obj = new KMPPatternSearch();
        obj.KMPSearch("saikumar is gsaiood boy","sai");
    }*/
    private void KMPSearch(String content,String pattern,boolean isCaseSensitive){
        foundAtIndex = new ArrayList<Integer>();
        if(isCaseSensitive==false){
        	pattern = pattern.toLowerCase();
        	content = content.toLowerCase();
        }
        int lps[]=preProcessPattern(pattern);
        int M = pattern.length();
        int N = content.length();
        int j = 0;  // index for pat[]
        int i = 0;  // index for content[]
        while (i < N){
            if (pattern.charAt(j) == content.charAt(i)){
                j++;
                i++;
            }
            if (j == M){
                //System.out.println("Found pattern "+"at index " + (i-j));
                foundAtIndex.add(i-j);
                j = lps[j-1];
            }
 
            // mismatch after j matches
            else if (i < N && pattern.charAt(j) != content.charAt(i)){
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j-1];
                else
                    i = i+1;
            }
        }
    }
    private int[] preProcessPattern(String pattern){
        // length of the previous longest prefix suffix
        int M=pattern.length();
        int lps[] = new int[M];
        int len = 0;
        int i = 1;
        lps[0] = 0;  // lps[0] is always 0
        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M){
            if (pattern.charAt(i) == pattern.charAt(len)){
                len++;
                lps[i] = len;
                i++;
            }
            else{  // (pat[i] != pat[len])
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar 
                // to search step.
                if (len != 0){
                    len = lps[len-1];
 
                    // Also, note that we do not increment
                    // i here
                }
                else  // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
        //for(i=0;i<M;i++)
            //System.out.println(lps[i]);
        return lps;
    }
}
