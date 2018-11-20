package pub.spring.web.mvc.bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-3-7
 * Time: 3:28:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PageData {

	/**
	 * The names of session attributes in the model, to be stored in the
	 * session or some conversational storage.
	 * <p>Note: This indicates the model attribute names. The session attribute
	 * names may or may not match the model attribute names; applications should
	 * not rely on the session attribute names but rather operate on the model only.
	 */
	String[] value() default {};

	/**
	 * The types of session attributes in the model, to be stored in the
	 * session or some conversational storage. All model attributes of this
	 * type will be stored in the session, regardless of attribute name.
	 */
	Class[] types() default {};

}
