package servlets;

import constant.Constant;
import vo.ImageVerifyCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author:      shfq
 * create date: 2015/12/21.
 */

/**
 * 主要用于 ajax 校验图片验证码，所以不需要移除 session 中的验证码
 */
public class FrontEndVerifyImgVerificationCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isVerifyCodeLegal(req)) {
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Type", "application/json;charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            String jsonString = "{\"result\" : true}";

            printWriter.print(jsonString);
            printWriter.flush();
            printWriter.close();
        } else {
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Type", "application/json;charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            String jsonString = "{\"result\" : false, \"errorMessage\" : \"" + getErrorMessage(req) + "\"}";

            printWriter.print(jsonString);
            printWriter.flush();
            printWriter.close();
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }

    private static boolean isVerifyCodeLegal(HttpServletRequest request) {
        String verificationCode = request.getParameter("imageVerifyCode");
        if (verificationCode == null) {
            return false;
        }
        ImageVerifyCode imageVerifyCode = (ImageVerifyCode) request.getSession().getAttribute(Constant.IMAGE_VERIFICATION_CODE);
        if (imageVerifyCode == null) {
            return false;
        } else {
            return imageVerifyCode.isVerifyCodeLegal(verificationCode, 5);
        }

    }

    private static String getErrorMessage(HttpServletRequest request) {
        ImageVerifyCode imageVerifyCode = (ImageVerifyCode) request.getSession().getAttribute(Constant.IMAGE_VERIFICATION_CODE);
        if (imageVerifyCode == null) {
            return "验证码已过期";
        }
        return imageVerifyCode.getErrorMessage();
    }
}
