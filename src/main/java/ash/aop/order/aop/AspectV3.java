package ash.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {
    // ash.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* ash.aop.order..*(..))")
    private void allOrder() {} // -> Pointcut Signature

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }

    // ash.aop.order 패키지와 하위 패키지 이면서 동시에 클래스 이름 패턴이 *Service
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[Transaction 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[Transaction 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[Transaction 롤백] {}", joinPoint.getSignature());
            throw  e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
