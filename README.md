# WineMall : 와인 쇼핑몰
MSA 구조의 쇼핑몰로 와인을 판매, 예약하는 쇼핑몰 서비스
* UserService:         회원정보, 로그인, 회원가입 등을 관리
* ProductService:      상품의 원산지, 수량 등의 정보 관리
* OrderService:        상품 주문 및 예약, 주문내역 관리
* Order-Orchestration: 상품 주문을 위한 분산 트랜잭션 관리
* CouponService:       쿠폰 관리
<br><br>

# 주문 서비스
wineMall 프로젝트는 MSA 구조로 각 도메인은 각각의 DB를 바라보고 있기 때문에 주문 시, productService의 product table, CouponService의 coupon table의 데이터 일관성도 관리해야 한다.
분산 트랜젝션을 적용하는 보편적인 방법으로는 Two Phase Commit Protocol(2PC), Saga Pattern이 존재하한다. 이 프로젝트에서는 로그 관리에 이정이 있으며 시나리오 변경에 용이한 Orchestration-Based SAGA 패턴 방식을 선택하였다.


## ORDER FINITE STATE MACHINE

![image](https://github.com/user-attachments/assets/f21a2608-3059-4659-9a5d-eed000c37f03)


- ODER_PENDING: 주문이 들어옴
- CHECK_STOCK
  - product-service에서 재고를 체크하고 주문이 가능하면 주문 상품 개수만큼 뺸다
  - 재고 주문이 불가능하면 주문을 canceled 상태로 변경한다
- APPLY_COUPON
  - coupon-service에서 쿠폰을 사용 가능하면 쿠폰 사용여부를 Y로 바꾼다
  - 쿠폰 사용이 불가능하면 상품 재고를 롤백시키고 주문을 canceled 상태로 변경한다
