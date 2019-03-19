package com.example.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : winnie
 * @date : 2019/3/19
 * @desc
 */
public class PermisFilter extends ZuulFilter {
    /**
     * filterType方法的返回值为过滤器的类型，
     * 过滤器的类型决定了过滤器在哪个生命周期执行，
     * pre表示在路由之前执行过滤器，其他可选值还有post、error、route和static，当然也可以自定义。
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * shouldFilter方法用来判断过滤器是否执行，
     * true表示执行，false表示不执行，
     * 在实际开发中，我们可以根据当前请求地址来决定要不要对该地址进行过滤，这里我直接返回true。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String login = request.getParameter("login");
        if (login == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.addZuulResponseHeader("content-type","text/html;charset=utf-8");
            ctx.setResponseBody("非法访问");
        }
        return null;
    }
}
