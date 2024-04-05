package com.cloudbees.ticket.reservation.controller;

import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.service.ReceiptService;
import com.cloudbees.ticket.reservation.utils.ResponseUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("tickets/receipt")
public class ReceiptController {

    private ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatusResponse> getReceiptByEmail(@RequestParam(value = "email", required = true) String email) throws UnprocessableEntityException, BusinessServiceException {
        return ofNullable(receiptService.getReceiptByEmail(email))
                .filter(Objects::nonNull).map(seat -> ResponseUtils.prepareSuccessResponse(seat, "Receipt details are retireved successfully!"))
                .orElse(ResponseUtils.prepareNoRecordsFoundResponse("No receipt found"));
    }
}
