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