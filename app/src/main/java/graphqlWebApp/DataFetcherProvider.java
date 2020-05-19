package graphQLWebApp;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.stream.Collectors;
import java.util.*;

class DataFetcherProvider {

    private static List<Map<String, String>> books = new ArrayList<Map<String, String>>();
    private static List<Map<String, String>> authors = new ArrayList<Map<String, String>>();

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

    public static DataFetcher addBook() {
        return dataFetchingEnvironment -> {
            Map<String,String> book = dataFetchingEnvironment.getArgument("input");
            books.add(book);
            return book;
        };
    }

    public static DataFetcher addAuthor() {
        return dataFetchingEnvironment -> {
            Map<String,String> author = dataFetchingEnvironment.getArgument("input");
            authors.add(author);
            return author;
        };
    }
}
