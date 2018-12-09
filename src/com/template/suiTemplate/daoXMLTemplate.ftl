<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign x = ["id","createTime","updateTime","dataStatus"]>
<mapper namespace="${table.commonConfig.daoUrl!''}${table.className}Mapper">
    <resultMap id="BaseResultMap" type="${table.className}">
        <id column="id" property="id" jdbcType="INTEGER"/>
     <#list table.lstEntityInfo as info>
         <#if x?seq_contains(info.originName)?string("yes", "no") == "no">
        <result column="${info.originName}" property="${info.tuoFengName}" jdbcType="${(info.jdbcType == 'INT')?string('INTEGER', '${info.jdbcType}')}"/>
         </#if>
     </#list>
    </resultMap>

    <sql id="Base_Column_List">
    <#list table.lstEntityInfo as info>
        <#if info_index != (table.lstEntityInfo?size - 1)>
        ${info.originName},
        </#if>
        <#if info_index == (table.lstEntityInfo?size - 1)>
        ${info.originName}
        </#if>
    </#list>
    </sql>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id" parameterType="${table.className}">
        <selectKey resultType="java.lang.Long" keyProperty="id" order="AFTER">
            SELECT @@IDENTITY
        </selectKey>
        insert into ${table.originalTable}
        <trim prefix="(" suffix=")" suffixOverrides=",">
<#list table.lstEntityInfo as info>
            <if test="${info.tuoFengName} != null">
    <#if info_index != (table.lstEntityInfo?size - 1)>
                ${info.originName},
    </#if>
    <#if info_index == (table.lstEntityInfo?size - 1)>
                ${info.originName}
    </#if>
            </if>
</#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides="," >
<#list table.lstEntityInfo as info>
            <if test="${info.tuoFengName} != null">
    <#if info_index != (table.lstEntityInfo?size - 1)>
                ${r'#{'}${info.tuoFengName}${r'}'},
    </#if>
    <#if info_index == (table.lstEntityInfo?size - 1)>
                ${r'#{'}${info.tuoFengName}${r'}'}
    </#if>
            </if>
</#list>
        </trim>
    </insert>

    <select id="selectById" parameterType="java.lang.Long" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"></include>
        from ${table.originalTable}
        where id = ${r'#{id}'}
    </select>

    <select id="selectCount" parameterType="${table.className}" resultType="java.lang.Integer">
        select
        count(*)
        from ${table.originalTable}
        where
        1=1
<#list table.lstEntityInfo as info>
    <#if info_index == (table.lstEntityInfo?size - 1)>
        <#if info.jdbcType != 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null">
        </#if>
        <#if info.jdbcType == 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null and queryVO.${info.tuoFengName} != ''">
        </#if>
            and ${info.originName} = ${r'#{queryVO.'}${info.tuoFengName}${r'}'}
        </if>
    </#if>
    <#if info_index != (table.lstEntityInfo?size - 1)>
        <#if info.jdbcType != 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null">
        </#if>
        <#if info.jdbcType == 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null and queryVO.${info.tuoFengName} != ''">
        </#if>
            and ${info.originName} = ${r'#{queryVO.'}${info.tuoFengName}${r'}'}
        </if>
    </#if>
</#list>
    </select>

    <select id="selectList" parameterType="${table.className}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"></include>
        from ${table.originalTable}
        where
        1=1
<#list table.lstEntityInfo as info>
    <#if info_index == (table.lstEntityInfo?size - 1)>
        <#if info.jdbcType != 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null">
        </#if>
        <#if info.jdbcType == 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null and queryVO.${info.tuoFengName} != ''">
        </#if>
            and ${info.originName} = ${r'#{queryVO.'}${info.tuoFengName}${r'}'}
        </if>
    </#if>
    <#if info_index != (table.lstEntityInfo?size - 1)>
        <#if info.jdbcType != 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null">
        </#if>
        <#if info.jdbcType == 'VARCHAR'>
        <if test="queryVO.${info.tuoFengName} != null and queryVO.${info.tuoFengName} != ''">
        </#if>
            and ${info.originName} = ${r'#{queryVO.'}${info.tuoFengName}${r'}'}
        </if>
    </#if>
</#list>
        limit ${r'#{'}startIndex${r'}'}, ${r'#{'}endIndex${r'}'}
    </select>

    <update id="update" parameterType="${table.className}">
        update ${table.originalTable}
        <set>
    <#list table.lstEntityInfo as info>
        <#if info.jdbcType != 'VARCHAR'>
            <if test="${info.tuoFengName} != null">
        </#if>
        <#if info.jdbcType == 'VARCHAR'>
            <if test="${info.tuoFengName} != null and ${info.tuoFengName} != ''">
        </#if>
         <#if info_index == (table.lstEntityInfo?size - 1)>
                ${info.originName} = ${r'#{'}${info.tuoFengName}${r'}'}
         </#if>
         <#if info_index != (table.lstEntityInfo?size - 1)>
                ${info.originName} = ${r'#{'}${info.tuoFengName}${r'}'},
         </#if>
            </if>
    </#list>
        </set>
        where id = ${r'#{id}'}
    </update>

    <delete id="deleteById" parameterType="java.lang.Long">
        delete from ${table.originalTable}
        where id = ${r'#{id}'}
    </delete>

    <select id="getAll" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${table.originalTable}
    </select>
</mapper>