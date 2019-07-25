package tutorials.graphql.java.service;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.stereotype.Service;
import tutorials.common.utils.ResourceUtils;
import tutorials.graphql.java.models.User;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jitendra Singh.
 */
@Service
public class GraphQLService {

    private GraphQL graphQL;

    @PostConstruct
    private void init() throws Exception {
        String schemaString = ResourceUtils.readClasspathResourceContent("users.graphql");

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefRegistry = schemaParser.parse(schemaString);

        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefRegistry, buildRuntimeWiring());
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder.dataFetchers(buildDataFetchers()))
                .build();
    }

    private Map<String, DataFetcher> buildDataFetchers() {
        Map<String, DataFetcher> dataFetchers = new HashMap<>();
        dataFetchers.put("hello", new StaticDataFetcher("Welcome to GraphQL world."));
        dataFetchers.put("users", env -> {
            Object obj = env.getArgument("name");
            return User.of(obj != null ? obj.toString() : "John", 28, "India");
        });
        return dataFetchers;
    }

    public ExecutionResult executeQuery(String graphQLQuery) {
        return graphQL.execute(graphQLQuery);
    }
}
