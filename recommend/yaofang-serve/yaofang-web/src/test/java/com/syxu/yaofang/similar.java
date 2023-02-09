package com.syxu.yaofang;

import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.RatcliffObershelp;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.text.similarity.FuzzyScore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest
public class similar {


    @Test
    public void similar(){
        String str1="重复发烧,咳嗽5天";
        String str2="头痛发热咳嗽";
        String str3="风温卫气同病，痰热蕴肺";
        String str4="风温";
//        RatcliffObershelp ratcliffObershelp = new RatcliffObershelp();
//        // substitution of s and t
//        System.out.println(ratcliffObershelp.similarity(str1,str2));
//
        JaroWinkler jaroWinkler = new JaroWinkler();//相似度较高
        // substitution of s and t
        System.out.println(jaroWinkler.similarity(str1, str2));
//        System.out.println(jaroWinkler.similarity(str3,str4));

        System.out.println(FuzzySearch.tokenSetRatio(str1, str2));//首先取集合(去掉重复词)，然后全匹配，对顺序不敏感，第二个字符串包含第一个字符串就100
        System.out.println(FuzzySearch.tokenSetRatio(str4, str3));//首先取集合(去掉重复词)，然后全匹配，对顺序不敏感，第二个字符串包含第一个字符串就100
        System.out.println(FuzzySearch.tokenSortRatio(str1, str2));//首先做排序，然后全匹配，对顺序不敏感(也就是更换单词位置之后，相似度依然会很高)
        System.out.println(FuzzySearch.tokenSortRatio(str3, str4));//首先做排序，然后全匹配，对顺序不敏感(也就是更换单词位置之后，相似度依然会很高)
        System.out.println(FuzzySearch.tokenSortPartialRatio(str1, str2));//首先做排序，然后搜索匹配(部分匹配)，对顺序不敏感
        System.out.println(FuzzySearch.tokenSortPartialRatio(str3, str4));//首先做排序，然后搜索匹配(部分匹配)，对顺序不敏感
        System.out.println(FuzzySearch.tokenSetPartialRatio(str1, str2));//首先取集合，然后搜索匹配(部分匹配)，对顺序不敏感
        System.out.println(FuzzySearch.tokenSetPartialRatio(str3, str4));//首先取集合，然后搜索匹配(部分匹配)，对顺序不敏感
    }

    @Test
    public  void levenshtein() {
        String str1="风温卫气同病，痰热蕴肺";
        String str2="风温";
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        System.out.println("差异步骤："+dif[len1][len2]);
        //计算相似度
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        System.out.println("相似度："+similarity);
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }


}
