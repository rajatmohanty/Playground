import java.util.Scanner;

/**
 * Created by Kray on 2017/4/14.
 */
public class Exam_2 {

    static int[] lucky = new int[]{7, 5, 0, 89, 12, 4, 31, 54};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if(findNumber(lucky, n)){
            System.out.println(1);
        }else{
            System.out.println(0);
        }
//        for (int i : lucky) {
//            if (i == n) {
//                System.out.println(1);
//                return;
//            }
//        }
//        System.out.println(0);
    }

    private static boolean findNumber(int[] nums, int n){
        if(nums.length == 0){
            return false;
        }
        if(nums.length == 1){
            return nums[0] == n;
        }else{
            int []ahalf = new int[nums.length / 2];
            int []bhalf = new int[nums.length - nums.length / 2];
//            for (int i = 0; i < nums.length / 2; i++) {
//                ahalf[i] = nums[i];
//            }
//            for (int i = nums.length / 2; i < nums.length; i++) {
//                bhalf[i - nums.length / 2] = nums[i];
//            }
            System.arraycopy(nums, 0, ahalf, 0, nums.length / 2);
            System.arraycopy(nums, nums.length - nums.length / 2, bhalf, 0, nums.length - nums.length / 2);
            return findNumber(ahalf, n) && findNumber(bhalf, n);
        }
    }

}
