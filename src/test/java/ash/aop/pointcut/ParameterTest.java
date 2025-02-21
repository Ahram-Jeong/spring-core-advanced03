package ash.aop.pointcut;

import ash.aop.member.MemberService;
import ash.aop.member.annotation.ClassAop;
import ash.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {
    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("WOODZ");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* ash.aop.member..*.*(..))")
        private void allMember() {

        }

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0]; // 배열에서 직접 꺼냄
            log.info("[logArgs1] {}, arg = {}", joinPoint.getSignature(), arg1);
            // [logArgs1] String ash.aop.member.MemberServiceImpl.hello(String), arg = WOODZ
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, arg = {}", joinPoint.getSignature(), arg);
            // [logArgs1] String ash.aop.member.MemberServiceImpl.hello(String), arg = WOODZ
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg = {}", arg);
        }

        // this : 프록시 객체를 전달 받음**
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this] {},  obj = {}", joinPoint.getSignature(), obj.getClass());
        }

        // target : 실제 대상 객체를 전달 받음**
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target] {},  obj = {}", joinPoint.getSignature(), obj.getClass());
        }

        // @target : 타입의 애노테이션을 전달 받음
        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target] {},  obj = {}", joinPoint.getSignature(), annotation.getClass());
        }

        // @within : 타입의 애노테이션을 전달 받음
        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within] {},  obj = {}", joinPoint.getSignature(), annotation.getClass());
        }

        // @annotation : 메소드의 애노테이션을 전달 받음
        @Before("allMember() && @annotation(annotation)")
        public void atWithin(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation] {},  annotationValue = {}", joinPoint.getSignature(), annotation.value());
        }
    }
}
