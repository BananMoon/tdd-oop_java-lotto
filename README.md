## TDD 원칙
- 원칙 1 - 실패하는 단위 테스트를 작성할 때까지 프로덕션 코드(production code)를 작성하지 않는다.
- 원칙 2 - 컴파일은 실패하지 않으면서 실행이 실패하는 정도로만 단위 테스트를 작성한다.
- 원칙 3 - 현재 실패하는 테스트를 통과할 정도로만 실제 코드를 작성한다.


## step2. 로또 (자동)
### 기능 요구사항
- 로또 구입 금액을 입력하면 구입 금액에 해당하는 로또를 발급해야 한다.
- 로또 1장의 가격은 1000원이다.
  - 14000원을 입력하면, 14개의 로또를 구매하는 것이다.
- 로또번호 생성기 (LottoNumberGenerator)
  -  **1~45**까지 6개의 숫자를 랜덤으로 생성한다.

### 클래스 정의 및 기능 요구사항 분리
- (테스트 X) LottoInterface : 사용자와 소통하는 인터페이스로, OutputView, InputView, LottoProgram을 호출한다.
  - [x] 사용자가 구매한 금액에 해당하는 갯수만큼 로또를 구매 요청한다.(LottoProgram.generateLottoTickets())
  - [x] 지난 주 로또 번호와 사용자의 구매 로또 matching 요청한다.(LottoProgram.matchCount(userNumber, winningNumber))
- LottoProgram : 로또 번호를 생성하고 당첨 통계를 관리한다. (주로 위임)
  - [x] 요청한 갯수만큼 로또 구매하여 반환한다. (LottoNumberGenerator에게 위임)
  - [x] (테스트 X) 로또 번호 간 매칭한다. (LottoNumbers에게 주요 로직 위임)
- (테스트 X) LottoNumberGenerator : 로또 번호를 생성하는 기기로, 로또 번호를 생성하는 역할을 한다.
  - (테스트 X) RandomGenerator : 랜덤으로 생성하는 로또 번호 생성기.
    - [x] 랜덤의 로또 번호를 생성한다.
- LottoNumbers : 로또 프로그램의 로또 번호(`LottoNumber`)를 일급 컬렉션으로 관리한다.
  - [x] 값 크기는 6개 여야 한다.
  - [x] 두 개의 LottoNumbers가 주어졌을 때 매칭되는 갯수를 반환한다.
- LottoNumber : 로또 번호
  - [x] **1~45** 사이의 숫자여야 한다.
- (테스트 X) InputView : 사용자로부터 입력을 받는 View. 사용자의 구입 금액과 지난 주 당첨 번호를 입력받는다.
  - [x] 구매 금액을 입력받는다. (기본 1000원 이상이다.)
  - [x] 지난 주 로또 번호를 입력받는다.
- (테스트 X) OutputView : 사용자에게 결과를 출력하는 view. 당첨 통계 및 수익률을 출력한다.
  - [x] 구매 금액에 따른 로또 구매 수량을 출력한다.
  - [x] 구매한 로또 수량만큼 사용자의 로또 번호를 출력한다.
  - [x] 당첨 통계를 출력한다. (LottoRankingSystem을 사용)
  - [x] 수익률을 출력한다.
- LottoRankingSystem : 로또의 등수 매기기 방식
  - [x] 로또 번호 일치 갯수에 따른 로또 당첨 등수를 반환한다.
  - [x] 로또 번호 일치 갯수가 로또 랭킹 시스템에 포함되지 않는지 체크한다.
- LottoWinningStatistics : 로또 당첨 통계
  - [x] 사용자 로또 번호와 당첨 번호를 매칭하여 당첨 통계 객체를 생성한다.
  - [x] 당첨된 로또의 금액을 계산한다.
  - [x] 인자로 전달받은 등수에 해당하는 로또의 갯수를 반환한다.

### 로또 이해
- 수익률 : 당첨 금액 / 구매 금액
- 사용자는 로또를 구매할 때 번호들을 직접 고를 수도, 랜덤으로 생성되는 번호로 추첨할 수도 있다.
- 로또 한 장 당 1000원이며, 로또 한 장에는 6개의 로또 번호를 입력한다.
- 로또 등수 매기기 규칙
  - 1등 : 로또 번호 6개 모두 일치
  - 2등 : 로또 번호 5개 + 보너스 번호 모두 일치
  - 3등 : 로또 번호 5개
  - 4등 : 로또 번호 4개 
  - 5등 : 로또 번호 3개
  - 그 외는 모두 꽝.

---

## step1. 문자열 계산기
### 기능 요구사항
- 사용자가 입력한 문자열 값에 따라 사칙연산을 수행할 수 있는 계산기를 구현해야 한다.
- 입력 문자열의 숫자와 사칙 연산 사이에는 **반드시 빈 공백 문자열**이 있다고 가정한다.
- **나눗셈의 경우 결과 값을 정수**로 떨어지는 값으로 한정한다.
- 문자열 계산기는 **사칙연산의 계산 우선순위가 아닌 입력 값에 따라 계산 순서가 결정**된다. 즉, 수학에서는 곱셈, 나눗셈이 덧셈, 뺄셈 보다 먼저 계산해야 하지만 이를 무시한다.
- 예를 들어 `2 + 3 * 4 / 2`와 같은 문자열을 입력할 경우 `2 + 3 * 4 / 2` 실행 결과인 10을 출력해야 한다.

### 구현 : 클래스와 상태 값
- CalculatorInterface
- Calculator
    - operators
    - operands
- Operators : 일급 컬렉션 (`List<Operator> operators`)
    - operators
- Operator : Enum
    - plus / minus / times / division
- Operands : 일급 컬렉션 (`List<Operands> operands`)
    - operands
- OperandNumber : 값 포장 (`Integer operand`)


## 프로그래밍 요구사항
- indent(들여쓰기) depth를 2단계에서 1단계로 줄여라.
    - depth의 경우 if문을 사용하는 경우 1단계의 depth가 증가한다. if문 안에 while문을 사용한다면 depth가 2단계가 된다.
- 메소드의 크기가 최대 10라인을 넘지 않도록 구현한다.
    - method가 한 가지 일만 하도록 최대한 작게 만들어라.
- else를 사용하지 마라.

---
# 로또
## 진행 방법
* 로또 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)
