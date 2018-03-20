package iQiyi2017;

import java.util.Scanner;

/**
 * Created by Kray on 2017/5/14.
 */
public class Exam1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[][] array = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = scanner.nextInt();
            }
        }
        int up = 0;
        int down = m - 1;
        int left = 0;
        int right = n - 1;
        int count = 1;
        while (up <= down && left <= right) {
            for (int i = left; i <= right; i++) {
                System.out.print(array[up][i]);
                if(count != m * n) {
                    System.out.print(" ");
                }
                count++;
            }
            up++;
            for (int i = up; i <= down; i++) {
                System.out.print(array[i][right]);
                if(count != m * n) {
                    System.out.print(" ");
                }
                count++;
            }
            right--;
            if (up <= down) {
                for (int i = right; i >= left; i--) {
                    System.out.print(array[down][i]);
                    if(count != m * n) {
                        System.out.print(" ");
                    }
                    count++;
                }
            }
            down--;
            if (left <= right) {
                for (int i = down; i >= up; i--) {
                    System.out.print(array[i][left]);
                    if(count != m * n) {
                        System.out.print(" ");
                    }
                    count++;
                }
            }
            left++;
        }
    }

}
