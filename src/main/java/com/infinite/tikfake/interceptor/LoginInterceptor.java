package com.infinite.tikfake.interceptor;

import com.infinite.tikfake.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        if(JwtUtil.verifyTokenOfUser(token)){
            return true;
        }
        returnResponse(response);
        return false;
    }

    /**
     * 响应信息
     *
     * @param response 返回信息
     */
    private void returnResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out;
        try {
            JSONObject res = new JSONObject();
            res.put("status_code", 1);
            res.put("status_msg", "请登录");
            out = response.getWriter();
            out.append(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
