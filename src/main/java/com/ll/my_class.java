package com.ll; // 패키지

public class my_class {

  public static boolean recursionDebug = true; // 내가 디버그 모드를 켜겠다 할때는 true로 변경

  public static int runCallCount = 0;

  public static int run(String exp) { // -(10 + 5)
    runCallCount++;

    exp = exp.trim();
    exp = stripOuterBracket(exp);

    // 음수괄호 패턴이면, 기존에 갖고있던 해석 패턴과는 맞지 않으니 패턴을 변경
    int[] pos = null;
    while ((pos = findNegativeCaseBracket(exp)) != null) {
      exp = changeNegativeBracket(exp, pos[0], pos[1]);
    }
    exp = stripOuterBracket(exp);
    if (recursionDebug) {
      System.out.printf("exp(%d) : %s\n", runCallCount, exp);
    }

    // 연산기호가 없으면 바로 리턴
    if (!exp.contains(" ")) return Integer.parseInt(exp);

    boolean needToMultiply = exp.contains(" * ");
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");

    boolean needToCompound = needToMultiply && needToPlus;
    boolean needToSplit = exp.contains("(") || exp.contains(")");

    if (needToSplit) {  // -(10 + 5)

      exp = exp.replaceAll("- ", "\\+ -");
      int splitPointIndex = findSplitPointIndex(exp);

      String firstExp = exp.substring(0, splitPointIndex);
      String secondExp = exp.substring(splitPointIndex + 1);

      char operator = exp.charAt(splitPointIndex);

      exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);

      return Calc.run(exp);

    } else if (needToCompound) {
      String[] bits = exp.split(" \\+ ");

      return Calc.run(bits[0]) + Calc.run(bits[1]); // TODO -> 정수형이면 연산못해서 Calc.run으로 바꿈
    }
    if (needToPlus) {
      exp = exp.replaceAll("\\- ", "\\+ \\-");

      String[] bits = exp.split(" \\+ ");

      int sum = 0;

      for (int i = 0; i < bits.length; i++) {
        sum += Integer.parseInt(bits[i]);
      }

      return sum;
    } else if (needToMultiply) {
      String[] bits = exp.split(" \\* ");

      int rs = 1;

      for (int i = 0; i < bits.length; i++) {
        rs *= Integer.parseInt(bits[i]);
      }
      return rs;
    }

    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }

  private static String changeNegativeBracket(String exp, int startPos, int endPos) {
    String head = exp.substring(0, startPos);
    String body = "(" + exp.substring(startPos + 1, endPos + 1) + " * -1)";
    String tail = exp.substring(endPos + 1);

    exp = head + body + tail;

    return exp;
  }

  private static int[] findNegativeCaseBracket(String exp) {
    for (int i = 0; i < exp.length() - 1; i++) {
      if (exp.charAt(i) == '-' && exp.charAt(i + 1) == '(') { // -(로 된 애들을 찾으려고
        // 어? 마이너스 괄호 찾았다
        int bracketCount = 1;

        for (int j = i + 2; j < exp.length(); j++) { // -(그 다음 인덱스부터 찾아야하니까 i + 2로 시작해서
          char c = exp.charAt(j);

          if (c == '(') {
            bracketCount++;
          } else if (c == ')') {
            bracketCount--;
          }

          if (bracketCount == 0) { // 그걸 정수 배열 형태로 담아줘
            return new int[]{i, j};
          }
        }
      }
    }
    return null;
  }

  private static int findSplitPointIndexBy(String exp, char findChar) {
    int bracketCount = 0;

    for (int i = 0; i < exp.length(); i++) {
      char c = exp.charAt(i);

      if (c == '(') {
        bracketCount++;
      } else if (c == ')') {
        bracketCount--;
      } else if (c == findChar) {
        if (bracketCount == 0) return i;
      }
    }
    return -1;
  }

  private static int findSplitPointIndex(String exp) {
    // - 인 경우 먼저 분리시키고 그다음 + 확인
    int index = findSplitPointIndexBy(exp, '+'); // + 인 경우
    if (index >= 0) return index;

    return findSplitPointIndexBy(exp, '*');
  }

  private static String stripOuterBracket(String exp) {
    if (exp.charAt(0) == '(' && exp.charAt(exp.length() - 1) == ')') { // exp의 처음이 (이고 마지막이 )일때
      int bracketCount = 0; // bracketCount 0

      for (int i = 0; i < exp.length(); i++) {
        if (exp.charAt(i) == '(') {
          bracketCount++;
        } else if (exp.charAt(i) == ')') {
          bracketCount--;
        }

        if (bracketCount == 0) {
          if (exp.length() == i + 1) {
            return stripOuterBracket(exp.substring(1, exp.length() - 1));
          }

          return exp;
        }
      }
    }
    return exp;
  }
}
