package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import swing.MySwing;
import entity.Student;

public class CsvUtil {

	private static BufferedReader reader;

	public static List<Student> readEntity() {
		File csv = new File("file/students.csv"); // CSV文件路径
		
		List<Student> students = new ArrayList<Student>();
		
		try {
			reader = new BufferedReader(new FileReader(csv));
		
			reader.readLine();// 第一行信息，为标题信息,不做处理
			String line = null;
			while ((line = reader.readLine()) != null) {
				Student stu = new Student();
				String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
				stu.setId(item[0]);
				stu.setTeam(item[1]);
				stu.setName(item[2]);
				stu.setStu_id(item[3]);
				stu.setAnswer_num(item[4]);
				stu.setTeam_status(item[5]);
				stu.setAnswer_level(item[6]);
				students.add(stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(students.get(0).getName());
		return students;
	}
	
	public static void csvWriter(List<Student> students){
		File csv = new File("file/students.csv");
		
		try {
			if(!csv.exists()){
				csv.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(csv));
			BufferedWriter writer = new BufferedWriter(write);
			writer.write("id,team,name,stu_id,answer_num,team_status,answer_level");
			writer.newLine();
			for(int i = 0; i < students.size(); i++){
				Student stu = students.get(i);
				writer.write(stu.getId());
				writer.write(",");
				writer.write(stu.getTeam());
				writer.write(",");
				writer.write(stu.getName());
				writer.write(",");
				writer.write(stu.getStu_id());
				writer.write(",");
				writer.write(stu.getAnswer_num());
				writer.write(",");
				writer.write(stu.getTeam_status());
				writer.write(",");
				writer.write(stu.getAnswer_level());
				writer.newLine();
			}
			
			writer.flush(); 
            write.close();
            writer.close();
			
		} catch (FileNotFoundException e) {
			DialogMethod("文件正在被占用或不存在\n请关闭文件后使用");
			e.printStackTrace();
			//System.exit(1);;
		} catch (IOException e){
			DialogMethod("文件出现了一些错误");
			e.printStackTrace();
		}
		
	}

	private static void DialogMethod(String content) {
		//创建JDialog窗口对象
		JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		dialog.setBounds(500, 250, 300, 180);
		
		JPanel panel = new JPanel(); 
		JTextArea textarea = new JTextArea();
		textarea.setText(content);
		
		panel.add(textarea);
		
		dialog.add(panel);
		dialog.setVisible(true);
	}
	
	public static void resetCSV(){
		File csv = new File("students初始版.csv"); // CSV文件路径
		
		List<Student> students = new ArrayList<Student>();
		
		try {
			reader = new BufferedReader(new FileReader(csv));
		
			reader.readLine();// 第一行信息，为标题信息,不做处理
			String line = null;
			while ((line = reader.readLine()) != null) {
				Student stu = new Student();
				String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
				stu.setId(item[0]);
				stu.setTeam(item[1]);
				stu.setName(item[2]);
				stu.setStu_id(item[3]);
				stu.setAnswer_num(item[4]);
				stu.setTeam_status(item[5]);
				stu.setAnswer_level(item[6]);
				students.add(stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		csvWriter(students);
	}
	
}
