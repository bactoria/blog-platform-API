package me.bacto.blog.account.ui;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.ui.dto.SignUpRequestDto;
import me.bacto.blog.account.usecase.AccountService;
import me.bacto.blog.account.usecase.dto.AccountSaveRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupRestController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        AccountSaveRequestDto accountSaveRequestDto = AccountSaveRequestDto.builder()
                .userId(signUpRequestDto.getUserId())
                .password(signUpRequestDto.getPassword())
                .username(signUpRequestDto.getUsername())
                .build();
        accountService.signUp(accountSaveRequestDto);
        return ResponseEntity.ok().build();
    }

}
