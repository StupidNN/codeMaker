import org.apache.ibatis.annotations.Param;
/**
 * 表名.${table.name}
 *
 * @history ${table.now} created
 */
public interface ${table.className?cap_first}Mapper {

    int insert(${table.className?cap_first} entity);

    ${table.className?cap_first} selectById(Long id);

    int update(${table.className?cap_first} entity);

    int selectCount(@Param("queryVO") ${table.className?cap_first} queryVO);

    List<${table.className?cap_first}> selectList(@Param("queryVO") ${table.className?cap_first} queryVO, @Param("startIndex") int startIndex, @Param("endIndex") int endIndex);

    int deleteById(Long id);

    List<${table.className?cap_first}> getAll();
}