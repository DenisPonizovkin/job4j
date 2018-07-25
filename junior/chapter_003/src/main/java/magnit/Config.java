package magnit;

public class Config {

    private final String base;
    private final String table;
    private final int n;

    public Config(String base, String table, int n) {
        this.base = System.getProperty("user.dir") + "/" + base;
        this.table = table;
        this.n = n;
    }

    public String getBase() {
        return base;
    }

    public String getTable() {
        return table;
    }

    public int getN() {
        return n;
    }
}
