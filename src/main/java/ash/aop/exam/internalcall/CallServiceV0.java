package ash.aop.exam.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {
    public void external() {
        log.info("call external");
        // 메소드 앞에 별도의 참조가 없으면 this라는 뜻으로 자기 자신의 인스턴스를 가리킨다.
        // 결과적으로 자기 자신의 내부 메소드를 호출하는 this.internal()이 되는데, 여기서 this는 실제 대상 객체(target)의 인스턴스를 뜻한다.
        // 결과적으로 이러한 내부 호출은 프록시를 거치지 않는다. 따라서 어드바이스도 적용할 수 없다.
        internal(); // 내부 메소드 호출 (this.internal())
    }

    public void internal() {
        log.info("call internal");
    }
}
