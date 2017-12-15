public class TableDriven {

    public static void main(String[] args) {
        int[] scores = new int[]{60, 70, 80, 90, 100};
        String[] grades = new String[]{"F", "D", "C", "B", "A"};

        int score = 100;

        int maxGradeLevel = grades.length - 1;
        int gradeLevel = 0;
        String currentGrade = "A";

        while (gradeLevel < maxGradeLevel) {
            if (score < scores[gradeLevel]) {
                currentGrade = grades[gradeLevel];
                break;
            }
            gradeLevel++;
        }
        System.out.println("Grade " + currentGrade + " , Score: " + score);

    }

}
