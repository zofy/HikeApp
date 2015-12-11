package sk.ics.upjs.hikeapp;

public enum DaOFactory {

    INSTANCE;

    public TuraDaO getTuraDaO() {
        return new MysqlTuraDaO();
    }

    public UzivatelDaO getUser() {
        return new UzivatelMysqlDaO();
    }
}
