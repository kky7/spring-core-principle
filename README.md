# 객체 지향 설계와 스프링
## 스프링
- 좋은 객체 지향 애플리케이션을 개발할 수 있게 도와주는 프레임워크

## 좋은 객체 지향 프로그래밍이란?
- 객체 지향 프로그래밍은 프로그램을 유연하고 변경이 용이하게 만든다.
### 다형성
  - 부모 타입 참조변수는 자식을 가리킬 수 있다.
  - 자식 타입 참조변수는 부모를 가리킬 수 없다.
  - 부모 타입으로 여러 자식을 품을 수 있다. (인터페이스 부모로 설계를 해놓으면 자식 타입을 갈아끼울 수 있다.)
  - 역할과 구현으로 세상을 구분
  - 클라이언트를 변경하지 않고, 서버의 구현 기능을 유연하게 변경할 수 있다.

### 역할과 구현을 분리
  - 예> 운전자 역할이 있는상태, 자동차는 어떤 종류(구현)여도 자동차의 역할을 한다.
  - 자동차가 바뀌어도(구현) 운전자는 운전할 수 있다. (역할)
  - 새로운 자동차가 나와도 클라이언트는 새로운 자동차를 배우지 않아도 되고, 클라이언트를 바꾸지 않아도 된다.

### 역할과 구현을 분리 장점
- 클라이언트는 대상의 역할(인터페이스)만 알면 된다.
- 클라이언트는 구현 대상의 내부 구조를 몰라도 된다.
- 클라이언트는 구현 대상의 내부 구조가 변경되어도 영향을 받지 않는다.
- 클라이언트는 구현 대상 자체를 변경해도 영향을 받지 않는다.

### 인터페이스
- 역할: 인터페이스
- 구현: 인터페이스를 구현한 클래스, 구현 객체 (인터페이스에서 설계한 함수를 오버라이딩해서 구현)
- ex. MemberRepository 를 구현한 MemoryMemberRepository에서 JdbcMemberRepository로 구현 Repository 변경 (인터페이스를 사용하여 변경에 유연하고 용이하다.)

## SOLID
### SRP 단일 책임 원칙
- 한 클래스는 하나의 책임만 가져야 한다.
- 변경이 있을 때 파급 효과가 적으면 단일 책임 원칙을 잘 따른 것

### OCP 개방-폐쇄 원칙
- 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
- 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현한다.

### LSP 리스코프 치환 원칙
- 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다.

### ISP 인터페이스 분리 원칙
- 특정 클라이언트를 위한 인터페이스가 여러개의 범용 인터페이스 하나보다 낫다.

### DIP 의존관계 역전 원칙
- 추상화에 의존해야지, 구체화에 의존하면 안된다.
- 구현 클래스에 의존하지 말고, 인터페이스에 의존
- 역할에 의존하게 해야 한다는 것과 같다.
- 인터페이스에 의존해야 유연하게 구현체를 변경할 수 있다.
  
--------------------------------------------------------
  
# 예제 - 객체 지향 원리 적용
## 새로운 할인 정책 적용과 문제점
- 정액 할인 정책에서 정률 할인 정책으로 변경시
- 추상화(인터페이스), 구체클래스(할인 정책 구현 클래스) 모두 의존 한다. 즉 DIP에 위반한다.
- 할인의 역할 뿐 아니라 누구를 선택할지 책임도 가지게 된다. -> 다양한 책임을 가짐
```java
//private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
```
- DIP에 위반하지 않도록
```java
private DiscountPolicy discountPolicy;
```
- 그러나 대입된 것이 없으므로 Null pointer Exception 발생 -> DIP를 위반하지 않을려면 누군가가 대신 주입 해주어야 한다.

## 관심사 분리
- 공연을 예로 들어 로미오 역할(인터페이스)을 하는 디카프리오라는 남자 배우(구현체)가 줄리엣 역할(인터페이스)을 하는 여자 주인공(구현체)를 초빙하는 것과 같음
- 디카프리오는 공연도하고 여자 주인공도 초빙하는 **다양한 책임**을 가지게 됨.
- 역할에 맞는 배우를 지정하는 책임을 담당하는 별도의 **공연 기획자**가 필요
- 배우와 공연 기획자의 **책임을 확실히 분리**해야함. (**관심사 분리**)

### AppConfig
```java
public class AppConfig {

    private MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); // 생성자 주입
    }

    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
```
```java
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findByid(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); // 단일책임원칙을 잘 지킨 것 (할인에 대한 변경이 필요하면 할인 쪽만 고치면 된다.)
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
```
- 애플리케이션의 전체 동작을 구성, 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스
- AppConfig를 이용해서 구현체를 선택한다.
- 객체의 생성과 연결은 AppConfig가 담당
- DIP: OrderServiceImpl은 MemberRepository, DiscountPolicy 추상에만 의존한다. 구체 클래스를 몰라도된다.
- 관심사 분리: 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
- 할인정책을 변경할려면 아래 부분만 변경하면됨. DIP, OCP 만족
```java
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

```