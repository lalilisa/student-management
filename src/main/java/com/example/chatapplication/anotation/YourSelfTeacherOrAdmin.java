package com.example.chatapplication.anotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("(authentication.principal.username==#username and hasAuthority('SUBJECT_TEACHER'))or hasAuthority('ADMIN')" )
public @interface YourSelfTeacherOrAdmin {
}