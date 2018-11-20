package pub.spring.web.mvc.bind;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2015-09-18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface XData {

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
