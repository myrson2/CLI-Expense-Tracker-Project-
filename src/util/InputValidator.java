package util;

import java.util.Scanner;

public class InputValidator {
    private static Scanner scan = new Scanner(System.in);

    public static String readString(String str){
        System.out.print(str);
        return scan.nextLine();
    }

    public static int readInt(String str){
        System.out.print(str);
        return scan.nextInt();
    }

    public static double readDouble(String str){
        System.out.print(str);
        return scan.nextDouble();
    }
}
