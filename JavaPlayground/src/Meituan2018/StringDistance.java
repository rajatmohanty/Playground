package Meituan2018;

import java.util.Scanner;

/**
 字符串距离
 时间限制：C/C++语言 2000MS；其他语言 4000MS
 内存限制：C/C++语言 65536KB；其他语言 589824KB
 题目描述：
 给出两个相同长度的由字符 a 和 b 构成的字符串，定义它们的距离为对应位置不同的字符的数量。如串”aab”与串”aba”的距离为 2；串”ba”与串”aa”的距离为 1；串”baa”和串”baa”的距离为 0。下面给出两个字符串 S 与 T，其中 S 的长度不小于 T 的长度。我们用|S|代表 S 的长度，|T|代表 T 的长度，那么在 S 中一共有|S|-|T|+1 个与 T 长度相同的子串，现在你需要计算 T 串与这些|S|-|T|+1 个子串的距离的和。

 输入
 第一行包含一个字符串 S。

 第二行包含一个字符串 T。

 S 和 T 均由字符 a 和 b 组成，1 ≤ |T| ≤ |S| ≤105 。

 输出
 输出对应的答案。


 样例输入
 aab

 aba

 样例输出
 2


 Hint
 Input Sample 2
 aaabb
 bab
 Output Sample 2
 5
 在样例 2 中，”aaa”与”bab”的距离为 2，”aab”与”bab”的距离为 1，”abb”与”bab”的距离为 2，
 所以最后答案为 5。
 */

public class StringDistance {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String s = scanner.nextLine();
        String t = scanner.nextLine();

        int result = 0;

        int l1 = s.length();
        int l2 = t.length();

        for (int i = 0; i < l1 - l2 + 1; i++) {
            result += distance(s.substring(i, i + l2), t);
        }

        System.out.print(result);
    }

    static int distance(String a, String b) {
        if (a.length() != b.length()) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                result++;
            }
        }
        return result;
    }

}
