package com.macro.mall.auth.controller;

import com.macro.mall.auth.domain.Oauth2TokenDto;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.constant.AuthConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 * Created by macro on 2020/7/17.
 */
@RestController
@Api(tags = "AuthController", description = "认证中心登录认证")
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

//    @Autowired
//    private Principal principal;

//    @ApiOperation("Oauth2获取token")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "grant_type", value = "授权模式", required = true, defaultValue = "password"),
//            @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID", required = true, defaultValue = "portal-app"),
//            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥", required = true, defaultValue = "123456"),
//            @ApiImplicitParam(name = "scope", value = "范围", required = true, defaultValue = "all"),
//            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
//            @ApiImplicitParam(name = "username", value = "登录用户名"),
//            @ApiImplicitParam(name = "password", value = "登录密码")
//    })
//
//    @RequestMapping(value = "/token", method = RequestMethod.POST)
//    public CommonResult<Oauth2TokenDto> postAccessToken(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
//        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
//        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
//                .token(oAuth2AccessToken.getValue())
//                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
//                .expiresIn(oAuth2AccessToken.getExpiresIn())
//                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();
//
//        return CommonResult.success(oauth2TokenDto);
//    }

    @ApiOperation("Oauth2获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", value = "授权模式", required = true, defaultValue = "password"),
            @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID", required = true, defaultValue = "portal-app"),
            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥", required = true, defaultValue = "123456"),
            @ApiImplicitParam(name = "scope", value = "范围", required = true, defaultValue = "all"),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", value = "登录用户名"),
            @ApiImplicitParam(name = "password", value = "登录密码")
    })
    @PostMapping(value = "/token")
    CommonResult getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) {
        OAuth2AccessToken oAuth2AccessToken = null;

        try {
            oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        } catch (HttpRequestMethodNotSupportedException e) {
            e.printStackTrace();
        }

        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();

        return CommonResult.success(oauth2TokenDto);
    }
}
/**
 token ： eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJibHpnIiwic2NvcGUiOlsiYWxsIl0sImlkIjoxMSwiZXhwIjoxNjQyMDU2OTg5LCJhdXRob3JpdGllcyI6WyLliY3lj7DkvJrlkZgiXSwianRpIjoiNzg0MjI1NGEtOGU2My00YjhiLWJiOTYtYmFiZTI0MDFiNWJkIiwiY2xpZW50X2lkIjoicG9ydGFsLWFwcCJ9.mDjw9aTU6ADl8eajiFY-TVzRIeajqtyVnFqlUIZNjCxRMbXKzrTv9VkfzIrtEyEkieDqpFVfE9FhPLFSjh4cj28lZPdvj7PkSeru7CvKcpPLYC-CIYOHXm21PEIJDKqhFjzzyodyGShkEaWBZUarYR4C9BhlFbA5Ww65fDLgtXA
 */
