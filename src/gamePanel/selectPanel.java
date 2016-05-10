package gamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import BaseSystem.FrameSystem.FrameWork;
import BaseSystem.ImageTool.ImageSever;
import BaseSystem.PanelSystem.PanelWork;
import BaseSystem.maintools.MessagePack;
import midtimeconv.MIDIPlayer;
import midtimeconv.midsequence;

public class selectPanel extends JPanel implements PanelWork,KeyListener,DropTargetListener{
	private FrameWork parent;
	private ArrayList<Bunner> bunners;
	private int currentIndex;
	private int oldcurrentIndex;
	
	private ArrayList<Point> apppoint;
	
	private MIDIPlayer MP;
	
	private boolean KeyOnf;
	
	private BunnerWLoder BWL;
	
	private boolean RagFlag;
	
	private ImageSever IS;
	
	private Color currentColor;
	
	private MenuPanel menuPanel;
	
	public selectPanel(){
		setBackground(Color.darkGray);
		bunners = new ArrayList<Bunner>();
		IS = new ImageSever();
		IS.setdefault("Image");
		IS.createImage("santa","Graphics/hqdefault.jpg" );
		IS.createImage("turtolrab", "Graphics/decem10.jpg");
		IS.createImage("ghost", "Graphics/illust477.jpg");
		IS.createImage("hart", "Graphics/image.jpg");
		BWL = new BunnerWLoder("data/setting.txt");
		
		menuPanel = new MenuPanel();
		
		bunners = BWL.getBunners();
		Random rm = new Random();
		currentIndex = rm.nextInt(bunners.size());
		oldcurrentIndex = -1;
		
		KeyOnf = false;
		RagFlag = false;
		
		apppoint = new ArrayList<Point>();
		apppoint.add(new Point(50, 50));
		apppoint.add(new Point(50, 130));
		apppoint.add(new Point(50, 210));
		new DropTarget(this,this);
		currentColor = new Color(0,255,0);
		
		this.add(menuPanel);
//		chengeCurrent();
	}
	public selectPanel(JFrame pframe) {

		setBounds(0, 0, pframe.getWidth(), pframe.getHeight());

		menuPanel.setBounds(pframe.getWidth()/15, pframe.getHeight()/15, pframe.getWidth()*14/15,pframe.getHeight()*14/15);
		revalidate();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int a = currentIndex;
		a -= (apppoint.size()/2);
		if(a < 0)
			a = bunners.size()+a;
		for(int cnt = 0;cnt < apppoint.size();cnt++){
			int x = (int)apppoint.get(cnt).getX();
			int y = (int)apppoint.get(cnt).getY();
			bunners.get(a).show(g,x,y);
			g.setColor(currentColor);
			if(cnt==(apppoint.size()/2) && apppoint.size()>1){
				g.drawRect(x-2, y-2,bunners.get(cnt).getRect().width+4,bunners.get(cnt).getRect().height+4);
			}
			a++;
			if(a >= bunners.size()){
				a = 0;
			}
		}
		if(bunners.get(currentIndex).getMeta_A().length > 1){
			//System.out.println("a");
			g.drawImage(IS.getImage(bunners.get(currentIndex).getMeta_A()[1]), 300, 100, 300, 300, null);
		}
	}
	@Override
	public void  panelrun(){
		if(!KeyOnf)
		chengeCurrent();
		Random rm = new Random();
		currentColor = new Color(128+rm.nextInt(127),128+rm.nextInt(127),128+rm.nextInt(127));
		if(MP != null && MP.isFinish() && !RagFlag){
			currentIndex = rm.nextInt(bunners.size());
			System.out.println("Raondom currentID = "+currentIndex);
		}
		repaint();
		//System.out.println("1111");
	}
	@Override
	public void setParent(FrameWork parent) {
		if(parent instanceof JFrame){
			setBounds(0, 0, ((JFrame)parent).getWidth(), ((JFrame)parent).getHeight());
		}
		this.parent = parent;
		if(parent instanceof JFrame){
			JFrame p = (JFrame)parent;
			p.addKeyListener(this);
			p.requestFocusInWindow();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void keyPressed(KeyEvent e) {
		KeyOnf = true;
		//System.out.println("aaa");
		if(e.getKeyCode() == KeyEvent.VK_UP){
			System.out.println("UP");
			currentIndex--;
			if(currentIndex < 0){
				currentIndex = bunners.size()-1;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			currentIndex++;
			
			if(currentIndex >= bunners.size()){
				currentIndex = 0;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			movePanel();
		}
		repaint();
	}
	private void movePanel(){
		if(RagFlag){
			return;
		}else{
			RagFlag = true;
		}
		if(MP != null){
			MP.close();
			MP = null;
			}
		String S = "";
		System.out.println("Return currentID = "+currentIndex);
		if(bunners.get(currentIndex).getMeta_A().length > 0)
			S = bunners.get(currentIndex).getMeta_A()[0];
		File file = new File(S+bunners.get(currentIndex).getMeta()+".mid");
		if(file.exists()){
			//BWL.write();
		MessagePack AMP = new MessagePack();
		AMP.setMeta_A("Chiled_Change");
		String filename = file.getAbsolutePath();
		AMP.setPresent(new musicGames(filename, 300));
		this.parent.setmessage(AMP);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		KeyOnf =false;
	}
	private void chengeCurrent(){

		if(currentIndex != oldcurrentIndex){
			oldcurrentIndex = currentIndex;
			if(MP != null){
			MP.close();
			MP = null;
			}
			String S  ="";
			if(bunners.get(currentIndex).getMeta_A().length > 0)
				S = bunners.get(currentIndex).getMeta_A()[0];
			File file = new File(S+bunners.get(currentIndex).getMeta()+".mid");
			if(file.exists()){
				RagFlag = true;
				System.out.println("<<<<<<");
				MP = new MIDIPlayer(getSequence(file));
				MP.setLoop();
				MP.start();
				RagFlag = false;
				//BWL.write();
			}
			
		}
	}

	/**
	 * 情報ダイアログを表示するメソッドです．
	 * 
	 * @param message メッセージ
	 */
	public void showInformationMessageDialog(String message) {
		// 情報ダイアログの表示
		JOptionPane.showMessageDialog(this,new JLabel(message)
		,"Information...",JOptionPane.INFORMATION_MESSAGE);
		repaint();
	}

	/**
	 * 警告ダイアログを表示するメソッドです．
	 * 
	 * @param message 警告メッセージ
	 */
	public void showWarningMessageDialog(String message) {
		// 警告ダイアログの表示
		JOptionPane.showMessageDialog(this,new JLabel(message)
		,"Warning...",JOptionPane.WARNING_MESSAGE);
		repaint();
	}

	/**
	 * エラーダイアログを表示するメソッドです．
	 * 
	 * @param message エラーメッセージ
	 */
	public void showErrorMessageDialog(String message) {
		// エラーダイアログの表示
		JOptionPane.showMessageDialog(this,new JLabel(message)
		,"Error...",JOptionPane.ERROR_MESSAGE);
		repaint();
	}


	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		dtde.acceptDrag(DnDConstants.ACTION_COPY);
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			// 転送されたデータの取得
			Transferable tr=dtde.getTransferable();
			// 転送データがサポート可能なデータかどうか判定
			if(tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// ドロップ動作を受け入れる
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

				// 転送データからファイルのリストを抽出
				java.util.List myList=(java.util.List)tr.getTransferData(
						DataFlavor.javaFileListFlavor);
				// リスト要素が単一かどうか判定
				// 複数ファイルのドロップを受け入れるのであれば，判定不要
				if(myList.size()==1) {
					// リストからファイルの取り出し
					File myFile=(File)myList.get(0);
					// ファイルの絶対パス名を表示
					// 実際には，この１行を変更すると良い
					//*TO-DO*/
					if(dropItemChack(myFile.getAbsolutePath())){
						System.out.println(myFile.getName());
						String S = myFile.getName().substring(0, myFile.getName().length()-4);
						System.out.println(myFile.getParent()+"/"+S);
						bunners.add(new Bunner(200, 40, null,S,myFile.getParent()+"/"));
						repaint();
//						if(fileCheck(myFile)){
//							//button.setEnabled(true);
//						}
					}else{
						
					}
					//showInformationMessageDialog(myFile.getAbsolutePath());
					// ドロップ動作を正常終了
					dtde.getDropTargetContext().dropComplete(true);
				} else {
					// 「要素が多い」という警告を表示
					showWarningMessageDialog("Too much elements.");
					// ドロップ動作を異常終了
					dtde.getDropTargetContext().dropComplete(false);
				}
			} else {
				showWarningMessageDialog("Unsupported.");
				// ドロップ動作をはねつける
				dtde.rejectDrop();
			}
		} catch (IOException ioe) {
			showErrorMessageDialog("I/O exception.");
			// ドロップ動作をはねつける
			dtde.rejectDrop();
		} catch (UnsupportedFlavorException ufe) {
			showErrorMessageDialog("Unsupported");
			// ドロップ動作をはねつける
			dtde.rejectDrop();
		}

	}

	private boolean dropItemChack(String absolutePath) {
		// TODO Auto-generated method stub
		String[] rx = {".*"};
		for(String s:rx){
			if(absolutePath.matches(s)){
				return true;
			}
		}
		return false;
	}
	private Sequence getSequence(File midfile){
		Sequence midsc = null;
		try {
			midsc = MidiSystem.getSequence(midfile);
		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return midsc;
	}
}
