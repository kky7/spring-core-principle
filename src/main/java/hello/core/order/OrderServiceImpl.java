package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 추상화(인터페이스), 구체클래스 모두 의존한다 -> DIP 위반,
//    할인의 역할 뿐 아니라 누구를 선택할지 책임도 가지게 된다. -> 다양한 책임을 가짐

//    private DiscountPolicy discountPolicy; // DIP 위반하지 않도록 -> 그러나 대입된 것이 없으므로 Null pointer Exception 발생 -> DIP를 위반하지 않을 려면 누군가가 대신 주입 해주어야 한다.


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
