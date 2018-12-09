<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign x = ["id","createTime","updateTime","dataStatus"]>
<mapper namespace="${table.className}Dao">
    <select id="getById" resultType="${table.className}">
        select * from ${table.name} where id=${r'#{id}'} and dataStatus = 0
    </select>

    <insert id="addNew" parameterType="${table.className}" keyProperty="id" useGeneratedKeys="true">
        insert into ${table.name}(
<#list table.lstEntityInfo as info>
    <#if x?seq_contains(info.name)?string("yes", "no") == "no">
        <#if info_index == (table.lstEntityInfo?size - 1)>
            ${info.name}
        </#if>
        <#if info_index != (table.lstEntityInfo?size - 1)>
            ${info.name},
        </#if>
    </#if>
</#list>
        )
        values(
<#list table.lstEntityInfo as info>
    <#if x?seq_contains(info.name)?string("yes", "no") == "no">
        <#if info_index == (table.lstEntityInfo?size - 1)>
            ${r'#{'}${info.name}${r'}'}
        </#if>
        <#if info_index != (table.lstEntityInfo?size - 1)>
            ${r'#{'}${info.name}${r'}'},
        </#if>
    </#if>
</#list>
        )
        <selectKey resultType="Long" keyProperty="id" order="AFTER">
            SELECT LAST_INSERT_ID()
        </selectKey>
    </insert>

    <update id="update" parameterType="${table.className}">
        update ${table.name}
        <set>
    <#list table.lstEntityInfo as info>
        <#if x?seq_contains(info.name)?string("yes", "no") == "no">
             <if test="${info.name} !=null and ${info.name} !=''">
         <#if info_index == (table.lstEntityInfo?size - 1)>
             ${info.name} = ${r'#{'}${info.name}${r'}'}
         </#if>
         <#if info_index != (table.lstEntityInfo?size - 1)>
             ${info.name} = ${r'#{'}${info.name}${r'}'},
         </#if>
             </if>
        </#if>
    </#list>
        </set>
        where id = ${r'#{id}'}
    </update>

    <update id="remove">
        update ${table.name} set dataStatus = 2 where id = ${r'#{id}'}
    </update>
</mapper>