package sk.ics.upjs.hikeapp;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.Blob;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

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
        List<String> popis = tmp.query("select popis from Tura where idT=?", new Object[]{idT}, new PopisMapper());

        return popis.get(0);
    }

    @Override
    public Blob dajDetail(long idT) {
        List<Blob> detail = tmp.query("select detail from tura where idT=?", new Object[]{idT}, new DetailMapper());
        if (!detail.isEmpty()) {
            return detail.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String dajNazovTury(long idT) {
        List<String> nazov = tmp.query("select nazov from tura where idT=?", new Object[]{idT}, new NazovMapper());
        return nazov.get(0);
    }

    @Override
    public List<Image> dajFotky(long idT) {
        String sql = "select fotka from fotky where idT=?";
        return tmp.query(sql, new Object[]{idT}, new FotkaMapper());
    }

    @Override
    public void pridajFotky(List<File> fotky) {
        List<Tura> t = tmp.query("select * from tura order by idT desc limit 0,1", new TuraMapper());
        Long idT = t.get(0).getIdT();
        System.out.println(idT);
        InputStream imageIs = null;
        for (File image : fotky) {
            try {
                //final File image = new File("C:\\puppy.jpg");
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
            String popis = null;
            Tura t = new Tura();
            t.setIdT(rs.getLong("idT"));
            t.setPohorie(rs.getString("Pohorie"));
            t.setRocneObdobie(rs.getString("RocneObdobie"));
            t.setObtiaznost(rs.getInt("Obtiaznost"));
            t.setCasovaNarocnost(rs.getDouble("CasovaNarocnost"));
            t.setDlzka(rs.getDouble("Dlzka"));
            t.setHodnotenie(rs.getDouble("Hodnotenie"));
            t.setMimoChodnika(rs.getBoolean("MimoChodnik"));
            t.setCiel(rs.getString("ciel"));
            t.setNazov(rs.getString("Nazov"));
            popis = (rs.getString("Popis"));
            t.setPopis(spracujPopisDoListu(popis));
            t.setDetail(rs.getString("Detail"));
            return t;
        }
    }

    public LinkedList<String> spracujPopisDoListu(String popis) {
        LinkedList<String> p = new LinkedList<String>();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < popis.length(); i++) {
            if (popis.charAt(i) == '-') {
                p.add(ret.toString());
                ret.delete(0, ret.length());
            } else {
                ret.append(popis.charAt(i));
            }
        }
        p.add(ret.toString());
        return p;
    }

    public String spracujPopisDoStringu(LinkedList<String> bodyTury) {
        StringBuilder ret = new StringBuilder();
        for (String bod : bodyTury) {
            if (ret.length() == 0) {
                ret.append(bod);
            } else {
                ret.append("-" + bod);
            }
        }
        return ret.toString();
    }

    @Override
    public void pridaj(Tura tura) {
        String insert = "insert into tura values(?,?,?,?,?,?,?,?,?,?,?,?)";
        tmp.update(insert, null, tura.getPohorie(), tura.getCiel(), tura.getRocneObdobie(), tura.getObtiaznost(),
                tura.getCasovaNarocnost(), tura.getDlzka(), tura.isMimoChodnika(), tura.getHodnotenie(),
                tura.getNazov(), spracujPopisDoStringu(tura.getPopis()), tura.getDetail());
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
