package pub.spring.web.mvc.controller;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.web.method.annotation.ModelMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import pub.spring.web.converter.JsonMessageConverter;
import pub.spring.web.mvc.bind.ModelResolver;
import pub.spring.web.mvc.bind.WebBindingInitializer;
import pub.spring.web.mvc.bind.XDataResolver;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by zzl on 2015/9/16.
 */
@SuppressWarnings("unchecked")
public class HandlerAdapter extends RequestMappingHandlerAdapter {

    @Override
    public void afterPropertiesSet() {

        getMessageConverters().add(new JsonMessageConverter());

        super.afterPropertiesSet();

        try {
            Object argumentResolverComposite = FieldUtils.readField(this, "argumentResolvers", true);
            List<HandlerMethodArgumentResolver> argumentResolvers = (List<HandlerMethodArgumentResolver>)
                            FieldUtils.readField(argumentResolverComposite, "argumentResolvers", true);
            ListIterator<HandlerMethodArgumentResolver> iter = argumentResolvers.listIterator();
            while(iter.hasNext()) {
                HandlerMethodArgumentResolver argumentResolver = iter.next();
                if(argumentResolver.getClass().equals(ModelMethodProcessor.class)) {
                    iter.previous();
                    iter.add(new ModelResolver());
                    break;
                }
            }
            //
            iter.add(new XDataResolver());
            //
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //xx
        this.setWebBindingInitializer(new WebBindingInitializer());
    }
}
