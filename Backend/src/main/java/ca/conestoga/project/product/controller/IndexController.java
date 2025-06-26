package ca.conestoga.project.product.controller;

import ca.conestoga.project.common.Const;
import ca.conestoga.project.common.ErrorEnum;
import ca.conestoga.project.entity.admin.Admin;
import ca.conestoga.project.service.AdminService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * default controller
 */
@Controller
public class IndexController {

    /**
     * index
     *
     * @return index
     */
    @RequestMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return new ModelAndView(Const.ERROR_PAGE, Const.ERROR_PAGE_MESSAGE, ErrorEnum.LOGIN_STATUS_ERROR.getMessage());
        }
        Admin admin = adminService.findByUsername(user.getUsername());
        if (admin == null) {
            return new ModelAndView(Const.ERROR_PAGE, Const.ERROR_PAGE_MESSAGE, ErrorEnum.ADMIN_ACCOUNT_NOT_FOUND.getMessage());
        }
        String path = "manage/event/list";
        if (adminService.checkDefaultPassword(admin)) {
            path = "manage/account/password/edit";
        }
        return new ModelAndView("index", "path", path);
    }

    /**
     * login page
     *
     * @return login page
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * constructor for object injection
     *
     * @param adminService Service for actions of admin
     */
    public IndexController(AdminService adminService) {
        this.adminService = adminService;
    }

    private final AdminService adminService;
}
