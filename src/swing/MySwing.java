package swing;

import java.awt.FlowLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextField;
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
import javax.swing.border.EmptyBorder;

import util.CsvUtil;
import util.RollcallUtil;
import util.winEventHandle;
import entity.Student;

public class MySwing extends JFrame implements ActionListener, ItemListener{

	List<Student> students = new ArrayList<Student>();
	public MySwing() {
		super("һ��СС�ĵ�������");
		setBounds(200, 200, 300, 200);
		this.addWindowListener(new winEventHandle());//���ڼ���
	}

	public void init() {
		//�˵���
		MenuBar myB = new MenuBar();// �����˵���
		setMenuBar(myB);
		Menu m1 = new Menu("����");// �����˵�����
		m1.add(new MenuItem("��ȡ����"));
		m1.add(new MenuItem("���ѡ��"));
//		MenuItem m11 = new MenuItem("����");// �����˵���
//		m11.setEnabled(false);// ����
//		m1.add(m11);// ���ӵ��˵�
		m1.addSeparator();// ���ӷָ���
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
//		System.out.println(students.get(3).getName());
		
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
	            	if(todayStus.size() == 5){
	            		//��ȡʱ���ѡ��ش��������
	            		long time = System.currentTimeMillis();
	                	if(time % 2 == 0){
	                		System.out.println("��ppt��" + todayStus.get(0).getName());
	                		ppt = "��ppt:" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	                		System.out.println("�ش����⣺" + todayStus.get(1).getName());
	                		answer = "�ش����⣺" + todayStus.get(1).getStu_id() + todayStus.get(1).getName() + "  " + "\n";
	                	}else{
	                		System.out.println("��ppt��" + todayStus.get(1).getName());
	                		ppt = "��ppt:" + todayStus.get(1).getStu_id() + todayStus.get(1).getName() + "  " + "\n";
	                		System.out.println("�ش����⣺" + todayStus.get(0).getName());
	                		answer = "�ش����⣺" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	                	}
	                	for(int i = 2; i < todayStus.size(); i++){
	                		System.out.println("�����ߣ�" + todayStus.get(i).getName());
	                		ask += "�����ߣ�" + todayStus.get(i).getStu_id() + todayStus.get(i).getName() + "  " + "\n";
	                	}
	            	}else if(todayStus.size() == 4){
	            		System.out.println("��ppt��" + todayStus.get(0).getName());
	            		ppt = "��ppt��" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	            		System.out.println("�ش����⣺" + todayStus.get(0).getName());
	            		answer = "�ش����⣺" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	            		for(int i = 1; i < todayStus.size(); i++){
	                		System.out.println("�����ߣ�" + todayStus.get(i).getName());
	                		ask += "�����ߣ�" + todayStus.get(i).getStu_id() + todayStus.get(i).getName() + "  " + "\n";
	                	}
	            	}
	            	else{
	            		ppt = "�Ѿ����һ����";
	            	}
	            	//�����ļ�
	            	CsvUtil.csvWriter(students);
	            	
	            	
	            	//����JDialog���ڶ���
	    			JDialog dialog = new JDialog();
	    			dialog.setBounds(200, 200, 300, 200);
	    			
	    			JPanel panel = new JPanel(); 
	    			JTextArea textarea = new JTextArea();
	    			textarea.setText(ppt + answer + ask);
	    			
	    			panel.add(textarea);
	    			
	    			dialog.add(panel);
	    			dialog.setVisible(true);
	    		}
	            		            
	        }
        );
        button.setBounds(137, 10, 84, 25);
        contentPane.add(button);
        button.addActionListener(this);
        this.setVisible(true);  
		
	}
	
	public static void main(String args[]) {
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
			createRCWindow(textContent);
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
			createRCWindow(textContent);
			//�����ļ�
			CsvUtil.csvWriter(students);
			break;
		case "����":
			String textContent2 = "1.�����˵�ѡ���������г�ǩ  \n"
					+ "2.�����˵����Խ����������  \n"
					+ "3.�˳�����ֱ�ӵ�����Ͻǣ�  \nҲ�����ڲ����˵����˳�  \n";
			createRCWindow(textContent2);
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
	
	
	public void createRCWindow(String textContent){
		//����JDialog���ڶ���
		JDialog dialog = new JDialog();
		dialog.setBounds(200, 200, 300, 200);
		
		JPanel panel = new JPanel(); 
		JTextArea textarea = new JTextArea();
		textarea.setText(textContent);
		
		panel.add(textarea);
		
		dialog.add(panel);
		dialog.setVisible(true);
	}

	
}