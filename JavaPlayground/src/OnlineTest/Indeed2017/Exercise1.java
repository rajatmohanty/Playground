package Indeed2017;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Scanner;

/**
 * Created by Kray on 2017/5/13.
 */

/*
There are N balls labeled with the first N uppercase letters. The balls have pairwise distinct weights.

You are allowed to ask at most Q queries. In each query, you can compare the weights of two balls (see Input/Output section for details).

Sort the balls in the ascending order of their weights.

Constraints
(N,Q)=(26,1000), (26,100), or (5,7).
Partial Score
There are three testsets. Each testset is worth 100 points.

In testset 1, N=26 and Q=1000.
In testset 2, N=26 and Q=100.
In testset 3, N=5 and Q=7.

 */


public class Exercise1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        //放的是前 N 个大写字母对应的位置（数字）
        char[] chars = new char[N];
        for (int i = 0; i < N; i++) {
            chars[i] = (char) (i + 'A');
        }
        int queryNum = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i; j <= N - 1; j++) {
                if (queryNum++ > Q) {
                    break;
                }
                char c1 = chars[j];
                char c2 = chars[j + 1];
                //Output
                System.out.printf("? %c %c\n", c1, c2);
                //Input
                try {
                    char c = (char) System.in.read();
                    if (c == '>') {
                        chars[j] = c2;
                        chars[j + 1] = c1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.print("! ");
        for (int i = 0; i < N; i++) {
            System.out.print(chars[i]);
        }
    }
}
