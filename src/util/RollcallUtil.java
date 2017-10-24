package util;

import java.util.ArrayList;
import java.util.List;

import entity.Student;

public class RollcallUtil {

	//无限制随机点名使用
	/**
	 * 随机点名用的学生池
	 * @param students
	 * @param stu_num
	 * @return
	 */
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
	
	//抽取小组使用
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
				//取消了无法被重复选中的限制
				//if(student.getTeam_status().equals("waiting")){
					student.setTeam_status("finished");
					todayStus.add(student);
				//}
			}
		}
		//已经被选中过，返回空集
		if(todayStus.size() == 0){
			System.out.println("空集");
			return todayStus;
		}
		//用于存储当前轮提问者防止重复
		List<Student> tempStu = new ArrayList<Student>();
		for(int i = 0; i < 3; i++){
//			Student student = getLuckyStu2(students, today_team);
			Student student = getLuckyStu3(students, today_team, tempStu);
			if(student != null){
				todayStus.add(student);
			}
		}
		//清空存储器
		tempStu.clear();
		return todayStus;
		
	}
	
	/**
	 * 第二种点名方式，通过计算得出均匀分布状态下最大的回答次数
	 * @param students
	 * @param today_team
	 * @return
	 */
	public static Student getLuckyStu2(List<Student> students, String today_team){
		int teamNum = Integer.valueOf(students.get(students.size()-1).getTeam());
		int peopleNum = students.size();
		List<Student> stuPool = new ArrayList<Student>();
		//整除多加1的原因是因为增加容错率，但几乎可以忽略，因为正常情况下无法整除,teamNum*3/peopleNum的期望为1.5
		int maxAnswer = ((teamNum*3) % peopleNum == 0) ?
				((teamNum*3) / peopleNum + 1) : ((teamNum*3) / peopleNum + 1);
		int answer_num = 0;
		for (Student student : students) {
			if(!student.getTeam().equals(today_team)){
				answer_num = Integer.valueOf(student.getAnswer_num());
				if(answer_num < maxAnswer){
					stuPool.add(student);
				}
			}
		}
		if(stuPool.size() != 0){
			//生成随机数，math.random左闭右开，如果越界可能在这
			int luckyNum =  (int)(Math.random() * stuPool.size());
			Student luckyStu = stuPool.get(luckyNum);
			answer_num = Integer.valueOf(luckyStu.getAnswer_num());
			//回答数加一
			luckyStu.setAnswer_num( (answer_num+1) + "" );
			return luckyStu;
		}
		return null;
	}
	
	/**
	 * 第三种点名方式，概率论二项分布方法，使得点完所有次数之前所有人都回答过
	 * 限制：目前完成的是最大回答为2的情况，暂时并未扩展
	 * @param students
	 * @param today_team
	 * @param tempStu 存储已经选中的提问者
	 * @return
	 */
	private static Student getLuckyStu3(List<Student> students, String today_team, List<Student> tempStu){
		//设λ为0.9，小于0.9取回答次数为0的人，大于0.9取回答次数为1的人
		//当0被取完时，再取回答次数为1的，回答2次的不再抽取
		double lambda = 0.9f;
		
		Student luckyStu;
//		int teamNum = Integer.valueOf(students.get(students.size()-1).getTeam());
//		int peopleNum = students.size();
		//整除多加1的原因是因为增加容错率，但几乎可以忽略，因为正常情况下无法整除,teamNum*3/peopleNum的期望为1.5
//		int maxAnswer = ((teamNum*3) % peopleNum == 0) ?
//				((teamNum*3) / peopleNum + 1) : ((teamNum*3) / peopleNum + 1);
		
		int answer_num = 0;
		double random;
		
		//将回答次数为0与为1的放入各自的集合
		List<Student> stu0 = new ArrayList<Student>();
		List<Student> stu1 = new ArrayList<Student>();
		
		for (Student student : students) {
			//标志该学生是否能被放入学生池中
			//重复的提问者无法被选中
			boolean judge = true;
			if(tempStu.size() != 0){
				for (Student answered_stu : tempStu) {
					if(answered_stu.getName().equals(student.getName())){
						judge = false;
					}
				}
			}
			//获取当前的学生组号
			String team_num = student.getTeam();
			
			//如果判断不能放入，则跳过该学生
			if(!judge){
				continue;
			} else if(team_num.equals(today_team)){
				//无法取到当前组的学生
				continue;
			}
			
			answer_num = Integer.valueOf(student.getAnswer_num());
			if(answer_num == 0) {
				stu0.add(student);
			} else if(answer_num == 1) {
				stu1.add(student);
			}
		}
 		random = Math.random();
 		if(random < lambda){
 			if(stu0.size() != 0) {
 				//随机抽取
 				int index = (int)(Math.random() * stu0.size());
 				luckyStu = stu0.get(index);
 			} else {
 				//回答0次不存在，则从回答1次的中间抽取
 				int index = (int)(Math.random() * stu1.size());
 				luckyStu = stu1.get(index);
 			}
 		} else {
 			if(stu1.size() != 0) {
 				int index = (int)(Math.random() * stu1.size());
 				luckyStu = stu1.get(index);
 			} else {
 				//回答1次的不存在，则从回答0次的中间抽取
 				int index = (int)(Math.random() * stu0.size());
 				luckyStu = stu0.get(index);
 			}
 		}
 		answer_num = Integer.valueOf(luckyStu.getAnswer_num());
		//回答数加一
		luckyStu.setAnswer_num( (answer_num+1) + "" );
		//存储器+1
		tempStu.add(luckyStu);
 		return luckyStu;
	}
}
