package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component // 1. 스프링 빈 등록 -> 2. @Autowired 있는 것을 의존관계 주입
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    // 생성자 주입 시 필드에 final
    // 1. 생성자에서만 값을 넣어줄 수 있다.
    // 2. 개발자가 초기화 하지 않는 실수를 막을 수 있다.

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findByid(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); // 단일책임원칙을 잘 지킨 것 (할인에 대한 변경이 필요하면 할인 쪽만 고치면 된다.)
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    
    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
