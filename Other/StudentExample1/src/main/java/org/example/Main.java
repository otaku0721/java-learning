package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        List<Student> list = new ArrayList(){{
            add(new Student("Tom",18,100,"class05"));
            add(new Student("Jerry",22,70,"class04"));
            add(new Student("Owen",23,90,"class05"));
            add(new Student("Jim",25,80,"class05"));
            add(new Student("Steve",20,66,"class06"));
            add(new Student("Kevin",24,100,"class04"));
        }};
        //获取平均年龄
        double averageAge = 0d;
        List<String> className = new ArrayList<String>();           //新建一个班级的List，记录出现过的班级，待会有用
        for(int i = 0;i<list.size();i++){                           //将所有年龄加起来
            averageAge+=(double)list.get(i).age;
            if (!className.contains(list.get(i).classNum))
                className.add(list.get(i).classNum);                //如果当前班级在变量里边不存在，则增加
        }
        averageAge = (averageAge / (double)list.size());            //求平均年龄
        System.out.println("所有学生平均年龄："+averageAge);          //打印
        List<Double> classAvgScore = new  ArrayList<Double>();      //这个是班级平均年龄的一个List
        for(int i = 0;i<className.size();i++){                      //创建一个索引，指向className这个List
            double avgScore=0d;                                     //平均成绩的变量
            int people = 0;                                         //记录班级人数
            for(int j = 0;j<list.size();j++){                       //遍历list（也就是刚刚的学生类的List）
                if (className.get(i).equals(list.get(j).classNum))  //如果classname（索引i）和学生类（j）里的班级名字对上
                {
                    avgScore +=(double)list.get(j).score;           //加上成绩
                    people++;                                       //人数加一
                }
            }
            classAvgScore.add(avgScore / (double)people );          //计算平均分并加入classAvgScore里
        }
        IntStream.range(0,className.size()).forEach
                (i->System.out.println("班级："+className.get(i).toString()+" 平均成绩:"+classAvgScore.get(i).toString()));
            //打印相关信息。由于上述算法是严格遵循顺序来的，所以直接按照顺序打印出两个变量即可，亦可以用TreeList排序
            //也可以用TreeMap，新建一个方法专门用来计算平均成绩也可以
        System.out.println("\n");
        main2();
    }
    public static void main2(){
        List<Student> list = new ArrayList<Student>() {{
            add(new Student("Tom", 18, 100, "class05"));
            add(new Student("Jerry", 22, 70, "class04"));
            add(new Student("Owen", 23, 90, "class05"));
            add(new Student("Jim", 25, 80, "class05"));
            add(new Student("Steve", 20, 66, "class06"));
            add(new Student("Kevin", 24, 100, "class04"));
        }};
        // 获取平均年龄
        double averageAge = list.stream().mapToInt(Student::getAge).average().orElse(0);
        System.out.println("所有学生平均年龄：" + averageAge);
        // 获取班级平均成绩
        Map<String, Double> classAvgScoreMap = list.stream()
                .collect(Collectors.groupingBy(Student::getClassName,
                        Collectors.mapping(Student::getScore,
                                Collectors.toList())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,   //这个Map是这样的：("Class05",{100,90,80}),("Class04",66)...
                        x->x.getValue()
                                .stream()
                                .mapToDouble(i->i)
                                .average()
                                .orElse(0)));

        for (Map.Entry<String, Double> entry : classAvgScoreMap.entrySet()) {
            System.out.println("班级：" + entry.getKey() + " 平均成绩：" + entry.getValue());
        }
    }
}
