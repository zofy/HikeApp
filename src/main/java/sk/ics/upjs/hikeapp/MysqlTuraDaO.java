package sk.ics.upjs.hikeapp;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.Blob;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

    // zatial je metoda tu: na spracovanie vyberu tur
    public String spracujVyberTur(Stack<String> zoznamAtributov) {
        StringBuilder buff = new StringBuilder();
        buff.append("select * from tura");
        if (!zoznamAtributov.isEmpty()) {
            buff.append(" where ");
            while (!zoznamAtributov.isEmpty()) {
                if (zoznamAtributov.peek().equals("casovaNarocnost") || zoznamAtributov.peek().equals("obtiaznost")) {
                    buff.append(zoznamAtributov.pop() + "<=? and ");
                } else {
                    buff.append(zoznamAtributov.pop() + "=? and ");
                }
            }
            buff.delete(buff.length() - 4, buff.length());
        }
        return buff.toString();
    }

    @Override
    public List<Tura> dajVybraneTury(Stack<String> nazvyAtributov, Stack<String> hodnotyAtributov) {
        Object[] hodnoty = new Object[hodnotyAtributov.size()];
        int i = 0;
        while (!hodnotyAtributov.isEmpty()) {
            hodnoty[i] = hodnotyAtributov.pop();
            i++;
        }
        String sql = spracujVyberTur(nazvyAtributov);
        return tmp.query(sql, hodnoty, new TuraMapper());
    }

    @Override
    public String dajPopis(long idT) {
        List<String> popis = tmp.query("select popis from Popis where idT=?", new Object[]{idT}, new PopisMapper());

        return popis.get(0);
    }

    @Override
    public Blob dajDetail(long idT) {
        List<Blob> detail = tmp.query("select detail from Popis where idT=?", new Object[]{idT}, new DetailMapper());
        if (!detail.isEmpty()) {
            return detail.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String dajNazovTury(long idT) {
        List<String> nazov = tmp.query("select nazov from popis where idT=?", new Object[]{idT}, new NazovMapper());
        return nazov.get(0);
    }

    @Override
    public List<Image> dajFotky(long idT) {
        String sql = "select fotka from fotky where idT=?";
        return tmp.query(sql, new Object[]{idT}, new FotkaMapper());
    }

    public class FotkaMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Image image = null;
            try {
                InputStream stream = rs.getBinaryStream("fotka");
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                int a = stream.read();
                while (a >= 0) {
                    output.write((char) a);
                    a = stream.read();
                }
                image = Toolkit.getDefaultToolkit().createImage(output.toByteArray());
            } catch (IOException ex) {
                Logger.getLogger(MysqlTuraDaO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return image;
        }

    }

    public class NazovMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            String nazovTury = rs.getString("nazov");
            return nazovTury;
        }

    }

    public class DetailMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Blob detail = (Blob) rs.getBlob("detail");
            return detail;
        }

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

    public class PopisMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            String popis = rs.getString("Popis");
            return popis;
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
            t.setCiel(rs.getString("ciel"));
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
