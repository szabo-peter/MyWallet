package hu.flowacademy.MyWallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/deleteMe")
public class DeleteMeController {

    @GetMapping
    public String doTheLogic() {
        log.info("A request received to delete Me ...");
        return "Delete me!";
    }

}