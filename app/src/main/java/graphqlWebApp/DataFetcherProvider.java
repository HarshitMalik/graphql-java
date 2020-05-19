package graphQLWebApp;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import com.google.common.collect.ImmutableMap;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class DataFetcherProvider {

    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "1",
                    "name", "Book 1",
                    "authorId", "1"),
            ImmutableMap.of("id", "2",
                    "name", "Book 2",
                    "authorId", "1"),
            ImmutableMap.of("id", "3",
                    "name", "Book 3",
                    "authorId", "2"),
            ImmutableMap.of("id", "4",
                    "name", "Book 4",
                    "authorId", "2"),
            ImmutableMap.of("id", "5",
                    "name", "Book 5",
                    "authorId", "3"),
            ImmutableMap.of("id", "6",
                    "name", "Book 6",
                    "authorId", "3")
          );

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "1",
                    "firstName", "author-1",
                    "lastName", "author-1"),
            ImmutableMap.of("id", "2",
                    "firstName", "author-2",
                    "lastName", "author-2"),
            ImmutableMap.of("id", "3",
                    "firstName", "author-3",
                    "lastName", "author-3")
          );

    public static DataFetcher getBooks() {
        return dataFetchingEnvironment -> {
            return books;
        };
    }

    public static DataFetcher getBookById() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public static DataFetcher getAuthors() {
        return dataFetchingEnvironment -> {
            return authors;
        };
    }

    public static DataFetcher getAuthorById() {
        return dataFetchingEnvironment -> {
            String authorId = dataFetchingEnvironment.getArgument("id");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public static DataFetcher getAuthorOfBook() {
        return dataFetchingEnvironment -> {
            Map<String,String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }
    
    public static DataFetcher getBooksOfAuthor() {
        return dataFetchingEnvironment -> {
            Map<String,String> author = dataFetchingEnvironment.getSource();
            String authorId = author.get("id");
            return books
                    .stream()
                    .filter(book -> book.get("authorId").equals(authorId))
                    .collect(Collectors.toList());
        };
    }
}
