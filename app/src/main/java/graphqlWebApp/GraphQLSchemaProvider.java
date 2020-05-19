package graphQLWebApp;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.DataFetcher;
import graphql.schema.StaticDataFetcher;
import graphql.schema.GraphQLObjectType;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLNonNull.nonNull;

import static graphql.Scalars.GraphQLString;
import static graphql.Scalars.GraphQLID;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphqlTypeComparatorRegistry.*;

class GraphQLSchemaProvider {

  private static GraphQLSchemaProvider instance;

  private final GraphQLSchema graphQLSchema;

  private GraphQLSchemaProvider(){
     GraphQLObjectType queryType = createQuery();

     GraphQLSchema schema = GraphQLSchema.newSchema()
       .query(queryType)
       .build();

     this.graphQLSchema = schema;
  }

  static GraphQLSchemaProvider getInstance() {
    if (instance == null) {
      instance = new GraphQLSchemaProvider();
    }
    return instance;
  }

  private GraphQLObjectType createQuery(){
      
      GraphQLObjectType bookType = newObject()
                                    .name("Book")
                                    .description("Book Data")
                                    .field(newFieldDefinition()
                                      .name("id")
                                      .description("Book ID")
                                      .type(GraphQLID)
                                    ) 
                                    .field(newFieldDefinition()
                                      .name("name")
                                      .description("Book Name")
                                      .type(GraphQLID)
                                    )
                                    .field(newFieldDefinition()
                                      .name("authorId")
                                      .description("Author ID of the Book")
                                      .type(GraphQLID)
                                    )
                                    .build();
      
      GraphQLObjectType authorType = newObject()
                                    .name("Author")
                                    .description("Author Data")
                                    .field(newFieldDefinition()
                                      .name("id")
                                      .description("Author ID")
                                      .type(GraphQLID)
                                    ) 
                                    .field(newFieldDefinition()
                                      .name("firstName")
                                      .description("Author's First Name")
                                      .type(GraphQLID)
                                    ) 
                                    .field(newFieldDefinition()
                                      .name("lastName")
                                      .description("Author's last Name")
                                      .type(GraphQLID)
                                    )
                                    .build();
      
      GraphQLObjectType queryType = newObject()
                                    .name("Query")
                                    .description("List of queries permissible")
                                    .field(newFieldDefinition()
                                      .name("books")
                                      .description("Return all books")
                                      .type(list(bookType))
                                      .dataFetcher(DataFetcherProvider.getBooksDataFetcher())
                                    ) 
                                    .field(newFieldDefinition()
                                      .name("book")
                                      .description("Return a book by id")
                                      .type(bookType)
                                      .argument(newArgument()
                                        .name("id")
                                        .description("id of the book, should be non null")
                                        .type(nonNull(GraphQLID)))
                                      .dataFetcher(DataFetcherProvider.getBookByIdDataFetcher())
                                      ) 
                                    .field(newFieldDefinition()
                                      .name("authors")
                                      .description("Return all authors")
                                      .type(list(authorType))
                                      .dataFetcher(DataFetcherProvider.getAuthorsDataFetcher())
                                    )
                                    .field(newFieldDefinition()
                                      .name("author")
                                      .description("Return a author by id")
                                      .type(authorType)
                                      .argument(newArgument()
                                        .name("id")
                                        .description("id of the book, should be non null")
                                        .type(nonNull(GraphQLID)))
                                      .dataFetcher(DataFetcherProvider.getAuthorByIdDataFetcher())
                                    )
                                    .build();                                  
      
      return queryType;
  }

  GraphQLSchema getSchema() {
    return graphQLSchema;
  }
}
