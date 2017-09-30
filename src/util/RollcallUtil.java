package util;

import java.util.ArrayList;
import java.util.List;

import entity.Student;

public class RollcallUtil {

	public static List<Student> getStu_ask(List<Student> students, int stu_num){
		
		//获取需要回答问题的同学
		List<Student> stuPool = new ArrayList<Student>();
		for(int i = 0; i < stu_num; i++){
			stuPool.add(getLuckyStu(students));
		}
		return stuPool;
	}
	
	/**
	 * level表示点名轮数，此方法用于升级轮数
	 * @param students
	 * @return
	 */
	private static List<Student> upLevel(List<Student> students){
		for (Student student : students) {
			
			int level_now = Integer.valueOf(student.getAnswer_level());
			//提升等级
			student.setAnswer_level( (level_now+1) + "" );
		}
		return students;
	}
	
	/**
	 * 判断是否需要提升轮数
	 * @param students
	 * @return
	 */
	private static boolean judge_level(List<Student> students){
		int count = 0;
		for (Student student : students) {
			int answer_num = Integer.valueOf(student.getAnswer_num());
			int answer_level = Integer.valueOf(student.getAnswer_level());
			//统计当前轮有被点名人数
			if(answer_num == answer_level){
				count++;
			}
		}
		//如果全部被点名，提升轮数
		if(count == students.size()){
			return true;
		}else{
			return false;	
		}
	}
	
	/**
	 * 获取该轮尚未回答问题的同学
	 * @param students
	 * @return
	 */
	private static Student getLuckyStu(List<Student> students){
		
		//判断是否需要提升轮数
		if(judge_level(students)){
			upLevel(students);
		}
		
		List<Student> stuPool = new ArrayList<Student>();
		int answer_num = 0;
		int answer_level = 0;
		for (Student student : students) {
			answer_num = Integer.valueOf(student.getAnswer_num());
			answer_level = Integer.valueOf(student.getAnswer_level());
			if(answer_num < answer_level){
				stuPool.add(student);
			}
		}
		//生成随机数，math.random左闭右开，如果越界可能在这
		int luckyNum =  (int)(Math.random() * stuPool.size());
		Student luckyStu = stuPool.get(luckyNum);
		answer_num = Integer.valueOf(luckyStu.getAnswer_num());
		//回答数加一
		luckyStu.setAnswer_num( (answer_num+1) + "" );
		return luckyStu;
	}
	
	/**
	 * 复写getLuckyStu方法，该方法实现了今天讲ppt的组不能被选中
	 * @param students
	 * @param today_team
	 * @return
	 */
	private static Student getLuckyStu(List<Student> students, String today_team){
		//判断是否需要提升轮数
		if(judge_level(students)){
			upLevel(students);
		}
		
		List<Student> stuPool = new ArrayList<Student>();
		int answer_num = 0;
		int answer_level = 0;
		for (Student student : students) {
			answer_num = Integer.valueOf(student.getAnswer_num());
			answer_level = Integer.valueOf(student.getAnswer_level());
			//今天讲ppt的无法被选中
			if(!student.getTeam().equals(today_team)){
				if(answer_num < answer_level){
					stuPool.add(student);
				}
			}
		}
		//生成随机数，math.random左闭右开，如果越界可能在这
		int luckyNum =  (int)(Math.random() * stuPool.size());
		Student luckyStu = stuPool.get(luckyNum);
		answer_num = Integer.valueOf(luckyStu.getAnswer_num());
		//回答数加一
		luckyStu.setAnswer_num( (answer_num+1) + "" );
		return luckyStu;
	}
	
	/**
	 * 获取今天要讲ppt和三个回答问题的同学，前两个是ppt，后三个回答问题
	 * @param students
	 * @param team_num
	 * @param today_team
	 * @return
	 */
	public static List<Student> getStu_team(List<Student> students, String today_team){
		
		List<Student> todayStus = new ArrayList<Student>();
		
		for (Student student : students) {
			if(student.getTeam().equals(today_team)){
				if(student.getTeam_status().equals("waiting")){
					student.setTeam_status("finished");
					todayStus.add(student);
				}
			}
		}
		//已经被选中过，返回空集
		if(todayStus.size() == 0){
			System.out.println("空集");
			return todayStus;
		}
		
		for(int i = 0; i < 3; i++){
			todayStus.add(getLuckyStu(students, today_team));
		}
		return todayStus;
		
	}
}
