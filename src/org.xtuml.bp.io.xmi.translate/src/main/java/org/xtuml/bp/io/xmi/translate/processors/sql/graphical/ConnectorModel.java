package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

public class ConnectorModel {
    private String key;
    private String sql;

    public ConnectorModel(String sql, String geId) {
        this.setKey(geId);
        this.sql = sql;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof ConnectorModel) {
                if (((ConnectorModel) obj).getKey().equals(getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }
}
