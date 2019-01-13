package com.travel.book.easy.travelbookeasy.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.BasicDto;
import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.VerificationTokenService;

@RestController
@RequestMapping(path = "/verification")
public class VerificationTokenController {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @RequestMapping(path = "/{token}", method = RequestMethod.GET)
    public ResponseEntity<String> verifyToken(@PathVariable("token") String token) {
        UserDto result = null;
        try {
            result = verificationTokenService.verifyToken(token);
        } catch (ApiException ae) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Your account is activated!", HttpStatus.OK);
    }


    @RequestMapping(path = "/{token}", method = RequestMethod.POST)
    public ResponseEntity<BasicDto<String>> verifyTokenForResetPassword(@PathVariable("token") String token, 
            @RequestBody UserDto userDto) {
        try {
            verificationTokenService.resetUserPassword(token, userDto.getPassword());
        } catch (ApiException e) {
            return new ResponseEntity<>(new BasicDto<String>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BasicDto<String>("Password changed successfully!"),HttpStatus.OK);
    }
 
}
