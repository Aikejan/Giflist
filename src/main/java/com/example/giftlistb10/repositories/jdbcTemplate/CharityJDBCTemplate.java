package com.example.giftlistb10.repositories.jdbcTemplate;

import com.example.giftlistb10.dto.charity.CharityResponse;
import com.example.giftlistb10.enums.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
@Service
public class CharityJDBCTemplate {

    private final JdbcTemplate jdbcTemplate;

    private CharityResponse rowMapper4getById(ResultSet rs, int rowName) throws SQLException {
        return new CharityResponse(
                rs.getString("fullName"),
                rs.getString("reservoirFullName"),
                rs.getString("userImage"),
                rs.getString("nameCharity"),
                rs.getLong("charityId"),
                rs.getBoolean("isBlock"),
                rs.getString("description"),
                rs.getString("ownerPhoneNumber"),
                rs.getString("charityImage"),
                rs.getString("category") == null ? null : Category.valueOf(rs.getString("category")),
                rs.getString("subCategory") == null ? null : SubCategory.valueOf(rs.getString("subCategory")),
                rs.getDate("createdAt") == null ? null : rs.getDate("createdAt").toLocalDate(),
                rs.getString("condition") == null ? null : Condition.valueOf(rs.getString("condition")),
                rs.getString("status") == null ? null : Status.valueOf(rs.getString("status")),
                rs.getString("bookedUserImage"),
                rs.getLong("charityReservoirId"),
                rs.getLong("ownerId")
        );
    }
    private CharityResponse rowMapper(ResultSet rs, int rowName) throws SQLException {
        return new CharityResponse(
                rs.getString("fullName"),
                rs.getString("reservoirFullName"),
                rs.getString("userImage"),
                rs.getString("nameCharity"),
                rs.getLong("charityId"),
                rs.getBoolean("isBlock"),
                rs.getString("charityImage"),
                rs.getDate("createdAt") == null ? null : rs.getDate("createdAt").toLocalDate(),
                rs.getString("condition") == null ? null : Condition.valueOf(rs.getString("condition")),
                rs.getString("status") == null ? null : Status.valueOf(rs.getString("status")),
                rs.getString("reservoirImage"),
                rs.getLong("reservoirId"),
                rs.getLong("userId")
        );
    }

    private CharityResponse mapCharityResponse(ResultSet rs) throws SQLException {
        CharityResponse result = new CharityResponse();
        result.setFullName(rs.getString("fullName"));
        result.setUserImage(rs.getString("userImage"));
        result.setNameCharity(rs.getString("nameCharity"));
        result.setCreatedAt(rs.getDate("createdAt") == null ? null : rs.getDate("createdAt").toLocalDate());
        result.setCondition(rs.getString("condition") == null ? null : Condition.valueOf(rs.getString("condition")));
        result.setStatus(rs.getString("status") == null ? null : Status.valueOf(rs.getString("status")));
        result.setCategory(rs.getString("category") == null ? null : Category.valueOf(rs.getString("category")));
        result.setSubCategory(rs.getString("subCategory") == null ? null : SubCategory.valueOf(rs.getString("subCategory")));
        result.setCharityImage(rs.getString("charityImage"));
        result.setBookedUserImage(rs.getString("bookedUserImage"));
        result.setCharityId(rs.getLong("charityId"));
        result.setIsBlock(rs.getBoolean("isBlock"));
        result.setCharityReservoirId(rs.getLong("charityReservoirId"));
        result.setUserId(rs.getLong("userId"));
        return result;
    }

    public Optional<CharityResponse> getCharityById(Long id) {
        String sql = """ 
                SELECT
                   CONCAT(u.first_name, ' ', u.last_name) as fullName,
                    ui.phone_number as ownerPhoneNumber,
                    u.id as ownerId,
                    ui.image as userImage,
                    c.name_charity as nameCharity,
                    c.id as charityId,
                    c.is_block as isBlock,
                    c.created_at as createdAt,
                    c.condition as condition,
                    c.image as charityImage,
                    c.description as description,
                    c.status as status,
                    c.category as category,
                    c.sub_category as subCategory,
                    bui.image as bookedUserImage,
                    bu.id as charityReservoirId,
                    CONCAT(bu.first_name, ' ', bu.last_name) as reservoirFullName
                FROM users u
                    JOIN user_info ui ON u.user_info_id = ui.id
                    JOIN charities c ON u.id = c.owner_id
                    LEFT JOIN users bu ON c.reservoir_id = bu.id
                    LEFT JOIN user_info bui ON bu.user_info_id = bui.id
                WHERE c.id = ?;
                """;
        return jdbcTemplate.query(sql, this::rowMapper4getById, id).stream().findFirst();
    }

