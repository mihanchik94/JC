package ru.itk.spring_data.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itk.spring_data.model.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Book save(Book book) {
        String query = "INSERT INTO books (title, author, publication_year) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYear().getValue());
            return statement;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            book.setId(key.longValue());
        } else {
            throw new DataAccessException("Failed to retrieve generated key") {};
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT id, title, author, publication_year FROM books";
        return jdbcTemplate.query(query, new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                int yearValue = rs.getInt("publication_year");
                book.setYear(Year.of(yearValue));
                return book;
            }
        });
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT id, title, author, publication_year FROM books WHERE id = ?";
        RowMapper<Book> rowMapper = (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            int yearValue = rs.getInt("publication_year");
            book.setYear(Year.of(yearValue));
            return book;
        };
        try {
            Book book = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int update(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
        return jdbcTemplate.update(query, book.getTitle(), book.getAuthor(),
                book.getYear().getValue(), book.getId());
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }
}
