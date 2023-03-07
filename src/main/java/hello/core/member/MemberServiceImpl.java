package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Autowired // ac.getBean(MemberRepository.class) 의존관계 자동 주입
    public MemberServiceImpl (MemberRepository memberRepository){
        this.memberRepository = memberRepository; // 생성자를 통해서 memberRepository에 구현체가 뭐가 들어갈지 선택
    }
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findByid(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
