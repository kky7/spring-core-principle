package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 클래스 래벨에 붙는 것
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
