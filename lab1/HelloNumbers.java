import test.LeapYear;

public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        for (int i = 1;i <= 10;i++) {
            System.out.print(x + " ");
            x = x + i;
        }

        System.out.println("\n");

        String[] s = new String[] {
            new String("24430"),
            new String("2405"),
            new String("5230"),
            new String("2400"),
        };

        LeapYear.main(s);
    }
}