import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 表名.${table.name}
 *
 * @history ${table.now} created
 */
@Service
public class ${table.className?cap_first}Manager {
    private Logger logger = LoggerFactory.getLogger(${table.className}Manager.class);

    @Autowired
    private ${table.className?cap_first}Mapper ${table.className?uncap_first}Mapper;

    public boolean save(${table.className?cap_first} entity) {
        if (entity.getId() == null) {
            return ${table.className?uncap_first}Mapper.insert(entity) == 1;
        } else {
            return this.update(entity);
        }
    }

    public boolean update(${table.className?cap_first} entity) {
        if (entity.getId() == null) {
            logger.error("更新实体:{}出错！id为空！", "${table.className?cap_first}");
            return false;
        }
        return ${table.className?uncap_first}Mapper.update(entity) == 1;
    }

    public PageVO<${table.className?cap_first}> queryForPage(PageVO pageVO) {
        ${table.className?cap_first} queryVO = new ${table.className?cap_first}();
        //:todo 设置pageVO中的查询参数到queryVO中
        int totalCount = ${table.className?uncap_first}Mapper.selectCount(queryVO);
        pageVO.setTotalCount(totalCount);
        List dataList = ${table.className?uncap_first}Mapper.selectList(queryVO, pageVO.getStartIndex(), pageVO.getEndIndex());
        pageVO.setDataList(dataList);
        return pageVO;
    }
}
