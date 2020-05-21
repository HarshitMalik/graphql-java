package graphQLWebApp;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLInputObjectType;

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLNonNull.nonNull;
import static graphql.schema.GraphQLTypeReference.typeRef;

import static graphql.Scalars.GraphQLString;
import static graphql.Scalars.GraphQLID;
import static graphql.Scalars.GraphQLInt;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

class GraphQLSchemaProvider {
      
  static GraphQLObjectType bookType = newObject()
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
                                      .name("author")
                                      .description("Author of the Book")
                                      .type(typeRef("Author"))
                                      .dataFetcher(DataFetcherProvider.getAuthorOfBook())
                                    )
                                    .field(newFieldDefinition()
                                      .name("copies")
                                      .description("Count of total copies present")
                                      .type(GraphQLInt)
                                      .dataFetcher(DataFetcherProvider.getCopiesCount())
                                    )
                                    .build();
      
  static GraphQLObjectType authorType = newObject()
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
                                    .field(newFieldDefinition()
                                      .name("books")
                                      .description("Author's books")
                                      .type(list(typeRef("Book")))
                                      .dataFetcher(DataFetcherProvider.getBooksOfAuthor())
                                    )
                                    .build();
      
  static GraphQLObjectType queryType = newObject()
                                    .name("Query")
                                    .description("List of queries permissible")
                                    .field(newFieldDefinition()
                                      .name("books")
                                      .description("Return all books")
                                      .type(list(bookType))
                                      .dataFetcher(DataFetcherProvider.getBooks())
                                    ) 
                                    .field(newFieldDefinition()
                                      .name("book")
                                      .description("Return a book by id")
                                      .type(bookType)
                                      .argument(newArgument()
                                        .name("id")
                                        .description("id of the book, should be non null")
                                        .type(nonNull(GraphQLID)))
                                      .dataFetcher(DataFetcherProvider.getBookById())
                                      ) 
                                    .field(newFieldDefinition()
                                      .name("authors")
                                      .description("Return all authors")
                                      .type(list(authorType))
                                      .dataFetcher(DataFetcherProvider.getAuthors())
                                    )
                                    .field(newFieldDefinition()
                                      .name("author")
                                      .description("Return a author by id")
                                      .type(authorType)
                                      .argument(newArgument()
                                        .name("id")
                                        .description("id of the book, should be non null")
                                        .type(nonNull(GraphQLID)))
                                      .dataFetcher(DataFetcherProvider.getAuthorById())
                                    )
                                    .build();

  static GraphQLInputObjectType inputBookType = newInputObject()
                                    .name("BookInput")
                                    .description("Input for Book")
                                    .field(newInputObjectField()
                                      .name("id")
                                      .description("The id of the Book")
                                      .type(nonNull(GraphQLID)))
                                    .field(newInputObjectField()
                                      .name("name")
                                      .description("The name of the Book")
                                      .type(nonNull(GraphQLString)))
                                    .field(newInputObjectField()
                                      .name("authorId")
                                      .description("The ID of the author of the Book")
                                      .type(nonNull(GraphQLID)))
                                    .build();

  static GraphQLInputObjectType inputAuthorType = newInputObject()
                                    .name("AuthorInput")
                                    .description("Input for Author")
                                    .field(newInputObjectField()
                                      .name("id")
                                      .description("The id of the Author")
                                      .type(nonNull(GraphQLID)))
                                    .field(newInputObjectField()
                                      .name("firstName")
                                      .description("The first name of the Author")
                                      .type(nonNull(GraphQLString)))
                                    .field(newInputObjectField()
                                      .name("lastName")
                                      .description("The last name of the Author")
                                      .type(nonNull(GraphQLString)))
                                    .build();

  static GraphQLObjectType mutationType = newObject()
                                    .name("Mutations")
                                    .description("List of mutations permissible")
                                    .field(newFieldDefinition()
                                      .name("addBook")
                                      .description("Add a new book")
                                      .type(bookType)
                                      .argument(newArgument()
                                        .name("input")
                                        .description("data of the book to be added")
                                        .type(nonNull(inputBookType)))
                                      .dataFetcher(DataFetcherProvider.addBook())
                                    )
                                    .field(newFieldDefinition()
                                      .name("addAuthor")
                                      .description("Add a new author")
                                      .type(authorType)
                                      .argument(newArgument()
                                        .name("input")
                                        .description("data of the author to be added")
                                        .type(nonNull(inputAuthorType)))
                                      .dataFetcher(DataFetcherProvider.addAuthor())
                                    )
                                    .build();

  static GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
       .query(queryType)
       .mutation(mutationType)
       .build();                                
  
  public static GraphQLSchema getSchema(){
      return graphQLSchema;
  }
}
