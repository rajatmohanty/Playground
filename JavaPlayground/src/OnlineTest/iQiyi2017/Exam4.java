package iQiyi2017;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kray on 2017/5/14.
 */

/*
0 1 100 100 100 100 100
1 0 1 100 100 100 100
100 1 0 1 100 100 100
100 100 1 0 1 100 100
100 100 100 1 0 1 100
100 100 100 100 1 0 1
100 100 100 100 100 0 1
1 1 1 1 1
1
 */
public class Exam4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] distance = new int[7][7];
        int[] energy = new int[5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                distance[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < 5; i++) {
            energy[i] = scanner.nextInt();
        }
        int damage = scanner.nextInt();

        ArrayList<Integer> beenTo = new ArrayList<>();
        int[] collect = new int[5];
        int total = 0;
        int current = 0;
        int next = -1;
        int totalTime = 0;

        beenTo.add(0);
        while (beenTo.size() < 6) {
            int min = 50000;
            current = beenTo.get(beenTo.size() - 1);
            for (int i = 0; i < 6; i++) {
                if (beenTo.contains(i)) {
                    continue;
                }
                if (distance[current][i] < min) {
                    min = distance[current][i];
                    next = i;
                }
            }
            beenTo.add(next);
            totalTime += distance[current][next];
            total += damage * totalTime * energy[next - 1];
        }
        totalTime += distance[current][next];
        for (int i = 0; i < beenTo.size(); i++) {
            System.out.print((char)('A' + beenTo.get(i)));
        }
        System.out.print("G " + totalTime + " " + total);
    }
}
