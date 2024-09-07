# WineMall : 와인 쇼핑몰
쿠폰 발급 서비스

* CouponService:       쿠폰 관리
  <br><br>

# 사용 기술
* Language: Kotlin 1.9.24
* Framework: Springboot 3.3.1
* DB: postgresql,redis
* ORM: JPA

# ERD
![wineshop (1)](https://github.com/user-attachments/assets/a33f33d0-b006-45fd-85b0-5a3fefd888da)


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
