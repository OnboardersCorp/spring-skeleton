package com.tastygear.external.api.feature.account

import com.tastygear.core.dto.account.AccountDto
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("account")
class AccountController(
    val accountService: AccountService,
) {

    @PostMapping("")
    fun signUp(@Valid @RequestBody requestDto: AccountDto.SignUpRequest): ResponseEntity<AccountDto.SignUpResponse> {
        return ok(accountService.signUp(requestDto))
    }

}
