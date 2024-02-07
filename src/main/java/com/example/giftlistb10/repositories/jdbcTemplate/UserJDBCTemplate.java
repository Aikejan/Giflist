package com.example.giftlistb10.repositories.jdbcTemplate;

import com.example.giftlistb10.dto.myFriends.MyFriendsResponse;
import com.example.giftlistb10.dto.userInfo.UserResponse;
import com.example.giftlistb10.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJDBCTemplate {

    private final JdbcTemplate jdbcTemplate;

    private UserResponse rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserResponse(
                resultSet.getLong("userId"),
                resultSet.getString("fullName"),
                resultSet.getString("country") == null ? null : Country.valueOf(resultSet.getString("country")),
                resultSet.getDate("dateOfBirth") == null ? null : resultSet.getDate("dateOfBirth").toLocalDate(),
                resultSet.getString("email"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("image"),
                resultSet.getString("clothingSize") == null ? null : ClothingSize.valueOf(resultSet.getString("clothingSize")),
                resultSet.getString("shoeSize") == null ? null : ShoeSize.valueOf(resultSet.getString("shoeSize")),
                resultSet.getString("hobby"),
                resultSet.getString("important"),
                resultSet.getString("linkFacebook"),
                resultSet.getString("linkVkontakte"),
                resultSet.getString("linkInstagram"),
                resultSet.getString("linkTelegram"),
                resultSet.getBoolean("isBlock")
        );
    }

    private UserResponse rowMapperUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserResponse(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("image"),
                resultSet.getInt("wishSum"),
                resultSet.getBoolean("isBlock")
        );
    }

    private UserResponse searchUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserResponse(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("image")
        );
    }

    public List<UserResponse> getAllUsers() {
        String sql = """
                select u.id,
                concat(u.first_name,' ',u.last_name) as name,
                ui.image as image,\s
                cast(count(w) as int) as wishSum,
                u.is_block as isBlock
                from users u\s
                join public.user_info ui on u.user_info_id = ui.id\s
                left join public.wishes w on u.id = w.owner_id where u.role = 'USER'\s
                group by u.id, concat(u.first_name,' ',u.last_name),ui.image;
                """;
        return jdbcTemplate.query(sql, this::rowMapperUser);
    }

    public List<UserResponse> searchUserByName(String userName) {
        String sql = """
                SELECT
                u.id,
                CONCAT(u.first_name, ' ', u.last_name) AS name,
                ui.image AS image
                FROM users u
                JOIN public.user_info ui ON u.user_info_id = ui.id
                JOIN public.wishes w ON u.id = w.owner_id
                WHERE u.role = 'USER' AND
                    (u.first_name ILIKE '%' || ? || '%' OR u.last_name ILIKE '%' || ? || '%')
                GROUP BY u.id, CONCAT(u.first_name, ' ', u.last_name), ui.image;
                """;
        return jdbcTemplate.query(sql, this::searchUser, userName, userName);
    }

    public UserResponse getOwnProfile(String email) {
        String sql = """
                select u.id as userId,
                concat(u.first_name,' ',u.last_name) as fullName,
                ui.country as country,
                ui.date_of_birth as dateOfBirth, 
                u.email as email,
                ui.phone_number as phoneNumber,
                ui.image as image, 
                ui.clothing_size as clothingSize,
                ui.shoe_size as shoeSize, 
                ui.hobby as hobby,
                ui.important as important,
                ui.link_face_book as linkFacebook,
                ui.link_vkontakte as linkVkontakte,
                ui.link_instagram as linkInstagram,
                ui.link_telegram linkTelegram,
                u.is_block as isBlock 
                from users u 
                join public.user_info ui on ui.id = u.user_info_id 
                where u.email=?;
                    """;
        return jdbcTemplate.query(sql, this::rowMapper, email)
                .stream()
                .findFirst()
                .orElse(new UserResponse());
    }

    public UserResponse getProfileByUserId(Long id) {
        String sql = """
                select u.id as userId,
                concat(u.first_name,' ',u.last_name) as fullName,
                ui.country as country,
                ui.date_of_birth as dateOfBirth, 
                u.email as email,
                ui.phone_number as phoneNumber,
                ui.image as image, 
                ui.clothing_size as clothingSize,
                ui.shoe_size as shoeSize, 
                ui.hobby as hobby,
                ui.important as important,
                ui.link_face_book as linkFacebook,
                ui.link_vkontakte as linkVkontakte,
                ui.link_instagram as linkInstagram,
                ui.link_telegram linkTelegram,
                u.is_block as isBlock 
                from users u 
                join public.user_info ui on ui.id = u.user_info_id 
                where u.id=?;
                    """;
        return jdbcTemplate.query(sql, this::rowMapper, id)
                .stream()
                .findFirst()
                .orElse(new UserResponse());
    }

   public List<MyFriendsResponse> getAllUserRequest(Long userId) {
        String sql = """
    SELECT ru.id as friendId,
           CONCAT(ru.first_name, ' ', ru.last_name) AS nameFriend,
           COUNT(w.id) AS countWish,
           COUNT(h.id) AS countHoliday,
           rui.image 
    FROM users u
    JOIN users_request r ON u.id = r.user_id
    JOIN users ru ON ru.id = r.request_id
    LEFT JOIN user_info rui ON rui.id = ru.user_info_id
    LEFT JOIN holidays h ON ru.id = h.user_id
    LEFT JOIN wishes w ON w.holiday_id = h.id
    WHERE u.id = ?
    GROUP BY ru.id, CONCAT(u.first_name, ' ', u.last_name), rui.image 
    """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new MyFriendsResponse(
                resultSet.getLong("friendId"),
                resultSet.getString("nameFriend"),
                resultSet.getInt("countWish"),
                resultSet.getInt("countHoliday"),
                resultSet.getString("image")
        ), userId);
    }

    public List<MyFriendsResponse> getAllUserFriends(Long userId) {
        String sql = """
     SELECT f.id as friendId,
            CONCAT(f.first_name, ' ', f.last_name) AS nameFriend,
            COUNT(h.id) AS countHoliday,
            COUNT(w.id) AS countWish,
            ui.image AS friendImage
     FROM users u
          join public.user_friends uf on u.id = uf.user_id
          left join users f on f.id = uf.friends
          left join user_info fui on fui.id = f.user_info_id
          join public.holidays h on f.id = h.user_id
          join public.wishes w on f.id = w.owner_id
          join user_info ui on ui.id = f.user_info_id
     WHERE u.id = ?
          GROUP BY f.id, CONCAT(f.first_name, ' ', f.last_name),ui.image;
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new MyFriendsResponse(
                resultSet.getLong("friendId"),
                resultSet.getString("nameFriend"),
                resultSet.getInt("countWish"),
                resultSet.getInt("countHoliday"),
                resultSet.getString("friendImage")
        ), userId);
    }
}