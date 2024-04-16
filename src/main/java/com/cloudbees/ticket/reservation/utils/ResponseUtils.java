package com.cloudbees.ticket.reservation.utils;

import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    private ResponseUtils() {}

    public static ResponseEntity<HttpStatusResponse> prepareSuccessResponse(Object data, String message) {
        HttpStatusResponse httpStatusResponse = new HttpStatusResponse(HttpStatus.OK.value(), message,data);
        return new ResponseEntity<>(httpStatusResponse,HttpStatus.OK);
    }

    public static ResponseEntity<HttpStatusResponse> prepareNoRecordsFoundResponse(String message) {
        HttpStatusResponse httpStatusResponse = new HttpStatusResponse(HttpStatus.OK.value(), message,null);
        return new ResponseEntity<>(httpStatusResponse,HttpStatus.OK);
    }

}
