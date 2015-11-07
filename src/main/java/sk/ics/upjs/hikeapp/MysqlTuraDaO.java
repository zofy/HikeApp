package sk.ics.upjs.hikeapp;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlTuraDaO implements TuraDaO {

    private JdbcTemplate tmp;

    public MysqlTuraDaO() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost/hike");
        ds.setUser("root");
        ds.setPassword("bambinko");
        tmp = new JdbcTemplate();
        tmp.setDataSource(ds);
    }

    @Override
    public List<String> dajZoznamPohori() {
        return tmp.query("Select distinct pohorie from tura", new PohorieMapper());
    }

    @Override
    public List<String> dajRocneObdobie() {
        return tmp.query("Select distinct rocneObdobie from tura", new ROMapper());
    }

    public class PohorieMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            String pohorie = rs.getString("Pohorie");
            return pohorie;
        }

    }

    public class ROMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            String ro = rs.getString("RocneObdobie");
            return ro;
        }

    }

    public class TuraMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Tura t = new Tura();
            t.setPohorie(rs.getString("Pohorie"));
            t.setRocneObdobie(rs.getString("RocneObdobie"));
            t.setObtiaznost(rs.getInt("Obtiaznost"));
            t.setCasovaNarocnost(rs.getDouble("CasovaNarocnost"));
            t.setDlzka(rs.getDouble("Dlzka"));
            t.setHodnotenie(rs.getDouble("Hodnotenie"));
            t.setMimoChodnika(rs.getBoolean("MimoChodnik"));
            return t;
        }
    }

    @Override
    public void pridaj(Tura tura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tura> dajVsetky() {
        return tmp.query("select * from tura", new TuraMapper());
    }

    @Override
    public void vymaz(Tura tura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
