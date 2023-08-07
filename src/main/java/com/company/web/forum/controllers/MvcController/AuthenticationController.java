package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.AuthenticationFailureException;
import com.company.web.forum.exceptions.BlockedUserException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.models.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationHelper authenticationHelper;
    @Autowired
    public AuthenticationController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model)
    {
        model.addAttribute("login", new LoginDto());
        return "loginOld";
    }
    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") LoginDto dto, BindingResult bindingResult,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "loginOld";
        }

        try {
            authenticationHelper.verifyAuthentication(dto.getUsername(), dto.getPassword());
            session.setAttribute("currentUser", dto.getUsername());
            return "redirect:/";
        } catch (AuthenticationFailureException e) {
            bindingResult.rejectValue("username", "auth error", e.getMessage());
            return "loginOld";
        } catch (BlockedUserException e) {
            bindingResult.rejectValue("username", "auth error", e.getMessage());
            return "BlockedUserView";
        }
    }
    @GetMapping("/logout")
    public String handleLogout(HttpSession session)
    {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }
}
