package com.example.demo.ExchangeRate.controller;

import com.example.demo.ExchangeRate.model.CurrencyTable;
import com.example.demo.ExchangeRate.model.Rate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {
    private final RestTemplate restTemplate;
    private final String NBPResourceUrl = "https://api.nbp.pl/api";
    private final String responseFormat = "?json";

    public ExchangeRateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CurrencyTable.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Gateway",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found currency.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Bad Gateway.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "504",
                    description = "Gateway timeout.",
                    content = @Content
            )
    })
    @Operation(summary = "Get currency")
    @GetMapping("/{currency}")
    public ResponseEntity<OptionalDouble> getCurrency(@PathVariable(value = "currency") String currency, @RequestParam(required = false, defaultValue = "1") Integer numberOfDays) {
        LocalDate finishDate = LocalDate.now();
        LocalDate startDate  = finishDate.minusDays(numberOfDays);

        CurrencyTable currencyTable = restTemplate.getForEntity(
                this.NBPResourceUrl + "/exchangerates/rates/a/" + currency + "/" + startDate + "/" + finishDate + "/" + this.responseFormat,
                CurrencyTable.class
        ).getBody();

        return ResponseEntity.ok(currencyTable.getRates().stream().mapToDouble(Rate::getMid).average());
    }
}
