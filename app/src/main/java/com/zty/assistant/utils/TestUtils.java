package com.zty.assistant.utils;

import java.util.ArrayList;

/**
 * Created by zhang.tianyi on 2020/3/6 13:25
 */

public class TestUtils {
    private static TestUtils mInstance;

    public static TestUtils getInstance(){
        if (mInstance == null){
            mInstance = new TestUtils();
        }
        return mInstance;
    }

    private TestUtils(){ }

    public static void main(String args[]){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(17);
        list.add(7);
        list.add(10);
        list.add(4);
        list.add(1);

        log(list,"默认数组：");//打印数组

//        log(selectSort(list),"排序数组：");//打印数组
    }

    /**
     * 选择排序
     * @param list 要排序的数组
     * @return 排序成功的数组
     */
    public static ArrayList<Integer> selectSort(ArrayList<Integer> list){
        for (int i = 0; i < list.size(); i++) {//做一个(n-1)次的循环代表进行了(n-1)次的选择
            int k = i;//先记录第一个待排序的元素位置
            for (int j = (k+1); j < list.size(); j++) {//做一个(n-1)~1次的循环代表比较了(n-1)~1次
                if (list.get(j) < list.get(k)){//比较元素
                    k = j;//如果循环中的元素值小于记录的元素值，则更改记录的元素值的位置k
                }
            }
            if (i != k){//i不等于k，说明存在更小(大)的元素值
                int item = list.get(k);
                list.set(k,list.get(i));
                list.set(i,item);
            }
        }
        return list;
    }

    /**
     * 桶排序
     * @param list 需要排序的数组
     * @return 排序成功的数组
     */
    public ArrayList<Integer> bucketSort(ArrayList<Integer> list){
        //遍历数组取出最大值max，用于后续制定桶的大小
        int max = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            max = max<list.get(i)?list.get(i):max;
        }
        //指定bucket桶
        ArrayList<Integer> bucket = new ArrayList<>();
        for (int i = 0; i <= max; i++) {
            bucket.add(0);
        }

        log(bucket,"桶：");

        for (int i = 0; i < list.size(); i++) {
            int item = list.get(i);
            int count = bucket.get(item);
            bucket.set(item,count+1);
        }

