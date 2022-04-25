package com.example.SpringMVC.form;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/form")
public class FormController {

    private final List<String> membership = Arrays.asList("Plat", "Gold", "Silver");

    @GetMapping()
    public String formPage() {
        return "form.edit-name";
    }

    @ResponseBody
    @GetMapping("/info")
    public String name(@RequestParam(defaultValue = "Foo") String name) {
        return name;
    }

    @GetMapping("/support")
    public String supportPage(Model model) {
        model.addAttribute("supportDetails", new FooForm());
        model.addAttribute("membershipList", membership);
        return "form.support";
    }

    @PostMapping("/support")
    public String postSupport(@Valid @ModelAttribute FooForm details, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("supportDetails", details);
            model.addAttribute("membershipList", membership);
            return "form.support";
        }

        model.addAttribute("details", details);
        return "form.submit";
    }

}
