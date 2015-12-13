package sk.ics.upjs.hikeapp;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

public class MysqlFotkaDaO implements FotkaDaO {

    private JdbcTemplate tmp;

    public MysqlFotkaDaO() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost/hike");
        ds.setUser("root");
        ds.setPassword("bambinko");
        tmp = new JdbcTemplate();
        tmp.setDataSource(ds);
    }

    @Override
    public List<Image> dajFotkyDanejTury(Long idT) {
        String sql = "select fotka from fotky where idT=?";
        return tmp.query(sql, new Object[]{idT}, new FotkaMapper());
    }

    @Override
    public void pridajFotky(List<File> fotky, Long idT) {
        System.out.println(idT);
        InputStream imageIs = null;
        for (File image : fotky) {
            try {
                imageIs = new FileInputStream(image);
                LobHandler lobHandler = new DefaultLobHandler();
                tmp.update("insert into Fotky values(?,?,?,?)", new Object[]{
                    null,
                    idT,
                    image.getName(),
                    new SqlLobValue(imageIs, (int) image.length(), lobHandler)},
                        new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.BLOB});
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MysqlTuraDaO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    imageIs.close();
                } catch (IOException ex) {
                    Logger.getLogger(MysqlTuraDaO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public List<Image> dajFotkyUzivatela(Long idU) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class FotkaMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Image image = null;
            try {
                InputStream stream = rs.getBinaryStream("fotka");
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                if (stream != null) {
                    int a = stream.read();
                    while (a >= 0) {
                        output.write((char) a);
                        a = stream.read();
                    }
                    image = Toolkit.getDefaultToolkit().createImage(output.toByteArray());
                }
            } catch (IOException ex) {
                Logger.getLogger(MysqlTuraDaO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return image;
        }

    }

}
