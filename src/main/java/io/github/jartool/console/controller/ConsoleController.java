package io.github.jartool.console.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import io.github.jartool.console.common.Constants;
import io.github.jartool.console.entity.AuthEntity;
import io.github.jartool.console.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ConsoleController
 *
 * @author jartool
 * @date 2021/10/15 16:18:51
 */
@Controller
public class ConsoleController {

    @Value("${console.auth.key:auth}")
    private String authKey;
    @Value("${console.auth.url:/auth}")
    private String authUrl;
    @Value("${console.auth.username:admin}")
    private String username;
    @Value("${console.auth.password:admin}")
    private String password;

    @GetMapping("${console.view:/console}")
    public String console(Model model) {
        model.addAttribute(Constants.Attribute.AUTH_KEY, authKey);
        model.addAttribute(Constants.Attribute.AUTH_URL, authUrl);
        return Constants.View.VIEW_CONSOLE;
    }


    @PostMapping("${console.auth.url:/auth}")
    @ResponseBody
    public JSONObject auth(@RequestBody AuthEntity authEntity) {
        JSONObject json = new JSONObject();
        json.set(Constants.Rep.CODE, -1);
        String secretKey = SecurityUtil.encrypt(username + password);
        if (CharSequenceUtil.isNotBlank(authEntity.getKey()) && authEntity.getKey().equals(secretKey)) {
            json.set(Constants.Rep.CODE, 0);
        } else if (username.equals(authEntity.getUsername()) && password.equals(authEntity.getPassword())){
            json.set(Constants.Rep.CODE, 0);
            json.set(Constants.Rep.SECRET, secretKey);
        }
        return json;
    }
}
