package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // 1. 스프링 빈 등록 -> 2. @Autowired 있는 것을 의존관계 주입
public class OrderServiceImpl implements OrderService{
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired // 생성자 주입은 빈 등록하면서 같이 자동주입 함
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

    @Autowired // 일반 메서드 주입
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    
    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
