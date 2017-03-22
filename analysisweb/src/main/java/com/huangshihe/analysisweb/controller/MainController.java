package com.huangshihe.analysisweb.controller;

import com.huangshihe.analysisweb.service.MainService;
import com.jfinal.core.Controller;

/**
 * Created by root on 3/21/17.
 */
public class MainController extends Controller {

    private MainService mainService = new MainService();

    public void index() {
        setAttr("username", "huangshihe");
        render("index.jsp");
    }

    public void analysisUserAction() {
        String username = getPara("username");
        setAttr("total", mainService.getTotalAvgPer());
        setAttr("record", mainService.getUserRecordsPer(username));
        render("analysisUserAction.jsp");
    }
}
