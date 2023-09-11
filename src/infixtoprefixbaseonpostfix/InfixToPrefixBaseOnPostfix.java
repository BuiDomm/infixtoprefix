/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package infixtoprefixbaseonpostfix;

/**
 *
 * @author ASUS
 */
import java.util.Scanner;
import java.util.Stack;

public class InfixToPrefixBaseOnPostfix {

    public static String infixToPrefix(String infixExpression) {
        // Bước 1: Đảo ngược chuỗi biểu thức trung tố.
        String reversedInfix = new StringBuilder(infixExpression).reverse().toString();
//        System.out.println(reversedInfix);

        // Bước 2: Chuyển đổi biểu thức trung tố thành biểu thức hậu tố.
        String postfixExpression = infixToPostfix(reversedInfix);

        // Bước 3: Đảo ngược biểu thức hậu tố để có biểu thức tiền tố.
        String prefixExpression = new StringBuilder(postfixExpression).reverse().toString();
        return prefixExpression;
    }

    //=> input => đảo input => postFix => đảo ngược postFix => prefix
    public static String infixToPostfix(String infixExpression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        // Duyệt qua từng ký tự trong biểu thức trung tố đã được đảo.
        for (char ch : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                // Nếu là chữ cái hoặc số, thêm vào biểu thức hậu tố.
                postfix.append(ch);
            } else if (ch == ')') {
//                (a+b)-c => c -)b+a(
//
                // Khi gặp dấu đóng ngoặc, pop các toán tử ra cho đến khi gặp dấu mở ngoặc.
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // Loại bỏ dấu mở ngoặc.
            } else if (ch == '(') {
                // Khi gặp dấu mở ngoặc, đưa vào ngăn xếp.
                stack.push(ch);
            } else {
                // Khi gặp toán tử, xem xét độ ưu tiên và pop toán tử có độ ưu tiên cao hơn.
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    if (stack.peek() != '(') {
                        postfix.append(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(ch);
            }
        }
        // Pop tất cả các toán tử còn lại trong ngăn xếp.
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input chuỗi trung tố:");
        String infixExpression = sc.nextLine();
        String newString = "";
        for (int i = 0; i < infixExpression.length(); i++) {
            if (infixExpression.charAt(i) == '(') {
                newString = newString + ")";
            } else if (infixExpression.charAt(i) == ')') {
                newString = newString + "(";

            } else {
                newString = newString + infixExpression.charAt(i);

            }
        }
        String prefixExpression = infixToPrefix(newString);
        System.out.println("Biểu thức tiền tố: " + prefixExpression);
    }
}
