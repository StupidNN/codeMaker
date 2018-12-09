package com.cardniu.docard.app.bossservice.imp;

/**
 * 表名.${table.name}
 *
 * @history ${table.now} created
 */
@Service
public class ${table.className?cap_first}ServiceImpl implements ${table.className?cap_first}Service {
    private static Logger log = LoggerFactory.getLogger(${table.className?cap_first}ServiceImpl.class);
    @Autowired
    private ${table.className?cap_first}Mapper ${table.className?uncap_first}Mapper;

    @Override
    public BaseDao<${table.className?cap_first}> getDao() {
        return ${table.className?uncap_first}Mapper;
    }
}
