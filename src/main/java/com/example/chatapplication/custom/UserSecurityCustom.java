package com.example.chatapplication.custom;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserSecurityCustom extends User implements UserDetailCustom {

    private List<Long> ids;
    private Long accountId;

    public UserSecurityCustom(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            List<Long> ids) {
        super(username, password, authorities);
        this.ids=ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public Long accountId() {
        return accountId;
    }

    @Override
    public List<Long> ids() {
        return ids;
    }
}
