package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.insmanager.module.MsgSession;
import com.hl.insmanager.service.MsgSessionService;
import com.hl.insmanager.vo.msg.MsgsUsersVO;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("msgSessionController")
@Scope("prototype")
public class MsgSessionController extends BaseController {

    @Autowired
    private MsgSessionService<MsgSession> msgSessionService;

    /**
     * 首页 8. 私信用户列表接口
     * @return
     */
    @RequestMapping(value = "/msgs/users", method = RequestMethod.GET)
    public Result msgsusers(HttpServletRequest request, PageVO pageVO){
        List<MsgsUsersVO> msgsUsersVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        msgsUsersVO = msgSessionService.msgsusers(getLoginerId(request));
        ResultsPageVO resultsPageVO = ResultsPageVO.init(msgsUsersVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

}

