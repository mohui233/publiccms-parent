package org.publiccms.common.interceptor;

import static com.publiccms.common.tools.RequestUtils.getEncodePath;
import static org.apache.commons.lang3.StringUtils.split;
import static org.publiccms.common.base.AbstractController.getAdminFromSession;
import static org.publiccms.common.base.AbstractController.setAdminToSession;
import static org.publiccms.common.constants.CmsVersion.getVersion;
import static org.publiccms.common.constants.CommonConstants.getXPowered;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.publiccms.entities.sys.SysSite;
import org.publiccms.entities.sys.SysUser;
import org.publiccms.logic.component.site.SiteComponent;
import org.publiccms.logic.service.sys.SysRoleAuthorizedService;
import org.publiccms.logic.service.sys.SysRoleService;
import org.publiccms.logic.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.publiccms.common.base.Base;
import com.publiccms.common.base.BaseInterceptor;

/**
 *
 * AdminContextInterceptor
 *
 */
public class AdminContextInterceptor extends BaseInterceptor implements Base {
    private String adminBasePath;
    private String loginUrl;
    private String loginJsonUrl;
    private String unauthorizedUrl;
    private String[] needNotLoginUrls;
    private String[] needNotAuthorizedUrls;

    @Autowired
    private SysRoleAuthorizedService roleAuthorizedService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SiteComponent siteComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.addHeader(getXPowered(), getVersion());
        String path = urlPathHelper.getLookupPathForRequest(request);
        String ctxPath = urlPathHelper.getOriginatingContextPath(request);
        if (adminBasePath.equals(path)) {
            try {
                StringBuilder sb = new StringBuilder(ctxPath);
                sb.append(adminBasePath).append(SEPARATOR);
                response.sendRedirect(sb.toString());
                return false;
            } catch (IOException e) {
                return true;
            }
        } else if (verifyNeedLogin(path)) {
            SysUser user = getAdminFromSession(request.getSession());
            if (null == user) {
                try {
                    redirectLogin(ctxPath, path, request.getQueryString(), request.getHeader("X-Requested-With"), response);
                    return false;
                } catch (IllegalStateException | IOException e) {
                    return true;
                }
            }
            SysSite site = siteComponent.getSite(request.getServerName());
            SysUser entity = sysUserService.getEntity(user.getId());
            if (!entity.isDisabled() && !entity.isSuperuserAccess() && null != site && site.getId() != entity.getSiteId()
                    && !site.isDisabled()) {
                try {
                    redirectLogin(ctxPath, path, request.getQueryString(), request.getHeader("X-Requested-With"), response);
                    return false;
                } catch (IllegalStateException | IOException e) {
                    return true;
                }
            } else if (verifyNeedAuthorized(path)) {
                if (!SEPARATOR.equals(path)) {
                    int index = path.lastIndexOf(DOT);
                    path = path.substring(path.indexOf(SEPARATOR) > 0 ? 0 : 1, index > -1 ? index : path.length());
                    if (0 == roleAuthorizedService.count(entity.getRoles(), path) && !ownsAllRight(entity.getRoles())) {
                        try {
                            StringBuilder sb = new StringBuilder(ctxPath);
                            sb.append(adminBasePath).append(unauthorizedUrl);
                            response.sendRedirect(sb.toString());
                            return false;
                        } catch (IOException e) {
                            return true;
                        }
                    }
                    user.setName(entity.getName());
                    user.setNickName(entity.getNickName());
                    user.setRoles(entity.getRoles());
                    user.setDeptId(entity.getDeptId());
                    setAdminToSession(request.getSession(), user);
                }
            }
        }
        return true;
    }

    private void redirectLogin(String ctxPath, String path, String queryString, String requestedWith,
            HttpServletResponse response) throws IOException {
        if ("XMLHttpRequest".equalsIgnoreCase(requestedWith)) {
            StringBuilder sb = new StringBuilder(ctxPath);
            sb.append(adminBasePath).append(loginJsonUrl);
            response.sendRedirect(sb.toString());
        } else {
            StringBuilder sb = new StringBuilder(ctxPath);
            sb.append(adminBasePath).append(loginUrl).append("?returnUrl=");
            sb.append(getEncodePath(adminBasePath + path, queryString));
            response.sendRedirect(sb.toString());
        }
    }

    private boolean ownsAllRight(String roles) {
        String[] roleIdArray = split(roles, ",");
        if (null != roles && 0 < roleIdArray.length) {
            Integer[] roleIds = new Integer[roleIdArray.length];
            for (int i = 0; i < roleIdArray.length; i++) {
                roleIds[i] = Integer.parseInt(roleIdArray[i]);
            }
            return sysRoleService.ownsAllRight(roleIds);
        }
        return false;
    }

    private boolean verifyNeedLogin(String url) {
        if (null == loginUrl) {
            return false;
        } else if (null != needNotLoginUrls && null != url) {
            for (String needNotLoginUrl : needNotLoginUrls) {
                if (null != needNotLoginUrl) {
                    if (url.startsWith(needNotLoginUrl)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean verifyNeedAuthorized(String url) {
        if (null == unauthorizedUrl) {
            return false;
        } else if (null != needNotAuthorizedUrls && null != url) {
            for (String needNotAuthorizedUrl : needNotAuthorizedUrls) {
                if (null != needNotAuthorizedUrl) {
                    if (url.startsWith(needNotAuthorizedUrl)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param adminBasePath
     *            the adminBasePath to set
     */
    public void setAdminBasePath(String adminBasePath) {
        this.adminBasePath = adminBasePath;
    }

    /**
     * @param loginUrl
     */
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    /**
     * @param loginJsonUrl
     */
    public void setLoginJsonUrl(String loginJsonUrl) {
        this.loginJsonUrl = loginJsonUrl;
    }

    /**
     * @param unauthorizedUrl
     */
    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    /**
     * @param needNotLoginUrls
     */
    public void setNeedNotLoginUrls(String[] needNotLoginUrls) {
        this.needNotLoginUrls = needNotLoginUrls;
    }

    /**
     * @param needNotAuthorizedUrls
     */
    public void setNeedNotAuthorizedUrls(String[] needNotAuthorizedUrls) {
        this.needNotAuthorizedUrls = needNotAuthorizedUrls;
    }
}
