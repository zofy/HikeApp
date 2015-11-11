package sk.ics.upjs.hikeapp;

public enum TuraDaOFactory {

    INSTANCE;

    public TuraDaO getTuraDaO() {
        return new MysqlTuraDaO();
    }
}
