# WineMall : 와인 쇼핑몰
MSA 구조의 쇼핑몰로 와인을 판매, 예약하는 쇼핑몰 서비스

<br><br>

# 사용 기술
* Language: Kotlin 1.9.24
* Framework: Springboot 3.3.1
* DB: postgresql
* ORM: JPA

# MSA 구조
![제목 없는 다이어그램 drawio (4)](https://github.com/user-attachments/assets/eb8f6405-203e-4624-b499-5056db764dd4)
* UserService:         회원정보, 로그인, 회원가입 등을 관리
* ProductService:      상품의 원산지, 수량 등의 정보 관리
* OrderService:        상품 주문 및 예약, 주문내역 관리
* Order-Orchestration: 상품 주문을 위한 분산 트랜잭션 관리
* CouponService:       쿠폰 관리

# ERD
![wineshop (1)](https://github.com/user-attachments/assets/a33f33d0-b006-45fd-85b0-5a3fefd888da)
<br>
link: https://www.erdcloud.com/d/DDk7wp6HMfjfkxgFH


# 주문 서비스
wineMall 프로젝트는 MSA 구조로 각 도메인은 각각의 DB를 바라보고 있기 때문에 주문 시, productService의 product table, CouponService의 coupon table의 데이터 일관성도 관리해야 합니다. <br>
분산 트랜젝션을 적용하는 보편적인 방법으로는 Two Phase Commit Protocol(2PC), Saga Pattern이 존재하한다. 이 프로젝트에서는 로그 관리에 이점이 있으며 시나리오 변경에 용이한 Orchestration-Based SAGA 패턴 방식을 선택하였습니다.


## ORDER FINITE STATE MACHINE

![image](https://github.com/user-attachments/assets/f21a2608-3059-4659-9a5d-eed000c37f03)




- ORDER_PENDING (주문 대기)
  - 설명: 사용자가 주문을 생성하면, 주문은 처음에 대기 상태로 설정
  - 다음 상태: CHECK_STOCK
- CHECK_STOCK (재고 확인)
  - 설명: product-service에서 주문된 상품의 재고를 확인
  - 성공 시: 재고가 충분하다면 주문 상품 개수만큼 재고를 차감하고, 쿠폰이 있으면 APPLY_COUPON으로, 쿠폰이 없다면 바로 ORDER_COMPLETED 상태로 전이
  - 실패 시: 재고가 부족하다면 주문은 CANCELED 상태로 전환
- APPLY_COUPON (쿠폰 적용)
  - 설명: coupon-service에서 사용 가능한 쿠폰을 확인하고, 사용 가능할 경우 쿠폰을 적용하여 쿠폰 사용 여부를 Y로 변경
  - 성공 시: 쿠폰 적용에 성공하면 ORDER_COMPLETED 상태로 전이
  - 실패 시: 쿠폰 적용에 실패하면, 재고 롤백이 발생하고 주문은 CANCELED 상태로 전환
- ORDER_COMPLETED (주문 완료)
  - 설명: 주문이 성공적으로 완료된 상태

<br>
<br>
<br>

# 쿠폰 서비스
쿠폰 서비스는 대량의 쿠폰을 발급하고, 사용자들에게 선착순으로 쿠폰을 할당하는 기능을 제공합니다. 이 서비스는 Redis를 사용하여 분산 환경에서의 락을 통해 동시성 문제를 해결하고, 안정적으로 쿠폰을 발급할 수 있도록 설계되었습니다.

## COUPON

## **주요 기능**
##### 대량 쿠폰 발급

대량의 쿠폰을 발급하고 이를 관리할 수 있는 기능을 제공합니다.

##### 선착순 쿠폰 발급

Redis의 분산락을 이용하여 다수의 사용자들이 동시에 쿠폰을 요청하더라도, 선착순으로 쿠폰을 발급할 수 있도록 구현하였습니다.

동시에 여러 사용자가 쿠폰을 발급받을 때 발생할 수 있는 Race Condition을 방지합니다.

#### 대기열 관리

대기열에 추가된 사용자는 쿠폰이 추가로 발급되거나 다른 사용자가 취소한 쿠폰을 순서대로 받을 수 있습니다.

