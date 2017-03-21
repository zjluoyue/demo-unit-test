package com.zjluoyue.offer;

import java.util.*;

/**
 * Created by Jia on 2017/3/10.
 */
public class Permutation {
    ArrayList<String> strList = new ArrayList<>();
    ArrayList<Character> result = new ArrayList<>();
    static int num = 1;
    public ArrayList<String> Permutation(String str) {
        if (str == null)
            return strList;
        char[] chars = str.toCharArray();
        Permutation(chars, 0, chars.length);
        Collections.sort(strList);
        return strList;
    }

    static Set<String> stringSet = new HashSet<>();

    private void Permutation(char[] chars, int i, int length) {
        if (i == length-1) {
            strList.add(String.valueOf(chars));
            // 需用set去重复
            stringSet.add(String.valueOf(chars));
            return;
        }
        for (int j = i; j < length; j++) {
            if (i == j || chars[i] != chars[j]) {
                char temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;
                Permutation(chars, i + 1, length);
                temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;
            }
        }
    }

    ArrayList Combination(char[] string)
    {
        if (string == null) return result;
        int i , length = string.length;
        for(i = 1 ; i <= length ; ++i)
            Combination(string , i);
        return result;
    }

    void Combination(char[] string ,int number)
    {
        if(number == 0)
        {
            System.out.printf("%d\t",num++);
            for (Character character : result) {
                System.out.print(character);
            }

            System.out.println();
            return;
        }
        if (string.length == 0)
            return;
        result.add(string[0]);
        Combination(Arrays.copyOfRange(string, 1, string.length), number - 1 );
        result.remove(result.size() - 1);
        Combination(Arrays.copyOfRange(string, 1, string.length) , number);
    }

    public static void main(String[] args) {
        ArrayList<String> strings = new Permutation().Permutation("acbb");
        System.out.println(strings.toString());
        System.out.println(stringSet.toString());
        char[] a = {'a', 'b', 'c'};
        ArrayList<String> re = new Permutation().Combination(a);
    }
}
