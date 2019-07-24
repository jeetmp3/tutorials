package tutorials.graphql.java.intro;

import tutorials.graphql.java.service.GraphQLService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Jitendra Singh.
 */
public class Launcher {

    static GraphQLService graphQLService = new GraphQLService();
    public static void main(String[] args) throws Exception {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Type \"exit\" to terminate");
            while(true) {
                String query = reader.readLine();
                if("exit".equalsIgnoreCase(query)) {
                    break;
                }
                Object response = graphQLService.executeQuery(query);
                System.out.println(response);
                System.out.println("===================================");
            }
        }
    }

}
