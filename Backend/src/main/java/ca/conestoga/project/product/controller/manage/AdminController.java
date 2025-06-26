package ca.conestoga.project.product.controller.manage;

import ca.conestoga.project.common.Const;
import ca.conestoga.project.common.ErrorEnum;
import ca.conestoga.project.common.RespBody;
import ca.conestoga.project.entity.admin.Admin;
import ca.conestoga.project.entity.admin.Log;
import ca.conestoga.project.product.bo.admin.PageAdminInfo;
import ca.conestoga.project.service.AdminService;
import ca.conestoga.project.service.LogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * controller of admin page
 */
@Controller
@RequestMapping("/manage/account/")
public class AdminController {

    /**
     * edit password
     *
     * @return edit password page
     */
    @GetMapping("/password/edit")
    public String resetPassword() {
        return "admin/edit_password";
    }

    /**
     * edit password action
     *
     * @param user account object
     * @param op   old password
     * @param np   new password
     * @return edit password result
     */
    @ResponseBody
    @PostMapping("/password/edit/action")
    public RespBody<?> resetPasswordAction(@AuthenticationPrincipal UserDetails user, @RequestParam String op, @RequestParam String np) {
        if (user == null) {
            return RespBody.isFail().msg(ErrorEnum.LOGIN_STATUS_ERROR);
        }
        Admin admin = adminService.findByUsername(user.getUsername());
        if (admin == null) {
            return RespBody.isFail().msg(ErrorEnum.ADMIN_ACCOUNT_NOT_FOUND);
        }
        return adminService.editPassword(admin, op, np);
    }

    /**
     * account list
     *
     * @return account list page
     */
    @GetMapping("list")
    //@PreAuthorize("hasAuthority('edit_admin')")
    public ModelAndView list() {
        List<PageAdminInfo> list = adminService.listAllAdmin().stream().map(admin -> {
            PageAdminInfo pageAdminInfo = new PageAdminInfo();
            pageAdminInfo.setId(admin.getId());
            pageAdminInfo.setUsername(admin.getUsername());
            pageAdminInfo.setRealname(admin.getRealname());
            pageAdminInfo.setStatus(admin.getStatus());
            pageAdminInfo.setStatusStr(Admin.StatusEnum.findStatusByValue(admin.getStatus()).getName());
            pageAdminInfo.setColor(Admin.StatusEnum.OFF.getValue() == admin.getStatus() ? Const.COLOR_GREY : Const.COLOR_BLACK);
            return pageAdminInfo;
        }).collect(Collectors.toList());
        return new ModelAndView("admin/admin_list", "list", list);
    }

    /**
     * add account
     *
     * @return add account page
     */
    @GetMapping("create")
    //@PreAuthorize("hasAuthority('edit_admin')")
    public String addAdmin() {
        return "admin/add_admin";
    }

    /**
     * add account action
     *
     * @param username username
     * @param realname real name
     * @return add result
     */
    @PostMapping("create/action")
    //@PreAuthorize("hasAuthority('edit_admin')")
    @ResponseBody
    public RespBody<?> addAdminAction(@RequestParam String username, @RequestParam String realname) {
        return adminService.saveAdmin(username, realname);
    }

    /**
     * edit account
     *
     * @param id account id
     * @return edit account page
     */
    @GetMapping("{id}/edit")
    //@PreAuthorize("hasAuthority('edit_admin')")
    public ModelAndView editAdmin(@PathVariable Integer id) {
        Admin admin = adminService.getAdminById(id);
        if (admin == null) {
            return new ModelAndView(Const.ERROR_PAGE, Const.ERROR_PAGE_MESSAGE, ErrorEnum.ADMIN_ACCOUNT_NOT_FOUND.getMessage());
        }
        return new ModelAndView("admin/edit_admin", "admin", admin);
    }

    /**
     * edit account action
     *
     * @param id       account id
     * @param username username to be edited
     * @param realname real name to be edited
     * @return edit result
     */
    @PostMapping("{id}/edit/action")
    //@PreAuthorize("hasAuthority('edit_admin')")
    @ResponseBody
    public RespBody<?> editAdminAction(@PathVariable Integer id, @RequestParam String username, @RequestParam String realname) {
        Admin admin = adminService.getAdminById(id);
        if (admin == null) {
            return RespBody.isFail().msg(ErrorEnum.ADMIN_ACCOUNT_NOT_FOUND);
        }
        return adminService.updateAdmin(admin, username, realname);
    }

