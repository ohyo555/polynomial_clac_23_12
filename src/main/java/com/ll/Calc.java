package com.ll;

public class Calc {
  public static int run(String exp) { // 10 + (10 + 5)
    exp = exp.trim(); // exp의 양끝 공백 제거 해서 담아줌
    exp = stripOuterBracket(exp);

    // 연산기호가 없으면 바로 리턴
    if (!exp.contains(" ")) return Integer.parseInt(exp);

    boolean needToMultiply = exp.contains(" * "); // "*" 포함되어 있으면 T아니면 F
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");

    boolean needToCompound = needToMultiply && needToPlus;
    boolean needToSplit = exp.contains("(") || exp.contains(")");

    if (needToSplit) {  // "(, )"이 포함된 경우 실행

      int splitPointIndex = findSplitPointIndex(exp);   // findSplitPointIndex메서드에서 반환된 값을 넣어주고

      String firstExp = exp.substring(0, splitPointIndex);    // 0부터 반환된 값까지 문자열 잘라주고
      String secondExp = exp.substring(splitPointIndex + 1);    // 반환된값 + 1 위치부터 끝까지 잘라줘

      char operator = exp.charAt(splitPointIndex);    // exp에서 위에서 반환된 위치의 문자 변수에 넣어주고

      exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);    // 1번 식과 2번 식을 잘라준 기호에 맞게 연산

      return Calc.run(exp); // 그 값을 반환 시켜줘

    } else if (needToCompound) {
      String[] bits = exp.split(" \\+ ");

      return Integer.parseInt(bits[0]) + Calc.run(bits[1]); // TODO
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

  private static int findSplitPointIndexBy(String exp, char findChar) {   // 원하는 문자 찾아서 위치 찾기
    int bracketCount = 0;

    for (int i = 0; i < exp.length(); i++) { // 0부터 exp길이 까지 반복하는데
      char c = exp.charAt(i);   // i번째에 해당하는 문자를 c에 넣어주고

      if (c == '(') {   // 그게 (면
        bracketCount++;   // bracketCount값 1증가
      } else if (c == ')') {    // )면
        bracketCount--;     // bracketCount값 1감소
      } else if (c == findChar) {   // findChar 이면 -> 이건 밑에 findSplitPointIndex메서드의 인자값
        if (bracketCount == 0) return i;    //  bracketCount값 0
      }
    }
    return -1;    // for문에서 return안걸리면 -1 리턴시켜, ( 나오고 나서 + 나왔을때를 구분하려고, ()기준으로 나눠야하니까
  }

  private static int findSplitPointIndex(String exp) {    // 원하는 문자 찾아서 위치 찾기
    int index = findSplitPointIndexBy(exp, '+');

    if (index >= 0) return index;     // index가 0보다 크거나 같으면 index 리턴

    return findSplitPointIndexBy(exp, '*');   // 아니면 findSplitPointIndexBy메서드 실행한 값 리턴 받음(*위치)
  }

  private static String stripOuterBracket(String exp) {   // ()제거 - exp가 처음부터 (로 시작해주는 경우에
    int outerBracketCount = 0;

    while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
      outerBracketCount++;    // exp의 0번째 문자가 (이고, exp의 길이와 outer~+1값 빼준 위치의 문자가 )면 outer 1씩 증가
    }

    if (outerBracketCount == 0) return exp;   // outer~가 0이면 exp문자 리턴해줘


    return exp.substring(outerBracketCount, exp.length() - outerBracketCount);    // 그게 아니라면 exp에서 outer위치부터 전체 길이에서 outer위치 뺀만큼 잘라서 반환시켜줘
  }
}