package ash.aop.order.aop.member;

import ash.aop.order.aop.member.annotation.ClassAop;
import ash.aop.order.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {
    @Override
    @MethodAop("Test me!")
    public String hello(String param) {
        return "OKAY";
    }

    public String internal(String param) {
        return "OK";
    }
}
