package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // jdbc를 통해 database에 저장하기
    public Memo save(Memo memo){
        String sql = "insert into memo values (?, ?)";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    // 전체 데이터베이스 정보 가져오기
    public List<Memo> findAll(){
        String sql = "Select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    // id를 이용해 정보 가져오기
    public Optional<Memo> findById(int id){
        String sql = "Select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }

    // RowMapper : ResultSet 형식 -> Memo로 바꾸어 줌
    private RowMapper<Memo> memoRowMapper(){
        return (rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text"));
    }
}
