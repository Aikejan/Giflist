package com.example.giftlistb10.repositories.jdbcTemplate;

import com.example.giftlistb10.dto.holiday.HolidayResponse;
import com.example.giftlistb10.dto.wish.WishesResponse;
import com.example.giftlistb10.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HolidayJDBCTemplate {

    private final JdbcTemplate jdbcTemplate;

    private HolidayResponse rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return new HolidayResponse(
                resultSet.getLong("id"),
                resultSet.getString("nameHoliday"),
                resultSet.getDate("dateOfHoliday") == null ? null : resultSet.getDate("dateOfHoliday").toLocalDate(),
                resultSet.getString("image")
        );
    }

    private WishesResponse mapRow(ResultSet response, int rowNum) throws SQLException {
      return new WishesResponse(
              response.getLong("id"),
              response.getString("name_wish"),
              response.getString("image"),
              response.getString("status_wish") == null ? null : Status.valueOf(response.getString("status_wish")),
              response.getString("name_holiday"),
              response.getDate("dateOfHoliday") == null ? null : response.getDate("dateOfHoliday").toLocalDate(),
              response.getString("reservoirImage"),
              response.getString("linkToWish"));
    }

    private HolidayResponse rowMap(ResultSet resultSet, int rowNum) throws SQLException {
        return new HolidayResponse(
                resultSet.getLong("id"),
                resultSet.getString("nameHoliday"),
                resultSet.getDate("dateOfHoliday") == null ? null : resultSet.getDate("dateOfHoliday").toLocalDate(),
                resultSet.getString("imageHoliday"),
                resultSet.getLong("friendId"),
                resultSet.getString("fullName"),
                resultSet.getString("friendImage"));
    }

    public HolidayResponse getHolidayById(Long holidayId) {
        String sql = """
               SELECT
                   h.id AS id,
                   h.name_holiday   AS nameHoliday,
                   h. date_of_holiday AS dateOfHoliday,
                   h. image AS imageHoliday,
                   u.id as friendId,
                   concat(u.first_name,' ', u.last_name) as fullName,
                   ui.image as friendImage
                   FROM holidays h
                   join  users u on u.id = h.user_id
                   join user_info ui on ui.id = u.user_info_id
                   WHERE h.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, this::rowMap, holidayId);
    }

    public List<HolidayResponse> getAllHolidaysUserById(Long userId) {
        String sql = """
                SELECT
                h.id AS id,
                h.name_holiday  AS nameHoliday,
                h.date_of_holiday AS dateOfHoliday,
                h.image AS image
                FROM users u join holidays h on u.id = h.user_id
                where h.user_id = ?
                """;
        return jdbcTemplate.query(sql, this::rowMapper, userId);
    }

    public List<WishesResponse> getAllHolidayOfWishesById(Long holidayId) {
        String sql = """
            SELECT
                    w.id,
                    w.image,
                    w.name_wish,
                    h.name_holiday,
                    w.date_of_holiday AS dateOfHoliday,
                    w.status_wish,
                    rui.image AS reservoirImage,
                    u.id = wu.is_all_ready_in_wish_list AS isAllReadyWishList,
                    w.link_to_gift AS linkToWish
                    FROM users u
                    JOIN wishes w ON u.id = w.owner_id
                    JOIN holidays h ON h.id = w.holiday_id
                    LEFT JOIN users ru ON ru.id = w.reservoir_id
                    LEFT JOIN user_info rui ON ru.user_info_id = rui.id
                    left join wish_is_all_ready_in_wish_list wu on wu.wish_id = w.id
                    WHERE h.id = ?
            """;
        return jdbcTemplate.query(sql, this::mapRow, holidayId);
    }

    public  List<HolidayResponse> getAllHolidaysFriendsById(Long userId) {
        String sql = """
                   SELECT 
                    h.id,
                    concat(f.first_name, ' ', f.last_name) as fullName,
                    h.name_holiday as nameHoliday,
                    h.image as imageHoliday,
                    h.date_of_holiday as dateOfHoliday,
                    fi.image as friendImage,
                    h.id as holidayId,
                    f.id as friendId
                    from users u
                    LEFT join user_friends uf on u.id = uf.user_id
                    LEFT join users f on f.id = uf.friends
                    LEFT join user_info fi on fi.id = f.user_info_id
                    JOIN holidays h on h.user_id = f.id
                    WHERE u.id = ?;
                   """;
        return jdbcTemplate.query(sql, this::rowMap, userId);
    }

    public  List<HolidayResponse> searchHolidayByName(String holidayName) {
        String sql = """
                   SELECT 
                    h.id,
                    concat(f.first_name, ' ', f.last_name) as fullName,
                    h.name_holiday as nameHoliday,
                    h.image as imageHoliday,
                    h.date_of_holiday as dateOfHoliday,
                    fi.image as friendImage,
                    h.id as holidayId,
                    f.id as friendId
                    from users u
                    LEFT join user_friends uf on u.id = uf.user_id
                    LEFT join users f on f.id = uf.friends
                    LEFT join user_info fi on fi.id = f.user_info_id
                    JOIN holidays h on h.user_id = f.id
                    WHERE h.name_holiday ILIKE '%' || ? || '%';
                   """;
        return jdbcTemplate.query(sql, this::rowMap, holidayName);
    }
}