package base.repository.storage;

import base.book.AudioBook;
import base.book.Book;
import base.book.EBook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBStorage implements Storable {

    List<Book> books = new ArrayList<>();
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/book-manager";
    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    public DBStorage() {
        init();
    }

    @Override
    public void init() {
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE BOOK " +
                    "(id BIGINT not NULL, " +
                    " name VARCHAR(50), " +
                    " author VARCHAR(50), " +
                    " isbn BIGINT, " +
                    " publishedDate DATE," +
                    " fileSize VARCHAR(10)," +
                    " language VARCHAR(10)," +
                    " playtime INTEGER," +
                    " dtype VARCHAR(10)," +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        } finally {
            try{
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            } catch(SQLException e) {
            }
        }
    }

    @Override
    public boolean save(List<Book> books) {
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);

            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM BOOK");
            books.stream().forEach(
                (book) -> {
                    try {
                        String sql = "INSERT INTO BOOK " + "VALUES" + "(?,?,?,?,?,?,?,?,?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setLong(1, book.getId());
                        pstmt.setString(2, book.getName());
                        pstmt.setString(3, book.getAuthor());
                        pstmt.setLong(4, book.getIsbn());
                        pstmt.setDate(5, Date.valueOf(book.getPublishedDate()));
                        if (book instanceof AudioBook) {
                            pstmt.setString(6, ((AudioBook) book).getFileSize());
                            pstmt.setString(7, ((AudioBook) book).getLanguage());
                            pstmt.setInt(8, ((AudioBook) book).getPlayTime());
                            pstmt.setString(9, "audio");
                        } else if (book instanceof EBook) {
                            pstmt.setString(6, ((EBook) book).getFileSize());
                            pstmt.setNull(7, Types.NULL);
                            pstmt.setNull(8, Types.NULL);
                            pstmt.setString(9, "ebook");
                        } else {
                            pstmt.setNull(6, Types.NULL);
                            pstmt.setNull(7, Types.NULL);
                            pstmt.setNull(8, Types.NULL);
                            pstmt.setString(9, "book");
                        }

                        pstmt.executeUpdate();
                        conn.commit();
                    } catch (SQLException e) {
                        try {
                            if(conn!=null) conn.close();
                            conn.rollback();
                        } catch (SQLException ex) {
                        }
                    } finally {
                        try {
                            if(stmt!=null) stmt.close();
                            if(pstmt!=null) pstmt.close();
                        } catch (SQLException e) {

                        }
                    }
                }
            );
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        } finally {
            try{
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            } catch(SQLException e) {}
        }
        return true;
    }

    @Override
    public List<Book> load() {
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM BOOK";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name
                Long id  = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                Long isbn = rs.getLong("isbn");
                LocalDate publishedDate = rs.getDate("publishedDate").toLocalDate();
                String fileSize = rs.getString("fileSize");
                String language = rs.getString("language");
                int playTime = rs.getInt("playTime");
                String dType = rs.getString("dtype");

                if (dType.equals("audio")) {
                    books.add(new AudioBook(id, name, author, isbn, publishedDate, fileSize, language, playTime));
                } else if (dType.equals("Ebook")) {
                    books.add(new EBook(id, name, author, isbn, publishedDate, fileSize));
                } else {
                    books.add(new Book(id, name, author, isbn, publishedDate));
                }
            }
            // STEP 5: Clean-up environment
            rs.close();
        } catch(SQLException e) {
        } catch(Exception e) {
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            } catch(SQLException e) {
            }
        }
        return books;
    }

    @Override
    public void backUp() {

    }
}
