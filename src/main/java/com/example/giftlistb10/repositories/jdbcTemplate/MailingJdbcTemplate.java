package com.example.giftlistb10.repositories.jdbcTemplate;

import com.example.giftlistb10.dto.mailingList.MailingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MailingJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public MailingResponse rowMapper(ResultSet rs, int rowName) throws SQLException{
        return new MailingResponse(
                rs.getString("image"),
                rs.getString("nameMailing"),
                rs.getDate("createdAt").toLocalDate(),
                rs.getLong("mailingId"),
                rs.getString("text")
        );
    }

    public List<MailingResponse> getAllMailings(){
        String sql = """
                select\s
                    ml.image as image,
                    ml.name_mailing as nameMailing,
                    ml.created_at as createdAt,
                    ml.text,
                    ml.id as mailingId
                from mailing_lists ml;
                """;
        return jdbcTemplate.query(sql,this::rowMapper);
    }

    public Optional<MailingResponse> getByMailingId(Long mailingId) {
        String sql = """
                select\s
                    ml.image as image,
                    ml.name_mailing as nameMailing,
                    ml.created_at as createdAt,
                    ml.text,
                    ml.id as mailingId
                from mailing_lists ml
                where ml.id=?
                """;
        return jdbcTemplate.query(sql,this::rowMapper,mailingId).stream().findFirst();
    }
}