package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 추상화(인터페이스), 구체클래스 모두 의존한다 -> DIP 위반

    private DiscountPolicy discountPolicy; // DIP 위반하지 않도록 -> 그러나 대입된 것이 없으므로 Null pointer Exception 발생 -> DIP를 위반하지 않을 려면 누군가가 대신 주입 해주어야 한다.
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findByid(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); // 단일책임원칙을 잘 지킨 것 (할인에 대한 변경이 필요하면 할인 쪽만 고치면 된다.)
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
