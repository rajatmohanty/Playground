package iQiyi2017;

import java.util.Scanner;

/**
 * Created by Kray on 2017/5/14.
 */
public class Exam3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int total = 0;
        for (int i = m + 1; i <= n; i++) {
            total += cal(m, i);
        }
        System.out.print(total);
    }

    //m 个袋子 n 个球 m-1 个挡板 n-1 个空隙
    public static int cal(int m, int n) {
        if (m == 1) {
            return 1;
        }
        int total = 0;
        int i = m * (m - 1) / 2;
        n = n - m;
        while (n >= i) {
            total += cal(m - 1, n);
            n -= m;
        }
        return total;
    }
}
