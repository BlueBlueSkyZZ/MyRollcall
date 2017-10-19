package swing;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import util.CsvUtil;
import util.RollcallUtil;
import entity.Student;

public class MySwing extends JFrame implements ActionListener, ItemListener{

	int count;//������
	List<Student> students = new ArrayList<Student>();
	public MySwing() {
		super("һ��СС�ĵ������");
		setBounds(200, 200, 450, 350);
//		this.addWindowListener(new winEventHandle());//���ڼ���
	}

	public void init() {
		
		JFrame mainJFrame = this;
		
		//�˵���
		MenuBar myB = new MenuBar();// �����˵���
		setMenuBar(myB);
		Menu m1 = new Menu("����");// �����˵�����
		m1.add(new MenuItem("��ȡ����"));
		m1.add(new MenuItem("���ѡ��"));
		m1.add(new MenuItem("��������"));
//		MenuItem m11 = new MenuItem("����");// �����˵���
//		m11.setEnabled(false);// ����
//		m1.add(m11);// ��ӵ��˵�
		m1.addSeparator();// ��ӷָ���
		m1.add("�˳�");
		m1.addActionListener(this);// ����m1
		myB.add(m1);
		Menu m2 = new Menu("����");
		m2.add("����");
		m2.add("����");
		m2.addActionListener(this);
		myB.add(m2);

		//�����ļ�
		students = CsvUtil.readEntity();
		
		//��ѡ��
		JPanel contentPane=new JPanel();  
        contentPane.setBorder(new EmptyBorder(5,5,5,5));  
        this.setContentPane(contentPane);  
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
        JLabel label=new JLabel("ѡ�����:");  
        contentPane.add(label);  
        JComboBox comboBox=new JComboBox();
        //��ȡ���һ����ţ����ܵ�����
        int total_team = Integer.valueOf(students.get(students.size()-1).getTeam());
        for(int i = 1; i <= total_team; i++){
        	comboBox.addItem(i);
        }
        contentPane.add(comboBox);  
        
        JButton button = new JButton("��ʼ");
        //String selectedNum = comboBox.getSelectedItem().toString();
        //��ť�¼�����
        button.addActionListener(
	        new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	String selectedNum = comboBox.getSelectedItem().toString();
	            	//System.out.println("ѡ�����ţ�" + selectedNum);
	            	
	            	String ppt = "";
	            	String ask = "";
	            	String answer = "";
	            	
	            	List<Student> todayStus = RollcallUtil.getStu_team(students, selectedNum);
	            	if(todayStus.size() == 5 ){
	            		//��ȡʱ���ѡ��ش��������
	            		long time = System.currentTimeMillis();
	                	if(time % 2 == 0){
	                		System.out.println("��    ppt��" + todayStus.get(0).getName());
	                		ppt = "��ppt:" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	                		System.out.println("�ش����⣺" + todayStus.get(1).getName());
	                		answer = "�ش����⣺" + todayStus.get(1).getStu_id() + todayStus.get(1).getName() + "  ";
	                	}else{
	                		System.out.println("��   ppt��" + todayStus.get(1).getName());
	                		ppt = "��ppt:" + todayStus.get(1).getStu_id() + todayStus.get(1).getName() + "  " + "\n";
	                		System.out.println("�ش����⣺" + todayStus.get(0).getName());
	                		answer = "�ش����⣺" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  ";
	                	}
	                	for(int i = 2; i < todayStus.size(); i++){
	                		System.out.println("�����ߣ�" + todayStus.get(i).getName());
	                		ask += "�����ߣ�" + todayStus.get(i).getStu_id() + todayStus.get(i).getName() + "  " + "\n";
	                	}
	            	}else if(todayStus.size() == 4){
	            		System.out.println("��ppt��" + todayStus.get(0).getName());
	            		ppt = "��ppt��" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	            		System.out.println("�ش����⣺" + todayStus.get(0).getName());
	            		answer = "�ش����⣺" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  ";
	            		for(int i = 1; i < todayStus.size(); i++){
	                		System.out.println("�����ߣ�" + todayStus.get(i).getName());
	                		ask += "�����ߣ�" + todayStus.get(i).getStu_id() + todayStus.get(i).getName() + "  " + "\n";
	                	}
	            	}
	            	else{
	            		ppt = "�����˶��ش���˰����������̫���˰ɣ���";
	            	}
	            	//�����ļ�
	            	CsvUtil.csvWriter(students);
	            	
	            	JFrame groupJFrame = new GroupFrame(selectedNum, ppt, ask, answer, mainJFrame);
	    		}
				
	        }
        );
        button.setBounds(137, 10, 84, 25);
        contentPane.add(button);
        button.addActionListener(this);
        this.setVisible(true);  
		
	}
	
	public static void main(String args[]) {
		setUIFont();
		MySwing myMenu = new MySwing();
		
		myMenu.init();
		myMenu.setVisible(true);
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		switch (e.getActionCommand()) {
		case "��ȡ����":
			List<Student> stus = RollcallUtil.getStu_ask(students, 3);
			String textContent = "";
			for (Student student : stus) {
				textContent += student.getStu_id() + "  " + student.getName() + "  ";
				textContent += "\n";
			}
			createDialogWindow(textContent);
			//�����ļ�
			CsvUtil.csvWriter(students);
			break;
		case "���ѡ��":
			stus = RollcallUtil.getStu_ask(students, 1);
			textContent = "";
			for(Student student : stus){
				textContent += student.getStu_id() + "  " + student.getName() + "  ";
				textContent += "\n ";
			}
			createDialogWindow(textContent);
			//�����ļ�
			CsvUtil.csvWriter(students);
			break;
		case "��������":
			//���ûش�����
			CsvUtil.resetCSV();
			createDialogWindow("���óɹ�");
			//���¶����ļ�
			students = CsvUtil.readEntity();
			break;
		case "����":
			String textContent2 = "1.�����˵�ѡ���������г�ǩ  \n"
					+ "2.�����˵����Խ����������  \n"
					+ "3.�˳�����ֱ�ӵ�����Ͻǣ�  \nҲ�����ڲ����˵����˳�  \n";
			createDialogWindow(textContent2);
			break;
		case "����":
			break;
		case "�˳�":
			System.exit(0);
			break;
		default:
			break;
		}
	}   
	
	/**
	 * ��������
	 * @param textContent ����
	 */
	public static void createDialogWindow(String textContent){
		//����JDialog���ڶ���
		JDialog dialog = new JDialog();
		dialog.setBounds(250, 250, 300, 180);
		
		JPanel panel = new JPanel(); 
		JTextArea textarea = new JTextArea();
		textarea.setText(textContent);
		
		panel.add(textarea);
		
		dialog.add(panel);
		dialog.setVisible(true);
	}

	/**
	 * ����swing����
	 */
	public static void setUIFont()
	{
		Font f = new Font("����",Font.PLAIN,20);
		String   names[]={ "Label", "CheckBox", "PopupMenu","MenuItem", "CheckBoxMenuItem",
				"JRadioButtonMenuItem","ComboBox", "Button", "Tree", "ScrollPane",
				"TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
				"OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
				"ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
				"PasswordField","TextField", "Table", "Label", "Viewport",
				"RadioButtonMenuItem","RadioButton", "DesktopPane", "InternalFrame"
		}; 
		for (String item : names) {
			 UIManager.put(item+ ".font",f); 
		}
	}
	
	
	private void GroupJFrame(String selectedNum, String ppt,
			String ask, String answer) {
		//����JDialog���ڶ���
		
		JFrame jFrame = new JFrame();
		jFrame.setBounds(200, 200, 450, 350);
		
		JPanel panel = new JPanel(); 
		JLabel label=new JLabel("��" + selectedNum + "��");
		JTextArea textarea = new JTextArea();
		textarea.setText(ppt + answer);
		JTextArea textarea2 = new JTextArea(); 
		textarea2.setText(ask);
		textarea2.setVisible(false);
		JButton button_in = new JButton("��ʾ������");
		count = 0;
		button_in.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				count = count + 1;
				if(count%2 == 1){
					textarea2.setVisible(true);
					button_in.setText("����������");
				}else{
					textarea2.setVisible(false);
					button_in.setText("��ʾ������");
				}
				
			}
		});
		if(ask.equals("")){
			button_in.setVisible(false);
		}
		panel.add(label);
		panel.add(textarea);
		panel.add(button_in);
		panel.add(textarea2);
		jFrame.add(panel);
		jFrame.setVisible(true);
	}
	
	
}

