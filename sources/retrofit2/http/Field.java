package retrofit2.http;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes6.dex */
public @interface Field {
    boolean encoded() default false;

    String value();
}
