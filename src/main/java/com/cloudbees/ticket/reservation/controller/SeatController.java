package com.cloudbees.ticket.reservation.controller;

import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.service.SeatService;
import com.cloudbees.ticket.reservation.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatusResponse> getSeatDetailsBySectionName(@RequestParam(value = "section", required = true, defaultValue = "A") String section) throws UnprocessableEntityException, BusinessServiceException {
        return ofNullable(seatService.getSeatDetailsBySectionName(section)).filter(seats -> !seats.isEmpty())
                .map(seats -> ResponseUtils.prepareSuccessResponse(seats, "Seat Details retrieved successfully"))
                .orElse(ResponseUtils.prepareNoRecordsFoundResponse("No records found"));
    }

    @PutMapping(value = "/reallocate")
    public ResponseEntity<HttpStatusResponse> reAllocateSeat(@RequestBody String email) throws UnprocessableEntityException, BusinessServiceException {
        seatService.reAllocateSeat(email);
        return ResponseUtils.prepareSuccessResponse(null,"Ticket Reallocated Successfully");
    }
}
