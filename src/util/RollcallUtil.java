package util;

import java.util.ArrayList;
import java.util.List;

import entity.Student;

public class RollcallUtil {

	//�������������ʹ��
	/**
	 * ��������õ�ѧ����
	 * @param students
	 * @param stu_num
	 * @return
	 */
	public static List<Student> getStu_ask(List<Student> students, int stu_num){
		
		//��ȡ��Ҫ�ش������ͬѧ
		List<Student> stuPool = new ArrayList<Student>();
		for(int i = 0; i < stu_num; i++){
			stuPool.add(getLuckyStu(students));
		}
		return stuPool;
	}
	
	/**
	 * level��ʾ�����������˷���������������
	 * @param students
	 * @return
	 */
	private static List<Student> upLevel(List<Student> students){
		for (Student student : students) {
			
			int level_now = Integer.valueOf(student.getAnswer_level());
			//�����ȼ�
			student.setAnswer_level( (level_now+1) + "" );
		}
		return students;
	}
	
	//��ȡС��ʹ��
	/**
	 * �ж��Ƿ���Ҫ��������
	 * @param students
	 * @return
	 */
	private static boolean judge_level(List<Student> students){
		int count = 0;
		for (Student student : students) {
			int answer_num = Integer.valueOf(student.getAnswer_num());
			int answer_level = Integer.valueOf(student.getAnswer_level());
			//ͳ�Ƶ�ǰ���б���������
			if(answer_num == answer_level){
				count++;
			}
		}
		//���ȫ������������������
		if(count == students.size()){
			return true;
		}else{
			return false;	
		}
	}
	
	/**
	 * ��ȡ������δ�ش������ͬѧ
	 * @param students
	 * @return
	 */
	private static Student getLuckyStu(List<Student> students){
		
		//�ж��Ƿ���Ҫ��������
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
		//�����������math.random����ҿ������Խ���������
		int luckyNum =  (int)(Math.random() * stuPool.size());
		Student luckyStu = stuPool.get(luckyNum);
		answer_num = Integer.valueOf(luckyStu.getAnswer_num());
		//�ش�����һ
		luckyStu.setAnswer_num( (answer_num+1) + "" );
		return luckyStu;
	}
	
	/**
	 * ��дgetLuckyStu�������÷���ʵ���˽��콲ppt���鲻�ܱ�ѡ��
	 * @param students
	 * @param today_team
	 * @return
	 */
	private static Student getLuckyStu(List<Student> students, String today_team){
		//�ж��Ƿ���Ҫ��������
		if(judge_level(students)){
			upLevel(students);
		}
		
		List<Student> stuPool = new ArrayList<Student>();
		int answer_num = 0;
		int answer_level = 0;
		for (Student student : students) {
			answer_num = Integer.valueOf(student.getAnswer_num());
			answer_level = Integer.valueOf(student.getAnswer_level());
			//���콲ppt���޷���ѡ��
			if(!student.getTeam().equals(today_team)){
				if(answer_num < answer_level){
					stuPool.add(student);
				}
			}
		}
		//�����������math.random����ҿ������Խ���������
		int luckyNum =  (int)(Math.random() * stuPool.size());
		Student luckyStu = stuPool.get(luckyNum);
		answer_num = Integer.valueOf(luckyStu.getAnswer_num());
		//�ش�����һ
		luckyStu.setAnswer_num( (answer_num+1) + "" );
		return luckyStu;
	}
	
	/**
	 * ��ȡ����Ҫ��ppt�������ش������ͬѧ��ǰ������ppt���������ش�����
	 * @param students
	 * @param team_num
	 * @param today_team
	 * @return
	 */
	public static List<Student> getStu_team(List<Student> students, String today_team){
		
		List<Student> todayStus = new ArrayList<Student>();
		
		for (Student student : students) {
			if(student.getTeam().equals(today_team)){
				//ȡ�����޷����ظ�ѡ�е�����
				//if(student.getTeam_status().equals("waiting")){
					student.setTeam_status("finished");
					todayStus.add(student);
				//}
			}
		}
		//�Ѿ���ѡ�й������ؿռ�
		if(todayStus.size() == 0){
			System.out.println("�ռ�");
			return todayStus;
		}
		//���ڴ洢��ǰ�������߷�ֹ�ظ�
		List<Student> tempStu = new ArrayList<Student>();
		for(int i = 0; i < 3; i++){
//			Student student = getLuckyStu2(students, today_team);
			Student student = getLuckyStu3(students, today_team, tempStu);
			if(student != null){
				todayStus.add(student);
			}
		}
		//��մ洢��
		tempStu.clear();
		return todayStus;
		
	}
	
