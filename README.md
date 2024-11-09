# java-convenience-store-precourse

- 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액 계산
  - 총 구매액은 상품별 가격과 수량을 곱하여 계산, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액 산출

- 구매 내역과 산출한 금액 정보를 영수증으로 출력

- 영수증 출력 후 추가 구매를 진행할지 또는 종료할지 선택

- 잘못된 값을 입력한 경우 IllegalArgumentException 발생
  - "[ERROR]로 시작하는 에러 메시지 출력 후 입력을 다시 받는다."

### 상품
- 이름, 가격, 수량, 프로모션 필드를 가진다.
- 프로모션 필드는 null을 가질 수 있다.

### 상품 입력
- 파일로부터 입력받는다.
- 현재 상품을 입력할 때, 이전 상품의 프로모션 유/무를 확인한다.
  - 프로모션이 있었을 경우, 이전 상품을 재고 0, 프로모션 없음으로 추가 입력
  - 프로모션이 없었을 경우 무시한다.

### 재고관리
- 상품의 존재 여부를 확인한다.
- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감한다.
- 재고를 차감함으로써 시스템은 최신 재고 상태를 유지한다.

### 출력
- 보유 상품 출력하기

### 주문 입력
- 주문 입력받기

### 주문 생성
- 검사하기
  - 빈 문자열 검사
  - [이름-수량] 패턴 검사
- ,로 주문 구분하기

### 프로모션 입력
- 진행 중인 프로모션 입력하기
- 검증하기
  - 속성의 수
  - 각 속성의 blank 검사
  - buy,get integer 전환 검사
  - start_date, end_date 날짜 전환 검사

### 구매
- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 
혜택에 대한 안내 메시지를 출력한다.
- 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 
일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.
- 존재 확인
  - 미존재 : 에러
  - 존재
    - 프로모션 여부 확인
      - 주문과 상품 비교해서 프로모션 이름 or null 가져오기 
        - 프로모션 x : 일반 상품 결제
        - 프로모션 o 
          - 고객 입력 수량 확인
            - 수량 만족 : 프로모션 재고 차감
              - 프로모션 재고 만족 : 프로모션 재고 차감
              - 프로모션 재고 미만족 : 정가 결제 여부 물어봄
                - 정가로 결제
                - 정가 결제 수량만큼 제외
            - 수량 미만족 : 안내 메시지 출력
              - 대답 입력
                - 대답 검증 (blank,Y/N)
              - 추가 구매
                - 프로모션 재고 만족 : 프로모션 재고 차감
                - 프로모션 재고 미만족 : 정가 결제 여부 물어봄
                  - 정가로 결제
                  - 정가 결제 수량만큼 제외
                - 추가 구매 x

### 프로그래밍 요구 사항
- indent depth는 2까지 허용
- 함수가 한 가지 일만 하도록 최대한 작게
- Java Enum을 적용한다.
- 함수의 길이가 10라인을 넘어가지 않도록 한다.