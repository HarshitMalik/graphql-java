package graphQLWebApp;

import graphql.execution.AsyncExecutionStrategy;
import graphql.kickstart.execution.GraphQLQueryInvoker;
import graphql.kickstart.execution.config.DefaultExecutionStrategyProvider;
import graphql.kickstart.servlet.GraphQLConfiguration;

class GraphQLConfigurationProvider {

  private static GraphQLConfigurationProvider instance;

  private final GraphQLConfiguration configuration;

  private GraphQLConfigurationProvider() {
    configuration = GraphQLConfiguration
        .with(GraphQLSchemaProvider.getInstance().getSchema())
        .with(GraphQLQueryInvoker.newBuilder()
            .withExecutionStrategyProvider(new DefaultExecutionStrategyProvider(
                new AsyncExecutionStrategy(),
                null,
                null))
            .build())
        .build();
  }

  static GraphQLConfigurationProvider getInstance() {
    if (instance == null) {
      instance = new GraphQLConfigurationProvider();
    }
    return instance;
  }

  GraphQLConfiguration getConfiguration() {
    return configuration;
  }
}
