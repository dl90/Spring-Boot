package local.restweb.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MsgController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/i18n")
    public String hello(
//            @RequestHeader(name = "Accept-Language", required = false) Locale locale
    ) {
        return messageSource.getMessage(
                "hello.message",
                null,
                "language not supported",
                LocaleContextHolder.getLocale());
    }

}
