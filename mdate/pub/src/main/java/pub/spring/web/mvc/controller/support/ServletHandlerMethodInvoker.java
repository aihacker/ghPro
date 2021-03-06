package pub.spring.web.mvc.controller.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-5
 */
public class ServletHandlerMethodInvoker extends HandlerMethodInvoker {

    protected final Log logger = LogFactory.getLog(getClass());

    private boolean responseArgumentUsed = false;

    private MethodInvokerContext context;

    public ServletHandlerMethodInvoker(ServletHandlerMethodResolver methodResolver, MethodInvokerContext context) {
        super(methodResolver, context.webBindingInitializer, context.sessionAttributeStore,
                context.parameterNameDiscoverer, context.customArgumentResolvers,
                context.messageConverters);
        this.context = context;
    }

    @Override
    protected void raiseMissingParameterException(String paramName, Class paramType) throws Exception {
        throw new MissingServletRequestParameterException(paramName, paramType.getSimpleName());
    }

    @Override
    protected void raiseSessionRequiredException(String message) throws Exception {
        throw new HttpSessionRequiredException(message);
    }

    @Override
    protected WebDataBinder createBinder(NativeWebRequest webRequest, Object target, String objectName)
            throws Exception {

        return new ServletRequestDataBinder(target, objectName);
    }

    @Override
    protected void doBind(WebDataBinder binder, NativeWebRequest webRequest) throws Exception {
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(webRequest.getNativeRequest(ServletRequest.class));
    }

