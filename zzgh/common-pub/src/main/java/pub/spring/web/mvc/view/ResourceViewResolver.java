package pub.spring.web.mvc.view;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;
import pub.web.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-5
 */
public class ResourceViewResolver extends InternalResourceViewResolver {

    private View applyLifecycleMethods(String viewName, AbstractView view) {
        return (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, viewName);
    }

    @Override
    protected View createView(String viewName, Locale locale) throws Exception {
        // If this resolver is not supposed to handle the given view,
        // return null to pass on to the next resolver in the chain.
        if (!canHandle(viewName, locale)) {
            return null;
        }
        // Check for special "redirect:" prefix.
        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX.length());
            RedirectView view = new RedirectView(redirectUrl, isRedirectContextRelative(), isRedirectHttp10Compatible());
            return applyLifecycleMethods(viewName, view);
        }
        // Check for special "forward:" prefix.
        if (viewName.startsWith(FORWARD_URL_PREFIX)) {
            String forwardUrl = viewName.substring(FORWARD_URL_PREFIX.length());
            return new InternalResourceView(forwardUrl);
        }

        //
        viewName = expandViewName(viewName);

        // Else fall back to superclass implementation: calling loadView.
        return super.createView(viewName, locale);
    }

    private String expandViewName(String viewName) {
        int slashPos = viewName.indexOf('/');
        if (slashPos == 0) {
            viewName = viewName.substring(1);
        }
        else if (slashPos == -1) {
            HttpServletRequest request = ServletUtils.getRequest();
            String home = request.getContextPath();
            String uri = request.getRequestURI();
            uri = uri.substring(home.length() + 1);
            if (uri.indexOf('/') != -1) {
                int pos = uri.lastIndexOf('/');
                viewName = uri.substring(0, pos + 1) + viewName;
            }
            else {
                viewName = "app/" + viewName;
            }
        }
        if (viewName.indexOf('.') == -1) {
            viewName += ".jsp";
        }
        return viewName;
    }
}
