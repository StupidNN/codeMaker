package model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 表名.${table.name}
 *
 * @history ${table.now} created
 */
public class ${table.className?cap_first} {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
<#assign x = []>
<#list table.lstEntityInfo as info>
    <#if x?seq_contains(info.tuoFengName)?string("yes", "no") == "no">
        <#if info.comment ??>
    /**
     * ${info.comment}
     */
        </#if>
    private ${info.type} ${info.tuoFengName};
    </#if>
</#list>
<#list table.lstEntityInfo as info>
    <#if x?seq_contains(info.tuoFengName)?string("yes", "no") == "no">

	public ${info.type} get${info.tuoFengName?cap_first}() {
		return ${info.tuoFengName};
	}

	public void set${info.tuoFengName?cap_first}(${info.type} ${info.tuoFengName}) {
		this.${info.tuoFengName} = ${info.tuoFengName};
	}
    </#if>
</#list>

    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
