package Indeed2017;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Scanner;

/**
 * Created by Kray on 2017/5/13.
 */

/*
Problem Statement
We have N ingredients, numbered 1 through N, used in making medicines. We will compound two medicines using these ingredients. Each medicine will be made by mixing K ingredients chosen from the N ingredients we have. Here, it is not possible to use an ingredient in both medicines at the same time.

There are M rules that decide the efficacy of a medicine. The i-th rule is represented by a tuple of three integers (ai,bi,ui). Based on these rules, the efficacy of a medicine is calculated as below:

(efficacy) = (the sum of ui over all i such that both ingredients ai and bi are used in the medicine)
Here, if there does not exist i such that both ingredients ai and bi are used in the medicine, the efficacy of the medicine is 0.

Find the maximum possible sum of the efficacies of the two medicines that we will compound.

Constraints
All input values are integers.
4≤N≤8
2≤K≤N⁄2
1≤M≤N(N−1)⁄2
1≤ai<bi≤N
All pairs (ai,bi) are distinct.
1≤ui≤100
 */
public class Exam2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int M = scanner.nextInt();
        int[] ai = new int[M];
        int[] bi = new int[M];
        int[] ui = new int[M];
        for (int i = 0; i < M; i++) {
            ai[i] = scanner.nextInt();
            bi[i] = scanner.nextInt();
            ui[i] = scanner.nextInt();
        }

        int realMaxSum = 0;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j <= i; j++) {
                if (ui[j] < ui[i]) {
                    int t = ui[i];
                    ui[i] = ui[j];
                    ui[j] = t;

                    t = bi[i];
                    bi[i] = bi[j];
                    bi[j] = t;

                    t = ai[i];
                    ai[i] = ai[j];
                    ai[j] = t;
                }
            }
        }

        for (int p = 0; p < M; p++) {
            ArrayList<Integer> medicineA = new ArrayList<>();
            int i = p;
            int sumA = 0;
            ArrayList<Integer> selectedUi = new ArrayList<>();
            while (i < M) {
                if (medicineA.size() + (medicineA.contains(ai[i]) ? 0 : 1) + (medicineA.contains(bi[i]) ? 0 : 1) <= K) {
                    sumA += ui[i];
                    if (!medicineA.contains(ai[i])) {
                        medicineA.add(ai[i]);
                    }
                    if (!medicineA.contains(bi[i])) {
                        medicineA.add(bi[i]);
                    }
                    selectedUi.add(i);
                }
                i++;
            }

            i = 0;
            int sumB = 0;
            ArrayList<Integer> tempB = new ArrayList<>();
            tempB.addAll(medicineA);
            ArrayList<Integer> medicineB = new ArrayList<>();
            while (i < M) {
//                System.out.println(ai[i] + " , " + bi[i] + " , " + ui[i]);
                if (selectedUi.contains(i)) {
                    i++;
                    continue;
                }
                System.out.println(tempB.contains(ai[i]));
                System.out.println(tempB.contains(bi[i]));
                System.out.println(ai[i] + ", " + bi[i]);
                if (tempB.contains(ai[i]) || tempB.contains(bi[i])) {
                    i++;
                    continue;
                }
                if (medicineB.size() + (medicineB.contains(ai[i]) ? 0 : 1) + (medicineB.contains(bi[i]) ? 0 : 1) <= K) {
                    sumB += ui[i];
                    if (!medicineB.contains(ai[i])) {
                        medicineB.add(ai[i]);
                    }
                    if (!medicineB.contains(ai[i])) {
                        medicineB.add(bi[i]);
                    }
                }
                i++;
            }

            if (sumA + sumB > realMaxSum) {
                realMaxSum = sumA + sumB;
            }
        }

        System.out.print(realMaxSum);
    }


}
