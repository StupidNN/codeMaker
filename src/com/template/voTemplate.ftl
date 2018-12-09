package model;

import com.villaday.appmodel.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 表名.${table.name}

 * @author zhangdahai
 * @history ${table.now} created
 */
public class ${table.className?cap_first} extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
<#assign x = ["id", "createTime", "lastUpdateTime", "dataStatus"]>
<#list table.lstEntityInfo as info>
    <#if x?seq_contains(info.name)?string("yes", "no") == "no">
        <#if info.comment ??>
    /**
     * ${info.comment}
     */
        </#if>
    private ${info.type} ${info.name};
    </#if>
</#list>
<#list table.lstEntityInfo as info>
    <#if x?seq_contains(info.name)?string("yes", "no") == "no">

	public ${info.type} get${info.name?cap_first}() {
		return ${info.name};
	}

	public void set${info.name?cap_first}(${info.type} ${info.name}) {
		this.${info.name} = ${info.name};
	}
    </#if>
</#list>
    public String toString(){
        return   ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
