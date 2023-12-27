package com.ll;

public class my_class {
  public static int run(String exp) {
    boolean needToMultiply = exp.contains("*");
    boolean needToPlus = !needToMultiply;
    boolean needToPlus2 = false;
    String[] bits = null;
    String[] bits2 = null;

    if (needToPlus) {
      exp = exp.replaceAll("\\- ", "\\+ \\-");

      bits = exp.split(" \\+ ");

      int sum = 0;

      for (int i = 0; i < bits.length; i++) {
        sum += Integer.parseInt(bits[i]);
      }

      return sum;
    } else if (needToMultiply) {

      int rs = 1;
      needToPlus2 = exp.contains("+");

      if (needToPlus2) {
        bits = exp.split(" \\+ ");
        bits2 = bits[1].split(" \\* ");

        int bits1 = Integer.parseInt(bits[0]);

        for (int j = 0; j < bits2.length; j++) {
          rs *= Integer.parseInt(bits2[j]);
        }
        return bits1 + rs;
      } else {
        String[] bits0 = exp.split(" \\* ");

        for (int i = 0; i < bits0.length; i++) {
          rs *= Integer.parseInt(bits0[i]);
        }
        return rs;
      }
    }
    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }
}