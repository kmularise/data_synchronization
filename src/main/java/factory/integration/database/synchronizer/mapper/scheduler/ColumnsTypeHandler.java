package factory.integration.database.synchronizer.mapper.scheduler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ColumnsTypeHandler implements TypeHandler<List<String>> {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws
		SQLException {
		try {
			ps.setString(i, objectMapper.writeValueAsString(parameter));
		} catch (JsonProcessingException e) {
			throw new SQLException("Error converting List<String> to JSON string.", e);
		}
	}

	@Override
	public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
		String json = rs.getString(columnName);
		return parseJsonStringList(json);
	}

	@Override
	public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
		String json = rs.getString(columnIndex);
		return parseJsonStringList(json);
	}

	@Override
	public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String json = cs.getString(columnIndex);
		return parseJsonStringList(json);
	}

	private List<String> parseJsonStringList(String json) throws SQLException {
		try {
			JsonNode rootNode = objectMapper.readTree(json);
			JsonNode columnsNode = rootNode.get("columns");
			return objectMapper.convertValue(columnsNode, new TypeReference<List<String>>() {
			});
		} catch (Exception e) {
			throw new SQLException("Error parsing JSON string to List<String>.", e);
		}
	}
}
