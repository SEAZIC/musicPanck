package gamePanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class BunnerWLoder {
	private File f;
	ArrayList<Bunner> bunners;
	public BunnerWLoder(String filename) {
		bunners = new ArrayList<Bunner>();
		f = new File(filename);
		if(f.exists()){

			InputStreamReader ISR;
			try {
				ISR = new InputStreamReader(new FileInputStream(f),Charset.forName("UTF-8"));

				BufferedReader br = new BufferedReader(ISR);

				String str;
				System.out.println("Input >> "+f.getName());
				try {
					while((str = br.readLine()) != null){
						//TextData.add(str);
						String[] Splitstring = str.split(",");
						if(Splitstring.length < 3){
						bunners.add(new Bunner(200, 40,null, Splitstring[0], Splitstring[1]));
						}else{
							System.out.println("22222");
							bunners.add(new Bunner(200, 40,null, Splitstring[0], Splitstring[1],Splitstring[2]));
						}
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
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public ArrayList<Bunner> getBunners() {
		return bunners;
	}
	public void write(){
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f));
		
		for(int cnt = 0;cnt < bunners.size();cnt++){
			String St = "";
			St += bunners.get(cnt).getMeta()+","+bunners.get(cnt).getMeta_A()[0]+"\n";
			bw.write(St);
			//bw.newLine();
		}
		bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