class GroupFrame extends JFrame implements ActionListener, ItemListener{

	int count;//��ʾ�����ؼ�����
	
	public GroupFrame(String selectedNum, String ppt,
			String ask, String answer, JFrame mainFrame) {
		
		super("����");
		JFrame groupFrame = this;
		
		setBounds(200, 200, 450, 350);
		
		JPanel panel = new JPanel(); 
		JLabel label=new JLabel("��" + selectedNum + "��");
		JTextArea textarea = new JTextArea();
		textarea.setText(ppt + answer);
		JTextArea textarea2 = new JTextArea(); 
		textarea2.setText(ask);
		textarea2.setVisible(false);
		JButton button_in = new JButton("��ʾ������");
		count = 0;
		button_in.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				count = count + 1;
				if(count%2 == 1){
					textarea2.setVisible(true);
					button_in.setText("����������");
				}else{
					textarea2.setVisible(false);
					button_in.setText("��ʾ������");
				}
				
			}
		});
		if(ask.equals("")){
			button_in.setVisible(false);
		}
		
		JButton button_back = new JButton("����");
		button_back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(true);
//				groupFrame.setVisible(false);
				//dispose�ͷ��ڴ�
				groupFrame.dispose();
			}
		});
		
		panel.add(label);
		panel.add(textarea);
		panel.add(button_in);
		panel.add(button_back);
		panel.add(textarea2);
		
		add(panel);
		setVisible(true);
		
		setBounds(200, 200, 450, 350);
		//this.addWindowListener(windowListener);//���ڼ���
		mainFrame.setVisible(false);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
