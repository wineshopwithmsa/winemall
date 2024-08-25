## ORDER FINITE STATE MACHINE

![image](https://github.com/user-attachments/assets/f21a2608-3059-4659-9a5d-eed000c37f03)


- ODER_PENDING: 주문이 들어옴
- CHECK_STOCK
  - product-service에서 재고를 체크하고 주문이 가능하면 주문 상품 개수만큼 뺸다
  - 재고 주문이 불가능하면 주문을 canceled 상태로 변경한다
- APPLY_COUPON
  - coupon-service에서 쿠폰을 사용 가능하면 쿠폰 사용여부를 Y로 바꾼다
  - 쿠폰 사용이 불가능하면 상품 재고를 롤백시키고 주문을 canceled 상태로 변경한다