	/**
	 * �ڶ��ֵ�����ʽ��ͨ������ó����ȷֲ�״̬�����Ļش����
	 * @param students
	 * @param today_team
	 * @return
	 */
	public static Student getLuckyStu2(List<Student> students, String today_team){
		int teamNum = Integer.valueOf(students.get(students.size()-1).getTeam());
		int peopleNum = students.size();
		List<Student> stuPool = new ArrayList<Student>();
		//�������1��ԭ������Ϊ�����ݴ��ʣ����������Ժ��ԣ���Ϊ����������޷�����,teamNum*3/peopleNum������Ϊ1.5
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
			//�����������math.random����ҿ������Խ���������
			int luckyNum =  (int)(Math.random() * stuPool.size());
			Student luckyStu = stuPool.get(luckyNum);
			answer_num = Integer.valueOf(luckyStu.getAnswer_num());
			//�ش�����һ
			luckyStu.setAnswer_num( (answer_num+1) + "" );
			return luckyStu;
		}
		return null;
	}
	
	/**
	 * �����ֵ�����ʽ�������۶���ֲ�������ʹ�õ������д���֮ǰ�����˶��ش��
	 * ���ƣ�Ŀǰ��ɵ������ش�Ϊ2���������ʱ��δ��չ
	 * @param students
	 * @param today_team
	 * @return
	 */
	private static Student getLuckyStu3(List<Student> students, String today_team, List<Student> tempStu){
		//���Ϊ0.9��С��0.9ȡ�ش����Ϊ0���ˣ�����0.9ȡ�ش����Ϊ1����
		//��0��ȡ��ʱ����ȡ�ش����Ϊ1�ģ��ش�2�εĲ��ٳ�ȡ
		double lambda = 0.9f;
		
		Student luckyStu;
//		int teamNum = Integer.valueOf(students.get(students.size()-1).getTeam());
//		int peopleNum = students.size();
		//�������1��ԭ������Ϊ�����ݴ��ʣ����������Ժ��ԣ���Ϊ����������޷�����,teamNum*3/peopleNum������Ϊ1.5
//		int maxAnswer = ((teamNum*3) % peopleNum == 0) ?
//				((teamNum*3) / peopleNum + 1) : ((teamNum*3) / peopleNum + 1);
		
		int answer_num = 0;
		double random;
		
		//���ش����Ϊ0��Ϊ1�ķ�����Եļ���
		List<Student> stu0 = new ArrayList<Student>();
		List<Student> stu1 = new ArrayList<Student>();
		
		for (Student student : students) {
			//��־��ѧ���Ƿ��ܱ�����ѧ������
			boolean judge = true;
			if(tempStu.size() != 0){
				for (Student answered_stu : tempStu) {
					if(answered_stu.getName().equals(student.getName())){
						judge = false;
					}
				}
			}
			//����жϲ��ܷ��룬��������ѧ��
			if(!judge){
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
 				//�����ȡ
 				int index = (int)(Math.random() * stu0.size());
 				luckyStu = stu0.get(index);
 			} else {
 				//�ش�0�β����ڣ���ӻش�1�ε��м��ȡ
 				int index = (int)(Math.random() * stu1.size());
 				luckyStu = stu1.get(index);
 			}
 		} else {
 			if(stu1.size() != 0) {
 				int index = (int)(Math.random() * stu1.size());
 				luckyStu = stu1.get(index);
 			} else {
 				//�ش�1�εĲ����ڣ���ӻش�0�ε��м��ȡ
 				int index = (int)(Math.random() * stu0.size());
 				luckyStu = stu0.get(index);
 			}
 		}
 		answer_num = Integer.valueOf(luckyStu.getAnswer_num());
		//�ش�����һ
		luckyStu.setAnswer_num( (answer_num+1) + "" );
		//�洢��+1
		tempStu.add(luckyStu);
 		return luckyStu;
	}
}
