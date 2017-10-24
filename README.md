# 点名软件
## 产品说明
这是一个使用java Swing实现的点名软件，选取小组进行点名，同时可以进行随机点名。

## 实现思路
为了加强扩展性，使用csv（逗号分隔值）格式，也便于使用excel打开。同时使用exe4j打包成exe携带java环境。

**小组点名**
使用时间戳取余2（单人小组例外）进行随机选取。   
**随机点名**
采用轮数制，每一轮每个同学只能被点名一次，这样避免了完全随机的缺点，使每个同学都能回答问题。

## 使用方式
windows环境点击exe文件即可运行，jar文件支持装有java环境的任意系统
简单的两处操作：  
- 1.主界面选择小组即可开始抽取   
- 2.操作菜单可以进行随机点名  

## 文件修改方式
位于**file**文件夹的**students.csv**文件有id,team,name,stu_id,answer_num,team_status,answer_level八个字段。
- **id**:学生的序号，从1开始
- **team**:所属团队序号，从1开始
- **name**:学生姓名
- **stu_id**:学生学号
- **answer_num**:学生回答的次数，初始皆为0
- **team_status**:团队回答状态，初始皆为等待状态置为waiting,被点名后置为finished
- **answer_level**:回答轮数，初始皆为0

PS：1.文件名请勿随意修改   
2.初始版在根目录中，修改文件名放入file文件夹即可

## 可能存在的bug
经目前的测试，还未出现明显的bug,已解决的bug有   
- 1.小组人数为1，方法是if else特殊处理   
- 2.重复选择一个小组，方法是加入了team_status标志位   

不过还是存在可能出现的bug，即id或team序号的连续，倘若出现间断，会有影响，但不是致命错误，不过还是建议按照我的字段要求存放数据。

-----
# v2.0 介绍
## 1 修改
### 1.1 点名机制修改
更换了点名机制，修改了之前的轮数机制，使用最大回答数限制的方法，**但本人认为这里存在极大的问题，后期需进行修改**。
### 1.2 界面修改
修改了重复的弹窗，将点名弹出的窗口销毁进行资源释放。

## 2 新增
### 2.1 新增重置
新增了重置数据功能，在**第一个菜单栏**中   
使用方法：**原始数据** 保存在根目录的 **students初始版.csv** 中，仅需将其中的姓名以及组号进行修改即可，其他保持默认。然后点击重置数据即可得到提示“重置成功”。
### 2.2 新增返回
新增了返回键，点击可以返回主界面。

-----
# v2.1 介绍
## 修改
修改了点名算法，使用**概率论二项分布**方式，实现了数据的均匀分布。

------
# v2.2 介绍
## Debug
修复了当前组可以被选中的bug
