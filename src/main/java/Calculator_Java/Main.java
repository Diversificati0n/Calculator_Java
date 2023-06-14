package Calculator_Java;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Integer> RomanNumbers = new HashMap<>();

    static {
        RomanNumbers.put("I", 1);
        RomanNumbers.put("II", 2);
        RomanNumbers.put("III", 3);
        RomanNumbers.put("IV", 4);
        RomanNumbers.put("V", 5);
        RomanNumbers.put("VI", 6);
        RomanNumbers.put("VII", 7);
        RomanNumbers.put("VIII", 8);
        RomanNumbers.put("IX", 9);
        RomanNumbers.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (пробел после каждого знака):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Вывод:");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Вывод:");
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new Exception("Некорректное выражение");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isRoman = isRomanNumeral(operand1) && isRomanNumeral(operand2);
        boolean isArabic = isArabicNumeral(operand1) && isArabicNumeral(operand2);

        if (!isRoman && !isArabic) {
            throw new Exception("Ошибка системы счисления");
        }

        int result;
        if (isRoman) {
            int num1 = romanToArabic(operand1);
            int num2 = romanToArabic(operand2);
            result = calculate(num1, operator, num2);
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new Exception("Некорректный ввод");
            }
            return arabicToRoman(result);
        } else {
            int num1 = Integer.parseInt(operand1);
            int num2 = Integer.parseInt(operand2);
            result = calculate(num1, operator, num2);
            return Integer.toString(result);
        }
    }

    private static boolean isRomanNumeral(String input) {
        return RomanNumbers.containsKey(input);
    }

    private static boolean isArabicNumeral(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int romanToArabic(String romanNumeral) {
        return RomanNumbers.get(romanNumeral);
    }

    private static String arabicToRoman(int number) {
        StringBuilder result = new StringBuilder();
        String[] romanSymbols = { "X", "IX", "V", "IV", "I" };
        int[] arabicValues = { 10, 9, 5, 4, 1 };

        for (int i = 0; i < romanSymbols.length; i++) {
            while (number >= arabicValues[i]) {
                result.append(romanSymbols[i]);
                number -= arabicValues[i];
            }
        }

        return result.toString();
    }

    private static int calculate(int num1, String operator, int num2) throws Exception {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return num1 / num2;
            default:
                throw new Exception("Ошибка");
        }
    }
}
