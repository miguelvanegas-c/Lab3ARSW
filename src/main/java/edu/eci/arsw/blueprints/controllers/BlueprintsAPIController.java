package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.dto.PointDTO;
import edu.eci.arsw.blueprints.model.entity.Point;
import edu.eci.arsw.blueprints.model.dto.BlueprintDTO;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.response.ApiResponse;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/blueprints")
public class BlueprintsAPIController {

    private final BlueprintsServices services;

    public BlueprintsAPIController(BlueprintsServices services) { this.services = services; }

    // GET /blueprints
    @Operation(summary = "Get all the blueprints in the system",
            description = "Return a blueprint list")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @GetMapping
    public ApiResponse<Set<BlueprintDTO>> getAll() {
        Set<BlueprintDTO> blueprints = services.getAllBlueprints();
        return new ApiResponse<>(HttpStatus.OK.value(), "The query was successful.", blueprints);
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "The author doesn't have blueprints")
    @Operation(summary = "Get all the blueprints from an author",
            description = "Return a blueprint list")
    // GET /blueprints/{author}
    @GetMapping("/{author}")
    public ApiResponse<Set<BlueprintDTO>> byAuthor(@PathVariable String author) throws BlueprintNotFoundException{
        Set<BlueprintDTO> blueprints = services.getBlueprintsByAuthor(author);
        return new ApiResponse<>(HttpStatus.OK.value(), "The query was successful.", blueprints);

    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "The author doesn't have a blueprints with this name")
    @Operation(summary = "Get a blueprint from an author and a specific name",
            description = "Return a blueprint list")
    // GET /blueprints/{author}/{bpname}
    @GetMapping("/{author}/{bpname}")
    public ApiResponse<BlueprintDTO> byAuthorAndName(@PathVariable String author, @PathVariable String bpname) throws BlueprintNotFoundException {
        BlueprintDTO blueprint = services.getBlueprint(author, bpname);
        return new ApiResponse<>(HttpStatus.OK.value(), "The query was successful.", blueprint);

    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Can´t create the blueprint")
    @Operation(summary = "Add a new blueprint",
            description = "Return the blueprint that was added")
    // POST /blueprints
    @PostMapping
    public ApiResponse<BlueprintDTO> add(@Valid @RequestBody BlueprintDTO req) throws BlueprintPersistenceException, BlueprintNotFoundException{
        services.addNewBlueprint(req);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "The blueprint was created", req);
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Can´t add the point")
    @Operation(summary = "Add a point to a blueprints",
            description = "Return if the operation was accepted")
    // PUT /blueprints/{author}/{bpname}/points
    @PutMapping("/{author}/{bpname}/points")
    public ApiResponse<?> addPoint(@PathVariable String author, @PathVariable String bpname,
                                      @RequestBody PointDTO p) throws BlueprintNotFoundException{
        services.addPoint(author, bpname, p);
        return new ApiResponse<>(HttpStatus.ACCEPTED.value(), "The point was added", null );

    }


}
