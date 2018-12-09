import com.villaday.dao.BaseDao;
import com.villaday.dao.BaseManager;
/**
 * 表名.${table.name}
 * @author zhangdahai
 * @history ${table.now} created
 */
public class ${table.className?cap_first}Manager extends BaseManager <${table.className?cap_first}> {

    private ${table.className?cap_first}Dao ${table.name}Dao;

    @Override
    public boolean checkBeforeRmove(long arg0, StringBuilder arg1) {
    return true;
    }

    @Override
    public boolean checkBeforeSave(${table.className?cap_first} arg0, StringBuilder arg1) {
    return true;
    }

    @Override
    public BaseDao <${table.className?cap_first}> getDao() {
        return ${table.name}Dao;
    }

    public void setActivityDao(${table.className?cap_first}Dao ${table.name}Dao) {
        this.${table.name}Dao = ${table.name}Dao;
    }
}
