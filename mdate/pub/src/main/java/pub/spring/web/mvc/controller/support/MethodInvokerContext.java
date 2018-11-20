package pub.spring.web.mvc.controller.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import pub.spring.web.mvc.bind.XDataSessionAttributeStore;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-5
 */
public class MethodInvokerContext implements BeanFactoryAware {

    WebBindingInitializer webBindingInitializer = new pub.spring.web.mvc.bind.WebBindingInitializer();
    SessionAttributeStore sessionAttributeStore = new XDataSessionAttributeStore(); //new DefaultSessionAttributeStore();
    ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    WebArgumentResolver[] customArgumentResolvers;
    HttpMessageConverter[] messageConverters;
    ConfigurableBeanFactory beanFactory;
    BeanExpressionContext expressionContext;
    ModelAndViewResolver[] customModelAndViewResolvers;
    MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();

    public MethodInvokerContext() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        this.messageConverters = new HttpMessageConverter[]{
                new ByteArrayHttpMessageConverter(), stringHttpMessageConverter,
                new SourceHttpMessageConverter(), new AllEncompassingFormHttpMessageConverter()};
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
        this.expressionContext = new BeanExpressionContext(this.beanFactory, new RequestScope());
    }

    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    public SessionAttributeStore getSessionAttributeStore() {
        return sessionAttributeStore;
    }

    public void setSessionAttributeStore(SessionAttributeStore sessionAttributeStore) {
        this.sessionAttributeStore = sessionAttributeStore;
    }

    public ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }

    public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    public WebArgumentResolver[] getCustomArgumentResolvers() {
        return customArgumentResolvers;
    }

    public void setCustomArgumentResolvers(WebArgumentResolver[] customArgumentResolvers) {
        this.customArgumentResolvers = customArgumentResolvers;
    }

    public HttpMessageConverter[] getMessageConverters() {
        return messageConverters;
    }

    public void setMessageConverters(HttpMessageConverter[] messageConverters) {
        this.messageConverters = messageConverters;
    }

    public ModelAndViewResolver[] getCustomModelAndViewResolvers() {
        return customModelAndViewResolvers;
    }

    public void setCustomModelAndViewResolvers(ModelAndViewResolver[] customModelAndViewResolvers) {
        this.customModelAndViewResolvers = customModelAndViewResolvers;
    }

    public MethodNameResolver getMethodNameResolver() {
        return methodNameResolver;
    }

    public void setMethodNameResolver(MethodNameResolver methodNameResolver) {
        this.methodNameResolver = methodNameResolver;
    }
}
