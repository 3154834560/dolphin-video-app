package com.example.dolphin.infrastructure.tool;

import com.example.dolphin.infrastructure.consts.StringPool;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 计算工具类
 *
 * @author 王景阳
 * @date 2022/9/19 10:51
 */
public class CalculateTool {

    /**
     * 获取等式答案
     *
     * @param equation 等式
     * @return 答案
     */
    public static BigDecimal getAnswer(String equation) {
        BigDecimal answer = null;
        boolean isFirst = true;
        int l = 0, r = 0;
        char up = StringPool.PLUS_SIGN;
        for (; r < equation.length(); r++) {
            char c = equation.charAt(r);
            if (isOperator(c)) {
                if (isFirst) {
                    answer = getNumber(equation.substring(l, r));
                    l = r + 1;
                    isFirst = false;
                } else {
                    if (isSameType(up, c) || (!isSameType(up, c) && isPlusOrMinus(c))) {
                        answer = calculate(answer, getNumber(equation.substring(l, r)), up);
                        l = r + 1;
                    } else {
                        answer = calculate(answer, getAnswer(equation.substring(l)), up);
                        break;
                    }
                }
                up = c;
            }

        }
        if (r == equation.length()) {
            if (answer != null) {
                answer = calculate(answer, getNumber(equation.substring(l, r)), up);
            } else {
                answer = getNumber(equation.substring(l, r));
            }
        }
        return answer;
    }

    /**
     * 将两个数进行计算
     *
     * @param bigDecimal1 第一个数
     * @param bigDecimal2 第二个数
     * @param operator    操作符
     * @return 结果
     */
    private static BigDecimal calculate(BigDecimal bigDecimal1, BigDecimal bigDecimal2, char operator) {
        switch (operator) {
            case StringPool.PLUS_SIGN:
                return bigDecimal1.add(bigDecimal2);
            case StringPool.MINUS_SIGN:
                return bigDecimal1.subtract(bigDecimal2);
            case StringPool.MULTIPLICATION:
                return bigDecimal1.multiply(bigDecimal2);
            default:
                return bigDecimal1.divide(bigDecimal2, 10, RoundingMode.HALF_UP);
        }
    }

    /**
     * 判断字符是否是操作符（ ‘+’，‘—’，‘x','÷'）
     */
    public static boolean isOperator(char c) {
        return c == StringPool.DIVISION_SIGN || c == StringPool.PLUS_SIGN || c == StringPool.MINUS_SIGN || c == StringPool.MULTIPLICATION;
    }

    /**
     * 判断字符是否是 ’+‘ 和 ’-‘
     */
    private static boolean isPlusOrMinus(char c) {
        return c == StringPool.PLUS_SIGN || c == StringPool.MINUS_SIGN;
    }

    /**
     * 判断两个操作符是否是同一级别
     * ’+‘ 和 ’-‘ 是同一级别
     * ’x‘ 和 ’÷‘ 是同一级别
     */
    private static boolean isSameType(char c1, char c2) {
        return (((c1 == StringPool.PLUS_SIGN || c1 == StringPool.MINUS_SIGN) && (c2 == StringPool.PLUS_SIGN || c2 == StringPool.MINUS_SIGN))
                || ((c1 == StringPool.MULTIPLICATION || c1 == StringPool.DIVISION_SIGN) && (c2 == StringPool.MULTIPLICATION || c2 == StringPool.DIVISION_SIGN)));
    }

    /**
     * 将字符串 numStr 转换为 bigDecimal
     */
    private static BigDecimal getNumber(String numStr) {
        BigDecimal bigDecimal = null;
        int percentSignNum = 0;
        int l = 0;
        for (int i = 0; i < numStr.length(); i++) {
            if (numStr.charAt(i) == StringPool.PERCENT_SIGN) {
                percentSignNum++;
                if (bigDecimal == null) {
                    bigDecimal = new BigDecimal(numStr.substring(l, i));
                }
                l = i + 1;
            }
        }
        if (bigDecimal == null) {
            return new BigDecimal(numStr);
        }
        bigDecimal = new BigDecimal("1e-" + percentSignNum * 2).multiply(bigDecimal);
        if (l < numStr.length()) {
            bigDecimal = bigDecimal.multiply(new BigDecimal(numStr.substring(l)));
        }
        return bigDecimal;
    }


}
