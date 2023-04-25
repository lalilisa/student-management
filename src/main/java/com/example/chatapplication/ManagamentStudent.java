package com.example.chatapplication;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.domain.Account;
import com.example.chatapplication.domain.Teacher;
import com.example.chatapplication.repo.AccountRepository;
import com.example.chatapplication.repo.TeacherClassRepository;
import com.example.chatapplication.repo.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ManagamentStudent {

    public static void main(String[] args) {
        SpringApplication.run(ManagamentStudent.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner init(AccountRepository accountRepository, TeacherRepository teacherRepository) {
        return args -> {
            Account a=accountRepository.findAccountByUsername("admin");
            if(a==null){
                Account admin=new Account();
                admin.setUsername("admin");
                admin.setPassword((new BCryptPasswordEncoder().encode("ductrung")));
                Account newAccount=  accountRepository.save(admin);
                Teacher teacher= Teacher.builder().accountId(newAccount.getId())
                        .code("ADMINPROS")
                        .gender(1)
                        .role(Category.Role.ADMIN)
                        .build();
                teacherRepository.save(teacher);
            }
        };
    }
}
