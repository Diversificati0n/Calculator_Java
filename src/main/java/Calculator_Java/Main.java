package Calculator_Java;

import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String exp = scn.nextLine();

        String result = calc(exp);
        System.out.println(result);
    }

    public static String calc(String input) {
        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};

        int actionIndex = -1;
        for (int i = 0; i < actions.length; i++) {
            if (input.contains(actions[i])) {
                actionIndex = i;
                break;
            }
        }

        if (actionIndex == -1) {
            return "Некорректное выражение";
        }

        String[] data = input.split(regexActions[actionIndex]);

        if (data.length != 2) {
            return "Некорректное количество операндов";
        }

        if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
            int a, b;
            boolean isRoman = converter.isRoman(data[0]);
            try {
                if (isRoman) {
                    a = converter.romanToInt(data[0]);
                    b = converter.romanToInt(data[1]);
                    if (a < 1 || a > 10 || b < 1 || b > 10) {
                        return "Римские числа от I до X";
                    }
                } else {
                    a = Integer.parseInt(data[0]);
                    b = Integer.parseInt(data[1]);
                    if (a < 1 || a > 10 || b < 1 || b > 10) {
                        return "Арабские числа от 1 до 10";
                    }
                }

                int result;
                switch (actions[actionIndex]) {
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "*":
                        result = a * b;
                        break;
                    default:
                        if (b == 0) {
                            return "Деление на ноль";
                        }
                        result = a / b;
                        break;
                }

                if (isRoman) {
                    if (result <= 0) {
                        return "Некорректный результат римских чисел";
                    }
                    return converter.intToRoman(result);
                } else {
                    return String.valueOf(result);
                }
            } catch (NumberFormatException e) {
                return "Некорректные числа";
            }
        } else {
            return "Неверный формат";
        }
    }
}

class Converter {
    TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    public Converter() {
        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);
        romanKeyMap.put('L', 50);
        romanKeyMap.put('C', 100);
        romanKeyMap.put('D', 500);
        romanKeyMap.put('M', 1000);

        arabianKeyMap.put(1000, "M");
        arabianKeyMap.put(900, "CM");
        arabianKeyMap.put(500, "D");
        arabianKeyMap.put(400, "CD");
        arabianKeyMap.put(100, "C");
        arabianKeyMap.put(90, "XC");
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(9, "IX");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");
    }

    public boolean isRoman(String number) {
        return romanKeyMap.containsKey(number.charAt(0));
    }

    public String intToRoman(int number) {
        String roman = "";
        int arabianKey;
        do {
            arabianKey = arabianKeyMap.floorKey(number);
            roman += arabianKeyMap.get(arabianKey);
            number -= arabianKey;
        } while (number != 0);
        return roman;
    }

    public int romanToInt(String s) {
        int end = s.length() - 1;
        char[] arr = s.toCharArray();
        int arabian;
        int result = romanKeyMap.get(arr[end]);
        for (int i = end - 1; i >= 0; i--) {
            arabian = romanKeyMap.get(arr[i]);
            if (arabian < romanKeyMap.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;
    }
}
