/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UltraDocumentSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
/**
 *
 * @author saikumar-kabaaliraaa
 */
public class ReadingPDF {
    public int readPDF(String path,String keyWord[],boolean isCaseSensitive) throws FileNotFoundException, IOException, DataFormatException {
        File file = null;
        PDFParser parser = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        try{
            file = new File(path);
            parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            //pdfStripper.setStartPage(1);
            //pdfStripper.setEndPage(3);
            String parsedText = pdfStripper.getText(pdDoc);
            //System.out.println(parsedText);
            KMPPatternSearch kmp = new KMPPatternSearch();
            for(int i=0;i<keyWord.length;i++){
            	kmp = new KMPPatternSearch(parsedText,keyWord[i],isCaseSensitive);
            	if(kmp.no_of_matches==0)
            		return -2;//do not display file name and no_of_matches in resultpanel
            }
            //if(parsedText.contains(keyWord)) return 1;
            pdDoc.close();
            cosDoc.close();
            //return -1 means display file name but not no_of_matches in resultpanel
            return (keyWord.length==1)?kmp.no_of_matches:-1;
        }
        catch(IOException e){
            
        }
        finally{
            if(pdDoc!=null) pdDoc.close();
            if(cosDoc!=null) cosDoc.close();
        }
        return 0;
    }
    /*public static void main(String a[]) throws IOException, FileNotFoundException, DataFormatException{
        String path = "C:\\Users\\hp\\Desktop\\sai\\sai789\\Gate From Kanodia\\papers\\Computer Science and Engineering_Full Paper_2009.pdf";
        String key = "generators";
        System.out.println(readPDF(path,key));
    }*/
}
