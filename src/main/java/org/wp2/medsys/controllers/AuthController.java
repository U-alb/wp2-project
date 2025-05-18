
package org.wp2.medsys.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.wp2.medsys.domain.*;
import org.wp2.medsys.DTO.RegisterDTO;          // use the package name you created
import org.wp2.medsys.repositories.UserRepository;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository  repo;
    private final PasswordEncoder encoder;

    /* ---------- views ---------- */

    @GetMapping("/login")
    public String login() {                     // just returns the login template
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        // empty DTO for the form-binding; five arguments now (username, email,
        // password, dateOfBirth, role)
        model.addAttribute("userForm",
                new RegisterDTO("", "", "", null, Role.PATIENT));
        return "register";
    }

    @GetMapping("/portal")
    public String portalRedirect(Authentication authentication) {
        String username = authentication.getName(); // always works
        User user = repo.findByUsername(username).orElse(null);

        if (user == null) {
            return "redirect:/login?error=usernotfound";
        }

        return switch (user.getRole()) {
            case DOCTOR -> "redirect:/portal/doctorportal";
            case PATIENT -> "redirect:/portal/patientportal";
            default -> "redirect:/login?error=unknownrole";
        };
    }

    @GetMapping("/portal/doctorportal")
    public String doctorPortal() {
        return "portal/doctorportal";  // make sure this file exists: templates/portal/doctorportal.html
    }

    @GetMapping("/portal/patientportal")
    public String patientPortal() {
        return "portal/patientportal";  // make sure this file exists: templates/portal/patientportal.html
    }



    /* ---------- form POST ---------- */

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDTO dto) {

        User user = switch (dto.role()) {

            case PATIENT -> new Patient(
                    dto.username(),
                    dto.email(),
                    encoder.encode(dto.password()),
                    dto.dateOfBirth(),     // ⭐ now provided
                    null,                  // gender  (optional)
                    null,                  // phone   (optional)
                    null);                 // address (optional)

            case DOCTOR  -> new Doctor(
                    dto.username(),
                    dto.email(),
                    encoder.encode(dto.password()),
                    dto.dateOfBirth(),     // ⭐ now provided
                    null,                  // speciality (optional)
                    null);                 // licence    (optional)

            case ADMIN   -> throw new IllegalStateException(
                    "Admin signup is disabled — seed admin users manually");
        };

        repo.save(user);
        return "redirect:/login?registered";
    }
}
