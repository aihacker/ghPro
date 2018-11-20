package pub.utils;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-5-4
 */
public class ClassScanner  extends ClassPathBeanDefinitionScanner {
//
//    private String basePackage;
//    private Class<? extends Annotation> annotationClass;
//
    public ClassScanner() {
        super(new SimpleBeanDefinitionRegistry());
    }
//
//    /**
//     * Configures parent scanner to search for the right interfaces. It can search for all
//     * interfaces or just for those that extends a markerInterface or/and those annotated with
//     * the annotationClass
//     */
//    @Override
//    protected void registerDefaultFilters() {
//        boolean acceptAllInterfaces = true;
//
//        // if specified, use the given annotation and / or marker interface
//        if (this.annotationClass != null) {
//            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
//            acceptAllInterfaces = false;
//        }
//
//        // always exclude interfaces with no methods
//        addExcludeFilter(new TypeFilter() {
//            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
//                    throws IOException {
//                ClassMetadata classMetadata = metadataReader.getClassMetadata();
//                Class<?> candidateClass = null;
//
//                try {
//                    candidateClass = getClass().getClassLoader().loadClass(classMetadata.getClassName());
//                } catch (ClassNotFoundException ex) {
//                    return false;
//                }
//
//                if (candidateClass.getMethods().length == 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
//    }
//
//    /**
//     * Calls the parent search that will search and register all the candidates. Then the
//     * registered objects are post processed to set them as MapperFactoryBeans
//     */
//    @Override
//    protected Set<Cla> doScan(String... basePackages) {
//        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
//
//        if (beanDefinitions.isEmpty()) {
//            logger.warn("No MyBatis mapper was found in '" + this.basePackage
//                    + "' package. Please check your configuration.");
//        } else {
//            for (BeanDefinitionHolder holder : beanDefinitions) {
//                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
//
//                if (logger.isDebugEnabled()) {
//                    logger.debug("Creating MapperFactoryBean with name '" + holder.getBeanName() + "' and '"
//                            + definition.getBeanClassName() + "' mapperInterface");
//                }
//
//                System.out.println("?");
//            }
//        }
//
//        return beanDefinitions;
//    }
//
//    @Override
//    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//        return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
//    }
//
//    @Override
//    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
//        if (super.checkCandidate(beanName, beanDefinition)) {
//            return true;
//        } else {
//            logger.warn("Skipping MapperFactoryBean with name '" + beanName + "' and '"
//                    + beanDefinition.getBeanClassName() + "' mapperInterface"
//                    + ". Bean already defined with the same name!");
//            return false;
//        }
//    }
//
}
