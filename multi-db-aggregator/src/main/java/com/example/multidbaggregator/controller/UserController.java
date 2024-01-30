package com.example.multidbaggregator.controller;

import com.example.multidbaggregator.model.UserEntity;
import com.example.multidbaggregator.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users. Supports optional filters by name and surname.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))))
    })
    @GetMapping("/users")
    public List<UserEntity> getAll(
            @Parameter(name = "name", description = "Filter users by name.", required = false, example = "John")
            @RequestParam(name = "name", required = false) String name,

            @Parameter(name = "surname", description = "Filter users by surname.", required = false, example = "Doe")
            @RequestParam(name = "surname", required = false) String surname) {
        return userService.getAll(name, surname);
    }
}
