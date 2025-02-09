package ash.aop.pointcut;

import ash.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // execution(* ..package..Class.)
        // public java.lang.String ash.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod = {}", helloMethod);
    }

    @Test
    void exactMatch() {
        // public java.lang.String ash.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        // 매칭 조건 : (접근제어자? 반환타입 선언타입? 메소드명 파라미터 예외?) -> ?은 생략 가능
        pointcut.setExpression("execution(public String ash.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
