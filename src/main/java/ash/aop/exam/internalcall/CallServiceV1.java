package ash.aop.exam.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {
    private final ApplicationContext applicationContext;

    @Autowired
    public CallServiceV1(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void external() {
        log.info("call external");
        CallServiceV1 proxy = applicationContext.getBean(CallServiceV1.class);
        proxy.internal(); // 자기 자신의 인스턴스가 아니라 프록시를 통해 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