    @Override
    protected HttpInputMessage createHttpInputMessage(NativeWebRequest webRequest) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return new ServletServerHttpRequest(servletRequest);
    }

    @Override
    protected HttpOutputMessage createHttpOutputMessage(NativeWebRequest webRequest) throws Exception {
        HttpServletResponse servletResponse = (HttpServletResponse) webRequest.getNativeResponse();
        return new ServletServerHttpResponse(servletResponse);
    }

    @Override
    protected Object resolveDefaultValue(String value) {
        if (context.beanFactory == null) {
            return value;
        }
        String placeholdersResolved = context.beanFactory.resolveEmbeddedValue(value);
        BeanExpressionResolver exprResolver = context.beanFactory.getBeanExpressionResolver();
        if (exprResolver == null) {
            return value;
        }
        return exprResolver.evaluate(placeholdersResolved, context.expressionContext);
    }

    @Override
    protected Object resolveCookieValue(String cookieName, Class paramType, NativeWebRequest webRequest)
            throws Exception {

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Cookie cookieValue = WebUtils.getCookie(servletRequest, cookieName);
        if (Cookie.class.isAssignableFrom(paramType)) {
            return cookieValue;
        }
        else if (cookieValue != null) {
            return UriUtils.decode(cookieValue.getValue(), "utf-8");
        }
        else {
            return null;
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    protected String resolvePathVariable(String pathVarName, Class paramType, NativeWebRequest webRequest)
            throws Exception {

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String> uriTemplateVariables =
                (Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (uriTemplateVariables == null || !uriTemplateVariables.containsKey(pathVarName)) {
            throw new IllegalStateException(
                    "Could not find @PathVariable [" + pathVarName + "] in @RequestMapping");
        }
        return uriTemplateVariables.get(pathVarName);
    }

    @Override
    protected Object resolveStandardArgument(Class<?> parameterType, NativeWebRequest webRequest) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        if (ServletRequest.class.isAssignableFrom(parameterType) ||
                MultipartRequest.class.isAssignableFrom(parameterType)) {
            Object nativeRequest = webRequest.getNativeRequest(parameterType);
            if (nativeRequest == null) {
                throw new IllegalStateException(
                        "Current request is not of type [" + parameterType.getName() + "]: " + request);
            }
            return nativeRequest;
        }
        else if (ServletResponse.class.isAssignableFrom(parameterType)) {
            this.responseArgumentUsed = true;
            Object nativeResponse = webRequest.getNativeResponse(parameterType);
            if (nativeResponse == null) {
                throw new IllegalStateException(
                        "Current response is not of type [" + parameterType.getName() + "]: " + response);
            }
            return nativeResponse;
        }
        else if (HttpSession.class.isAssignableFrom(parameterType)) {
            return request.getSession();
        }
        else if (Principal.class.isAssignableFrom(parameterType)) {
            return request.getUserPrincipal();
        }
        else if (Locale.class.equals(parameterType)) {
            return RequestContextUtils.getLocale(request);
        }
        else if (InputStream.class.isAssignableFrom(parameterType)) {
            return request.getInputStream();
        }
        else if (Reader.class.isAssignableFrom(parameterType)) {
            return request.getReader();
        }
        else if (OutputStream.class.isAssignableFrom(parameterType)) {
            this.responseArgumentUsed = true;
            return response.getOutputStream();
        }
        else if (Writer.class.isAssignableFrom(parameterType)) {
            this.responseArgumentUsed = true;
            return response.getWriter();
        }
        return super.resolveStandardArgument(parameterType, webRequest);
    }

    @SuppressWarnings("unchecked")
    public ModelAndView getModelAndView(Method handlerMethod, Class handlerType, Object returnValue,
            ExtendedModelMap implicitModel, ServletWebRequest webRequest) throws Exception {

        ResponseStatus responseStatusAnn = AnnotationUtils.findAnnotation(handlerMethod, ResponseStatus.class);
        if (responseStatusAnn != null) {
            HttpStatus responseStatus = responseStatusAnn.value();
            String reason = responseStatusAnn.reason();
            if (!StringUtils.hasText(reason)) {
                webRequest.getResponse().setStatus(responseStatus.value());
            }
            else {
                webRequest.getResponse().sendError(responseStatus.value(), reason);
            }

            // to be picked up by the RedirectView
            webRequest.getRequest().setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, responseStatus);

            responseArgumentUsed = true;
        }

        // Invoke custom resolvers if present...
        if (context.customModelAndViewResolvers != null) {
            for (ModelAndViewResolver mavResolver : context.customModelAndViewResolvers) {
                ModelAndView mav = mavResolver.resolveModelAndView(
                        handlerMethod, handlerType, returnValue, implicitModel, webRequest);
                if (mav != ModelAndViewResolver.UNRESOLVED) {
                    return mav;
                }
            }
        }

        if (returnValue instanceof HttpEntity) {
            handleHttpEntityResponse((HttpEntity<?>) returnValue, webRequest);
            return null;
        }
        else if (AnnotationUtils.findAnnotation(handlerMethod, ResponseBody.class) != null) {
            handleResponseBody(returnValue, webRequest);
            return null;
        }
        else if (returnValue instanceof ModelAndView) {
            ModelAndView mav = (ModelAndView) returnValue;
            mav.getModelMap().mergeAttributes(implicitModel);
            return mav;
        }
        else if (returnValue instanceof Model) {
            return new ModelAndView().addAllObjects(implicitModel).addAllObjects(((Model) returnValue).asMap());
        }
        else if (returnValue instanceof View) {
            return new ModelAndView((View) returnValue).addAllObjects(implicitModel);
        }
        else if (AnnotationUtils.findAnnotation(handlerMethod, ModelAttribute.class) != null) {
            addReturnValueAsModelAttribute(handlerMethod, handlerType, returnValue, implicitModel);
            return new ModelAndView().addAllObjects(implicitModel);
        }
        else if (returnValue instanceof Map) {
            return new ModelAndView().addAllObjects(implicitModel).addAllObjects((Map) returnValue);
        }
        else if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue).addAllObjects(implicitModel);
        }
        else if (returnValue == null) {
            // Either returned null or was 'void' return.
            if (this.responseArgumentUsed || webRequest.isNotModified()) {
                return null;
            }
            else {
                // Assuming view name translation...
                return new ModelAndView().addAllObjects(implicitModel);
            }
        }
        else if (!BeanUtils.isSimpleProperty(returnValue.getClass())) {
            // Assume a single model attribute...
            addReturnValueAsModelAttribute(handlerMethod, handlerType, returnValue, implicitModel);
            return new ModelAndView().addAllObjects(implicitModel);
        }
        else {
            throw new IllegalArgumentException("Invalid handler method return value: " + returnValue);
        }
    }

    private void handleResponseBody(Object returnValue, ServletWebRequest webRequest)
            throws Exception {
        if (returnValue == null) {
            return;
        }
        HttpInputMessage inputMessage = createHttpInputMessage(webRequest);
        HttpOutputMessage outputMessage = createHttpOutputMessage(webRequest);
        writeWithMessageConverters(returnValue, inputMessage, outputMessage);
    }

    private void handleHttpEntityResponse(HttpEntity<?> responseEntity, ServletWebRequest webRequest)
            throws Exception {
        if (responseEntity == null) {
            return;
        }
        HttpInputMessage inputMessage = createHttpInputMessage(webRequest);
        HttpOutputMessage outputMessage = createHttpOutputMessage(webRequest);
        if (responseEntity instanceof ResponseEntity && outputMessage instanceof ServerHttpResponse) {
            ((ServerHttpResponse) outputMessage).setStatusCode(((ResponseEntity) responseEntity).getStatusCode());
        }
        HttpHeaders entityHeaders = responseEntity.getHeaders();
        if (!entityHeaders.isEmpty()) {
            outputMessage.getHeaders().putAll(entityHeaders);
        }
        Object body = responseEntity.getBody();
        if (body != null) {
            writeWithMessageConverters(body, inputMessage, outputMessage);
        }
        else {
            // flush headers
            outputMessage.getBody();
        }
    }

    @SuppressWarnings("unchecked")
    private void writeWithMessageConverters(Object returnValue,
            HttpInputMessage inputMessage, HttpOutputMessage outputMessage)
            throws IOException, HttpMediaTypeNotAcceptableException {
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        if (acceptedMediaTypes.isEmpty()) {
            acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
        }
        MediaType.sortByQualityValue(acceptedMediaTypes);
        Class<?> returnValueType = returnValue.getClass();
        List<MediaType> allSupportedMediaTypes = new ArrayList<MediaType>();
        if (context.messageConverters != null) {
            for (MediaType acceptedMediaType : acceptedMediaTypes) {
                for (HttpMessageConverter messageConverter : context.messageConverters) {
                    if (messageConverter.canWrite(returnValueType, acceptedMediaType)) {
                        messageConverter.write(returnValue, acceptedMediaType, outputMessage);
                        if (logger.isDebugEnabled()) {
                            MediaType contentType = outputMessage.getHeaders().getContentType();
                            if (contentType == null) {
                                contentType = acceptedMediaType;
                            }
                            logger.debug("Written [" + returnValue + "] as \"" + contentType +
                                    "\" using [" + messageConverter + "]");
                        }
                        this.responseArgumentUsed = true;
                        return;
                    }
                }
            }
            for (HttpMessageConverter messageConverter : context.messageConverters) {
                allSupportedMediaTypes.addAll(messageConverter.getSupportedMediaTypes());
            }
        }
        throw new HttpMediaTypeNotAcceptableException(allSupportedMediaTypes);
    }

}