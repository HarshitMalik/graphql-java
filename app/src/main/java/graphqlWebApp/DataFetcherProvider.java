package graphQLWebApp;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;
import java.lang.*;
import java.util.*;
import java.io.*;

class DataFetcherProvider {

    private static List<Map<String, String>> books = new ArrayList<Map<String, String>>();
    private static List<Map<String, String>> authors = new ArrayList<Map<String, String>>();

    public static DataFetcher getBooks() {
        return dataFetchingEnvironment -> {
            System.out.println("Inside getBooks()");
            return books;
        };
    }

    public static DataFetcher getBookById() {
        return dataFetchingEnvironment -> {
            System.out.println("Inside getBookById()");
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
            System.out.println("Inside getAuthors()");
            return authors;
        };
    }

    public static DataFetcher getAuthorById() {
        return dataFetchingEnvironment -> {
            System.out.println("Inside getAuthorById()");
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
            System.out.println("Inside getAuthorOfBooks()");
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
            System.out.println("Inside getBooksOfAuthor()");
            Map<String,String> author = dataFetchingEnvironment.getSource();
            String authorId = author.get("id");
            return books
                    .stream()
                    .filter(book -> book.get("authorId").equals(authorId))
                    .collect(Collectors.toList());
        };
    }

    public static DataFetcher getCopiesCount() {
        return dataFetchingEnvironment -> {
            return CompletableFuture.supplyAsync(
                () -> {
                  System.out.println("Inside getCopiesCount");
                  System.out.println("Sleeping for 3 seconds");
                  try {
                        Thread.sleep(3000);
                  } catch (Exception e) {
                    System.out.println(e);
                  }
                  System.out.println("Fetching data");
                  return 5;
            });
        };
    }

    public static DataFetcher addBook() {
        return dataFetchingEnvironment -> {
            System.out.println("Inside addBooks()");
            Map<String,String> book = dataFetchingEnvironment.getArgument("input");
            books.add(book);
            return book;
        };
    }

    public static DataFetcher addAuthor() {
        return dataFetchingEnvironment -> {
            System.out.println("Inside addAuthor()");
            Map<String,String> author = dataFetchingEnvironment.getArgument("input");
            authors.add(author);
            return author;
        };
    }
}
