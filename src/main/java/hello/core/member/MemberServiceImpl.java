package hello.core.member;

public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
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
