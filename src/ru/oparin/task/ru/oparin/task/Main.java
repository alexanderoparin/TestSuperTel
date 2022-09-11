package ru.oparin.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] indexes = {"1,3-5", "2", "3-4"};
        String[] resultAfterMethod1 = sequencesOfNumbers(indexes);
        System.out.println(Arrays.toString(resultAfterMethod1));
        int[][] resultAfterMethod2 = permutationsToArrInt(resultAfterMethod1);
        System.out.println(Arrays.deepToString(resultAfterMethod2));
    }


    // второй метод из таски
    public static int[][] permutationsToArrInt(String[] stringArr) {
        List<List<Integer>> lists = new ArrayList<>();
        List<List<Integer>> permutations = new ArrayList<>();
        for (int i = 0; i < stringArr.length; i++) {
            lists.add(i, stringToIntList(stringArr[i]));
        }
        permutationHelper(permutations, new ArrayList<>(), lists);
        return permutations.stream()
                .map(l -> l.stream().mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);
    }

    // генерация всевозможных перестановок в первоначальном листе листов
    private static void permutationHelper(List<List<Integer>> resultList, List<Integer> tempList, List<List<Integer>> initLists) {
        if (tempList.size() == initLists.size()) {
            resultList.add(new ArrayList<>(tempList));
            return;
        }
        for (int i = 0; i < initLists.size(); i++) {
            if (tempList.size() != i) continue;
            for (int j = 0; j < initLists.get(i).size(); j++) {
                tempList.add(initLists.get(i).get(j));
                permutationHelper(resultList, tempList, initLists);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    // метод переводит строку в лист интов
    // "1,3,4,5" -> [1,3,4,5]
    public static List<Integer> stringToIntList(String str) {
        String[] tempArr = str.split(",");
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < tempArr.length; i++) {
            intList.add(Integer.parseInt(tempArr[i]));
        }
        return intList;

    }

    // первый метод из таски,
    // из {"1,3-5", "2", "3-4"} получаем {"1,3,4,5", "2", "3,4"}
    public static String[] sequencesOfNumbers(String[] indexes) {
        List<String> result = new ArrayList<>();
        for (String index : indexes) {
            if (index.contains(",")) {
                StringBuilder sb = new StringBuilder();
                String[] tempArr = index.split(",");
                for (int i = 0; i < tempArr.length; i++) {
                    sb.append(ifContainHyphen(tempArr[i]));
                    if (i < tempArr.length - 1) {
                        sb.append(",");
                    }
                }
                result.add(sb.toString());
            } else {
                result.add(ifContainHyphen(index));
            }
        }
        return result.toArray(new String[0]);
    }

    // метод проверяет строку на содержание дефиса, если он есть, то изменяет её
    // "3-7" -> "3,4,5,6,7"
    public static String ifContainHyphen(String str) {
        if (str.contains("-")) {
            List<Integer> intList = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            String[] tempArr = str.split("-");
            int left = Integer.parseInt(tempArr[0]);
            int right = Integer.parseInt(tempArr[1]);
            intList.add(left);
            while (left != right) {
                left++;
                intList.add(left);
            }
            for (int i = 0; i < intList.size(); i++) {
                sb.append(intList.get(i));
                if (i < (intList.size() - 1)) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        return str;
    }
}
