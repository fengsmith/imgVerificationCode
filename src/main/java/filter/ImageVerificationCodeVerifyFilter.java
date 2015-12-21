package filter;


import vo.ImageVerifyCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * author:      shfq
 * create date: 2015/12/14.
 */
public class ImageVerificationCodeVerifyFilter implements Filter {
    private static final Set<String> backEndBlackList = new HashSet<String>();
    static {
        backEndBlackList.add("/login");
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (needToFilter(request)) {
            if (isVerifyCodeLegal(request)) {
                removeVerifyCode(request);
                filterChain.doFilter(request, httpServletResponse);
                return;

            } else {
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
                PrintWriter printWriter = httpServletResponse.getWriter();
                String jsonString = "{\"result\" : false, \"errorMessage\" : \"" + "验证码不正确" + "\"}";

                printWriter.print(jsonString);
                printWriter.flush();
                printWriter.close();
                return;
            }

        } else {
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }

    public void destroy() {

    }

    private static boolean isVerifyCodeLegal(HttpServletRequest request) {
        String verificationCode = request.getParameter("imageVerifyCode");
        if (verificationCode == null) {
            return false;
        }
        ImageVerifyCode imageVerifyCode = (ImageVerifyCode) request.getSession().getAttribute("imageVerifyCode");

        return imageVerifyCode.isVerifyCodeLegal(verificationCode, 5);
    }

    private static boolean needToFilter(HttpServletRequest request) {
        return backEndBlackList.contains(request.getServletPath());
    }


    private static void removeVerifyCode(HttpServletRequest request) {
        request.removeAttribute("imageVerifyCode");
    }


}