    public List<CharityResponse> getAllMyCharities(Long userId) {
        String sql = """
                  SELECT
                         CONCAT(u.first_name, ' ', u.last_name) as fullName,
                         u.id as userId,
                         ui.image as userImage,
                         c.is_block as isBlock,
                         c.image as charityImage,
                         c.name_charity as nameCharity,
                         c.condition as condition,
                         c.created_at as createdAt,
                         c.id as charityId,
                         c.status as status,
                         rui.image as reservoirImage,
                         ru.id as reservoirId,
                         CONCAT(ru.first_name, ' ', ru.last_name) as reservoirFullName
                  FROM users u
                  join user_info ui on ui.id = u.user_info_id
                           JOIN charities c on c.owner_id=u.id
                           LEFT JOIN users ru ON ru.id = c.reservoir_id
                           LEFT JOIN user_info rui ON ru.user_info_id = rui.id
                  WHERE u.id = ?
                     """;
        return jdbcTemplate.query(sql, this::rowMapper, userId);
    }

    public List<CharityResponse> getAllFriendsCharities(Long userId) {
        String sql = """
                SELECT
                    f.id AS userId,
                    concat(f.first_name, ' ', f.last_name) AS fullName,
                    fui.image AS userImage,
                    c.name_charity AS nameCharity,
                    c.id as charityId,
                    c.is_block as isBlock,
                    c.image AS charityImage,
                    c.condition AS condition,
                    c.created_at AS createdAt,
                    c.status AS status,
                    ru.id AS reservoirId,
                    rui.image AS reservoirImage,
                  CONCAT(ru.first_name, ' ', ru.last_name) as reservoirFullName
                FROM
                    users u
                   JOIN user_friends uf ON u.id = uf.user_id
                   JOIN users f ON f.id = uf.friends
                   JOIN user_info fui ON fui.id = f.user_info_id
                   JOIN charities c ON c.owner_id = f.id
                   LEFT JOIN users ru ON ru.id = c.reservoir_id
                   LEFT JOIN user_info rui ON rui.id = ru.user_info_id
                WHERE
                    u.id = ?
                """;
        return jdbcTemplate.query(sql, this::rowMapper, userId);
    }

    public List<CharityResponse> getAllCharities() {
        String sql = """
                SELECT ou.id                                    AS userId,
                       concat(ou.first_name, ' ', ou.last_name) AS fullName,
                       oui.image                                AS userImage,
                       c.name_charity                           AS nameCharity,
                       c.is_block as isBlock,
                       c.id                                     as charityId,
                       c.image                                  AS charityImage,
                       c.condition                              AS condition,
                       c.created_at                             AS createdAt,
                       c.status                                 AS status,
                       ru.id                                    AS reservoirId,
                       rui.image                                AS reservoirImage,
                       CONCAT(ru.first_name, ' ', ru.last_name) as reservoirFullName
                FROM users ou
                         join user_info oui on ou.user_info_id = oui.id
                         join charities c on ou.id = c.owner_id
                         left join users ru on ru.id = c.reservoir_id
                         left join user_info rui on ru.user_info_id = rui.id;
                         """;
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    public List<CharityResponse> searchCharity(
            String value,
            Condition condition,
            Category category,
            SubCategory subCategory,
            Country country
    ) {
        String sql = """
                    SELECT
                        c.id as charityId,
                        concat(u.first_name, ' ', u.last_name) AS fullName,
                        ui.image AS userImage,
                        c.name_charity AS nameCharity,
                        c.created_at AS createdAt,
                        c.condition AS condition,
                        c.image AS charityImage,
                        c.status AS status,
                        c.is_block as isBlock,
                        c.category,
                        c.sub_category AS subCategory,
                        bui.image AS bookedUserImage,
                        c.reservoir_id AS charityReservoirId,
                        c.owner_id AS userId
                    FROM users u
                        JOIN user_info ui ON u.user_info_id = ui.id
                        JOIN charities c ON u.id = c.owner_id
                        LEFT JOIN users bu ON c.reservoir_id = bu.id
                        LEFT JOIN user_info bui ON bu.user_info_id = bui.id
                    WHERE
                        (c.name_charity ILIKE ? || '%' OR ? IS NULL)
                        AND (('ALL' = ?) OR (c.condition = ? OR ? IS NULL))
                        AND (c.category = ? OR ? IS NULL)
                        AND (c.sub_category = ? OR ? IS NULL)
                        AND (ui.country = ? OR ? IS NULL);
                """;

        return jdbcTemplate.query(
                sql,
                new Object[]{
                        value, value,
                        condition, condition, condition,
                        category, category, subCategory, subCategory,
                        country, country
                },
                new int[]{
                        Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR
                },
                (rs, rowNum) -> mapCharityResponse(rs));
    }
}