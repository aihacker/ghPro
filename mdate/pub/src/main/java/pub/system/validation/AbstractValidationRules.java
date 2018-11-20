package pub.system.validation;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-2-15
 */
public abstract class AbstractValidationRules implements ValidationRules {

	@Override
	public ValidationRules required() {
		return required(null);
	}

    @Override
	public ValidationRules min(Object valueToCompare) {
		return min(valueToCompare, null);
	}

	@Override
	public ValidationRules max(Object valueToCompare) {
		return max(valueToCompare, null);
	}

	@Override
	public ValidationRules telno() {
		return telno(null);
	}

	@Override
	public ValidationRules mobile() {
		return mobile(null);
	}

	@Override
	public ValidationRules minLength(int minLength) {
		return minLength(minLength, null);
	}

	@Override
	public ValidationRules maxLength(int maxLength) {
		return maxLength(maxLength, null);
	}

	@Override
	public ValidationRules number() {
		return number(null);
	}

	@Override
	public ValidationRules integer() {
		return integer(null);
	}

	@Override
	public ValidationRules email() {
		return email(null);
	}
	
	@Override
	public ValidationRules url() {
		return url(null);
	}

	@Override
	public ValidationRules digits() {
		return digits(null);
	}

    @Override
    public ValidationRules regex(String regex) {
        return regex(regex, null);
    }
}
