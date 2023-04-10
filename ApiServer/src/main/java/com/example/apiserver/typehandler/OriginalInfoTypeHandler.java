package com.example.apiserver.typehandler;

import com.example.apiserver.dto.OriginalInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OriginalInfoTypeHandler implements TypeHandler<OriginalInfo> {

    @Override
    public void setParameter(PreparedStatement ps, int i, OriginalInfo parameter, JdbcType jdbcType) throws SQLException {
        // 객체를 JSON 문자열로 변환하여 DB에 저장합니다.
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ps.setString(i, json);
    }

    @Override
    public OriginalInfo getResult(ResultSet rs, String columnName) throws SQLException {
        // JSON 문자열을 객체로 변환하여 반환합니다.
        String json = rs.getString(columnName);
        return fromJson(json);
    }

    @Override
    public OriginalInfo getResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return fromJson(json);
    }

    @Override
    public OriginalInfo getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return fromJson(json);
    }

    private OriginalInfo fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, OriginalInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}