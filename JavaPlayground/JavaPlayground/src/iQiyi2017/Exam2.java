package iQiyi2017;

import java.util.Scanner;

/**
 * Created by Kray on 2017/5/14.
 */
public class Exam2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalTime = scanner.nextInt();
        int adNum = scanner.nextInt();
        int[] adTimes = new int[adNum];
        for (int a = 0; a < adNum; a++) {
            adTimes[a] = scanner.nextInt();
        }

        int realMax = 0;
        int n = 1 << adNum;
        for (int i = 1; i < n; i++) {    //从 1 循环到 2^len -1
            int temp = 0;
            for (int j = 0; j < adNum; j++) {
                if ((i & (1 << j)) != 0) {
                    temp += adTimes[j];
                }
            }
            if (temp > realMax && temp <= totalTime) {
                realMax = temp;
            }
        }

        System.out.print(realMax);
    }
}
