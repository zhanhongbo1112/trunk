package com.yqboots.project.web.form;

import com.yqboots.project.web.WebKeys;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016-08-04.
 */
@ControllerAdvice
public class SearchFormInitBinder {
    /**
     * To resolve the issue that different search form objects with the same session key may throw exception
     *
     * @param request
     */
    @InitBinder
    public void initBinder(HttpServletRequest request) {
        WebUtils.setSessionAttribute(request, WebKeys.SEARCH_FORM, null);
    }
}
