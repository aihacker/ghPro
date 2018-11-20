package pub.spring.web;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.view.JsonView;
import pub.types.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-3-14
 */
public class JsonHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, 
                                              HttpServletResponse response, 
                                              Object handler, Exception ex) {

        if(ex instanceof ValidationError) {
            return new ModelAndView(jsonView, JsonView.directResult, ActionResult.fail(ex));
        }

        //
        ex.printStackTrace();

        if(!request.getRequestURI().endsWith(".json")) {
            return null;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ok", false);
        model.put("error", true);
        model.put("msg", ex.getMessage());
        model.put("type", ex.getClass().getName());
        model.put("stacktrace", ExceptionUtils.getStackTrace(ex));
        return new ModelAndView(jsonView, model);
    }

    @Autowired
    private JsonView jsonView;
    
}
