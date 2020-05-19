package graphQLWebApp;

import javax.servlet.annotation.WebServlet;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.kickstart.servlet.GraphQLConfiguration;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends GraphQLHttpServlet {

     @Override
  protected GraphQLConfiguration getConfiguration() {
    return GraphQLConfigurationProvider.getInstance().getConfiguration();
  }
}
