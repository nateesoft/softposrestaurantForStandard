package com.softpos.pos.core.controller;

import java.util.List;
import java.util.Map;
import com.softpos.util.DatabaseUtility;

public class DbProduct {

    private final DatabaseUtility du = new DatabaseUtility();

    public List<Map<String, Object>> getAllData() {
        return du.queryList("SELECT * FROM product WHERE pactive = 'Y' and pfix='F' ORDER BY pcode");
    }

    /** Returns products matching keyword in PCode or PDesc. includeInactive=true adds PActive='N'. Raw ASCII Thai. */
    public List<Map<String, Object>> searchProducts(String keyword, boolean includeInactive) {
        String activeClause = includeInactive ? "and PActive in ('Y','N')" : "and PActive='Y'";
        String sql = "select PCode, PDesc, PUnit1, PPrice11, PGroup, GroupName "
                + "from product p, groupfile g "
                + "where p.pgroup=g.groupcode "
                + "and (PCode like '%" + keyword + "%' "
                + "or PDesc like '%" + keyword + "%') "
                + activeClause + " order by PCode";
        return du.queryList(sql);
    }

    public Map<String, Object> getAtPk(String pcode) {
        return du.querySingle("SELECT * FROM product WHERE pcode=? AND pactive = 'Y' and PFix='F'", pcode);
    }

    public List<Map<String, Object>> getAtPgroup(String pgroup) {
        return du.queryList("SELECT * FROM product WHERE pgroup=? AND pactive = 'Y' and pfix='F' ORDER BY pcode", pgroup);
    }

    public boolean seekAtPk(String pcode) {
        boolean success = false;
        Map<String, Object> data = getAtPk(pcode);
        if (data != null) {
            if (!data.isEmpty()) {
                success = true;
            }
        }
        return success;
    }
}
