package com.zb.Account;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Slf4j
public class AccountDto {
    private String accountNumber;
    private String nickname;
    private LocalDateTime registeredAt;

    public void log(){
        log.error("error is occurred");
    }

}
