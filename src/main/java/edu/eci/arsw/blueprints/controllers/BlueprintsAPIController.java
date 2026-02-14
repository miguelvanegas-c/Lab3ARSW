package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.response.ApiResponse;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public ApiResponse<Set<Blueprint>> getAll() {
        Set<Blueprint> blueprints = services.getAllBlueprints();
        return new ApiResponse<Set<Blueprint>>(HttpStatus.OK.value(), "The query was successful.",blueprints);
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "The author doesn't have blueprints")
    @Operation(summary = "Get all the blueprints from an author",
            description = "Return a blueprint list")
    // GET /blueprints/{author}
    @GetMapping("/{author}")
    public ApiResponse<Set<Blueprint>> byAuthor(@PathVariable String author) throws BlueprintNotFoundException{
        Set<Blueprint> blueprints = services.getBlueprintsByAuthor(author);
        return new ApiResponse<Set<Blueprint>>(HttpStatus.OK.value(), "The query was successful.",blueprints);

    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "The author doesn't have a blueprints with this name")
    @Operation(summary = "Get a blueprint from an author and a specific name",
            description = "Return a blueprint list")
    // GET /blueprints/{author}/{bpname}
    @GetMapping("/{author}/{bpname}")
    public ApiResponse<Blueprint> byAuthorAndName(@PathVariable String author, @PathVariable String bpname) throws BlueprintNotFoundException {
        Blueprint blueprint = services.getBlueprint(author, bpname);
        return new ApiResponse<Blueprint>(HttpStatus.OK.value(), "The query was successful.",blueprint);

    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Can´t create the blueprint")
    @Operation(summary = "Add a new blueprint",
            description = "Return the blueprint that was added")
    // POST /blueprints
    @PostMapping
    public ApiResponse<Blueprint> add(@Valid @RequestBody NewBlueprintRequest req) throws BlueprintPersistenceException{
        Blueprint bp = new Blueprint(req.author(), req.name(), req.points());
        services.addNewBlueprint(bp);
        return new ApiResponse<Blueprint>(HttpStatus.CREATED.value(),"The blueprint was created", bp);
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Can´t add the point")
    @Operation(summary = "Add a point to a blueprints",
            description = "Return if the operation was accepted")
    // PUT /blueprints/{author}/{bpname}/points
    @PutMapping("/{author}/{bpname}/points")
    public ApiResponse<?> addPoint(@PathVariable String author, @PathVariable String bpname,
                                      @RequestBody Point p) throws BlueprintNotFoundException{
        services.addPoint(author, bpname, p.getX(), p.getY());
        return new ApiResponse<>(HttpStatus.ACCEPTED.value(), "The point was added", null );

    }

    public record NewBlueprintRequest(
            @NotBlank String author,
            @NotBlank String name,
            @Valid java.util.List<Point> points
    ) { }
}