        ArrayList<Integer> orderedList = new ArrayList<>();
        for (int i = 0; i < bucket.size(); i++) {
            int item = bucket.get(i);
            switch (item){
                case 0:
                    break;
                case 1:
                    orderedList.add(i);
                    break;
                default:
                    for (int j = 0; j < item; j++) {
                        orderedList.add(i);
                    }
                    break;
            }
        }
        return orderedList;
    }

    /**
     * 插入排序
     * @param list 需要排序的数组
     * @return 排序成功的数组
     */
    public ArrayList<Integer> insertSort(ArrayList<Integer> list){
        //将数组list分为有序数组orderedList和无序数组unOrderedList
        ArrayList<Integer> orderedList = new ArrayList<>(list.subList(0,1));
        ArrayList<Integer> unOrderedList = new ArrayList<>(list.subList(1,list.size()));

        int n = unOrderedList.size();//获取无序数组的size，用来做循环
        for (int i = 0; i < n; i++) {//做一个n次循环
            int item = unOrderedList.get(0);//获取无序数组的第一个元素item
            for (int j = 0; j < orderedList.size(); j++) {//遍历有序数组
                if (item <= orderedList.get(j)){//比较有序数组内元素和item大小
                    orderedList.add(j,item);//如果item小于等于当前元素则插入到当前元素的前面
                    break;//已经完成本次插入，可以跳出当前插入的循环，进行下一次插入
                } else if (j == (orderedList.size()-1)){//如果到了最后一位，并且不满足上面之前“小于等于”的判断条件，则插入到有序组的末尾
                    orderedList.add(item);
                }
            }
            log(orderedList,"第"+ (i+1) +"次插入：");
            unOrderedList.remove(0);//第一个元素已经插入完毕，删除该元素
        }
        return orderedList;
    }


    /**
     * 归并排序
     * @param list 需要排序的数组
     * @return 排序成功的数组
     */
    private static ArrayList<Integer> split(ArrayList<Integer> list){
        log(list,"拆分结果：");
        if (list.size() <= 1){//如果list只有一个元素或者小于一个元素，说明已经拆分到最后一步，可以直接返回list
            return list;
        }
        int i = list.size()/2;//取list大小一半的值，为了平均拆分list
        ArrayList<Integer> leftList = split(new ArrayList<>(list.subList(0,i)));//拆分后左侧的list
        ArrayList<Integer> rightList = split(new ArrayList<>(list.subList(i,list.size())));//拆分后右侧的list
        return merge(leftList,rightList);
    }

    /**
     * 将两个有序数组有序合并为一个数组
     * @param leftList 第一个有序数组
     * @param rightList 第二个有序数组
     * @return 合并的数组
     */
    private static ArrayList<Integer> merge(ArrayList<Integer> leftList,ArrayList<Integer> rightList){
        int size = leftList.size() + rightList.size();//此为合并后数组的大小，也是下面循环的次数
        ArrayList<Integer> reList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (leftList.size() < 1){//如果leftList内没有元素，则将rightList所有元素直接加入到reList中，并结束循环
                reList.addAll(rightList);
                break;
            } else if (rightList.size() < 1){//如果rightList内没有元素，则将leftList所有元素直接加入到reList中，并结束循环
                reList.addAll(leftList);
                break;
            } else {//两个list中均有元素，则进行比较，将较小的加入到reList中
                if (leftList.get(0) > rightList.get(0)){
                    reList.add(rightList.get(0));
                    rightList.remove(0);
                } else {
                    reList.add(leftList.get(0));
                    leftList.remove(0);
                }
            }
        }
        return reList;
    }

    /**
     * 打印数组
     * @param list 要打印的数组
     * @param string 打印前缀
     */
    private static void log(ArrayList<Integer> list,String string){
        String s = "";
        for (int m = 0; m < list.size(); m++) {
            if (m == 0){
                s = list.get(0)+"";
            } else {
                s = s + "," + (list.get(m)+"");
            }
        }
        if (string == null){
            System.out.println("--打印数组：" + s);
        } else {
            System.out.println(string + s);
        }
    }

    //打印int[]数组
    private static void printInt(String s, int[] ints){
        StringBuilder println = new StringBuilder();
        println.append("{");
        for (int anInt : ints) {
            println.append(anInt);
            println.append(",");
        }
        println.delete(println.length()-1,println.length());
        println.append("}");
        System.out.println(s + "ints = " + println);
    }

    //冒泡排序
    private static void bubleSort(int[] ints){
        printInt("冒泡排序数据处理前",ints);
        long start = System.currentTimeMillis();
        for (int i = 1; i < ints.length; i++) {
            for (int j = 0; j < ints.length-i; j++){
                if (ints[j]>ints[j+1]){
                    int changeValue = ints[j];
                    ints[j] = ints[j+1];
                    ints[j+1] = changeValue;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("冒泡排序耗时"+(end-start)+"毫秒");
        printInt("冒泡排序数据处理后",ints);
    }

    //插入排序
    private static void insertSort(int[] ints){
        printInt("插入排序数据处理前",ints);
        long start = System.currentTimeMillis();
        for (int i = 1; i < ints.length; i++) {
            int value = ints[i];
            for (int j = i;j > 0;j--){
                if (ints[j-1]>value){
                    ints[j] = ints[j-1];
                    ints[j-1] = value;
                } else {
                    break;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("插入排序耗时"+(end-start)+"毫秒");
        printInt("插入排序数据处理后",ints);
    }

    //归并排序
    private static void mergeSort(){

    }
}
