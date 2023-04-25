package com.example.chatapplication.repo;

import com.example.chatapplication.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByUsername(String username);

}

