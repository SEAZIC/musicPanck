package BaseSystem.TextEdit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TextReader {
	private String filename;
	private ArrayList<String> TextData;
	private boolean spacedelete;
	public TextReader() {
		TextData = new ArrayList<String>();
	}
	public TextReader(String fileName){
		super();
		this.filename = fileName;
		readFile(fileName);
	}
	public void readFile(String filename){
		System.out.println(filename);
		System.out.println(getClass().getClassLoader().getResource(filename).toString());
		try {
			InputStreamReader ISR = new InputStreamReader(getClass().getClassLoader().getResource(filename).openStream());
			BufferedReader br = new BufferedReader(ISR);
			
			String str;
			TextData = new ArrayList<String>();
			try {
				while((str = br.readLine()) != null){
//					if(spacedelete){
//						System.out.println("repsp");
//						str.replaceAll(" | ","");
//					}
					TextData.add(str);
					System.out.println(str);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ISR.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<String> getTextData() {
		return TextData;
	}
	public boolean isTextFound(){
		if(TextData.isEmpty() || TextData.size() == 0){
			return false;
		}
		return true;
	}
	public void setSpacedelete(boolean spacedelete) {
		this.spacedelete = spacedelete;
	}
}
