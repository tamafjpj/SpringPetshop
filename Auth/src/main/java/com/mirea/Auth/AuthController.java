package com.mirea.Auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AuthController {
    @RequestMapping(value = "validate", method = POST)
    @ResponseBody
    public ResponseEntity<String> validateToken(@RequestHeader String token)
    {
        System.out.println(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
