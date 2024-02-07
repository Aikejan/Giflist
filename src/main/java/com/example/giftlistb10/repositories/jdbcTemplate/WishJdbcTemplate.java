package com.example.giftlistb10.repositories.jdbcTemplate;

import com.example.giftlistb10.dto.booking.WishBookingResponse;
import com.example.giftlistb10.dto.complaint.ComplaintResponse;
import com.example.giftlistb10.dto.wish.WishesResponse;
import com.example.giftlistb10.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public ComplaintResponse getAllComplainWishes(ResultSet rs, int rowName) throws SQLException {
        return new ComplaintResponse(
                rs.getLong("ownerId"),
                rs.getString("ownerName"),
                rs.getString("ownerImage"),
                rs.getLong("wishId"),
                rs.getString("nameWish"),
                rs.getString("wishImage"),
                rs.getString("holidayName"),
                rs.getDate("dateOfHoliday").toLocalDate(),
                rs.getBoolean("isBlock"),
                rs.getString("statusWish") != null ? Status.valueOf(rs.getString("statusWish")) : null,
                rs.getString("reservoirImage")
        );
    }

    public WishesResponse rowMapper(ResultSet rs, int rowName) throws SQLException {
        return new WishesResponse(
                rs.getLong("id"),
                rs.getString("nameWish"),
                rs.getString("image"),
                rs.getString("statusWish") != null ? Status.valueOf(rs.getString("statusWish")) : null,
                rs.getString("holidayName"),
                rs.getDate("dateOfHoliday").toLocalDate(),
                rs.getString("linkToWish"),
                rs.getBoolean("isBlock")
        );
    }

    public WishBookingResponse bookingWish(ResultSet rs, int rowName) throws SQLException {
        return new WishBookingResponse(
                rs.getLong("id"),
                rs.getString("nameWish"),
                rs.getString("holidayName"),
                rs.getDate("dateOfHoliday").toLocalDate(),
                rs.getString("image"),
                rs.getString("statusWish") != null ? Status.valueOf(rs.getString("statusWish")) : null,
                rs.getLong("ownerId"),
                rs.getString("ownerName"),
                rs.getString("userImage"),
                rs.getLong("reservoirId"),
                rs.getBoolean("isAllReadyWishList")
        );
    }

    public WishesResponse rowMapper2(ResultSet rs, int rowName) throws SQLException {
        return new WishesResponse(
                rs.getLong("wishId"),
                rs.getString("wishName"),
                rs.getString("wishImage"),
                rs.getString("wishStatus") != null ? Status.valueOf(rs.getString("wishStatus")) : null,
                rs.getString("description"),
                rs.getString("holidayName"),
                rs.getDate("dateOfHoliday").toLocalDate(),
                rs.getLong("ownerId"),
                rs.getString("fullName"),
                rs.getBoolean("isBlock"),
                rs.getString("userImage"),
                rs.getLong("reservoirId"),
                rs.getString("reservoirImage"),
                rs.getString("linkToWish")
        );
    }

    public WishesResponse rowMapperWishesFriends(ResultSet rs, int rowName) throws SQLException {
            return new WishesResponse(
                    rs.getLong("wishId"),
                    rs.getString("wishName"),
                    rs.getString("wishImage"),
                    rs.getString("wishStatus") != null ? Status.valueOf(rs.getString("wishStatus")) : null,
                    rs.getString("holidayName"),
                    rs.getDate("dateOfHoliday").toLocalDate(),
                    rs.getLong("ownerId"),
                    rs.getString("fullName"),
                    rs.getString("reservoirFullName"),
                    rs.getString("userImage"),
                    rs.getLong("reservoirId"),
                    rs.getString("reservoirImage"),
                    rs.getBoolean("isAllReadyWishList"),
                    rs.getString("linkToWish"));
        }

    public List<WishesResponse> getAllWishes() {
        String sql = """
                SELECT
                    w.id,
                    w.name_wish AS nameWish,
                    w.image AS image,
                    w.status_wish AS statusWish,
                    h.name_holiday AS holidayName,
                    h.date_of_holiday AS dateOfHoliday,
                    w.link_to_gift AS linkToWish,
                    w.is_block AS isBlock
                FROM wishes w
                LEFT JOIN holidays h ON w.holiday_id = h.id
                """;
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    public Optional<WishesResponse> getWishById(Long id) {
        String sql = """
               SELECT w.id                                   AS wishId,
                      w.name_wish                            AS wishName,
                      w.image                                AS wishImage,
                      w.status_wish                          AS wishStatus,
                      w.description                          AS description,
                      h.name_holiday                         AS holidayName,
                      h.date_of_holiday                      AS dateOfHoliday,
                      u.id                                   AS ownerId,
                      concat(u.first_name, ' ', u.last_name) AS fullName,
                      w.is_block                             AS isBlock,
                      ui.image                               AS userImage,
                      ru.id                                  AS reservoirId,
                      rui.image                              as reservoirImage,
                      w.link_to_gift                         AS linkToWish
               FROM wishes w
                        left join users u on u.id = w.owner_id
                        left join user_info ui on ui.id = u.user_info_id
                        join holidays h on h.id = w.holiday_id
                        left join users ru on ru.id = w.reservoir_id
                        left join user_info rui on rui.id = ru.user_info_id
               where w.id = ?
                """;
        return jdbcTemplate.query(sql, this::rowMapper2, id).stream().findFirst();
    }

    public List<WishesResponse> getAllWishesFriends(Long userId) {
        String sql = """
                  SELECT
                        w.id AS wishId,
                        w.name_wish AS wishName,
                        w.image AS wishImage,
                        w.status_wish AS wishStatus,
                        h.name_holiday AS holidayName,
                        h.date_of_holiday AS dateOfHoliday,
                        f.id AS ownerId,
                        concat(f.first_name ,' ' ,f.last_name) AS fullName,
                        fui.image AS userImage,
                        rui.id AS reservoirId,
                        rui.image as reservoirImage,
                        concat(ru.first_name,' ',ru.last_name) as reservoirFullName,
                        u.id = wu.is_all_ready_in_wish_list AS isAllReadyWishList,
                        w.link_to_gift AS linkToWish
                FROM
                      users u
                        join public.user_friends uf on u.id = uf.user_id
                        left join users f on f.id = uf.friends
                        left join user_info fui on fui.id = f.user_info_id
                        join wishes w on w.owner_id = f.id
                        join holidays h on h.id = w.holiday_id
                        left join users ru on ru.id = w.reservoir_id
                        left join user_info rui on rui.id = ru.id
                        left join wish_is_all_ready_in_wish_list wu on wu.wish_id = w.id
                    where u.id = ?
                """;
        return jdbcTemplate.query(sql, this::rowMapperWishesFriends, userId);
    }

    public List<WishesResponse> getAllMyWishes(Long userId) {
        String sql = """
                SELECT
                                  w.id AS wishId,
                                  w.name_wish AS wishName,
                                  w.image AS wishImage,
                                  w.status_wish AS wishStatus,
                                  w.is_block AS isBlock,
                                  w.description AS description,
                                  h.name_holiday AS holidayName,
                                  h.date_of_holiday AS dateOfHoliday,
                                  u.id AS ownerId,
                                  concat(u.first_name ,' ' ,u.last_name) AS fullName,
                                  fui.image AS userImage,
                                  rui.id AS reservoirId,
                                  concat(ru.first_name,' ',ru.last_name) as reservoirFullName,
                                  rui.image as reservoirImage,
                                  u.id = wu.is_all_ready_in_wish_list AS isAllReadyWishList,
                                  w.link_to_gift AS linkToWish
                              FROM
                                  users u
                                      left join user_info fui on fui.id = u.user_info_id
                                      join wishes w on w.owner_id = u.id
                                      join holidays h on h.id = w.holiday_id
                                      left join users ru on ru.id = w.reservoir_id
                                      left join user_info rui on rui.id = ru.id
                                      left join wish_is_all_ready_in_wish_list wu on wu.wish_id = w.id
                              where u.id = ?
                """;
        return jdbcTemplate.query(sql, this::rowMapper2, userId);
    }

    public List<WishesResponse> searchWishByName(String wishName) {
        String sql = """
                SELECT
                    w.id AS wishId,
                    w.name_wish AS wishName,
                    w.image AS wishImage,
                    w.status_wish AS wishStatus,
                    h.name_holiday AS holidayName,
                    h.date_of_holiday AS dateOfHoliday,
                    u.id AS ownerId,
                    concat(u.first_name ,' ' ,u.last_name) AS fullName,
                    fui.image AS userImage,
                    rui.id AS reservoirId,
                    concat(ru.first_name,' ',ru.last_name) as reservoirFullName,
                    rui.image as reservoirImage,
                    u.id = wu.is_all_ready_in_wish_list AS isAllReadyWishList,
                    w.link_to_gift AS linkToWish
                FROM
                    users u
                        left join user_info fui on fui.id = u.user_info_id
                        join wishes w on w.owner_id = u.id
                        join holidays h on h.id = w.holiday_id
                        left join users ru on ru.id = w.reservoir_id
                        left join user_info rui on rui.id = ru.id
                        left join wish_is_all_ready_in_wish_list wu on wu.wish_id = w.id
                where w.name_wish ILIKE '%' || ? || '%'
                """;
        return jdbcTemplate.query(sql, this::rowMapperWishesFriends, wishName);
    }

    public List<WishBookingResponse> getBookingWishes(String email) {
        String sql = """
                SELECT
                  w.id AS id,
                  w.name_wish AS nameWish,
                  h.name_holiday AS holidayName,
                  h.date_of_holiday AS dateOfHoliday,
                  w.image as image,
                  w.status_wish AS statusWish,
                  w.owner_id AS ownerId,
                  concat(u.first_name ,' ' ,u.last_name) AS ownerName,
                  fui.image AS userImage,
                  rui.id AS reservoirId,
                  rui.id = wu.is_all_ready_in_wish_list AS isAllReadyWishList
              FROM
                  users u
                      left join user_info fui on fui.id = u.user_info_id
                      join wishes w on w.owner_id = u.id
                      join holidays h on h.id = w.holiday_id
                      left join users ru on ru.id = w.reservoir_id
                      left join user_info rui on rui.id = ru.id
                      left join wish_is_all_ready_in_wish_list wu on wu.wish_id = w.id
              where ru.email = ?
                """;
        return jdbcTemplate.query(sql, this::bookingWish, email);
    }

    public List<ComplaintResponse> getAllWishesComplain() {
        String sql = """
                SELECT
                       w.id as wishId,
                       w.name_Wish as nameWish,
                       w.image as wishImage,
                       w.is_Block as isBlock,
                       w.status_Wish as statusWish,
                       h.name_holiday as holidayName,
                       h.date_of_holiday as dateOfHoliday,
                       ow.id as ownerId,
                       concat(ow.first_name,' ',ow.last_name) as ownerName,
                       ui.image as ownerImage,
                       rui.image as reservoirImage
                from wishes w
                join public.complains c on w.id = c.wish_id
                join holidays h on w.holiday_id = h.id
                join users ow on w.owner_id = ow.id
                join user_info ui on ow.user_info_id = ui.id
                left join users res on w.reservoir_id = res.id
                left join user_info rui on rui.id = res.user_info_id;
                """;
        return jdbcTemplate.query(sql, this::getAllComplainWishes);
    }
}