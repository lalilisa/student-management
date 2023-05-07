package com.example.chatapplication.common;

import com.example.chatapplication.domain.Points;
import com.example.chatapplication.domain.Subject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Random;

@Slf4j
public class Utils {

    public static String convertPointBase4ToCharacter(Double base4){
        if(base4 ==4)
            return "A+";
        if(base4 >= 3.7)
            return  "A";
        if(base4 >= 3.5)
            return "B+";
        if(base4 >= 3)
            return "B";
        if(base4 >= 2.5)
            return "C+";
        if(base4 >= 2)
            return "C";
        if(base4 >= 1.5)
            return "D+";
        if(base4 >= 1)
            return  "D";
        return "F";
    }
    public static Double convertPointBase10ToBase4(Double base10){
        if(base10 <= 10 && base10>=8.95)
            return  4.0;
        if(base10 >= 8.45)
            return  3.7;
        if(base10 >= 7.95)
            return 3.5;
        if(base10 >= 6.95)
            return 3.0;
        if(base10 >= 6.45)
            return 2.5;
        if(base10 >= 5.45)
            return 2.0;
        if(base10 >= 4.95)
            return 1.5;
        if(base10 >= 4)
            return  1.0;
        return 0.0;
    }

    public static Double caculateFinalExamPoint(Points points, Subject subject){
        if(points==null || points.getCc()==null ||points.getCk()==null)
            return (double) 0;
        Double cc=points.getCc();
        Double bt= points.getBt();
        Double th=points.getTh();
        Double kt=points.getKt();
        Double ck=points.getCk();
        if(subject.getPrecentth()==0)
                th= (double) 0;
        if(subject.getPrecentkt()==0)
            kt= (double) 0;
        if(subject.getPrecentbt()==0)
            bt= (double) 0;
           return     ( cc*subject.getPrecentcc()+
                        bt*subject.getPrecentbt()+
                        th*subject.getPrecentth()+
                        kt*subject.getPrecentkt()+
                        points.getCk()*subject.getPrecentck()) / (double) 100;
    }

    public static String autogenPassword(){
        return new Random().ints(10, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static void main(String[] args) {
        System.out.println(autogenPassword());
    }

    public static String hashPassword(String plainText){
        int salt = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(salt, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainText);
    }
}
