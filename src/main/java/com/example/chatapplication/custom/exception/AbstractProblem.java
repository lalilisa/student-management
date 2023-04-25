package com.example.chatapplication.custom.exception;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

import java.util.Collections;
import java.util.Map;

@Getter
public class AbstractProblem extends ThrowableProblem {
    private final String title;
    private final StatusType status;
    private final String detail;
    private final Map<String, Object> parameters;

    public AbstractProblem(String title, StatusType status) {
        this(title, status, null);
    }

    @JsonCreator
    public AbstractProblem(@JsonProperty("title") String title, @JsonProperty("status") String status, @JsonProperty("detail") String detail) {
        this(title, new HttpStatusAdapter(HttpStatus.valueOf(Integer.parseInt(status))), detail, Collections.emptyMap());
    }

    public AbstractProblem(String title, StatusType status, String detail) {
        this(title, status, detail, Collections.emptyMap());
    }

    public AbstractProblem(String title, StatusType status, String detail, Map<String, Object> parameters) {
        this(title, status, detail, parameters, null);
    }

    public AbstractProblem(String title, StatusType status, String detail, Map<String, Object> parameters, ThrowableProblem cause) {
        super(cause);
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.parameters = parameters;
    }
}
