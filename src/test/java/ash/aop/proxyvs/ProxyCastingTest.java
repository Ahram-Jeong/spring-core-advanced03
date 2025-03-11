package ash.aop.proxyvs;

import ash.aop.member.MemberService;
import ash.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProxyCastingTest {
    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시
        proxyFactory.addInterface(MemberService.class);

        // 빈 어드바이스 추가하여 프록시 객체 강제 생성
        proxyFactory.addAdvice((org.aopalliance.intercept.MethodInterceptor) invocation -> invocation.proceed());

        // 프록시를 인터페이스로 캐스팅 성공
//        Object proxy = proxyFactory.getProxy();
        MemberService memberServieceProxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServieceProxy;
        });

    }
}
