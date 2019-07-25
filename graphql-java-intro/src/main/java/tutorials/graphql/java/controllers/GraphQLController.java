package tutorials.graphql.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tutorials.graphql.java.models.GraphQLRequest;
import tutorials.graphql.java.service.GraphQLService;

import java.util.Map;

/**
 * @author Jitendra Singh.
 */
@RestController
public class GraphQLController {

    private final GraphQLService graphQLService;

    @Autowired
    public GraphQLController(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @PostMapping("/graphql")
    public Map<String, Object> executeQuery(@RequestBody GraphQLRequest request) {
        return graphQLService.executeQuery(request.getQuery()).toSpecification();
    }
}
