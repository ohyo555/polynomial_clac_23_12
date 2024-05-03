## TDD 다항식 계산기

### 💡목표💡
- 3 * 1 + (1 - (4 * 1 - (1 - 1)))

- 주요 클래스
![image](https://github.com/ohyo555/polynomial_clac_23_12/assets/153146836/bdb1a56f-8bbf-4838-9574-6f47e5af5946)
  * Calc 클래스: 다항식 계산 로직이 구현된 클래스
    + run(String exp): 주어진 수식을 계산하여 결과 반환
    + _run(String exp, int depth): 내부적으로 사용되는 재귀적인 계산 메서드로, 괄호 등을 처리
    + findNegativeCaseBracket(String exp): 음수 처리를 위해 마이너스 괄호를 찾고 변경
    + 등등
  * CalcTest 클래스: JUnit을 사용하여 Calc 클래스의 기능을 테스트하는 테스트 케이스 구현
![image](https://github.com/ohyo555/polynomial_clac_23_12/assets/153146836/9ef3832f-b761-47d2-85ff-d385d9020ebb)
- 기능
  * 사칙 연산 및 괄호 처리: 덧셈, 뺄셈, 곱셈 연산을 수행하며 괄호를 적절히 처리하여 계산
  * 음수 처리: 마이너스 괄호를 찾아 음수로 변환하여 계산
  * JUnit 테스트: 각 수식에 대한 계산이 정확하게 이루어지는지 테스트 케이스로 확인
