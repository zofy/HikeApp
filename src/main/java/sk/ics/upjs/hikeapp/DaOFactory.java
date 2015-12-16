package sk.ics.upjs.hikeapp;

public enum DaOFactory {

    INSTANCE;

    public TuraDaO getTuraDaO() {
        return new MysqlTuraDaO();
    }

    public UzivatelDaO getUserDaO() {
        return new UzivatelMysqlDaO();
    }

    public FotkaDaO getFotkaDaO() {
        return new MysqlFotkaDaO();
    }
}
