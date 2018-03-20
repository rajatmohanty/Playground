package Indeed2017;

import java.util.Scanner;

/**
 * Created by Kray on 2017/5/13.
 */

/*
Problem Statement
A binary image is an image consisting of black and white pixels.

You are given a binary image with a height of H pixels and a width of W pixels as pi,j, a rectangular array of H×W characters. pi,j corresponds to the pixel at the i-th row from the top and j-th column from the left in the binary image. If pi,j = ., the corresponding pixel is white; if pi,j = #, the corresponding pixel is black.

Additionally, you are also given two integers A and B. Your task is to produce a new image with a height of A×H pixels and a width of B×W pixels, by arranging A×B copies of the given image in A rows and B columns. Output the obtained image in the same format as the input.

Constraints
1≤H≤10
1≤W≤10
1≤A≤10
1≤B≤10
pi,j is either . or #.
 */
public class Exam1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int A = scanner.nextInt();
        int B = scanner.nextInt();
        char[][] originalImage = new char[H][W];
        for (int i = 0; i < H; i++) {
            String temp = scanner.next();
            for (int j = 0; j < W; j++) {
                originalImage[i][j] = temp.charAt(j);
            }
        }

        for (int i = 0; i < A * H; i++) {
            for (int j = 0; j < B * W; j++) {
                System.out.print(originalImage[i % H][j % W]);
            }
            if (i != A * H - 1) {
                System.out.println();
            }
        }
    }

}
