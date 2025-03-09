package ash.aop.proxyvs;

import ash.aop.member.MemberService;
import ash.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {
    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
//        Object proxy = proxyFactory.getProxy();
        MemberService memberServieceProxy = (MemberService) proxyFactory.getProxy();


        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServieceProxy;
    }
}