    /**
     * active account
     *
     * @param id account id
     * @return active result
     */
    @PostMapping("{id}/enable")
    //@PreAuthorize("hasAuthority('edit_admin')")
    @ResponseBody
    public RespBody<?> enableAdmin(@PathVariable Integer id) {
        Admin admin = adminService.getAdminById(id);
        if (admin == null) {
            return RespBody.isFail().msg(ErrorEnum.ADMIN_ACCOUNT_NOT_FOUND);
        }
        return adminService.enableAdmin(admin);
    }

    /**
     * disable account
     *
     * @param id account id
     * @return disable result
     */
    @PostMapping("{id}/disable")
    //@PreAuthorize("hasAuthority('edit_admin')")
    @ResponseBody
    public RespBody<?> disableAdmin(@PathVariable Integer id) {
        Admin admin = adminService.getAdminById(id);
        if (admin == null) {
            return RespBody.isFail().msg(ErrorEnum.ADMIN_ACCOUNT_NOT_FOUND);
        }
        return adminService.disableAdmin(admin);
    }

    /**
     * reset password action
     *
     * @param id account id
     * @return reset result
     */
    @ResponseBody
    @PostMapping("{id}/password/reset")
    //@PreAuthorize("hasAuthority('reset_password')")
    public RespBody<?> resetPasswordAction(@PathVariable int id) {
        Admin admin = adminService.getAdminById(id);
        if (admin == null) {
            return RespBody.isFail().msg(ErrorEnum.ADMIN_ACCOUNT_NOT_FOUND);
        }
        return adminService.resetPassword(admin);
    }

    /**
     * view log of first page
     *
     * @return log page
     */
    @GetMapping("log/list")
    public ModelAndView listAllLog() {
        return listAllLog(1);
    }

    /**
     * view log by page
     *
     * @param page page number
     * @return log page
     */
    @GetMapping("log/list/{page}")
    public ModelAndView listAllLog(@PathVariable int page) {
        if (page < 1) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, Const.DEFAULT_ADMIN_PAGE_SIZE, Sort.Direction.DESC, "createTime");
        Map<String, Object> model = new HashMap<>(3);
        model.put("list", logService.listAllLog(pageable));
        model.put("page", page);
        model.put("url", "");
        return new ModelAndView("admin/log_list", model);
    }

    /**
     * view admin log
     *
     * @return admin log page
     */
    @GetMapping("log/list/admin")
    public ModelAndView listAdminLog() {
        return listAdminLog(1);
    }

    /**
     * view admin log
     *
     * @param page page number
     * @return admin log page
     */
    @GetMapping("log/list/admin/{page}")
    public ModelAndView listAdminLog(@PathVariable int page) {
        if (page < 1) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, Const.DEFAULT_ADMIN_PAGE_SIZE, Sort.Direction.DESC, "createTime");
        Map<String, Object> model = new HashMap<>(2);
        model.put("list", logService.listLogByType(Log.TypeEnum.ADMIN.getValue(), pageable));
        model.put("page", page);
        model.put("url", "admin/");
        return new ModelAndView("admin/log_list", model);
    }

    /**
     * view api log
     *
     * @return api log page
     */
    @GetMapping("log/list/api")
    public ModelAndView listApiLog() {
        return listApiLog(1);
    }

    /**
     * view api log
     *
     * @param page page number
     * @return api log page
     */
    @GetMapping("log/list/api/{page}")
    public ModelAndView listApiLog(@PathVariable int page) {
        if (page < 1) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, Const.DEFAULT_ADMIN_PAGE_SIZE, Sort.Direction.DESC, "createTime");
        Map<String, Object> model = new HashMap<>(2);
        model.put("list", logService.listLogByType(Log.TypeEnum.API.getValue(), pageable));
        model.put("page", page);
        model.put("url", "api/");
        return new ModelAndView("admin/log_list", model);
    }

    /**
     * constructor for object injection
     *
     * @param adminService Service for actions of admin
     * @param logService   Service for actions of log
     */
    public AdminController(AdminService adminService, LogService logService) {
        this.adminService = adminService;
        this.logService = logService;
    }

    private final AdminService adminService;
    private final LogService logService;
}
