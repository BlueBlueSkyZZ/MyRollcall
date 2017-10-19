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

	int count;//计数器
	List<Student> students = new ArrayList<Student>();
	public MySwing() {
		super("一个小小的点名软件");
		setBounds(200, 200, 450, 350);
//		this.addWindowListener(new winEventHandle());//窗口监听
	}

	public void init() {
		
		JFrame mainJFrame = this;
		
		//菜单栏
		MenuBar myB = new MenuBar();// 创建菜单组
		setMenuBar(myB);
		Menu m1 = new Menu("操作");// 创建菜单对象
		m1.add(new MenuItem("抽取三人"));
		m1.add(new MenuItem("随机选择"));
		m1.add(new MenuItem("数据重置"));
//		MenuItem m11 = new MenuItem("保存");// 创建菜单项
//		m11.setEnabled(false);// 禁用
//		m1.add(m11);// 添加到菜单
		m1.addSeparator();// 添加分隔线
		m1.add("退出");
		m1.addActionListener(this);// 监听m1
		myB.add(m1);
		Menu m2 = new Menu("帮助");
		m2.add("帮助");
		m2.add("关于");
		m2.addActionListener(this);
		myB.add(m2);

		//读入文件
		students = CsvUtil.readEntity();
		
		//复选框
		JPanel contentPane=new JPanel();  
        contentPane.setBorder(new EmptyBorder(5,5,5,5));  
        this.setContentPane(contentPane);  
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
        JLabel label=new JLabel("选择组号:");  
        contentPane.add(label);  
        JComboBox comboBox=new JComboBox();
        //获取最后一组组号，即总的组数
        int total_team = Integer.valueOf(students.get(students.size()-1).getTeam());
        for(int i = 1; i <= total_team; i++){
        	comboBox.addItem(i);
        }
        contentPane.add(comboBox);  
        
        JButton button = new JButton("开始");
        //String selectedNum = comboBox.getSelectedItem().toString();
        //按钮事件监听
        button.addActionListener(
	        new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	String selectedNum = comboBox.getSelectedItem().toString();
	            	//System.out.println("选择的组号：" + selectedNum);
	            	
	            	String ppt = "";
	            	String ask = "";
	            	String answer = "";
	            	
	            	List<Student> todayStus = RollcallUtil.getStu_team(students, selectedNum);
	            	if(todayStus.size() == 5 ){
	            		//获取时间戳选择回答问题的人
	            		long time = System.currentTimeMillis();
	                	if(time % 2 == 0){
	                		System.out.println("讲    ppt：" + todayStus.get(0).getName());
	                		ppt = "讲ppt:" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	                		System.out.println("回答问题：" + todayStus.get(1).getName());
	                		answer = "回答问题：" + todayStus.get(1).getStu_id() + todayStus.get(1).getName() + "  ";
	                	}else{
	                		System.out.println("讲   ppt：" + todayStus.get(1).getName());
	                		ppt = "讲ppt:" + todayStus.get(1).getStu_id() + todayStus.get(1).getName() + "  " + "\n";
	                		System.out.println("回答问题：" + todayStus.get(0).getName());
	                		answer = "回答问题：" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  ";
	                	}
	                	for(int i = 2; i < todayStus.size(); i++){
	                		System.out.println("提问者：" + todayStus.get(i).getName());
	                		ask += "提问者：" + todayStus.get(i).getStu_id() + todayStus.get(i).getName() + "  " + "\n";
	                	}
	            	}else if(todayStus.size() == 4){
	            		System.out.println("讲ppt：" + todayStus.get(0).getName());
	            		ppt = "讲ppt：" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  " + "\n";
	            		System.out.println("回答问题：" + todayStus.get(0).getName());
	            		answer = "回答问题：" + todayStus.get(0).getStu_id() + todayStus.get(0).getName() + "  ";
	            		for(int i = 1; i < todayStus.size(); i++){
	                		System.out.println("提问者：" + todayStus.get(i).getName());
	                		ask += "提问者：" + todayStus.get(i).getStu_id() + todayStus.get(i).getName() + "  " + "\n";
	                	}
	            	}
	            	else{
	            		ppt = "所有人都回答过了啊！！误操作太多了吧！！";
	            	}
	            	//保存文件
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
		case "抽取三人":
			List<Student> stus = RollcallUtil.getStu_ask(students, 3);
			String textContent = "";
			for (Student student : stus) {
				textContent += student.getStu_id() + "  " + student.getName() + "  ";
				textContent += "\n";
			}
			createDialogWindow(textContent);
			//保存文件
			CsvUtil.csvWriter(students);
			break;
		case "随机选择":
			stus = RollcallUtil.getStu_ask(students, 1);
			textContent = "";
			for(Student student : stus){
				textContent += student.getStu_id() + "  " + student.getName() + "  ";
				textContent += "\n ";
			}
			createDialogWindow(textContent);
			//保存文件
			CsvUtil.csvWriter(students);
			break;
		case "数据重置":
			//重置回答数据
			CsvUtil.resetCSV();
			createDialogWindow("重置成功");
			//重新读入文件
			students = CsvUtil.readEntity();
			break;
		case "帮助":
			String textContent2 = "1.下拉菜单选中组数进行抽签  \n"
					+ "2.操作菜单可以进行随机点名  \n"
					+ "3.退出可以直接点击右上角，  \n也可以在操作菜单中退出  \n";
			createDialogWindow(textContent2);
			break;
		case "关于":
			break;
		case "退出":
			System.exit(0);
			break;
		default:
			break;
		}
	}   
	
	/**
	 * 创建弹窗
	 * @param textContent 内容
	 */
	public static void createDialogWindow(String textContent){
		//创建JDialog窗口对象
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
	 * 设置swing字体
	 */
	public static void setUIFont()
	{
		Font f = new Font("宋体",Font.PLAIN,20);
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
		//创建JDialog窗口对象
		
		JFrame jFrame = new JFrame();
		jFrame.setBounds(200, 200, 450, 350);
		
		JPanel panel = new JPanel(); 
		JLabel label=new JLabel("第" + selectedNum + "组");
		JTextArea textarea = new JTextArea();
		textarea.setText(ppt + answer);
		JTextArea textarea2 = new JTextArea(); 
		textarea2.setText(ask);
		textarea2.setVisible(false);
		JButton button_in = new JButton("显示提问者");
		count = 0;
		button_in.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				count = count + 1;
				if(count%2 == 1){
					textarea2.setVisible(true);
					button_in.setText("隐藏提问者");
				}else{
					textarea2.setVisible(false);
					button_in.setText("显示提问者");
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

	int count;//显示与隐藏计数器
	
	public GroupFrame(String selectedNum, String ppt,
			String ask, String answer, JFrame mainFrame) {
		
		super("点名");
		JFrame groupFrame = this;
		
		setBounds(200, 200, 450, 350);
		
		JPanel panel = new JPanel(); 
		JLabel label=new JLabel("第" + selectedNum + "组");
		JTextArea textarea = new JTextArea();
		textarea.setText(ppt + answer);
		JTextArea textarea2 = new JTextArea(); 
		textarea2.setText(ask);
		textarea2.setVisible(false);
		JButton button_in = new JButton("显示提问者");
		count = 0;
		button_in.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				count = count + 1;
				if(count%2 == 1){
					textarea2.setVisible(true);
					button_in.setText("隐藏提问者");
				}else{
					textarea2.setVisible(false);
					button_in.setText("显示提问者");
				}
				
			}
		});
		if(ask.equals("")){
			button_in.setVisible(false);
		}
		
		JButton button_back = new JButton("返回");
		button_back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(true);
//				groupFrame.setVisible(false);
				//dispose释放内存
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
		//this.addWindowListener(windowListener);//窗口监听
		mainFrame.setVisible(false);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
