package midtimeconv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class midiFileSet {

	String result;
	
	public midiFileSet(){

		result = "";
		
		String startFile = "Startfile.txt";
		File file = new File(startFile);
		 String str = null;
		 String str2 = null;
		 int setparam = 0;
		try{
		     // File file = new File("c:¥¥tmp¥¥test.txt");

		      if (checkBeforeReadfile(file)){
		        BufferedReader br = new BufferedReader(new FileReader(file));

		       
		        if((str = br.readLine()) != null){
		          System.out.println(str);
		        }else{
		         System.exit(-1);
		        }
		        if((str2 = br.readLine()) != null){
			          System.out.println(str2);
			        	try{
			       setparam = Integer.parseInt(str2);

	        		System.out.println("指定値が正しく読めました"+str2);
	        		result += "閾値OK";
			        	}catch(NumberFormatException e){
			        		System.out.println("指定値が正しく読めませんでした"+str2);
			        		setparam = 99999999;
			        		result += "閾値Bad";
			        	}
		        }else{
			      //System.exit(-1);

			        }

		        br.close();
		      }else{
		    	  result += "ノットファイル";
		        System.out.println("ファイルが見つからないか開けません");
		      }
		    }catch(FileNotFoundException e){
		      System.out.println(e);
		      return ;
		    }catch(IOException e){
		      System.out.println(e);
		    }
		
		file = new File(str);
		
		midsequence MS = new midsequence(file,setparam);
		file = new File(str.replace(".", ""));
		file.mkdir();
		File file2 = new File(str.replace(".", "")+File.separator+"key.txt");
		File file3 = new File(str.replace(".", "")+File.separator+"timing.txt");
		
		System.out.println("Save2_Key&timing");
		String St = null;
		try{
			//File file = new File(Filename);

			if (checkBeforeWritefile(file2)){
				BufferedWriter bw = new BufferedWriter(new FileWriter(file2));

				for(int cnt = 0;cnt < MS.getnoteonkey().size();cnt++){
					
					St = MS.getnoteonkey().get(cnt)+",";
					bw.write(St);
					//bw.newLine();
				}
				bw.close();
			}else{
				result += "\t" +
						"Ｄｏｎ’ｔＷｒｉｔｅｔｏKEYＦｉｌｅ";
				System.out.println("ファイルに書き込めません");
			}if (checkBeforeWritefile(file3)){
				BufferedWriter bw = new BufferedWriter(new FileWriter(file3));

				for(int cnt = 0;cnt < MS.getTickontiming().size();cnt++){
					
					St = MS.getTickontiming().get(cnt)+",";
					bw.write(St);
					//bw.newLine();
				}
				bw.close();
			}else{

				result += "\tＤｏｎ’ｔＷｒｉｔｅｔｏTIMEＦｉｌｅ";
				System.out.println("ファイルに書き込めません");
			}
		}catch(IOException e){
			System.out.println(e);
		}
		result += "--COMPLETED";
		
	}
	public midiFileSet(String S,String M){

		result = "";
		 String str = S;
		 int setparam = 0;
		
		File file = new File(str);
		if(checkBeforeReadfile(file)){
			
		}else{
		result = "BADFILE!!!";
			return;
		}
		try{
			setparam = Integer.parseInt(M);
		}catch(NumberFormatException e){
			System.out.print("しきい値が正しく読み込めませんでした¥n");
			setparam = -1;
		}
			if(setparam < 0)
				setparam  = 99999999;
		midsequence MS = new midsequence(file,setparam);
		file = new File(str.replace(".", ""));
		file.mkdir();
		File file2 = new File(str.replace(".", "")+File.separator+"key.txt");
		File file3 = new File(str.replace(".", "")+File.separator+"timing.txt");
		
		System.out.println("Save2_Key&timing");
		String St = null;
		try{
			//File file = new File(Filename);

			if (checkBeforeWritefile(file2)){
				BufferedWriter bw = new BufferedWriter(new FileWriter(file2));

				for(int cnt = 0;cnt < MS.getnoteonkey().size();cnt++){
					
					St = MS.getnoteonkey().get(cnt)+",";
					bw.write(St);
					//bw.newLine();
				}
				bw.close();
			}else{
				result += "\t" +
						"Ｄｏｎ’ｔＷｒｉｔｅｔｏKEYＦｉｌｅ";
				System.out.println("ファイルに書き込めません");
			}if (checkBeforeWritefile(file3)){
				BufferedWriter bw = new BufferedWriter(new FileWriter(file3));

				for(int cnt = 0;cnt < MS.getTickontiming().size();cnt++){
					
					St = MS.getTickontiming().get(cnt)+",";
					bw.write(St);
					//bw.newLine();
				}
				bw.close();
			}else{

				result += "\tＤｏｎ’ｔＷｒｉｔｅｔｏTIMEＦｉｌｅ";
				System.out.println("ファイルに書き込めません");
			}
		}catch(IOException e){
			System.out.println(e);
		}
		result += "--COMPLETED";
		
	}
	public String result(){
		return result;
	}
	private static boolean checkBeforeWritefile(File file){
		System.out.println("FileChak");
		try {
				file.createNewFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (file.exists()){
			if (file.isFile() && file.canWrite()){
				
				return true;
			}
		}
		System.out.println("FileChakfalse");
		return false;
	}
	  private static boolean checkBeforeReadfile(File file){
		    if (file.exists()){
		      if (file.isFile() && file.canRead()){
		        return true;
		      }
		    }

		    return false;
		  }
}
