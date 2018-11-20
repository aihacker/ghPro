package pub.spring.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-2-7
 */
public class ClassNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String name = definition.getBeanClassName();
        return name;
    }

}
