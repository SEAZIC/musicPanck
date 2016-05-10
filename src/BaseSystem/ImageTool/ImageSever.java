package BaseSystem.ImageTool;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageSever {
	private String defaultroot;
	private String FS = File.separator;
	private static HashMap<String,BufferedImage> SeverImage;
	private boolean overrite;
	public ImageSever() {
		// TODO Auto-generated constructor stub
		if(SeverImage == null)
			SeverImage =new HashMap<String,BufferedImage>();
		defaultroot ="img";
		overrite = false;
	}
	/**デフォルトルートの設定
	 * 
	 * */
	public void setdefault(String root){
		defaultroot = root;
	}
	/** イメージのロード
	 * @param settingname ファイルを呼び出すときのキー値
	 * @param FileName ファイルの名前 */
	public void createImage(String settingname,String FileName){
		if(!overrite && SeverImage.containsKey(settingname)){
			System.out.println("notoverrite!!");
			return;
		}else{
			System.out.println(""+overrite+" "+settingname+" "+SeverImage.containsKey(settingname));
		}
		System.out.println(getClass().getClassLoader().getResource(defaultroot+FS+FileName));
//		File file = new File(getClass().getClassLoader().getResource(defaultroot+FS+FileName).getPath());
		//		if(file.isFile())
		//			System.out.println("filecheckok");
		//		System.out.println(file.getPath());
		SeverImage.put(settingname, openImageFile(getClass().getClassLoader().getResource(defaultroot+FS+FileName)));

	}
	/** イメージのロード
	 * @param settingname ファイルを呼び出すときのキー値
	 * @param FileName ファイルのイメージ */
	public void createImage(String settingname,BufferedImage image){
		if(!overrite && SeverImage.containsKey(settingname)){
			return;
		}
		SeverImage.put(settingname, image);
	}

	/** イメージのロード
	 * @param 画像のファイル名 
	 * @return イメージファイル*/
	public BufferedImage openImageFile(File file) {

		BufferedImage i = null;
		Image img = new ImageIcon(file.getPath()).getImage();
		if(img.getWidth(null) > 0)
			i = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		else return null;
		Graphics g = i.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return i;
	}
	/** イメージのロード
	 * @param 画像のファイル名 
	 * @return イメージファイル*/
	public BufferedImage openImageFile(URL url) {

		BufferedImage i = null;
		if(url.getFile() == null)
			return null;
		Image img = new ImageIcon(url).getImage();
		if(img.getWidth(null) > 0)
			i = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		else return null;
		Graphics g = i.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return i;
	}
	public void saveImage(String Filename,BufferedImage image,String format){
		File outputfile = new File(defaultroot+FS+Filename+"."+format);
		int number = 1;
		while(true){
			if(outputfile.exists()){
				outputfile = new File(defaultroot+FS+Filename+number+"."+format);
			}else{
				try {
					outputfile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		if(outputfile.canWrite()){
			try{
				OutputStream out=new FileOutputStream(outputfile);//ファイルとアプリを繋ぐ
				ImageIO.write(image,format, out);//指定の形式で出力
				out.close();
			}catch(IOException e){
				//例外処理
			}
		}
	}

	public BufferedImage getImage(String S){
//		System.out.println(SeverImage.containsKey(S));
		return SeverImage.get(S);
	}
	public boolean isOverrite() {
		return overrite;
	}
	public void setOverrite(boolean overrite) {
		this.overrite = overrite;
	}
}
